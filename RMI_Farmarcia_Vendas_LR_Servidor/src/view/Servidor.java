package view;

import java.rmi.AlreadyBoundException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import controller.FarmaciaController;
import rmi.FarmaciaInterface;

public class Servidor {
    public static void main(String[] args) {
        try {
            Registry conexao = LocateRegistry.createRegistry(1100);
            System.out.println("Registro de conexão iniciado!");
            FarmaciaInterface servicoFarmacia = new FarmaciaController();
            System.out.println("Serviço em espera!");
            conexao.bind("farmacia", servicoFarmacia);
        } catch(RemoteException e) {
            System.out.println("Erro na conexão ou serviço: " + e.getMessage());
        } catch(AlreadyBoundException e) {
            System.out.println("Erro no registro do serviço: " + e.getMessage());
        }
    }
}




