package src;
import java.util.List;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final WinningStartegy winningStartegy;
    private int currentPlayerIndex;

    Game(Board board, List<Player> players, WinningStartegy winningStartegy){
        this.board = board;
        this.players = players;
        this.winningStartegy = winningStartegy;
        this.currentPlayerIndex = 0;
    }
    
    public void switchTurn(){
        currentPlayerIndex++;
        if(currentPlayerIndex == players.size()){
            currentPlayerIndex = 0;
        }
        return;
    }

    public void runTheGame(){

        while(!board.isBoardFull()){

            Player player = players.get(currentPlayerIndex);
            Cell cell = player.getMove(board);
            while(!board.isValidMove(cell)){
                cell = player.getMove(board);
            }
            board.fill(player.getMark(), cell);
            if(winningStartegy.isItWinningMove(board, cell)){
                System.out.println("Player " + player.getName()+ "is winner");
                return;
            }
            switchTurn();

            
        }
        System.out.println("Match turnned into draw");

        return;
    }
}