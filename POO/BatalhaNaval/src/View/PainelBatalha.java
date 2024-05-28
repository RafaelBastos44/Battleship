package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Model.Tabuleiro;
import Controller.AtaqueListener;
import Controller.BatalhaNaval;

public class PainelBatalha extends JPanel implements AtaqueListener {
    private ComponenteTabuleiro componenteTabuleiroOcultoJogador1;
    private ComponenteTabuleiro componenteTabuleiroOcultoJogador2;
    private Tabuleiro tabuleiroJogador1;
    private Tabuleiro tabuleiroJogador2;
    private Tabuleiro tabuleiroOcultoJogador1;
    private Tabuleiro tabuleiroOcultoJogador2;
    private JLabel labelTurno;
    private JLabel labelAtaquesRestantes;
    private JLabel labelAcerto;
    private String nomeJogador1;
    private String nomeJogador2;
    private String resultado;
    private BatalhaNaval batalhaNaval;

    public PainelBatalha(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, String nomeJogador1, String nomeJogador2, BatalhaNaval batalhaNaval) {
        this.tabuleiroJogador1 = tabuleiro1;
        this.tabuleiroJogador2 = tabuleiro2;
        this.tabuleiroOcultoJogador1 = tabuleiroOculto1;
        this.tabuleiroOcultoJogador2 = tabuleiroOculto2;
        this.nomeJogador1 = nomeJogador1;
        this.nomeJogador2 = nomeJogador2;
        this.batalhaNaval = batalhaNaval;

        setLayout(new BorderLayout());

        // Cria os componentes de tabuleiro
        componenteTabuleiroOcultoJogador1 = new ComponenteTabuleiro(15, 30, tabuleiroOcultoJogador1, null, false, batalhaNaval);
        componenteTabuleiroOcultoJogador2 = new ComponenteTabuleiro(15, 30, tabuleiroOcultoJogador2, null, false, batalhaNaval);

        // Painel para os tabuleiros
        JPanel panelTabuleiros = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelTabuleiros.add(componenteTabuleiroOcultoJogador1, gbc);

        gbc.gridx = 1;
        panelTabuleiros.add(componenteTabuleiroOcultoJogador2, gbc);

        // Labels para exibir informações
        labelTurno = new JLabel("Turno de " + nomeJogador1);
        labelTurno.setHorizontalAlignment(JLabel.CENTER);

        labelAtaquesRestantes = new JLabel("Ataques restantes: " + batalhaNaval.getAtaquesRestantes());
        labelAtaquesRestantes.setHorizontalAlignment(JLabel.CENTER);

        labelAcerto = new JLabel(resultado);
        labelAcerto.setHorizontalAlignment(JLabel.CENTER);

        // Painel para os labels
        JPanel panelLabels = new JPanel(new GridLayout(2, 1));
        panelLabels.add(labelTurno);
        panelLabels.add(labelAtaquesRestantes);

        JPanel panelResultado = new JPanel();
        panelResultado.add(labelAcerto);

        // Adiciona componentes ao painel principal
        add(panelTabuleiros, BorderLayout.CENTER);
        add(panelLabels, BorderLayout.NORTH);
        add(panelResultado, BorderLayout.SOUTH);
    }

    @Override
    public void onAtaque(int linha, int coluna, String resultado, int ataquesRestantes) {
        labelAcerto.setText(resultado);

        // Atualiza os labels
        labelTurno.setText("Turno de " + (batalhaNaval.isVezJogador1() ? nomeJogador1 : nomeJogador2));
        labelAtaquesRestantes.setText("Ataques restantes: " + ataquesRestantes);

        // Atualiza os componentes do tabuleiro
        componenteTabuleiroOcultoJogador1.repaint();
        componenteTabuleiroOcultoJogador2.repaint();
    }

    public ComponenteTabuleiro getComponenteTabuleiroBatalhaJogador1() {
        return componenteTabuleiroOcultoJogador1;
    }

    public ComponenteTabuleiro getComponenteTabuleiroBatalhaJogador2() {
        return componenteTabuleiroOcultoJogador2;
    }
    
}
