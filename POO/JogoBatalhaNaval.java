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

        try {
            turno = lerEstadoJogo(tabuleiroJogador1, tabuleiroJogador2);
        } catch (FileNotFoundException e) {
            System.out.println("Nenhum jogo salvo encontrado, começando um novo jogo.");
            // Inicialização dos navios caso não haja jogo salvo
            inicializarNavios(tabuleiroJogador1);
            inicializarNavios(tabuleiroJogador2);
        } catch (NoSuchElementException | NumberFormatException e) {
            System.out.println("Arquivo de jogo corrompido, começando um novo jogo.");
            inicializarNavios(tabuleiroJogador1);
            inicializarNavios(tabuleiroJogador2);
        }

        boolean acerto;
        while (true) {
            System.out.println("Vez do Jogador " + turno);
            System.out.print("Digite a linha e a coluna para atacar: ");
            int linha = scanner.nextInt();
            int coluna = scanner.nextInt();

            if (turno == 1) {
                acerto = tabuleiroJogador2.atacar(linha, coluna);
                tabuleiroJogador2.exibirTabuleiro();
            } else {
                acerto = tabuleiroJogador1.atacar(linha, coluna);
                tabuleiroJogador1.exibirTabuleiro();
            }

            gravarEstadoJogo(tabuleiroJogador1, tabuleiroJogador2, turno);

            if (!acerto) {
                turno = (turno == 1) ? 2 : 1;
            }
        }
    }

    private static void inicializarNavios(Tabuleiro tabuleiro) {
        // Exemplo de posicionamento de navios
        tabuleiro.posicionarNavio(2, 2, 1, 'S', true);  // Submarino
        tabuleiro.posicionarNavio(3, 5, 3, 'D', true);  // Destruidor
        tabuleiro.posicionarNavio(5, 1, 5, 'P', true);  // Porta-Aviões
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
