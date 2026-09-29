package src;

import java.util.Random;

public class BotStrategy implements MoveStrategy {
    private final Random random = new Random();

    @Override
    public Cell decideMove(Board board) {
        int size = board.getSize();
        int r = random.nextInt(size);
        int c = random.nextInt(size);
        return new Cell(r, c);
    }
    
}
