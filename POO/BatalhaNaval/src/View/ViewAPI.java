package View;
import Model.Tabuleiro;
import Model.Navio;

public class ViewAPI {

    private static ViewAPI instance;
    private Navio navioSelecionado;

    private ViewAPI() {
        navioSelecionado = new Navio('N');
    }

    public static ViewAPI getInstance() {
        if (instance == null) {
            instance = new ViewAPI();
        }
        return instance;
    }

    public void inicializaNomes() {
        new JanelaNomes();
    }

    public void inicializaJogo(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2) {
        new JanelaPosicionamento(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, navioSelecionado,1);
        navioSelecionado.setSymbol('N');
    }
    
}
