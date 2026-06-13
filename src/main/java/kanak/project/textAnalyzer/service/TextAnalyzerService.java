package kanak.project.textAnalyzer.service;



import kanak.project.textAnalyzer.dto.TextAnalysisResponse;
import kanak.project.textAnalyzer.entity.TextAnalysis;
import kanak.project.textAnalyzer.repository.TextAnalysisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TextAnalyzerService {

    @Autowired
    private TextAnalysisRepository textAnalysisRepository;

    @Autowired
    private SentimentService sentimentService;

    public TextAnalysisResponse analyzeText(String text) {
        // Calculate stats
        int wordCount = countWords(text);
        int characterCount = text.length();
        int sentenceCount = countSentences(text);

        // Calculate sentiment scores
        double positiveScore = sentimentService.calculatePositiveScore(text);
        double negativeScore = sentimentService.calculateNegativeScore(text);
        String sentiment = sentimentService.determineSentiment(positiveScore, negativeScore);

        // Save to MongoDB
        TextAnalysis analysis = new TextAnalysis();
        analysis.setOriginalText(text);
        analysis.setSentiment(sentiment);
        analysis.setWordCount(wordCount);
        analysis.setCharacterCount(characterCount);
        analysis.setSentenceCount(sentenceCount);
        analysis.setPositiveScore(positiveScore);
        analysis.setNegativeScore(negativeScore);
        analysis.setAnalyzedAt(LocalDateTime.now());
        textAnalysisRepository.save(analysis);

        log.info("Text analyzed — Sentiment: {}, Words: {}", sentiment, wordCount);

        // Return response
        return TextAnalysisResponse.builder()
                .sentiment(sentiment)
                .wordCount(wordCount)
                .characterCount(characterCount)
                .sentenceCount(sentenceCount)
                .positiveScore(Math.round(positiveScore * 100.0) / 100.0)
                .negativeScore(Math.round(negativeScore * 100.0) / 100.0)
                .message(getSentimentMessage(sentiment))
                .build();
    }

    public List<TextAnalysis> getHistory() {
        return textAnalysisRepository.findAll();
    }

    public List<TextAnalysis> getHistoryBySentiment(String sentiment) {
        return textAnalysisRepository.findBySentiment(sentiment.toUpperCase());
    }

    private int countWords(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        return text.trim().split("\\s+").length;
    }

    private int countSentences(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        String[] sentences = text.split("[.!?]+");
        return (int) java.util.Arrays.stream(sentences)
                .filter(s -> !s.trim().isEmpty())
                .count();
    }

    private String getSentimentMessage(String sentiment) {
        return switch (sentiment) {
            case "POSITIVE" -> "The text has a positive tone!";
            case "NEGATIVE" -> "The text has a negative tone.";
            default -> "The text has a neutral tone.";
        };
    }
}
