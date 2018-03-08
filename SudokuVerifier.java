import java.util.*;
import java.io.*;

/*
Mark Giambone
CPSC 380-01
This class goes through the columns, rows, and sub grids of the sudoku board and validate it
*/
public class SudokuVerifier
{
	public int[] arr = {1,2,3,4,5,6,7,8,9};
	public int[] validRow = new int[9];
	public int[] validCol = new int[9];
	public int[] validSubGrid = new int[9];

	//verify rows 
	public void rowChecker(int[][] sudo)
	{
		
		for(int row = 0; row < 9; ++row)
		{
		   int[] temp = new int[9];
		   for(int col = 0; col < 8; ++col)
		   {
			   temp[col] = sudo[row][col];
		   }
		   Arrays.sort(temp);

		   int count = 0;
		   for(int i = 0; i<9; ++i)
		   {
			   if(arr[i] == temp[i])
				   ++count;
		   }

		   if(count == 9)
		   {
			   validRow[row] = 1;
		   }
		   else
		   	validRow[row] = 0;
		}

		int rowCheck = 0;

		for(int i = 0; i<9; ++i){
			if(validRow[i] == 1){
				++rowCheck;

				if(rowCheck == 9){
					System.out.println("No row errors.");
				}
			}
			else
				System.out.println("Row" + (i+1) + " has an error.");
		}
	}

	//validate columns
	public void colChecker(int[][] sudo)
	{
		for(int col = 0; col < 9; ++col)
		{
		   int[] temp = new int[9];
		   for(int row = 0; row < 8; ++row)
		   {
			   temp[row] = sudo[row][col];
		   }
		   Arrays.sort(temp);

		   int count = 0;
		   for(int i = 0; i<9; ++i)
		   {
			   if(arr[i] == temp[i]){
				   ++count;
			   }
		   }

		   if(count == 9)
		   {
			   validCol[col] = 1;
		   }
		   else
		   	validCol[col] = 0;
		}

		int colCheck = 0;
		for(int i = 0; i<9; ++i){
			if(validCol[i] == 1){
				++colCheck;

				if(colCheck == 9){
					System.out.println("No column errors.");
				}
			}
			else
				System.out.println("Column" + (i+1) + " has an error.");
		}
	}

	//validate 3x3 sub grids
	public void subGridChecker(int[][] sudo)
	{
		int curr = 0;
		for(int row = 0; row < 9; row += 3)
		{
		   for(int col = 0; col < 9; col += 3)
		   {
			  int[] temp = new int[9];
			  
			  //assign the 3x3 sub grids
			  temp[0] = sudo[row][col];
			  temp[1] = sudo[row][col+1];
			  temp[2] = sudo[row][col+2];
			  temp[3] = sudo[row+1][col];
			  temp[4] = sudo[row+1][col+1];
			  temp[5] = sudo[row+1][col+2];
			  temp[6] = sudo[row+2][col];
			  temp[7] = sudo[row+2][col+1];
			  temp[8] = sudo[row+2][col+2];

			  Arrays.sort(temp);
			  int count = 0;

			  for(int i = 0; i < 9; ++i)
			  {
				 if(arr[i] == temp[i])
				 	++count;
			  }
			  if(count == 9)
			  	validSubGrid[curr] = 1;
			  else
			  	validSubGrid[curr] = 0;
			  ++curr;
			}

		}
		int subGridCheck = 0;

		for(int i =0; i<0; ++i)
		{
			if(validSubGrid[i] == 1)
			{
				++subGridCheck;
				if(subGridCheck == 9)
					System.out.println("No sub grid errors.");
				else
					System.out.println("Sub grid" + (i+1) + " has an error.");
			}
		}

	}

	public static void main(String[] args)
	{
		String fileName;
		boolean continued = true;
		String answer;
		String[] line;
		SudokuVerifier sv = new SudokuVerifier();
		int[][] input = new int[9][9];		

		while(continued == true){
			try{//testing for input file errors
				System.out.println("Enter filename. ");
				Scanner sc = new Scanner(System.in);
				fileName = sc.nextLine();
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
				System.out.println("Continue?(Y/N)");
				answer = sc.nextLine();
				if(answer.equals("N") || answer.equals("n")){
					continued = false;
					break;
				}
			}
			catch(Exception e){
				System.out.println("Error With File.");
			}

			System.out.println("Sudoku puzzle given: ");
			for(int row = 0; row<9; ++row)
			{
				for(int col = 0; col<9; ++col)
				{
					if(col != 2 && col !=5)
					{
						System.out.print(input[row][col] + " ");
					}
					else
						System.out.print(input[row][col] + " ");
				}

				if(row !=2 && row != 5)
					System.out.print("\n");
				else
				{
					System.out.print("\n\n");
				}
			}
			System.out.println();

			boolean fixed = false;
			int cycle = 1;

			while(!fixed)
			{
				new Thread(() -> sv.rowChecker(input)).start();
				new Thread(() -> sv.colChecker(input)).start();
				new Thread(() -> sv.subGridChecker(input)).start();

				System.out.println("Cycle" + cycle);
				cycle++;
				int wrongRowNumbers = 0;
				int wrongColNumbers = 0;

				for(int i = 0; i<9;++i)
				{
					if (sv.validRow[i] == 0)
						wrongRowNumbers++;
					if(sv.validCol[i] == 0)
						wrongColNumbers++;
				}

				if(wrongRowNumbers == 0 && wrongColNumbers == 0)
				{
					fixed = true;
				}
				else
				{
					int[] target = {1,2,3,4,5,6,7,8,9};
					int currRow = 0;
					int currCol = 0;
					int missingNum = 0;

					for(int i=0; i<9; ++i)
					{
						if(sv.validRow[i] == 0)
							currRow = i;
						if(sv.validCol[i] == 0)
							currCol = i;
					}

					int[] tempRow = new int[9];
					for(int col = 0; col<9; ++col)
					{
						tempRow[col] = input[currRow][col];
					}

					Arrays.sort(tempRow);

					for(int i =0; i<9; ++i)
					{
						if(target[i] != tempRow[i])
							missingNum = i+1;
					}

					System.out.println("Replacing " + input[currRow][currCol]+ " with " + missingNum + " at row:" + (currRow+1) + " and column:" + (currCol+1));
					input[currRow][currCol] = missingNum;
				}
			}
		}

		System.out.println("Correct Sudoku Puzzle: ");
		for(int row = 0; row<9; ++row)
		{
			for(int col = 0; col<9; ++col)
			{
				if(col != 2 && col !=5)
				{
					System.out.println(input[row][col] + " ");
				}
				else
					System.out.println(input[row][col] + " ");
			}

			if(row !=2 && row != 5)
				System.out.println();
			else
			{
				System.out.println();
				System.out.println();
			}
		}
	}
}	