package Controller;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Scanner;

public class ControllerAPITest {
    //metodos de teste
    private static final int TIMEOUT = 100000;

    public static ControllerAPITest getInstance() {
        // implementation of getInstance() method
        return new ControllerAPITest();
    }

    @Test(timeout = TIMEOUT)
    public void testgetInstance() {
        ControllerAPITest controller = ControllerAPITest.getInstance();
        assertNotNull(controller);
    }
}
