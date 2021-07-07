package push_box;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PushBoxTest {
    PushBox go(int[][] map, int[][] targets, int heroX, int heroY) {
        PushBox pushBox = new PushBox(map, targets, heroX, heroY, 100);
        pushBox.go();
        List<PushProcess> processes = pushBox.getProcesses();
        for (PushProcess process : processes) {
            process.draw();
        }
        return pushBox;
    }

    @Test
    void oneWayMap() {
        int[][] map = {
                {1, 1, 1, 1, 1, 1},
                {1, 0, 2, 0, 0, 1},
                {1, 1, 1, 1, 1, 1}
        };
        int[][] targets = {
                {1, 4}
        };
        PushBox pb = go(map, targets, 1, 1);
        List<PushProcess> processes = pb.getProcesses();
        Assertions.assertEquals(3, processes.size());
        Assertions.assertEquals(PushProcess.Status.FINISHED, processes.get(2).getStatus());
    }

    @Test
    void loopWay() {
        int[][] map = {
                {1, 1, 1, 1, 1, 1},
                {1, 0, 2, 0, 0, 1},
                {1, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1}
        };
        int[][] targets = {
                {1, 4}
        };
        PushBox pb = go(map, targets, 1, 1);
        List<PushProcess> processes = pb.getProcesses();
        Assertions.assertEquals(3, processes.size());
        Assertions.assertEquals(PushProcess.Status.FINISHED, processes.get(2).getStatus());
    }

    @Test
    void bh3XYS() {
        int[][] bh3Map = {
                {1, 1, 1, 1, 1, 1, 1}, // 1 = wall
                {1, 0, 0, 0, 0, 0, 1}, // 0 = empty
                {1, 0, 2, 2, 2, 0, 1}, // 2 = box
                {1, 0, 2, 0, 2, 0, 1},
                {1, 0, 2, 2, 2, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1}
        };
        int[][] bh3Targets = new int[][] {
                {1, 1}, {1, 3}, {1, 5},
                {3, 1}, {3, 5},
                {5, 1}, {5, 3}, {5, 5}
        };
        int bh3HeroX = 3, bh3HeroY = 3;
        PushBox pb = go(bh3Map, bh3Targets, bh3HeroX, bh3HeroY);
    }
}
