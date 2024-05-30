package Controller;

import Model.Tabuleiro;
import View.JanelaPosicionamento;
import View.JanelaNomes;
import View.JanelaBatalha;
import View.PainelBatalha;
import View.ComponenteTabuleiro;
import View.JanelaFimDeJogo;

public class BatalhaNaval {
    private Tabuleiro tabuleiroJogador1;
    private Tabuleiro tabuleiroJogador2;
    private Tabuleiro tabuleiroOculto1;
    private Tabuleiro tabuleiroOculto2;
    private String nomeJogador1;
    private String nomeJogador2;
    private boolean vezJogador1 = true;
    private int ataquesRestantes = 3;
    private JanelaBatalha janelaBatalha;

    // Armazenar referências dos componentes de tabuleiro
    private ComponenteTabuleiro componenteTabuleiroOcultoJogador1;
    private ComponenteTabuleiro componenteTabuleiroOcultoJogador2;

    public BatalhaNaval() {
        tabuleiroJogador1 = new Tabuleiro(15);
        tabuleiroJogador2 = new Tabuleiro(15);
        JanelaNomes janelaNomes = new JanelaNomes();

        nomeJogador1 = janelaNomes.getJogador1();
        nomeJogador2 = janelaNomes.getJogador2();

        JanelaPosicionamento janelaPosicionamento = new JanelaPosicionamento(tabuleiroJogador1, tabuleiroJogador2, nomeJogador1, nomeJogador2, this::iniciarBatalha);
        janelaPosicionamento.setSize(680, 550);

        JanelaBatalha janelaBatalha = new JanelaBatalha(tabuleiroJogador1, tabuleiroJogador2, tabuleiroOculto1, tabuleiroOculto2, nomeJogador1, nomeJogador2, this);
        janelaBatalha.setSize(960, 580);
        janelaBatalha.setVisible(false);
    }

    private void iniciarBatalha(Void v) {
        tabuleiroOculto1 = new Tabuleiro(15);
        tabuleiroOculto2 = new Tabuleiro(15);

        janelaBatalha = new JanelaBatalha(tabuleiroJogador1, tabuleiroJogador2, tabuleiroOculto1, tabuleiroOculto2, nomeJogador1, nomeJogador2, this);
        janelaBatalha.setSize(960, 570);

        PainelBatalha painelBatalha = janelaBatalha.getPainelBatalha();
        componenteTabuleiroOcultoJogador1 = painelBatalha.getComponenteTabuleiroBatalhaJogador1();
        componenteTabuleiroOcultoJogador2 = painelBatalha.getComponenteTabuleiroBatalhaJogador2();

        componenteTabuleiroOcultoJogador1.setHabilitado(!vezJogador1);
        componenteTabuleiroOcultoJogador2.setHabilitado(vezJogador1);

        // Passar a referência da janela para o controlador
        janelaBatalha.setVisible(true);
    }

    public void realizarAtaque(int linha, int coluna) {
        if (ataquesRestantes > 0) {
            System.out.println("Antes do ataque: vezJogador1 = " + vezJogador1); // Depuração
    
            Tabuleiro tabuleiroAtacado = vezJogador1 ? tabuleiroJogador2 : tabuleiroJogador1;
            Tabuleiro tabuleiroOculto = vezJogador1 ? tabuleiroOculto2 : tabuleiroOculto1;
    
            String resultado = tabuleiroAtacado.atacar(linha, coluna, tabuleiroOculto);
            if (resultado.equals("Tiro já realizado nesta posição.")){
                janelaBatalha.getPainelBatalha().onAtaque(linha, coluna, resultado, ataquesRestantes);
                return;
            }
            
            ataquesRestantes--; // Decrementa o contador de ataques

            // Verificar fim de jogo ou fim do turno
            if (tabuleiroAtacado.todosNaviosAfundados(tabuleiroAtacado, tabuleiroOculto)) {
                String vencedor = vezJogador1 ? nomeJogador1 : nomeJogador2;

                // Exibir a janela de fim de jogo
                Runnable jogarNovamenteAction = () -> {
                    new BatalhaNaval(); // Reiniciar o jogo
                    janelaBatalha.dispose();
                };

                JanelaFimDeJogo janelaFimDeJogo = new JanelaFimDeJogo(vencedor, jogarNovamenteAction);
                janelaFimDeJogo.setLocationRelativeTo(null); // Centralizar a janela
                janelaFimDeJogo.setVisible(true);

                // Encerrar a janela da batalha
                janelaBatalha.dispose();

            } else if (ataquesRestantes == 0) { // Alterna o turno se acabaram os ataques
                vezJogador1 = !vezJogador1; 
                ataquesRestantes = 3; 

                componenteTabuleiroOcultoJogador1.setHabilitado(!vezJogador1);
                componenteTabuleiroOcultoJogador2.setHabilitado(vezJogador1);
            }
            janelaBatalha.getPainelBatalha().onAtaque(linha, coluna, resultado, ataquesRestantes);

            // Notificar os listeners sobre o ataque (DEPOIS de alternar o turno E verificar o fim de jogo)
            System.out.println("Depois do ataque: vezJogador1 = " + vezJogador1); // Depuração
        } else {
        }
    }

    public boolean isVezJogador1() {
        return vezJogador1;
    }

    public int getAtaquesRestantes() {
        return ataquesRestantes;
    }

    public static void main(String[] args) {
        new BatalhaNaval();
    }
}
