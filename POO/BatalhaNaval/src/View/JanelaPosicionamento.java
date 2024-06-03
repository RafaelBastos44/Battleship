package View;

import javax.swing.*;

import Model.Navio;
import Model.Tabuleiro;

import java.awt.*;
// import java.awt.event.*;

class JanelaPosicionamento extends JFrame {
    private JButton btnTrocarJogador;
    private Navio navioSelecionado;
    private Tabuleiro tabuleiro1;
    private Tabuleiro tabuleiro2;
    private Tabuleiro tabuleiroOculto1;
    private Tabuleiro tabuleiroOculto2;
    private int[] contadores = {0, 0, 0, 0, 0};
    private String[] nomes;

    public JanelaPosicionamento(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, Navio navioSelecionado, int jogador, String[] Nomes) {
        this.tabuleiro1 = tabuleiro1;
        this.tabuleiro2 = tabuleiro2;
        this.tabuleiroOculto1 = tabuleiroOculto1;
        this.tabuleiroOculto2 = tabuleiroOculto2;
        this.navioSelecionado = navioSelecionado;
        this.nomes = Nomes;

        setSize(700,580);
        setLayout(new BorderLayout());
        setTitle("Batalha Naval - Posicionamento - Jogador " + jogador);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        PainelNavios painelNavios = new PainelNavios(navioSelecionado, contadores);

        JLabel labelNomeJogador = new JLabel(Nomes[jogador-1]);
        labelNomeJogador.setHorizontalAlignment(JLabel.CENTER);
        add(labelNomeJogador, BorderLayout.NORTH);

        if (jogador == 1) {
            PainelTabuleiro painelTabuleiro = new PainelTabuleiro(tabuleiro1, navioSelecionado, contadores);
            add(painelNavios);
            add(painelTabuleiro, BorderLayout.EAST);
        }
        else {
            PainelTabuleiro painelTabuleiro = new PainelTabuleiro(tabuleiro2, navioSelecionado, contadores);
            add(painelNavios);
            add(painelTabuleiro, BorderLayout.EAST);
        }

        if (jogador == 1) {
            btnTrocarJogador = new JButton("Trocar para o jogador 2");
            btnTrocarJogador.addActionListener(e -> abrirSegundaJanela());
        }
        else {
            btnTrocarJogador = new JButton("Começar o jogo!");
            btnTrocarJogador.addActionListener(e -> abrirJanelaAtaque());
        }
        add(btnTrocarJogador, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void abrirSegundaJanela() {
        if(contadores[0] < 1 || contadores[1] < 1 || contadores[2] < 0 || contadores[3] < 0 || contadores[4] < 0) {
            return;
        }
        new JanelaPosicionamento(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, navioSelecionado, 2, nomes);
        this.dispose();
    }

    private void abrirJanelaAtaque() {
        new JanelaBatalha(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2);
        this.dispose();
    }
}
