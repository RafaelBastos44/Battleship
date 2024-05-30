package Model;

public class Couracado extends Navio {
    private static final char SYMBOL = 'G';
    private static final int[] COLOR = {0, 150, 0}; // Verde

    public Couracado() {
        super(5, COLOR);
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}