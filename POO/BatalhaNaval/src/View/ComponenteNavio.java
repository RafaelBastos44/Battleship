package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComponenteNavio extends JPanel {
    private Navio[] navios;
    private Navio navioSelecionado = null;
    private int[] limites = {1, 0, 0, 0, 0}; // Limites de navios (Couraçado, Cruzador, Destroyer, Submarino, Hidroavião)
    private int[] contadores = {0, 0, 0, 0, 0}; // Contadores de navios colocados

    public ComponenteNavio() {
        navios = new Navio[]{
            new Couracado(),
            new Cruzador(),
            new Destroyer(),
            new Submarino(),
            new Hidroaviao()
        };

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (navioSelecionado == null) {
                    int y = e.getY();
                    int cellSize = 30;
                    int padding = 20;
                    int navioIndex = y / (cellSize + padding);
                    if (navioIndex < navios.length && contadores[navioIndex] < limites[navioIndex]) {
                        navioSelecionado = navios[navioIndex];
                        System.out.println("Navio selecionado: " + navioSelecionado.getClass().getSimpleName());
                        repaint();
                    }
                } else {
                    navioSelecionado = null; // Desseleciona se clicar novamente
                    repaint();
                }
            }
        });
    }

    public Navio getNavioSelecionado() {
        return navioSelecionado;
    }

    public boolean todosNaviosPosicionados() {
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] < limites[i]) {
                return false; // Ainda há navios a serem posicionados
            }
        }
        return true; // Todos os navios foram posicionados
    }

    public void incrementarContador(Navio navio) {
        for (int i = 0; i < navios.length; i++) {
            if (navios[i].getClass() == navio.getClass()) {
                contadores[i]++;
                break;
            }
        }
        repaint();
    }

    public void resetContadores() {
        for (int i = 0; i < contadores.length; i++) {
            contadores[i] = 0;
        }
        repaint();
    }

    public char getTipoNavioSelecionado() {
        if (navioSelecionado != null) {
            return navioSelecionado.getSymbol();
        }
        return ' '; // Nenhum navio selecionado
    }

    public void deselectNavio() {
        navioSelecionado = null;
        repaint();
    }

    public void resetSelection() {
        navioSelecionado = null;
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
            navio.draw(g2d, x, y, cellSize);
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(limites[i] - contadores[i]), x + 5 * cellSize + 10, y + cellSize / 2);
            if (navio == navioSelecionado) {
                g2d.setColor(Color.RED); // Indica o navio selecionado
                if (navio instanceof Hidroaviao) {
                    g2d.drawRect(x + 30 - cellSize - 2, y - 2, 3 * cellSize + 4, 2 * cellSize + 4);
                } else {
                    g2d.drawRect(x - 2, y - 2, navio.size * cellSize + 4, cellSize + 4);
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
