package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Model.Tabuleiro;
import java.util.function.Consumer;

public class JanelaPosicionamento extends JFrame {
    private PainelPosicionamento painelJogador1;
    private PainelPosicionamento painelJogador2;
    private boolean vezJogador1 = true;
    private Tabuleiro tabuleiroJogador1;
    private Tabuleiro tabuleiroJogador2;
    private JButton btnTrocarJogador;
    private Consumer<Void> onFinish;

    public JanelaPosicionamento(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2, String nomeJogador1, String nomeJogador2, Consumer<Void> onFinish) {
        this.tabuleiroJogador1 = tabuleiro1;
        this.tabuleiroJogador2 = tabuleiro2;
        this.onFinish = onFinish;

        painelJogador1 = new PainelPosicionamento(tabuleiro1, this, nomeJogador1);
        painelJogador2 = new PainelPosicionamento(tabuleiro2, this, nomeJogador2);

        setTitle("Batalha Naval - Posicionamento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new CardLayout());
        mainPanel.add(painelJogador1, "Jogador1");
        mainPanel.add(painelJogador2, "Jogador2");

        btnTrocarJogador = new JButton("Trocar Jogador");
        btnTrocarJogador.addActionListener(e -> trocarJogador(mainPanel));

        add(mainPanel, BorderLayout.CENTER);
        add(btnTrocarJogador, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        painelJogador1.requestFocusForTabuleiro();
    }

    private void trocarJogador(JPanel mainPanel) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();

        PainelPosicionamento painelAtual = vezJogador1 ? painelJogador1 : painelJogador2;
        if (painelAtual.todosNaviosPosicionados()) {
            vezJogador1 = !vezJogador1;
            cl.next(mainPanel);

            if (!vezJogador1) {
                btnTrocarJogador.setText("Iniciar Batalha");
                btnTrocarJogador.setEnabled(false);
                iniciarVerificacao();
                btnTrocarJogador.addActionListener(e -> finalizarPosicionamento());
            } else {
                painelJogador2.requestFocusForTabuleiro();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Posicione todos os navios antes de trocar de jogador!");
            painelAtual.requestFocusForTabuleiro();
        }
    }

    private void iniciarVerificacao() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (painelJogador2.todosNaviosPosicionados()) {
                    btnTrocarJogador.setEnabled(true);
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    private void finalizarPosicionamento() {
        this.dispose();
        onFinish.accept(null);
    }

    public PainelPosicionamento getPainelJogador1() {
        return painelJogador1;
    }

    public PainelPosicionamento getPainelJogador2() {
        return painelJogador2;
    }
}
