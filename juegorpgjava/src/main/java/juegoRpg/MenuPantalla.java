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
    private Enemigo enemigo;
    private KeyHandler keyHandler;
    private Mapa mapa;

    public MenuPantalla(RpgJuego game) {
        this.game = game;
        this.batch = game.batch;
        this.font = new BitmapFont();
        this.mapa = game.mapa; // Ensure mapa is initialized first

        // Ensure mapa is initialized and accessible before creating Jugador and Enemigo
        this.jugador = new Jugador("Steve", "assets/Textura/jugadorSprite/player.png", mapa);
        this.enemigo = new Enemigo("EnemigoGenérico", 1, 200, 200, "assets/Textura/enemigoSprite/slime.png", mapa);

        this.keyHandler = new KeyHandler(jugador);

        // Set input processor
        Gdx.input.setInputProcessor(keyHandler);
    }


    @Override
    public void render(float delta) {
        // Clear the screen with a black color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the game logic
        keyHandler.update();
        jugador.update(delta);

        // Start rendering
        batch.begin();

        if (mapa != null) {
            // Render the map
            mapa.render(batch);
        } else {
            System.err.println("Mapa es null");
        }

        // Draw player information
        font.draw(batch, "Jugador: " + jugador.getNombre(), 100, 150);
        font.draw(batch, "Nivel: " + jugador.getNivel(), 100, 130);
        font.draw(batch, "Experiencia: " + jugador.getExperiencia(), 100, 110);
        font.draw(batch, "Salud: " + jugador.getSalud(), 100, 90);

        // Draw player and enemy
        jugador.draw(batch);
        enemigo.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Method for handling screen resizing
    }

    @Override
    public void pause() {
        // Method for handling game pause
    }

    @Override
    public void resume() {
        // Method for handling game resume
    }

    @Override
    public void dispose() {
        // Dispose of resources to prevent memory leaks
        font.dispose();
        jugador.getSprite().getTexture().dispose();
        if (mapa != null) {
            mapa.dispose();
        }
    }
}
