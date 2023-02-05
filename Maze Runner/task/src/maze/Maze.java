package maze;

import java.util.*;

public class Maze {
    private Vertex[][] matrix;
    private int x;
    private int y;

    public int[][] result;

    public Maze(int x, int y) {
        this.x = x;
        this.y = y;
        this.matrix = new Vertex[(x - 1) / 2][(y - 1) / 2];
        this.matrix = createMatrix();
    }

    public void printAdjMatrix(int[][] adjacentMatrix) {
        for (int[] ints : adjacentMatrix) {
            for (int j = 0; j < adjacentMatrix[0].length; j++) {
                String value;
                if (ints[j] == Integer.MAX_VALUE) {
                    value = "Ш";
                } else {
                    value = String.valueOf(ints[j]);
                }
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public List<Vertex> createVertexList(Vertex[][] matrix) {
        List<Vertex> list = new ArrayList<>();
        int name = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = new Vertex(i, j, name++);
                list.add(matrix[i][j]);
            }
        }
        return list;
    }

    public Vertex[][] createMatrix () {
        List<Vertex> list = createVertexList(this.matrix);

        //create adjMatrix

        int[][] adjMatrix = new int[list.size()][list.size()];

        Random random = new Random();

        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[0].length; j++) {
                if (i == j) {
                    adjMatrix[i][j] = 0;
                } else {
                    if (checkAdj(list.get(i), list.get(j))) {

                        if (adjMatrix[j][i] != 0) {
                            adjMatrix[i][j] = adjMatrix[j][i];
                        } else {
                            adjMatrix[i][j] = random.nextInt(4) + 1;
                        }

                        //connect vertixes in the list
                        list.get(i).connect(list.get(j), adjMatrix[i][j]);
                    } else {
                        adjMatrix[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
        }

        //create result matrix with Vertixes with connected edges
        int count = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                this.matrix[i][j] = list.get(count++);
            }
        }
        return this.matrix;
    }


    public boolean checkAdj(Vertex v1, Vertex v2) {
        int x1 = v1.getX();
        int y1 = v1.getY();
        int x2 = v2.getX();
        int y2 = v2.getY();
        return (Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 0) ||
                (Math.abs(x2 - x1) == 0 && Math.abs(y2 - y1) == 1);
    }



    public void prim() {

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
        List<Edge> mst = new ArrayList<>();
        Vertex start = matrix[new Random().nextInt(matrix.length)][0];

        addEdges(start, priorityQueue);
        while (!priorityQueue.isEmpty()) {
            Edge minEdge = priorityQueue.poll();

            if (minEdge._to.isVisited()) {
                continue;
            } else {
                mst.add(minEdge);
                addEdges(minEdge._to, priorityQueue);
            }
        }
        createResult(mst);
    }

    void addEdges(Vertex s, PriorityQueue<Edge> priorityQueue) {
        s.setVisited(true);
        for (Edge edge: s.getEdges()) {
            if (!edge._to.isVisited()) {
                priorityQueue.add(edge);
            }
        }
    }



    public void createResult(List<Edge> list) {


        int[][] initMatrix = new int[this.matrix.length * 2 - 1][this.matrix[0].length * 2 - 1];
        Arrays.stream(initMatrix).forEach(row -> Arrays.stream(row).forEach(x -> x = 0));
        list.forEach(vertex -> {
            int fromX = vertex._from.getX();
            int fromY = vertex._from.getY();
            int toX = vertex._to.getX();
            int toY = vertex._to.getY();

            int resultX;
            int resultY;

            if (fromX == toX) {
                resultX = fromX * 2;
                if (fromY < toY) {

                    resultY = toY * 2 - 1;
                } else {
                    resultY = toY * 2 + 1;
                }
            } else {
                resultY = fromY * 2;
                if (fromX < toX) {
                    resultX = toX * 2 - 1;
                } else  {
                    resultX = toX * 2 + 1;
                }
            }

            initMatrix[fromX * 2][fromY * 2] = 1;
            initMatrix[resultX][resultY] = 1;
            initMatrix[toX * 2][toY * 2] = 1;
        });

        int[][] resultMatrix = new int[initMatrix.length + 2][initMatrix[0].length + 2];
        for (int i = 0; i < resultMatrix.length; i++) {
            for (int j = 0; j < resultMatrix[0].length; j++) {
                if (i == 0 || i == resultMatrix.length - 1) {
                    resultMatrix[i][j] = 0;
                }
                if (j == 0 || j == resultMatrix[0].length - 1) {
                    resultMatrix[i][j] = 0;
                }
                if (i > 0 && i < resultMatrix.length - 1 && j > 0 && j < resultMatrix[0].length - 1) {
                    resultMatrix[i][j] = initMatrix[i - 1][j - 1];
                }
            }
        }



        //create enter
        while (true) {
            int enter = new Random().nextInt(resultMatrix.length - 1);
            if (resultMatrix[enter][1] == 1) {
                resultMatrix[enter][0] = 1;
                break;
            }
        }
        //create exit
        while (true) {
            int enter = new Random().nextInt(resultMatrix.length - 1);
            if (resultMatrix[enter][resultMatrix[0].length - 2] == 1) {
                resultMatrix[enter][resultMatrix[0].length - 1] = 1;
                break;
            }
        }


        //for even matrix sizes
        if (x % 2 == 0 || y % 2 == 0) {
            if (x % 2 == 0 && y % 2 == 1) {
                int[][] evenMatrix = new int[resultMatrix.length + 1][resultMatrix[0].length];
                for (int i = 0; i < evenMatrix.length; i++) {
                    for (int j = 0; j < evenMatrix[0].length; j++) {
                        if (i == evenMatrix.length - 2) {
                            evenMatrix[i][j] = resultMatrix[i - 1][j];
                        } else if (i == evenMatrix.length - 1) {
                            evenMatrix[i][j] = 0;
                        } else {
                            evenMatrix[i][j] = resultMatrix[i][j];
                        }

                    }
                }
                result = correctExits(evenMatrix);

            } else if (x % 2 == 1) {
                int[][] evenMatrix = new int[resultMatrix.length][resultMatrix[0].length + 1];
                for (int i = 0; i < evenMatrix.length; i++) {
                    for (int j = 0; j < evenMatrix[0].length; j++) {
                        if (j == evenMatrix[0].length - 1) {
                            evenMatrix[i][j] = resultMatrix[i][resultMatrix.length - 1];
                        } else {
                            evenMatrix[i][j] = resultMatrix[i][j];
                        }
                    }
                }
                result = evenMatrix;
            } else {
                int[][] evenMatrixDemo = new int[resultMatrix.length][resultMatrix[0].length + 1];
                for (int i = 0; i < evenMatrixDemo.length; i++) {
                    for (int j = 0; j < evenMatrixDemo[0].length; j++) {
                        if (j == evenMatrixDemo[0].length - 1) {
                            evenMatrixDemo[i][j] = resultMatrix[i][resultMatrix.length - 1];
                        } else {
                            evenMatrixDemo[i][j] = resultMatrix[i][j];
                        }
                    }
                }
                int[][] evenMatrix = new int[evenMatrixDemo.length + 1][evenMatrixDemo[0].length];
                for (int i = 0; i < evenMatrix.length; i++) {
                    for (int j = 0; j < evenMatrix[0].length; j++) {
                        if (i == evenMatrix.length - 2) {
                            evenMatrix[i][j] = evenMatrixDemo[i - 1][j];
                        } else if (i == evenMatrix.length - 1) {
                            evenMatrix[i][j] = 0;
                        } else {
                            evenMatrix[i][j] = evenMatrixDemo[i][j];
                        }
                    }
                }
                result = correctExits(evenMatrix);
            }
        } else {
            result = resultMatrix;
        }
    }

    public static void printMatrix(int[][] matrix) {
        String space = "  ";
        String wall = "\u2588\u2588";
        String way = "//";
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (ints[j] == 0) {
                    System.out.print(wall);
                } else if (ints[j] == 1) {
                    System.out.print(space);
                } else {
                    System.out.print(way);
                }
            }
            System.out.println();
        }

    }

    private int[][] correctExits(int[][] matrix) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 1) {
                count++;
            }
            if (matrix[i][matrix[0].length - 1] == 1) {
                count++;
            }
        }

        if (count != 2) {
            int side = 0;
            for (int i = 0; i < matrix.length; i++) {
                if (side == 1) {
                    matrix[i][0] = 0;
                    side = 0;
                    break;
                }
                if (matrix[i][0] == 1) {
                    side++;
                }

            }
            for (int i = 0; i < matrix.length; i++) {
                if (side == 1) {
                    matrix[i][matrix[0].length - 1] = 0;
                    break;
                }
                if (matrix[i][matrix[0].length - 1] == 1) {
                    side++;
                }

            }
        }
        return matrix;
    }
}


