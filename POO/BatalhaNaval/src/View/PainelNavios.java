package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Model.*;
import Observer.*;

class PainelNavios extends JPanel implements ObservadorIF {
    private Navio[] navios;
    private char[] tipos = {'G', 'C', 'D', 'S', 'H'};
    private int[] limites = {1, 0, 0, 0, 1}; // Limites de navios (Couraçado, Cruzador, Destroyer, Submarino, Hidroavião)
    private Navio navioSelecionado;

    public PainelNavios(Navio navioSelecionado) {
        this.navioSelecionado = navioSelecionado;

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
                requestFocusInWindow();
                if (navioSelecionado.getSymbol() == 'N') {
                    int y = e.getY();
                    int cellSize = 30;
                    int padding = 20;
                    int navioIndex = y / (cellSize + padding);
                    if (navioIndex < tipos.length && navioSelecionado.getContadores()[navioIndex] < limites[navioIndex]) {
                        navioSelecionado.setSymbol(tipos[navioIndex]);
                        System.out.println("Navio selecionado: " + navioSelecionado.getSymbol());
                    }
                } else {
                    navioSelecionado.setSymbol('N');
                }
                repaint();
            }
        });

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    deselectNavio();
                }
            }
        });
    }

    @Override
    public void notify(ObservadoIF o) {
        revalidate();
        repaint();
    }

    public Navio getNavioSelecionado() {
        return navioSelecionado;
    }

    public void deselectNavio() {
        navioSelecionado.setSymbol('N');
        repaint();
    }

    public void resetContador(){
        navioSelecionado.setContadores(new int[5]);
    }

    public boolean todosNaviosPosicionados() {
        for (int i = 0; i < navioSelecionado.getContadores().length; i++) {
            if (navioSelecionado.getContadores()[i] < limites[i]) {
                return false; // Ainda há navios a serem posicionados
            }
        }
        return true; // Todos os navios foram posicionados
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
                for (int j = 0; j < navio.getSize(); j++) {
                    g2d.fillRect(x + j * cellSize, y, cellSize, cellSize);
                }
            } else {
                g2d.fillRect(x - cellSize + 30, y + cellSize, cellSize, cellSize); // Célula 1 à esquerda
                g2d.fillRect(x + 30, y, cellSize, cellSize); // Célula 2 central
                g2d.fillRect(x + cellSize + 30, y + cellSize, cellSize, cellSize); // Célula 3 à direita
            }
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(limites[i] - navioSelecionado.getContadores()[i]), x + 5 * cellSize + 10, y + cellSize / 2);
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
