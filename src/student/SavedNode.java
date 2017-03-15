package student;

import eu.superhub.wp5.planner.planningstructure.GraphNode;

/**
 * Created by lactosis on 14.3.17.
 */
public class SavedNode {
    private GraphNode node;
    private SavedNode previous;
    private double hx;
    private double gx;
    private double fx;


    public SavedNode(GraphNode node, SavedNode previous, double gx, double hx) {
        this.node = node;
        this.hx = hx;
        this.gx = gx;
        this.fx = gx+hx;
        this.previous = previous;
    }

    public SavedNode getPrevious() {
        return previous;
    }

    public void setPrevious(SavedNode previous) {
        this.previous = previous;
    }

    public GraphNode getNode() {
        return node;
    }

    public double getFx() {
        return fx;
    }

    public void setNode(GraphNode node) {
        this.node = node;
    }

    public double getGx() {
        return gx;
    }

    public void setGx(double gx) {
        this.gx = gx;
        fx = gx + hx;
    }

    public double getHx() {
        return hx;
    }

    public void setHx(double hx) {
        this.hx = hx;
        fx = gx + hx;
    }

    @Override
    public String toString() {
        return "SavedNode - F(x)=" + getFx() + " Node: " + node + " Previous: " + previous;
    }

    @Override
    public boolean equals(Object obj) {
        return ((SavedNode) obj).getNode().equals(getNode());
    }
}
