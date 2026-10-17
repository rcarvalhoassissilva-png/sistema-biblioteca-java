import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Emprestimo {
    private int codigo;
    private LocalDate dataEmprestimo;
    private boolean aberto;
    private Leitor leitor;                       // associacao binaria
    private ArrayList<ItemEmprestimo> itens;     // composicao

    public Emprestimo(int codigo, Leitor leitor) {
        this.codigo = codigo;
        this.leitor = leitor;
        this.dataEmprestimo = LocalDate.now();
        this.aberto = true;
        this.itens = new ArrayList<>();
    }

    public int getCodigo() { return codigo; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public boolean isAberto() { return aberto; }
    public Leitor getLeitor() { return leitor; }

    // O emprestimo cria o item: o item nao existe fora dele.
    public boolean adicionarLivro(Livro livro) {
        if (livro == null || contemLivro(livro)) {
            return false;
        }
        itens.add(new ItemEmprestimo(livro));
        return true;
    }

    public boolean contemLivro(Livro livro) {
        for (ItemEmprestimo item : itens) {
            if (item.getLivro() == livro) {
                return true;
            }
        }
        return false;
    }

    public void finalizar() {
        for (ItemEmprestimo item : itens) {
            item.registrarDevolucao();
            item.getLivro().devolver();
        }
        aberto = false;
    }

    @Override
    public String toString() {
        StringBuilder livros = new StringBuilder();
        for (int i = 0; i < itens.size(); i++) {
            if (i > 0) livros.append(", ");
            livros.append(itens.get(i).getLivro().getTitulo());
        }
        return codigo + " - " + leitor.getNome() + " - "
                + dataEmprestimo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " - " + (aberto ? "aberto" : "finalizado")
                + " - livros: " + livros;
    }
}
