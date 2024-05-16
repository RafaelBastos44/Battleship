package Model;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class JogoBatalhaNaval {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Tabuleiro tabuleiroJogador1 = new Tabuleiro(15);
        Tabuleiro tabuleiroJogador2 = new Tabuleiro(15);
        Tabuleiro tabuleiroOculto1 = new Tabuleiro(15);
        Tabuleiro tabuleiroOculto2 = new Tabuleiro(15);
        int turno = 1; // 1 para jogador 1, 2 para jogador 2
        int ataque = 0;
        int arrayRetorno[] = new int[2];

        System.out.println("BATALHA NAVAL\n");
        System.out.println("Regras:");
        System.out.println("1. No começo, cada jogador deve posicionar seus navios no tabuleiro.");
        System.out.println("2. Cada jogador tem 3 ataques por rodada.");
        System.out.println("3. O jogo termina quando todos os navios de um jogador forem destruídos.\n");

        System.out.println("Para começar um jogo novo, digite 'n'. Para carregar um jogo salvo, digite 'c'.");
        char opcao = scanner.next().charAt(0);

        while (opcao != 'n' && opcao != 'c') {
            System.out.println("Opçao inválida. Digite 'n' para começar um jogo novo ou 'c' para carregar um jogo salvo.");
            opcao = scanner.next().charAt(0);
        }

        if (opcao == 'n') {
            System.out.println("Jogador 1, posicione seus navios:");
            inicializa(tabuleiroJogador1, scanner);
            System.out.println("Jogador 2, posicione seus navios:");
            inicializa(tabuleiroJogador2, scanner);
        }
        else if (opcao == 'c') {
            try {
                arrayRetorno = lerEstadoJogo(tabuleiroJogador1, tabuleiroJogador2, tabuleiroOculto1, tabuleiroOculto2);
                turno = arrayRetorno[0];
                ataque = arrayRetorno[1];
            } catch (FileNotFoundException e) {
                System.out.println("Nenhum jogo salvo encontrado, começando um novo jogo.");
                System.out.println("Jogador 1, posicione seus navios:");
                inicializa(tabuleiroJogador1, scanner);
                System.out.println("Jogador 2, posicione seus navios:");
                inicializa(tabuleiroJogador2, scanner);
            } catch (NoSuchElementException | NumberFormatException e) {
                System.out.println("Arquivo de jogo corrompido, começando um novo jogo.");
                System.out.println("Jogador 1, posicione seus navios:");
                inicializa(tabuleiroJogador1, scanner);
                System.out.println("Jogador 2, posicione seus navios:");
                inicializa(tabuleiroJogador2, scanner);
            }
        }

        while (true) {
            if (ataque == 3){
                turno = (turno == 1) ? 2 : 1;
                ataque = 0;
            }

            System.out.println("Vez do Jogador " + turno);
            for(int i = ataque; i < 3; i++) { // Cada jogador joga 3 vezes
                System.out.print("Ataque " + (i + 1) + " - Digite a linha(letra) e a coluna(numero) para atacar (separados por espaço): ");
                char linhaChar = scanner.next().toUpperCase().charAt(0);
                int coluna = scanner.nextInt() - 1;
                int linha = linhaChar - 'A';

                if (turno == 1) {
                    System.out.print(tabuleiroJogador2.atacar(linha, coluna, tabuleiroOculto2) + "\n");
                    System.out.println("Tabuleiro oculto do Jogador 2:");
                    tabuleiroOculto2.exibirTabuleiro();
                } else {
                    System.out.print(tabuleiroJogador1.atacar(linha, coluna, tabuleiroOculto1) + "\n");
                    System.out.println("Tabuleiro oculto do Jogador 1:");
                    tabuleiroOculto1.exibirTabuleiro();
                }

                gravarEstadoJogo(tabuleiroJogador1, tabuleiroJogador2, tabuleiroOculto1, tabuleiroOculto2, turno, i+1);

                if (turno == 1 && verificaFim(tabuleiroJogador2, tabuleiroOculto2, turno))
                {
                    System.out.println("Jogador 1 venceu!");
                    deletaEstadoJogo();
                    System.exit(0);
                }
                else if (turno == 2 && verificaFim(tabuleiroJogador1, tabuleiroOculto1, turno))
                {
                    System.out.println("Jogador 2 venceu!");
                    deletaEstadoJogo();
                    System.exit(0);
                }

                if (i == 2) { // Se não acertar no último ataque, troca de jogador
                    turno = (turno == 1) ? 2 : 1;
                    break; // Sair do loop de ataque
                }
            }
            ataque = 0;
        }
    }

    private static boolean verificaFim(Tabuleiro tabuleiro, Tabuleiro tabuleiroOculto, int turno)
    {
        boolean fim = true;
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            for (int j = 0; j < tabuleiro.getTamanho(); j++) {
                if(tabuleiro.getCelula(i, j) != '~' && tabuleiroOculto.getCelula(i, j) != 'X')
                {
                    fim = false;
                }
            }
        }
        
        return fim;
    }

    public static void inicializa(Tabuleiro tabuleiro, Scanner scanner) {
        String tipo;

        int numCouracado = 1; //1
        int numCruzador = 1; //2
        int numDestroyer = 1; //3
        int numHidroaviao = 1; //5
        int numSubmarino = 1; //4

        char linhaChar;
        int linha;
        int coluna;
        int orientacao;

        while (numCouracado + numCruzador + numDestroyer + numHidroaviao + numSubmarino > 0) {
            System.out.println("Você tem:\n");
            System.out.println(numCouracado + " Couraçado(s), cada um ocupa 5 quadrados (Tipo: G)");
            System.out.println(numCruzador + " Cruzador(es), cada um ocupa 4 quadrados (Tipo: C)");
            System.out.println(numDestroyer + " Destroyer(s), cada um ocupa 2 quadrados (Tipo: D)");
            System.out.println(numSubmarino + " Submarino(s), cada um ocupa 1 quadrado (Tipo: S)");
            System.out.println(numHidroaviao + " Hidroaviao(s), cada um ocupa 3 quadrados em formato triangular (Tipo: H)\n");

            do {
                System.out.println("Digite o tipo do navio.");
                tipo = scanner.next().toUpperCase();
            } while ((!tipo.equals("G") || tipo.equals("G") && numCouracado == 0) &&
                    (!tipo.equals("D") || tipo.equals("D") && numDestroyer == 0) &&
                    (!tipo.equals("S") || tipo.equals("S") && numSubmarino == 0) &&
                    (!tipo.equals("H") || tipo.equals("H") && numHidroaviao == 0) &&
                    (!tipo.equals("C") || tipo.equals("C") && numCruzador == 0));

            System.out.println("Digite a linha(letra) e a coluna(numero) do navio separados por espaço.");
            linhaChar = scanner.next().toUpperCase().charAt(0);
            coluna = scanner.nextInt() - 1; // dando erro quando o jogador bota "a c" por exemplo 
            linha = linhaChar - 'A';
            System.out.println("Digite a orientaçao do navio: (0 para direita, 1 para baixo, 2 para esquerda, 3 para cima)");
            orientacao = scanner.nextInt();

            if (tabuleiro.posicionarNavio(linha, coluna, tipo.charAt(0), orientacao)) {
                if (tipo.equals("G") && numCouracado > 0) {
                    numCouracado--;
                    // mexer aquiiii
                } else if (tipo.equals("D") && numDestroyer > 0) {
                    numDestroyer--;
                } else if (tipo.equals("S") && numSubmarino > 0) {
                    numSubmarino--;
                } else if (tipo.equals("H") && numHidroaviao > 0) {
                    numHidroaviao--;
                } else if (tipo.equals("C") && numCruzador > 0) {
                    numCruzador--;
                }
            } else {
                System.out.println("Posição inválida, tente novamente.");
            }
            System.out.println("\n");
            tabuleiro.exibirTabuleiro();
        }
    }

    private static int[] lerEstadoJogo(Tabuleiro tabuleiroJogador1, Tabuleiro tabuleiroJogador2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2) throws FileNotFoundException, NoSuchElementException {
        File file = new File("estado_jogo.txt");
        try (Scanner fileScanner = new Scanner(file)) {
            fileScanner.nextLine(); // Pula "Jogador 1"
            lerTabuleiro(fileScanner, tabuleiroJogador1);
            fileScanner.nextLine(); // Pula "Oculto 1"
            lerTabuleiro(fileScanner, tabuleiroOculto1);

            fileScanner.nextLine(); // Pula "Jogador 2"
            lerTabuleiro(fileScanner, tabuleiroJogador2);
            fileScanner.nextLine(); // Pula "Oculto 2"
            lerTabuleiro(fileScanner, tabuleiroOculto2);

            String linhaTurno = fileScanner.nextLine();
            String linhaAtaque = fileScanner.nextLine();

            int turno = Integer.parseInt(linhaTurno.split(": ")[1]);
            int numeroDoAtaque = Integer.parseInt(linhaAtaque.split(": ")[1]);

            return new int[]{turno, numeroDoAtaque}; // Retornando o turno e o número do ataque como um array
        } catch (IOException e) {
            System.out.println("Exceçao ao ler o estado do jogo: " + e.getMessage());
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

    private static void gravarEstadoJogo(Tabuleiro tabuleiroJogador1, Tabuleiro tabuleiroJogador2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, int turno, int numeroDoAtaque) {
        try (FileWriter writer = new FileWriter("estado_jogo.txt")) {
            writer.write("Jogador 1\n");
            writer.write(tabuleiroParaString(tabuleiroJogador1));
            writer.write("Oculto 1\n");
            writer.write(tabuleiroParaString(tabuleiroOculto1));
            writer.write("Jogador 2\n");
            writer.write(tabuleiroParaString(tabuleiroJogador2));
            writer.write("Oculto 2\n");
            writer.write(tabuleiroParaString(tabuleiroOculto2));

            writer.write("Turno: " + turno + "\n");
            writer.write("Número do Ataque: " + numeroDoAtaque + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o estado do jogo: " + e.getMessage());
        }
    }

    private static void deletaEstadoJogo() {
        File file = new File("estado_jogo.txt");
        if (file.exists()) {
            file.delete();
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
