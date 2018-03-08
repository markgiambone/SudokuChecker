import java.util.*;
import java.io.*;

/*
Mark Giambone
CPSC 380-01
This class takes input from a file and uses the verifier
It will loop until the user has tested all files
*/
public class SudokuInput{
	public static void main(String[] args){
		String fileName;
		boolean continued = true;
		String answer;
		String[] line;
		SudokuVerifier sv = new SudokuVerifier();
		int[][] input = new int[9][9];
		Scanner sc = new Scanner(System.in);		

		while(continued == true){
			System.out.println("Enter filename. ");
			fileName = sc.nextLine();

			try{//testing for input file errors
				FileReader fr = new FileReader(fileName);
				BufferedReader br = new BufferedReader(fr);		
				int row = 0;

				while((line = br.readLine().split(",")) != null){
					for(int i =0; i<9; ++i){
						input[row][i] = Integer.parseInt(line[i]);
					}
					++row;
				}
				br.close();
			}
			catch(Exception e){
				System.out.println("Error With File.");
			}

			System.out.println("Continue?(Y/N)");
			answer = sc.nextLine();
			if(answer == "N" || answer == "n"){
				continued = false;
			}
		}
	}
}