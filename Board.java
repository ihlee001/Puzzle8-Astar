import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/*
 * Iain Lee CS 420
 * This class represents the board class and will solve the 8 puzzle
 * board problem once it is created.  It also holds the game stats
 * so that it is easily printable.  Heuristic is chosen by 1 or 2.
 */
public class Board {
	private int[][] goal_board;
	private PriorityQueue<GameState> frontier;
	private HashSet<String> map;
	GameState final_gamestate;
	long totaltime;
	int heuristic;
	int search_cost = 0;

/***********************************Constructor*****************************************/	
	public Board(int[][] board, int h){
		heuristic = h;
		this.goal_board = new int[][] {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
		this.frontier = new PriorityQueue<GameState>(11, new GameStateComparator());
		this.map = new HashSet<String>();
		GameState game;
		if(heuristic == 1){//Which heuristic you want to use: 1 or 2
			game = new GameState(board, 0, getheuristic1(board), new LinkedList<int[][]>());
		}
		else{
			game = new GameState(board, 0, getheuristic2(board), new LinkedList<int[][]>());
		}
		if(is_solvable(board)){//Checks to see if the game is solvable
			long startTime = System.nanoTime();
			solve(game);//if solvable please solve
			long endTime = System.nanoTime();
			totaltime = endTime - startTime;
		}
		else System.out.println("This is not solvable");
	}

/**********************************Solver Methods***************************************/
	//Recursive method that will recurse till the goal state is found
	private void solve(GameState game){
		++search_cost;
		this.map.add(board_to_string(game.board));//add to map to show we've visited
		if(is_solved(game.board)) final_gamestate = game;//when solved end
		else{
			find_frontiers(game);//Find the frontiers
			GameState next_game = this.frontier.poll();//Grab next frontier node
			solve(next_game);//recurse
		}
	}
	
	private void find_frontiers(GameState game){
		int[] zero = find_zero(game.board);
		if(zero[1] + 1 < 3){//check right
			add_frontier(zero[0], zero[1] + 1, game, zero);
		}
		if(zero[1] - 1 >= 0){//check left
			add_frontier(zero[0], zero[1] - 1, game, zero);
		}
		if(zero[0] + 1 < 3){//check above
			add_frontier(zero[0] + 1, zero[1], game, zero);
		}
		if(zero[0] - 1 >= 0){//check below
			add_frontier(zero[0] - 1, zero[1], game, zero);
		}
	}
	
	//Finds the current location of where the moving tile is
	private int[] find_zero(int[][] board){
		int[] zero = new int[2];
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(board[i][j] == 0){ 
					zero[0] = i;
					zero[1] = j;
					break;
				}
			}
		}
		return zero;
	}
	
	//Adds to the frontier queue
	private void add_frontier(int x, int y, GameState game, int[] zero){
		int[][] copy_board = new int[3][3];
		copy_array(game.board, copy_board);
		copy_board[zero[0]][zero[1]] = game.board[x][y];
		copy_board[x][y] = 0;
		String string_copy_board = board_to_string(copy_board);
		if(!map.contains(string_copy_board)){
			if(heuristic == 1) this.frontier.add(new GameState(copy_board, game.g + 1, getheuristic1(copy_board), game.list));
			else this.frontier.add(new GameState(copy_board, game.g + 1, getheuristic2(copy_board), game.list));
		}
	}

/*******************************Heuristic Methods***********************************/	
	private int getheuristic1(int[][] board){
		int missed_tiles = 0;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(board[i][j] != goal_board[i][j]){ 
					missed_tiles++;
				}
			}
		}
		return missed_tiles;
	}
	
	private int getheuristic2(int[][] board){
		int distance_sum = 0;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				int num = board[i][j];
				distance_sum += Math.abs(num/3 - i) + Math.abs(num%3 - j);
			}
		}
		return distance_sum;
	}

/**********************************Utility Methods**************************************/
	//Checks to see if the game is sovlable
	private boolean is_solvable(int[][] board){
		int counter = 0;
		boolean first_time = true;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				int tile = board[i][j];
				first_time = true;
				for(int k = i; k < board.length; k++){
					for(int l = 0; l < board[k].length; l++){
						if(first_time){
							l = j;
							first_time = false;
						}
						if(tile > board[k][l]) counter++;
					}
				}
			}
		}
		return counter % 2 == 0;
	}
	
	//Converts 2d array to string
	private String board_to_string(int[][] game_board){
		String game = "";
		for(int i = 0; i < game_board.length; i++){
			for(int j = 0; j < game_board[i].length; j++){
				game += game_board[i][j];
			}
		}
		return game;
	}
	
	//Checks to see if the board is solved
	private boolean is_solved(int[][] board){
		return Arrays.deepEquals(goal_board, board);
	}
	
	//Creates a clone of the board so that changing one board wont' affect others
	private void copy_array(int[][] board, int[][] copy){
		for(int i=0; i< board.length; i++){
			for(int j=0; j< board[i].length; j++){
				  copy[i][j] = board[i][j];
			}
		}
	}
	
/*************************************Getters*******************************************/
	//Returns the goal board
	public int[][] get_goal(){
		return goal_board;
	}
	
	public int get_steps(){
		return final_gamestate.g;
	}

/*************************************Printers******************************************/
	//Prints the current game state
	public void print_board(int[][] board){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	//Prints each step of the solution
	public void print_board_states(){
		int i = 0;
		for(int[][] s: final_gamestate.list){
			System.out.println("State: " + i);
			print_board(s);
			System.out.println();
			i++;
		}
	}
}
