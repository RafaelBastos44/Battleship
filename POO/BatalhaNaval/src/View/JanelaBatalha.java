package View;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import Model.Tabuleiro;

class JanelaBatalha extends JFrame{
    private boolean[] vezJogador1 = {true};

    public JanelaBatalha(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2){
        setTitle("Batalha Naval - Batalha");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 600);
        add(new PainelTabuleiroBatalha(tabuleiro1, tabuleiroOculto1,2,vezJogador1), BorderLayout.WEST);
        add(new PainelTabuleiroBatalha(tabuleiro2, tabuleiroOculto2,1,vezJogador1), BorderLayout.EAST);

        setVisible(true);
    }
}
