package  com.example.reproductorapp.objects;

public class Errores {

    private String descripcion;
    private TipoError tipo;
    private Token token;

    public Errores(String descripcion, TipoError tipo, Token token) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.token = token;
    }

    public Errores(String descripcion, TipoError tipo) {
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoError getTipo() {
        return tipo;
    }

    public void setTipo(TipoError tipo) {
        this.tipo = tipo;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
