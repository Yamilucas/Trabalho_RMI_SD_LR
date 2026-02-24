package view;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi.FarmaciaInterface;
import javax.swing.JOptionPane;

public class Cliente {
    public static FarmaciaInterface servico;

    public static void main(String[] args) {
        try {
            Registry conexao = LocateRegistry.getRegistry("127.0.0.1", 1100);
            servico = (FarmaciaInterface) conexao.lookup("farmacia");
            new MenuPrincipal().setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor: " + e.getMessage());
        }
    }
}