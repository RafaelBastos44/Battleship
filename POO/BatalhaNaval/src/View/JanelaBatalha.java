package View;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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
    private String[] nomes;
    private int jogador = 1;
    private int[] ataques = {1};
    

    public JanelaBatalha(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, String[] Nomes, boolean turno, int ataque) {
        this.tabuleiro1 = tabuleiro1;
        this.tabuleiro2 = tabuleiro2;
        this.tabuleiroOculto1 = tabuleiroOculto1;
        this.tabuleiroOculto2 = tabuleiroOculto2;
        this.nomes = Nomes;
        vezJogador1[0] = turno;
        this.ataques[0] = ataque;
   
        jogador = verificaJogador(vezJogador1);

        System.out.println(ataque);

        setTitle("Batalha Naval - Batalha");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel panelBatalha = new JPanel(new GridBagLayout());
        setSize(960, 610);

        JLabel respostaLabel = new JLabel("");
        respostaLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel ataquesLabel = new JLabel("Ataques Restantes: " + ataque);
        ataquesLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel jogadorLabel = new JLabel("Vez de " + Nomes[jogador-1]);
        jogadorLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton btnSalvar = new JButton("Salvar");
        JPanel panelLabels = new JPanel(new GridLayout(3, 1));
        panelLabels.add(jogadorLabel);
        panelLabels.add(ataquesLabel);
        panelLabels.add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarJogo(ataques));


        PainelTabuleiroBatalha painelBatalha1 = new PainelTabuleiroBatalha(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, 2, vezJogador1, respostaLabel, jogadorLabel, ataquesLabel, Nomes, ataques);
        PainelTabuleiroBatalha painelBatalha2 = new PainelTabuleiroBatalha(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, 1, vezJogador1, respostaLabel, jogadorLabel, ataquesLabel, Nomes, ataques);
        
        this.ataques[0] = ataque;

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


        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void salvarJogo(int[] ataque){
        if(ataque[0] != 3){
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(null);
        File selectedFile = fileChooser.getSelectedFile();
        Model.Salvamento.gravarEstadoJogo(tabuleiro1, tabuleiro2, tabuleiroOculto1, tabuleiroOculto2, vezJogador1[0], ataques[0], nomes, selectedFile);
    }

    @Override
    public void notify(ObservadoIF o) {
        new JanelaFimDeJogo(nomes[verificaJogador(vezJogador1)-1]);
        tabuleiro1.limpar();
        tabuleiro2.limpar();
        tabuleiroOculto1.limpar();
        tabuleiroOculto2.limpar();
        this.dispose();
    }

    public int verificaJogador(boolean[] vezJogador1){
        if(vezJogador1[0] == true){
            return 1;
        }
        else{
            return 2;
        }
    }

}
