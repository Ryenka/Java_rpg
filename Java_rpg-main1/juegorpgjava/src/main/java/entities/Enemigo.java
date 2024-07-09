package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Enemigo extends Entities {
    private Animation<TextureRegion> walkUpAnimation, walkDownAnimation, walkLeftAnimation, walkRightAnimation;
    private TextureRegion idleFrame;
    private float stateTime;
    private Vector2 position;
    private Vector2 targetPosition;
    private Texture texture;
    private static final int FRAME_WIDTH = 56; // Ancho del frame basado en la hoja de sprites
    private static final int FRAME_HEIGHT = 104; // Alto del frame basado en la hoja de sprites
    private static final float MOVE_TIME = 0.1f; // Tiempo para moverse un tile
    private float moveTimer;
    private Direction moveDirection;
    private Direction lastDirection;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    public Enemigo(String tipo, int nivel, int x, int y, String spritePath) {
        super(tipo, nivel, 100, new Sprite(new Texture(spritePath)));
        this.texture = new Texture(spritePath);

        TextureRegion[][] tmp = TextureRegion.split(texture, FRAME_WIDTH, FRAME_HEIGHT);

        // Manejo de errores para las dimensiones de la hoja de sprites
        if (tmp.length > 5 && tmp[5].length >= 5) {
            walkUpAnimation = createAnimationFromRow(tmp, 5, 0, 5);
        } else {
            walkUpAnimation = createFallbackAnimation();
        }
        if (tmp.length > 3 && tmp[3].length >= 5) {
            walkDownAnimation = createAnimationFromRow(tmp, 3, 0, 5);
        } else {
            walkDownAnimation = createFallbackAnimation();
        }
        if (tmp.length > 4 && tmp[4].length >= 5) {
            walkRightAnimation = createAnimationFromRow(tmp, 4, 0, 5);
        } else {
            walkRightAnimation = createFallbackAnimation();
        }
        walkLeftAnimation = createMirroredAnimation(walkRightAnimation);

        idleFrame = tmp.length > 3 && tmp[3].length > 0 ? tmp[3][0] : tmp[0][0];

        position = new Vector2(x, y);
        targetPosition = new Vector2(x, y);
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
        return (int) position.x;
    }

    public void setX(int x) {
        this.position.x = x;
    }

    public int getY() {
        return (int) position.y;
    }

    public void setY(int y) {
        this.position.y = y;
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

        TextureRegion currentFrame = idleFrame;

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
        this.targetPosition.add(deltaX, deltaY);
        moveDirection = determineDirection(deltaX, deltaY);
    }

    private void moveOneTile() {
        int TILE_SIZE = sprite.getRegionWidth();
        switch (moveDirection) {
            case UP:
                targetPosition.y += TILE_SIZE;
                break;
            case DOWN:
                targetPosition.y -= TILE_SIZE;
                break;
            case LEFT:
                targetPosition.x -= TILE_SIZE;
                break;
            case RIGHT:
                targetPosition.x += TILE_SIZE;
                break;
            default:
                break;
        }
        moveDirection = Direction.NONE; // Reiniciar dirección de movimiento después de mover un tile
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
            mirroredFrames[i].flip(true, false); // Voltear horizontalmente
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



