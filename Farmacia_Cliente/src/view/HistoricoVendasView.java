package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import model.Venda;
import model.Cliente;
import model.Funcionario;
import model.Produto;

public class HistoricoVendasView extends JFrame {
    private JTable tabelaVendas;
    private JButton btnAtualizar;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public HistoricoVendasView() {
        setTitle("Histórico de Vendas");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Histórico de Vendas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Ubuntu", Font.BOLD, 18));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // Tabela
        tabelaVendas = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabelaVendas);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAtualizar = new JButton("Atualizar");

        buttonPanel.add(btnAtualizar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> carregarVendas());

        carregarVendas();

        add(mainPanel);
    }

    private void carregarVendas() {
        try {
            List<Venda> vendas = view.Cliente.servico.listarVendas();

            String[] colunas = {"ID", "Cliente", "Funcionário", "Produto", "Quantidade", "Data"};
            DefaultTableModel model = new DefaultTableModel(colunas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (Venda v : vendas) {
                String nomeCliente = "N/A";
                String nomeFuncionario = "N/A";
                String nomeProduto = "N/A";

                try {
                    if (v.getIdCliente() != 0) {
                        Cliente cliente = view.Cliente.servico.buscarClientePorNome(
                            getClienteNomePord(v.getIdCliente())
                        );
                        nomeCliente = cliente != null ? cliente.getNome() : "Cliente Excluído";
                    }

                    if (v.getIdFuncionario() != 0) {
                        Funcionario funcionario = view.Cliente.servico.buscarFuncionarioPorNome(
                            getFuncionarioNomePorId(v.getIdFuncionario())
                        );
                        nomeFuncionario = funcionario != null ? funcionario.getNome() : "Funcionário Excluído";
                    }

                    if (v.getIdProduto() != 0) {
                        Produto produto = view.Cliente.servico.buscarProdutoPorNome(
                            getProdutoNomePorId(v.getIdProduto())
                        );
                        nomeProduto = produto != null ? produto.getNome() : "Produto Excluído";
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao buscar nomes: " + e.getMessage());
                }

                Object[] row = {
                    v.getId(),
                    nomeCliente,
                    nomeFuncionario,
                    nomeProduto,
                    v.getQuantidade(),
                    dateFormat.format(v.getData())
                };
                model.addRow(row);
            }

            tabelaVendas.setModel(model);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar vendas: " + ex.getMessage());
        }
    }

    private String getClienteNomePord(int id) {
        try {
            List<Cliente> clientes = view.Cliente.servico.listarClientes();
            for (Cliente c : clientes) {
                if (c.getId() == id) return c.getNome();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getFuncionarioNomePorId(int id) {
        try {
            List<Funcionario> funcionarios = view.Cliente.servico.listarFuncionarios();
            for (Funcionario f : funcionarios) {
                if (f.getId() == id) return f.getNome();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getProdutoNomePorId(int id) {
        try {
            List<Produto> produtos = view.Cliente.servico.listarProdutos();
            for (Produto p : produtos) {
                if (p.getId() == id) return p.getNome();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}