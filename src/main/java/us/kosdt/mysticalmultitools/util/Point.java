package us.kosdt.mysticalmultitools.util;

public class Point {

    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point offset) {
        return new Point(x + offset.x, y + offset.y);
    }

}
