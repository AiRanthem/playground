package push_box;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

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
    Set<PushProcess> visited;

    public PushBox(int[][] origMap, int[][] targets, int heroX, int heroY, int maxProcess) {
        this.origMap = origMap;
        this.targets = targets;
        this.heroX = heroX;
        this.heroY = heroY;
        this.maxProcess = maxProcess;
        this.maxHeight = origMap.length - 2;
        this.maxWidth = origMap[0].length - 2;
        this.processes = new Stack<>();
        this.visited = new HashSet<>(maxProcess);
    }

    private boolean dfs(PushProcess process) { // true = found, false = not found
        if (processes.size() >= maxProcess || visited.contains(process)) {
            return false;
        }
        processes.push(process);
        visited.add(process);
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
}
