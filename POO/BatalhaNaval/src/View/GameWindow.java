package View;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private String jogador1;
    private String jogador2;

    public GameWindow(String jogador1, String jogador2) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;

        setTitle("Batalha Naval");
        setSize(780, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        ShipPanel shipPanel = new ShipPanel();
        BoardPanel boardPanel = new BoardPanel(shipPanel);

        mainPanel.add(shipPanel, BorderLayout.WEST);
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel instructionLabel = new JLabel(jogador1 + ", selecione uma arma na lista.");
        JButton readyButton = new JButton("Tabuleiro Pronto!");

        bottomPanel.add(instructionLabel, BorderLayout.CENTER);
        bottomPanel.add(readyButton, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}
