import java.util.stream.IntStream;

/**
 * This is a template for the class corresponding to your original
 * advanced heuristic.  This class is an implementation of the
 * <tt>Heuristic</tt> interface.  After thinking of an original
 * heuristic, you should implement it here, filling in the constructor
 * and the <tt>getValue</tt> method.
 */
public class AdvancedHeuristic implements Heuristic {

    /**
     * This is the required constructor, which must be of the given form.
     */
    public AdvancedHeuristic(Puzzle puzzle) {
	// your code here

    }
	
    /**
     * This method returns the value of the heuristic function at the
     * given state.
     */
    public int getValue(State state) {
        Puzzle puz = state.getPuzzle();
        final int STARTCOL = state.getVariablePosition(0) + puz.getCarSize(0) - 1;

        int totalMoves = IntStream.range(1, puz.getNumCars())
               .filter(i -> puz.getCarOrient(i) && puz.getFixedPosition(i) > STARTCOL && isBlocking(state, i))
               .map(j -> getMovesNeeded(state, j))
               .sum();

        return totalMoves;
    }

    private int getMovesNeeded(State state, int carIdx) {
        // Check, if movable to top
        int topMoves = Integer.MAX_VALUE;
        if (isMovableToTop(state, carIdx)) {
            // Calculate moves needed
            topMoves = 1 + getNumberOfBlockingHorizontalCars(state, carIdx, true);
        }

        // Check, if movable to bottom
        int bottomMoves = Integer.MAX_VALUE;
        if (isMovableToBottom(state, carIdx)) {
            // Calculate moves needed
            bottomMoves = 1 + getNumberOfBlockingHorizontalCars(state, carIdx, false);
        }

        // Return min. moves
        return topMoves < bottomMoves ? topMoves : bottomMoves;
    }

    private boolean isMovableToTop(State state, int carIdx) {
        Puzzle puz = state.getPuzzle();
        final int EXITPOS = puz.getFixedPosition(0);
        final int CARSIZE = puz.getCarSize(carIdx);
        final int COLUMN = puz.getFixedPosition(carIdx);

        int totalCarSizes = CARSIZE;
        totalCarSizes += IntStream.range(1, puz.getNumCars())
                .filter(i -> puz.getCarOrient(i) && puz.getFixedPosition(i) == COLUMN && state.getVariablePosition(i) < EXITPOS && i != carIdx)
                .map(puz::getCarSize)
                .sum();

        // Check, if enough space
        return (EXITPOS - totalCarSizes) >= 0;
    }

    private boolean isMovableToBottom(State state, int carIdx) {
        Puzzle puz = state.getPuzzle();
        final int EXITPOS = puz.getFixedPosition(0);
        final int CARSIZE = puz.getCarSize(carIdx);
        final int COLUMN = puz.getFixedPosition(carIdx);

        int totalCarSizes = CARSIZE;
        totalCarSizes += IntStream.range(1, puz.getNumCars())
                .filter(i -> puz.getCarOrient(i) && puz.getFixedPosition(i) == COLUMN && state.getVariablePosition(i) > EXITPOS && i != carIdx)
                .map(puz::getCarSize)
                .sum();

        // Check, if enough space
        return (EXITPOS + totalCarSizes) < state.getPuzzle().getGridSize();
    }

    private int getNumberOfBlockingHorizontalCars(State state, int carIdx, boolean top) {
        Puzzle puz = state.getPuzzle();
        final int COLUMN = puz.getFixedPosition(carIdx);
        final int EXITPOS = puz.getFixedPosition(0);

        int totalNumber = (int) IntStream.range(1, puz.getNumCars())
                // Get only horizontal cars
                .filter(i -> !puz.getCarOrient(i) &&
                        // Filter only cars of one half
                        !(top && puz.getFixedPosition(i) > EXITPOS) &&
                        !(!top && puz.getFixedPosition(i) < EXITPOS) &&

                        // Filter only cars that block with the car passed as argument
                        (state.getVariablePosition(i) == COLUMN || (state.getVariablePosition(i) < COLUMN && (state.getVariablePosition(i) + puz.getCarSize(i) - 1) >= COLUMN )))
                .count();

        return totalNumber;
    }

    private boolean isBlocking(State state, int carIdx) {
        Puzzle puz = state.getPuzzle();
        final int EXITPOS = puz.getFixedPosition(0);

        int carPos = state.getVariablePosition(carIdx);
        int carSize = puz.getCarSize(carIdx);

        return carPos == EXITPOS || (carPos < EXITPOS && (carPos + carSize - 1) >= EXITPOS);
    }

}
