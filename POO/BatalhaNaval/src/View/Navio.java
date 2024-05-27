package View;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Navio {
    protected int size;
    protected Color color;

    public Navio(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    public abstract char getSymbol();

    public Color getColorShip() {
        return color;
    }

    public void draw(Graphics2D g2d, int x, int y, int cellSize) {
        g2d.setColor(color);
        for (int i = 0; i < size; i++) {
            g2d.fillRect(x + i * cellSize, y, cellSize, cellSize);
        }
    }
}

class Couracado extends Navio {
    private static final char SYMBOL = 'G';
    private static final Color COLOR = new Color(0, 150, 0); // Verde

    public Couracado() {
        super(5, COLOR);
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}

class Cruzador extends Navio {
    private static final char SYMBOL = 'C';
    private static final Color COLOR = new Color(141, 3, 255); // Roxo

    public Cruzador() {
        super(4, COLOR);
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}

class Destroyer extends Navio {
    private static final char SYMBOL = 'D';
    private static final Color COLOR = new Color(0, 100, 255); // Azul

    public Destroyer() {
        super(2, COLOR);
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}

class Submarino extends Navio {
    private static final char SYMBOL = 'S';
    private static final Color COLOR = new Color(255, 100, 0); // Laranja

    public Submarino() {
        super(1, COLOR);
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}

class Hidroaviao extends Navio {
    private static final char SYMBOL = 'H';
    private static final Color COLOR = Color.GRAY;

    public Hidroaviao() {
        super(3, COLOR);
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    @Override
    public void draw(Graphics2D g2d, int x, int y, int cellSize) {
        g2d.setColor(color);
        // Desenhando a forma especial com base no quadrado do meio como referência
        g2d.fillRect(x - cellSize + 30, y + cellSize, cellSize, cellSize); // Célula 1 à esquerda
        g2d.fillRect(x + 30, y, cellSize, cellSize); // Célula 2 central
        g2d.fillRect(x + cellSize + 30, y + cellSize, cellSize, cellSize); // Célula 3 à direita
    }
}
