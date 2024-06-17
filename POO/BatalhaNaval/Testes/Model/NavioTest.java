package Model;

import static org.junit.Assert.*;
import org.junit.Test;



public class NavioTest {
    //metodos de teste
    private static final int TIMEOUT = 2000;

    @Test(timeout = TIMEOUT)
    public void testGetSize() {
        Navio navio = new Navio('N');
        navio.setSize(5);
        assertEquals(5, navio.getSize());
    }

    @Test(timeout = TIMEOUT)
    public void testGetSymbol() {
        Navio navio = new Navio('N');
        assertEquals('N', navio.getSymbol());
    }

    @Test(timeout = TIMEOUT)
    public void testSetColorShip() {
        Navio navio = new Navio('N');
        int[] rgb = {255, 0, 0};
        navio.setColorShip(rgb);
        assertArrayEquals(rgb, navio.getColorShip());
    }

    @Test(timeout = TIMEOUT)
    public void testSetContadores() {
        Navio navio = new Navio('N');
        int[] contadores = {1, 2, 3};
        navio.setContadores(contadores);
        assertArrayEquals(contadores, navio.getContadores());
    }

    @Test(timeout = TIMEOUT)
    public void testSetSize() {
        Navio navio = new Navio('N');
        navio.setSize(5);
        assertEquals(5, navio.getSize());
    }

    @Test(timeout = TIMEOUT)
    public void testSetSymbol() {
        Navio navio = new Navio('N');
        navio.setSymbol('M');
        assertEquals('M', navio.getSymbol());
    }

}