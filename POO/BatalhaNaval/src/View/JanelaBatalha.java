package View;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Tabuleiro;

class JanelaBatalha extends JFrame {
    private boolean[] vezJogador1 = {true};

    public JanelaBatalha(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, String[] Nomes) {
        setTitle("Batalha Naval - Batalha");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel panelBatalha = new JPanel(new GridBagLayout());
        setSize(960, 550);

        JLabel ataqueLabel = new JLabel("");
        ataqueLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel jogadorLabel = new JLabel("Vez de " + Nomes[0]);
        jogadorLabel.setHorizontalAlignment(JLabel.CENTER);

        PainelTabuleiroBatalha painelBatalha1 = new PainelTabuleiroBatalha(tabuleiro1, tabuleiroOculto1, 2, vezJogador1, ataqueLabel, jogadorLabel, Nomes);
        PainelTabuleiroBatalha painelBatalha2 = new PainelTabuleiroBatalha(tabuleiro2, tabuleiroOculto2, 1, vezJogador1, ataqueLabel, jogadorLabel, Nomes);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelBatalha.add(painelBatalha1, gbc);

        gbc.gridx = 1;
        panelBatalha.add(painelBatalha2, gbc);

        add(panelBatalha, BorderLayout.CENTER);
        add(ataqueLabel, BorderLayout.SOUTH);
        add(jogadorLabel, BorderLayout.NORTH);

        setVisible(true);
    }
}
