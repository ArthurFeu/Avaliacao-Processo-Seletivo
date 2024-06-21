package service;

import java.util.*;

public class Menu {
	public void menu() {
		Cadastros cadastros = new Cadastros();

		int opcao = -1;
		while (opcao != 0) {
			System.out.println("\n========== MENU ==========");
			System.out.println("1 - Cadastrar Funcionário Individualmente");
			System.out.println("2 - Cadastrar Funcionários em Lote (via .csv)");
			System.out.println("3 - Listar Funcionários");
			System.out.println("4 - Remover Funcionário");
			System.out.println("0 - Sair");

			System.out.print("Escolha uma opção: ");
			opcao = new Scanner(System.in).nextInt();
			switch (opcao) {
				case 1:
					cadastros.cadastrarFuncionarioIndividualmente();
					break;
				case 2:
					cadastros.cadastrarFuncionariosEmLote();
					break;
				case 3:
					cadastros.listarFuncionarios();
					break;
				case 4:
					System.out.print("Nome do funcionário a ser removido: ");
					String nome = new Scanner(System.in).nextLine();
					cadastros.removerFuncionario(nome);
					break;
				case 0:
					System.exit(0);
					break;
				default:
					System.out.println("Opção inválida");
					break;
			}
			opcao = -1;
		}

	}

	public static void main(String[] args) {
		System.setProperty("file.encoding", "UTF-8");
		Menu principal = new Menu();
		principal.menu();
	}
}
