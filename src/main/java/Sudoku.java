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
    private final int difficulty;
    private final Position[] locked;
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
    public Sudoku(int difficulty){
        System.out.println("Creating Random Sudoku Board");
        this.difficulty = difficulty;
        locked = new Position[difficulty];
        reset();
    }
    public void setPosition(Position position, int newVal) throws InvalidArgumentException {
        if (newVal > 9) throw new InvalidArgumentException("The Entered Value Is Greater Than 9");
        if (newVal < 0) throw new InvalidArgumentException("The Entered Value Is Less Than 0");
        position.y --;
        position.x --;
        if (locked(position)) {
            throw new InvalidArgumentException("The Entered Position Is \u001B[33mLocked\u001B[37m");
        }
        board[position.y][position.x] = newVal;
    }
    public void reset(){
        int[][] board = solvedBoard.clone();
        this.board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        Random random = new Random(System.currentTimeMillis());
        for (int j = 1; j<10; j++){//randomize where each of the numbers are
            swapNumbers(board, j, random.nextInt(9)+1);
        }
        for (int j = 0; j<3; j++){//randomize columns 0-2
            switchColumns(board, j, random.nextInt(3));
        }
        for (int j = 3; j<6; j++){//randomize columns 3-5
            switchColumns(board, j, random.nextInt(3)+3);
        }
        for (int j = 6; j<9; j++){//randomize columns 6-8
            switchColumns(board, j, random.nextInt(3)+6);
        }
        for (int j = 0; j<3; j++){//randomize rows 0-2
            switchRows(board, j, random.nextInt(3));
        }
        for (int j = 3; j<6; j++){//randomize rows 3-5
            switchRows(board, j, random.nextInt(3)+3);
        }
        for (int j = 6; j<9; j++){//randomize rows 6-8
            switchRows(board, j, random.nextInt(3)+6);
        }
        for (int j = 0; j<3; j++){//randomize block rows
            switchBlockRows(board, j, random.nextInt(3));
        }
        for (int j = 0; j<3; j++){//randomize block columns
            switchBlockColumns(board, j, random.nextInt(3));
        }
        //Now board is a randomized solved sudoku puzzle
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
        int i = 0;
        for (Object position: locked.toArray()){
            this.locked[i] = (Position) position;
            i++;
        }
        for (Position pos: this.locked){
            this.board[pos.x][pos.y] = board[pos.x][pos.y];
        }
    }
    private void switchSpots(int[][] board, Position pos1, Position pos2){
        int hold = board[pos1.x][pos1.y];
        board[pos1.x][pos1.y] = board[pos2.x][pos2.y];
        board[pos2.x][pos2.y] = hold;
    }
    private void swapNumbers(int[][] board, int num1, int num2){
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
    private void switchColumns(int[][] board, int column1, int column2){
        int[] hold = board[column1];
        board[column1] = board[column2];
        board[column2] = hold;
    }
    private void switchRows(int[][] board, int row1, int row2){
        Position pos1 = new Position(), pos2 = new Position();
        pos1.y = row1;
        pos2.y = row2;
        for (int i = 0; i<board.length; i++) {
            pos1.x = i;
            pos2.x = i;
            switchSpots(board, pos1, pos2);
        }
    }
    private void switchBlockColumns(int[][] board, int column1, int column2){
        for (int i = 0; i<3; i++){
            switchColumns(board, column1*3+i, column2*3+i);
        }
    }
    private void switchBlockRows(int[][] board, int row1, int row2){
        for (int i = 0; i<3; i++){
            switchRows(board, row1*3+i, row2*3+i);
        }
    }
    public boolean win() {
        boolean retVal = true;
        int num;
        int[] occurences = new int[]{0,0,0,0,0,0,0,0,0};
        int i = 0;
        for (int[] row: board){
            i++;
            for (int val: row){
                if (val != 0){
                    occurences[val]++;
                }
            }
            for (int occ: occurences){
                if (occ!=i){
                    retVal = false;
                }
            }
        }
        for (i = 0; i<9; i++){
            occurences = new int[]{0,0,0,0,0,0,0,0,0};
            for(int[] row: board){
                occurences[row[i]]++;
            }
            for (int occ: occurences){
                if (occ!= 1){
                    retVal = false;
                }
            }
        }
        return retVal;
    }
    public void print() {
        System.out.println("\u001B[36m    1   2   3   4   5   6   7   8   9\u001B[37m");
        System.out.println("  |---|---|---|---|---|---|---|---|---|");
        for (int i = 0; i < board.length; i++){
            System.out.print("\u001B[35m"+(i+1)+" ");
            System.out.print("\u001B[37m|");
            for (int j = 0; j < board[i].length; j++){
                Position position = new Position();
                position.x = i;
                position.y = j;
                if (board[i][j] == 0){
                    System.out.print("   |");
                } else if (locked(position)){
                    System.out.print(" ");
                    System.out.print("\u001B[33m"+board[i][j]);
                    System.out.print("\u001B[37m |");
                } else {
                    System.out.print(" ");
                    System.out.print("\u001B[32m"+board[i][j]);
                    System.out.print("\u001B[37m |");
                }
            }
            System.out.println();
            System.out.println("  |---|---|---|---|---|---|---|---|---|");
        }
    }
    private boolean locked(Position position){
        boolean retval = false;
        for (Position pos : locked){
            if (position.y == pos.y && position.x == pos.x){
                retval = true;
                break;
            }
        }
        return retval;
    }
}
