package kanak.project.textAnalyzer.repository;

import kanak.project.textAnalyzer.entity.TextAnalysis;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TextAnalysisRepository extends MongoRepository<TextAnalysis, ObjectId> {
    List<TextAnalysis> findBySentiment(String sentiment);
}