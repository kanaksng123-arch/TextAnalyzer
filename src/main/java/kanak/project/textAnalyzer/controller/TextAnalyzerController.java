package kanak.project.textAnalyzer.controller;

import jakarta.validation.Valid;
import kanak.project.textAnalyzer.dto.TextAnalysisResponse;
import kanak.project.textAnalyzer.dto.TextRequest;
import kanak.project.textAnalyzer.entity.TextAnalysis;
import kanak.project.textAnalyzer.service.TextAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analyze")
@CrossOrigin(origins = "*")
public class TextAnalyzerController {

    @Autowired
    private TextAnalyzerService textAnalyzerService;

    @PostMapping
    public ResponseEntity<TextAnalysisResponse> analyzeText(
            @Valid @RequestBody TextRequest request) {
        TextAnalysisResponse response = textAnalyzerService.analyzeText(request.getText());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TextAnalysis>> getHistory() {
        List<TextAnalysis> history = textAnalyzerService.getHistory();
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @GetMapping("/history/{sentiment}")
    public ResponseEntity<List<TextAnalysis>> getHistoryBySentiment(
            @PathVariable String sentiment) {
        List<TextAnalysis> history = textAnalyzerService.getHistoryBySentiment(sentiment);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("Text Analyzer is running!", HttpStatus.OK);
    }
}
