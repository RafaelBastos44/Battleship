package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import Model.*;

public class JanelaMenu extends JFrame {

    private Tabuleiro tabuleiro1;
    private Tabuleiro tabuleiro2;
    private Tabuleiro tabuleiroOculto1;
    private Tabuleiro tabuleiroOculto2;
    private Navio navioSelecionado;
    private String[] nomes;

    public JanelaMenu(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, Navio navioSelecionado, String[] nomes) {
        this.tabuleiro1 = tabuleiro1;
        this.tabuleiro2 = tabuleiro2;
        this.tabuleiroOculto1 = tabuleiroOculto1;
        this.tabuleiroOculto2 = tabuleiroOculto2;
        this.navioSelecionado = navioSelecionado;
        this.nomes = nomes;

        setTitle("Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton novoJogoButton = new JButton("Novo Jogo");
        JButton carregarJogoButton = new JButton("Carregar Jogo");

        novoJogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                novoJogo();
            }
        });

        carregarJogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarJogo();
            }
        });

        panel.add(novoJogoButton);
        panel.add(carregarJogoButton);

        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void novoJogo() {
        // Abrir a janela de posicionamento
        JanelaPosicionamento janelaPosicionamento = new JanelaPosicionamento(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, navioSelecionado, 1, nomes);
        janelaPosicionamento.setVisible(true);
        this.dispose(); // Fechar a janela atual
    }

    private void carregarJogo() {
        // Lógica para carregar um jogo salvo
        try {
            Object[] estado = Model.Salvamento.lerEstadoJogo(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, nomes);
            boolean turno = (boolean) estado[0];
            int numeroDoAtaque = (int) estado[1];

            JOptionPane.showMessageDialog(this, "Jogo carregado! Turno: " + (turno ? "Jogador 1" : "Jogador 2") + ", Número do Ataque: " + numeroDoAtaque);
            // Abrir a janela de batalha com o estado carregado
            JanelaBatalha janelaBatalha = new JanelaBatalha(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, nomes, turno, numeroDoAtaque);
            janelaBatalha.setVisible(true);
            this.dispose(); // Fechar a janela atual
        } catch (FileNotFoundException | NoSuchElementException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar o jogo: " + e.getMessage());
        }
    }
}
