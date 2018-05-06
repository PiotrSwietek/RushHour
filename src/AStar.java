import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * This is the template for a class that performs A* search on a given
 * rush hour puzzle with a given heuristic.  The main search
 * computation is carried out by the constructor for this class, which
 * must be filled in.  The solution (a path from the initial state to
 * a goal state) is returned as an array of <tt>State</tt>s called
 * <tt>path</tt> (where the first element <tt>path[0]</tt> is the
 * initial state).  If no solution is found, the <tt>path</tt> field
 * should be set to <tt>null</tt>.  You may also wish to return other
 * information by adding additional fields to the class.
 */
public class AStar {

    /**
     * The solution path is stored here
     */
    public State[] path;

    /**
     * This is the constructor that performs A* search to compute a
     * solution for the given puzzle using the given heuristic.
     */
    public AStar(Puzzle puzzle, Heuristic heuristic) {

        List<Node> closedSet = new LinkedList<>();
        List<Node> openSet = new LinkedList<>();

        openSet.add(puzzle.getInitNode());

        while (!openSet.isEmpty()) {
            Node current = openSet.stream().min(Comparator.comparingInt(o -> f(o, heuristic))).get();

            if (current.getState().isGoal()) {
                reconstructPath(current);
                return;
            }

            openSet.removeIf(node -> isNodeEqual(node, current));
            closedSet.add(current);

            for (Node neighbor : current.expand()) {
                if (closedSet.stream().anyMatch(node -> isNodeEqual(node, neighbor))) { // closedSet.contains(neighbor)
                    continue;
                }

                Optional<Node> oldNeighborOptional = openSet.stream().filter(node -> isNodeEqual(node, neighbor)).findFirst();
                if(!oldNeighborOptional.isPresent()){ // !openSet.contains(neighbor)
                    openSet.add(neighbor);
                }else { // when state is already in openSet, check if neighbor has a better "start to neighbor" path
                    Node oldNeighbor = oldNeighborOptional.get();
                    if(oldNeighbor.getDepth() > neighbor.getDepth()){
                        openSet.remove(oldNeighbor);
                        openSet.add(neighbor);
                    }
                }
            }
        }
    }

    private boolean isNodeEqual(Node n1, Node n2){
        return n1.getState().equals(n2.getState());
    }

    private void reconstructPath(Node finalNode) {
        Node node = finalNode;

        List<State> s = new LinkedList<>();
        while (node != null) {
            s.add(node.getState());
            node = node.getParent();
        }
        path = s.toArray(new State[0]);
    }

    private int f(Node node, Heuristic heuristic) {
        return node.getDepth() + heuristic.getValue(node.getState());
    }
}
