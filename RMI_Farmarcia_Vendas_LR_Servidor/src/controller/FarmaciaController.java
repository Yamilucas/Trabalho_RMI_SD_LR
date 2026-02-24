package controller;

import rmi.FarmaciaInterface;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import model.Cliente;
import model.Funcionario;
import model.Produto;
import model.Venda;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.Conexao;

public class FarmaciaController extends UnicastRemoteObject implements FarmaciaInterface {

    public FarmaciaController() throws RemoteException {}

    @Override
    public boolean cadastrar_Cliente(Cliente c) throws RemoteException {
        boolean retorno = false;
        Conexao conn = new Conexao();
        
        System.out.println("Tentando cadastrar cliente: " + c.getNome());
        conn.conectar(); 
        String sql = "insert into cliente(nome, cpf, telefone, endereco) values (?,?,?,?)";
        try {
            PreparedStatement sentenca = conn.conector.prepareStatement(sql);
            sentenca.setString(1, c.getNome());
            sentenca.setString(2, c.getCpf());
            sentenca.setString(3, c.getTelefone());
            sentenca.setString(4, c.getEndereco());
            
            int linhasAfetadas = sentenca.executeUpdate();
            if(linhasAfetadas > 0) {
                retorno = true;
                System.out.println("Cliente cadastrado com sucesso!");
            }
        } catch(SQLException e) {
            System.out.println("Erro SQL ao cadastrar cliente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conn.desconectar();
        }
        return retorno;
    }

    @Override
    public boolean cadastrar_Funcionario(Funcionario f) throws RemoteException {
        boolean retorno = false;
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "insert into funcionario(nome, cpf, telefone, cargo) values (?,?,?,?)";
        try {
            PreparedStatement sentenca = conn.conector.prepareStatement(sql);
            sentenca.setString(1, f.getNome());
            sentenca.setString(2, f.getCpf());
            sentenca.setString(3, f.getTelefone());
            sentenca.setString(4, f.getCargo());
            int linhasAfetadas = sentenca.executeUpdate();
            if(linhasAfetadas > 0) {
                retorno = true;
            }
        } catch(SQLException e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
        }
        conn.desconectar();
        return retorno;
    }

    @Override
    public boolean cadastrar_Produto(Produto p) throws RemoteException {
        boolean retorno = false;
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "insert into produto(nome, preco, quantidade) values (?,?,?)";
        try {
            PreparedStatement sentenca = conn.conector.prepareStatement(sql);
            sentenca.setString(1, p.getNome());
            sentenca.setDouble(2, p.getPreco());
            sentenca.setInt(3, p.getQuantidade());
            int linhasAfetadas = sentenca.executeUpdate();
            if(linhasAfetadas > 0) {
                retorno = true;
            }
        } catch(SQLException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
        conn.desconectar();
        return retorno;
    }

    @Override
    public boolean Processar_Venda(Venda v) throws RemoteException {
    boolean retorno = false;
    Conexao conn = new Conexao();
    conn.conectar();

    try {
        conn.conector.setAutoCommit(false);

        String sqlVerificaEstoque = "select quantidade from produto where id = ?";
        PreparedStatement stmtEstoque = conn.conector.prepareStatement(sqlVerificaEstoque);
        stmtEstoque.setInt(1, v.getIdProduto());
        ResultSet rs = stmtEstoque.executeQuery();
        
        if (rs.next()) {
            int estoqueAtual = rs.getInt("quantidade");
            if (estoqueAtual < v.getQuantidade()) {
                System.out.println("Estoque insuficiente! Disponível: " + estoqueAtual + ", Solicitado: " + v.getQuantidade());
                conn.conector.rollback();
                conn.desconectar();
                return false;
            }
        } else {
            System.out.println("Produto não encontrado!");
            conn.conector.rollback();
            conn.desconectar();
            return false;
        }

        String sqlVenda = "insert into venda(idCliente, idFuncionario, idProduto, quantidade, data) values (?,?,?,?,?)";
        PreparedStatement sentencaVenda = conn.conector.prepareStatement(sqlVenda);
        sentencaVenda.setInt(1, v.getIdCliente());
        sentencaVenda.setInt(2, v.getIdFuncionario());
        sentencaVenda.setInt(3, v.getIdProduto());
        sentencaVenda.setInt(4, v.getQuantidade());
        
        sentencaVenda.setTimestamp(5, new java.sql.Timestamp(v.getData().getTime()));

        int linhasVenda = sentencaVenda.executeUpdate();

        String sqlEstoque = "update produto set quantidade = quantidade - ? where id = ?";
        PreparedStatement sentencaEstoque = conn.conector.prepareStatement(sqlEstoque);
        sentencaEstoque.setInt(1, v.getQuantidade());
        sentencaEstoque.setInt(2, v.getIdProduto());

        int linhasEstoque = sentencaEstoque.executeUpdate();

        if (linhasVenda > 0 && linhasEstoque > 0) {
            conn.conector.commit();
            retorno = true;
            System.out.println("Venda processada com sucesso!");
        } else {
            conn.conector.rollback();
            System.out.println("Falha ao processar venda - rollback executado");
        }

    } catch(SQLException e) {
        try {
            conn.conector.rollback();
            System.out.println("Erro SQL : " + e.getMessage());
        } catch(SQLException ex) {
            System.out.println("Erro SQL: " + ex.getMessage());
        }
        System.out.println("Erro ao processar venda: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            conn.conector.setAutoCommit(true);
        } catch(SQLException e) {
            System.out.println("Erro ao restaurar autoCommit: " + e.getMessage());
        }
        conn.desconectar();
    }
    return retorno;
}

    @Override
    public boolean Mostrar_informacoes_atuais_produto(Produto p) throws RemoteException {
        boolean retorno = false;
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "select nome, preco, quantidade from produto where id = ?";
        try {
            PreparedStatement stmt = conn.conector.prepareStatement(sql);
            stmt.setInt(1, p.getId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setQuantidade(rs.getInt("quantidade"));
                retorno = true;
            }
        } catch(SQLException e) {
            System.out.println("Erro ao buscar informações do produto: " + e.getMessage());
        }
        conn.desconectar();
        return retorno;
    }
    
    @Override
    public List<Cliente> listarClientes() throws RemoteException {
        List<Cliente> clientes = new ArrayList<>();
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "select * from cliente";
        try {
            PreparedStatement stmt = conn.conector.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setTelefone(rs.getString("telefone"));
                c.setEndereco(rs.getString("endereco"));
                clientes.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return clientes;
    }

    @Override
    public List<Funcionario> listarFuncionarios() throws RemoteException {
        List<Funcionario> funcionarios = new ArrayList<>();
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "select * from funcionario";
        try {
            PreparedStatement stmt = conn.conector.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCpf(rs.getString("cpf"));
                f.setTelefone(rs.getString("telefone"));
                f.setCargo(rs.getString("cargo"));
                funcionarios.add(f);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return funcionarios;
    }

    @Override
    public List<Produto> listarProdutos() throws RemoteException {
        List<Produto> produtos = new ArrayList<>();
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "select * from produto";
        try {
            PreparedStatement stmt = conn.conector.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setQuantidade(rs.getInt("quantidade"));
                produtos.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return produtos;
    }

    @Override
    public List<Venda> listarVendas() throws RemoteException {
        List<Venda> vendas = new ArrayList<>();
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "select * from venda order by data desc";
        try {
            PreparedStatement stmt = conn.conector.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getInt("id"));
                v.setIdCliente(rs.getInt("idCliente"));
                v.setIdFuncionario(rs.getInt("idFuncionario"));
                v.setIdProduto(rs.getInt("idProduto"));
                v.setQuantidade(rs.getInt("quantidade"));
                v.setData(rs.getDate("data"));
                vendas.add(v);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendas: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return vendas;
    }

    @Override
    public Cliente buscarClientePorNome(String nome) throws RemoteException {
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "select * from cliente where nome = ?";
        try {
            PreparedStatement stmt = conn.conector.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setTelefone(rs.getString("telefone"));
                c.setEndereco(rs.getString("endereco"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return null;
    }

    @Override
    public Funcionario buscarFuncionarioPorNome(String nome) throws RemoteException {
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "select * from funcionario where nome = ?";
        try {
            PreparedStatement stmt = conn.conector.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCpf(rs.getString("cpf"));
                f.setTelefone(rs.getString("telefone"));
                f.setCargo(rs.getString("cargo"));
                return f;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar funcionário: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return null;
    }

    @Override
    public Produto buscarProdutoPorNome(String nome) throws RemoteException {
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "select * from produto where nome = ?";
        try {
            PreparedStatement stmt = conn.conector.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setQuantidade(rs.getInt("quantidade"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return null;
    }

    @Override
    public boolean excluir_Cliente(String nome) throws RemoteException {
        boolean retorno = false;
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "delete from cliente where nome = ?";
        try {
            PreparedStatement sentenca = conn.conector.prepareStatement(sql);
            sentenca.setString(1, nome);
            int linhasAfetadas = sentenca.executeUpdate();
            if(linhasAfetadas > 0) {
                retorno = true;
                System.out.println("Cliente excluído com sucesso: " + nome);
            }
        } catch(SQLException e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
        conn.desconectar();
        return retorno;
    }

    @Override
    public boolean excluir_Funcionario(String nome) throws RemoteException {
        boolean retorno = false;
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "delete from funcionario where nome = ?";
        try {
            PreparedStatement sentenca = conn.conector.prepareStatement(sql);
            sentenca.setString(1, nome);
            int linhasAfetadas = sentenca.executeUpdate();
            if(linhasAfetadas > 0) {
                retorno = true;
                System.out.println("Funcionário excluído com sucesso: " + nome);
            }
        } catch(SQLException e) {
            System.out.println("Erro ao excluir funcionário: " + e.getMessage());
        }
        conn.desconectar();
        return retorno;
    }

    @Override
    public boolean excluir_Produto(String nome) throws RemoteException {
        boolean retorno = false;
        Conexao conn = new Conexao();
        conn.conectar();
        String sql = "delete from produto where nome = ?";
        try {
            PreparedStatement sentenca = conn.conector.prepareStatement(sql);
            sentenca.setString(1, nome);
            int linhasAfetadas = sentenca.executeUpdate();
            if(linhasAfetadas > 0) {
                retorno = true;
                System.out.println("Produto excluído com sucesso: " + nome);
            }
        } catch(SQLException e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage());
        }
        conn.desconectar();
        return retorno;
    }
}