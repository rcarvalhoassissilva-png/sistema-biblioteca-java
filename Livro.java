public class Livro {
    private int codigo;
    private String titulo;
    private String autor;
    private boolean disponivel;

    public Livro(int codigo, String titulo, String autor) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true; // o livro nasce disponivel
    }

    public int getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public boolean isDisponivel() { return disponivel; }

    public boolean atualizarDados(String titulo, String autor) {
        if (titulo == null || titulo.isBlank() || autor == null || autor.isBlank()) {
            return false;
        }
        this.titulo = titulo.trim();
        this.autor = autor.trim();
        return true;
    }

    public void emprestar() { this.disponivel = false; }

    public void devolver() { this.disponivel = true; }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " (" + autor + ") - "
                + (disponivel ? "disponível" : "indisponível");
    }
}
