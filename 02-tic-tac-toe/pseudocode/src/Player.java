package src;

public class Player {
    private final String name;
    private final Mark mark;
    private final MoveStrategy moveStrategy;
    public Player(String name, Mark m, MoveStrategy moveStrategy){
        this.mark = m;
        this.name = name;
        this.moveStrategy = moveStrategy;
    }

    public String getName(){
        return name;
    }
    public Mark getMark(){
        return mark;
    }

    public Cell getMove(Board currentBoard){
        return moveStrategy.decideMove(currentBoard);
    }



}
