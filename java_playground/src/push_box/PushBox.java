package push_box;

/**
 * push all boxes to target
 */
public class PushBox {
    enum PushProcessStatus {
        FINISHED, // all boxes at target
        DEAD,     // all boxes not moveable but not finished
        RUNNING   // some box moveable
    }

    int[][] origMap = {
            {1, 1, 1, 1, 1, 1, 1}, // 1 = wall
            {1, 0, 0, 0, 0, 0, 1}, // 0 = empty
            {1, 0, 2, 2, 2, 0, 1}, // 2 = box
            {1, 0, 2, 0, 2, 0, 1},
            {1, 0, 2, 2, 2, 0, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1}
    };

//    int[][] origMap = {
//            {1, 1, 1, 1, 1, 1, 1}, // 1 = wall
//            {1, 2, 2, 0, 0, 2, 1}, // 0 = empty
//            {1, 0, 0, 0, 0, 0, 1}, // 2 = box
//            {1, 0, 0, 0, 0, 0, 1},
//            {1, 2, 0, 0, 0, 2, 1},
//            {1, 2, 0, 0, 2, 2, 1},
//            {1, 1, 1, 1, 1, 1, 1}
//    };

    public static int maxWidth = 5;

    public static int maxHeight = 5;

    public static void main(String[] args) {
        PushBox pushBox = new PushBox();
        PushProcess process = new PushProcess(pushBox.origMap);
        PushProcessStatus status = process.getStatus();
        System.out.println(status.name());
        process.draw("test");
    }
}
