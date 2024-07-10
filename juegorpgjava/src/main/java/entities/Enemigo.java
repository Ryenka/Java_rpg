package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import Mapaa.Mapa;

public class Enemigo extends Entities{
    private Animation<TextureRegion> walkUpAnimation, walkDownAnimation, walkLeftAnimation, walkRightAnimation;
    private TextureRegion idleFrame;
    private float stateTime;
    private Vector2 position;
    private Vector2 targetPosition;
    private Texture texture;
    private static final int FRAME_WIDTH = 56; // Frame width based on the spritesheet
    private static final int FRAME_HEIGHT = 104; // Frame height based on the spritesheet
    private static final float MOVE_TIME = 0.1f; // Time to move one tile
    private float moveTimer;
    private Direction moveDirection;
    private Direction lastDirection;
    private Mapa mapa;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    public Enemigo(String tipo, int nivel, int x, int y, String spritePath, Mapa mapa) {
        super(tipo, nivel, 100, new Sprite(new Texture(spritePath))); // Ensure sprite texture is loaded
        this.texture = new Texture(spritePath);
        this.mapa = mapa;

        if (texture == null) {
            throw new IllegalArgumentException("Texture is null: " + spritePath);
        }

        TextureRegion[][] tmp = TextureRegion.split(texture, FRAME_WIDTH, FRAME_HEIGHT);

        // Error handling for the spritesheet dimensions
        walkUpAnimation = (tmp.length > 5 && tmp[5].length >= 5) ? createAnimationFromRow(tmp, 5, 0, 5) : createFallbackAnimation();
        walkDownAnimation = (tmp.length > 3 && tmp[3].length >= 5) ? createAnimationFromRow(tmp, 3, 0, 5) : createFallbackAnimation();
        walkRightAnimation = (tmp.length > 4 && tmp[4].length >= 5) ? createAnimationFromRow(tmp, 4, 0, 5) : createFallbackAnimation();
        walkLeftAnimation = createMirroredAnimation(walkRightAnimation);

        idleFrame = (tmp.length > 3 && tmp[3].length > 0) ? tmp[3][0] : tmp[0][0];

        position = new Vector2(mapa.toWorldCoordinate(x), mapa.toWorldCoordinate(y));
        targetPosition = new Vector2(mapa.toWorldCoordinate(x), mapa.toWorldCoordinate(y));
        stateTime = 0f;
        moveTimer = 0f;
        moveDirection = Direction.NONE;
        lastDirection = Direction.DOWN;
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
        return mapa.toGridCoordinate(position.x);
    }

    public void setX(int x) {
        this.position.x = mapa.toWorldCoordinate(x);
        this.targetPosition.x = mapa.toWorldCoordinate(x);
    }

    public int getY() {
        return mapa.toGridCoordinate(position.y);
    }

    public void setY(int y) {
        this.position.y = mapa.toWorldCoordinate(y);
        this.targetPosition.y = mapa.toWorldCoordinate(y);
    }

    @Override
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

        TextureRegion currentFrame = moveDirection == Direction.NONE ? idleFrame : switch (moveDirection) {
            case UP -> walkUpAnimation.getKeyFrame(stateTime, true);
            case DOWN -> walkDownAnimation.getKeyFrame(stateTime, true);
            case LEFT -> walkLeftAnimation.getKeyFrame(stateTime, true);
            case RIGHT -> walkRightAnimation.getKeyFrame(stateTime, true);
            default -> idleFrame;
        };

        sprite.setRegion(currentFrame);
        sprite.setPosition(position.x, position.y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void recibirDanio(int danio) {
        this.salud -= danio;
        if (this.salud < 0) {
            this.salud = 0;
        }
    }

    public void mover(int deltaX, int deltaY) {
        this.targetPosition.add(mapa.toWorldCoordinate(deltaX), mapa.toWorldCoordinate(deltaY));
        moveDirection = determineDirection(deltaX, deltaY);
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
        moveDirection = Direction.NONE; // Reset movement direction after moving one tile
    }

    private Direction determineDirection(int deltaX, int deltaY) {
        if (deltaX > 0) return Direction.RIGHT;
        if (deltaX < 0) return Direction.LEFT;
        if (deltaY > 0) return Direction.UP;
        if (deltaY < 0) return Direction.DOWN;
        return Direction.NONE;
    }

    private Animation<TextureRegion> createAnimationFromRow(TextureRegion[][] sheet, int row, int startX, int frameCount) {
        frameCount = Math.min(frameCount, sheet[row].length - startX);
        TextureRegion[] frames = new TextureRegion[frameCount];
        System.arraycopy(sheet[row], startX, frames, 0, frameCount);
        return new Animation<>(0.1f, frames);
    }

    private Animation<TextureRegion> createMirroredAnimation(Animation<TextureRegion> original) {
        TextureRegion[] originalFrames = original.getKeyFrames();
        TextureRegion[] mirroredFrames = new TextureRegion[originalFrames.length];

        for (int i = 0; i < originalFrames.length; i++) {
            mirroredFrames[i] = new TextureRegion(originalFrames[i]);
            mirroredFrames[i].flip(true, false); // Flip horizontally
        }

        return new Animation<>(original.getFrameDuration(), mirroredFrames);
    }

    private Animation<TextureRegion> createFallbackAnimation() {
        return new Animation<>(0.1f, new TextureRegion(texture));
    }

    public void dispose() {
        texture.dispose();
    }
}
