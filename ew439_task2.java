import java.util.*; // Import the util library
import java.io.*; // Import the I/O library

public class ew439_task2 {
    public static ArrayList<Stack<Integer>> towers = new ArrayList<Stack<Integer>>();
    public static ArrayList<String> towersNotUsed = new ArrayList<String>();
    public static int numberofTowers;
    public static int sourceTower;
    public static int numberofdisks;
    public static int destinationTower;
    public static boolean error = false;
    public static boolean towersEmpty = false;

    public static void main(String[] args) throws FileNotFoundException {        
        FileInputStream inputFile = new FileInputStream(args[0]);

        Scanner myReader = new Scanner(inputFile);
        File file = new File(args[0]);


        String variablesString = myReader.nextLine();
        String[] splitedVariablesString = variablesString.split("\\s+");
        int [] intvariables = new int[splitedVariablesString.length];

        for (int i = 0; i < splitedVariablesString.length; i ++) {
            intvariables[i] = Integer.parseInt(splitedVariablesString[i]);
        }

        numberofTowers = intvariables[1];
        sourceTower = intvariables[2];
        numberofdisks = intvariables[0];
        destinationTower = intvariables[3];
        
        int numberOfMoves = numberOfMoves(myReader);

        inititateStartState();
        checkMoves(numberOfMoves, file);
        myReader.close();
    }


    public static int numberOfMoves(Scanner file) {
        int count = 0;

        while(file.hasNextLine()) {
            if (file.nextLine() != null) {
                count +=1;
            }
        }

        return count;        
    }

    public static void inititateStartState() {
        for (int i = 1; i < numberofTowers + 1; i++) {
            towersNotUsed.add(Integer.toString(i));
        }
        towersNotUsed.remove(Integer.toString(sourceTower));

        Stack<Integer> startStack = new Stack<Integer>();
        for (int i = numberofdisks; i > 0; i--) {
            startStack.add(i);
        }
        





        for (int i = 0; i < numberofTowers; i++ ) {
            if (i == sourceTower - 1) {
                towers.add(startStack);
            } else {
                Stack<Integer> stack = new Stack<Integer>();
                towers.add(stack);  
            }
          
        }

        // int count = 0;
        // for (int i = numberofTowers - 1 ; i > 0; i--) {
        //     if (count == sourceTower - 1){
        //         towers.add(startStack);
        //     }
        //     Stack<Integer> stack = new Stack<Integer>();
        //     towers.add(stack);
        //     count +=1;
        // }
        System.out.println("");
        System.out.println("The status of all the towers at the start is as follows:");

        printMoves();
    }



    public static void printMoves() {
        for (int i = 0; i < numberofTowers; i++) {
            String towerstatement = Arrays.toString(towers.get(i).toArray());
            int towerNumber = i + 1; 

            System.out.println("Tower " + towerNumber + ":   " + towerstatement);
        }

    }


    public static void checkMoves(int numberOfMoves, File file) throws FileNotFoundException {

        for (int i = 1; i < numberOfMoves + 1; i++) {
            int[] moves = readNextMove(i, file);
        
            int discToMove = moves[0];

            int towerFromNumber = moves[1];
            int towerFromIndex = moves[1] - 1;
            //0
            int towerToMoveNumber = moves[2];
            int towerToMoveIndex = moves[2] - 1;
            //1


            System.out.println("");
            System.out.println("Move: disc " + discToMove + " from tower " + towerFromNumber + " to tower " + towerToMoveNumber);

            if (discToMove > numberofdisks) {
                System.out.println("Move Error: The disc_number: " + discToMove + " is out of range");
                System.out.println("");
                System.out.println("The status of all the towers at the end is as follows:");
                printMoves();
                System.out.println("");
                System.out.println("The sequence of moves is incorrect.");
                error = true;
                break;
            }
            
            Stack<Integer> currentTower = null;
            try {
                currentTower = towers.get(towerFromIndex);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Move Error: The source tower: " + towerFromNumber + " is out of range");
                System.out.println("");
                System.out.println("The status of all the towers at the end is as follows:");
                printMoves();
                System.out.println("");
                System.out.println("The sequence of moves is incorrect.");
                error = true;
                break;
            }


            Stack<Integer> towerToMove = null;
            try {
                towerToMove = towers.get(towerToMoveIndex);
            } catch(IndexOutOfBoundsException e) {
                System.out.println("Move Error: The destination tower: " + towerToMoveNumber + " is out of range");
                System.out.println("");
                System.out.println("The status of all the towers at the end is as follows:");
                printMoves();
                System.out.println("");
                System.out.println("The sequence of moves is incorrect.");
                error = true;
                break;
            }             

            if (currentTower.peek() == discToMove) {

                if (!towerToMove.empty() && towerToMove.peek() < discToMove) {
                    String towerstatement = Arrays.toString(towers.get(towerToMoveIndex).toArray());
                    System.out.println("Move Error: Destination tower: " + towerstatement + " has a smaller disc than " + discToMove + " on the top");
                    System.out.println("");
                    System.out.println("The status of all the towers at the end is as follows:");
                    printMoves();
                    System.out.println("");
                    System.out.println("The sequence of moves is incorrect.");
                    error = true;
                    break;                    
                }
                currentTower.pop();
                towerToMove.push(discToMove);
                try {
                    towersNotUsed.remove(Integer.toString(towerToMoveNumber));
                } catch (Exception ignore) {}
                
                printMoves();

            } else if (currentTower.peek() != discToMove) {
                String towerstatement = Arrays.toString(towers.get(towerFromIndex).toArray());
                System.out.println("Move Error: Source tower: " + towerstatement + " does not have the disc "  + discToMove + " on the top");
                System.out.println("");
                System.out.println("The status of all the towers at the end is as follows:");
                printMoves();
                System.out.println("");
                System.out.println("The sequence of moves is incorrect.");
                error = true;
                break;
            } 

        }


    
        if (error) {} else {
            checkOtherTowersEmpty();
            endState();
        }
        
    }


    public static void endState() {

        if (error) {
            if (towersNotUsed.size() == 0) {} else {    
            }   
        } else {
            if (towersNotUsed.size() == 0) {
                System.out.println("");
                System.out.println("The status of all the towers at the end is as follows:");
                printMoves();
                System.out.println("");
                System.out.println("The sequence of moves is correct.");
            } else {
                System.out.println("");
                System.out.println("The status of all the towers at the end is as follows:");
                printMoves();
                System.out.println("Error: " + " Tower " + towersNotUsed.get(0) + " has not been used!"); 
                System.out.println("");
                System.out.println("The sequence of moves is incorrect.");
                
            
            }               
        }
        
    }


    public static void checkOtherTowersEmpty() {
        boolean notEmpty = false;
        int towerNumber = 0;
        for (int i = 0; i < towers.size(); i++) {
            if (i != destinationTower - 1) {
                Stack<Integer> currentTower = towers.get(i);
                if (currentTower.size() > 0) {
                    notEmpty = true;
                    towerNumber = i + 1;
                }  
            }
        }
        if (notEmpty) {
            System.out.println("");
            System.out.println("Error: Incomplete solution. Tower " + towerNumber + " is not empty!");
            System.out.println("");
            System.out.println("The status of all the towers at the end is as follows:");
            printMoves();
            System.out.println("");
            System.out.println("The sequence of moves is incorrect.");
            error = true;
        } else {
            endState();
        }

    }


    public static int[] readNextMove(int lineCount, File myReader) throws FileNotFoundException {
        int discToMove = 0;
        int towerFrom = 0;
        int towerToMove= 0;

        int[] values = new int[3];

        int count = 0;
        Scanner myreader = new Scanner(myReader);
        while(true) {
            String currentLine = myreader.nextLine();
            if (currentLine != null) {
                if (count == lineCount) {
                    String[] currentVariables = currentLine.split("\\s+");
                    discToMove = Integer.parseInt(currentVariables[0]);
                    towerFrom = Integer.parseInt(currentVariables[1]);
                    towerToMove = Integer.parseInt(currentVariables[2]);
                    break;
                } else {
                    count += 1;
                }
            }
        }

        values[0] = discToMove;
        values[1] = towerFrom;
        values[2] = towerToMove;
        myreader.close();
        return values;

    }


}
