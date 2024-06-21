package tests.service;

import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import service.Cadastros;
import entity.Funcionario;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.Month;

public class TestCadastros {

	@Test
	public void testCadastroIndividualComSucesso() {
		Cadastros cadastros = new Cadastros();

		ByteArrayInputStream mockInput = new ByteArrayInputStream("Arthur Feu\n01/01/1990\n5000\nGerente\n".getBytes());
		System.setIn(mockInput);
		Scanner scanner = new Scanner(System.in);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		Funcionario funcionario = cadastros.cadastrarFuncionarioIndividualmente();

		System.setIn(System.in);

		assertNotNull(funcionario);
		assertEquals("Arthur Feu", funcionario.getNome());
		assertEquals(LocalDate.of(1990, Month.JANUARY, 1), funcionario.getDataNascimento());
		assertEquals(5000, funcionario.getSalario(), 0.001);
		assertEquals("Gerente", funcionario.getCargo());

		assertTrue(outputStream.toString().contains("Funcionário cadastrado com sucesso!"));
	}

	@Test
	public void testCadastroIndividualComErroNome() {
		Cadastros cadastros = new Cadastros();

		ByteArrayInputStream mockInput = new ByteArrayInputStream(
				"Jo\nArthur Feu\n01/01/1990\n5000\nGerente\n".getBytes());
		System.setIn(mockInput);
		Scanner scanner = new Scanner(System.in);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		Funcionario funcionario = cadastros.cadastrarFuncionarioIndividualmente();

		System.setIn(System.in);

		assertTrue(outputStream.toString()
				.contains("Nome inválido. Por favor, insira um nome válido com pelo menos 3 caracteres."));
	}

	@Test
	public void testCadastroIndividualComErroData() {
		Cadastros cadastros = new Cadastros();

		ByteArrayInputStream mockInput = new ByteArrayInputStream(
				"Arthur Feu\n01/01/90\n01/01/1990\n5000\nGerente\n".getBytes());
		System.setIn(mockInput);
		Scanner scanner = new Scanner(System.in);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		Funcionario funcionario = cadastros.cadastrarFuncionarioIndividualmente();

		System.setIn(System.in);

		assertTrue(
				outputStream.toString().contains("Formato de data inválido. Por favor, insira no formato dd/mm/aaaa."));
	}

	@Test
	public void testCadastroIndividualComErroSalario() {
		Cadastros cadastros = new Cadastros();

		ByteArrayInputStream mockInput = new ByteArrayInputStream(
				"Arthur Feu\n01/01/1990\nfive thousand\n5000\nGerente\n".getBytes());
		System.setIn(mockInput);
		Scanner scanner = new Scanner(System.in);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		Funcionario funcionario = cadastros.cadastrarFuncionarioIndividualmente();

		System.setIn(System.in);

		assertTrue(outputStream.toString().contains("Valor de salário inválido. Por favor, insira um número válido."));
	}

	@Test
	public void testCadastroIndividualComErroCargo() {
		Cadastros cadastros = new Cadastros();

		ByteArrayInputStream mockInput = new ByteArrayInputStream(
				"Arthur Feu\n01/01/1990\n5000\nGe\nGerente\n".getBytes());
		System.setIn(mockInput);
		Scanner scanner = new Scanner(System.in);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		Funcionario funcionario = cadastros.cadastrarFuncionarioIndividualmente();

		System.setIn(System.in);

		assertTrue(outputStream.toString()
				.contains("Cargo inválido. Por favor, insira um cargo válido com pelo menos 3 caracteres."));
	}
}
