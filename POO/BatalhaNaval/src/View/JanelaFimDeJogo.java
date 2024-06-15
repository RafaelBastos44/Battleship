package View;

import javax.swing.*;

import Controller.ControllerAPI;

import java.awt.*;

public class JanelaFimDeJogo extends JFrame {
    private JLabel mensagemVencedor;
    private JButton botaoJogarNovamente;
    private JButton botaoEncerrar;

    public JanelaFimDeJogo(String vencedor) {
        setTitle("Fim de Jogo");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mensagemVencedor = new JLabel(vencedor + " venceu a batalha!", SwingConstants.CENTER);
        mensagemVencedor.setFont(new Font("Arial", Font.BOLD, 16));
        add(mensagemVencedor, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());

        botaoJogarNovamente = new JButton("Jogar Novamente");
        botaoJogarNovamente.addActionListener(e -> {
            this.dispose();
            Model.Salvamento.deletaEstadoJogo();
            ControllerAPI.getInstance().inicializa(); 
        });
        painelBotoes.add(botaoJogarNovamente);

        botaoEncerrar = new JButton("Encerrar");
        botaoEncerrar.addActionListener(e -> {
            Model.Salvamento.deletaEstadoJogo();
            System.exit(0);
        });
        painelBotoes.add(botaoEncerrar);

        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }
}