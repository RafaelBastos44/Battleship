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

    public boolean posicionarNavio(int linha, int coluna, char tipo, int orientacao) {
        int tamanhoNavio = 0;
        switch (tipo) {
            case 'S':
                tamanhoNavio = 1;
                break;
            case 'C':
                tamanhoNavio = 4;
                break;
            case 'D':
                tamanhoNavio = 2;
                break;
            case 'H':
                tamanhoNavio = 3;
                break;
            case 'G':
                tamanhoNavio = 5;
                break;
            default:
                return false;
        }

        if (orientacao < 0 || orientacao > 3) {
            return false;
        }

        if (orientacao == 0) {
            if (coluna + tamanhoNavio > tamanho) {
                return false;
            }
            for (int i = 0; i < tamanhoNavio; i++) {
                if (tabuleiro[linha][coluna + i] != '~') {
                    return false;
                }
            }
            for (int i = 0; i < tamanhoNavio; i++) {
                // Preenche o tabuleiro com o tipo do navio
                tabuleiro[linha][coluna + i] = tipo;
            }
        }

        else if (orientacao == 1) {
             if (linha + tamanhoNavio > tamanho) {
                return false;
            }
            for (int i = 0; i < tamanhoNavio; i++) {
                if (tabuleiro[linha + i][coluna] != '~') {
                    return false;
                }
            }
            for (int i = 0; i < tamanhoNavio; i++) {
                // Preenche o tabuleiro com o tipo do navio
                tabuleiro[linha + i][coluna] = tipo;
            }
        }
        
        else if (orientacao == 2) {
            if (coluna - tamanhoNavio < 0) {
                return false;
            }
            for (int i = 0; i < tamanhoNavio; i++) {
                if (tabuleiro[linha][coluna - i] != '~') {
                    return false;
                }
            }
            for (int i = 0; i < tamanhoNavio; i++) {
                // Preenche o tabuleiro com o tipo do navio
                tabuleiro[linha][coluna - i] = tipo;
            }
        }
        
        else if (orientacao == 3) {
            if (linha - tamanhoNavio < 0) {
                return false;
            }
            for (int i = 0; i < tamanhoNavio; i++) {
                if (tabuleiro[linha - i][coluna] != '~') {
                    return false;
                }
            }
            for (int i = 0; i < tamanhoNavio; i++) {
                // Preenche o tabuleiro com o tipo do navio
                tabuleiro[linha - i][coluna] = tipo;
            }
        }
        return true;
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
