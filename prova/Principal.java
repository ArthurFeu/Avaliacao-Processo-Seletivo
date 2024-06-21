package prova;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entity.Funcionario;
import service.Cadastros;

public class Principal {

	public static void pressioneEnterParaContinuar() {
		System.out.println("\n");
		Scanner scanner = new Scanner(System.in);
		System.out.print("Pressione Enter para continuar...");
		scanner.nextLine();
		System.out.println("\n");
	}

	public static void main(String[] args) {
		System.setProperty("file.encoding", "UTF-8");
		Cadastros cadastros = new Cadastros();

		System.out.println("QUESTAO: Inserir todos os funcionários, na mesma ordem e informações da tabela.");
		cadastros.cadastrarFuncionariosEmLote();
		cadastros.listarFuncionarios();
		pressioneEnterParaContinuar();

		System.out.println("QUESTAO: Remover o funcionário com nome “João”.");
		cadastros.removerFuncionario("João");
		pressioneEnterParaContinuar();

		System.out.println("QUESTAO: Imprimir todos os funcionários com todas suas informações.");
		cadastros.listarFuncionarios();
		pressioneEnterParaContinuar();

		System.out.println("QUESTAO: Realizar aumento de 10% no salário de todos os funcionários.");
		cadastros.realizarAumentoGlobal(10);
		cadastros.listarFuncionarios();
		pressioneEnterParaContinuar();

		System.out.println(
				"QUESTAO: Agrupar e imprimir os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.");
		Map<String, List<Funcionario>> funcionariosPorCargo = cadastros.agruparFuncionariosPorCargo();
		cadastros.imprimirFuncionarioPorCargo(funcionariosPorCargo);
		pressioneEnterParaContinuar();

		System.out.println("QUESTAO: Imprimir os funcionários que fazem aniversário no mês 10 e 12.");
		cadastros.imprimirAniversariantes(10);
		cadastros.imprimirAniversariantes(12);
		pressioneEnterParaContinuar();

		System.out.println("QUESTAO: Imprimir o funcionário com a maior idade.");
		cadastros.imprimirFuncionarioMaiorIdade();
		pressioneEnterParaContinuar();

		System.out.println("QUESTAO: Imprimir a lista de funcionários por ordem alfabética.");
		cadastros.listarFuncionariosOrdemAlfabetica();
		pressioneEnterParaContinuar();

		System.out.println("QUESTAO: Imprimir o total dos salários dos funcionários.");
		Double totalSalarios = cadastros.calcularTotalSalarios();
		System.out.println("Total dos salários: " + totalSalarios);
		pressioneEnterParaContinuar();

		System.out.println(
				"QUESTAO: Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.");
		cadastros.imprimirFuncionariosSalarioMinimo(1212);
		System.out.println("Fim do programa.");

	}

}
