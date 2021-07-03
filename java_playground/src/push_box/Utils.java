package push_box;

public class Utils {
    public static boolean isTarget(int x, int y) {
        if (x % 2 != 0 && y % 2 != 0) {
            return x != 3 || y != 3;
        }
        return false;
    }
}
