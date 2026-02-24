package view;

import javax.swing.*;
import java.awt.*;
import model.Cliente;
import rmi.FarmaciaInterface;

public class ClienteView extends JFrame {
    private JTextField txtNome, txtCpf, txtTelefone, txtEndereco;
    private JButton btnCadastrar;

    public ClienteView() {
        setTitle("Cadastro de Cliente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Cadastro de Cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Ubuntu", Font.BOLD, 18));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        formPanel.add(txtNome);

        formPanel.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        formPanel.add(txtCpf);

        formPanel.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        formPanel.add(txtTelefone);

        formPanel.add(new JLabel("Endereço:"));
        txtEndereco = new JTextField();
        formPanel.add(txtEndereco);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnCadastrar = new JButton("Cadastrar");

        buttonPanel.add(btnCadastrar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> cadastrarCliente());

        add(mainPanel);
    }

    private void cadastrarCliente() {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String telefone = txtTelefone.getText();
            String endereco = txtEndereco.getText();

            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!");
                return;
            }

            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setCpf(cpf);
            cliente.setTelefone(telefone);
            cliente.setEndereco(endereco);

            boolean sucesso = view.Cliente.servico.cadastrar_Cliente(cliente);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao cadastrar cliente!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
    }
}