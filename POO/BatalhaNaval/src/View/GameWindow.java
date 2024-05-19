package View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("Batalha Naval");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        ShipPanel shipPanel = new ShipPanel();
        BoardPanel boardPanel = new BoardPanel(shipPanel);

        mainPanel.add(shipPanel, BorderLayout.WEST);
        mainPanel.add(boardPanel, BorderLayout.CENTER);
    }
}
