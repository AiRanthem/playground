package sequence_maze;

import java.util.Arrays;

public class SequenceMaze {
    private final int size;
    private final int[][] map;
    private final int x, y;
    private int steps;
    private int[][] solution;

    public SequenceMaze(int size, int[][] map) {
        this.size = size;
        this.map = map;
        this.steps = 0;
        this.x = map.length;
        this.y = map[0].length;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (map[i][j] > 0) {
                    steps++;
                }
            }
        }
    }

    public void solve() {
        solution = new int[map.length][map[0].length];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (map[i][j] < 0) {
                    continue;
                }
                reset(solution);
                dfs(1, i, j, 0);
                if (finished(solution)) {
                    for (int[] line : solution) {
                        for (int n : line) {
                            System.out.printf("%4d", n);
                        }
                        System.out.println();
                    }
                    return;
                }
            }
        }
        System.out.println("Solution not found");
    }

    private void reset(int[][] solution) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                solution[i][j] = 0;
            }
        }
    }

    private boolean finished(int[][] solution) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (solution[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean dfs(int turn, int i, int j, int last) {
        if (turn > steps) {
            return true;
        }
        if (i >= x || j >= y || i < 0 || j < 0 || map[i][j] < 0 || solution[i][j] != 0) {
            return false;
        }
        if (last > 0 && map[i][j] != last % size + 1) {
            return false;
        }
        solution[i][j] = turn;
        if (dfs(turn + 1, i + 1, j, map[i][j])) {
            return true;
        }
        if (dfs(turn + 1, i - 1, j, map[i][j])) {
            return true;
        }
        if (dfs(turn + 1, i, j - 1, map[i][j])) {
            return true;
        }
        if (dfs(turn + 1, i, j + 1, map[i][j])) {
            return true;
        }
        solution[i][j] = 0;
        return false;
    }
}
