import java.util.Scanner;

public class App {

    static String[][] board = new String[9][9];

    static String[][] GoBoard2 = {
        {null,null,"-O-","-O-",null,null,null,null,"-@-"},
        {null,"-O-","-@-","-@-","-O-",null,null,null,"-O-"},
        {null,"-O-","-@-","-@-","-@-",null,null,"-@-","-O-"},
        {null,"-O-","-@-","-@-","-@-","-O-",null,null,"-@-"},
        {null,"-O-","-@-","-@-","-@-","-O-",null,null,null},
        {null,null,"-O-","-@-","-@-","-O-",null,"-@-",null},
        {null,null,null,"-O-","-O-",null,null,"-O-","-@-"},
        {null,null,null,null,null,null,"-@-","-O-","-@-"},
        {null,null,null,null,null,null,null,"-@-",null},
    };

    static String[][] GoBoard = {
        {null,"-O-","-O-",null,null,null,null,"-@-",null},
        {null,null,"-O-",null,null,null,null,"-@-",null},
        {null,"-O-","-O-",null,null,null,"-@-","-@-",null},
        {null,"-O-","-O-",null,null,null,"-O-","-@-",null},
        {null,null,"-O-",null,null,null,"-@-","-@-",null},
        {null,null,"-O-",null,null,null,null,"-@-",null},
        {null,null,"-O-",null,null,null,null,"-@-",null},
        {null,null,"-O-",null,null,null,null,"-@-",null},
        {null,"-O-",null,null,null,null,null,"-@-",null},
    };

    static boolean[][] territory = new boolean[9][9];
    static boolean[][] beenChecked = new boolean[9][9];
    static boolean bterritory = false;
    static boolean wterritory = false;
    static float bPoints = 0;
    static float wPoints = 0;

    static void printBoard(String[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                    if(board[i][j] == null){
                        System.out.print("-+-");
                    }
                    else{
                        System.out.print(board[i][j]);
                    }
                }
            System.out.println();
            }  
    }

    static void clearBoolean(boolean[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                    board[i][j] = false;
            } 
        }
    }

    static float countTerritory(boolean[][] board){
        float counter = 0;

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j]){
                    counter++;
                }
            } 
        }
        return counter;
    }

    static boolean checkAround(String[][] board, int x, int y, String stoneColor){
        //check in bounds/been checked already/has liberties/enemy stone
        if(x>8 || y>8 || x<0 || y<0){
            return false;
        }

        if(beenChecked[y][x]){
            return false;
        }

        if(board[y][x] == null){
            return true;
        }

        if(!board[y][x].equals(stoneColor)){
            return false;
        }

        beenChecked[y][x] = true;

        if(checkAround(board, x+1, y, stoneColor))
            return true;      
        if(checkAround(board, x-1, y, stoneColor))
            return true;        
        if(checkAround(board, x, y+1, stoneColor))
            return true;         
        if(checkAround(board, x, y-1, stoneColor))
            return true;

        return false;
    }

    static void captureStones(String[][] board, int x, int y, String stoneColor){
        float capturedStones = 0;
        //check board for all stones in a group, removes the stones
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(beenChecked[i][j]){
                    board[i][j] = null;
                    capturedStones++;
                }
            } 
        }
        //adds stones to respective scores
        if(stoneColor.equals("-@-")){
            bPoints += capturedStones;
        }
        else if(stoneColor.equals("-O-")){
            wPoints += capturedStones;
        }
    }

    static void checkTerritory(String[][] board, int x, int y){
        //checks if within board
        if(x < 0 || x >= board.length || y < 0 || y >= board[0].length){
            return;
        }
        //checks if stone has already been checked
        if(beenChecked[y][x]){
            return;
        }
        //cheacks if the spot is a stone or territory and which color
        if(board[y][x] != null){
            if(board[y][x].equals("-@-")){
                bterritory = true;
                return;
            }
            if(board[y][x].equals("-O-")){
                wterritory = true;
                return;
            }
        }
        //adds to checker and territory to count later
        beenChecked[y][x] = true;
        territory[y][x] = true;

        checkTerritory(board, x+1, y);
        checkTerritory(board, x-1, y);
        checkTerritory(board, x, y+1);
        checkTerritory(board, x, y-1);
    }

    static void claimTerritory(String[][] board){
        //safety check clear
        clearBoolean(beenChecked);
        //for whole board
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                //check dead space
                if(board[i][j] == null && !beenChecked[i][j]){
                    //safety territory reset
                    bterritory = false;
                    wterritory = false;
                    //checking territory
                    checkTerritory(board, i, j);
                    //if surrounded only by black
                    if(bterritory && !wterritory){
                        bPoints += countTerritory(territory);
                        clearBoolean(territory);
                    }
                    //if surrounded only by white
                    else if(wterritory && !bterritory){
                        wPoints += countTerritory(territory);
                        clearBoolean(territory);
                    }
                    //if not actrually captured territory
                    else{
                        clearBoolean(territory);
                    }
                }
            } 
        }
    }

    static boolean placeStone(String[][] board, int x, int y, String stoneColor){
        //checking bounds/empty space to put a stone
        if(x>8 || y>8 || x<0 || y<0){
            System.out.println("--This position is out of bounds. Please choose again.--");
            return false;
        }
        else if(board[y][x] != null){
            System.out.println("--The space is already occupied. Please choose again.--");
            return false;
        }
        else{
            if(stoneColor.equals("-@-")){
                board[y][x] = "-@-";
            }
            else if(stoneColor.equals("-O-")){
                board[y][x] = "-O-";
            }
        }
        //check if suicide
        clearBoolean(beenChecked);
        if(!checkAround(board, x, y, stoneColor)){
            board[y][x] = null;
            System.out.println("--Cannot suicide a stone. Please choose again.--");
            return false;
        }
        clearBoolean(beenChecked);
        //check around stone
        if(x+1 <= 8 && board[y][x+1] != null && board[y][x+1] != stoneColor){
            if(!checkAround(board, x+1, y, board[y][x+1])){
                captureStones(board, x+1, y, stoneColor);
            }
            clearBoolean(beenChecked);
        }
        if(x-1 >= 0 && board[y][x-1] != null && board[y][x-1] != stoneColor){
            if(!checkAround(board, x-1, y, board[y][x-1])){
                captureStones(board, x-1, y, stoneColor);
            }
            clearBoolean(beenChecked);
        }
        if(y+1 <= 8 && board[y+1][x] != null && board[y+1][x] != stoneColor){
            if(!checkAround(board, x, y+1, board[y+1][x])){
                captureStones(board, x, y+1, stoneColor);
            }
            clearBoolean(beenChecked);
        }
        if(y-1 >= 0 && board[y-1][x] != null && board[y-1][x] != stoneColor){
            if(!checkAround(board, x, y-1, board[y-1][x])){
                captureStones(board, x, y-1, stoneColor);    
            }
            clearBoolean(beenChecked);
        }
        return true;
    }
    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        boolean playing = true;
        boolean blackTurn = true;
        int turnNum = 1;
        String line = "-".repeat(50);

        while(playing){
            
            printBoard(GoBoard);
            System.out.println("Turn: "+ turnNum);
            System.out.println("Score: B- " + bPoints + ";W- " + wPoints);
            if(blackTurn){
                System.out.println("Black's Turn");
            }
            else{
                System.out.println("White's Turn");
            }

            System.out.println("Please Enter X Coord (-1 to End Game):");
            int x = scan.nextInt();

            if(x == -1){
                System.out.println("~Komi Rules~ Input which version: 'Japan' (6.5), 'Chinese' (7.5), 'None'");
                scan.nextLine();
                String komi = scan.nextLine();
                if(komi.equalsIgnoreCase("Japan")){
                    wPoints += 6.5;
                }
                else if(komi.equalsIgnoreCase("Chinese")){
                    wPoints += 7.5;
                }

                claimTerritory(GoBoard);

                System.out.println(line);
                printBoard(GoBoard);
                System.out.println("Final Score:");
                System.out.println("Black:"+ bPoints);
                System.out.println("White:"+ wPoints);
                break;
            }

            System.out.println("Please Enter Y Coord:");
            int y = scan.nextInt();

            if(blackTurn){
                if(placeStone(GoBoard, x, y, "-@-")){
                    turnNum++;
                    blackTurn = !blackTurn;
                }
            }
            else{
                if(placeStone(GoBoard, x, y, "-O-")){
                    turnNum++;
                    blackTurn = !blackTurn;
            }
            }
        }
        scan.close();
        
    }
}
