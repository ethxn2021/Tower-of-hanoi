// -----------------------------------------------------------------------------
// Author: Sanjay Bhattacherjee
// Module: COMP5180
// 	Assignment 2 (2021-22)
// 	Task 1 (dummy program)
// Problem:
// Tower of Hanoi problem with any number (>=3) of towers
// -----------------------------------------------------------------------------

import java.io.*; // Import the I/O library

// change to public after
public class ew439_task1 {

	// ---------------------------------------------------------------------
	// Function: Empty Constructor
	// ---------------------------------------------------------------------
	public ew439_task1 ()
	{
	}

	// ---------------------------------------------------------------------
	// Function: Prints every move on screen and also writes it to a file
	// ---------------------------------------------------------------------
	public int print_move (int disc, int source_tower, int destination_tower, FileWriter writer)
	{
		try {
			System.out.printf("\nMove disk %d from T%d to T%d", disc, source_tower, destination_tower);
			writer.write("\n" + disc + "\t" +  source_tower + "\t" + destination_tower);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

	// ---------------------------------------------------------------------
	// Function: The recursive function for moving n discs 
	//           from s to d with t-2 buffer towers.
	//           It prints all the moves with disk numbers.
	// ---------------------------------------------------------------------
	public int move_t (int number_of_discs, int number_of_towers, int source_tower, int destination_tower, FileWriter writer)
	{
		// The following are hard-coded statements for an example instance (6,5,1,2)
		// Replace the following with your recursive definition of the move_t function

        if (number_of_towers > 3) {
            if (number_of_discs == 1)
            {
                print_move(number_of_discs, source_tower, destination_tower,writer);
            }
            else
            {   
                int helpTower = number_of_towers;
                while (helpTower == destination_tower || helpTower == source_tower) {
                    helpTower = helpTower - 1;
                }
    
                move_t(number_of_discs - 1, number_of_towers, source_tower, helpTower, writer);
                
                print_move(number_of_discs, source_tower, destination_tower,writer);
    
                move_t(number_of_discs - 1, number_of_towers, helpTower, destination_tower,writer);
    
            }
        } else if(number_of_towers <= 3) {
            if (number_of_discs == 1) {
                print_move(number_of_discs, source_tower, destination_tower,writer);
            } else {
                int helpTower = 6 - source_tower - destination_tower;
    
                move_t(number_of_discs - 1, number_of_towers, source_tower, helpTower,writer);
                print_move(number_of_discs, source_tower, destination_tower,writer);
                move_t(number_of_discs - 1, number_of_towers, helpTower, destination_tower,writer);
            }
        }
        return 0;

	}

	// ---------------------------------------------------------------------
	// Function: main
	// ---------------------------------------------------------------------
	public static void main(String[] args) {

		
        int n, t, s, d;
		String my_ID = new String("ky97");

		if (args.length < 4)
		{
			System.out.printf("Usage: java %s_task1 <n> <t> <s> <d>\n", my_ID);
			return;
		}

		n = Integer.parseInt(args[0]);  // Read user input n
		t = Integer.parseInt(args[1]);  // Read user input t
		s = Integer.parseInt(args[2]);  // Read user input s
		d = Integer.parseInt(args[3]);  // Read user input d

		// Check the inputs for sanity
		if (n<1 || t<3 || s<1 || s>t || d<1 || d>t)
		{
			System.out.printf("Please enter proper parameters. (n>=1; t>=3; 1<=s<=t; 1<=d<=t)\n");
			return;
		}

		// Create the output file name
		String filename;
		filename = new String(my_ID + "_ToH_n" + n + "_t" + t + "_s" + s + "_d" + d + ".txt");
		try {
			// Create the Writer object for writing to "filename"
			FileWriter writer;
			writer = new FileWriter(filename, false);

			// Write the first line: n, t, s, d
			writer.write(n + "\t" + t + "\t" + s + "\t" + d);

			// Create the object of this class to solve the generalised ToH problem
			ew439_task1 ToH = new ew439_task1();

			System.out.printf("\nThe output is also written to the file %s", filename);

			// Call the recursive function that solves the ToH problem
			ToH.move_t(n, t, s, d, writer);

			// Close the file
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.printf("\n");
		return;
	}
}