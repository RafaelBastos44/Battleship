package Model;

import static org.junit.Assert.*;
import org.junit.Test;



public class TabuleiroTest {
    //metodos de teste
    private static final int TIMEOUT = 2000;

    @Test(timeout = TIMEOUT)
    public void testGetTamanho() {
        Tabuleiro tabuleiro = new Tabuleiro(15);
        assertEquals(15, tabuleiro.getTamanho());
    }

    @Test(timeout = TIMEOUT)
    public void testPosicionarNavio() {
        Tabuleiro tabuleiro = new Tabuleiro(15);
        // Verifica se todos os navios foram posicionados
        assertTrue(tabuleiro.posicionarNavio(0, 0, 'S', 0));
        assertTrue(tabuleiro.posicionarNavio(1, 1, 'C', 0));
        assertTrue(tabuleiro.posicionarNavio(2, 2, 'D', 0));
        assertTrue(tabuleiro.posicionarNavio(3, 3, 'G', 0));
        assertTrue(tabuleiro.posicionarNavio(7, 7, 'H', 0));
        assertFalse(tabuleiro.posicionarNavio(0, 0, 'H', 0));
    }
    // demais metodos sao testados ao jogar o jogo

}
