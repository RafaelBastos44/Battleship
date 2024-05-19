package View;

import java.awt.Color;
import java.awt.Graphics2D;

public class Hidroaviao extends Ship {

    public Hidroaviao() {
        super(3, Color.BLACK); // Definindo o tamanho como 3 e a cor como preto
    }

    @Override
    public void draw(Graphics2D g2d, int x, int y, int cellSize) {
        g2d.setColor(color);
        // Desenhando a forma especial com base no quadrado do meio como referência
        if (isHorizontal) {
            g2d.fillRect(x - cellSize + 30, y + cellSize, cellSize, cellSize); // Célula 1 à esquerda
            g2d.fillRect(x + 30, y, cellSize, cellSize); // Célula 2 central
            g2d.fillRect(x + cellSize + 30, y + cellSize, cellSize, cellSize); // Célula 3 à direita
        } else {
            g2d.fillRect(x + 30 - cellSize, y - cellSize, cellSize, cellSize); // Célula 1 acima
            g2d.fillRect(x + 30, y, cellSize, cellSize); // Célula 2 central
            g2d.fillRect(x + 30 - cellSize, y + cellSize, cellSize, cellSize); // Célula 3 abaixo
        }
    }
}

