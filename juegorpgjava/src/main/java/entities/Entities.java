package entities;
import Interfaz.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entities implements Actualizacion,Dibujo{
    protected String nombre;
    protected int nivel;
    protected int salud;
    protected Sprite sprite;

    public Entities(String nombre, int nivel, int salud, Sprite sprite) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.salud = salud;
        this.sprite = sprite;
    }
    public abstract void update(float delta);
    public abstract void draw(SpriteBatch batch);

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
