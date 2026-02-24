package model;

public class Funcionario extends Pessoa {
    private String cargo;

    public Funcionario() {}
    public Funcionario(int id, String nome, String cpf, String telefone, String cargo) {
        super(id, nome, cpf, telefone);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
