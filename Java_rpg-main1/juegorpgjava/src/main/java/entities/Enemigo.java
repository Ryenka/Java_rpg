package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemigo extends Entities{
    private int x, y;


    public Enemigo(String tipo, int nivel, int x, int y) {
        super(tipo, nivel, 100, new Sprite());
        this.x = x;
        this.y = y;
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

    @Override
    public void update(float delta) {
        // Lógica de actualización del enemigo
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Lógica para dibujar el enemigo
        batch.draw(sprite, x, y);
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
