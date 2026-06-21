package kanak.project.textAnalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class GeminiRequest {
    private List<Content> contents;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class Content { private List<Part> parts; }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class Part { private String text; }

    public static GeminiRequest of(String prompt) {
        return new GeminiRequest(List.of(new Content(List.of(new Part(prompt)))));
    }
}
