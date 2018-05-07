import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * This is a template for the class corresponding to the blocking
 * heuristic.  This heuristic returns zero for goal states, and
 * otherwise returns one plus the number of cars blocking the path of
 * the goal car to the exit.  This class is an implementation of the
 * <tt>Heuristic</tt> interface, and must be implemented by filling in
 * the constructor and the <tt>getValue</tt> method.
 */
public class BlockingHeuristic implements Heuristic {

    /**
     * This is the required constructor, which must be of the given form.
     */
    public BlockingHeuristic(Puzzle puzzle) {
    }
	

    /**
     * This method returns the value of the heuristic function at the
     * given state.
     */
    public int getValue(State state) {
        if(state.isGoal()) return 0;

        int value = 1;
        
        Puzzle puzzle = state.getPuzzle();

        int redX = state.getVariablePosition(0);
        int redY = puzzle.getFixedPosition(0);
        int redRightX = redX + puzzle.getCarSize(0) -1;

        for (int v = 0; v < puzzle.getNumCars(); v++) {
            boolean isVertical = puzzle.getCarOrient(v);
            if(isVertical){
                boolean isRightOfRed =  redRightX < puzzle.getFixedPosition(v);

                if(isRightOfRed){
                    int yStart = state.getVariablePosition(v);
                    int yEnd = yStart + puzzle.getCarSize(v) -1;
                    boolean isBlockingRedVertically = (redY >= yStart) && (redY <= yEnd);

                    if(isBlockingRedVertically){
                        value++;
                    }
                }
            }
        }

        return value;
    }

}
