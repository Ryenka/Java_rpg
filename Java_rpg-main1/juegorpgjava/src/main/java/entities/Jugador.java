package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Jugador extends Entities {

    private int experiencia;
    private Animation<TextureRegion> walkUpAnimation, walkDownAnimation, walkLeftAnimation, walkRightAnimation;
    private TextureRegion idleFrame;
    private float stateTime;
    private Vector2 position;
    private Vector2 targetPosition;
    private float moveTimer;
    private static final float MOVE_TIME = 0.2f; // Tiempo para moverse cada cuadro
    private static final float FRAME_DURATION = 0.1f; // Duración de cada frame
    private Direction moveDirection;
    private Direction lastDirection;
    private Texture texture;
    private final int TILE_SIZE = 64;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    public Jugador(String nombre, String spritePath) {
        super(nombre, 1, 100, new Sprite(new Texture(spritePath)));
        this.experiencia = 0;
        this.texture = new Texture(spritePath);

        TextureRegion[][] tmp = TextureRegion.split(texture, TILE_SIZE, TILE_SIZE);
        walkUpAnimation = createAnimationFromRow(tmp, 5, 0, 5);
        walkDownAnimation = createAnimationFromRow(tmp, 4, 0, 5);
        walkLeftAnimation = createAnimationFromRow(tmp, 7, 0, 5);
        walkRightAnimation = createAnimationFromRow(tmp, 6, 0, 5);

        idleFrame = tmp[0][0];
        sprite = new Sprite(idleFrame);

        position = new Vector2(0, 0);
        targetPosition = new Vector2(0, 0);
        stateTime = 0f;
        moveTimer = 0f;
        moveDirection = Direction.NONE;
        lastDirection = Direction.DOWN;
    }

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

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
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

    public void setPosition(int x, int y) {
        this.sprite.setPosition(x, y);
        this.position.set(x, y);
        this.targetPosition.set(x, y);
    }

    public void move(Direction direction) {
        moveDirection = direction;
    }

    public void update(float delta) {
        stateTime += delta;
        if (moveDirection != Direction.NONE) {
            moveTimer -= delta;
            if (moveTimer <= 0) {
                moveOneTile();
                lastDirection = moveDirection;
                moveTimer = MOVE_TIME;
            }
        }

        position.lerp(targetPosition, delta * (1 / MOVE_TIME));

        TextureRegion currentFrame;
        if (moveDirection == Direction.NONE) {
            currentFrame = idleFrame;
        } else {
            currentFrame = switch (moveDirection) {
                case UP -> walkUpAnimation.getKeyFrame(stateTime, true);
                case DOWN -> walkDownAnimation.getKeyFrame(stateTime, true);
                case LEFT -> walkLeftAnimation.getKeyFrame(stateTime, true);
                case RIGHT -> walkRightAnimation.getKeyFrame(stateTime, true);
                default -> idleFrame;
            };
        }
        sprite.setRegion(currentFrame);
        sprite.setPosition(position.x, position.y);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void ganarExperiencia(int puntos) {
        this.experiencia += puntos;
        if (this.experiencia >= 100) {
            this.nivel++;
            this.experiencia = 0;
        }
    }

    public void recibirDanio(int danio) {
        this.salud -= danio;
        if (this.salud < 0) {
            this.salud = 0;
        }
    }

    public void curar(int puntos) {
        this.salud += puntos;
        if (this.salud > 100) {
            this.salud = 100;
        }
    }

    private void moveOneTile() {
        int TILE_SIZE = sprite.getRegionWidth();
        switch (moveDirection) {
            case UP -> targetPosition.y += TILE_SIZE;
            case DOWN -> targetPosition.y -= TILE_SIZE;
            case LEFT -> targetPosition.x -= TILE_SIZE;
            case RIGHT -> targetPosition.x += TILE_SIZE;
            default -> {}
        }
    }

    private Animation<TextureRegion> createAnimationFromRow(TextureRegion[][] sheet, int row, int startX, int frameCount) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        System.arraycopy(sheet[row], startX, frames, 0, frameCount);
        return new Animation<>(FRAME_DURATION, frames);
    }

    public void dispose() {
        texture.dispose();
    }
}

