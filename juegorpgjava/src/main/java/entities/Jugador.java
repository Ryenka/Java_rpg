package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import Mapaa.Mapa;

public class Jugador extends Entities{

    private int experiencia;
    private Animation<TextureRegion> walkUpAnimation, walkDownAnimation, walkLeftAnimation, walkRightAnimation;
    private TextureRegion idleFrame;
    private float stateTime;
    private Vector2 position;
    private Vector2 targetPosition;
    private float moveTimer;
    private static final float MOVE_TIME = 0.2f; // Time to move each frame
    private static final float FRAME_DURATION = 0.1f; // Duration of each frame
    private Direction moveDirection;
    private Direction lastDirection;
    private Texture texture;
    private Mapa mapa;
    private final int TILE_SIZE = 32; // Size of the tiles in the map

    public int getExperiencia() {
        return experiencia;
    }


    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    public Jugador(String nombre, String spritePath, Mapa mapa) {
        super(nombre, 1, 100, new Sprite(new Texture(spritePath)));
        this.experiencia = 0;
        this.mapa = mapa;

        TextureRegion[][] tmp = TextureRegion.split(new Texture(spritePath), TILE_SIZE * 2, TILE_SIZE * 2); // Adjust for 64x64 sprites
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

    public void setPosition(int x, int y) {
        this.sprite.setPosition(mapa.toWorldCoordinate(x), mapa.toWorldCoordinate(y));
        this.position.set(mapa.toWorldCoordinate(x), mapa.toWorldCoordinate(y));
        this.targetPosition.set(mapa.toWorldCoordinate(x), mapa.toWorldCoordinate(y));
    }

    private void moveOneTile() {
        int tileSize = mapa.toWorldCoordinate(1);
        switch (moveDirection) {
            case UP -> targetPosition.y += tileSize;
            case DOWN -> targetPosition.y -= tileSize;
            case LEFT -> targetPosition.x -= tileSize;
            case RIGHT -> targetPosition.x += tileSize;
            default -> {}
        }
    }

    private Animation<TextureRegion> createAnimationFromRow(TextureRegion[][] sheet, int row, int startX, int frameCount) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        System.arraycopy(sheet[row], startX, frames, 0, frameCount);
        return new Animation<>(FRAME_DURATION, frames);
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
