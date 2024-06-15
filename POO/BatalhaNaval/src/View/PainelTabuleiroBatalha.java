package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import Model.Tabuleiro;
import Model.Salvamento;
import Observer.*;

@SuppressWarnings("unused")
class PainelTabuleiroBatalha extends JPanel implements ObservadoIF {
    private int tamanhoCelula = 30;
    private int numCelulas = 15;
    private int ataques = 3;
    private Tabuleiro tabuleiroOculto;
    private Tabuleiro tabuleiro;
    private JLabel respostaLabel;
    private List<ObservadorIF> observers = new ArrayList<>();
    
    public PainelTabuleiroBatalha(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, int jogador, boolean[] vezJogador1, JLabel respostaLabel, JLabel jogadorLabel, JLabel ataquesLabel, String[] Nomes, int ataque) {
        if(jogador == 2)
        {
            this.tabuleiroOculto = tabuleiroOculto1;
            this.tabuleiro = tabuleiro1;
        }
        else{
        this.tabuleiroOculto = tabuleiroOculto2;
        this.tabuleiro = tabuleiro2
        ;}

        ataques = ataque;
            
        this.respostaLabel = respostaLabel;
        setPreferredSize(new Dimension(numCelulas * tamanhoCelula, numCelulas * tamanhoCelula));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jogador == 1 && vezJogador1[0] || jogador == 2 && !vezJogador1[0]) {
                    int linha = e.getY() / tamanhoCelula;
                    int coluna = e.getX() / tamanhoCelula;
                    String ataque = tabuleiro.atacar(linha, coluna, tabuleiroOculto);

                    if (!ataque.isEmpty()) {
                        ataques--;
                        Model.Salvamento.gravarEstadoJogo(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, vezJogador1[0], ataques, Nomes);
                        repaint();
                        respostaLabel.setText(ataque);
                        verificaFim();
                    } else {
                        respostaLabel.setText("Ataque inválido!");
                    }

                    System.out.println("Linha: " + linha + " Coluna: " + coluna + " Jogador: " + jogador + " Ataques restantes: " + ataques);
                    if (ataques == 0) {
                        if (jogador == 1) {
                            vezJogador1[0] = false;
                            jogadorLabel.setText("Vez de " + Nomes[1]);
                        } else {
                            vezJogador1[0] = true;
                            jogadorLabel.setText("Vez de " + Nomes[0]);
                        }
                        ataques = 3;
                    }
                    ataquesLabel.setText("Ataques Restantes: "+ataques);
                } else {
                    respostaLabel.setText("Não é a vez de " + Nomes[jogador-1]);
                }
                repaint();
            }
        });
    }

    private void verificaFim() {
        if (tabuleiro.todosNaviosAfundados(tabuleiroOculto)) {
            respostaLabel.setText("Fim de jogo!");
            notifyObservers();
            //System.exit(0);
        }
    }

    @Override
    public void addObservador(ObservadorIF o) {
        observers.add(o);
    }

    private void notifyObservers() {
        for (ObservadorIF observer : observers) {
            observer.notify(this);
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
