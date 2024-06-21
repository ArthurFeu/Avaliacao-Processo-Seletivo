package entity;

import java.time.LocalDate;

public class Funcionario extends Pessoa {
	private double salario;
	private String cargo;

	public Funcionario(String nome, LocalDate dataNascimento, double salario, String cargo) {
		super(nome, dataNascimento);
		this.salario = salario;
		this.cargo = cargo;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
}
