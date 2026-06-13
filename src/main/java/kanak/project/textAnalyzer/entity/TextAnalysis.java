package kanak.project.textAnalyzer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "text_analysis")
@Data
@NoArgsConstructor
public class TextAnalysis {

        @Id
        private ObjectId id;
        private String originalText;
        private String sentiment;       // POSITIVE, NEGATIVE, NEUTRAL
        private int wordCount;
        private int characterCount;
        private int sentenceCount;
        private double positiveScore;
        private double negativeScore;
        private LocalDateTime analyzedAt;
    }

