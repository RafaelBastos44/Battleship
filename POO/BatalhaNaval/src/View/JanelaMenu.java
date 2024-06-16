package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import Model.*;

public class JanelaMenu extends JFrame {

    private Tabuleiro tabuleiro1;
    private Tabuleiro tabuleiro2;
    private Tabuleiro tabuleiroOculto1;
    private Tabuleiro tabuleiroOculto2;
    private Navio navioSelecionado;

    public JanelaMenu(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, Navio navioSelecionado) {
        this.tabuleiro1 = tabuleiro1;
        this.tabuleiro2 = tabuleiro2;
        this.tabuleiroOculto1 = tabuleiroOculto1;
        this.tabuleiroOculto2 = tabuleiroOculto2;
        this.navioSelecionado = navioSelecionado;

        setTitle("Batalha Naval");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centraliza a janela na tela

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;

        JLabel titleLabel = new JLabel("Batalha Naval", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panel.add(titleLabel, gbc);

        JButton novoJogoButton = new JButton("Novo Jogo");
        novoJogoButton.setPreferredSize(new Dimension(200, 50));
        novoJogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                novoJogo();
            }
        });

        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(novoJogoButton, gbc);

        JButton carregarJogoButton = new JButton("Carregar Jogo");
        carregarJogoButton.setPreferredSize(new Dimension(200, 50));
        carregarJogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarJogo();
            }
        });

        gbc.gridy = 2;
        panel.add(carregarJogoButton, gbc);

        add(panel);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void novoJogo() {
        // Abrir a janela de nomes para capturar os nomes dos jogadores
        JanelaNomes janelaNomes = new JanelaNomes();
        String[] nomes = {janelaNomes.getJogador1(), janelaNomes.getJogador2()};

        // Abrir a janela de posicionamento com os nomes capturados
        JanelaPosicionamento janelaPosicionamento = new JanelaPosicionamento(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, navioSelecionado, 1, nomes);
        janelaPosicionamento.setVisible(true);
        this.dispose(); // Fechar a janela atual
    }

    private void carregarJogo() {
        try {
            String[] nomes = {"Jogador 1", "Jogador 2"};
            Object[] estado = Model.Salvamento.lerEstadoJogo(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, nomes);
            boolean turno = (boolean) estado[0];
            int numeroDoAtaque = (int) estado[1];

            JOptionPane.showMessageDialog(this, "Jogo carregado! Turno: " + (turno ? "Jogador 1" : "Jogador 2") + ", Número do Ataque: " + numeroDoAtaque);
            JanelaBatalha janelaBatalha = new JanelaBatalha(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, nomes, turno, numeroDoAtaque);
            janelaBatalha.setVisible(true);
            this.dispose(); // Fechar a janela atual
        } catch (FileNotFoundException | NoSuchElementException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar o jogo: " + e.getMessage());
        }
    }
}
