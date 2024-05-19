package View;

import java.awt.Color;
import java.awt.Graphics2D;

public class Ship {
    protected int size;
    protected Color color;
    protected boolean isHorizontal; // Nova variável para orientação

    public Ship(int size, Color color) {
        this.size = size;
        this.color = color;
        this.isHorizontal = true; // Por padrão, os navios serão horizontais
    }

    public char getSymbol() {
        return 'S'; // Default symbol for generic ship
    }

    public void draw(Graphics2D g2d, int x, int y, int cellSize) {
        g2d.setColor(color);
        if (isHorizontal) {
            for (int i = 0; i < size; i++) {
                g2d.fillRect(x + i * cellSize, y, cellSize, cellSize);
            }
        }
        else {
            for (int i = 0; i < size; i++) {
                g2d.fillRect(x, y + i * cellSize, cellSize, cellSize);
            }
        }
    }

    public void rotate() {
        isHorizontal = !isHorizontal;
    }
}


// Subclasse Couraçado
class Couracado extends Ship {
    public Couracado() {
        super(5, new Color(0, 150, 0)); // Verde
    }

    public char getSymbol() {
        return 'G'; // Symbol for Couracado
    }
}

// Subclasse Cruzador
class Cruzador extends Ship {
    public Cruzador() {
        super(4, new Color(141, 3, 255)); // Roxo
    }

    public char getSymbol() {
        return 'C'; // Symbol for Cruzador
    }
}

// Subclasse Destroyer
class Destroyer extends Ship {
    public Destroyer() {
        super(2, new Color(0, 100, 255)); // Azul
    }

    public char getSymbol() {
        return 'D'; // Symbol for Destroyer
    }
}

// Subclasse Submarino
class Submarino extends Ship {
    public Submarino() {
        super(1, new Color(255, 100, 0)); // Laranja
    }

    public char getSymbol() {
        return 'S'; // Symbol for Submarino
    }
}


