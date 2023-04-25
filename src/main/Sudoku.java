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
    public boolean sudoku() {
        boolean retVal = true;
        int occurrences, num;
        // checking all rows
        for (int i = 0; i < board.length; i++) {//cycles through each row
            for (num = 1; num <= 9; num++) {//cycles through numbers 1 - 9
                occurrences = 0;
                for (int val:board[i]) {// goes through each value in the row
                    if (val == num){
                        occurrences++;// every time the number appears in the row, occurrences increments
                    }
                }
                if (occurrences!=1){// if there is not 1 occurrence of the number in the row, then it is not solved
                    retVal = false;
                }
            }
        }
        // checking all columns
        for (int i = 0; i < board[0].length; i++) {//cycles through each column
            for (num = 1; num <= 9; num++) {//cycles through numbers 1 - 9
                occurrences = 0;
                for (int j = 0; j <board.length; j++){// goes through each value in the column
                    if (board[j][i] == num){
                        occurrences++;// every time the number appears in the column, occurrences increments
                    }
                }
                if (occurrences!=1){// if there is not 1 occurrence of the number in the column, then it is not solved
                    retVal = false;
                }
            }
        }
        // checking squares
        for (int square = 1; square < 10; square++){
            int squareY, squareX;
            Position pos = new Position();
            squareY = square/3;//sets the y value for each square
            squareX = square%3;//sets the x value for each square
            for (num = 1; num <10; num++) {//checks if numbers 1 through 9 are in the square once
                occurrences = 0;
                for (pos.x = 3*squareX-3; pos.x<= 3*squareX; pos.x++){//pos is the position of the current spot being checked
                    for (pos.y = 3 * squareY - 3; pos.y <= 3 * squareY; pos.y++) {//cycles through the 3 y values in each square for each of the 3 x values
                        if (num == board[pos.x][pos.y]){//if the number is in the position
                            occurrences++;
                        }
                    }
                }
                if (occurrences!=1){
                    retVal = false;// if there is not one of any number in the square, then it is not solved
                }
            }
        }
        return retVal;
    }
    public boolean set(Position position, int value){
        boolean retVal = value<10 && value > 0;
        if(retVal) board[position.x-1][position.y-1] = value-1;
        return retVal;
    }
}
