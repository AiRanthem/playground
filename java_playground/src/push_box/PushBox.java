package push_box;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * push all boxes to target
 * 1. find all operations can perform through DFS ( an operation is a push )
 * 2. perform each operation as a DFS, record it with a process ( a process describes the map and the operation
 * at the certain time )
 * 3. find a list of process to show the solution
 */
public class PushBox {
    int[][] origMap;
    int[][] targets;
    int maxWidth;
    int maxHeight;
    int heroX, heroY;
    int maxProcess;
    Stack<PushProcess> processes;

    public PushBox(int[][] origMap, int[][] targets, int heroX, int heroY, int maxProcess) {
        this.origMap = origMap;
        this.targets = targets;
        this.heroX = heroX;
        this.heroY = heroY;
        this.maxProcess = maxProcess;
        this.maxHeight = origMap.length - 2;
        this.maxWidth = origMap[0].length - 2;
        this.processes = new Stack<>();
    }

    private boolean dfs(PushProcess process) { // true = found, false = not found
        if (processes.size() >= maxProcess) {
            return false;
        }
        processes.push(process);
        if (process.getStatus() == PushProcess.Status.FINISHED) {
            return true;
        }
        List<PushProcess.Operation> operations = process.searchAllOperations();
        for (PushProcess.Operation op : operations) {
            PushProcess nextProcess = process.performOperation(op);
            if (nextProcess.getStatus() != PushProcess.Status.DEAD) {
                if (dfs(nextProcess)) {
                    return true;
                }
            }
        }
        processes.pop();
        return false;
    }

    public void go() {
        boolean found = dfs(new PushProcess(origMap, maxHeight, maxWidth, heroX, heroY, targets));
        System.out.println(found);
    }

    public List<PushProcess> getProcesses() {
        return new ArrayList<>(processes);
    }

    public static void main(String[] args) {
        int[][] bh3Map = {
                {1, 1, 1, 1, 1, 1, 1}, // 1 = wall
                {1, 0, 0, 0, 0, 0, 1}, // 0 = empty
                {1, 0, 2, 2, 2, 0, 1}, // 2 = box
                {1, 0, 2, 0, 2, 0, 1},
                {1, 0, 2, 2, 2, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1}
        };
        int bh3MaxHeight = 5, bh3MaxWidth = 5, bh3HeroX = 3, bh3HeroY = 3;
        int[][] bh3Targets = new int[][] {
                {1, 1}, {1, 3}, {1, 5},
                {3, 1}, {3, 5},
                {5, 1}, {5, 3}, {5, 5}
        };

        int[][] simpleMap = {
                {1, 1, 1, 1, 1},
                {1, 0, 2, 0, 1},
                {1, 1, 1, 1, 1}
        };

        PushBox pushBox = new PushBox(bh3Map, bh3Targets, bh3HeroX, bh3HeroY, 100);
        pushBox.go();
        List<PushProcess> processes = pushBox.getProcesses();
        for (PushProcess process : processes) {
            process.draw("test");
        }
    }
}
