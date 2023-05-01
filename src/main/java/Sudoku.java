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
    private int difficulty;
    private Position[] locked;
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
        reset(difficulty);
    }
    public void setPosition(Position position, int newVal) throws InvalidArgumentException {
        if (newVal > 9) throw new InvalidArgumentException("The Entered Value Is Greater Than 9");
        if (newVal < 0) throw new InvalidArgumentException("The Entered Value Is Less Than 0");
        position.column--;
        position.row--;
        if (locked(position)) {
            throw new InvalidArgumentException("The Entered Position ("+(position.row +1)+","+(position.column +1)+") with current value "+board[position.row][position.column]+" Is \u001B[33mLocked\u001B[37m");
        }
        board[position.row][position.column] = newVal;
    }
    public void reset(int difficulty){
        this.difficulty = difficulty;
        locked = new Position[difficulty];
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
        for (int j = 0; j<3; j++){//randomize rows 0-2
            switchRows(board, j, random.nextInt(3));
        }
        for (int j = 3; j<6; j++){//randomize rows 3-5
            switchRows(board, j, random.nextInt(3)+3);
        }
        for (int j = 6; j<9; j++){//randomize rows 6-8
            switchRows(board, j, random.nextInt(3)+6);
        }
        for (int j = 0; j<3; j++){//randomize columns 0-2
            switchColumn(board, j, random.nextInt(3));
        }
        for (int j = 3; j<6; j++){//randomize columns 3-5
            switchColumn(board, j, random.nextInt(3)+3);
        }
        for (int j = 6; j<9; j++){//randomize columns 6-8
            switchColumn(board, j, random.nextInt(3)+6);
        }
        for (int j = 0; j<3; j++){//randomize block rows
            switchBlockRows(board, j, random.nextInt(3));
        }
        for (int j = 0; j<3; j++){//randomize block columns
            switchBlockColumns(board, j, random.nextInt(3));
        }
        //Now board is a randomized solved sudoku puzzle
        ArrayList<Position> locked = new ArrayList<Position>();
        for (int i = 0; i<difficulty; i++){//loops until the difficulty
            Position pos = new Position();
            int rand = random.nextInt(81);
            boolean exists = false;
            pos.row = rand%9;
            pos.column = rand/9;
            for(Position position:locked){
                if (position.column == pos.column && pos.row == position.row) {//adds positions to locked
                    exists = true;
                    break;
                }
            }
            if (!exists) locked.add(pos);//checks if the position already exists
            else i--;//if the position exists then it will loop an extra time
        }
        int i = 0;
        for (Object position: locked.toArray()){//copies it to the local array
            this.locked[i] = (Position) position;
            i++;
        }
        for (Position pos: this.locked){//sets the original values for the local board
            this.board[pos.row][pos.column] = board[pos.row][pos.column];
        }
    }
    private void switchSpots(int[][] board, Position pos1, Position pos2){
        int hold = board[pos1.row][pos1.column];
        board[pos1.row][pos1.column] = board[pos2.row][pos2.column];
        board[pos2.row][pos2.column] = hold;
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
    private void switchRows(int[][] board, int row1, int row2){
        int[] hold = board[row1];
        board[row1] = board[row2];
        board[row2] = hold;
    }
    private void switchColumn(int[][] board, int column1, int column2){
        Position pos1 = new Position(), pos2 = new Position();
        pos1.column = column1;
        pos2.column = column2;
        for (int i = 0; i<board.length; i++) {
            pos1.row = i;
            pos2.row = i;
            switchSpots(board, pos1, pos2);
        }
    }
    private void switchBlockRows(int[][] board, int column1, int column2){
        for (int i = 0; i<3; i++){
            switchRows(board, column1*3+i, column2*3+i);
        }
    }
    private void switchBlockColumns(int[][] board, int row1, int row2){
        for (int i = 0; i<3; i++){
            switchColumn(board, row1*3+i, row2*3+i);
        }
    }
    public boolean win() {
        boolean retval, columns = true, rows = true, blocks;
        for (int i = 0; i<9; i++){
            if (!correctColumn(i)){
                columns = false;
            }
        }
        for (int i = 0; i<9; i++){
            if (!correctRow(i)){
                rows = false;
            }
        }
        blocks = validBlocks();
        retval = columns&&rows&&blocks;
        return retval;
    }
    private boolean correctRow(int idx){
        boolean retval = true, exists = false;
        for (int num = 1; num <= 9; num++){
            exists = false;
            for (int i = 0; i < 9; i++){
                if (board[idx][i] == num){
                    exists = true;
                    break;
                }
            }
            if (!exists){
                retval = false;
                break;
            }
        }
        return retval;
    }
    private boolean correctColumn(int idx){
        boolean retval = true, exists = false;
        for (int num = 1; num <= 9; num++){
            exists = false;
            for (int i = 0; i < 9; i++){
                if (board[i][idx] == num){
                    exists = true;
                    break;
                }
            }
            if (!exists){
                retval = false;
                break;
            }
        }
        return retval;
    }
    private boolean validBlocks(){
        boolean retval = true, exists = false;
        for (int block = 0; block < 9; block++){
            for (int num = 1; num <= 9; num++) {
                exists = false;
                for (int y = block / 3 * 3; y < block / 3 * 3 + 3; y++) {
                    for (int x = block % 3 * 3; x < block % 3 * 3 + 3; x++) {
                        if (board[y][x] == num){
                            exists = true;
                            break;
                        }
                    }
                    if (exists) break;
                }
                if (!exists) {
                    retval = false;
                    break;
                }
            }
            if(!retval) break;
        }
        return retval;
    }
    public void print() {
        System.out.println("\u001B[36m    1   2   3    4   5   6    7   8   9\u001B[37m");
        System.out.println("  |---|---|---||---|---|---||---|---|---|");
        for (int i = 0; i < board.length; i++){
            System.out.print("\u001B[35m"+(i+1)+" ");
            System.out.print("\u001B[37m|");
            for (int j = 0; j < board[i].length; j++){
                Position position = new Position();
                position.row = i;
                position.column = j;
                if (j%3 == 2 && j != 8) {
                    if (board[i][j] == 0) {
                        System.out.print("   ||");
                    } else if (locked(position)) {
                        System.out.print(" ");
                        System.out.print("\u001B[33m" + board[i][j]);
                        System.out.print("\u001B[37m ||");
                    } else {
                        System.out.print(" ");
                        System.out.print("\u001B[32m" + board[i][j]);
                        System.out.print("\u001B[37m ||");
                    }
                } else {
                    if (board[i][j] == 0) {
                        System.out.print("   |");
                    } else if (locked(position)) {
                        System.out.print(" ");
                        System.out.print("\u001B[33m" + board[i][j]);
                        System.out.print("\u001B[37m |");
                    } else {
                        System.out.print(" ");
                        System.out.print("\u001B[32m" + board[i][j]);
                        System.out.print("\u001B[37m |");
                    }
                }
            }
            System.out.println();
            if (i == 2||i == 5){
                System.out.println("  |===|===|===||===|===|===||===|===|===|");
            } else System.out.println("  |---|---|---||---|---|---||---|---|---|");
        }
    }
    private boolean locked(Position position){
        boolean retval = false;
        for (Position pos : locked){
            if (position.column == pos.column && position.row == pos.row){
                retval = true;
                break;
            }
        }
        return retval;
    }
}
