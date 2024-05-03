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
        if(linha < 0 || linha >= tamanho || coluna < 0 || coluna >= tamanho) {
            return false;
        }
        double radianos,theta = 0;
        int x1, x2, y1, y2, cos, sin = 0;
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

        if (tipo == 'H') {
            radianos = Math.toRadians(90); //Transforma graus em radianos
            theta = radianos*orientacao; //Quantos radianos quer rotacionar
            cos = (int) Math.cos(theta); //Calcula o cos de theta e passa para int
            sin = (int) Math.sin(theta); //Calcula o sen de theta e passa para int

            // Nosso "x" representa linha e "y" coluna
            //                                                    [] -> (x, y)
            //Valores de translatação do ponto    (x+1, y-1) <- []  [] -> (x+1, y+1)
            x1 = 1; //(x-x-1)
            y1 = -1; //(y-y-1)
            x2 = 1; //(x-x+1)
            y2 = 1; //(y-y+1)

            //Verica se está ou não na matriz
            if (((linha+((x1*cos)-(y1*sin))) > tamanho - 1) || ((linha+((x1*cos)-(y1*sin))) < 0)) {
                return false;
            }
            if (((linha+((x2*cos)-(y2*sin))) > tamanho - 1) || ((linha+((x2*cos)-(y2*sin))) < 0)) {
                return false;
            }
            if (((coluna+((x1*sin)+(y1*cos))) > tamanho - 1) || (coluna+((x1*sin)+(y1*cos)) < 0))  {
                return false;
            }
            if (((coluna+((x2*sin)+(y2*cos))) > tamanho - 1) || ((coluna+((x2*sin)+(y2*cos))) < 0)) {
                return false;
            } 
            
            //Verifica se já existe outro navio no local
            if ((tabuleiro[linha][coluna]) != '~') {
                return false;
            }
            if ((tabuleiro[linha+((x1*cos)-(y1*sin))][coluna+((x1*sin)+(y1*cos))]) != '~') {
                return false;
            }
            if ((tabuleiro[linha+((x2*cos)-(y2*sin))][coluna+((x2*sin)+(y2*cos))]) != '~') {
                return false;
            }

            //Altera matriz do tabuleiro
            //Aqui fazemos a rotação de fato, pela formula x' = x cos(theta) - y sen(theta) e y' = x sen(theta) + y cos(theta)
            tabuleiro[linha][coluna] = tipo;
            tabuleiro[linha+((x1*cos)-(y1*sin))][coluna+((x1*sin)+(y1*cos))] = tipo; 
            tabuleiro[linha+((x2*cos)-(y2*sin))][coluna+((x2*sin)+(y2*cos))] = tipo;

            return true;
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