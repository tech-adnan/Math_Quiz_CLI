// Author Name: Adnan Shaikh
// Roll No: 03
// Displaying The 2 Player Maths Quiz Game
// Start Date: 14/08/2024
// Modified Date: 14/08/2024
// Description: This is a game to play between two players who want to check who is better at Calculating Numericals.

package TwoPlayerMathQuiz;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class TwoPlayerMathQuiz {
    static Scanner input = new Scanner(System.in);
    static Random rand = new Random();
    static int scorePlayer1 = 0;
    static int scorePlayer2 = 0;
    static final int MAX_TIME = 10; 

    public static void main(String[] args) {
        System.out.println("Welcome to the Math Quiz for Two Players!");
        System.out.println("Each player will be having " + MAX_TIME + " seconds to respond."); 

        System.out.print("Before Starting the game, Just wanted to ask you'll how many rounds the game should have? ");
        int totalRounds = input.nextInt();

        for (int round = 1; round <= totalRounds; round++) {
            System.out.println("\nRound " + round);
            presentQuestion("Hercules");
            presentQuestion("Thor");
        }

        System.out.println("\nFinal Scores:");
        System.out.println("Hercules: " + scorePlayer1);
        System.out.println("Thor: " + scorePlayer2);

        if (scorePlayer1 > scorePlayer2) {
            System.out.println("Hercules is the Forgone winner!");
        } else if (scorePlayer2 > scorePlayer1) {
            System.out.println("Thor is the Absolute winner!");
        } else {
            System.out.println("Sorry to say but it's a tie, A disappointing end to an awesome game!"); 
        }
    }

    static void presentQuestion(String currentPlayer) {
        int number1 = rand.nextInt(20) + 1;
        int number2 = rand.nextInt(20) + 1;
        char mathOperator = getRandomOperator();

        System.out.println(currentPlayer + ", solve this:");
        System.out.println(number1 + " " + mathOperator + " " + number2 + " = ?");

        int expectedAnswer = calculate(number1, number2, mathOperator);
        AtomicBoolean isAnswered = new AtomicBoolean(false);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!isAnswered.get()) {
                    System.out.println("\nTime is up! The correct answer to the question was: " + expectedAnswer);
                    isAnswered.set(true);
                }
            }
        };

        timer.schedule(task, MAX_TIME * 1000);

        new Thread(() -> {
            System.out.print("Your answer: ");
            int playerResponse = input.nextInt();

            if (!isAnswered.get()) {
                isAnswered.set(true);
                timer.cancel();

                if (playerResponse == expectedAnswer) {
                    System.out.println("Correct!");
                    if (currentPlayer.equals("Hercules")) {
                        scorePlayer1++;
                    } else {
                        scorePlayer2++;
                    }
                } else {
                    System.out.println("Absolutely Wrong! The correct answer was: " + expectedAnswer);
                }
            }
        }).start();

        try {
            Thread.sleep(MAX_TIME * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static char getRandomOperator() {
        char[] operators = {'+', '-', '*', '/'};
        return operators[rand.nextInt(operators.length)];
    }

    static int calculate(int a, int b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    System.out.println("Division by zero is undefined. Setting answer to 0.");
                    return 0;
                }
                return a / b;
            default:
                return 0;
        }
    }
}
