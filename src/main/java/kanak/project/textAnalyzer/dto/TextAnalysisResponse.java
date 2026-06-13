package kanak.project.textAnalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextAnalysisResponse {

    private String sentiment;
    private int wordCount;
    private int characterCount;
    private int sentenceCount;
    private double positiveScore;
    private double negativeScore;
    private String message;
}
