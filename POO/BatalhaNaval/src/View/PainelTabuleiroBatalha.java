package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import Model.Tabuleiro;

class PainelTabuleiroBatalha extends JPanel{
    private int tamanhoCelula = 30;
    private int numCelulas = 15;
    private int ataques = 3;
    private Tabuleiro tabuleiroOculto;
    private Tabuleiro tabuleiro;

    public PainelTabuleiroBatalha(Tabuleiro tabuleiro, Tabuleiro tabuleiroOculto, int jogador, boolean[] vezJogador1){
        this.tabuleiroOculto = tabuleiroOculto;
        this.tabuleiro = tabuleiro;
        setPreferredSize(new Dimension(numCelulas * tamanhoCelula, numCelulas * tamanhoCelula));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(jogador == 1 && vezJogador1[0] || jogador == 2 && !vezJogador1[0]){
                    int linha = e.getY() / tamanhoCelula;
                    int coluna = e.getX() / tamanhoCelula;
                    String ataque = tabuleiro.atacar(linha, coluna, tabuleiroOculto);

                    if(ataque != "") {
                        ataques--;
                        repaint();
                        JOptionPane.showMessageDialog(null, ataque);
                        verificaFim();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ataque inválido!");
                    }

                    System.out.println("Linha: " + linha + " Coluna: " + coluna + " Jogador: " + jogador + " Ataques restantes: " + ataques);
                    if (ataques == 0){
                        if(jogador == 1){
                            vezJogador1[0] = false;
                            System.out.println("Vez do jogador 2");
                        }
                        else{
                            vezJogador1[0] = true;
                            System.out.println("Vez do jogador 1");
                        }
                        ataques = 3;
                    }
                }
                else{
                    System.out.println("Nao é a vez do jogador " + jogador);
                }
                repaint();
            }
        });
    }

    private void verificaFim(){
        if(tabuleiro.todosNaviosAfundados(tabuleiroOculto)){
            JOptionPane.showMessageDialog(null, "Fim de jogo!");
            System.exit(0);
        }
    }

    private Color getColor(char symbol) {
        switch (symbol) {
            case 'X': return Color.RED;
            case 'O': return Color.BLUE;
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
                char celula = tabuleiroOculto.getCelula(i, j);
                Color corCelula = getColor(celula);
                Rectangle2D.Double rect = new Rectangle2D.Double(j * tamanhoCelula, i * tamanhoCelula, tamanhoCelula, tamanhoCelula);
                g2d.setColor(corCelula);
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
            }
        }
    }
}