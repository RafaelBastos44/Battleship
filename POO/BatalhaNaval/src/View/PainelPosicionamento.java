package View;

import javax.swing.*;
import java.awt.*;
import Model.Tabuleiro;

public class PainelPosicionamento extends JPanel {
    private ComponenteTabuleiro componenteTabuleiro;
    private ComponenteNavio componenteNavio;
    @SuppressWarnings("unused")
    private String nomeJogador;

    public PainelPosicionamento(Tabuleiro tabuleiro, JanelaPosicionamento janela, String nomeJogador) {
        this.nomeJogador = nomeJogador; // Inicialize o atributo com o nome do jogador
        componenteTabuleiro = new ComponenteTabuleiro(15, 30, tabuleiro, this);
        componenteNavio = new ComponenteNavio();

        setLayout(new BorderLayout());
        add(componenteTabuleiro, BorderLayout.CENTER);
        add(componenteNavio, BorderLayout.WEST);

        // Adicione um JLabel para exibir o nome do jogador
        JLabel labelNomeJogador = new JLabel(nomeJogador);
        labelNomeJogador.setHorizontalAlignment(JLabel.CENTER);
        add(labelNomeJogador, BorderLayout.NORTH);
    }

    public ComponenteNavio getComponenteNavio() {
        return componenteNavio;
    }

    public void resetNavios() {
        componenteNavio.resetContadores();
        componenteTabuleiro.repaint();
    }

    public void requestFocusForTabuleiro() {
        componenteTabuleiro.requestFocusInWindow();
    }

    public boolean todosNaviosPosicionados() {
        return componenteNavio.todosNaviosPosicionados();
    }

}
