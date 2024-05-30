package Model;

public class Hidroaviao extends Navio {
    private static final char SYMBOL = 'H';
    private static final int[] COLOR = {153, 153, 153}; // Cinza

    public Hidroaviao() {
        super(3, COLOR);
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
