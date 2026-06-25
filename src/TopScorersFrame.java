import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class TopScorersFrame extends JFrame {

    public TopScorersFrame() {
        setTitle("Top 5 Scorers");
        setSize(500, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("🏆 Top 5 Scorers", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(lblTitle, BorderLayout.NORTH);

        String[] columns = {"Rank", "Username", "Wins", "Losses", "Draws", "Score"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        PlayerService     service  = new PlayerService();
        ArrayList<Player> topList  = service.getTopFiveScorers();

        int rank = 1;
        for (Player p : topList) {
            tableModel.addRow(new Object[]{
                    rank,
                    p.getUsername(),
                    p.getWins(),
                    p.getLosses(),
                    p.getDraws(),
                    p.getScore()
            });
            rank++;
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(55);
        table.getColumnModel().getColumn(3).setPreferredWidth(55);
        table.getColumnModel().getColumn(4).setPreferredWidth(55);
        table.getColumnModel().getColumn(5).setPreferredWidth(60);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Refresh");
        JButton btnClose   = new JButton("Exit");

        btnClose.addActionListener(e -> dispose());
        btnRefresh.addActionListener(e -> {

            tableModel.setRowCount(0);
            ArrayList<Player> refreshed = service.getTopFiveScorers();
            int r = 1;
            for (Player p : refreshed) {
                tableModel.addRow(new Object[]{r, p.getUsername(), p.getWins(), p.getLosses(), p.getDraws(), p.getScore()});
                r++;
            }
        });

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(btnRefresh);
        southPanel.add(btnClose);
        panel.add(southPanel, BorderLayout.SOUTH);

        add(panel);
    }
}