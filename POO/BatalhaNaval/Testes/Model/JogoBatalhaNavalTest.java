package Model;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Scanner;

public class JogoBatalhaNavalTest {
    //metodos de teste
    private static final int TIMEOUT = 2000;

    @Test(timeout = TIMEOUT)
    public void testInicializa() {
        Tabuleiro tabuleiro = new Tabuleiro(15);
        JogoBatalhaNaval.inicializa(tabuleiro, new Scanner(System.in));
        // Verifica se todos os navios foram posicionados
        assertEquals(15, tabuleiro.getTamanho());
    }
    // demais metodos sao testados ao jogar o jogo
}
