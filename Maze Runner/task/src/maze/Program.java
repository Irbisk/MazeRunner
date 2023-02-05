package maze;

import java.util.Scanner;

public class Program {
    Scanner scanner = new Scanner(System.in);
    private static int[][] mazeResult;
    private static boolean on = true;
    public void start() {
        while (on) {
            if (mazeResult == null) {
                menuNull();
            } else{
                menu();
            }
        }
    }

    private void menuNull() {
        System.out.println("=== Menu ===\n" +
                "1. Generate a new maze\n" +
                "2. Load a maze\n" +
                "0. Exit");

        int command = scanner.nextInt();

        switch (command) {
            case 1:
                MazeHandler mazeHandler = new MazeHandler();
                mazeResult = mazeHandler.generateNewMaze();
                break;
            case 2:
                mazeHandler = new MazeHandler();
                mazeResult = mazeHandler.loadMaze();
                break;
            case 0:
                System.out.println("Bye!");
                on = false;
            default:
                System.out.println("Incorrect option. Please try again");
        }


    }

    private void menu() {

            System.out.println("=== Menu ===\n" +
                    "1. Generate a new maze\n" +
                    "2. Load a maze\n" +
                    "3. Save the maze\n" +
                    "4. Display the maze\n" +
                    "5. Find the escape\n" +
                    "0. Exit");
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    MazeHandler mazeHandler = new MazeHandler();
                    mazeResult = mazeHandler.generateNewMaze();
                    break;
                case 2:
                    mazeHandler = new MazeHandler();
                    mazeResult = mazeHandler.loadMaze();
                    break;
                case 3:
                    mazeHandler = new MazeHandler();
                    mazeHandler.saveMaze(mazeResult);
                    break;
                case 4:
                    Maze.printMatrix(mazeResult);
                    break;
                case 5:
                    mazeHandler = new MazeHandler();
                    mazeHandler.findEscape(mazeResult);
                    break;
                case  0:
                    on = false;
                    System.out.println("Bye!");
                default:
                    System.out.println("Incorrect option. Please try again");
            }
    }



}
