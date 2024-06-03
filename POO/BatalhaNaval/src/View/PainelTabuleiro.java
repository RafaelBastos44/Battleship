package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

//import Model.Tabuleiro;
import Model.*;


class PainelTabuleiro extends JPanel {
    private Tabuleiro tabuleiroTemp;
    private int tamanhoCelula = 30;
    private int numCelulas = 15;
    private int orientacao = 0;
    private boolean posicionando = false;
    
    public PainelTabuleiro(Tabuleiro tabuleiro, Navio navioSelecionado, int[] contadores) {
        this.tabuleiroTemp = new Tabuleiro(numCelulas);

        setPreferredSize(new Dimension(numCelulas * tamanhoCelula, numCelulas * tamanhoCelula));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = e.getY() / tamanhoCelula;
                int coluna = e.getX() / tamanhoCelula;
                System.out.println(linha + " " + coluna);
                if(SwingUtilities.isLeftMouseButton(e) && navioSelecionado.getSymbol() != 'N') {
                    tabuleiroTemp.setTabuleiro(tabuleiro.getTabuleiro());
                    insereNavioTabuleiro(navioSelecionado, linha, coluna, orientacao);
                    posicionando = true;
                }
                else if(SwingUtilities.isRightMouseButton(e) && posicionando) {
                    orientacao++;
                    if(orientacao > 3) {
                        orientacao = 0;
                    }
                    tabuleiroTemp.setTabuleiro(tabuleiro.getTabuleiro());
                    insereNavioTabuleiro(navioSelecionado, linha, coluna, orientacao);
                    repaint();
                }
                repaint();
            }
        });

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    tabuleiro.setTabuleiro(tabuleiroTemp.getTabuleiro());
                    tabuleiro.exibirTabuleiro();
                    
                    if (navioSelecionado.getSymbol() == 'G') {
                        contadores[0]++;
                    }
                    else if (navioSelecionado.getSymbol() == 'C') {
                        contadores[1]++;
                    }
                    else if (navioSelecionado.getSymbol() == 'D') {
                        contadores[2]++;
                    }
                    else if (navioSelecionado.getSymbol() == 'S') {
                        contadores[3]++;
                    }
                    else if (navioSelecionado.getSymbol() == 'H') {
                        contadores[4]++;
                    }
                    navioSelecionado.setSymbol('N');
                    posicionando = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    tabuleiro.exibirTabuleiro();
                }
                repaint();
            }
        });
    }
    
    private Color getColor(char symbol) {
        switch (symbol) {
            case 'G': return new Color(0, 150, 0);
            case 'C': return new Color(140, 0, 255);
            case 'D': return new Color(0, 100, 255);
            case 'S': return new Color(255, 100, 0);
            case 'H': return new Color(153, 153, 153);
            default: return Color.WHITE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Desenhar o tabuleiro atual
        for (int i = 0; i < numCelulas; i++) {
            for (int j = 0; j < numCelulas; j++) {
                char celula = tabuleiroTemp.getCelula(i, j);
                Color corCelula = getColor(celula);
                Rectangle2D.Double rect = new Rectangle2D.Double(j * tamanhoCelula, i * tamanhoCelula, tamanhoCelula, tamanhoCelula);
                g2d.setColor(corCelula);
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
            }
        }
    }

    private void insereNavioTabuleiro(Navio navio, int linha, int coluna, int orientacao) {
        int size = navio.getSize();
        char symbol = navio.getSymbol();
        if (tabuleiroTemp.posicionarNavio(linha, coluna, symbol, orientacao)) {
            for (int i = 0; i < size; i++) {
                if (orientacao == 0) {
                    tabuleiroTemp.setCelula(linha, coluna + i, symbol);
                } else {
                    tabuleiroTemp.setCelula(linha + i, coluna, symbol);
                }
            }
        }
    }
}