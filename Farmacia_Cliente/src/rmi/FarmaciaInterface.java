package rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Cliente;
import model.Funcionario;
import model.Venda;
import model.Produto;
import java.util.List;

public interface FarmaciaInterface extends Remote {
    public boolean cadastrar_Cliente(Cliente c) throws RemoteException;
    public boolean cadastrar_Funcionario(Funcionario f) throws RemoteException;
    public boolean cadastrar_Produto(Produto p) throws RemoteException;
    public boolean Processar_Venda(Venda v) throws RemoteException;
    public boolean Mostrar_informacoes_atuais_produto(Produto p) throws RemoteException;
    
    public List<Cliente> listarClientes() throws RemoteException;
    public List<Funcionario> listarFuncionarios() throws RemoteException;
    public List<Produto> listarProdutos() throws RemoteException;
    public List<Venda> listarVendas() throws RemoteException;
    
    public Cliente buscarClientePorNome(String nome) throws RemoteException;
    public Funcionario buscarFuncionarioPorNome(String nome) throws RemoteException;
    public Produto buscarProdutoPorNome(String nome) throws RemoteException;
    
    public boolean excluir_Cliente(String nome) throws RemoteException;
    public boolean excluir_Funcionario(String nome) throws RemoteException;
    public boolean excluir_Produto(String nome) throws RemoteException;
}