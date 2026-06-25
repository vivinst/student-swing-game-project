import javax.swing.*;
import java.awt.*;

public class StatisticsFrame extends JFrame {

    public StatisticsFrame(Player player) {

        PlayerService service      = new PlayerService();
        Player        freshPlayer  = service.getPlayerById(player.getId());
        if (freshPlayer == null) freshPlayer = player; // fallback jika DB error

        setTitle("My Statistics");
        setSize(320, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JLabel lblTitle = new JLabel("Statistics: " + freshPlayer.getUsername(), SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(lblTitle, BorderLayout.NORTH);


        JPanel statsPanel = new JPanel(new GridLayout(5, 2, 10, 8));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Game Data"));

        statsPanel.add(new JLabel("WIN:"));
        statsPanel.add(new JLabel(String.valueOf(freshPlayer.getWins())));

        statsPanel.add(new JLabel("LOSE:"));
        statsPanel.add(new JLabel(String.valueOf(freshPlayer.getLosses())));

        statsPanel.add(new JLabel("DRAW:"));
        statsPanel.add(new JLabel(String.valueOf(freshPlayer.getDraws())));

        int totalGames = freshPlayer.getWins() + freshPlayer.getLosses() + freshPlayer.getDraws();
        statsPanel.add(new JLabel("Total Game:"));
        statsPanel.add(new JLabel(String.valueOf(totalGames)));

        statsPanel.add(new JLabel("Total Score:"));
        JLabel lblScore = new JLabel(String.valueOf(freshPlayer.getScore()));
        lblScore.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblScore.setForeground(new Color(0, 120, 50));
        statsPanel.add(lblScore);

        panel.add(statsPanel, BorderLayout.CENTER);


        JLabel lblInfo = new JLabel(
                "<html><center><small>WIN = +10 poin &nbsp;|&nbsp; DRAW = +3 poin &nbsp;|&nbsp; LOSE = +0 poin</small></center></html>",
                SwingConstants.CENTER
        );
        lblInfo.setForeground(Color.GRAY);

        JButton btnClose = new JButton("Exit");
        btnClose.addActionListener(e -> dispose());

        JPanel southPanel = new JPanel(new BorderLayout(5, 5));
        southPanel.add(lblInfo,   BorderLayout.NORTH);
        southPanel.add(btnClose,  BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        add(panel);
    }
}