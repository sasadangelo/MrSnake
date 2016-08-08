package org.androidforfun.snakoid.view;

import org.androidforfun.snakoid.framework.Graphics;
import org.androidforfun.snakoid.framework.Pixmap;
import org.androidforfun.snakoid.model.Fruit;
import org.androidforfun.snakoid.model.Snake;
import org.androidforfun.snakoid.model.SnakeBody;
import org.androidforfun.snakoid.model.SnakeHead;
import org.androidforfun.snakoid.model.SnakeTail;
import org.androidforfun.snakoid.model.SnakoidWorld;

public class SnakoidWorldRenderer {
    private Graphics g;

    public SnakoidWorldRenderer(Graphics graphics) {
        g=graphics;
    }

    public void draw() {
        Snake snake = SnakoidWorld.getInstance().getSnake();
        Fruit fruit = SnakoidWorld.getInstance().getFruit();

        Pixmap fruitPixmap = null;
        switch(fruit.getType()) {
            case Fruit.APPLE:
                fruitPixmap = Assets.apple;
                break;
            case Fruit.CHERRIES:
                fruitPixmap = Assets.cherries;
                break;
            case Fruit.ORANGE:
                fruitPixmap = Assets.orange;
                break;
        }
        int x = fruit.getX() * 32;
        int y = fruit.getY() * 32;
        g.drawPixmap(fruitPixmap, x, y);
        
        int len = snake.getLength();
        Pixmap snakeBodyPixmap = null;
        for(int i = 0; i < len; i++) {
            SnakeBody snakeBody = snake.getSnakeBody(i);
            if (snakeBody instanceof SnakeHead) {
                switch (snakeBody.getDirection()) {
                    case UP:
                        snakeBodyPixmap=Assets.headUp;
                        break;
                    case DOWN:
                        snakeBodyPixmap=Assets.headDown;
                        break;
                    case LEFT:
                        snakeBodyPixmap=Assets.headLeft;
                        break;
                    case RIGHT:
                        snakeBodyPixmap=Assets.headRight;
                        break;
                }
            } else if (snakeBody instanceof SnakeTail) {
                switch (snakeBody.getDirection()) {
                    case UP:
                        snakeBodyPixmap=Assets.tailUp;
                        break;
                    case DOWN:
                        snakeBodyPixmap=Assets.tailDown;
                        break;
                    case LEFT:
                        snakeBodyPixmap=Assets.tailLeft;
                        break;
                    case RIGHT:
                        snakeBodyPixmap=Assets.tailRight;
                        break;
                }
            } else {
                switch (snakeBody.getDirection()) {
                    case UP:
                    case DOWN:
                        snakeBodyPixmap=Assets.bodyVertical;
                        break;
                    case LEFT:
                    case RIGHT:
                        snakeBodyPixmap=Assets.bodyHorizontal;
                        break;
                    // The turn LEFT - DOWN anticlockwise is similar to UP - RIGHT:
                    //
                    //  ___
                    // |
                    // |
                    case UP_RIGHT:
                    case LEFT_DOWN:
                        snakeBodyPixmap=Assets.bodyCornerUpRight;
                        break;
                    // The turn UP - LEFT anticlockwise is similar to RIGHT - DOWN:
                    //
                    //  ___
                    //     |
                    //     |
                    case RIGHT_DOWN:
                    case UP_LEFT:
                        snakeBodyPixmap=Assets.bodyCornerRightDown;
                        break;
                    // The turn RIGHT - UP anticlockwise is similar to DOWN - LEFT:
                    //
                    //
                    //     |
                    //  ___|
                    case DOWN_LEFT:
                    case RIGHT_UP:
                        snakeBodyPixmap=Assets.bodyCornerDownLeft;
                        break;
                    // The turn DOWN - RIGHT anticlockwise is similar to LEFT - UP:
                    //
                    //
                    // |
                    // |___
                    case LEFT_UP:
                    case DOWN_RIGHT:
                        snakeBodyPixmap=Assets.bodyCornerLeftUp;
                        break;
                }
            }
            x = snakeBody.getX() * 32;
            y = snakeBody.getY() * 32;
            g.drawPixmap(snakeBodyPixmap, x, y);
        }
        
        g.drawPixmap(Assets.controlpanelshadow, 0, 388);
        g.drawPixmap(Assets.controlpanel, 0, 392);
    }
}
