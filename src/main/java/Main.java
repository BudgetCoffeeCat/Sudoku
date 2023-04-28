import java.util.Scanner;

/*  Main.java
 *  25 April 2023
 *  Deepesh Kothari
 *  Purpose:
 *      Creates a class that runs a game of sudoku
 */
public class Main {
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku(getInt("Set Your difficulty\n(the number of squares that start filled out)(integer from 1 to 81)", 1, 81));
        Scanner scanner = new Scanner(System.in);
        int value;
        System.out.println("Done making board.");
        sudoku.print();
        Position position = new Position();
        position.y = getInt("Enter the row #(integer from 1-9)", 1, 9);
        position.x = getInt("Enter the column #(integer from 1-9)", 1, 9);
        value = getInt("Enter the value (integer from 0-9, 0 to represent blank)", 0, 9);
        try {
            sudoku.setPosition(position, value);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        sudoku.print();
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
            }
        }
        return retval;
    }
}
