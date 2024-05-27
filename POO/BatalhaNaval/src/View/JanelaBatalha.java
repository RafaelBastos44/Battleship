package View;

import javax.swing.*;
import java.awt.*;
import Model.Tabuleiro;

public class JanelaBatalha extends JFrame {
    private PainelBatalha painelBatalha;

    public JanelaBatalha(Tabuleiro tabuleiroJogador1, Tabuleiro tabuleiroJogador2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, String nomeJogador1, String nomeJogador2) {
        setTitle("Batalha Naval - Batalha");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        painelBatalha = new PainelBatalha(tabuleiroJogador1, tabuleiroJogador2, tabuleiroOculto1, tabuleiroOculto2, nomeJogador1, nomeJogador2);
        add(painelBatalha, BorderLayout.CENTER);

        setSize(800, 600);  // Define o tamanho da janela de batalha
        setLocationRelativeTo(null);  // Centraliza a janela na tela
        setVisible(true);
    }
}
