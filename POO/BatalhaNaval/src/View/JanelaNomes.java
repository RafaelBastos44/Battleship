package View;

import javax.swing.*;

class JanelaNomes {
    private String jogador1;
    private String jogador2;

    public JanelaNomes() {
        inicializaNomes();
    }

    private void inicializaNomes() {
        jogador1 = JOptionPane.showInputDialog(null, "Nome do jogador 1:", "Nome 1", JOptionPane.QUESTION_MESSAGE);
        if (jogador1 == null || jogador1.isEmpty()) {
            jogador1 = "Jogador 1";
        }

        jogador2 = JOptionPane.showInputDialog(null, "Nome do jogador 2:", "Nome 2", JOptionPane.QUESTION_MESSAGE);
        if (jogador2 == null || jogador2.isEmpty()) {
            jogador2 = "Jogador 2";
        }
    }

    public String getJogador1() {
        return jogador1;
    }

    public String getJogador2() {
        return jogador2;
    }
}
