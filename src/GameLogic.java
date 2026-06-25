import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

    private char[][] board;
    private Random   random;

    public GameLogic() {
        board  = new char[3][3];
        random = new Random();
        resetBoard();
    }

    public void resetBoard() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = ' ';
            }
        }
    }

    public boolean makeMove(int row, int col, char symbol) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) return false;
        if (board[row][col] != ' ') return false;
        board[row][col] = symbol;
        return true;
    }

    public boolean checkWin(char symbol) {
        for (int r = 0; r < 3; r++) {
            if (board[r][0] == symbol && board[r][1] == symbol && board[r][2] == symbol)
                return true;
        }

        for (int c = 0; c < 3; c++) {
            if (board[0][c] == symbol && board[1][c] == symbol && board[2][c] == symbol)
                return true;
        }

        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol)
            return true;
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)
            return true;

        return false;
    }

    public boolean isBoardFull() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == ' ') return false;
            }
        }
        return true;
    }

    public int[] computerMove() {
        ArrayList<int[]> emptyCells = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == ' ') {
                    emptyCells.add(new int[]{r, c});
                }
            }
        }
        if (emptyCells.isEmpty()) return null;
        return emptyCells.get(random.nextInt(emptyCells.size()));
    }

    public char[][] getBoard() {
        return board;
    }
}