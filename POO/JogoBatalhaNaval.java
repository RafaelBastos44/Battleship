import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class JogoBatalhaNaval {
    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        Tabuleiro tabuleiroJogador1 = new Tabuleiro(10);
        Tabuleiro tabuleiroJogador2 = new Tabuleiro(10);
        int turno = 1; // 1 para jogador 1, 2 para jogador 2

        try {
            turno = lerEstadoJogo(tabuleiroJogador1, tabuleiroJogador2);
        } catch (FileNotFoundException e) {
            System.out.println("Nenhum jogo salvo encontrado, começando um novo jogo.");
            System.out.println("Jogador 1, posicione seus navios.");
            inicializa(tabuleiroJogador1);
            System.out.println("Jogador 2, posicione seus navios.");
            inicializa(tabuleiroJogador2);
        } catch (NoSuchElementException | NumberFormatException e) {
            System.out.println("Arquivo de jogo corrompido, começando um novo jogo.");
            System.out.println("Jogador 1, posicione seus navios.");
            inicializa(tabuleiroJogador1);
            System.out.println("Jogador 2, posicione seus navios.");
            inicializa(tabuleiroJogador2);
        }

        // boolean acerto;
        // while (true) {
        //     System.out.println("Vez do Jogador " + turno);
        //     System.out.print("Digite a linha e a coluna para atacar: ");
        //     int linha = scanner.nextInt();
        //     int coluna = scanner.nextInt();

        //     if (turno == 1) {
        //         acerto = tabuleiroJogador2.atacar(linha, coluna);
        //         tabuleiroJogador2.exibirTabuleiro();
        //     } else {
        //         acerto = tabuleiroJogador1.atacar(linha, coluna);
        //         tabuleiroJogador1.exibirTabuleiro();
        //     }

        //     gravarEstadoJogo(tabuleiroJogador1, tabuleiroJogador2, turno);

        //     if (!acerto) {
        //         turno = (turno == 1) ? 2 : 1;
        //     }
        // }
    }

    public static void inicializa(Tabuleiro tabuleiro)
    {
        Scanner scanner = new Scanner(System.in);
        String x, y, tipo, orientacao;
        int numCouracado = 1; //1
        int numCruzador = 1; //2
        int numDestroyer = 1; //3
        int numHidroaviao = 1; //5
        int numSubmarino = 1; //4

        while(numCouracado + numCruzador + numDestroyer + numHidroaviao + numSubmarino > 0)
        {
            System.out.println("Voce tem:\n");
            System.out.println(numCouracado + " Couracado(s), cada um ocupa 5 quadrados");
            System.out.println(numCruzador + " Cruzador(es), cada um ocupa 4 quadrados");
            System.out.println(numDestroyer + " Destroyer(s), cada um ocupa 2 quadrados");
            System.out.println(numSubmarino + " Submarino(s), cada um ocupa 1 quadrado");
            System.out.println(numHidroaviao + " Hidroaviao(s), cada um ocupa 3 quadrados em formato triangular\n");

            do {
                System.out.println("Digite o tipo do navio.");
                tipo = scanner.nextLine().toUpperCase();
            }
            while ((!tipo.equals("C") || tipo.equals("C") && numCouracado == 0) &&
            (!tipo.equals("D") || tipo.equals("D") && numDestroyer == 0) &&
            (!tipo.equals("S") || tipo.equals("S") && numSubmarino == 0) &&
            (!tipo.equals("H") || tipo.equals("H") && numHidroaviao == 0) &&
            (!tipo.equals("G") || tipo.equals("G") && numCruzador == 0));

            System.out.println("Digite a coordenada x do navio.");
            x = scanner.nextLine();
            System.out.println("Digite a coordenada y do navio.");
            y = scanner.nextLine();
            System.out.println("Digite a orientacao do navio. (0,1,2,3)");
            orientacao = scanner.nextLine();

            if(tabuleiro.posicionarNavio(Integer.parseInt(x), Integer.parseInt(y), tipo.charAt(0), Integer.parseInt(orientacao)))
            {
                if(tipo.equals("C") && numCouracado > 0)
                {
                    numCouracado--;
                }
                else if(tipo.equals("D") && numDestroyer > 0)
                {
                    numDestroyer--;
                }
                else if(tipo.equals("S") && numSubmarino > 0)
                {
                    numSubmarino--;
                }
                else if(tipo.equals("H") && numHidroaviao > 0)
                {
                    numHidroaviao--;
                }
                else if(tipo.equals("G") && numCruzador > 0)
                {
                    numCruzador--;
                }
            }
            else
            {
                System.out.println("Posicao invalida, tente novamente.");
            }
            
            tabuleiro.exibirTabuleiro();
        }

        //scanner.close();
    }

    private static int lerEstadoJogo(Tabuleiro tabuleiroJogador1, Tabuleiro tabuleiroJogador2) throws FileNotFoundException, NoSuchElementException {
        File file = new File("estado_jogo.txt");
        Scanner fileScanner = new Scanner(file);
        String input;
        int turno = 1;

        input = fileScanner.nextLine(); // Lê "Tabuleiro Jogador 1:"
        for (int i = 0; i < tabuleiroJogador1.getTamanho(); i++) {
            input = fileScanner.nextLine();
            for (int j = 0; j < tabuleiroJogador1.getTamanho(); j++) {
                tabuleiroJogador1.setCelula(i, j, input.charAt(j * 2));
            }
        }

        fileScanner.nextLine(); // Lê espaço em branco
        input = fileScanner.nextLine(); // Lê "Tabuleiro Jogador 2:"
        for (int i = 0; i < tabuleiroJogador2.getTamanho(); i++) {
            input = fileScanner.nextLine();
            for (int j = 0; j < tabuleiroJogador2.getTamanho(); j++) {
                tabuleiroJogador2.setCelula(i, j, input.charAt(j * 2));
            }
        }

        fileScanner.nextLine(); // Lê espaço em branco
        input = fileScanner.nextLine(); // Lê "Turno: Jogador X"
        turno = Integer.parseInt(input.split(" ")[2]);

        fileScanner.close();
        return turno;
    }

    private static void gravarEstadoJogo(Tabuleiro tabuleiroJogador1, Tabuleiro tabuleiroJogador2, int turno) {
        try {
            FileWriter writer = new FileWriter("estado_jogo.txt");
            writer.write("Tabuleiro Jogador 1:\n");
            writer.write(tabuleiroParaString(tabuleiroJogador1) + "\n\n");
            writer.write("Tabuleiro Jogador 2:\n");
            writer.write(tabuleiroParaString(tabuleiroJogador2) + "\n\n");
            writer.write("Turno: Jogador " + turno + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String tabuleiroParaString(Tabuleiro tabuleiro) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            for (int j = 0; j < tabuleiro.getTamanho(); j++) {
                sb.append(tabuleiro.getCelula(i, j)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
