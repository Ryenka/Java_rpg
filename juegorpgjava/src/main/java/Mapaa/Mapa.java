package Mapaa;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Mapa {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private static final int TILE_SIZE = 31;

    public Mapa(String mapaPath, OrthographicCamera camera) {
        try {
            this.tiledMap = new TmxMapLoader().load(mapaPath);
            this.renderer = new OrthogonalTiledMapRenderer(tiledMap);
            this.camera = camera;
        } catch (com.badlogic.gdx.utils.GdxRuntimeException e) {
            System.err.println("Error cargando mapa: " + mapaPath);
            e.printStackTrace();
            throw e; // O maneja la excepción de otra manera adecuada
        }
    }
    public void render(SpriteBatch batch) {
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    public void dispose() {
        tiledMap.dispose();
        renderer.dispose();
    }
    public OrthographicCamera getCamera() {
        return camera;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }
    public int toWorldCoordinate(int gridCoordinate) {
        return gridCoordinate * TILE_SIZE;
    }

    public int toGridCoordinate(float worldCoordinate) {
        return (int) (worldCoordinate / TILE_SIZE);
    }
}
