// written by Xiaozhen Huang & Zhouyang Gao
// X500: huan2121 & gao00224


import java.util.Scanner;
public class BattleBoatsGame {
    public static boolean isFire(String s) {
        boolean isInteger = false;
        try {
            Integer.parseInt(s.split(",")[0]);
            Integer.parseInt(s.split(",")[1]);
            // s is a valid integer
            isInteger = true;
        } catch (NumberFormatException ex) {// s is not an integer
        }
        return isInteger;
    }

    public static void main(String[] args) {
        BattleBoatsBoard board;
        Scanner myScanner = new Scanner(System.in);
        System.out.println(" ”Hello welcome to BattleBoats!" +
                " Please enter 1 to play in standard mode or 2 to " +
                "play in expert”.");
        String mode = myScanner.nextLine();
        while (true) {
            if ((mode.equals("1")) || (mode.equals("2"))) {
                break;
            }
            System.out.println(" Invalid input. Please enter 1 " +
                    "to play in standard mode or 2 to play in expert.");
            mode = myScanner.nextLine();
        }

        if (mode.equals("1")) {//standard
            board = new BattleBoatsBoard(8);
            System.out.println("You choose the standard model!\n"
                    + "There are 5 boats in 8 X 8 Board!\n"
                    + "For each power, you can use once!\n"
                    + "Gema Begins!");
        } else {//expert
            board = new BattleBoatsBoard(12);
            System.out.println("You choose the expert model!\n"
                    + "There are 10 boats in 12 X 12 Board!\n"
                    + "For each power, you can use twice!\n"
                    + "Gema Begins!");
        }
        System.out.println(board.display());


        // game start
        while (board.shipsRemain > 0) {
            System.out.println();
            System.out.println("Please enter a coordinate in the form of 'row,col', eg 2,3" +
                    ", or you can type 'drone' or 'missile' to use your power!");
            Scanner gameScanner = new Scanner(System.in);
            String move = gameScanner.nextLine();
            if (move.split(",").length > 1) {
                if (isFire(move)) {
                    int r = Integer.parseInt(move.split(",")[0]);
                    int c = Integer.parseInt(move.split(",")[1]);
                    if ((r < 0) || (c < 0) || (r > board.n - 1) || (r > board.n - 1)) {
                        System.out.println("Valid coordinates,Please enter a coordinate in the Board.");
                    } else {
                        board.fire(r, c);
                        System.out.println(board.display());
                    }
                } else {
                    System.out.println("Valid input!");
                }
            } else {
                switch (move) {
                    case "drone":
                    case "Drone":
                    case "DRONE": // use the power of drone
                        if (board.numDrone > 0) {
                            System.out.println("You use the Drone power!\n" +
                                    "Would you like to scan a row or column? Type in 0 for row or 1 for column.");
                            String direction = gameScanner.nextLine();
                            while (true) {
                                if ((direction.equals("0")) ||(direction.equals("0"))){
                                    break;
                                }
                                System.out.println("Invalid input. Please type in 0 for row or 1 for column.");
                                direction = gameScanner.nextLine();
                            }
                            System.out.println("Which row or column would you like to scan?");
                            String index = gameScanner.nextLine();
                            while (true) {
                                try {
                                    Integer.parseInt(index);
                                    // s is a valid integer
                                    if ((Integer.parseInt(index)>=0)&&((Integer.parseInt(index)< board.n))){
                                        break;
                                    }else{
                                        System.out.println("Invalid Input. Please type in a number within the boundaries of the board.");
                                    }
                                } catch (NumberFormatException ex) {// s is not an integer
                                    System.out.println("Invalid Input. Please type in a number within the boundaries of the board.");
                                    index = gameScanner.nextLine();
                                }
                            }
                            System.out.println(board.drone(Integer.parseInt(direction), Integer.parseInt(index)));
                            System.out.println(board.display());
                        } else {
                            System.out.println("Drone has been used the max amount of times.");
                        }
                        //drone finished
                        break;
                    case "missile":
                    case "Missile":
                    case "MISSILE": //use the power of missile
                        if (board.numMissile > 0) {
                            System.out.println("Where would you like to launch your missile? Please enter a coordinate");
                            String rc = gameScanner.nextLine();
                            while (true) {
                                if ((rc.split(",").length > 1) && (isFire(rc))) {
                                    int r = Integer.parseInt(rc.split(",")[0]);
                                    int c = Integer.parseInt(rc.split(",")[1]);
                                    if ((r > 0) || (c > 0) || (r < board.n) || (r < board.n)) {
                                        break;
                                    }
                                }
                                System.out.println("Valid coordinates,Please enter a coordinate in the Board.");
                                rc = gameScanner.nextLine();
                            }
                            int r = Integer.parseInt(rc.split(",")[0]);
                            int c = Integer.parseInt(rc.split(",")[1]);
                            board.missile(r, c);
                            System.out.println(board.display());
                        } else {
                            System.out.println("Missile has been used the max amount of times.");
                        }
                        break;
                    case "Print":
                    case "print":
                    case "PRINT":
                        System.out.println(board.print());
                        break;
                    default:
                        System.out.println("Valid input!");
                        break;
                }
            }
        }
        System.out.println("Game Over!");
        System.out.println("The total number of turns is "+ board.turns+",\n" +
                " but the total number of cannon shots is only "+board.shots+".");
    }
}// BattleBoatsGame.java
