import java.util.Comparator;

/*
 * Iain Lee CS 420
 * This class represents the gamestate comparator.  It allows for the priority
 * queue to correctly sort the gamestates by their costs.  
 */
public class GameStateComparator implements Comparator<GameState>{
	@Override
	public int compare(GameState x, GameState y){
		return x.cost - y.cost;
	}
}
