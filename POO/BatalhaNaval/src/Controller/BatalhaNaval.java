package Controller;

import Model.Tabuleiro;
import View.JanelaPosicionamento;
import View.JanelaNomes;
import View.JanelaBatalha;
import View.PainelBatalha;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BatalhaNaval {
    private Tabuleiro tabuleiroJogador1;
    private Tabuleiro tabuleiroJogador2;
    private Tabuleiro tabuleiroOculto1;
    private Tabuleiro tabuleiroOculto2;
    private String nomeJogador1;
    private String nomeJogador2;
    private boolean vezJogador1 = true;
    private int ataquesRestantes = 3;
    private List<AtaqueListener> listeners = new ArrayList<>();
    private JanelaBatalha janelaBatalha;

    public BatalhaNaval() {
        tabuleiroJogador1 = new Tabuleiro(15);
        tabuleiroJogador2 = new Tabuleiro(15);
        JanelaNomes janelaNomes = new JanelaNomes();

        nomeJogador1 = janelaNomes.getJogador1();
        nomeJogador2 = janelaNomes.getJogador2();

        JanelaBatalha janelaBatalha = new JanelaBatalha(tabuleiroJogador1, tabuleiroJogador2, tabuleiroOculto1, tabuleiroOculto2, nomeJogador1, nomeJogador2, this);
        janelaBatalha.setSize(960, 580);
        janelaBatalha.setVisible(false);

        JanelaPosicionamento janelaPosicionamento = new JanelaPosicionamento(tabuleiroJogador1, tabuleiroJogador2, nomeJogador1, nomeJogador2, this::iniciarBatalha);
        janelaPosicionamento.setSize(680, 550);
    }

    private void iniciarBatalha(Void v) {
        tabuleiroOculto1 = new Tabuleiro(15);
        tabuleiroOculto2 = new Tabuleiro(15);

        janelaBatalha = new JanelaBatalha(tabuleiroJogador1, tabuleiroJogador2, tabuleiroOculto1, tabuleiroOculto2, nomeJogador1, nomeJogador2, this);
        janelaBatalha.setSize(960, 580);

        // Adicione o listener aqui, na classe JanelaBatalha
        adicionarAtaqueListener(janelaBatalha.getPainelBatalha());

        // Passar a referência da janela para o controlador
        this.setJanelaBatalha(janelaBatalha);
    }

    public void realizarAtaque(int linha, int coluna) {
        if (ataquesRestantes > 0) {
            System.out.println("Antes do ataque: vezJogador1 = " + vezJogador1); // Depuração
    
            Tabuleiro tabuleiroAtacado = vezJogador1 ? tabuleiroJogador2 : tabuleiroJogador1;
            Tabuleiro tabuleiroOculto = vezJogador1 ? tabuleiroOculto2 : tabuleiroOculto1;
    
            String resultado = tabuleiroAtacado.atacar(linha, coluna, tabuleiroOculto);
    
            ataquesRestantes--; // Decrementa o contador de ataques
    
            // Verificar fim de jogo ou fim do turno
            if (tabuleiroAtacado.todosNaviosAfundados(tabuleiroAtacado, tabuleiroOculto)) {
                String vencedor = vezJogador1 ? nomeJogador1 : nomeJogador2;
                JOptionPane.showMessageDialog(null, vencedor + " venceu a batalha!");
    
                // Encerrar a janela da batalha
                janelaBatalha.dispose();
    
                // Lógica para reiniciar o jogo (opcional)
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja jogar novamente?", "Fim de Jogo", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    // Reiniciar o jogo (chamar o construtor de BatalhaNaval novamente)
                    new BatalhaNaval();
                } else {
                    // Encerrar o programa
                    System.exit(0);
                }
            } else if (ataquesRestantes == 0) { // Alterna o turno se acabaram os ataques
                vezJogador1 = !vezJogador1; 
                ataquesRestantes = 3; 
            } 
    
            // Notificar os listeners sobre o ataque (DEPOIS de alternar o turno E verificar o fim de jogo)
            for (AtaqueListener listener : listeners) {
                listener.onAtaque(linha, coluna, resultado, ataquesRestantes);
            }
    
            System.out.println("Depois do ataque: vezJogador1 = " + vezJogador1); // Depuração
        } else {
            JOptionPane.showMessageDialog(null, "Você já realizou todos os ataques deste turno!");
        }
    }

    public void adicionarAtaqueListener(AtaqueListener listener) {
        listeners.add(listener);
    }

    public boolean isVezJogador1() {
        return vezJogador1;
    }

    public int getAtaquesRestantes() {
        return ataquesRestantes;
    }

    public void setJanelaBatalha(JanelaBatalha janelaBatalha) {
        this.janelaBatalha = janelaBatalha;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BatalhaNaval());
    }
}
