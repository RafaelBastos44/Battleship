package Model;

public class ModelAPI {
    private static ModelAPI instance;
    private static Tabuleiro tabuleiroJogador1;
    private static Tabuleiro tabuleiroJogador2;
    private static Tabuleiro tabuleiroOculto1;
    private static Tabuleiro tabuleiroOculto2;

    private ModelAPI() {
        ModelAPI.tabuleiroJogador1 = new Tabuleiro(15);
        ModelAPI.tabuleiroJogador2 = new Tabuleiro(15);
        ModelAPI.tabuleiroOculto1 = new Tabuleiro(15);
        ModelAPI.tabuleiroOculto2 = new Tabuleiro(15);
    }
    
    public static ModelAPI getInstance() {
        if (instance == null) {
            instance = new ModelAPI();
        }
        return instance;
    }

    public Tabuleiro getTabuleiro1() {
        return tabuleiroJogador1;
    }

    public Tabuleiro getTabuleiro2() {
        return tabuleiroJogador2;
    }

    public Tabuleiro getTabuleiroOculto1() {
        return tabuleiroOculto1;
    }

    public Tabuleiro getTabuleiroOculto2() {
        return tabuleiroOculto2;
    }
}
