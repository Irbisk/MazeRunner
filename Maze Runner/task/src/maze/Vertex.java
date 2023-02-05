package maze;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private int name;
    private boolean visited = false;
    private List<Edge> edges;
    private List<Vertex> neighbours;
    private final int x;
    private final int y;
    private Vertex prev = null;
    private int value = 0;

    public List<Edge> getEdges() {
        return edges;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vertex(int x, int y, int value, int name) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.name = name;
    }

    public Vertex getPrev() {
        return prev;
    }

    public Vertex(int x, int y, int name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.edges = new ArrayList<>();
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Integer getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public boolean isVisited() {
        return visited;
    }

    public void connect(Vertex vertex, int edgeWeight) {
        edges.add(new Edge(this, vertex, edgeWeight));
        edges.add(new Edge(vertex, this, edgeWeight));
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Vertex> getNeighbours(int[][] mazeResult, List<Vertex> vertexList) {
        List<Vertex> neighbours = new ArrayList<>();
        int x = this.getX();
        int y = this.getY();

        // up
        int x1 = x - 1;
        int y1 = y;

        if (x1 >= 0) {
            if (mazeResult[x1][y1] == 1) {
                neighbours.add(findVertexByCoordinates(vertexList, x1, y1));
            }
        }

        // down

        x1 = x + 1;
        y1 = y;

        if (x1 < mazeResult.length) {
            if (mazeResult[x1][y1] == 1) {
                neighbours.add(findVertexByCoordinates(vertexList, x1, y1));
            }
        }

        //right
        x1 = x;
        y1 = y + 1;

        if (y1 < mazeResult[0].length) {
            if (mazeResult[x1][y1] == 1) {
                neighbours.add(findVertexByCoordinates(vertexList, x1, y1));
            }
        }
        //left
        x1 = x;
        y1 = y - 1;
        if (y1 >= 0) {
            if (mazeResult[x1][y1] == 1) {
                neighbours.add(findVertexByCoordinates(vertexList, x1, y1));
            }

        }

        return neighbours;
    }

    private static Vertex findVertexByCoordinates(List<Vertex> vertexList, int x, int y) {
        Vertex result = null;
        for (Vertex vertex: vertexList) {
            if (x == vertex.getX() && y == vertex.getY()) {
                result = vertex;
                break;
            }
        }
        return result;
    }



    public static Vertex findStart(int[][] mazeResult, List<Vertex> vertexList) {
        Vertex start = null;
        for (int i = 0; i < mazeResult.length; i++) {
            if (mazeResult[i][0] == 1) {
                start = findVertexByCoordinates(vertexList, i, 0);
            }
        }
        return start;
    }

    public static Vertex findEnd(int[][] mazeResult, List<Vertex> vertexList) {
        Vertex end = null;
        for (int i = 0; i < mazeResult.length; i++) {
            if (mazeResult[i][mazeResult.length - 1] == 1) {
                end = findVertexByCoordinates(vertexList, i, mazeResult.length - 1);
            }
        }
        return end;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\nX: " + getX() + "\nY: " + getY();
    }
}
