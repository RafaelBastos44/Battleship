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
    public void testGetCelula() {
        Tabuleiro tabuleiro = new Tabuleiro(15);
        tabuleiro.setCelula(0, 0, 'S');
        assertEquals('S', tabuleiro.getCelula(0, 0));
    }

    @Test(timeout = TIMEOUT)
    public void testSetCelula() {
        Tabuleiro tabuleiro = new Tabuleiro(15);
        tabuleiro.setCelula(0, 0, 'S');
        assertEquals('S', tabuleiro.getCelula(0, 0));
        tabuleiro.setCelula(0, 0, 'X');
        assertEquals('X', tabuleiro.getCelula(0, 0));
    }

    @Test(timeout = TIMEOUT)
    public void testGetTabuleiro() {
        Tabuleiro tabuleiro = new Tabuleiro(15);
        Tabuleiro tabuleiroComparacao = new Tabuleiro(15);
        assertArrayEquals(tabuleiroComparacao.getTabuleiro(), tabuleiro.getTabuleiro());
    }

    @Test(timeout = TIMEOUT)
    public void testSetTabuleiro() {
        Tabuleiro tabuleiro = new Tabuleiro(15);
        char[][] newTabuleiro = new char[15][15];
        newTabuleiro[0][0] = 'S';
        tabuleiro.setTabuleiro(newTabuleiro);
        assertArrayEquals(newTabuleiro, tabuleiro.getTabuleiro());
    }

    @Test(timeout = TIMEOUT)
    public void testLimpar() {
        Tabuleiro tabuleiro = new Tabuleiro(15);
        tabuleiro.setCelula(0, 0, 'S');
        tabuleiro.limpar();
        assertEquals('~', tabuleiro.getCelula(0, 0));
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
        assertFalse(tabuleiro.posicionarNavio(15, 15, 'S', 0));
        assertFalse(tabuleiro.posicionarNavio(15, 15, 'C', 0));
        assertFalse(tabuleiro.posicionarNavio(15, 15, 'D', 0));
        assertFalse(tabuleiro.posicionarNavio(15, 15, 'G', 0));

    }
    @Test(timeout = TIMEOUT)
    public void testTodosNaviosAfundados() {
        Tabuleiro tabuleiro = new Tabuleiro(15);
        Tabuleiro tabuleiroOculto = new Tabuleiro(15);
        tabuleiro.posicionarNavio(0, 0, 'S', 0);
        assertFalse(tabuleiro.todosNaviosAfundados(tabuleiroOculto));
        tabuleiroOculto.setCelula(0, 0, 'X');
        assertTrue(tabuleiro.todosNaviosAfundados(tabuleiroOculto));

    }

}