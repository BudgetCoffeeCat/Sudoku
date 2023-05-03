import java.util.Scanner;

/*  Main.java
 *  25 April 2023
 *  Deepesh Kothari
 *  Purpose:
 *      Creates a class that runs a game of sudoku
 */
public class Main {
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku(15);
        int value, wins = 0;
        boolean win = false, play = true;
        System.out.println("Done making board.");
        while (play) {
            sudoku.reset(getInt("Set Your difficulty\n(the number of squares that start filled out)(integer from 0 to 80)", 0, 80));
            while (!win) {
                sudoku.print();
                Position position = new Position();
                value = getInt("\u001B[36mEnter the column # \u001B[35mthen Enter the row # \u001B[32m then enter the value (integer from 0-9, 0 to represent blank\u001B[37m)\nIf you put value 4 in spot (5, 9), you would write \u001B[36m5\u001B[35m9\u001B[32m4\u001B[37m", 110, 999);
                position.row = (value / 10) % 10;
                position.column = value / 100;
                value = value % 10;
                try {
                    sudoku.setPosition(position, value);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                win = sudoku.win();
                if (win) {
                    sudoku.print();
                    System.out.println("You solved the puzzle!");
                } else {
                    System.out.println("The puzzle is not solved.");
                }
            }
            wins++;
            value = getInt("Do you want to play again?(1:y, 2:n)", 1, 2);
            play = value == 1;
            win = false;
        }
        System.out.println("You solved "+wins+" puzzles");
    }
    private static int getInt(String message, int origin, int bound){
        int retval = 0;
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                System.out.println(message);
                retval = scanner.nextInt();
                if (retval > bound|| retval < origin) {
                    System.out.println("Invalid Input, Try Again");
                }else break;
            } catch (Exception e){
                System.out.println("Invalid Input, Try Again");
                scanner.nextLine();
            }
        }
        return retval;
    }
}
