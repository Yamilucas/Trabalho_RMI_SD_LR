package view;

import javax.swing.*;
import java.awt.*;
import model.Funcionario;

public class FuncionarioView extends JFrame {
    private JTextField txtNome, txtCpf, txtTelefone, txtCargo;
    private JButton btnCadastrar;

    public FuncionarioView() {
        setTitle("Cadastro de Funcionário");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Cadastro de Funcionário", SwingConstants.CENTER);
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

        formPanel.add(new JLabel("Cargo:"));
        txtCargo = new JTextField();
        formPanel.add(txtCargo);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnCadastrar = new JButton("Cadastrar");
        
        buttonPanel.add(btnCadastrar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> cadastrarFuncionario());

        add(mainPanel);
    }

    private void cadastrarFuncionario() {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String telefone = txtTelefone.getText();
            String cargo = txtCargo.getText();

            if (nome.isEmpty() || cpf.isEmpty() || cargo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome, CPF e Cargo são obrigatórios!");
                return;
            }

            Funcionario funcionario = new Funcionario();
            funcionario.setNome(nome);
            funcionario.setCpf(cpf);
            funcionario.setTelefone(telefone);
            funcionario.setCargo(cargo);

            boolean sucesso = view.Cliente.servico.cadastrar_Funcionario(funcionario);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao cadastrar funcionário!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtCargo.setText("");
    }
}