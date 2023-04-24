/*  Sudoku.java
 *  24 April 2023
 *  Deepesh Kothari
 *  Purpose:
 *      Creates a class that stores the information for a sudoku board
 */
package src.main;

public class Sudoku {
    private int[][] board;
    public Sudoku(){
        reset();
    }
    public void reset(){
        int[][] board = new int[9][9];
        // add code for a random board
        this.board = board;
    }
    public boolean win(){
        boolean retVal = false;
        // add code to check if the position is winning
        return retVal;
    }
    public boolean set(Position position, int value){
        boolean retVal = value<10 && value > 0;
        if(retVal) board[position.x-1][position.y-1] = value-1;
        return retVal;
    }

    public static void print() {
        System.out.println("___________________");
        System.out.println("| | | | | | | | | |");
        System.out.println("___________________");
        System.out.println("| | | | | | | | | |");
        System.out.println("___________________");
        System.out.println("| | | | | | | | | |");
        System.out.println("___________________");
        System.out.println("| | | | | | | | | |");
        System.out.println("___________________");
        System.out.println("| | | | | | | | | |");
        System.out.println("___________________");
        System.out.println("| | | | | | | | | |");
        System.out.println("___________________");
        System.out.println("| | | | | | | | | |");
        System.out.println("___________________");
        System.out.println("| | | | | | | | | |");
        System.out.println("___________________");
        System.out.println("| | | | | | | | | |");
        System.out.println("___________________");
    }
}
