package View;

import javax.swing.*;

import Controller.BatalhaNaval;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import Model.Tabuleiro;

public class ComponenteTabuleiro extends JPanel {
    private int numCelulas;
    private int tamanhoCelula;
    private Tabuleiro tabuleiro;
    private PainelPosicionamento janela;
    private int orientacao = 0; // 0: horizontal, 1: vertical, 2: horizontal invertido, 3: vertical invertido
    private int linhaTemporaria = -1;
    private int colunaTemporaria = -1;
    private boolean navioTemporarioAtivo = false;
    private BatalhaNaval batalhaNaval;

    public ComponenteTabuleiro(int numCelulas, int tamanhoCelula, Tabuleiro tabuleiro, PainelPosicionamento janela, boolean Posionando, BatalhaNaval batalhaNaval) {
        System.out.println("BatalhaNaval no ComponenteTabuleiro: " + batalhaNaval); // Verificar se é nulo
        this.numCelulas = numCelulas;
        this.tamanhoCelula = tamanhoCelula;
        this.tabuleiro = tabuleiro;
        this.janela = janela;

        setPreferredSize(new Dimension(numCelulas * tamanhoCelula, numCelulas * tamanhoCelula));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = e.getY() / tamanhoCelula;
                int coluna = e.getX() / tamanhoCelula;
                System.out.println(linha + " " + coluna);
                if (SwingUtilities.isLeftMouseButton(e) && !Posionando) { // Ataque
                    batalhaNaval.realizarAtaque(linha, coluna);
                }else if (SwingUtilities.isRightMouseButton(e)) {
                    if (navioTemporarioAtivo) {
                        rotacionarNavioTemporario();
                        repaint();
                    }
                } else {
                    if (janela.getComponenteNavio().getNavioSelecionado() != null) {
                        linhaTemporaria = linha;
                        colunaTemporaria = coluna;
                        navioTemporarioAtivo = true;
                        repaint();
                    }
                }
            }
        });

        // Adiciona um KeyListener para capturar a tecla ESC
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (navioTemporarioAtivo) {
                        Navio navioSelecionado = janela.getComponenteNavio().getNavioSelecionado();
                        if (navioSelecionado != null) {
                            if (tabuleiro.posicionarNavio(linhaTemporaria, colunaTemporaria, navioSelecionado.getSymbol(), orientacao)) {
                                // Coloca o navio no tabuleiro e incrementa o contador
                                janela.getComponenteNavio().incrementarContador(navioSelecionado);
                                janela.getComponenteNavio().deselectNavio();
                                navioTemporarioAtivo = false;
                                linhaTemporaria = -1;
                                colunaTemporaria = -1;
                                orientacao = 0; // Redefine a orientação para 0 após posicionar o navio
                                repaint();
                            } else {
                                System.out.println("Posição inválida!");
                                // TODO: Adicionar feedback visual para o usuário
                            }
                        } else {
                            // Apenas deseleciona o navio temporário se nenhum navio estiver selecionado
                            navioTemporarioAtivo = false;
                            linhaTemporaria = -1;
                            colunaTemporaria = -1;
                            orientacao = 0;
                            repaint();
                        }
                    } else {
                        // Deseleciona o navio no ComponenteNavio
                        janela.getComponenteNavio().deselectNavio();
                    }
                }
            }
        });
    }

    private void rotacionarNavioTemporario() {
        char tipoNavio = janela.getComponenteNavio().getNavioSelecionado().getSymbol();
        if (tipoNavio == 'H') {
            orientacao = (orientacao + 1) % 4; // Hidroavião tem 4 orientações
        } else {
            orientacao = (orientacao + 1) % 2; // Outros navios têm 2 orientações
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Desenhar o tabuleiro atual
        for (int i = 0; i < numCelulas; i++) {
            for (int j = 0; j < numCelulas; j++) {
                char celula = tabuleiro.getCelula(i, j);
                Color corCelula = getColorForSymbol(celula);
                Rectangle2D.Double rect = new Rectangle2D.Double(j * tamanhoCelula, i * tamanhoCelula, tamanhoCelula, tamanhoCelula);
                g2d.setColor(corCelula);
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
            }
        }

        // Desenhar o navio temporário
        if (navioTemporarioAtivo && linhaTemporaria != -1 && colunaTemporaria != -1) {
            Navio navioSelecionado = janela.getComponenteNavio().getNavioSelecionado();
            if (navioSelecionado != null) {
                desenharNavioTemporario(g2d, linhaTemporaria, colunaTemporaria, navioSelecionado, orientacao);
            }
        }
    }

    private void desenharNavioTemporario(Graphics2D g2d, int linha, int coluna, Navio navio, int orientacao) {
        // Armazenar o estado atual do tabuleiro
        char[][] estadoAtual = new char[numCelulas][numCelulas];
        for (int i = 0; i < numCelulas; i++) {
            System.arraycopy(tabuleiro.getTabuleiro()[i], 0, estadoAtual[i], 0, numCelulas);
        }

        // Tentar posicionar o navio temporariamente
        if (tabuleiro.posicionarNavio(linha, coluna, navio.getSymbol(), orientacao)) {
            // Desenhar as células temporárias
            for (int i = 0; i < numCelulas; i++) {
                for (int j = 0; j < numCelulas; j++) {
                    if (tabuleiro.getCelula(i, j) == navio.getSymbol()) {
                        Rectangle2D.Double rect = new Rectangle2D.Double(j * tamanhoCelula, i * tamanhoCelula, tamanhoCelula, tamanhoCelula);
                        g2d.setColor(navio.getColorShip());
                        g2d.fill(rect);
                        g2d.setColor(Color.BLACK);
                        g2d.draw(rect);
                    }
                }
            }
        }

        // Restaurar o estado original do tabuleiro
        for (int i = 0; i < numCelulas; i++) {
            System.arraycopy(estadoAtual[i], 0, tabuleiro.getTabuleiro()[i], 0, numCelulas);
        }
    }

    private Color getColorForSymbol(char symbol) {
        Navio navio = null;
        switch (symbol) {
            case 'O': return Color.BLUE;
            case 'X': return Color.RED;
            case 'G': navio = new Couracado(); break;
            case 'C': navio = new Cruzador(); break;
            case 'D': navio = new Destroyer(); break;
            case 'S': navio = new Submarino(); break;
            case 'H': navio = new Hidroaviao(); break;
        }
        return navio != null ? navio.getColorShip() : Color.WHITE;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(numCelulas * tamanhoCelula, numCelulas * tamanhoCelula);
    }

    
}
