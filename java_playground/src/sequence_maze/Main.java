package sequence_maze;

public class Main {
    public static void main(String[] args) {
        // 火 1 七 2 ω 3
        SequenceMaze sequenceMaze = new SequenceMaze(3, new int[][]{
                {1, 2, 3, 1},
                {3, 2, 1, 3},
                {2, 3, 1, 2}
        });
        sequenceMaze.solve();

    }
}
