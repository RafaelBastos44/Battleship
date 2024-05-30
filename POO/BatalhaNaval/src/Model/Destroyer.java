package Model;

public class Destroyer extends Navio {
    private static final char SYMBOL = 'D';
    private static final int[] COLOR = {0, 100, 255}; // Azul

    public Destroyer() {
        super(2, COLOR);
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}