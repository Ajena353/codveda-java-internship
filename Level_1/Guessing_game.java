import java.util.Scanner;
import java.util.Random;

public class Guessing_game {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.println("== Number Guessing Game ==");
        int secretNumber = random.nextInt(100)+1;
        int attempts = 5;
        boolean hasGuessed = false;
        while(attempts>0){
            System.out.println("Enter your guess (1-100): ");
            int guess = sc.nextInt();
            if(guess==secretNumber){
                System.out.println("Congratulations! You've guessed the number!");
                hasGuessed = true;
                break;
            }
            else if(guess<secretNumber){
                System.out.println("Too low!");
            }
            else{
                System.out.println("Too high!");
            }
            attempts--;
            System.out.println("Attempts remaining: " + attempts);
        }
        if(!hasGuessed){
            System.out.println("Sorry, you've run out of attempts. The number was: " + secretNumber);
        }
        sc.close();
    }
}