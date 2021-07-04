package push_box;

import java.util.ArrayList;
import java.util.List;

/**
 * coordinate:
 * -1/0: walkable road ( -1 means reached in operation search )
 * 1: wall ( the game map is always surrounded by enclosed walls )
 * 2: box
 */
class PushProcess {
    int[][] map;
    int maxHeight, maxWidth;
    int heroX, heroY;
    int[][] targets;

    public enum Direction{
        UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

        public final int x, y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public enum Status {
        FINISHED, // all boxes at target
        DEAD,     // all boxes not moveable but not finished
        ALIVE   // some box moveable
    }

    public static class Operation {
        int boxX, boxY;
        Direction direction;

        public Operation(int boxX, int boxY, Direction direction) {
            this.boxX = boxX;
            this.boxY = boxY;
            this.direction = direction;
        }

        public int getX() {
            return boxX;
        }

        public int getY() {
            return boxY;
        }

        public Direction getDirection() {
            return this.direction;
        }
    }

    public PushProcess(int[][] map, int maxHeight, int maxWidth, int heroX, int heroY, int[][] targets) {
        this.heroX = heroX;
        this.heroY = heroY;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.targets = targets;
        setMap(map);
    }

    public void setMap(int[][] newMap) {
        this.map = new int[newMap.length][newMap[0].length];
        for (int i = 0; i < map.length; i++) {
            System.arraycopy(newMap[i], 0, this.map[i], 0, newMap[0].length);
        }
    }
    private boolean isTarget(int x, int y) {
        for (int[] target : targets) {
            if (target[0] == x && target[1] == y) {
                return true;
            }
        }
        return false;
    }

    private boolean moveAble(int x, int y) {
        // 0 is empty and -1 is marked as arrived before.
        return (map[x - 1][y] <= 0 && map[x + 1][y] <= 0) || (map[x][y - 1] <= 0 && map[x][y + 1] <= 0);
    }

    public Status getStatus() {
        List<int[]> boxes = new ArrayList<>();
        for (int i = 1; i <= maxHeight; i++) {
            for (int j = 1; j <= maxWidth; j++) {
                if (map[i][j] == 2) {
                    boxes.add(new int[]{i, j});
                }
            }
        }

        boolean allAtTarget = true, allNotMoveable = true;
        for (int[] box : boxes) {
            int x = box[0], y = box[1];
            allAtTarget = allAtTarget && isTarget(x, y);
            allNotMoveable = allNotMoveable && !moveAble(x, y);
        }

        if (allAtTarget) {
            return Status.FINISHED;
        } else if (allNotMoveable) {
            return Status.DEAD;
        } else {
            return Status.ALIVE;
        }
    }

    private void dfsOperations(List<Operation> operations) {
        map[heroX][heroY] = -1;
        for (Direction direction : Direction.values()) {
            int x = direction.x + heroX;
            int y = direction.y + heroY;
            if (map[x][y] == 0) { // a road that not reached ever, go there
                heroX = x;
                heroY = y;
                dfsOperations(operations);
                heroX = x-direction.x;
                heroY = y-direction.y;
            } else if (map[x][y] == 2 && moveAble(x, y)) { // a moveable box found means a new operation
                operations.add(new Operation(x, y, direction));
            }
        }
    }

    public List<Operation> searchAllOperations() {
        ArrayList<Operation> operations = new ArrayList<>();
        dfsOperations(operations);
        return operations;
    }

    public void draw(String cmt) {
        System.out.printf("%s:\n", cmt);
        for (int i = 0; i <= maxHeight; i++) {
            System.out.printf("%d ", i);
        }
        System.out.println();
        for (int i = 1; i <= maxHeight; i++) {
            System.out.printf("%d ", i);
            for (int j = 1; j <= maxWidth; j++) {
                if (map[i][j] <= 0) {
                    if (isTarget(i, j)) {
                        System.out.print("⊞ ");
                    } else {
                        System.out.print("□ ");
                    }
                } else if (map[i][j] == 2) {
                    System.out.print("◩ ");
                } else {
                    System.out.print("  ");
                }
            }
            switch (i) {
                case 2 -> System.out.print("        ⊞: Target\n");
                case 3 -> System.out.print("        □: Empty\n");
                case 4 -> System.out.print("        ◩: Box\n");
                default -> System.out.println();
            }
        }
    }

}
