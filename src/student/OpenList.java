package student;

import cz.cvut.atg.zui.astar.AbstractOpenList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by lactosis on 14.3.17.
 */
public class OpenList extends AbstractOpenList {
    Comparator<SavedNode> comp = new Comparator<SavedNode>() {
        @Override
        public int compare(SavedNode o1, SavedNode o2) {
            if (o1.getFx() == o2.getFx()) {
                return Double.compare(o1.getHx(), o2.getHx());
            } else {
                return Double.compare(o1.getFx(), o2.getFx());
            }
        }
    };

    private PriorityQueue<SavedNode> openList = new PriorityQueue<>(comp);

    @Override
    protected boolean addItem(Object item) {
        SavedNode node = (SavedNode) item;
        if (!openList.contains(node)) {
            openList.offer(node);
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

    public void refreshNode(SavedNode node) {
        for (SavedNode savedNode : openList) {
            if (savedNode.getNode().equals(node.getNode())) {
                if (savedNode.getFx() > node.getFx()) {
                    openList.remove(savedNode);
                    openList.add(node);
                    return;
                }
            }
        }
        add(node);
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
