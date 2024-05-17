//Written by Xiaozhen Huang and Zhouyang Gao
// huan2121 and gao00224


public class MyMaze{
    Cell[][] maze;

    public MyMaze(int rows, int cols) {
        maze = new Cell[rows][cols];
        for(int i = 0;  i < rows; i++){
            for(int j = 0; j < cols; j++){
                maze[i][j] = new Cell();
                if (i == rows - 1 && j == cols -1){  //check if bottom right where exit goes
                    maze[i][j].setRight(false);
                }
            }
        }
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(int rows, int cols) {
        MyMaze nm = new MyMaze(rows, cols);
        Stack1Gen m = new Stack1Gen();
        int[] t = new int[2];
        int[] t1 = new int[2];
        t[0] = 0;
        t[1] = 0;
        t1[0] = t[0];
        t1[1] = t[1];
        m.push(t1);
        nm.maze[0][0].setVisited(true); // set at top left
        while(!m.isEmpty()) {
            t = (int[])m.top();
            boolean visited = true;

            //check whether it out of bounds, whether it has been visited or not
            if(t[0]-1 >= 0){
                if(!nm.maze[t[0]-1][t[1]].getVisited()){
                    visited = false;
                }
            }
            if(t[1]-1 >= 0){
                if(!nm.maze[t[0]][t[1]-1].getVisited()){
                    visited = false;
                }
            }
            if(t[0]+1 <= nm.maze.length -1){
                if(!nm.maze[t[0]+1][t[1]].getVisited()){
                    visited = false;
                }
            }
            if(t[1]+1 <= nm.maze.length -1){
                if(!nm.maze[t[0]][t[1]+1].getVisited()){
                    visited = false;
                }
            }

            boolean k = false;
            if(visited){
                m.pop();
                k = true;
            }

            //inter loop
            while(visited == false){
                if( k ){
                    break;
                }
                int newTemp = (int) (Math.random() * 4);
                if (newTemp == 0) {
                    if ((t[0] - 1) < 0 || nm.maze[t[0] - 1][t[1]].getVisited())//checks out of bounds and that it hasn't been visited before
                        newTemp = (int) (Math.random() * 4);//if it has been visited goes to a new spot around the current one.
                    else {
                        nm.maze[t[0] - 1][t[1]].setBottom(false);//open a hole above the current cell
                        nm.maze[t[0] - 1][t[1]].setVisited(true);
                        t[0]--;
                        t1 = new int[2];
                        t1[0] = t[0];
                        t1[1] = t[1];
                        m.push(t1);//initialized new arrays so they aren't changed down the line when temp changes
                        visited = true;
                    }
                } else if (newTemp == 1) {
                    if ((t[0] + 1) > nm.maze.length - 1 || nm.maze[t[0] + 1][t[1]].getVisited()){
                        newTemp = (int) (Math.random() * 4);}
                    else {
                        nm.maze[t[0]][t[1]].setBottom(false);//opens a hole that belows current cell
                        nm.maze[t[0] + 1][t[1]].setVisited(true);
                        t[0]++;
                        t1 = new int[2];
                        t1[0] = t[0];
                        t1[1] = t[1];
                        m.push(t1);
                        visited = true;
                    }
                } else if (newTemp == 2) {
                    if ((t[1] - 1) < 0 || nm.maze[t[0]][t[1] - 1].getVisited()) {
                        newTemp = (int) (Math.random() * 4);
                    }
                    else {
                        nm.maze[t[0]][t[1] - 1].setRight(false);
                        nm.maze[t[0]][t[1] - 1].setVisited(true);
                        t[1]--;
                        t1 = new int[2];
                        t1[0] = t[0];
                        t1[1] = t[1];
                        m.push(t1);
                        visited = true;
                    }
                } else {
                    if ((t[1] + 1) > nm.maze[0].length - 1 || nm.maze[t[0]][t[1] + 1].getVisited()){
                        newTemp = (int) (Math.random() * 4);}
                    else {
                        nm.maze[t[0]][t[1]].setRight(false);
                        nm.maze[t[0]][t[1] + 1].setVisited(true);
                        t[1]++;
                        t1 = new int[2];
                        t1[0] = t[0];
                        t1[1] = t[1];
                        m.push(t1);
                        visited = true;
                    }
                }
            }
        }
        for (int i = 0; i < nm.maze.length; i++)
            for (int j = 0; j < nm.maze[0].length; j++)
                nm.maze[i][j].setVisited(false);
        return nm;
    }


    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze(boolean path) {
        for (int i = 0; i < (maze.length * 2) + 1; i++) {
            String row = "";
            if (i == 1)
                row += " ";
            else
                row += "|";
            for (int j = 0; j < maze[0].length; j++) {
                if (i == 0 || i == maze.length * 2) {
                    row += "---|";
                } else {
                    if (i % 2 == 1) {
                        if (path && maze[i / 2][j].getVisited()) {
                            row += " * ";
                        } else {
                            row += "   ";
                        }
                        if (maze[i / 2][j].getRight())
                            row += "|";
                        else
                            row += " ";
                    } else {
                        if (maze[i / 2 - 1][j].getBottom())
                            row += "---|";
                        else
                            row += "   |";
                    }
                }
            }
            System.out.println(row);
        }
    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() {
        Q1Gen solve = new Q1Gen();
        int[] cur = new int[2];
        int[] temp = new int[2];
        cur[0] = 0;
        cur[1] = 0;
        temp[0] = cur[0];
        temp[1] = cur[1];
        solve.add(temp);
        int rowLen,colLen;

        rowLen= maze.length;
        colLen= maze[0].length;

        while (! solve.isEmpty()) {//if queue is not empty
            cur = (int[]) solve.remove();//current takes from front of the queue
            maze[cur[0]][cur[1]].setVisited(true);//mark current cell as visited
            if (cur[0] == maze.length - 1 && cur[1] == maze[0].length - 1) {
                break;
            } else {
                if ((cur[0] - 1 >= 0)&&(cur[0]-1<rowLen)) {//makes sure not out of bounds
                    if (!(maze[cur[0] - 1][cur[1]].getVisited() || maze[cur[0] - 1][cur[1]].getBottom())) {
                        temp = new int[2];
                        temp[0] = cur[0] - 1;
                        temp[1] = cur[1];
                        solve.add(temp);//adds into temp array so coordinates don't get missed with when current is changed
                    }
                }
                if ((cur[0] + 1 <rowLen)&&(cur[0]+1>=0)) {
                    if (!(maze[cur[0] + 1][cur[1]].getVisited() || maze[cur[0]][cur[1]].getBottom())) {
                        temp = new int[2];
                        temp[0] = cur[0] + 1;
                        temp[1] = cur[1];
                        solve.add(temp);
                    }
                }
                if ((cur[1] - 1 >= 0)&&(cur[1]-1<colLen)) {
                    if (!(maze[cur[0]][cur[1] - 1].getVisited() || maze[cur[0]][cur[1] - 1].getRight())) {
                        temp = new int[2];
                        temp[0] = cur[0];
                        temp[1] = cur[1] - 1;
                        solve.add(temp);
                    }
                }
                if ((cur[1] + 1 <colLen)&&(cur[1]+1>=0)) {
                    if (!(maze[cur[0]][cur[1] + 1].getVisited() || maze[cur[0]][cur[1]].getRight())) {
                        temp = new int[2];
                        temp[0] = cur[0];
                        temp[1] = cur[1] + 1;
                        solve.add(temp);
                    }
                }
            }
        }
        printMaze(true);

    }

    public static void main(String[] args){
        //Any testing can be put in this main function
        MyMaze test = new MyMaze(5, 5);
        test = test.makeMaze(5, 20);
        test.printMaze(false);
        test.solveMaze();
    }
}
