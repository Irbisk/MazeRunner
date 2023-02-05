package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MazeHandler {
    private static final String pathToFile = "C:\\Users\\irbis\\IdeaProjects\\Maze Runner\\Maze Runner\\task\\src\\files\\";
    Scanner scanner = new Scanner(System.in);

    public void saveMaze(int[][] mazeResult) {
        String fileName = scanner.nextLine();
        File file = new File(pathToFile + fileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.print(mazeResult.length + " ");
            for (int i = 0; i < mazeResult.length; i++) {
                for (int j = 0; j < mazeResult[0].length; j++) {
                    printWriter.print(mazeResult[i][j] + " ");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int[][] loadMaze() {
        String fileName = scanner.nextLine();
        int[][] mazeResult = null;
        try {
            String text = readFileAsString(pathToFile + fileName);
            if (!text.isEmpty()) {
                String[] numbers = text.split(" ");
                if (numbers[0].matches("\\d+")) {
                    int size = Integer.parseInt(numbers[0]);
                    if (numbers.length == size * size + 1) {
                        mazeResult = new int[size][size];
                        int number = 0;
                        for (int i = 0; i < size; i++) {
                            for (int j = 0; j < size; j++) {
                                mazeResult[i][j] = Integer.parseInt(numbers[++number]);
                            }
                        }
                    } else {
                        System.out.println("Cannot load the maze. It has an invalid format");
                    }
                } else {
                    System.out.println("Cannot load the maze. It has an invalid format");
                }
            } else {
                System.out.println("Cannot load the maze. It has an invalid format");
            }

        } catch (FileNotFoundException e) {
            System.out.println("The file " + fileName + " does not exist");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mazeResult;
    }



    public int[][] generateNewMaze() {
        System.out.println("Enter the size of a new maze");
        int size = scanner.nextInt();
        scanner.nextLine();
        Maze maze = new Maze(size, size);
        maze.prim();
        Maze.printMatrix(maze.result);
        return maze.result;
    }

    public void findEscape(int[][] mazeResult) {
        int name = 0;
        List<Vertex> vertexList = new ArrayList<>();
        for (int i = 0; i < mazeResult.length; i++) {
            for (int j = 0; j < mazeResult[0].length; j++) {
                vertexList.add(new Vertex(i, j, mazeResult[i][j], name++));
            }
        }

        Vertex start = Vertex.findStart(mazeResult, vertexList);
        Vertex end = Vertex.findEnd(mazeResult, vertexList);

        System.out.println("start " + start.getX() + "," + start.getY());
        System.out.println("end " + end.getX() + "," + end.getY());


        ShortestPath shortestPath = new ShortestPath(start, end);
        shortestPath.dfs(start, mazeResult, vertexList);
        List<Vertex> route = shortestPath.trace_route();
        route.forEach(vertex -> {
            int x = vertex.getX();
            int y = vertex.getY();
            mazeResult[x][y] = 2;
        });

        Maze.printMatrix(mazeResult);

    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }





}
