import java.util.Scanner;

public class App {

    static String[][] cube = {
        {null,null,null,"r","r","r",null,null,null},
        {null,null,null,"r","r","r",null,null,null},
        {null,null,null,"r","r","r",null,null,null},
        {"y","y","y","b","b","b","w","w","w"},
        {"y","y","y","b","b","b","w","w","w"},
        {"y","y","y","b","b","b","w","w","w"},
        {null,null,null,"o","o","o",null,null,null},
        {null,null,null,"o","o","o",null,null,null},
        {null,null,null,"o","o","o",null,null,null},
        {null,null,null,"g","g","g",null,null,null},
        {null,null,null,"g","g","g",null,null,null},
        {null,null,null,"g","g","g",null,null,null},
    };
    //printing functions
    static void printCube(String[][] cube){
        for(int i = 0; i < cube.length; i++){
            for(int j = 0; j < cube[i].length; j++){
                if(cube[i][j] == null){
                    System.out.print(" ");
                }
                else
                    System.out.print(cube[i][j]);
            }
        System.out.println();
        }
    }

    static void printFace(String[][] cube, int x, int y){
        int originalX = x;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                if(j%2 == 0){
                    System.out.print(cube[y][x]);
                    x++;
                }
                else 
                    System.out.print("|");
            }
            x = originalX;
            y++;
            System.out.println();
        }
    }
    
    static void outputCube(String[][] cube){
        //red
        printFace(cube, 3, 0);
        System.out.println();
        //yellow
        printFace(cube, 0, 3);
        System.out.println();
        //blue
        printFace(cube, 3, 3);
        System.out.println();
        //white
        printFace(cube, 6, 3);
        System.out.println();
        //orange
        printFace(cube, 3, 6);
        System.out.println();
        //green
        printFace(cube, 3, 9);
    }

    //movement functions
    static void up(String[][] cube){
        //turn
        String temp1 = cube[3][0];
        String temp2 = cube[3][1];
        String temp3 = cube[3][2];

        cube[3][0] = cube[3][3];
        cube[3][1] = cube[3][4];
        cube[3][2] = cube[3][5];

        cube[3][3] = cube[3][6];
        cube[3][4] = cube[3][7];
        cube[3][5] = cube[3][8];

        cube[3][6] = cube[11][5];
        cube[3][7] = cube[11][4];
        cube[3][8] = cube[11][3];

        cube[11][5] = temp1;
        cube[11][4] = temp2;
        cube[11][3] = temp3;
        //red-face
        String ftempe = cube[0][3];
        cube[0][3] = cube[2][3];
        cube[2][3] = cube[2][5];
        cube[2][5] = cube[0][5];
        cube[0][5] = ftempe;

        String ftempo = cube[0][4];
        cube[0][4] = cube[1][3];
        cube[1][3] = cube[2][4];
        cube[2][4] = cube[1][5];
        cube[1][5] = ftempo;
    }

    static void down(String[][] cube){
        //turn
        String temp1 = cube[5][6];
        String temp2 = cube[5][7];
        String temp3 = cube[5][8];

        cube[5][6] = cube[5][3];
        cube[5][7] = cube[5][4];
        cube[5][8] = cube[5][5];

        cube[5][3] = cube[5][0];
        cube[5][4] = cube[5][1];
        cube[5][5] = cube[5][2];

        cube[5][0] = cube[9][5];
        cube[5][1] = cube[9][4];
        cube[5][2] = cube[9][3];

        cube[9][5] = temp1;
        cube[9][4] = temp2;
        cube[9][3] = temp3;
        //orange-face
        String ftempe = cube[6][3];
        cube[6][3] = cube[8][3];
        cube[8][3] = cube[8][5];
        cube[8][5] = cube[6][5];
        cube[6][5] = ftempe;

        String ftempo = cube[6][4];
        cube[6][4] = cube[7][3];
        cube[7][3] = cube[8][4];
        cube[8][4] = cube[7][5];
        cube[7][5] = ftempo;
    }

    static void left(String[][] cube){
        //turn
        int y = 11;
        int originalY = 11;
        for(int i = 0; i < 3; i++){
            String temp = cube[11][3];
            for(int j = 0; j < 11; j++){
                cube[y][3] = cube[y-1][3];
                y--;
            }
            y = originalY;
            cube[0][3] = temp;
        }
        //yellow-face
        String ftempe = cube[3][0];
        cube[3][0] = cube[5][0];
        cube[5][0] = cube[5][2];
        cube[5][2] = cube[3][2];
        cube[3][2] = ftempe;

        String ftempo = cube[3][1];
        cube[3][1] = cube[4][0];
        cube[4][0] = cube[5][1];
        cube[5][1] = cube[4][2];
        cube[4][2] = ftempo;
    }

    static void right(String[][] cube){
        //turn
        int y = 0;
        int originalY = 0;
        for(int i = 0; i < 3; i++){
            String temp = cube[0][5];
            for(int j = 0; j < 11; j++){
                cube[y][5] = cube[y+1][5];
                y++;
            }
            y = originalY;
            cube[11][5] = temp;
        }
        //white-face
        String ftempe = cube[3][6];
        cube[3][6] = cube[5][6];
        cube[5][6] = cube[5][8];
        cube[5][8] = cube[3][8];
        cube[3][8] = ftempe;

        String ftempo = cube[3][7];
        cube[3][7] = cube[4][6];
        cube[4][6] = cube[5][7];
        cube[5][7] = cube[4][8];
        cube[4][8] = ftempo;
    }

    static void front(String[][] cube){
        //turn
        String temp1 = cube[2][3];
        String temp2 = cube[2][4];
        String temp3 = cube[2][5];

        cube[2][3] = cube[5][2];
        cube[2][4] = cube[4][2];
        cube[2][5] = cube[3][2];

        cube[5][2] = cube[6][5];
        cube[4][2] = cube[6][4];
        cube[3][2] = cube[6][3];

        cube[6][5] = cube[3][6];
        cube[6][4] = cube[4][6];
        cube[6][3] = cube[5][6];

        cube[3][6] = temp1;
        cube[4][6] = temp2;
        cube[5][6] = temp3;

        //blue-face
        String ftempe = cube[3][3];
        cube[3][3] = cube[5][3];
        cube[5][3] = cube[5][5];
        cube[5][5] = cube[3][5];
        cube[3][5] = ftempe;

        String ftempo = cube[3][4];
        cube[3][4] = cube[4][3];
        cube[4][3] = cube[5][4];
        cube[5][4] = cube[4][5];
        cube[4][5] = ftempo;
    }

    static void back(String[][] cube){
        //turn
        String temp1 = cube[0][3];
        String temp2 = cube[0][4];
        String temp3 = cube[0][5];

        cube[0][3] = cube[3][8];
        cube[0][4] = cube[4][8];
        cube[0][5] = cube[5][8];

        cube[3][8] = cube[8][5];
        cube[4][8] = cube[8][4];
        cube[5][8] = cube[8][3];

        cube[8][5] = cube[5][0];
        cube[8][4] = cube[4][0];
        cube[8][3] = cube[3][0];

        cube[5][0] = temp1;
        cube[4][0] = temp2;
        cube[3][0] = temp3;

        //green-face
        String ftempe = cube[9][3];
        cube[9][3] = cube[11][3];
        cube[11][3] = cube[11][5];
        cube[11][5] = cube[9][5];
        cube[9][5] = ftempe;

        String ftempo = cube[9][4];
        cube[9][4] = cube[10][3];
        cube[10][3] = cube[11][4];
        cube[11][4] = cube[10][5];
        cube[10][5] = ftempo;
    }

    //testing functions
    static void final_output_only_mode(String[][] cube){
        Scanner scan = new Scanner(System.in);
        boolean playing = true;
        String solution = "Solution:";

        System.out.println("Type 'quit' to exit.");

        while(playing){
            System.out.println("Please Enter Move: (u,d,l,r,f,b) or (u',d',l',r',f',b')");
            String move = scan.nextLine();

            switch(move){
                case "quit":
                    System.out.println(solution);
                    playing = !playing;
                    break;
                case "u":
                    up(cube);
                    solution = solution + " u'";
                    break;
                case "f":
                    front(cube);
                    solution = solution + " f'";
                    break;
                case "r":
                    right(cube);
                    solution = solution + " r'";
                    break;
                case "l":
                    left(cube);
                    solution = solution + " l'";
                    break;
                case "d":
                    down(cube);
                    solution = solution + " d'";
                    break;
                case "b":
                    back(cube);
                    solution = solution + " b'";
                    break;
                case "u'":
                    up(cube);
                    up(cube);
                    up(cube);
                    solution = solution + " u";
                    break;
                case "f'":
                    front(cube);
                    front(cube);
                    front(cube);
                    solution = solution + " f";
                    break;
                case "r'":
                    right(cube);
                    right(cube);
                    right(cube);
                    solution = solution + " r";
                    break;
                case "l'":
                    left(cube);
                    left(cube);
                    left(cube);
                    solution = solution + " l";
                    break;
                case "d'":
                    down(cube);
                    down(cube);
                    down(cube);
                    solution = solution + " d";
                    break;
                case "b'":
                    back(cube);
                    back(cube);
                    back(cube);
                    solution = solution + " b";
                    break;
                default:
                    System.out.println("Not a real Move. Try Again."); 
            }
        }
        scan.close();
        outputCube(cube);
        //printCube(cube);
    }

    static void print_every_loop_mode(String[][] cube){
        Scanner scan = new Scanner(System.in);
        boolean playing = true;
        String solution = "Solution:";

        System.out.println("Type 'quit' to exit.");

        while(playing){
            //printCube(cube);
            outputCube(cube);

            System.out.println("Please Enter Move: (u,d,l,r,f,b) or (u',d',l',r',f',b')");
            String move = scan.nextLine();

            switch(move){
                case "quit":
                    System.out.println(solution);
                    playing = !playing;
                    break;
                case "u":
                    up(cube);
                    solution = solution + " u'";
                    break;
                case "f":
                    front(cube);
                    solution = solution + " f'";
                    break;
                case "r":
                    right(cube);
                    solution = solution + " r'";
                    break;
                case "l":
                    left(cube);
                    solution = solution + " l'";
                    break;
                case "d":
                    down(cube);
                    solution = solution + " d'";
                    break;
                case "b":
                    back(cube);
                    solution = solution + " b'";
                    break;
                case "u'":
                    up(cube);
                    up(cube);
                    up(cube);
                    solution = solution + " u";
                    break;
                case "f'":
                    front(cube);
                    front(cube);
                    front(cube);
                    solution = solution + " f";
                    break;
                case "r'":
                    right(cube);
                    right(cube);
                    right(cube);
                    solution = solution + " r";
                    break;
                case "l'":
                    left(cube);
                    left(cube);
                    left(cube);
                    solution = solution + " l";
                    break;
                case "d'":
                    down(cube);
                    down(cube);
                    down(cube);
                    solution = solution + " d";
                    break;
                case "b'":
                    back(cube);
                    back(cube);
                    back(cube);
                    solution = solution + " b";
                    break;
                default:
                    System.out.println("Not a real Move. Try Again."); 
            }
        }
        scan.close();
    }
    public static void main(String[] args) throws Exception {
        //final_output_only_mode(cube);
        print_every_loop_mode(cube);
    }
}
