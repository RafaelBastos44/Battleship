package View;
import Model.Tabuleiro;

import java.awt.Frame;

import Model.ModelAPI;
import Model.Navio;

public class ViewAPI {

    private static ViewAPI instance;
    private Navio navioSelecionado;

    ViewAPI() {
        navioSelecionado = new Navio('N');
    }

    public static ViewAPI getInstance() {
        if (instance == null) {
            instance = new ViewAPI();
        }
        return instance;
    }

    public String[] inicializaNomes() {
        JanelaNomes janela = new JanelaNomes();
        String nomes[] = new String[2];
        nomes[0] = janela.getJogador1();
        nomes[1] = janela.getJogador2();

        return nomes;
    }

    public void inicializaJogo(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, String[] Nomes) {
        new JanelaPosicionamento(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, navioSelecionado,1, Nomes);
        navioSelecionado.setSymbol('N');
    }
    
}
