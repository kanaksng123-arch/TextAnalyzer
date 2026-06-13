package kanak.project.textAnalyzer.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SentimentService {

    private static final List<String> POSITIVE_WORDS = Arrays.asList(
            "good", "great", "excellent", "amazing", "wonderful", "fantastic",
            "happy", "joy", "love", "beautiful", "awesome", "brilliant",
            "outstanding", "superb", "perfect", "best", "nice", "positive",
            "success", "win", "winning", "incredible", "delightful", "pleased",
            "glad", "cheerful", "excited", "thrilled", "grateful", "thankful",
            "hope", "inspire", "motivated", "confident", "proud", "peaceful"
    );

    private static final List<String> NEGATIVE_WORDS = Arrays.asList(
            "bad", "terrible", "awful", "horrible", "worst", "hate",
            "sad", "angry", "disgusting", "ugly", "poor", "failure",
            "wrong", "evil", "negative", "lose", "losing", "dreadful",
            "pathetic", "miserable", "depressed", "anxious", "fear", "scared",
            "worried", "frustrated", "disappointed", "upset", "crying", "pain",
            "hurt", "suffer", "problem", "difficult", "hard", "struggle"
    );

    public double calculatePositiveScore(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        long positiveCount = Arrays.stream(words)
                .filter(POSITIVE_WORDS::contains)
                .count();
        return words.length > 0 ? (double) positiveCount / words.length * 100 : 0;
    }

    public double calculateNegativeScore(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        long negativeCount = Arrays.stream(words)
                .filter(NEGATIVE_WORDS::contains)
                .count();
        return words.length > 0 ? (double) negativeCount / words.length * 100 : 0;
    }

    public String determineSentiment(double positiveScore, double negativeScore) {
        if (positiveScore > negativeScore && positiveScore > 5) {
            return "POSITIVE";
        } else if (negativeScore > positiveScore && negativeScore > 5) {
            return "NEGATIVE";
        } else {
            return "NEUTRAL";
        }
    }
}
