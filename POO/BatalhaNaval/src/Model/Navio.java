package Model;

public abstract class Navio {
    private int size;
    private int[] rgb;

    public Navio(int size, int[] rgb) {
        this.size = size;
        this.rgb = rgb;
    }

    public abstract char getSymbol();

    public int[] getColorShip() {
        return rgb;
    }

    public int getSize() {
        return size;
    }
}
