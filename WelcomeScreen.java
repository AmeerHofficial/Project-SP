// SID : 2290854
// Group 10

package CoreNLP;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WelcomeScreen {

    public static void main(String[] args) {
        showWelcomeScreen();
    }

    public static void showWelcomeScreen() {
        // Stylish welcome message
        System.out.println("**********************************************");
        System.out.println("*                                            *");
        System.out.println("*    Welcome to Textual Feedback Analysis    *");
        System.out.println("*         Using Java NLP Libraries           *");
        System.out.println("*                                            *");
        System.out.println("**********************************************");

        // Schedule main code execution after 5 seconds
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(MainCode::execute, 5, TimeUnit.SECONDS);
    }

    static class MainCode {
        public static void execute() {            // Your main code goes here
            System.out.println("Executing main code...");
            SentimentAnalysis.main(new String[0]);

        }
    }
}
