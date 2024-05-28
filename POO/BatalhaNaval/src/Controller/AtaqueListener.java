package Controller;

public interface AtaqueListener {
    void onAtaque(int linha, int coluna, String resultado, int ataquesRestantes); // Adicionado o parâmetro ataquesRestantes
}
