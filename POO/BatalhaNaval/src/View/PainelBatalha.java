package View;

import javax.swing.*;
import java.awt.*;
import Model.Tabuleiro;

public class PainelBatalha extends JPanel {
    private ComponenteTabuleiro componenteTabuleiroOcultoJogador1;
    private ComponenteTabuleiro componenteTabuleiroOcultoJogador2;
    private Tabuleiro tabuleiroJogador1;
    private Tabuleiro tabuleiroJogador2;
    private Tabuleiro tabuleiroOcultoJogador1;
    private Tabuleiro tabuleiroOcultoJogador2;
    private JLabel labelTurno;
    private boolean vezJogador1 = true;
    private String nomeJogador1;
    private String nomeJogador2;

    public PainelBatalha(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, Tabuleiro tabuleiroOculto1, Tabuleiro tabuleiroOculto2, String nomeJogador1, String nomeJogador2) {
        this.tabuleiroJogador1 = tabuleiro1;
        this.tabuleiroJogador2 = tabuleiro2;
        this.tabuleiroOcultoJogador1 = tabuleiroOculto1;
        this.tabuleiroOcultoJogador2 = tabuleiroOculto2;
        this.nomeJogador1 = nomeJogador1;
        this.nomeJogador2 = nomeJogador2;

        setLayout(new BorderLayout());

        // Cria os componentes de tabuleiro
        componenteTabuleiroOcultoJogador1 = new ComponenteTabuleiro(15, 30, tabuleiroOcultoJogador1, null);
        componenteTabuleiroOcultoJogador2 = new ComponenteTabuleiro(15, 30, tabuleiroOcultoJogador2, null);

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

        // Label para exibir de quem é a vez
        labelTurno = new JLabel("Turno de " + nomeJogador1);
        labelTurno.setHorizontalAlignment(JLabel.CENTER);

        // Adiciona componentes ao painel principal
        add(panelTabuleiros, BorderLayout.CENTER);
        add(labelTurno, BorderLayout.NORTH);

        // Botão para realizar ataque
        JButton btnAtacar = new JButton("Atacar");
        btnAtacar.addActionListener(e -> realizarAtaque());

        add(btnAtacar, BorderLayout.SOUTH);
    }

    private void realizarAtaque() {
        // Solicita as coordenadas do ataque
        String input = JOptionPane.showInputDialog(this, "Digite as coordenadas de ataque (formato: linha,coluna):");
        if (input == null || !input.matches("\\d+,\\d+")) {
            JOptionPane.showMessageDialog(this, "Coordenadas inválidas! Tente novamente.");
            return;
        }

        String[] partes = input.split(",");
        int linha = Integer.parseInt(partes[0]);
        int coluna = Integer.parseInt(partes[1]);

        String resultado;
        if (vezJogador1) {
            // Jogador 1 ataca o tabuleiro oculto do jogador 2
            resultado = tabuleiroJogador2.atacar(linha, coluna, tabuleiroOcultoJogador2);
        } else {
            // Jogador 2 ataca o tabuleiro oculto do jogador 1
            resultado = tabuleiroJogador1.atacar(linha, coluna, tabuleiroOcultoJogador1);
        }

        JOptionPane.showMessageDialog(this, resultado);

        // Alterna a vez dos jogadores
        vezJogador1 = !vezJogador1;
        labelTurno.setText("Turno de " + (vezJogador1 ? nomeJogador1 : nomeJogador2));

        // Atualiza a interface
        componenteTabuleiroOcultoJogador1.repaint();
        componenteTabuleiroOcultoJogador2.repaint();
    }
}
