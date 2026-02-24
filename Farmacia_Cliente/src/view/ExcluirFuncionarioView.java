package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Funcionario;

public class ExcluirFuncionarioView extends JFrame {
    private JComboBox<String> cmbFuncionarios;
    private JButton btnExcluir, btnAtualizar;
    private List<Funcionario> funcionariosList;

    public ExcluirFuncionarioView() {
        setTitle("Excluir Funcionário");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Excluir Funcionário", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Ubuntu", Font.BOLD, 18));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        formPanel.add(new JLabel("Selecione o Funcionário:"));
        cmbFuncionarios = new JComboBox<>();
        formPanel.add(cmbFuncionarios);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAtualizar = new JButton("Atualizar Lista");
        btnExcluir = new JButton("Excluir");

        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnExcluir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> carregarFuncionarios());
        btnExcluir.addActionListener(e -> excluirFuncionario());

        carregarFuncionarios();

        add(mainPanel);
    }

    private void carregarFuncionarios() {
        try {
            funcionariosList = view.Cliente.servico.listarFuncionarios();
            
            cmbFuncionarios.removeAllItems();
            
            if (funcionariosList.isEmpty()) {
                cmbFuncionarios.addItem("Nenhum funcionário cadastrado");
            } else {
                for (Funcionario f : funcionariosList) {
                    cmbFuncionarios.addItem(f.getNome());
                }
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar funcionários: " + ex.getMessage());
        }
    }

    private void excluirFuncionario() {
        try {
            String nomeFuncionario = (String) cmbFuncionarios.getSelectedItem();
            
            if (nomeFuncionario == null || nomeFuncionario.equals("Nenhum funcionário cadastrado")) {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário válido!");
                return;
            }
            
            boolean sucesso = view.Cliente.servico.excluir_Funcionario(nomeFuncionario);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                carregarFuncionarios();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao excluir funcionário!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir funcionário: " + ex.getMessage());
        }
    }
}