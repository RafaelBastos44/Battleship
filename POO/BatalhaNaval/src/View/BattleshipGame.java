package View;

import javax.swing.SwingUtilities;

public class BattleshipGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JanelaNomes();
        });
    }
}