package juegoRpg;

import Mapaa.Mapa;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class RpgJuego extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public Mapa mapa;
    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MenuPantalla(this));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600); // Configuración de la cámara
        mapa = new Mapa("assets/Textura/MapaRpg/rpgMapa.tmx", camera);
    }
    @Override
    public void render() {
        super.render();
        batch.begin();
        mapa.render(batch); // Renderizar el mapa
        batch.end();
    }
    @Override
    public void dispose() {
        batch.dispose();
        mapa.dispose();
    }

}
