package push_box;

/**
 * push all boxes to target
 * 1. find all operations can perform through DFS ( an operation is a push )
 * 2. perform each operation as a BFS, record it with a process ( a process describes the map and the operation
 * at the certain time )
 * 3. find a list of process to show the solution
 */
public class PushBox {


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

    int maxWidth = 5;
    int maxHeight = 5;
    int heroX = 3, heroY = 3; // hero position

}
