import java.util.ArrayList;

public class Biblioteca {
    // Agregacao: leitores e livros nascem fora e sao recebidos prontos.
    private ArrayList<Leitor> leitores = new ArrayList<>();
    private ArrayList<Livro> livros = new ArrayList<>();
    private ArrayList<Emprestimo> emprestimos = new ArrayList<>();

    // ---------- Leitores ----------

    public boolean cadastrarLeitor(Leitor leitor) {
        if (leitor == null || leitor.getCodigo() <= 0                    // RN02
                || vazio(leitor.getNome()) || vazio(leitor.getEmail())   // RN01
                || consultarLeitor(leitor.getCodigo()) != null) {        // RN03
            return false;
        }
        leitores.add(leitor);
        return true;
    }

    public Leitor consultarLeitor(int codigo) {
        for (Leitor leitor : leitores) {
            if (leitor.getCodigo() == codigo) {
                return leitor;
            }
        }
        return null;
    }

    public void listarLeitores() {
        if (leitores.isEmpty()) {
            System.out.println("Nenhum leitor cadastrado.");
            return;
        }
        for (Leitor leitor : leitores) {
            System.out.println(leitor);
        }
    }

    public boolean removerLeitor(Leitor leitor) {
        if (leitor == null || !leitores.contains(leitor)) {
            return false;
        }
        for (Emprestimo e : emprestimos) {                               // RN13
            if (e.isAberto() && e.getLeitor() == leitor) {
                return false;
            }
        }
        return leitores.remove(leitor);
    }

    // ---------- Livros ----------

    public boolean cadastrarLivro(Livro livro) {
        if (livro == null || livro.getCodigo() <= 0                      // RN02
                || vazio(livro.getTitulo()) || vazio(livro.getAutor())   // RN01
                || consultarLivro(livro.getCodigo()) != null) {          // RN04
            return false;
        }
        livros.add(livro);
        return true;
    }

    public Livro consultarLivro(int codigo) {
        for (Livro livro : livros) {
            if (livro.getCodigo() == codigo) {
                return livro;
            }
        }
        return null;
    }

    public void listarLivros() {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    public boolean removerLivro(Livro livro) {
        if (livro == null || !livros.contains(livro)) {
            return false;
        }
        for (Emprestimo e : emprestimos) {                               // RN13
            if (e.isAberto() && e.contemLivro(livro)) {
                return false;
            }
        }
        return livros.remove(livro);
    }

    // ---------- Emprestimos ----------

    // Valida tudo antes de alterar qualquer objeto, para nao deixar estado pela metade.
    public boolean registrarEmprestimo(int codigo, Leitor leitor, ArrayList<Livro> livrosEscolhidos) {
        if (codigo <= 0 || consultarEmprestimo(codigo) != null) {        // RN02, RN05
            return false;
        }
        if (leitor == null || !leitores.contains(leitor)) {              // RN06
            return false;
        }
        if (livrosEscolhidos == null || livrosEscolhidos.isEmpty()) {    // RN07
            return false;
        }
        ArrayList<Livro> vistos = new ArrayList<>();
        for (Livro livro : livrosEscolhidos) {
            if (livro == null || !livros.contains(livro)                 // livro cadastrado
                    || !livro.isDisponivel()                             // RN08
                    || vistos.contains(livro)) {                         // RN09
                return false;
            }
            vistos.add(livro);
        }

        // A Biblioteca cria o Emprestimo com os objetos ja consultados.
        Emprestimo emprestimo = new Emprestimo(codigo, leitor);
        for (Livro livro : livrosEscolhidos) {
            if (emprestimo.adicionarLivro(livro)) {
                livro.emprestar();                                       // RN10
            }
        }
        emprestimos.add(emprestimo);
        return true;
    }

    public void listarEmprestimos() {
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo registrado.");
            return;
        }
        for (Emprestimo e : emprestimos) {
            System.out.println(e);
        }
    }

    public boolean registrarDevolucao(int codigo) {
        Emprestimo e = consultarEmprestimo(codigo);
        if (e == null || !e.isAberto()) {                                // RN12
            return false;
        }
        e.finalizar();                                                   // RN11
        return true;
    }

    // ---------- Auxiliares privados ----------

    private Emprestimo consultarEmprestimo(int codigo) {
        for (Emprestimo e : emprestimos) {
            if (e.getCodigo() == codigo) {
                return e;
            }
        }
        return null;
    }

    private boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
