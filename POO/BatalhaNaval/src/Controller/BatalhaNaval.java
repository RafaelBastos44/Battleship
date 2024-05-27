package Controller;

import Model.Tabuleiro;
import View.JanelaPosicionamento;
import View.JanelaNomes;
import View.JanelaBatalha;

import javax.swing.*;

public class BatalhaNaval {
    private Tabuleiro tabuleiroJogador1;
    private Tabuleiro tabuleiroJogador2;
    private String nomeJogador1;
    private String nomeJogador2;

    public BatalhaNaval() {
        tabuleiroJogador1 = new Tabuleiro(15);
        tabuleiroJogador2 = new Tabuleiro(15);
        JanelaNomes janelaNomes = new JanelaNomes();

        nomeJogador1 = janelaNomes.getJogador1();
        nomeJogador2 = janelaNomes.getJogador2();

        JanelaPosicionamento janelaPosicionamento = new JanelaPosicionamento(tabuleiroJogador1, tabuleiroJogador2, nomeJogador1, nomeJogador2, this::iniciarBatalha);
        janelaPosicionamento.setSize(680, 550);  // Tamanho da janela de posicionamento
    }

    private void iniciarBatalha(Void v) {
        Tabuleiro tabuleiroOculto1 = new Tabuleiro(15); // Tabuleiro oculto do jogador 1
        Tabuleiro tabuleiroOculto2 = new Tabuleiro(15); // Tabuleiro oculto do jogador 2

        JanelaBatalha janelaBatalha = new JanelaBatalha(tabuleiroJogador1, tabuleiroJogador2, tabuleiroOculto1, tabuleiroOculto2, nomeJogador1, nomeJogador2);
        janelaBatalha.setSize(960, 580);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BatalhaNaval());
    }
}
