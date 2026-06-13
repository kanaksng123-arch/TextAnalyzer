# TextAnalyzer 📝

A Spring Boot REST API that analyzes text for sentiment, word count, character count, and sentence count, with results stored in MongoDB.

## 🔗 Links
- Live Demo: https://textanalyzer-production.up.railway.app
- GitHub: https://github.com/kanaksng123-arch/TextAnalyzer

## 🛠️ Tech Stack
- Framework: Spring Boot 3.5.15
- Database: MongoDB
- Validation: Spring Validation (Jakarta)
- Monitoring: Spring Boot Actuator
- Build Tool: Maven
- Language: Java 17

## ✨ Features
- Analyze text for sentiment (POSITIVE / NEGATIVE / NEUTRAL)
- Word count, character count, and sentence count calculation
- Positive/negative sentiment scoring
- Store every analysis in MongoDB
- View full analysis history
- Filter history by sentiment
- Global exception handling
- Health check endpoint

## 🏗️ Architecture
```
Client → Controllers → Services (Text Analyzer + Sentiment) → MongoDB
```

## 🚀 API Endpoints

### Analyze
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /analyze | Analyze text and save result |

### History
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /analyze/history | Get all analysis history |
| GET | /analyze/history/{sentiment} | Get history filtered by sentiment |

### Health
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /analyze/health | Health check |

## 🔐 Usage
Send a POST request to `/analyze`:
```json
{
  "text": "I love this project, it's amazing!"
}
```

Response:
```json
{
  "sentiment": "POSITIVE",
  "wordCount": 6,
  "characterCount": 35,
  "sentenceCount": 1,
  "positiveScore": 0.5,
  "negativeScore": 0.0,
  "message": "The text has a positive tone!"
}
```

## ⚙️ Environment Variables
- MONGODB_URI

## 👨‍💻 Developer
Kanak — B.Tech EEE Student, BPIT Delhi
Self-learning Java backend development
