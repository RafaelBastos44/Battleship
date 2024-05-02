public class Tabuleiro {
    private char[][] tabuleiro;
    private int tamanho;

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        tabuleiro = new char[tamanho][tamanho];
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro[i][j] = '~'; // Água
            }
        }
    }

    public void posicionarNavio(int linha, int coluna, int tamanho, char tipo, boolean horizontal) {
        if (horizontal) {
            for (int i = 0; i < tamanho; i++) {
                if (coluna + i < this.tamanho) {
                    tabuleiro[linha][coluna + i] = tipo;
                }
            }
        } else {
            for (int i = 0; i < tamanho; i++) {
                if (linha + i < this.tamanho) {
                    tabuleiro[linha + i][coluna] = tipo;
                }
            }
        }
    }

    public boolean atacar(int linha, int coluna) {
        char cell = tabuleiro[linha][coluna];
        if (cell != '~' && cell != 'X' && cell != 'O') {
            tabuleiro[linha][coluna] = 'X'; // Acertou um navio
            return true;
        } else if (cell == '~') {
            tabuleiro[linha][coluna] = 'O'; // Tiro na água
        }
        return false;
    }

    public void exibirTabuleiro() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getTamanho() {
        return tamanho;
    }

    public char getCelula(int i, int j) {
        return tabuleiro[i][j];
    }

    public void setCelula(int i, int j, char valor) {
        tabuleiro[i][j] = valor;
    }
}
