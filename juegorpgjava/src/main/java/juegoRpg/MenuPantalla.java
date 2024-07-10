package juegoRpg;
import Keyss.KeyHandler;
import Mapaa.Mapa;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.Enemigo;
import entities.Jugador;

public class MenuPantalla extends ScreenAdapter {
    private RpgJuego game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Jugador jugador;
    private KeyHandler keyHandler;
    private Enemigo enemigo;
    private Mapa mapa;


    public MenuPantalla(RpgJuego game) {
        this.game = game;
        this.batch = game.batch;
        this.font = new BitmapFont();
        this.jugador = new Jugador("Steve", "assets/Textura/jugadorSprite/player.png"); //implementar que el jugador decida su nomnbre
        this.enemigo = new Enemigo("EnemigoGenérico", 1, 200, 200);
        this.keyHandler = new KeyHandler(jugador);
        this.mapa = game.mapa;
        Gdx.input.setInputProcessor(keyHandler);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        keyHandler.update();
        batch.begin();
        if (mapa != null) {
            mapa.render(batch); // Renderizar el mapa
        } else {
            System.err.println("Mapa es null");
        }
        font.draw(batch, "Jugador: " + jugador.getNombre(), 100, 150);
        font.draw(batch, "Nivel: " + jugador.getNivel(), 100, 130);
        font.draw(batch, "Experiencia: " + jugador.getExperiencia(), 100, 110);
        font.draw(batch, "Salud: " + jugador.getSalud(), 100, 90);
        jugador.update(delta);
        jugador.draw(batch);
        font.draw(batch, "E", enemigo.getX(), enemigo.getY());
        batch.end();
    }
    @Override
    public void resize(int width, int height) {
        // Método necesario para cuando se cambia el tamaño de la pantalla
    }
    @Override
    public void pause() {
        // Método necesario para cuando se pausa el juego
    }
    @Override
    public void resume() {
        // Método necesario para cuando se reanuda el juego
    }

    @Override
    public void dispose() {
        font.dispose();
        jugador.getSprite().getTexture().dispose();
        mapa.dispose();
    }
}
