package src;


public class Board {
    private final int size;
    private final Mark[][] grid;

    Board(int size){
        this.size = size;
        this.grid = new Mark[size][size];
    }

    public boolean isValidMove(Cell cell){
        if(cell.Col() < 0 || cell.Col() >= size || cell.Row() < 0 || cell.Row() >= size){
            return false;
        }
        return grid[cell.Row()][cell.Col()] == null;
    }

    public void fill(Mark m, Cell cell){
        if(!isValidMove(cell)){
            throw new IllegalArgumentException("Cell already Filled");
        }
        grid[cell.Row()][cell.Col()] = m;
        return ;
    }


    public int getSize() {
        return size;
    }

    public Mark getCell(int row, int col) {
        return grid[row][col];
    }

    public boolean isBoardFull(){
        for (int index = 0; index < grid.length; index++) {
            for (int x = 0; x < grid[0].length; x++) {
                if(grid[index][x] == null) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(grid[i][j] == null ? "-" : grid[i][j]);
                if (j < size - 1) sb.append(" | ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
