import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class JogoBatalhaNaval {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Tabuleiro tabuleiroJogador1 = new Tabuleiro(10);
        Tabuleiro tabuleiroJogador2 = new Tabuleiro(10);
        int turno = 1; // 1 para jogador 1, 2 para jogador 2
        int ataque = 0;

        try {
            turno = lerEstadoJogo(tabuleiroJogador1, tabuleiroJogador2)[0];
            ataque = lerEstadoJogo(tabuleiroJogador1, tabuleiroJogador2)[1];
        } catch (FileNotFoundException e) {
            System.out.println("Nenhum jogo salvo encontrado, começando um novo jogo.");
            inicializa(tabuleiroJogador1, scanner);
            inicializa(tabuleiroJogador2, scanner);
        } catch (NoSuchElementException | NumberFormatException e) {
            System.out.println("Arquivo de jogo corrompido, começando um novo jogo.");
            inicializa(tabuleiroJogador1, scanner);
            inicializa(tabuleiroJogador2, scanner);
        }

        while (true) {
            if (ataque == 3){
                turno = (turno == 1) ? 2 : 1;
                ataque = 0;
            }
            System.out.println("Vez do Jogador " + turno);
            for(int i = ataque; i < 3; i++) { // Cada jogador joga 3 vezes
                System.out.print("Ataque " + (i + 1) + " - Digite a linha e a coluna para atacar: ");
                int linha = scanner.nextInt();
                int coluna = scanner.nextInt();

                if (turno == 1) {
                    tabuleiroJogador2.atacar(linha, coluna);
                    tabuleiroJogador2.exibirTabuleiro();
                } else {
                    tabuleiroJogador1.atacar(linha, coluna);
                    tabuleiroJogador1.exibirTabuleiro();
                }

                gravarEstadoJogo(tabuleiroJogador1, tabuleiroJogador2, turno, i+1);

                if (i == 2) { // Se não acertar no último ataque, troca de jogador
                    turno = (turno == 1) ? 2 : 1;
                    break; // Sair do loop de ataque
                }
            }
            ataque = 0;
        }
    }

    public static void inicializa(Tabuleiro tabuleiro, Scanner scanner) {
        String x, y, tipo, orientacao;
        int numCouracado = 1; //1
        int numCruzador = 0; //2
        int numDestroyer = 0; //3
        int numHidroaviao = 1; //5
        int numSubmarino = 0; //4

        while (numCouracado + numCruzador + numDestroyer + numHidroaviao + numSubmarino > 0) {
            System.out.println("Você tem:\n");
            System.out.println(numCouracado + " Couraçado(s), cada um ocupa 5 quadrados");
            System.out.println(numCruzador + " Cruzador(es), cada um ocupa 4 quadrados");
            System.out.println(numDestroyer + " Destroyer(s), cada um ocupa 2 quadrados");
            System.out.println(numSubmarino + " Submarino(s), cada um ocupa 1 quadrado");
            System.out.println(numHidroaviao + " Hidroavião(s), cada um ocupa 3 quadrados em formato triangular\n");

            do {
                System.out.println("Digite o tipo do navio.");
                tipo = scanner.nextLine().toUpperCase();
            } while ((!tipo.equals("C") || tipo.equals("C") && numCouracado == 0) &&
                    (!tipo.equals("D") || tipo.equals("D") && numDestroyer == 0) &&
                    (!tipo.equals("S") || tipo.equals("S") && numSubmarino == 0) &&
                    (!tipo.equals("H") || tipo.equals("H") && numHidroaviao == 0) &&
                    (!tipo.equals("G") || tipo.equals("G") && numCruzador == 0));

            System.out.println("Digite a coordenada x do navio.");
            x = scanner.nextLine();
            System.out.println("Digite a coordenada y do navio.");
            y = scanner.nextLine();
            System.out.println("Digite a orientação do navio. (0,1,2,3)");
            orientacao = scanner.nextLine();

            if (tabuleiro.posicionarNavio(Integer.parseInt(x), Integer.parseInt(y), tipo.charAt(0), Integer.parseInt(orientacao))) {
                if (tipo.equals("C") && numCouracado > 0) {
                    numCouracado--;
                } else if (tipo.equals("D") && numDestroyer > 0) {
                    numDestroyer--;
                } else if (tipo.equals("S") && numSubmarino > 0) {
                    numSubmarino--;
                } else if (tipo.equals("H") && numHidroaviao > 0) {
                    numHidroaviao--;
                } else if (tipo.equals("G") && numCruzador > 0) {
                    numCruzador--;
                }
            } else {
                System.out.println("Posição inválida, tente novamente.");
            }
            
            tabuleiro.exibirTabuleiro();
        }
    }

    private static int[] lerEstadoJogo(Tabuleiro tabuleiroJogador1, Tabuleiro tabuleiroJogador2) throws FileNotFoundException, NoSuchElementException {
        File file = new File("estado_jogo.txt");
        try (Scanner fileScanner = new Scanner(file)) {
            fileScanner.nextLine(); // Pula "Jogador 1"
            lerTabuleiro(fileScanner, tabuleiroJogador1);
            fileScanner.nextLine(); // Pula "Jogador 2"
            lerTabuleiro(fileScanner, tabuleiroJogador2);
            String linhaTurno = fileScanner.nextLine();
            String linhaAtaque = fileScanner.nextLine();
            int turno = Integer.parseInt(linhaTurno.split(": ")[1]);
            int numeroDoAtaque = Integer.parseInt(linhaAtaque.split(": ")[1]);
            return new int[]{turno, numeroDoAtaque}; // Retornando o turno e o número do ataque como um array
        } catch (IOException e) {
            System.out.println("Erro ao ler o estado do jogo: " + e.getMessage());
            throw e; // Relança a exceção após registrar o erro
        }
    }
    
    private static void lerTabuleiro(Scanner scanner, Tabuleiro tabuleiro) throws NoSuchElementException {
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            String linha = scanner.nextLine().trim();
            String[] celulas = linha.split(" ");
            for (int j = 0; j < celulas.length; j++) {
                tabuleiro.setCelula(i, j, celulas[j].charAt(0));
            }
        }
    }
    
    
    

    private static void gravarEstadoJogo(Tabuleiro tabuleiroJogador1, Tabuleiro tabuleiroJogador2, int turno, int numeroDoAtaque) {
        try (FileWriter writer = new FileWriter("estado_jogo.txt")) {
            writer.write("Jogador 1\n");
            writer.write(tabuleiroParaString(tabuleiroJogador1));
            writer.write("Jogador 2\n");
            writer.write(tabuleiroParaString(tabuleiroJogador2));
            writer.write("Turno: " + turno + "\n");
            writer.write("Número do Ataque: " + numeroDoAtaque + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o estado do jogo: " + e.getMessage());
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
