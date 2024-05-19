package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private BoardPanel boardPanel;
    private ShipPanel shipPanel;
    private JButton readyButton;
    private boolean isPlayerOneTurn;
    private String playerOneName;
    private String playerTwoName;

    public GameWindow(String playerOneName, String playerTwoName) {
        super("Batalha Naval");

        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;

        // Initialize components
        shipPanel = new ShipPanel();
        boardPanel = new BoardPanel(shipPanel);
        readyButton = new JButton("Tabuleiro Pronto");
        isPlayerOneTurn = true; // Player 1 starts first

        // Layout setup
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(shipPanel, BorderLayout.WEST);
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        add(readyButton, BorderLayout.SOUTH);

        // Add action listener to the button
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardPanel.printGrid(); // Print the grid to the terminal
                if (isPlayerOneTurn) {
                    // Player 1 has finished positioning ships
                    isPlayerOneTurn = false;
                    boardPanel.clearGrid(); // Clear board for player 2
                    shipPanel.resetSelection(); // Reset ship selection for player 2
                    boardPanel.switchPlayer(); // Switch to player 2
                    readyButton.setText(playerTwoName + ": Tabuleiro Pronto");
                    JOptionPane.showMessageDialog(GameWindow.this, playerTwoName + ", posicione seus navios.");
                    boardPanel.requestFocusInWindow(); // Ensure the focus is on the boardPanel
                } else {
                    // Player 2 has finished positioning ships
                    readyButton.setEnabled(false);
                    // Proceed to the next part of the game (e.g., starting the battle phase)
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550); // Adjust size as needed
        setVisible(true);

        // Ensure the focus is on the boardPanel initially
        boardPanel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        // Example names, replace with actual player names
        new GameWindow("Jogador 1", "Jogador 2");
    }
}
