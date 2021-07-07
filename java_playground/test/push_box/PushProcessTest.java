package push_box;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;

class PushProcessTest {

    int[][] bh3Map = {
            {1, 1, 1, 1, 1, 1, 1}, // 1 = wall
            {1, 0, 0, 0, 0, 0, 1}, // 0 = empty
            {1, 0, 2, 2, 2, 0, 1}, // 2 = box
            {1, 0, 2, 0, 2, 0, 1},
            {1, 0, 2, 2, 2, 0, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1}
    };

    int[][] deadMap = {
            {1, 1, 1, 1, 1, 1, 1}, // 1 = wall
            {1, 2, 2, 0, 0, 2, 1}, // 0 = empty
            {1, 0, 0, 0, 0, 0, 1}, // 2 = box
            {1, 0, 0, 0, 0, 2, 1},
            {1, 2, 0, 0, 0, 0, 1},
            {1, 2, 0, 0, 2, 2, 1},
            {1, 1, 1, 1, 1, 1, 1}
    };

    int[][] finishedMap = {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 2, 0, 2, 0, 2, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 2, 0, 0, 0, 2, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 2, 0, 2, 0, 2, 1},
            {1, 1, 1, 1, 1, 1, 1}
    };

    int bh3MaxHeight = 5, bh3MaxWidth = 5, bh3HeroX = 3, bh3HeroY = 3;
    int[][] bh3Targets = new int[][] {
            {1, 1}, {1, 3}, {1, 5},
            {3, 1}, {3, 5},
            {5, 1}, {5, 3}, {5, 5}
    };

    @Test
    void searchAllOperations() {
        PushProcess process = new PushProcess(bh3Map, bh3MaxHeight, bh3MaxWidth, bh3HeroX, bh3HeroY, bh3Targets);
        List<PushProcess.Operation> operations = process.searchAllOperations();
        process.draw("test");
        for (PushProcess.Operation operation : operations) {
            System.out.printf("%d, %d, %s\n", operation.getX(), operation.getY(), operation.getDirection());
        }
        Assertions.assertEquals(4, operations.size());
        System.out.println();
        process.setMap(deadMap);
        operations = process.searchAllOperations();
        Assertions.assertEquals(0, operations.size());
        process.setMap(finishedMap);
        operations = process.searchAllOperations();
        for (PushProcess.Operation operation : operations) {
            System.out.printf("%d, %d, %s\n", operation.getX(), operation.getY(), operation.getDirection());
        }
        Assertions.assertEquals(8, operations.size());
    }

    @Test
    void testPerformOperation() {
        PushProcess oldProcess = new PushProcess(bh3Map, bh3MaxHeight, bh3MaxWidth, bh3HeroX, bh3HeroY, bh3Targets);
        Assertions.assertEquals(2, oldProcess.map[2][3]);
        PushProcess process = oldProcess.performOperation(new PushProcess.Operation(2, 3, PushProcess.Direction.UP));
        process.draw("test");
        Assertions.assertEquals(2, process.map[1][3]);
        Assertions.assertEquals(0, process.map[2][3]);
        Assertions.assertEquals(2, process.heroX);
        Assertions.assertEquals(3, process.heroY);
        List<PushProcess.Operation> operations = process.searchAllOperations();
        Assertions.assertEquals(5, operations.size());
        for (PushProcess.Operation operation : operations) {
            System.out.printf("%d, %d, %s\n", operation.getX(), operation.getY(), operation.getDirection());
        }
    }

    @Test
    void testDraw() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();//把标准输出指定到ByteArrayOutputStream中
        PrintStream console = System.out;// 获取System.out 输出流的句柄
        System.setOut(new PrintStream(bytes));//将原本输出到控制台Console的字符流重定向到bytes

        PushProcess process = new PushProcess(bh3Map, bh3MaxHeight, bh3MaxWidth, bh3HeroX, bh3HeroY, bh3Targets);
        process.draw("test");

        Assertions.assertEquals("test:\n" +
                "0 1 2 3 4 5 \n" +
                "1 ⊞ □ ⊞ □ ⊞ \n" +
                "2 □ ◩ ◩ ◩ □         ⊞: Target\n" +
                "3 ⊞ ◩ □ ◩ ⊞         □: Empty\n" +
                "4 □ ◩ ◩ ◩ □         ◩: Box\n" +
                "5 ⊞ □ ⊞ □ ⊞ \n", bytes.toString());

        System.setOut(console);
        System.out.println(bytes);
    }

    @Test
    void testGetStatus() {
        PushProcess process = new PushProcess(bh3Map, bh3MaxHeight, bh3MaxWidth, bh3HeroX, bh3HeroY, bh3Targets);
        Assertions.assertEquals(PushProcess.Status.ALIVE, process.getStatus());
        process.setMap(deadMap);
        Assertions.assertEquals(PushProcess.Status.DEAD, process.getStatus());
        process.setMap(finishedMap);
        Assertions.assertEquals(PushProcess.Status.FINISHED, process.getStatus());
    }

    @Test
    void testHash() {
        PushProcess process1 = new PushProcess(bh3Map, bh3MaxHeight, bh3MaxWidth, bh3HeroX, bh3HeroY, bh3Targets);
        PushProcess process2 = new PushProcess(bh3Map, bh3MaxHeight, bh3MaxWidth, bh3HeroX, bh3HeroY, bh3Targets);
        HashSet<PushProcess> set = new HashSet<>();
        set.add(process1);
        set.add(process2);
        Assertions.assertEquals(process1, process2);
        Assertions.assertEquals(1, set.size());
    }
}