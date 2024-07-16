// SID : 2290854
// Group 10

package CoreNLP;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentClass;
import edu.stanford.nlp.util.CoreMap;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class SentimentAnalysis {

    public static void main(String[] args) {
        // Initialize Stanford NLP pipeline
        StanfordCoreNLP stanfordCoreNLP = initializePipeline();

        // Process text in batches
        processTextInBatches(stanfordCoreNLP);

        // After predictions are written, call SentimentAnalysisEvaluation
        String predictionFilePath = "predictions.txt";
        SentimentAnalysisEvaluation.evaluate(predictionFilePath);
    }

    private static StanfordCoreNLP initializePipeline() {
        // Create properties for Stanford NLP pipeline
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        return new StanfordCoreNLP(props);
    }

    private static void processTextInBatches(StanfordCoreNLP pipeline) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                CoreNLP.SentimentAnalysis.class.getResourceAsStream("/Review.txt")))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("predictions.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Clean and preprocess the text data
                    String preprocessedText = preprocessText(line);
                    // Tokenization
                    Annotation annotation = new Annotation(preprocessedText);
                    pipeline.annotate(annotation);
                    // Retrieve sentiment for the current line
                    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
                    System.out.println(sentences);
                    if (!sentences.isEmpty()) {
                        CoreMap sentence = sentences.get(0); // Get the first sentence
                        String sentiment = sentence.get(SentimentClass.class);
                        // Map sentiment to numerical values (positive: 3, neutral: 2, negative: 1)
                        int sentimentValue = mapSentimentToValue(sentiment);
                        // Write sentiment value to file
                        writer.write(String.valueOf(sentimentValue));
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String preprocessText(String text) {
        // Convert to lowercase
        text = text.toLowerCase();

        // Remove punctuation
        text = text.replaceAll("[^a-zA-Z0-9\\s]", "");

        // Remove extra whitespaces
        text = text.trim().replaceAll(" +", " ");

        // Remove stop words
        String[] stopWords = {"the", "and", "is", "in", "it", "to", "this", "of"};
        for (String stopWord : stopWords) {
            text = text.replaceAll("\\b" + stopWord + "\\b", "");
        }

        // Perform stemming or lemmatization (not implemented in this example)

        return text;
    }

    private static int mapSentimentToValue(String sentiment) {
        switch (sentiment) {
            case "Positive":
            case "Very positive":
                return 3;
            case "Neutral":
                return 2;
            case "Negative":
            case "Very negative":
                return 1;
            default:
                return 0; // Unknown sentiment
        }
    }
}
