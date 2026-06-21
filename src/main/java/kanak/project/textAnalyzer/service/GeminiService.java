package kanak.project.textAnalyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kanak.project.textAnalyzer.dto.AIAnalysisResult;
import kanak.project.textAnalyzer.dto.GeminiRequest;
import kanak.project.textAnalyzer.dto.GeminiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent")
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AIAnalysisResult analyze(String text) {
        String prompt = """
                Analyze the following text. Respond ONLY with valid JSON, no markdown, no extra text, in this exact structure:
                {"sentiment": "positive/negative/neutral", "sentimentReason": "one sentence why", "summary": "1-2 sentence summary", "keyThemes": ["theme1", "theme2", "theme3"]}

                Text: %s
                """.formatted(text);

        try {
            GeminiResponse response = webClient.post()
                    .header("x-goog-api-key", apiKey)
                    .bodyValue(GeminiRequest.of(prompt))
                    .retrieve()
                    .onStatus(s -> s.value() == 429, r -> Mono.error(new RuntimeException("Rate limit")))
                    .onStatus(HttpStatusCode::is5xxServerError, r -> Mono.error(new RuntimeException("Server error")))
                    .bodyToMono(GeminiResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)))
                    .block();

            String rawJson = response.extractText().replaceAll("```json", "").replaceAll("```", "").trim();
            log.info("Gemini AI analysis completed successfully");
            return objectMapper.readValue(rawJson, AIAnalysisResult.class);
        } catch (Exception e) {
            log.error("Gemini AI analysis failed: {}", e.getMessage());
            return AIAnalysisResult.builder()
                    .sentiment("unknown")
                    .sentimentReason("AI analysis unavailable right now — please try again.")
                    .summary("")
                    .keyThemes(List.of())
                    .build();
        }
    }
}