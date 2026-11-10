
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class Question {
    String question;
    String[] options;
    int correctAnswer;

    Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    void displayQuestion(int number) {
        System.out.println("\nQ" + number + ". " + question);

        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    boolean checkAnswer(int answer) {
        return answer == correctAnswer;
    }
}

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Question> questions = new ArrayList<>();

    static void loadQuestions() {
        questions.add(new Question(
            "Which language is used for Android development?",
            new String[]{"Java", "HTML", "CSS", "SQL"}, 1
        ));

        questions.add(new Question(
            "Which data structure follows FIFO?",
            new String[]{"Stack", "Queue", "Tree", "Graph"}, 2
        ));

        questions.add(new Question(
            "Which keyword creates an object in Java?",
            new String[]{"class", "static", "new", "void"}, 3
        ));

        questions.add(new Question(
            "Which method is the entry point of Java?",
            new String[]{"start()", "run()", "main()", "init()"}, 3
        ));

        questions.add(new Question(
            "Which collection allows dynamic size?",
            new String[]{"ArrayList", "int", "char", "float"}, 1
        ));
    }

    static void startQuiz() {
        System.out.print("\nEnter participant name: ");
        sc.nextLine();
        String name = sc.nextLine().trim();

        if (name.isEmpty() || name.contains("|")) {
            System.out.println("Invalid participant name.");
            return;
        }

        int score = 0;
        int questionNumber = 1;

        System.out.println("\n===== QUIZ STARTED =====");

        for (Question q : questions) {
            q.displayQuestion(questionNumber);

            System.out.print("Your answer (1-4): ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Quiz cancelled.");
                sc.next();
                return;
            }

            int answer = sc.nextInt();

            if (answer < 1 || answer > 4) {
                System.out.println("Invalid option. Quiz cancelled.");
                return;
            }

            if (q.checkAnswer(answer)) {
                System.out.println("Correct answer!");
                score++;
            } else {
                System.out.println("Wrong answer!");
            }

            questionNumber++;
        }

        int total = questions.size();
        double percentage = (score * 100.0) / total;

        System.out.println("\n===== QUIZ RESULT =====");
        System.out.println("Participant: " + name);
        System.out.println("Correct Answers: " + score);
        System.out.println("Wrong Answers: " + (total - score));
        System.out.printf("Percentage: %.2f%%%n", percentage);

        saveResult(name, score, total, percentage);
    }

    static void saveResult(
        String name, int score, int total, double percentage
    ) {
        try (FileWriter writer = new FileWriter("quiz_results.txt", true)) {
            writer.write(
                name + " | Score: " + score + "/" + total
                + " | Percentage: "
                + String.format("%.2f", percentage) + "%\n"
            );

            System.out.println("Result saved successfully.");

        } catch (IOException e) {
            System.out.println("Unable to save result.");
        }
    }

    static void displayResults() {
        try (Scanner reader = new Scanner(
            new java.io.File("quiz_results.txt")
        )) {
            System.out.println("\n===== PREVIOUS QUIZ RESULTS =====");

            if (!reader.hasNextLine()) {
                System.out.println("No results available.");
                return;
            }

            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine());
            }

        } catch (IOException e) {
            System.out.println("No result file found yet.");
        }
    }

    public static void main(String[] args) {
        loadQuestions();

        while (true) {
            System.out.println("\n===== QUIZ MANAGEMENT SYSTEM =====");
            System.out.println("1. Start Quiz");
            System.out.println("2. View Previous Results");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                sc.next();
                continue;
            }

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    startQuiz();
                    break;

                case 2:
                    displayResults();
                    break;

                case 3:
                    System.out.println("Quiz system closed.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
