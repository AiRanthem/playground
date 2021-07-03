package push_box;

import java.util.ArrayList;
import java.util.List;

class PushProcess {
    int[][] map;

    public PushProcess(int[][] map) {
        this.map = new int[7][7];
        for (int i = 0; i < map.length; i++) {
            System.arraycopy(map[i], 0, this.map[i], 0, map[0].length);
        }
    }

    private boolean moveAble(int x, int y) {
        return (map[x - 1][y] == 0 && map[x + 1][y] == 0) || (map[x][y - 1] == 0 && map[x][y + 1] == 0);
    }

    public PushBox.PushProcessStatus getStatus() {
        List<int[]> boxes = new ArrayList<>();
        for (int i = 1; i <= PushBox.maxHeight; i++) {
            for (int j = 1; j <= PushBox.maxWidth; j++) {
                if (map[i][j] == 2) {
                    boxes.add(new int[]{i, j});
                }
            }
        }

        boolean allAtTarget = true, allNotMoveable = true;
        for (int[] box : boxes) {
            int x = box[0], y = box[1];
            allAtTarget = allAtTarget && Utils.isTarget(x, y);
            allNotMoveable = allNotMoveable && !moveAble(x, y);
        }

        if (allAtTarget) {
            return PushBox.PushProcessStatus.FINISHED;
        } else if (allNotMoveable) {
            return PushBox.PushProcessStatus.DEAD;
        } else {
            return PushBox.PushProcessStatus.RUNNING;
        }
    }

    public void draw(String cmt) {
        System.out.printf("%s:\n", cmt);
        for (int i = 0; i <= PushBox.maxHeight; i++) {
            System.out.printf("%d ", i);
        }
        System.out.println();
        for (int i = 1; i <= PushBox.maxHeight; i++) {
            System.out.printf("%d ", i);
            for (int j = 1; j <= PushBox.maxWidth; j++) {
                if (map[i][j] == 0) {
                    if (Utils.isTarget(i, j)) {
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
        System.out.println();
    }

}
