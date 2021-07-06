package push_box;

import org.junit.jupiter.api.Test;

import java.util.List;

public class PushBoxTest {
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
        PushBox pushBox = new PushBox(map, targets, 1, 1, 100);
        pushBox.go();
        List<PushProcess> processes = pushBox.getProcesses();
        for (PushProcess process : processes) {
            process.draw("test");
        }
    }

    @Test
    void loopWay() {
        // todo
    }
}
