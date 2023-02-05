package maze;

public class Edge implements Comparable<Edge> {

    Vertex _from;
    Vertex _to;
    int weight;
    static int totalEdges = 0;

    public Edge(Vertex from, Vertex to, int weight) {
        this._from = from;
        this._to = to;
        this.weight = weight;
    }


    @Override
    public String toString() {
        return this._from.getName() + " ----- " + this._to.getName();
    }

    @Override
    public int compareTo(Edge o) {
        return (this.weight - o.weight);
    }

}
