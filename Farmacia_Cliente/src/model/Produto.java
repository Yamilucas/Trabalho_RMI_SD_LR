/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.io.Serializable;

/**
 *
 * @author Rodrian
 */
public class Produto implements Serializable {
    private int id;
    private String nome;
    private double preco;
    private int quantidade;

    public Produto() {}
    public Produto(int id, String nome, double preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int getId() { 
        return id;
    }
    public void setId(int id) { 
        this.id = id; 
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome; 
    }

    public double getPreco() { 
        return preco; 
    }
    public void setPreco(double preco) {
        this.preco = preco; 
    }

    public int getQuantidade() { 
        return quantidade;
    }
    public void setQuantidade(int quantidade) { 
        this.quantidade = quantidade;
    }
}
