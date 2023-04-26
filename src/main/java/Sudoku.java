/*  Sudoku.java
 *  24 April 2023
 *  Deepesh Kothari
 *  Purpose:
 *      Creates a class that stores the information for a sudoku board
 */

import java.util.ArrayList;
import java.util.Random;

public class Sudoku {
    private int[][] board;
    private int difficulty = 15;
    Position[] locked;
    private final int[][] solvedBoard = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 1, 5, 6, 4, 8, 9, 7},
            {5, 6, 4, 8, 9, 7, 2, 3, 1},
            {8, 9, 7, 2, 3, 1, 5, 6, 4},
            {3, 1, 2, 6, 4, 5, 9, 7, 8},
            {6, 4, 5, 9, 7, 8, 3, 1, 2},
            {9, 7, 8, 3, 1, 2, 6, 4, 5}
    };
    public Sudoku(){
        reset();
    }
    public void setDifficulty(int difficulty) {
        if (difficulty > 10 && difficulty < 81) {
            this.difficulty = difficulty;
        }
    }

    public void reset(){
        int[][] board = solvedBoard.clone();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i<3; i++){//shuffle the board 3 times
            for (int j = 1; j<10; i++){//switch where each of the numbers are
                swapNumbers(j, random.nextInt(9)+1);
            }
            for (int j = 0; j<3; j++){//randomize columns 0-2
                switchColumns(j, random.nextInt(3));
            }
            for (int j = 3; j<6; j++){//randomize columns 3-5
                switchColumns(j, random.nextInt(3)+3);
            }
            for (int j = 6; j<9; j++){//randomize columns 6-8
                switchColumns(j, random.nextInt(3)+6);
            }
            for (int j = 0; j<3; j++){//randomize rows 0-2
                switchRows(j, random.nextInt(3));
            }
            for (int j = 3; j<6; j++){//randomize rows 3-5
                switchRows(j, random.nextInt(3)+3);
            }
            for (int j = 6; j<9; j++){//randomize rows 6-8
                switchRows(j, random.nextInt(3)+6);
            }
            for (int j = 0; j<3; j++){//randomize block rows
                switchBlockRows(j, random.nextInt(3));
            }
            for (int j = 0; j<3; j++){//randomize block columns
                switchBlockColumns(j, random.nextInt(3));
            }
        }//Now board is a randomized solved sudoku puzzle
        ArrayList<Position> locked = new ArrayList<Position>();
        for (int i = 0; i<difficulty; i++){
            Position pos = new Position();
            int rand = random.nextInt(81);
            boolean exists = false;
            pos.x = rand%9;
            pos.y = rand/9;
            for(Position position:locked){
                if (position.y == pos.y && pos.x == position.x) {
                    exists = true;
                    break;
                }
            }
            if (!exists) locked.add(pos);
            else i--;
        }
        this.locked = (Position[]) locked.toArray();
        this.board = new int[9][9];
        for (Position pos: this.locked){
            this.board[pos.x][pos.y] = board[pos.x][pos.y];
        }
    }
    private void switchSpots(Position pos1, Position pos2){
        int hold = board[pos1.x][pos1.y];
        board[pos1.x][pos1.y] = board[pos2.x][pos2.y];
        board[pos2.x][pos2.y] = hold;
    }
    private void swapNumbers(int num1, int num2){
        for(int[] row: board){
            for (int i = 0; i<row.length; i++){
                if (row[i] == num1){
                    row[i] = num2;
                } else if (row[i] == num2){
                    row[i] = num1;
                }
            }
        }
    }
    private void switchColumns(int column1, int column2){
        int[] hold = board[column1];
        board[column1] = board[column2];
        board[column2] = hold;
    }
    private void switchRows(int row1, int row2){
        Position pos1 = new Position(), pos2 = new Position();
        pos1.y = row1;
        pos2.y = row2;
        for (int i = 0; i<board.length; i++) {
            pos1.x = i;
            pos2.x = i;
            switchSpots(pos1, pos2);
        }
    }
    private void switchBlockColumns(int column1, int column2){
        for (int i = 0; i<=3; i++){
            switchColumns(column1*3+i, column2*3+i);
        }
    }
    private void switchBlockRows(int row1, int row2){
        for (int i = 0; i<=3; i++){
            switchRows(row1*3+i, row2*3+i);
        }
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
        for (int block = 0; block < 9; block++){
            int blockY, blockX;
            Position pos = new Position();
            blockY = block/3;//sets the y value for each block
            blockX = block%3;//sets the x value for each block
            for (num = 1; num <10; num++) {//checks if numbers 1 through 9 are in the block once
                occurrences = 0;
                for (pos.x = 3*blockX-3; pos.x<= 3*blockX; pos.x++){//pos is the position of the current spot being checked
                    for (pos.y = 3 * blockY - 3; pos.y <= 3 * blockY; pos.y++) {//cycles through the 3 y values in each block for each of the 3 x values
                        if (num == board[pos.x][pos.y]){//if the number is in the position
                            occurrences++;
                        }
                    }
                }
                if (occurrences!=1){
                    retVal = false;// if there is not one of any number in the block, then it is not solved
                }
            }
        }
        return retVal;
    }
    public static void print(list) {
        for (int i = 0; i < reset().board[][].length; i++){
            System.out.println("___________________");
            System.out.println("|");
            for (int j = 0; j < reset().board[][].length; j++){
                if (list[i][j] == 0){
                    System.out.println(" |");
                } else{
                    System.out.println(list[i][j]+"|");
                }
            }
        }
        System.out.println("___________________");
    }
    public boolean set(Position position, int value){
        boolean retVal = value<10 && value > 0;
        if(retVal) board[position.x-1][position.y-1] = value-1;
        return retVal;
    }
}
