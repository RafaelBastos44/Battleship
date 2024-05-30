package Model;

public class Cruzador extends Navio {
    private static final char SYMBOL = 'C';
    private static final int[] COLOR = {141, 3, 255}; // Roxo

    public Cruzador() {
        super(4, COLOR);
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
