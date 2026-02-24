package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Produto;

public class ExcluirProdutoView extends JFrame {
    private JComboBox<String> cmbProdutos;
    private JButton btnExcluir, btnAtualizar;
    private List<Produto> produtosList;

    public ExcluirProdutoView() {
        setTitle("Excluir Produto");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Excluir Produto", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Ubuntu", Font.BOLD, 18));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        formPanel.add(new JLabel("Selecione o Produto:"));
        cmbProdutos = new JComboBox<>();
        formPanel.add(cmbProdutos);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");

        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnExcluir);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> carregarProdutos());
        btnExcluir.addActionListener(e -> excluirProduto());

        carregarProdutos();

        add(mainPanel);
    }

    private void carregarProdutos() {
        try {
            produtosList = view.Cliente.servico.listarProdutos();
            
            cmbProdutos.removeAllItems();
            
            if (produtosList.isEmpty()) {
                cmbProdutos.addItem("Nenhum produto cadastrado");
            } else {
                for (Produto p : produtosList) {
                    cmbProdutos.addItem(p.getNome());
                }
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + ex.getMessage());
        }
    }

    private void excluirProduto() {
        try {
            String nomeProduto = (String) cmbProdutos.getSelectedItem();
            
            if (nomeProduto == null || nomeProduto.equals("Nenhum produto cadastrado")) {
                JOptionPane.showMessageDialog(this, "Selecione um produto válido!");
                return;
            }
            
            boolean sucesso = view.Cliente.servico.excluir_Produto(nomeProduto);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
                carregarProdutos();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao excluir produto! Verifique se não há vendas associadas.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir produto: " + ex.getMessage());
        }
    }
}