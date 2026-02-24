package model;

public class Cliente extends Pessoa {
    private String endereco;

    public Cliente() {}
    public Cliente(int id, String nome, String cpf, String telefone, String endereco) {
        super(id, nome, cpf, telefone);
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
