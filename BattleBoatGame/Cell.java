// written by Xiaozhen Huang & Zhouyang Gao
// X500: huan2121 & gao00224


public class Cell{
    public int row;// Indicates the row value of the cell
    public int col;//Indicates the column value of the cell
    public char status; //Character indicating the status of the Cell.
    // There are three different
    // "-" : Has not been guessed, no boat present
    // 'B' : Has not been guessed, boat present
    // 'H': Has been guessed, boat present
    // 'M' : Has been guessed, no boat present

    public char get_status(){
        return status;
    }

    public void set_status(char c){
        this.status = c;
    }

    public Cell(int row, int col, char status){
        this.row = row;
        this.col = col;
        this.status = status;
    }// Cell class constructor

    public String toString(){
        return "("+row+","+col+")";
    }

    // main method for test
    /*
    public static void main(String[] args){
        Cell cell1 = new Cell(1,2,'H');
        System.out.println(cell1);
        System.out.println(cell1.get_status());
        System.out.println(cell1);
        cell1.set_status('-');
        System.out.println(cell1.get_status());
        System.out.println(cell1);
    }
    */
}