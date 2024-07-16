// SID : 2290854
// Group 10

package CoreNLP;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SentimentAnalysisEvaluation {

    public static void main(String[] args) {
        String labelFilePath = "src/main/resources/label.txt"; // Path to the label file
        String predictionFilePath = "predictions.txt"; // Path to the prediction file

        // Read the label file and store its contents in an array
        int[] labels = readLabelFile(labelFilePath);
        // Read the prediction file and store its contents in an array
        int[] predictions = readPredictionFile(predictionFilePath);

        // Calculate evaluation metrics
        accuracy(labels, predictions);
        calculateMetrics(labels, predictions);
        calculateRecall(labels, predictions);
    }
    public static void evaluate(String predictionFilePath) {
        String labelFilePath = "src/main/resources/label.txt"; // Path to the label file

        // Read the label file and store its contents in an array
        int[] labels = readLabelFile(labelFilePath);
        // Read the prediction file and store its contents in an array
        int[] predictions = readPredictionFile(predictionFilePath);

        // Calculate evaluation metrics
        accuracy(labels, predictions);
        calculateMetrics(labels, predictions);
        calculateRecall(labels, predictions);
    }

    public static void calculateMetrics(int[] labels, int[] predictions) {
        int numClasses = 3; // Assuming there are 3 classes (1, 2, 3)

        double[] precision = new double[numClasses];
        double[] recall = new double[numClasses];

        for (int c = 1; c <= numClasses; c++) {
            int truePositives = 0;
            int falsePositives = 0;
            int falseNegatives = 0;

            // Calculate true positives, false positives, and false negatives for class c
            for (int i = 0; i < labels.length; i++) {
                if (labels[i] == c && predictions[i] == c) {
                    truePositives++;
                } else if (labels[i] != c && predictions[i] == c) {
                    falsePositives++;
                } else if (labels[i] == c && predictions[i] != c) {
                    falseNegatives++;
                }
            }

            // Calculate precision and recall for class c
            double classPrecision = (double) truePositives / (truePositives + falsePositives);
            double classRecall = (double) truePositives / (truePositives + falseNegatives);

            // Store precision and recall for class c
            precision[c - 1] = classPrecision;
            recall[c - 1] = classRecall;
        }

        // Calculate average precision and recall across all classes
        double avgPrecision = 0;
        double avgRecall = 0;
        for (int i = 0; i < numClasses; i++) {
            avgPrecision += precision[i];
            avgRecall += recall[i];
        }
        avgPrecision /= numClasses;
        avgRecall /= numClasses;

        // Print average precision and recall
        System.out.println("Average Precision: " + avgPrecision);
        System.out.println("Average Recall: " + avgRecall);
    }

    public static void accuracy(int[] labels, int[] predictions) {
        int truePositives = 0;

        // Calculate true positives
        for (int i = 0; i < labels.length; i++) {
            if (labels[i] == predictions[i]) {
                truePositives++;
            }
        }

        // Calculate accuracy
        double accuracy = (double) truePositives / labels.length;

        // Print the evaluation metric
        System.out.println("Accuracy: " + accuracy);
    }

    public static int[] readLabelFile(String filePath) {
        List<Integer> labelsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                labelsList.add(Integer.parseInt(line));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading the label file: " + e.getMessage());
        }
        // Convert the list of labels to an array
        int[] labels = new int[labelsList.size()];
        for (int i = 0; i < labelsList.size(); i++) {
            labels[i] = labelsList.get(i);
        }
        return labels;
    }

    public static int[] readPredictionFile(String filePath) {
        List<Integer> predictionsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                predictionsList.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading the prediction file: " + e.getMessage());
        }
        // Convert the list of predictions to an array
        int[] predictions = new int[predictionsList.size()];
        for (int i = 0; i < predictionsList.size(); i++) {
            predictions[i] = predictionsList.get(i);
        }
        return predictions;
    }

    public static void printArray(int[] arr) {
        for (int val : arr) {
            System.out.println(val);
        }
    }

    public static void calculateRecall(int[] labels, int[] predictions) {
        int numClasses = 3; // Assuming there are 3 classes (1, 2, 3)

        double[] recall = new double[numClasses];

        for (int c = 1; c <= numClasses; c++) {
            int truePositives = 0;
            int falseNegatives = 0;

            // Calculate true positives and false negatives for class c
            for (int i = 0; i < labels.length; i++) {
                if (labels[i] == c && predictions[i] == c) {
                    truePositives++;
                } else if (labels[i] == c && predictions[i] != c) {
                    falseNegatives++;
                }
            }

            // Calculate recall for class c
            double classRecall = (double) truePositives / (truePositives + falseNegatives);

            // Store recall for class c
            recall[c - 1] = classRecall;
        }

        // Print recall for each class
        for (int i = 0; i < numClasses; i++) {
            System.out.println("Recall for class " + (i + 1) + ": " + recall[i]);
        }
    }
}
