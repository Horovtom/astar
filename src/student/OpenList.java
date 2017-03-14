package student;

import cz.cvut.atg.zui.astar.AbstractOpenList;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by lactosis on 14.3.17.
 */
public class OpenList extends AbstractOpenList {
    private PriorityQueue<SavedNode> openList = new PriorityQueue<>((o1, o2) -> (int) (o1.getFx() - o2.getFx()));

    @Override
    protected boolean addItem(Object item) {
        SavedNode node = (SavedNode) item;
        if (!openList.contains(node)) {
            openList.add(node);
            return true;
        }
        return false;
    }

    public SavedNode getNodeById(long id) {
        for (SavedNode savedNode : openList) {
            if (savedNode.getNode().getId() == id) {
                openList.remove(savedNode);
                return savedNode;
            }
        }
        return null;
    }

    public SavedNode poll() {
        //System.out.println("Poll openList: " + openList.size());
        return openList.poll();
    }

    public boolean isEmpty() {
        return openList.isEmpty();
    }

    public static void main(String[] args) {
        PriorityQueue<SavedNode> openList = new PriorityQueue<>((o1, o2) -> (int) (o1.getFx() - o2.getFx()));
        openList.add(new SavedNode(null, null, 2, 3));
        openList.add(new SavedNode(null, null, 3, 3));
        openList.poll();
        System.out.println(Arrays.toString(openList.toArray()));

    }
}
