import java.util.Scanner;

public class App {

    static String[][] board = new String[9][9];

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
        

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean playing = true;
        boolean black = true;

        while(playing){
            
            printBoard(board);

            System.out.println("Please Enter X Coord:");
            int x = scan.nextInt();
            System.out.println("Please Enter Y Coord:");
            int y = scan.nextInt();

            if(x>8 || y>8 || x<0 || y<0){
                System.out.println("This position is out of bounds. Please choose again");
            }
            else if(board[y][x] != null){
                System.out.println("The space is already occupied. Please choose again.");
            }
            else{
                if(black){
                    board[y][x] = "-@-";
                }
                else{
                    board[y][x] = "-O-";
                }
            black = !black;
            }
        }
        scan.close();
        
    }
}
