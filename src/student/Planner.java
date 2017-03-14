package student;

import cz.cvut.atg.zui.astar.AbstractOpenList;
import cz.cvut.atg.zui.astar.PlannerInterface;
import cz.cvut.atg.zui.astar.RoadGraph;
import cz.cvut.atg.zui.astar.Utils;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;
import eu.superhub.wp5.planner.planningstructure.PermittedMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class Planner implements PlannerInterface {

    private RoadGraph graph = null;
    private double fastestKPH = 0;
    private GraphNode destination = null;
    private GraphNode origin = null;
    private OpenList openList = null;
    private SavedNode finalNode = null;
    private TreeSet<Long> closedList;

    @Override
    public List<GraphEdge> plan(RoadGraph graph, GraphNode origin, GraphNode destination) {
        this.graph = graph;
        this.destination = destination;
        this.origin = origin;
        this.closedList = new TreeSet<>();
        resetFastestAllowedKmph();

        openList = (OpenList) getOpenList();
        //Add first node to the open list...
        openList.add(new SavedNode(origin, null, getGx(origin, null, 0), getHx(origin)));

        while (finalNode == null && !openList.isEmpty()) updateOpenList();

        List<GraphEdge> graphEdges = composePath();

        return graphEdges;
        //throw new NotImplementedException();
    }

    private List<GraphEdge> composePath() {
        List<GraphEdge> path = new ArrayList<>();
        SavedNode current = finalNode;

        while (current.getNode().getId() != origin.getId()) {
            path.add(graph.getEdge(current.getPrevious().getNode().getId(), current.getNode().getId()));
            current = current.getPrevious();
        }
        Collections.reverse(path);
        return path;
    }

    private void updateOpenList(){
        SavedNode toExpand = openList.poll();
        if (toExpand.getNode().equals(destination)) {
            finalNode = toExpand;
        }

        closedList.add(toExpand.getNode().getId());

        List<GraphEdge> nodeOutcomingEdges = graph.getNodeOutcomingEdges(toExpand.getNode().getId());
        if (nodeOutcomingEdges == null) return;

        for (GraphEdge nodeOutcomingEdge : nodeOutcomingEdges) {
            if (!nodeOutcomingEdge.getPermittedModes().contains(PermittedMode.CAR)) continue;
            GraphNode node = graph.getNodeByNodeId(nodeOutcomingEdge.getToNodeId());
            if (closedList.contains(node.getId())) continue;
            SavedNode newSavedNode = new SavedNode(node, toExpand, getGx(nodeOutcomingEdge, toExpand.getGx()), getHx(node));
            openList.add(newSavedNode);
        }
    }

    /**
     * Gets the fastest allowed speed in Kmph in current {@linkplain #graph} and sets it to {@linkplain #fastestKPH}
     * MIGHT BE UNNECESSARY
     */
    private void resetFastestAllowedKmph() {
        long time = System.currentTimeMillis();
        for (GraphEdge graphEdge : graph.getAllEdges()) {
            fastestKPH = Math.max(fastestKPH, graphEdge.getAllowedMaxSpeedInKmph());
        }
        System.out.println("Time taken to get FastestAllowedKmph(): " + (System.currentTimeMillis() - time) + " milliseconds ");
    }

    /**
     * A simple heuristic, returning time to arrive at fastest possible speed by flight distance
     */
    private double getHx(GraphNode node) {
        //return 0;
        return (Utils.distanceInKM(node, destination) / fastestKPH) ;
    }

    /**
     * Gets the distance from origin to current node.
     */
    private double getGx(GraphNode node, GraphNode previous, double previousGx) {
        //It is origin node
        if (previous == null) return 0;

        GraphEdge edge = graph.getEdge(previous.getId(), node.getId());
        return ((edge.getLengthInMetres() / 1000) / edge.getAllowedMaxSpeedInKmph()) + previousGx;
    }

    private double getGx(GraphEdge edge, double previousGx) {
        return ((edge.getLengthInMetres() / 1000)/edge.getAllowedMaxSpeedInKmph()) + previousGx;
    }

    @Override
    public AbstractOpenList getOpenList() {
        return new OpenList();
    }
}