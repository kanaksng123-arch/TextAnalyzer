package kanak.project.textAnalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AIAnalysisResult {
    private String sentiment;
    private String sentimentReason;
    private String summary;
    private List<String> keyThemes;
}
