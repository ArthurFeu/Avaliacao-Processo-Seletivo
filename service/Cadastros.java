package service;

import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import entity.Funcionario;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Cadastros {
	private List<Funcionario> listaFuncionarios;

	public Cadastros() {
		this.listaFuncionarios = new ArrayList<>();
	}

	public Funcionario cadastrarFuncionarioIndividualmente() {
		// Como comentei no README, a validação dos dados de entrada poderia ser feita
		// dentro de uma classe Util, mas optei por fazer diretamente aqui para
		// simplificar o código, dado o escopo da prova.

		Scanner scanner = new Scanner(System.in);
		Funcionario funcionario = new Funcionario(null, null, 0, null);

		inserirNomeFuncionario(scanner, funcionario);
		inserirDataNascimento(scanner, funcionario);
		inserirSalario(scanner, funcionario);
		inserirCargo(scanner, funcionario);

		listaFuncionarios.add(funcionario);
		System.out.println("Funcionário cadastrado com sucesso!");
		return funcionario;
	}

	private void inserirNomeFuncionario(Scanner scanner, Funcionario funcionario) {
		System.out.print("Nome: ");
		String nome = scanner.nextLine();
		while (nome.length() < 3 || nome.isEmpty()) {
			System.out.println("Nome inválido. Por favor, insira um nome válido com pelo menos 3 caracteres.");
			System.out.print("Nome: ");
			nome = scanner.nextLine();
		}
		funcionario.setNome(nome);
	}

	private void inserirDataNascimento(Scanner scanner, Funcionario funcionario) {
		boolean dataValida = false;
		LocalDate dataNascimento = null;
		while (!dataValida) {
			try {
				System.out.print("Data de Nascimento (dd/mm/aaaa): ");
				String dataNascimentoStr = scanner.nextLine();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
				dataValida = true;
			} catch (DateTimeParseException e) {
				System.out.println("Formato de data inválido. Por favor, insira no formato dd/mm/aaaa.");
			}
		}
		funcionario.setDataNascimento(dataNascimento);
	}

	private void inserirSalario(Scanner scanner, Funcionario funcionario) {
		boolean salarioValido = false;
		while (!salarioValido) {
			try {
				System.out.print("Salário: ");
				double salario = Double.parseDouble(scanner.nextLine());
				funcionario.setSalario(salario);
				salarioValido = true;
			} catch (NumberFormatException e) {
				System.out.println("Valor de salário inválido. Por favor, insira um número válido.");
			}
		}
	}

	private void inserirCargo(Scanner scanner, Funcionario funcionario) {
		System.out.print("Cargo: ");
		String cargo = scanner.nextLine();
		while (cargo.length() < 3 || cargo.isEmpty()) {
			System.out.println("Cargo inválido. Por favor, insira um cargo válido com pelo menos 3 caracteres.");
			System.out.print("Cargo: ");
			cargo = scanner.nextLine();
		}
		funcionario.setCargo(cargo);
	}

	public void cadastrarFuncionariosEmLote() {
		try {
			File file = new File("./externalData/funcionarios.csv");
			Scanner fileScanner = new Scanner(file);

			while (fileScanner.hasNextLine()) {
				String linha = fileScanner.nextLine();
				String[] dados = linha.split(",");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				Funcionario funcionario = new Funcionario(dados[0], LocalDate.parse(dados[1], formatter),
						Double.parseDouble(dados[2]), dados[3]);
				listaFuncionarios.add(funcionario);
			}

			fileScanner.close();
			System.out.println("Funcionários cadastrados com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao cadastrar funcionarios do CSV" + e.getMessage());
		}
	}

	public String formataSalario(double salario) {
		DecimalFormat df = new DecimalFormat("#,##0.00");

		DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
		symbols.setDecimalSeparator(',');

		// Define o separador de milhar como ponto
		symbols.setGroupingSeparator('.');

		// Aplica os símbolos personalizados ao DecimalFormat
		df.setDecimalFormatSymbols(symbols);

		return "R$ " + df.format(salario);
	}

	public void imprimirFuncionario(Funcionario funcionario) {
		DateTimeFormatter formatoPadrao = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		System.out.println(
				funcionario.getNome() + " - "
						+ funcionario.getDataNascimento().format(formatoPadrao) + " - "
						+ formataSalario(funcionario.getSalario()) + " - "
						+ funcionario.getCargo());
	}

	public void listarFuncionarios() {
		if (listaFuncionarios.isEmpty()) {
			System.out.println("Não há funcionários cadastrados.");
		} else {
			System.out.println("Lista de Funcionários:");
			for (Funcionario funcionario : listaFuncionarios) {
				imprimirFuncionario(funcionario);
			}
		}
	}

	public void removerFuncionario(String nome) {
		for (Funcionario funcionario : listaFuncionarios) {
			if (funcionario.getNome().equals(nome)) {
				listaFuncionarios.remove(funcionario);
				System.out.println("Funcionário removido com sucesso!");
				return;
			}
		}
		System.out.println("Funcionário não encontrado.");
	}

	public void realizarAumentoGlobal(double percentual) {
		for (Funcionario funcionario : listaFuncionarios) {
			double novoSalario = funcionario.getSalario() * (1 + percentual / 100);
			funcionario.setSalario(novoSalario);
		}
	}

	public Map agruparFuncionariosPorCargo() {
		// Primeiro crio todos os cargos
		List<String> cargos = new ArrayList<>();

		// Aqui eu considero que todos os cargos já estão padronizados e não teremos
		// repetidos, mas em uma situação de cadastro real, todo o processo seria
		// diferente, com os cargos sendo entidades próprias e não strings.

		for (Funcionario funcionario : listaFuncionarios) {
			if (!cargos.contains(funcionario.getCargo())) {
				cargos.add(funcionario.getCargo());
			}
		}

		// Agora eu crio um hashmap onde a chave é o cargo e o valor é uma lista de
		// funcionários

		Map<String, List<Funcionario>> funcionariosPorCargo = new HashMap<>();

		for (String cargo : cargos) {
			List<Funcionario> funcionarios = new ArrayList<>();
			for (Funcionario funcionario : listaFuncionarios) {
				if (funcionario.getCargo().equals(cargo)) {
					funcionarios.add(funcionario);
				}
			}
			// Adiciono a lista de funcionários para o cargo correspondente
			funcionariosPorCargo.put(cargo, funcionarios);
		}

		return funcionariosPorCargo;
	}

	public void imprimirFuncionarioPorCargo(Map<String, List<Funcionario>> funcionariosPorCargo) {
		for (String cargo : funcionariosPorCargo.keySet()) {
			System.out.println("\nCargo: " + cargo);
			for (Funcionario funcionario : funcionariosPorCargo.get(cargo)) {
				imprimirFuncionario(funcionario);
				// Aqui a impressao dos cargos é redundante, mas optei por seguir a instrução da
				// prova
			}
		}
	}

	public void imprimirAniversariantes(int mes) {
		for (Funcionario funcionario : listaFuncionarios) {
			if (funcionario.getDataNascimento().getMonthValue() == mes) {
				imprimirFuncionario(funcionario);
			}
		}
	}

	public void imprimirFuncionarioMaiorIdade() {
		Funcionario maiorIdade = listaFuncionarios.get(0);
		for (Funcionario funcionario : listaFuncionarios) {
			if (funcionario.getDataNascimento().isBefore(maiorIdade.getDataNascimento())) {
				maiorIdade = funcionario;
			}
		}

		LocalDate hoje = LocalDate.now();
		int idade = hoje.getYear() - maiorIdade.getDataNascimento().getYear();

		System.out.println("Nome: " + maiorIdade.getNome() + " - Idade: " + idade);
	}

	public void listarFuncionariosOrdemAlfabetica() {
		// Primeiro vou ordenar os funcionarios por ordem alfabetica em uma nova lista
		List<Funcionario> funcionariosOrdenados = new ArrayList<>(listaFuncionarios);

		Collections.sort(funcionariosOrdenados, new Comparator<Funcionario>() {
			@Override
			public int compare(Funcionario f1, Funcionario f2) {
				return f1.getNome().compareTo(f2.getNome());
			}
		});

		// Agora eu imprimo os funcionarios
		for (Funcionario funcionarioOrdenado : funcionariosOrdenados) {
			imprimirFuncionario(funcionarioOrdenado);
		}
	}

	public double calcularTotalSalarios() {
		double total = 0;
		for (Funcionario funcionario : listaFuncionarios) {
			total += funcionario.getSalario();
		}
		return total;
	}

	public double quantosSalariosMinimosGanha(Funcionario funcionario, double salarioMinimo) {

		return (funcionario.getSalario() / salarioMinimo);
	}

	public void imprimirFuncionariosSalarioMinimo(double salarioMinimo) {
		for (Funcionario funcionario : listaFuncionarios) {
			double salarios = quantosSalariosMinimosGanha(funcionario, salarioMinimo);

			System.out.printf("%s - %.1f salários mínimos\n", funcionario.getNome(), salarios);

		}
	}
}
