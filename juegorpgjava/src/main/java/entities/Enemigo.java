package entities;

public class Enemigo {
    private String tipo;
    private int nivel;
    private int salud;
    private int x, y;


    public Enemigo(String tipo, int nivel, int x, int y) {
        this.tipo = tipo;
        this.nivel = nivel;
        this.salud = 100;
        this.x = x;
        this.y = y;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void recibirDanio(int danio) {
        this.salud -= danio;
        if (this.salud < 0) {
            this.salud = 0;
        }
    }
    public void mover(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
}
