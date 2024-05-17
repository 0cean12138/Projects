// written by Xiaozhen Huang & Zhouyang Gao
// X500: huan2121 & gao00224

public class BattleBoat {
    public int r;// track the r
    public int c;//track the c
    public int size;// indicate the length of the boat
    public boolean orientation; // false <-> horizontal, true <-> vertical
    public Cell[] location;

    public BattleBoat() {}// default constructor

    public BattleBoat(int n, int length) {
        // n is the length of n X n square matrix.
        // length is the size of the boat.
        this.orientation = (int)Math.floor(Math.random() * 2) == 1;
        this.size = length;
        this.location = new Cell[this.size];

        if (orientation) {
            r = (int) Math.floor(Math.random() * (n));//x
            c = (int) Math.floor(Math.random() * (n - length));//y
            for (int i = 0; i < length; i++) {
                this.location[i] = new Cell(r++, c, 'B');
            }
        }else {
            r = (int) Math.floor(Math.random() * (n - length));//x
            c = (int) Math.floor(Math.random() * (n));//y
            for (int i = 0; i < length; i++) {
                this.location[i] = new Cell(r, c++, 'B');
            }
        }
    }

    public int get_size() {
        return size;
    }

    public Cell[] get_location() {
        return location;
    }

    public boolean sunk(){//True for Sunk; False for not sunk\
        for(int i=0;i<this.get_size();i++){
            if(this.location[i].get_status()=='B'){
                return false;
            }
        }
        return true;
    }

    public String toString(){
        String s = "{";
        for (int i=0;i<this.get_size()-1;i++){
            s+=location[i]+",";
        }
        s+=location[this.get_size()-1] + "}";
        return s;
    }
/*
    public static void main(String[] args){
        BattleBoat Boat1= new BattleBoat(8,3);
        System.out.println(Boat1.get_size());
        System.out.println(Boat1.get_location()[1].row);
        System.out.println(Boat1);
        for(int i=0;i<Boat1.get_size();i++){
            Boat1.location[i].set_status('H');
        }
        System.out.println(Boat1.sunk());
    }
 */
    public static void main(String[] args){}

} // BattleBoat.java
