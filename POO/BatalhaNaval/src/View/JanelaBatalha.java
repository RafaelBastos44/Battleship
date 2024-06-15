package View;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Tabuleiro;
import Observer.*;

class JanelaBatalha extends JFrame implements ObservadorIF {
    private boolean[] vezJogador1 = {true};
    private Tabuleiro tabuleiro1;
    private Tabuleiro tabuleiro2;
    private Tabuleiro tabuleiroOculto1;
    private Tabuleiro tabuleiroOculto2;
    int jogador = 1;
    

    public JanelaBatalha(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, String[] Nomes, boolean turno, int ataque) {
        this.tabuleiro1 = tabuleiro1;
        this.tabuleiro2 = tabuleiro2;
        this.tabuleiroOculto1 = tabuleiroOculto1;
        this.tabuleiroOculto2 = tabuleiroOculto2;
        vezJogador1[0] = turno;
   
        if(vezJogador1[0] == true){
            jogador = 1;
        }
        else{
            jogador = 2;
        }

        System.out.println(ataque);

        setTitle("Batalha Naval - Batalha");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel panelBatalha = new JPanel(new GridBagLayout());
        setSize(960, 550);

        JLabel respostaLabel = new JLabel("");
        respostaLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel ataquesLabel = new JLabel("Ataques Restantes: " + ataque);
        ataquesLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel jogadorLabel = new JLabel("Vez de " + Nomes[jogador-1]);
        jogadorLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelLabels = new JPanel(new GridLayout(2, 1));
        panelLabels.add(jogadorLabel);
        panelLabels.add(ataquesLabel);


        PainelTabuleiroBatalha painelBatalha1 = new PainelTabuleiroBatalha(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, 2, vezJogador1, respostaLabel, jogadorLabel, ataquesLabel, Nomes, ataque);
        PainelTabuleiroBatalha painelBatalha2 = new PainelTabuleiroBatalha(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, 1, vezJogador1, respostaLabel, jogadorLabel, ataquesLabel, Nomes, ataque);
        
        painelBatalha1.addObservador(this);
        painelBatalha2.addObservador(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelBatalha.add(painelBatalha1, gbc);

        gbc.gridx = 1;
        panelBatalha.add(painelBatalha2, gbc);

        add(panelBatalha, BorderLayout.CENTER);
        add(respostaLabel, BorderLayout.SOUTH);
        add(panelLabels, BorderLayout.NORTH);

        setVisible(true);
    }

    @Override
    public void notify(ObservadoIF o) {
        new JanelaFimDeJogo("Luis");
        tabuleiro1.limpar();
        tabuleiro2.limpar();
        tabuleiroOculto1.limpar();
        tabuleiroOculto2.limpar();
        this.dispose();
    }

}
