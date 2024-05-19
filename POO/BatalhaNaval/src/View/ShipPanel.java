package View;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Model.Jogador;

public class ShipPanel extends JPanel {
    private Ship[] ships;
    Ship selectedShip;

    public ShipPanel() {
        ships = new Ship[]{
            new Couracado(),
            new Cruzador(),
            new Submarino(),
            new Destroyer(),
            new Hidroaviao() // Adicionando o hidroavião
        };

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedShip == null) { // Permite selecionar um navio apenas se nenhum estiver selecionado
                    int y = e.getY();
                    int cellSize = 30;
                    int padding = 20;

                    int shipIndex = y / (cellSize + padding);
                    if (shipIndex < ships.length) {
                        selectedShip = ships[shipIndex];
                        System.out.println("Navio selecionado: " + selectedShip.getClass().getSimpleName());
                        repaint();
                    }
                }
            }
        });
    }

    public Ship getSelectedShip() {
        return selectedShip;
    }

    public void deselectShip() {
        selectedShip = null;
        repaint();
    }

    public void resetSelection() {
        selectedShip = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int x = 10;
        int y = 10;
        int cellSize = 30;
        int padding = 20;

        for (Ship ship : ships) {
            ship.draw(g2d, x, y, cellSize);
            if (ship == selectedShip) {
                g2d.setColor(Color.RED); // Indica o navio selecionado
                if (ship instanceof Hidroaviao) {
                    if (ship.isHorizontal) {
                        g2d.drawRect(x + 30 - cellSize - 2, y - 2, 3 * cellSize + 4, 2 * cellSize + 4);
                    } else {
                        g2d.drawRect(x + 30 - cellSize - 2, y - 2, 2 * cellSize + 4, 3 * cellSize + 4);
                    }
                } else {
                    if (ship.isHorizontal) {
                        g2d.drawRect(x - 2, y - 2, ship.size * cellSize + 4, cellSize + 4);
                    } else {
                        g2d.drawRect(x - 2, y - 2, cellSize + 4, ship.size * cellSize + 4);
                    }
                }
            }
            y += cellSize + padding;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 600);
    }
}