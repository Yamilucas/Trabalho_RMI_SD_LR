package view;

import javax.swing.*;
import java.awt.*;
import model.Produto;
import java.util.List;

public class ConsultaProdutoView extends JFrame {
    private JComboBox<String> cmbProdutos;
    private JLabel lblNome, lblPreco, lblQuantidade;
    private JButton btnConsultar, btnAtualizar;
    private List<Produto> produtosList;

    public ConsultaProdutoView() {
        setTitle("Consulta de Produto");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Consulta de Produto", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Ubuntu", Font.BOLD, 18));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        formPanel.add(new JLabel("Selecione o Produto:"));
        cmbProdutos = new JComboBox<>();
        formPanel.add(cmbProdutos);

        formPanel.add(new JLabel("Nome:"));
        lblNome = new JLabel("---");
        lblNome.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        formPanel.add(lblNome);

        formPanel.add(new JLabel("Preço:"));
        lblPreco = new JLabel("---");
        lblPreco.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        formPanel.add(lblPreco);

        formPanel.add(new JLabel("Quantidade em Estoque:"));
        lblQuantidade = new JLabel("---");
        lblQuantidade.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        formPanel.add(lblQuantidade);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAtualizar = new JButton("Atualizar Lista");
        btnConsultar = new JButton("Consultar");

        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnConsultar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> carregarProdutos());
        btnConsultar.addActionListener(e -> consultarProduto());

        carregarProdutos();

        add(mainPanel);
    }

    private void carregarProdutos() {
        try {
            produtosList = view.Cliente.servico.listarProdutos();
            
            cmbProdutos.removeAllItems();
            
            if (produtosList.isEmpty()) {
                cmbProdutos.addItem("Nenhum produto cadastrado");
                JOptionPane.showMessageDialog(this, 
                    "Nenhum produto cadastrado no sistema!\nCadastre produtos primeiro.");
            } else {
                for (Produto p : produtosList) {
                    cmbProdutos.addItem(p.getNome());
                }
                JOptionPane.showMessageDialog(this, 
                    produtosList.size() + " produtos carregados com sucesso!");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + ex.getMessage());
        }
    }

    private void consultarProduto() {
        try {
            String nomeProduto = (String) cmbProdutos.getSelectedItem();
            
            if (nomeProduto == null || nomeProduto.equals("Nenhum produto cadastrado")) {
                JOptionPane.showMessageDialog(this, "Selecione um produto válido!");
                return;
            }
            
            Produto produtoSelecionado = view.Cliente.servico.buscarProdutoPorNome(nomeProduto);
            
            if (produtoSelecionado != null) {
                // Atualizar labels com as informações do produto
                lblNome.setText(produtoSelecionado.getNome());
                lblPreco.setText(String.format("R$ %.2f", produtoSelecionado.getPreco()));
                lblQuantidade.setText(String.valueOf(produtoSelecionado.getQuantidade()));
                
                // Destacar estoque baixo
                if (produtoSelecionado.getQuantidade() < 10) {
                    lblQuantidade.setForeground(Color.RED);
                    lblQuantidade.setText(produtoSelecionado.getQuantidade() + " (ESTOQUE BAIXO!)");
                } else {
                    lblQuantidade.setForeground(Color.BLACK);
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado!");
                limparCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar produto: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        lblNome.setText("---");
        lblPreco.setText("---");
        lblQuantidade.setText("---");
        lblQuantidade.setForeground(Color.BLACK);
    }
}