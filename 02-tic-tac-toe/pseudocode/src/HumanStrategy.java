package src;

import java.util.Scanner;

/**
 * WinningStartegy
 */
public class HumanStrategy implements MoveStrategy{
    private final Scanner scanner;

    HumanStrategy(){
        scanner = new Scanner(System.in);
    }

    @Override
    public Cell decideMove(Board board) {
        System.out.println("Current board");
        System.out.print(board);
        System.out.println("Give row index");
        int r = scanner.nextInt();
        System.out.println("Give col index");
        int c = scanner.nextInt();
        Cell newMove = new Cell(r, c);
        return newMove;
    }

}
