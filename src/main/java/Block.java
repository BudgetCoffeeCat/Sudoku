public class Block {
    private final int[][] positions = new int[3][3];
    private final int x, y, idx;
    public Block(int idx, int[][] board){
        this.idx = idx;
        x = idx/3;
        y = idx%3;
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                positions[i][j] = board[3*x+i][3*y+j];
            }
        }
    }
    public int getIdx() {
        return idx;
    }
    public int[][] getPositions() {
        return positions;
    }
}
