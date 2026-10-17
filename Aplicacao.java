import java.util.ArrayList;
import java.util.Scanner;

public class Aplicacao {
    private Scanner scanner = new Scanner(System.in);
    private Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        new Aplicacao().executar();
    }

    public void executar() {
        int opcao = -1;
        do {
            exibirMenu();
            opcao = lerInteiro("Opção: ");
            switch (opcao) {
                case 1: cadastrarLeitor(); break;
                case 2: consultarLeitor(); break;
                case 3: biblioteca.listarLeitores(); break;
                case 4: atualizarLeitor(); break;
                case 5: removerLeitor(); break;
                case 6: cadastrarLivro(); break;
                case 7: consultarLivro(); break;
                case 8: biblioteca.listarLivros(); break;
                case 9: atualizarLivro(); break;
                case 10: removerLivro(); break;
                case 11: registrarEmprestimo(); break;
                case 12: biblioteca.listarEmprestimos(); break;
                case 13: registrarDevolucao(); break;
                case 0: System.out.println("Aplicação encerrada."); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
        scanner.close();
    }

    private void exibirMenu() {
        System.out.println();
        System.out.println("1 - Cadastrar leitor");
        System.out.println("2 - Consultar leitor");
        System.out.println("3 - Listar leitores");
        System.out.println("4 - Atualizar leitor");
        System.out.println("5 - Remover leitor");
        System.out.println("6 - Cadastrar livro");
        System.out.println("7 - Consultar livro");
        System.out.println("8 - Listar livros");
        System.out.println("9 - Atualizar livro");
        System.out.println("10 - Remover livro");
        System.out.println("11 - Registrar empréstimo");
        System.out.println("12 - Listar empréstimos");
        System.out.println("13 - Registrar devolução");
        System.out.println("0 - Sair");
    }

    // ---------- Leitores ----------

    private void cadastrarLeitor() {
        int codigo = lerInteiro("Código do leitor: ");
        String nome = lerTexto("Nome: ");
        String email = lerTexto("E-mail: ");
        Leitor leitor = new Leitor(codigo, nome, email);   // o objeto nasce fora da biblioteca
        if (biblioteca.cadastrarLeitor(leitor)) {
            System.out.println("Leitor cadastrado.");
        } else {
            System.out.println("Não foi possível cadastrar o leitor.");
        }
    }

    private void consultarLeitor() {
        Leitor leitor = biblioteca.consultarLeitor(lerInteiro("Código do leitor: "));
        if (leitor == null) {
            System.out.println("Registro não encontrado.");
        } else {
            System.out.println(leitor);
        }
    }

    private void atualizarLeitor() {
        Leitor leitor = biblioteca.consultarLeitor(lerInteiro("Código do leitor: "));
        if (leitor == null) {
            System.out.println("Registro não encontrado.");
            return;
        }
        String nome = lerTexto("Novo nome: ");
        String email = lerTexto("Novo e-mail: ");
        if (leitor.atualizarDados(nome, email)) {
            System.out.println("Dados do leitor atualizados.");
        } else {
            System.out.println("Dados inválidos. Nada foi alterado.");
        }
    }

    private void removerLeitor() {
        Leitor leitor = biblioteca.consultarLeitor(lerInteiro("Código do leitor: "));
        if (leitor == null) {
            System.out.println("Registro não encontrado.");
            return;
        }
        if (biblioteca.removerLeitor(leitor)) {
            System.out.println("Leitor removido.");
        } else {
            System.out.println("Leitor não removido. Há empréstimo aberto.");
        }
    }

    // ---------- Livros ----------

    private void cadastrarLivro() {
        int codigo = lerInteiro("Código do livro: ");
        String titulo = lerTexto("Título: ");
        String autor = lerTexto("Autor: ");
        Livro livro = new Livro(codigo, titulo, autor);    // o objeto nasce fora da biblioteca
        if (biblioteca.cadastrarLivro(livro)) {
            System.out.println("Livro cadastrado.");
        } else {
            System.out.println("Não foi possível cadastrar o livro.");
        }
    }

    private void consultarLivro() {
        Livro livro = biblioteca.consultarLivro(lerInteiro("Código do livro: "));
        if (livro == null) {
            System.out.println("Registro não encontrado.");
        } else {
            System.out.println(livro);
        }
    }

    private void atualizarLivro() {
        Livro livro = biblioteca.consultarLivro(lerInteiro("Código do livro: "));
        if (livro == null) {
            System.out.println("Registro não encontrado.");
            return;
        }
        String titulo = lerTexto("Novo título: ");
        String autor = lerTexto("Novo autor: ");
        if (livro.atualizarDados(titulo, autor)) {
            System.out.println("Dados do livro atualizados.");
        } else {
            System.out.println("Dados inválidos. Nada foi alterado.");
        }
    }

    private void removerLivro() {
        Livro livro = biblioteca.consultarLivro(lerInteiro("Código do livro: "));
        if (livro == null) {
            System.out.println("Registro não encontrado.");
            return;
        }
        if (biblioteca.removerLivro(livro)) {
            System.out.println("Livro removido.");
        } else {
            System.out.println("Livro não removido. Há empréstimo aberto.");
        }
    }

    // ---------- Empréstimos ----------

    private void registrarEmprestimo() {
        int codigo = lerInteiro("Código do empréstimo: ");
        Leitor leitor = biblioteca.consultarLeitor(lerInteiro("Código do leitor: "));
        if (leitor == null) {
            System.out.println("Registro não encontrado.");
            return;
        }
        ArrayList<Livro> escolhidos = new ArrayList<>();
        while (true) {
            int codLivro = lerInteiro("Código do livro (0 para encerrar): ");
            if (codLivro == 0) {
                break;
            }
            Livro livro = biblioteca.consultarLivro(codLivro);
            if (livro == null) {
                System.out.println("Registro não encontrado.");
            } else {
                escolhidos.add(livro);
            }
        }
        if (biblioteca.registrarEmprestimo(codigo, leitor, escolhidos)) {
            System.out.println("Empréstimo registrado.");
        } else {
            System.out.println("Não foi possível registrar o empréstimo.");
        }
    }

    private void registrarDevolucao() {
        if (biblioteca.registrarDevolucao(lerInteiro("Código do empréstimo: "))) {
            System.out.println("Devolução registrada.");
        } else {
            System.out.println("Não foi possível registrar a devolução.");
        }
    }

    // ---------- Leitura do teclado (RN15) ----------

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (!scanner.hasNextLine()) {
                return 0; // fim da entrada: encerra de forma controlada
            }
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro.");
            }
        }
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.hasNextLine() ? scanner.nextLine() : "";
    }
}
