package view;

import javax.swing.*;
import java.awt.*;
import model.Produto;

public class ProdutoView extends JFrame {
    private JTextField txtNome, txtPreco, txtQuantidade;
    private JButton btnCadastrar;

    public ProdutoView() {
        setTitle("Cadastro de Produto");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Cadastro de Produto", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Ubuntu", Font.BOLD, 18));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        formPanel.add(txtNome);

        formPanel.add(new JLabel("Preço:"));
        txtPreco = new JTextField();
        formPanel.add(txtPreco);

        formPanel.add(new JLabel("Quantidade:"));
        txtQuantidade = new JTextField();
        formPanel.add(txtQuantidade);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnCadastrar = new JButton("Cadastrar");

        buttonPanel.add(btnCadastrar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> cadastrarProduto());

        add(mainPanel);
    }

    private void cadastrarProduto() {
        try {
            String nome = txtNome.getText();
            double preco = Double.parseDouble(txtPreco.getText());
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            if (nome.isEmpty() || preco <= 0 || quantidade < 0) {
                JOptionPane.showMessageDialog(this, "Dados inválidos! Verifique os valores.");
                return;
            }

            Produto produto = new Produto();
            produto.setNome(nome);
            produto.setPreco(preco);
            produto.setQuantidade(quantidade);

            boolean sucesso = view.Cliente.servico.cadastrar_Produto(produto);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao cadastrar produto!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço e Quantidade devem ser números válidos!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtPreco.setText("");
        txtQuantidade.setText("");
    }
}