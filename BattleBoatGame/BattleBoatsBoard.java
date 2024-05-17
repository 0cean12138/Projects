// written by Xiaozhen Huang & Zhouyang Gao
// X500: huan2121 & gao00224


public class BattleBoatsBoard{
    int shots;// keep track of total shots
    int turns;//keep track of total turns
    int shipsRemain;// track of total ships remaining
    Cell[][] board;
    BattleBoat[] boats;
    int n;// the number of r or c
    int numDrone;
    int numMissile;

    public BattleBoatsBoard(int n){
        this.n=n;
        board = new Cell[n][n];
        for(int i =0;i<n;i++){
            for(int j=0;j<n;j++){
                board[i][j] =new Cell(i,j,'-');
            }
        }
        this.turns = 0;
        this.shots = 0;
        if (n==8){
            this.numDrone=1;
            this.numMissile=1;
            this.shipsRemain = 5;
            boats =new BattleBoat[5];
        }else if (n == 12){
            this.numDrone=2;
            this.numMissile=2;
            this.shipsRemain = 10;
            boats =new BattleBoat[10];
        }
        this.placeBoats();
    }// constructor

    public void placeBoats(){
        int count= 0;
        while(count<this.shipsRemain) {
            if (this.n==8) {//mode standard
                if (count == 0) {//length 5
                    BattleBoat boat = new BattleBoat(8, 5);
                    if((boat.get_location()[4].row<this.n)&&(boat.get_location()[4].col<this.n)){
                        if ((board[boat.get_location()[0].row][boat.get_location()[0].col].get_status() == '-') &&
                            (board[boat.get_location()[1].row][boat.get_location()[1].col].get_status() == '-') &&
                            (board[boat.get_location()[2].row][boat.get_location()[2].col].get_status() == '-') &&
                            (board[boat.get_location()[3].row][boat.get_location()[3].col].get_status() == '-') &&
                            (board[boat.get_location()[4].row][boat.get_location()[4].col].get_status() == '-')) {
                            for (int i = 0; i < boat.get_size(); i++) {
                                board[boat.get_location()[i].row][boat.get_location()[i].col].set_status('B');
                                boats[count] = boat;
                            }
                            count++;
                        }
                    }
                } else if (count == 1) {//length 4
                    BattleBoat boat = new BattleBoat(8, 4);
                    if((boat.get_location()[3].row<this.n)&&(boat.get_location()[3].col<this.n)){
                    if ((board[boat.get_location()[0].row][boat.get_location()[0].col].get_status() == '-') &&
                            (board[boat.get_location()[1].row][boat.get_location()[1].col].get_status() == '-') &&
                            (board[boat.get_location()[2].row][boat.get_location()[2].col].get_status() == '-') &&
                            (board[boat.get_location()[3].row][boat.get_location()[3].col].get_status() == '-')) {
                        for (int i = 0; i < boat.get_size(); i++) {
                            board[boat.get_location()[i].row][boat.get_location()[i].col].set_status('B');
                            boats[count] = boat;
                        }
                        count++;
                    }}
                } else if ((count == 2) || (count == 3)) {//length 3
                    BattleBoat boat = new BattleBoat(8, 3);
                    if((boat.get_location()[2].row<this.n)&&(boat.get_location()[2].col<this.n)){
                    if ((board[boat.get_location()[0].row][boat.get_location()[0].col].get_status() == '-') &&
                            (board[boat.get_location()[1].row][boat.get_location()[1].col].get_status() == '-') &&
                            (board[boat.get_location()[2].row][boat.get_location()[2].col].get_status() == '-')) {
                        for (int i = 0; i < boat.get_size(); i++) {
                            board[boat.get_location()[i].row][boat.get_location()[i].col].set_status('B');
                            boats[count] = boat;
                        }
                        count++;
                    }}
                } else if (count == 4) {//length 2
                    BattleBoat boat = new BattleBoat(8, 2);
                    if((boat.get_location()[1].row<this.n)&&(boat.get_location()[1].col<this.n)){
                    if ((board[boat.get_location()[0].row][boat.get_location()[0].col].get_status() == '-') &&
                            (board[boat.get_location()[1].row][boat.get_location()[1].col].get_status() == '-')) {
                        for (int i = 0; i < boat.get_size(); i++) {
                            board[boat.get_location()[i].row][boat.get_location()[i].col].set_status('B');
                            boats[count] = boat;
                        }
                        count++;
                    }}
                }
            } else {
                if ((count == 0) || (count == 1)) {//length 5
                    BattleBoat boat = new BattleBoat(12, 5);
                    if((boat.get_location()[4].row<this.n)&&(boat.get_location()[4].col<this.n)){
                    if ((board[boat.get_location()[0].row][boat.get_location()[0].col].get_status() == '-') &&
                            (board[boat.get_location()[1].row][boat.get_location()[1].col].get_status() == '-') &&
                            (board[boat.get_location()[2].row][boat.get_location()[2].col].get_status() == '-') &&
                            (board[boat.get_location()[3].row][boat.get_location()[3].col].get_status() == '-') &&
                            (board[boat.get_location()[4].row][boat.get_location()[4].col].get_status() == '-')) {
                        for (int i = 0; i < boat.get_size(); i++) {
                            board[boat.get_location()[i].row][boat.get_location()[i].col].set_status('B');
                            boats[count] = boat;
                        }
                        count++;
                    }}
                } else if ((count == 2) || (count == 3)) {//length 4
                    BattleBoat boat = new BattleBoat(12, 4);
                    if((boat.get_location()[3].row<this.n)&&(boat.get_location()[3].col<this.n)){
                    if ((board[boat.get_location()[0].row][boat.get_location()[0].col].get_status() == '-') &&
                            (board[boat.get_location()[1].row][boat.get_location()[1].col].get_status() == '-') &&
                            (board[boat.get_location()[2].row][boat.get_location()[2].col].get_status() == '-') &&
                            (board[boat.get_location()[3].row][boat.get_location()[3].col].get_status() == '-')) {
                        for (int i = 0; i < boat.get_size(); i++) {
                            board[boat.get_location()[i].row][boat.get_location()[i].col].set_status('B');
                            boats[count] = boat;
                        }
                        count++;
                    }}
                } else if ((count == 4) || (count == 5) || (count == 6) || (count == 7)) {//length 3
                    BattleBoat boat = new BattleBoat(12, 3);
                    if((boat.get_location()[2].row<this.n)&&(boat.get_location()[2].col<this.n)){
                    if ((board[boat.get_location()[0].row][boat.get_location()[0].col].get_status() == '-') &&
                            (board[boat.get_location()[1].row][boat.get_location()[1].col].get_status() == '-') &&
                            (board[boat.get_location()[2].row][boat.get_location()[2].col].get_status() == '-')) {
                        for (int i = 0; i < boat.get_size(); i++) {
                            board[boat.get_location()[i].row][boat.get_location()[i].col].set_status('B');
                            boats[count] = boat;
                        }
                        count++;
                    }}
                } else if ((count == 8) || (count == 9)) {//length 2
                    BattleBoat boat = new BattleBoat(12, 2);
                    if((boat.get_location()[1].row<this.n)&&(boat.get_location()[1].col<this.n)){
                    if ((board[boat.get_location()[0].row][boat.get_location()[0].col].get_status() == '-') &&
                            (board[boat.get_location()[1].row][boat.get_location()[1].col].get_status() == '-')) {
                        for (int i = 0; i < boat.get_size(); i++) {
                            board[boat.get_location()[i].row][boat.get_location()[i].col].set_status('B');
                            boats[count] = boat;
                        }
                        count++;
                    }
                    }
                }
            }
        }
    }

public void fire(int r,int c){
        if((r<0)||(c<0)||(r>(this.n-1))||(c>(this.n-1))){
            System.out.println("Out of bounds. Penalty.");
            this.turns+=1;
        }else if(this.board[r][c].get_status()=='-'){
            this.board[r][c].set_status('M');
            System.out.println("Miss");
            this.turns+=1;
            this.shots+=1;
        }else if((this.board[r][c].get_status()=='H')||(this.board[r][c].get_status()=='M')){
            System.out.println("Attack Over Again. Penalty.");
            this.turns+=1;
        }else if(this.board[r][c].get_status()=='B'){
            this.board[r][c].set_status('H');

            int k=0;// find the boat in boats and
            for(int i=0;i<this.boats.length;i++){
                for (int j=0;j<this.boats[i].get_size();j++) {
                    if ((this.boats[i].get_location()[j].row==r)&&(this.boats[i].get_location()[j].col==c)) {
                        k = i;
                        this.boats[i].get_location()[j].set_status('H');
                    }
                }
            }
            if(this.boats[k].sunk()){
                System.out.println("Sunk!");
                shipsRemain-=1;
            }else{
                System.out.println("Hit!");
            }
            this.turns+=1;
            this.shots+=1;
            }
        }

public String display(){// print out the player board state every turn
        String b = "";
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if((this.board[i][j].get_status()=='-')||(this.board[i][j].get_status()=='B')){
                    b+="- ";
                }else if(this.board[i][j].get_status()=='M'){
                    b+="O ";
                }else if(this.board[i][j].get_status()=='H'){
                    b+="X ";
                }
            }
            b+="\n";
        }
        return b;
    }

public String print(){
        // the fully revealed board if a player types in the print
        //command (This would be used for debugging purposes)
        String[][] s=new String[n][n];
        for(int i =0;i<boats.length;i++){
            for(int j=0;j<boats[i].get_size();j++){
                s[boats[i].get_location()[j].row][boats[i].get_location()[j].col] =Integer.toString(i+1);
            }
        }
        for(int i =0;i<n;i++){
            for(int j=0;j<n;j++){
                if (this.board[i][j].get_status()=='-') {
                    s[i][j] = "-";
                }else if(this.board[i][j].get_status()=='H'){
                    s[i][j] = "X";
                }else if(this.board[i][j].get_status()=='M'){
                    s[i][j] = "O";
                }
            }
        }
        String p="";
        for(int i =0;i<n;i++){
            for(int j=0;j<n;j++){
                p=p + s[i][j] + " ";
            }
            p+="\n";
        }
        return p;
    }

public void missile(int r, int c){
        System.out.println("Missile has Launched!");
        for(int i=r-1;i<r+2;i++){
            for(int j=c-1;j<c+2;j++){
                if((this.board[i][j].get_status()=='-')||(this.board[i][j].get_status()=='M')){
                    this.board[i][j].set_status('M');
                }else if((this.board[i][j].get_status()=='B')||(this.board[i][j].get_status()=='H')) {
                    this.board[i][j].set_status('H');

                    int k=0;// find the boat in boats and
                    for(int n=0;n<this.boats.length;n++){
                        for (int m=0;m<this.boats[n].get_size();m++) {
                            if ((this.boats[n].get_location()[m].row==i)&&(this.boats[n].get_location()[m].col==j)) {
                                k = n;
                                this.boats[n].get_location()[m].set_status('H');
                            }
                        }
                    }
                    if(this.boats[k].sunk()){
                        System.out.println("A Boat is Sunk!");
                        shipsRemain-=1;
                    }
                }
            }
        }
        this.turns+=1;
        this.numMissile-=1;
    }

public String drone(int direction, int index){
        // direction is o <-> row, 1 <-> col
        int num = 0;
        if (direction == 0) {
            for (int i = 0; i < this.n; i++) {
                if ((this.board[index][i].get_status() == 'B') || (this.board[index][i].get_status() == 'H')) {
                    num += 1;
                }
            }
        } else if (direction == 1) {
            for (int i = 0; i < this.n; i++) {
                if ((this.board[i][index].get_status() == 'B') || (this.board[i][index].get_status() == 'H')) {
                    num += 1;
                }
            }
        }
        this.numDrone-=1;
        if (num == 0) {
            return "There is no other Boats left in the specified area.";
        } else {
            return " Drone has scanned " + num + " targets in the specified area."; }
    }

    /*
    public static void main(String[] args){
    }

 */

}// BattleBoatsBoard.java