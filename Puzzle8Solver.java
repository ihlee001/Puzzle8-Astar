import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * Iain Lee CS 420
 * This the main class reads text files and transforms them into 2d arrays
 * and then uses the board class to find their solutions.  It also 
 * outputs 3 sample problems
 */
public class Puzzle8Solver {
	public static void main(String[] args) throws IOException{
		//Main output of 3 cases where solution depth is greater than 10
		normal_output();
		
/*		Uncomment below this if you want to test one 2d array sample
		Use this to input your own sample by configuring the test.txt file*/
		//
		//
		//one_sample_text_input();
		
/*		Uncomment below this this for mass file testing cases used from the sample inputs
		Currently test2.txt contains 100 cases of depth 20*/
		//
		//
		//file_test_cases();
	}
	
	//Reads a text file but just one sample only.
	//Mainly meant for user to provide one input to test out if it works
	public static void one_sample_text_input() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
		int[][] board = new int[3][3];
		try{
			String line = reader.readLine();
			int count = 0;
			while(line != null){
				String[] split_line = line.trim().split(" ");
				for(int i = 0; i < 3; i++){
					board[count][i] = Integer.parseInt(split_line[i]);
				}
				line = reader.readLine();
				count++;
			}
		} finally{reader.close();}
		Board game_board = new Board(board, 2);
		print_stats(game_board);
	}
	
	//Reads a text file where it converts strings into arrays
	//mainly used for mass test cases where each line is a new sample case
	//Used for the sample inputs provided
	public static void file_test_cases() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader("test2.txt"));
		try{
			String line = reader.readLine().trim();
			int mean = 0;
			long run_time = 0;
			long searches = 0;
			while(line != null){
				++mean;
				int[][] board = new int[3][3];
				int count = 0;
				for(int i = 0; i < 3; i++){
					for(int j = 0; j < 3; j++){
						board[i][j] = Integer.parseInt("" + line.charAt(count));
						count++;
					}
				}
				Board game_board = new Board(board, 2);
				run_time += game_board.totaltime;
				searches += game_board.search_cost;
				System.out.println(line + " Searches: " + game_board.search_cost + " Steps:"
						+ " " + game_board.get_steps() + " Total Time: " + game_board.totaltime);
				line = reader.readLine();
			}
			System.out.println("Average Run Time: " + run_time/mean);
			System.out.println("Average Search Cost: " + searches/mean);
			System.out.println("Number of Cases: " + mean);
		} finally{reader.close();}
	}
	
	//Program output for three sample solutions of greater than depth 10
	public static void normal_output(){
		int[][] board1 = {{5, 4, 1}, {6, 3, 2}, {0, 7, 8}};
		int[][] board2 = {{5, 4, 1}, {6, 0, 3}, {7, 8, 2}};
		int[][] board3 = {{5, 8, 0}, {3, 6, 2}, {1, 4, 7}};
		Board game_board1 = new Board(board1, 2);
		Board game_board2 = new Board(board2, 2);
		Board game_board3 = new Board(board3, 2);
		print_stats(game_board1);
		print_stats(game_board2);
		print_stats(game_board3);
	}
	
	//Prints out game board info
	public static void print_stats(Board game_board){
		System.out.println("==========");
		game_board.print_board_states();
		System.out.println("Searches: " + game_board.search_cost);
		
	}
	
	//Prints the board in a nice format
	public static void print_board(int[][] board){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
