import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private boolean[][] isSiteOpen;
    private WeightedQuickUnionUF quWithBothRoots;

    private final int topRoot;
    private final int bottomRoot;
    private int totalOpenSites;
    private final int gridSize;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if(n <= 0)
            throw new IllegalArgumentException();

        this.gridSize = n;
        this.isSiteOpen = new boolean[this.gridSize][this.gridSize];
        this.quWithBothRoots = new WeightedQuickUnionUF(this.gridSize*this.gridSize+2);
        this.topRoot = this.gridSize*this.gridSize;
        this.bottomRoot = this.gridSize*this.gridSize+1;
    }

    private void check_if_row_col_are_in_range(int row, int col)
    {
        if(row <= 0 || col <= 0 || row > this.gridSize || col > this.gridSize)
            throw new IllegalArgumentException("Parameters out of the grid");
    }

    private int get_index_of(int row, int col)
    {
        return (row-1)*this.gridSize+(col-1);
    }

    private void tryUnion(int i, int row, int col)
    {
        if(row >= 1 && row <= this.gridSize && col >= 1 && col <= this.gridSize)
        {
            if (this.isSiteOpen[row - 1][col - 1]) {
                int adjacentIndex = this.get_index_of(row, col);
                this.quWithBothRoots.union(i, adjacentIndex);
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.check_if_row_col_are_in_range(row, col);

        if(this.isSiteOpen[row-1][col-1])
            return;
        this.isSiteOpen[row-1][col-1] = true;
        this.totalOpenSites++;

        int i = this.get_index_of(row, col);

        if(row == 1)
            this.quWithBothRoots.union(i, this.topRoot);
        if(row == this.gridSize)
            this.quWithBothRoots.union(i, this.bottomRoot);

        this.tryUnion(i , row-1, col); //up
        this.tryUnion(i , row+1, col); //down
        this.tryUnion(i , row, col-1); //left
        this.tryUnion(i , row, col+1); //right
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        this.check_if_row_col_are_in_range(row, col);
        return this.isSiteOpen[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        this.check_if_row_col_are_in_range(row, col);
        int i = this.get_index_of(row, col);
        return this.quWithBothRoots.connected(i, this.topRoot);
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return this.totalOpenSites;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return this.quWithBothRoots.connected(topRoot, bottomRoot);
    }

    // test client (optional)
    public static void main(String[] args)
    {
        /* file format
        3
        1 3
        2 3
        3 3
        3 1
        2 1
        1 1
         */
        int size = StdIn.readInt();
        Percolation percolation = new Percolation(size);
        while(!StdIn.isEmpty())
        {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            percolation.open(row, col);
        }
        StdOut.println("Percolates: " + percolation.percolates());

    }

}
