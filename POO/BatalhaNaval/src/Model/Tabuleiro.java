package Model;

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
        if (linha < 0 || linha >= tamanho || coluna < 0 || coluna >= tamanho) {
            return false;
        }

        int tamanhoNavio;
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
                return posicionarHidroaviao(linha, coluna, orientacao);
            case 'G':
                tamanhoNavio = 5;
                break;
            default:
                return false;
        }

        switch (orientacao) {
            case 0:
                if (coluna + tamanhoNavio > tamanho) {
                    return false;
                }
                for (int i = 0; i < tamanhoNavio; i++) {
                    if (tabuleiro[linha][coluna + i] != '~') {
                        return false;
                    }
                }
                for (int i = 0; i < tamanhoNavio; i++) {
                    tabuleiro[linha][coluna + i] = tipo;
                }
                break;
            case 1:
                if (linha + tamanhoNavio > tamanho) {
                    return false;
                }
                for (int i = 0; i < tamanhoNavio; i++) {
                    if (tabuleiro[linha + i][coluna] != '~') {
                        return false;
                    }
                }
                for (int i = 0; i < tamanhoNavio; i++) {
                    tabuleiro[linha + i][coluna] = tipo;
                }
                break;
            case 2:
                if (coluna - tamanhoNavio < -1) {
                    return false;
                }
                for (int i = 0; i < tamanhoNavio; i++) {
                    if (tabuleiro[linha][coluna - i] != '~') {
                        return false;
                    }
                }
                for (int i = 0; i < tamanhoNavio; i++) {
                    tabuleiro[linha][coluna - i] = tipo;
                }
                break;
            case 3:
                if (linha - tamanhoNavio < -1) {
                    return false;
                }
                for (int i = 0; i < tamanhoNavio; i++) {
                    if (tabuleiro[linha - i][coluna] != '~') {
                        return false;
                    }
                }
                for (int i = 0; i < tamanhoNavio; i++) {
                    tabuleiro[linha - i][coluna] = tipo;
                }
                break;
            default:
                return false;
        }

        return true;
    }

    private boolean posicionarHidroaviao(int linha, int coluna, int orientacao) {
        double radianos, theta;
        int x1, x2, y1, y2, cos, sin;

        radianos = Math.toRadians(90); // Transforma graus em radianos
        theta = radianos * orientacao; // Quantos radianos quer rotacionar
        cos = (int) Math.cos(theta); // Calcula o cos de theta e passa para int
        sin = (int) Math.sin(theta); // Calcula o sen de theta e passa para int

        // Valores de translatação do ponto
        x1 = 1; // (x-x-1)
        y1 = -1; // (y-y-1)
        x2 = 1; // (x-x+1)
        y2 = 1; // (y-y+1)

        // Verifica se está ou não na matriz
        if (linha + ((x1 * cos) - (y1 * sin)) > tamanho - 1 || linha + ((x1 * cos) - (y1 * sin)) < 0) {
            return false;
        }
        if (linha + ((x2 * cos) - (y2 * sin)) > tamanho - 1 || linha + ((x2 * cos) - (y2 * sin)) < 0) {
            return false;
        }
        if (coluna + ((x1 * sin) + (y1 * cos)) > tamanho - 1 || coluna + ((x1 * sin) + (y1 * cos)) < 0) {
            return false;
        }
        if (coluna + ((x2 * sin) + (y2 * cos)) > tamanho - 1 || coluna + ((x2 * sin) + (y2 * cos)) < 0) {
            return false;
        }

        // Verifica se já existe outro navio no local
        if (tabuleiro[linha][coluna] != '~') {
            return false;
        }
        if (tabuleiro[linha + ((x1 * cos) - (y1 * sin))][coluna + ((x1 * sin) + (y1 * cos))] != '~') {
            return false;
        }
        if (tabuleiro[linha + ((x2 * cos) - (y2 * sin))][coluna + ((x2 * sin) + (y2 * cos))] != '~') {
            return false;
        }

        // Altera matriz do tabuleiro
        // Aqui fazemos a rotação de fato, pela fórmula x' = x cos(theta) - y sen(theta) e y' = x sen(theta) + y cos(theta)
        tabuleiro[linha][coluna] = 'H';
        tabuleiro[linha + ((x1 * cos) - (y1 * sin))][coluna + ((x1 * sin) + (y1 * cos))] = 'H';
        tabuleiro[linha + ((x2 * cos) - (y2 * sin))][coluna + ((x2 * sin) + (y2 * cos))] = 'H';

        return true;
    }

    public String atacar(int linha, int coluna, Tabuleiro tabuleiroOculto) {
        if (linha < 0 || linha >= tamanho || coluna < 0 || coluna >= tamanho) {
            return "";
        }

        char cell = tabuleiro[linha][coluna];
        char celOculto = tabuleiroOculto.getCelula(linha, coluna);
        if (cell != '~' && celOculto != 'X') {
            tabuleiroOculto.setCelula(linha, coluna, 'X');
            switch (cell) {
                case 'G':
                    return "Couraçado atingido!";
                case 'S':
                    return "Submarino atingido!";
                case 'D':
                    return "Destroyer atingido!";
                case 'C':
                    return "Cruzador atingido!";
                case 'H':
                    return "Hidroavião atingido!";
            }
        } else if (cell == '~' && celOculto != 'O') {
            tabuleiroOculto.setCelula(linha, coluna, 'O');
            return "Tiro na água.";
        }
        return "";
    }

    public void exibirTabuleiro() {
        for (int i = 0; i < tamanho; i++) {
            System.out.print((char) ('A' + i) + " - ");
            for (int j = 0; j < tamanho; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public boolean todosNaviosAfundados(Tabuleiro tabuleiroOculto) {
        boolean fim = true;
        for (int i = 0; i < getTamanho(); i++) {
            for (int j = 0; j < getTamanho(); j++) {
                if(getCelula(i, j) != '~' && tabuleiroOculto.getCelula(i, j) != 'X')
                {
                    fim = false;
                }
            }
        }
        return fim;
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

    public char[][] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(char[][] tabuleiro) {
        this.tabuleiro = new char[tabuleiro.length][];
        for (int i = 0; i < tabuleiro.length; i++) {
            this.tabuleiro[i] = tabuleiro[i].clone();
        }
    }

    public void limpar() {
        for (int linha = 0; linha < 15; linha++) {
            for (int coluna = 0; coluna < 15; coluna++) {
                // Redefinir o estado da célula para o valor inicial
                tabuleiro[linha][coluna] = '~'; // Substitua 'AGUA' pelo valor que você usa
            }
        }
    }
}
