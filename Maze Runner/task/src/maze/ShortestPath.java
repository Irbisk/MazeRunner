package maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShortestPath {
    private final Vertex start;
    private final Vertex end;
    private int length = 0;
    private int shortLength = Integer.MAX_VALUE;


    public ShortestPath(Vertex start, Vertex end) {
        this.start = start;
        this.end = end;
    }

    public void dfs(Vertex vertex, int[][] mazeResult, List<Vertex> vertexList){

        length +=1;

        if(length > shortLength)
            return;

        if(vertex.getX() == end.getX() && vertex.getY() == end.getY()){
            shortLength = length;
            return;
        }

        vertex.setVisited(true);


        for(Vertex nbr: vertex.getNeighbours(mazeResult, vertexList)){
            if(!nbr.isVisited()){
                nbr.setPrev(vertex);
                dfs(nbr, mazeResult, vertexList);
            }
        }
        length -=1;
    }

    public List<Vertex> trace_route(){
        Vertex vertex = end;
        List<Vertex> route = new ArrayList<>();

        while(vertex != null){
            route.add(vertex);
            vertex = vertex.getPrev();
        }
        Collections.reverse(route);
        return route;
    }
}

