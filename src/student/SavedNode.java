package student;

import eu.superhub.wp5.planner.planningstructure.GraphNode;

/**
 * Created by lactosis on 14.3.17.
 */
public class SavedNode {
    private GraphNode node;
    private GraphNode previous;
    private double hx;
    private double gx;

    public SavedNode(GraphNode node, GraphNode previous, double gx, double hx) {
        this.node = node;
        this.hx = hx;
        this.gx = gx;
        this.previous = previous;
    }

    public GraphNode getPrevious() {
        return previous;
    }

    public void setPrevious(GraphNode previous) {
        this.previous = previous;
    }

    public GraphNode getNode() {
        return node;
    }

    public double getFx() {
        return gx + hx;
    }

    public void setNode(GraphNode node) {
        this.node = node;
    }

    public double getGx() {
        return gx;
    }

    public void setGx(double gx) {
        this.gx = gx;
    }

    public double getHx() {
        return hx;
    }

    public void setHx(double hx) {
        this.hx = hx;
    }

    @Override
    public String toString() {
        return "SavedNode -  Node: " + node + " F(x)=" + getFx() + " Previous: " + previous;
    }
}
