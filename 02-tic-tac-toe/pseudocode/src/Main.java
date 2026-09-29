package src;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String []args){
        System.out.println("Game Starts");
        HumanStrategy humanStrategy = new HumanStrategy();
        BotStrategy botStrategy = new BotStrategy();
        Player p1 = new Player("Prashant", Mark.O, humanStrategy);
        Player p2 = new Player("Bot", Mark.X, botStrategy);
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Board board = new Board(3);
        WinningStartegy winningStartegy = new FullLineWinningStrategy();
        Game newGame = new  Game(board, players, winningStartegy);
        newGame.runTheGame();
    }
}
