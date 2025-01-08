
public class Node {
    // Private data fields
    private int name;
    private int parent;
    private int dTime;
    private int fTime;
    private int dist;
    private int color;

    // Constants for color representation
    public static final int WHITE = 0;
    public static final int GRAY = 1;
    public static final int BLACK = 2;

    // Constructor
    public Node(int name) {
        this.name = name;
        this.parent = -1;
        this.dTime = 0;
        this.fTime = 0;
        this.dist = Integer.MIN_VALUE; // Initial "negative infinity"
        this.color = WHITE; // Initially unvisited
    }

    // Getters and setters
    public int getName() {
        return name;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getDTime() {
        return dTime;
    }

    public void setDTime(int dTime) {
        this.dTime = dTime;
    }

    public int getFTime() {
        return fTime;
    }

    public void setFTime(int fTime) {
        this.fTime = fTime;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    // toString method
    @Override
    public String toString() {
        return "Node{" +
                "name=" + name +
                ", parent=" + parent +
                ", dTime=" + dTime +
                ", fTime=" + fTime +
                ", dist=" + dist +
                ", color=" + color +
                '}';
    }
}
