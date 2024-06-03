package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Model.*;

class PainelNavios extends JPanel{
    private Navio[] navios;
    private char[] tipos = {'G', 'C', 'D', 'S', 'H'};
    private int[] limites = {1, 1, 0, 0, 0}; // Limites de navios (Couraçado, Cruzador, Destroyer, Submarino, Hidroavião)
    private int[] contadores; // Contadores de navios colocados
    private Navio navioSelecionado;

    public PainelNavios(Navio navioSelecionado, int[] contadores) {
        this.navioSelecionado = navioSelecionado;
        this.contadores = contadores;

        navios = new Navio[]{
            new Navio('G'),
            new Navio('C'),
            new Navio('D'),
            new Navio('S'),
            new Navio('H')
        };

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (navioSelecionado.getSymbol() == 'N') {
                    int y = e.getY();
                    int cellSize = 30;
                    int padding = 20;
                    int navioIndex = y / (cellSize + padding);
                    // System.out.println(navioIndex);
                    if (navioIndex < tipos.length && contadores[navioIndex] < limites[navioIndex]) {
                        navioSelecionado.setSymbol(tipos[navioIndex]);
                        System.out.println("Navio selecionado: " + navioSelecionado.getSymbol());
                    }
                } else {
                    navioSelecionado.setSymbol('N');
                }
                repaint();
            }
        });

    }

    public Navio getNavioSelecionado() {
        return navioSelecionado;
    }

    public void deselectNavio() {
        navioSelecionado = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int x = 10;
        int y = 10;
        int cellSize = 30;
        int padding = 20;

        for (int i = 0; i < navios.length; i++) {
            Navio navio = navios[i];

            Color ColorShip = new Color(navio.getColorShip()[0], navio.getColorShip()[1], navio.getColorShip()[2]);
            g2d.setColor(ColorShip);
            if (navio.getSymbol() != 'H') {
                 for(int j = 0; j < navio.getSize(); j++){
                    g2d.fillRect(x + j * cellSize, y, cellSize, cellSize);
                }
            } else {
                g2d.fillRect(x - cellSize + 30, y + cellSize, cellSize, cellSize); // Célula 1 à esquerda
                g2d.fillRect(x + 30, y, cellSize, cellSize); // Célula 2 central
                g2d.fillRect(x + cellSize + 30, y + cellSize, cellSize, cellSize); // Célula 3 à direita
            }
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(limites[i] - contadores[i]), x + 5 * cellSize + 10, y + cellSize / 2);
            if (navio.getSymbol() == navioSelecionado.getSymbol()) {
                g2d.setColor(Color.RED); // Indica o navio selecionado
                if (navio.getSymbol() == 'H') {
                    g2d.drawRect(x + 30 - cellSize - 2, y - 2, 3 * cellSize + 4, 2 * cellSize + 4);
                } else {
                    g2d.drawRect(x - 2, y - 2, navio.getSize() * cellSize + 4, cellSize + 4);
                }
            }
            y += cellSize + padding;
        }
    }
}
