package Model;

public class Navio {
    private int size;
    private int[] rgb;
    private char symbol;

    public Navio(char symbol) {
        this.symbol = symbol;

        if (symbol == 'G') {
            this.size = 5;
            this.rgb = new int[] {0, 150, 0};
        } else if (symbol == 'S') {
            this.size = 1;
            this.rgb = new int[] {255, 100, 0};
        } else if (symbol == 'C') {
            this.size = 4;
            this.rgb = new int[] {140, 0, 255};
        } else if (symbol == 'D') {
            this.size = 2;
            this.rgb = new int[] {0, 100, 255};
        } else if (symbol == 'H') {
            this.size = 3;
            this.rgb = new int[] {153, 153, 153};
        }
        else {
            this.size = 0;
            this.rgb = new int[] {255, 255, 255};
        }
    }

    // public abstract char getSymbol();

    public int[] getColorShip() {
        return rgb;
    }

    public int getSize() {
        return size;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void setColorShip(int[] rgb) {
        this.rgb = rgb;
    }
}
