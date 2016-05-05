import java.util.LinkedList;

/*
 * Iain Lee CS 420
 * This class represents the game state of the game.  It will hold the board
 * and its current cost and also a reference to previous states
 */

public class GameState {
	LinkedList<int[][]> list;
	int[][] board;
	int cost;
	int g;
	
	
	public GameState(int[][] board, int g, int h, LinkedList<int[][]> list){
		this.board = board;
		this.g = g;
		this.cost = g + h;
		this.list = new LinkedList<int[][]>(); 
		this.list = (LinkedList)list.clone();
		this.list.add(board);
	}
}
