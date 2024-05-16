package View;
import javax.swing.*;

public class JanelaNomes {
    private String jogador1;
    private String jogador2;

    public JanelaNomes() {
        inicializaNomes();
    }

    private void inicializaNomes() {

        jogador1 = JOptionPane.showInputDialog(null, "Nome do jogador 1:","Nome 1", JOptionPane.QUESTION_MESSAGE);
        if (jogador1 == null || jogador1.isEmpty()) { //caso clique somente no ok o nome por padrão vai ser jogador 1
            jogador1 = "Jogador 1";
        }

        jogador2 = JOptionPane.showInputDialog(null, "Nome do jogador 2:","Nome 2", JOptionPane.QUESTION_MESSAGE);
        if (jogador2 == null || jogador2.isEmpty()) { //caso clique somente no ok o nome por padrão vai ser jogador 2
            jogador2 = "Jogador 2";
        }

        System.out.println("Jogador 1: " + jogador1);
        System.out.println("Jogador 2: " + jogador2);
    }

    public static void main(String[] args) {
        new JanelaNomes();
    }
}