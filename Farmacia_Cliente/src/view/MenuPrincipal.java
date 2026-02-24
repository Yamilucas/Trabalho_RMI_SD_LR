package view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private JButton btnCadastrarCliente;
    private JButton btnCadastrarFuncionario;
    private JButton btnCadastrarProduto;
    private JButton btnProcessarVenda;
    private JButton btnConsultarProduto;
    private JButton btnExcluirCliente;
    private JButton btnExcluirFuncionario;
    private JButton btnExcluirProduto;
    private JButton btnHistoricoVendas;
    private JLabel lblTitulo;

    public MenuPrincipal() {
        setTitle("Sistema Farmácia - Cliente RMI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        lblTitulo = new JLabel("Sistema de Farmácia");
        lblTitulo.setFont(new Font("Ubuntu", Font.BOLD, 24));
        
        btnCadastrarCliente = new JButton("Cadastrar Cliente");
        btnCadastrarFuncionario = new JButton("Cadastrar Funcionário");
        btnCadastrarProduto = new JButton("Cadastrar Produto");
        btnProcessarVenda = new JButton("Processar Venda");
        btnConsultarProduto = new JButton("Consultar Produto");
        btnExcluirCliente = new JButton("Excluir Cliente");
        btnExcluirFuncionario = new JButton("Excluir Funcionário");
        btnExcluirProduto = new JButton("Excluir Produto");
        btnHistoricoVendas = new JButton("Histórico de Vendas");

        Dimension buttonSize = new Dimension(200, 40);
        btnCadastrarCliente.setPreferredSize(buttonSize);
        btnCadastrarFuncionario.setPreferredSize(buttonSize);
        btnCadastrarProduto.setPreferredSize(buttonSize);
        btnProcessarVenda.setPreferredSize(buttonSize);
        btnConsultarProduto.setPreferredSize(buttonSize);
        btnExcluirCliente.setPreferredSize(buttonSize);
        btnExcluirFuncionario.setPreferredSize(buttonSize);
        btnExcluirProduto.setPreferredSize(buttonSize);
        btnHistoricoVendas.setPreferredSize(buttonSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        mainPanel.add(lblTitulo, gbc);

        gbc.gridy = 1;
        mainPanel.add(btnCadastrarCliente, gbc);

        gbc.gridy = 2;
        mainPanel.add(btnCadastrarFuncionario, gbc);

        gbc.gridy = 3;
        mainPanel.add(btnCadastrarProduto, gbc);

        gbc.gridy = 4;
        mainPanel.add(btnProcessarVenda, gbc);

        gbc.gridy = 5;
        mainPanel.add(btnConsultarProduto, gbc);

        gbc.gridy = 6;
        mainPanel.add(btnExcluirCliente, gbc);

        gbc.gridy = 7;
        mainPanel.add(btnExcluirFuncionario, gbc);

        gbc.gridy = 8;
        mainPanel.add(btnExcluirProduto, gbc);

        gbc.gridy = 9;
        mainPanel.add(btnHistoricoVendas, gbc);

        // Ações dos botões com inicialização direta dos objetos
        btnCadastrarCliente.addActionListener(e -> new ClienteView().setVisible(true));
        btnCadastrarFuncionario.addActionListener(e -> new FuncionarioView().setVisible(true));
        btnCadastrarProduto.addActionListener(e -> new ProdutoView().setVisible(true));
        btnProcessarVenda.addActionListener(e -> new VendaView().setVisible(true));
        btnConsultarProduto.addActionListener(e -> new ConsultaProdutoView().setVisible(true));
        btnExcluirCliente.addActionListener(e -> new ExcluirClienteView().setVisible(true));
        btnExcluirFuncionario.addActionListener(e -> new ExcluirFuncionarioView().setVisible(true));
        btnExcluirProduto.addActionListener(e -> new ExcluirProdutoView().setVisible(true));
        btnHistoricoVendas.addActionListener(e -> new HistoricoVendasView().setVisible(true));

        add(mainPanel);
    }
}
