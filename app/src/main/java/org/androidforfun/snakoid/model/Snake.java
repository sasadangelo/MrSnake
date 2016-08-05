package org.androidforfun.snakoid.model;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeBody> parts = new ArrayList<>();
    private Direction direction;
    private Direction neckDirection;

    public Snake() {
        reset();
    }
    
    public void turnAntiClockwise() {
        SnakeHead head = (SnakeHead) parts.get(0);
        switch (direction) {
            case UP:
                neckDirection=Direction.UP_LEFT;
                direction=Direction.LEFT;
                break;
            case LEFT:
                neckDirection=Direction.LEFT_DOWN;
                direction=Direction.DOWN;
                break;
            case DOWN:
                neckDirection=Direction.DOWN_RIGHT;
                direction=Direction.RIGHT;
                break;
            case RIGHT:
                neckDirection=Direction.RIGHT_UP;
                direction=Direction.UP;
                break;
        }
    }
    
    public void turnClockwise() {
        SnakeHead head = (SnakeHead) parts.get(0);
        //SnakeBody neck = (SnakeBody) parts.get(1);
        switch (direction) {
            case UP:
                neckDirection=Direction.UP_RIGHT;
                direction=Direction.RIGHT;
                break;
            case RIGHT:
                neckDirection=Direction.RIGHT_DOWN;
                direction=Direction.DOWN;
                break;
            case DOWN:
                neckDirection=Direction.DOWN_LEFT;
                direction=Direction.LEFT;
                break;
            case LEFT:
                neckDirection=Direction.LEFT_UP;
                direction=Direction.UP;
                break;
        }
    }
    
    public void eat() {
        SnakeTail tail = (SnakeTail) parts.get(parts.size()-1);
        parts.add(parts.size()-1, new SnakeBody(tail.x, tail.y, tail.direction));
        switch (tail.direction) {
            case UP:
                tail.y+=1;
                if(tail.y > 12)
                    tail.y = 0;
                break;
            case DOWN:
                tail.y-=1;
                if(tail.y < 0)
                    tail.y = 12;
                break;
            case LEFT:
                tail.x+=1;
                if(tail.x > 9)
                    tail.x = 0;
                break;
            case RIGHT:
                tail.x-=1;
                if(tail.x < 0)
                    tail.x = 9;
                break;
        }
    }
    
    public void advance() {
        int last = parts.size() - 1;
        SnakeHead head = (SnakeHead) parts.get(0);
        SnakeBody neck = parts.get(1);
        SnakeTail tail = (SnakeTail) parts.get(last);

        for(int i = last; i > 0; i--) {
            SnakeBody before = parts.get(i-1);
            SnakeBody part = parts.get(i);
            part.x = before.x;
            part.y = before.y;
            part.direction=before.direction;
        }

        head.direction=direction;
        switch (direction) {
            case UP:
                head.y -= 1;
                break;
            case LEFT:
                head.x -= 1;
                break;
            case DOWN:
                head.y += 1;
                break;
            case RIGHT:
                head.x += 1;
                break;
        }

        if(head.x < 0)
            head.x = 9;
        if(head.x > 9)
            head.x = 0;
        if(head.y < 0)
            head.y = 12;
        if(head.y > 12)
            head.y = 0;

        switch (neckDirection) {
            case UP_RIGHT:
            case LEFT_UP:
            case DOWN_LEFT:
            case RIGHT_DOWN:
            case LEFT_DOWN:
            case UP_LEFT:
            case DOWN_RIGHT:
            case RIGHT_UP:
                neck.direction=neckDirection;
                neckDirection=Direction.UP;
                break;
        }

        switch (tail.direction) {
            case DOWN_LEFT:
            case UP_LEFT:
                tail.direction=Direction.LEFT;
                break;
            case LEFT_UP:
            case RIGHT_UP:
                tail.direction=Direction.UP;
                break;
            case RIGHT_DOWN:
            case LEFT_DOWN:
                tail.direction=Direction.DOWN;
                break;
            case UP_RIGHT:
            case DOWN_RIGHT:
                tail.direction=Direction.RIGHT;
                break;
        }
    }

    public void reset() {
        direction = Direction.UP;
        neckDirection = Direction.UP;
        parts.clear();
        parts.add(new SnakeHead(5, 6, Direction.UP));
        parts.add(new SnakeBody(5, 7, Direction.UP));
        parts.add(new SnakeBody(5, 8, Direction.UP));
        parts.add(new SnakeTail(5, 9, Direction.UP));
    }

    public boolean checkBitten() {
        int len = parts.size();
        SnakeBody head = parts.get(0);
        for(int i = 1; i < len; i++) {
            SnakeBody part = parts.get(i);
            if(part.x == head.x && part.y == head.y)
                return true;
        }        
        return false;
    }

    public Direction getDirection() {
        return direction;
    }

    public SnakeHead getSnakeHead() {
        return (SnakeHead) parts.get(0);
    }

    public SnakeTail getSnakeTail() {
        return (SnakeTail) parts.get(parts.size()-1);
    }

    public SnakeBody getSnakeBody(int i) {
        return parts.get(i);
    }

    public int getLength() {
        return parts.size();
    }
}
