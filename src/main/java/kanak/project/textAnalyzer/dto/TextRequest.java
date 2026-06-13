package kanak.project.textAnalyzer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class TextRequest {
    @NotBlank(message = "Text cannot be empty")
    @Size(min = 1, max = 5000, message = "Text must be between 1 and 5000 characters")
    private String text;
}
