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
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600); // Configuración de la cámara
        mapa = new Mapa("assets/Textura/MapaRpg/rpgMapa.tmx", camera);
        this.setScreen(new MenuPantalla(this));
    }
    @Override
    public void render() {
        
    }
    @Override
    public void dispose() {
        batch.dispose();
        mapa.dispose();
    }

}
