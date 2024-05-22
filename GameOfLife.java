package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        // WRITE YOUR CODE HERE
        StdIn.setFile(file);

        int rows = StdIn.readInt();
        int cols = StdIn.readInt();

        grid = new boolean[rows][cols];


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = StdIn.readBoolean();
            }
        }
         
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        // WRITE YOUR CODE HERE
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
            return grid[row][col];
        } 

        return false;
    }
    

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j]) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        // WRITE YOUR CODE HERE
        int neighboringCell = 0;
        
        int north = row - 1;
        int south = row + 1;
        int east = col + 1;
        int west = col - 1;

        if(north < 0){
            north = grid.length - 1;
        }
        if(south >= grid.length){
            south = 0;
        }
        if(east >= grid[row].length){
            east = 0;
        }
        if(west < 0){
            west = grid[row].length - 1;
        }
    

        if(grid[north][col] == true){
            neighboringCell++;
        }
        if(grid[south][col] == true){
            neighboringCell++;
        }
        if(grid[row][east] == true){
            neighboringCell++;
        }
        if(grid[row][west] == true){
            neighboringCell++;
        }
        if(grid[north][west] == true){
            neighboringCell++;
        }
        if(grid[north][east] == true){
            neighboringCell++;
        }
        if(grid[south][west] == true){
            neighboringCell++;
        }
        if(grid[south][east] == true){
            neighboringCell++;
        }


        return neighboringCell;


    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        // WRITE YOUR CODE HERE
        int row = grid.length;
        int col = grid[0].length;

        boolean[][] newGrid = new boolean[row][col];

        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(grid[i][j]){
                    newGrid[i][j] = numOfAliveNeighbors(i, j) == 2 || numOfAliveNeighbors(i, j) == 3;
                } else{
                    newGrid[i][j] = numOfAliveNeighbors(i, j) == 3;
                }
            }
        }

        return newGrid;
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        // WRITE YOUR CODE HERE
        boolean[][] newGrid = computeNewGrid();

        int row = grid.length;
        int col = grid[0].length;

        totalAliveCells = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = newGrid[i][j];
                if (grid[i][j]) {
                    totalAliveCells++;
                }
            }
        }
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        // WRITE YOUR CODE HERE
        for (int i = 0; i < n; i++) {
            nextGeneration();
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        // WRITE YOUR CODE HERE
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(grid.length, grid[0].length);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j]) {
                    int[][] neighbors = {
                        {-1, 0}, {0, -1}, {0, 1}, {1, 0}
                    };

                    for (int k = 0; k < neighbors.length; k++) {
                        int neighborRow = i + neighbors[k][0];
                        int neighborCol = j + neighbors[k][1];

                        if (neighborRow >= 0 && neighborRow < grid.length &&
                            neighborCol >= 0 && neighborCol < grid[0].length &&
                            grid[neighborRow][neighborCol]) {
                            uf.union(i, j, neighborRow, neighborCol);
                        }
                    }
                }
            }
        }

        boolean[] numOfRoots = new boolean[grid.length * grid[0].length];

        int numCommunities = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j]) {
                    int root = uf.find(i, j);
                    if (!numOfRoots[root]) {  
                        numCommunities++;
                        numOfRoots[root] = true;
                    }
                }
            }
        }

        return numCommunities;
    }
}