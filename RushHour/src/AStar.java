import java.util.*;

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

    /** The solution path is stored here */
    public List<State> path;
    private Puzzle puzzle;
    private Heuristic heuristic;

    //init the openQueue, initialCapacity set to 11 as specified in the docs
    final Queue<Node> openQueue = new PriorityQueue<>(11, new NodeComparator());

    //initialize closedList
    final Set<Node> closedList = new HashSet<>();
    /**
     * This is the constructor that performs A* search to compute a
     * solution for the given puzzle using the given heuristic.
     */
    public AStar(Puzzle puzzle, Heuristic heuristic) {
        this.puzzle = puzzle;
        this.heuristic = heuristic;
    }

    public List<State> astar(){
        //init the result Path
        List<State> resultPath = new ArrayList<>();

        //get the first node
        Node startNode = puzzle.getInitNode();

        // add node to openQueue
        openQueue.add(startNode);


        while(!openQueue.isEmpty()){
            //get and remove node from openQueue
            final Node currentNode = openQueue.poll();

            //is this the goal? if yes return the resultpath
            if(currentNode.getState().isGoal()){
                return resultPath;
            }

            //add this node to closedList
            closedList.add(currentNode);

            //expand the current open node and add its children to open list
            expandNode(currentNode);
        }

        return null;
    }

    /*expands given node and adds its children to openQueue IF:
    *  - succesor found for the first time
    *  - a better path to the node is found
    * */
    private void expandNode(Node node){
        List<Node> childNodes = Arrays.asList(node.expand());

        for (Node child : childNodes) {
            if (!closedList.contains(child)){

                int tentative_g = 0; //what is this?

            }

        }
    }

}
