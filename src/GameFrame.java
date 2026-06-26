import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private Player        currentPlayer;
    private PlayerService playerService;
    private GameLogic     gameLogic;

    private JButton[][] buttons;  
    private JLabel      lblStatus;
    private boolean     gameOver;

    public GameFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        this.gameLogic     = new GameLogic();
        this.gameOver      = false;

        setTitle("Tic Tac Toe - " + player.getUsername());
        setSize(400, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

       
        lblStatus = new JLabel("Your turn (X)", SwingConstants.CENTER);
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        buttons = new JButton[3][3];

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                buttons[r][c] = new JButton("");
                buttons[r][c].setFont(new Font("SansSerif", Font.BOLD, 36));
                buttons[r][c].setFocusPainted(false);
                final int row = r;
                final int col = c;
                buttons[r][c].addActionListener(e -> handlePlayerMove(row, col));
                boardPanel.add(buttons[r][c]);
            }
        }

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton btnNewGame = new JButton("New Game");
        JButton btnBack    = new JButton("Back to Menu");

        btnNewGame.addActionListener(e -> resetGame());
        btnBack.addActionListener(e -> {
            MainMenuFrame menu = new MainMenuFrame(currentPlayer);
            menu.setVisible(true);
            this.dispose();
        });

        bottomPanel.add(btnNewGame);
        bottomPanel.add(btnBack);

        setLayout(new BorderLayout());
        add(lblStatus,   BorderLayout.NORTH);
        add(boardPanel,  BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }


    private void handlePlayerMove(int row, int col) {
        if (gameOver) return;

        boolean moved = gameLogic.makeMove(row, col, 'X');
        if (!moved) {
            JOptionPane.showMessageDialog(this, "Cell occupied, select another", "Invalid", JOptionPane.WARNING_MESSAGE);
            return;
        }

        buttons[row][col].setText("X");
        buttons[row][col].setForeground(new Color(30, 100, 200));
        buttons[row][col].setEnabled(false);

        if (gameLogic.checkWin('X')) {
            finishGame("WIN");
            return;
        }

        if (gameLogic.isBoardFull()) {
            finishGame("DRAW");
            return;
        }

        lblStatus.setText("Computer is thinking...");
        int[] compMove = gameLogic.computerMove();

        if (compMove != null) {
            gameLogic.makeMove(compMove[0], compMove[1], 'O');
            buttons[compMove[0]][compMove[1]].setText("O");
            buttons[compMove[0]][compMove[1]].setForeground(new Color(200, 50, 50));
            buttons[compMove[0]][compMove[1]].setEnabled(false);
        }

        if (gameLogic.checkWin('O')) {
            finishGame("LOSE");
            return;
        }

        if (gameLogic.isBoardFull()) {
            finishGame("DRAW");
            return;
        }

        lblStatus.setText("Your Turn (X)");
    }

    private void finishGame(String result) {
        gameOver = true;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                buttons[r][c].setEnabled(false);
            }
        }

        playerService.updateStatistics(currentPlayer, result);

        String message;
        if (result.equals("WIN")) {
            message = "Congratulation, you win! (+10 poin)";
            lblStatus.setText("Result : WIN!");
        } else if (result.equals("LOSE")) {
            message = "You Lose! Computers are smarter this time.";
            lblStatus.setText("Result: LOSE!");
        } else {
            message = "DRAW! The game ended in a draw. (+3 poin)";
            lblStatus.setText("Result: DRAW!");
        }

        JOptionPane.showMessageDialog(this, message, "Game End", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetGame() {
        gameLogic.resetBoard();
        gameOver = false;
        lblStatus.setText("Your turn (X)");

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                buttons[r][c].setText("");
                buttons[r][c].setEnabled(true);
                buttons[r][c].setForeground(UIManager.getColor("Button.foreground"));
            }
        }
    }
}
