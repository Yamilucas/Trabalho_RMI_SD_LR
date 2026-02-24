package view;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import model.Cliente;
import model.Funcionario;
import model.Produto;
import model.Venda;

public class VendaView extends JFrame {
    private JComboBox<String> cmbClientes, cmbFuncionarios, cmbProdutos;
    private JTextField txtQuantidade;
    private JButton btnProcessar, btnAtualizar;
    private List<Cliente> clientesList;
    private List<Funcionario> funcionariosList;
    private List<Produto> produtosList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public VendaView() {
        setTitle("Processar Venda");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Processar Venda", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Ubuntu", Font.BOLD, 18));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        formPanel.add(new JLabel("Cliente:"));
        cmbClientes = new JComboBox<>();
        formPanel.add(cmbClientes);

        formPanel.add(new JLabel("Funcionário:"));
        cmbFuncionarios = new JComboBox<>();
        formPanel.add(cmbFuncionarios);

        formPanel.add(new JLabel("Produto:"));
        cmbProdutos = new JComboBox<>();
        formPanel.add(cmbProdutos);

        formPanel.add(new JLabel("Quantidade:"));
        txtQuantidade = new JTextField();
        formPanel.add(txtQuantidade);

        formPanel.add(new JLabel("Data:"));
        JLabel lblDataAtual = new JLabel(dateFormat.format(new Date()));
        formPanel.add(lblDataAtual);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAtualizar = new JButton("Atualizar");
        btnProcessar = new JButton("Processar Venda");

        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnProcessar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> carregarDados());
        btnProcessar.addActionListener(e -> processarVenda());

        carregarDados();

        add(mainPanel);
    }

    private void carregarDados() {
        try {
            clientesList = view.Cliente.servico.listarClientes();
            funcionariosList = view.Cliente.servico.listarFuncionarios();
            produtosList = view.Cliente.servico.listarProdutos();

            cmbClientes.removeAllItems();
            cmbFuncionarios.removeAllItems();
            cmbProdutos.removeAllItems();

            for (Cliente c : clientesList) {
                cmbClientes.addItem(c.getNome());
            }

            for (Funcionario f : funcionariosList) {
                cmbFuncionarios.addItem(f.getNome());
            }

            for (Produto p : produtosList) {
                cmbProdutos.addItem(p.getNome());
            }

            if (clientesList.isEmpty() || funcionariosList.isEmpty() || produtosList.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Cadastre clientes, funcionários e produtos antes de processar vendas!");
            } else {
                JOptionPane.showMessageDialog(this, "Dados carregados com sucesso!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void processarVenda() {
        try {
            String nomeCliente = (String) cmbClientes.getSelectedItem();
            String nomeFuncionario = (String) cmbFuncionarios.getSelectedItem();
            String nomeProduto = (String) cmbProdutos.getSelectedItem();
            int quantidade = Integer.parseInt(txtQuantidade.getText());
            Date data = new Date();

            if (nomeCliente == null || nomeFuncionario == null || nomeProduto == null) {
                JOptionPane.showMessageDialog(this, "Selecione cliente, funcionário e produto!");
                return;
            }

            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero!");
                return;
            }

            Cliente cliente = view.Cliente.servico.buscarClientePorNome(nomeCliente);
            Funcionario funcionario = view.Cliente.servico.buscarFuncionarioPorNome(nomeFuncionario);
            Produto produto = view.Cliente.servico.buscarProdutoPorNome(nomeProduto);

            if (cliente == null || funcionario == null || produto == null) {
                JOptionPane.showMessageDialog(this, "Cliente, funcionário ou produto não encontrado!");
                return;
            }

            Venda venda = new Venda();
            venda.setIdCliente(cliente.getId());
            venda.setIdFuncionario(funcionario.getId());
            venda.setIdProduto(produto.getId());
            venda.setQuantidade(quantidade);
            venda.setData(data);
            
            boolean sucesso = view.Cliente.servico.Processar_Venda(venda);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Venda processada com sucesso!");
                limparCampos();
                carregarDados(); 
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao processar venda. Verifique o estoque!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser um número válido!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao processar venda: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtQuantidade.setText("");
        if (cmbClientes.getItemCount() > 0) cmbClientes.setSelectedIndex(0);
        if (cmbFuncionarios.getItemCount() > 0) cmbFuncionarios.setSelectedIndex(0);
        if (cmbProdutos.getItemCount() > 0) cmbProdutos.setSelectedIndex(0);
    }
}