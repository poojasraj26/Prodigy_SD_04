package core4;
import java.util.Scanner;
public class SudokuSolver {
	 // Function to check if placing num in (row, col) is safe
    public static boolean isSafe(int[][] board, int N, int row, int col, int num) {
        // Check the row
        for (int i = 0; i < N; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // Check the column
        for (int i = 0; i < N; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // Check the sub-grid
        int subGridSize = (int) Math.sqrt(N); // The sub-grid size is sqrt(N)
        int startRow = row - row % subGridSize;
        int startCol = col - col % subGridSize;
        for (int i = 0; i < subGridSize; i++) {
            for (int j = 0; j < subGridSize; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // Function to solve the Sudoku using backtracking
    public static boolean solveSudoku(int[][] board, int N) {
        // Find an empty cell (represented by 0)
        int[] emptyCell = findEmptyCell(board, N);
        if (emptyCell == null) {
            return true;  // Puzzle solved
        }
        int row = emptyCell[0];
        int col = emptyCell[1];

        // Try numbers from 1 to N
        for (int num = 1; num <= N; num++) {
            if (isSafe(board, N, row, col, num)) {
                // Place the number in the empty cell
                board[row][col] = num;

                // Recursively try to solve the rest of the board
                if (solveSudoku(board, N)) {
                    return true;
                }

                // Backtrack: reset the cell if placing num doesn't work
                board[row][col] = 0;
            }
        }

        return false;  // Trigger backtracking
    }

    // Function to find the next empty cell (row, col)
    public static int[] findEmptyCell(int[][] board, int N) {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (board[row][col] == 0) {
                    return new int[] { row, col };
                }
            }
        }
        return null;  // No empty cells found (board is solved)
    }

    // Function to print the Sudoku board
    public static void printBoard(int[][] board, int N) {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    // Function to take user input for the Sudoku grid
    public static void inputBoard(int[][] board, int N) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Sudoku puzzle (0 for empty cells):");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print("Enter number for cell (" + (i+1) + ", " + (j+1) + "): ");
                board[i][j] = sc.nextInt();
            }
        }
    }

    // Main function to test the solver
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input the size of the Sudoku grid (N x N)
        System.out.print("Enter the size of the Sudoku grid (N x N): ");
        int N = sc.nextInt();

        // Check if N is a perfect square
        double sqrt = Math.sqrt(N);
        if (sqrt != (int) sqrt) {
            System.out.println("Error: The size N must be a perfect square (e.g., 4, 9, 16, 25, etc.).");
            return;
        }

        // Initialize the Sudoku board
        int[][] sudokuBoard = new int[N][N];

        // Input the Sudoku puzzle from the user
        inputBoard(sudokuBoard, N);

        System.out.println("\nSudoku Puzzle:");
        printBoard(sudokuBoard, N);

        // Solve the Sudoku puzzle
        if (solveSudoku(sudokuBoard, N)) {
            System.out.println("\nSolved Sudoku:");
            printBoard(sudokuBoard, N);
        } else {
            System.out.println("\nNo solution exists");
        }
    }
}