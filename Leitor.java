public class Leitor {
    private int codigo;
    private String nome;
    private String email;

    public Leitor(int codigo, String nome, String email) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
    }

    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    // Altera somente nome e e-mail do proprio objeto (RN01: textos nao podem ser vazios).
    public boolean atualizarDados(String nome, String email) {
        if (nome == null || nome.isBlank() || email == null || email.isBlank()) {
            return false;
        }
        this.nome = nome.trim();
        this.email = email.trim();
        return true;
    }

    @Override
    public String toString() {
        return codigo + " - " + nome + " (" + email + ")";
    }
}
