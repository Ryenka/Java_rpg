package juegoRpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class RpgJuego extends Game {
    public SpriteBatch batch;
    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MenuPantalla(this));
    }
    @Override
    public void render() {
        super.render();
    }
    @Override
    public void dispose() {
        batch.dispose();
    }

}
