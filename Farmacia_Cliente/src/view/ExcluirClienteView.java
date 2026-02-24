package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Cliente;

public class ExcluirClienteView extends JFrame {
    private JComboBox<String> cmbClientes;
    private JButton btnExcluir, btnAtualizar;
    private List<Cliente> clientesList;

    public ExcluirClienteView() {
        setTitle("Excluir Cliente");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Excluir Cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Ubuntu", Font.BOLD, 18));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        formPanel.add(new JLabel("Selecione o Cliente:"));
        cmbClientes = new JComboBox<>();
        formPanel.add(cmbClientes);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");

        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnExcluir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> carregarClientes());
        btnExcluir.addActionListener(e -> excluirCliente());

        carregarClientes();

        add(mainPanel);
    }

    private void carregarClientes() {
        try {
            clientesList = view.Cliente.servico.listarClientes();
            
            cmbClientes.removeAllItems();
            
            if (clientesList.isEmpty()) {
                cmbClientes.addItem("Nenhum cliente cadastrado");
            } else {
                for (Cliente c : clientesList) {
                    cmbClientes.addItem(c.getNome());
                }
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }

    private void excluirCliente() {
        try {
            String nomeCliente = (String) cmbClientes.getSelectedItem();
            
            if (nomeCliente == null || nomeCliente.equals("Nenhum cliente cadastrado")) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente válido!");
                return;
            }
            
            boolean sucesso = view.Cliente.servico.excluir_Cliente(nomeCliente);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                carregarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao excluir cliente!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + ex.getMessage());
        }
    }
}