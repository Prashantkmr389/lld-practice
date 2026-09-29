package src;

public class FullLineWinningStrategy implements WinningStartegy {

    @Override
    public boolean isItWinningMove(Board board, Cell lastCell) {
        int r = lastCell.Row();
        int c = lastCell.Col();
        Mark m = board.getCell(r, c);
        if (m == null) {
            return false;
        }

        int size = board.getSize();

        // 1. Check row r
        boolean rowWin = true;
        for (int j = 0; j < size; j++) {
            if (board.getCell(r, j) != m) {
                rowWin = false;
                break;
            }
        }
        if (rowWin) return true;

        // 2. Check column c
        boolean colWin = true;
        for (int i = 0; i < size; i++) {
            if (board.getCell(i, c) != m) {
                colWin = false;
                break;
            }
        }
        if (colWin) return true;

        // 3. Check main diagonal (top-left to bottom-right)
        if (r == c) {
            boolean diagWin = true;
            for (int i = 0; i < size; i++) {
                if (board.getCell(i, i) != m) {
                    diagWin = false;
                    break;
                }
            }
            if (diagWin) return true;
        }

        // 4. Check anti-diagonal (top-right to bottom-left)
        if (r + c == size - 1) {
            boolean antiDiagWin = true;
            for (int i = 0; i < size; i++) {
                if (board.getCell(i, size - 1 - i) != m) {
                    antiDiagWin = false;
                    break;
                }
            }
            if (antiDiagWin) return true;
        }

        return false;
    }
    
}
