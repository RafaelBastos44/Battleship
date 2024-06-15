package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Salvamento {

    public static Object[] lerEstadoJogo(Tabuleiro tabuleiroJogador1, Tabuleiro tabuleiroJogador2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, String[] nomes) throws FileNotFoundException, NoSuchElementException {
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

            String linhaNome1 = fileScanner.nextLine();
            String linhaNome2 = fileScanner.nextLine();
            String linhaTurno = fileScanner.nextLine();
            String linhaAtaque = fileScanner.nextLine();

            nomes[0] = linhaNome1.split(": ")[1];
            nomes[1] = linhaNome2.split(": ")[1];
            boolean turno = Boolean.parseBoolean(linhaTurno.split(": ")[1]);
            int numeroDoAtaque = Integer.parseInt(linhaAtaque.split(": ")[1]);

            return new Object[]{turno, numeroDoAtaque}; // Retornando o turno e o número do ataque como um array
        } catch (IOException e) {
            System.out.println("Exceção ao ler o estado do jogo: " + e.getMessage());
            throw new RuntimeException(e); // Relança a exceção após registrar o erro
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

    public static void gravarEstadoJogo(Tabuleiro tabuleiroJogador1, Tabuleiro tabuleiroJogador2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, boolean turno, int numeroDoAtaque, String[] nomes) {
        if(numeroDoAtaque == 0){
            numeroDoAtaque = 3;
            turno = !turno;
        }
        try (FileWriter writer = new FileWriter("estado_jogo.txt")) {
            writer.write("Jogador 1\n");
            writer.write(tabuleiroParaString(tabuleiroJogador1));
            writer.write("Oculto 1\n");
            writer.write(tabuleiroParaString(tabuleiroOculto1));
            writer.write("Jogador 2\n");
            writer.write(tabuleiroParaString(tabuleiroJogador2));
            writer.write("Oculto 2\n");
            writer.write(tabuleiroParaString(tabuleiroOculto2));

            writer.write("Nome Jogador 1: " + nomes[0] + "\n");
            writer.write("Nome Jogador 2: " + nomes[1] + "\n");

            writer.write("Turno: " + turno + "\n");
            writer.write("Número do Ataque: " + numeroDoAtaque + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o estado do jogo: " + e.getMessage());
        }
    }

    public static void deletaEstadoJogo() {
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
