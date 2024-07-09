package Keys;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import entities.Jugador;


public class KeyHandler extends InputAdapter {
    private Jugador jugador;
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    public KeyHandler(Jugador jugador) {
        this.jugador = jugador;
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.W:
                upPressed = true;
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                downPressed = true;
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                leftPressed = true;
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                rightPressed = true;
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.W:
                upPressed = false;
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                downPressed = false;
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                leftPressed = false;
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                rightPressed = false;
                break;
            default:
                return false;
        }
        return true;
    }

    public void update() {
        if (upPressed) {
            jugador.move(Jugador.Direction.UP);
        } else if (downPressed) {
            jugador.move(Jugador.Direction.DOWN);
        } else if (leftPressed) {
            jugador.move(Jugador.Direction.LEFT);
        } else if (rightPressed) {
            jugador.move(Jugador.Direction.RIGHT);
        } else {
            jugador.move(Jugador.Direction.NONE);
        }
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
