package Model;

public class Submarino extends Navio {
    private static final char SYMBOL = 'S';
    private static final int[] COLOR = {255, 100, 0}; // Laranja

    public Submarino() {
        super(1, COLOR);
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
