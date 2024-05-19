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

    public void draw(Graphics2D g2d, int x, int y, int cellSize) {
        g2d.setColor(color);
        if (isHorizontal) {
            for (int i = 0; i < size; i++) {
                g2d.fillRect(x + i * cellSize, y, cellSize, cellSize);
            }
        } else {
            for (int i = 0; i < size; i++) {
                g2d.fillRect(x, y + i * cellSize, cellSize, cellSize);
            }
        }
    }

    public void rotate() {
        isHorizontal = !isHorizontal;
    }
}


// Subclasse Carrier
class Carrier extends Ship {
    public Carrier() {
        super(5, new Color(34, 139, 34)); // Verde escuro
    }
}

// Subclasse Battleship
class Battleship extends Ship {
    public Battleship() {
        super(4, new Color(0, 128, 0)); // Verde
    }
}

// Subclasse Cruiser
class Cruiser extends Ship {
    public Cruiser() {
        super(3, new Color(255, 215, 0)); // Amarelo
    }
}

// Subclasse Submarine
class Submarine extends Ship {
    public Submarine() {
        super(3, new Color(255, 165, 0)); // Laranja
    }
}

// Subclasse Destroyer
class Destroyer extends Ship {
    public Destroyer() {
        super(2, new Color(139, 69, 19)); // Marrom
    }
}


