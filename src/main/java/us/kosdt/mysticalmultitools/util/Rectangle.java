package us.kosdt.mysticalmultitools.util;

import net.minecraft.client.gui.GuiScreen;

public class Rectangle {

    public final Point position;
    public final int width;
    public final int height;

    public Rectangle(Point position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public boolean contains(Point point) {
        int xOffset = point.x - position.x;
        int yOffset = point.y - position.y;
        return xOffset >= 0 && xOffset <= this.width && yOffset >= 0 && yOffset <= this.height;
    }

    public boolean contains(Rectangle other) {
        return contains(other.topLeft())
                && contains(other.topRight())
                && contains(other.bottomLeft())
                && contains(other.bottomRight());
    }

    public boolean overlaps(Rectangle other) {
        return contains(other.topLeft())
                || contains(other.topRight())
                || contains(other.bottomLeft())
                || contains(other.bottomRight());
    }

    public Rectangle translateTo(Point newPosition) {
        return new Rectangle(newPosition, width, height);
    }

    public Rectangle translateBy(Point offset) {
        return new Rectangle(position.add(offset), width, height);
    }

    public Rectangle resizeTo(int newWidth, int newHeight) {
        return new Rectangle(position, newWidth, newHeight);
    }

    public Rectangle resizeBy(int addWidth, int addHeight) {
        return new Rectangle(position, width + addWidth, height + addHeight);
    }

    public Rectangle scaleBy(double scaleX, double scaleY) {
        return new Rectangle(position, (int)(width * scaleX), (int)(height * scaleY));
    }

    public Rectangle withX(int newX) {
        return translateTo(new Point(newX, position.y));
    }

    public Rectangle withY(int newY) {
        return translateTo(new Point(position.x, newY));
    }

    public Rectangle withWidth(int newWidth) {
        return resizeTo(newWidth, height);
    }

    public Rectangle withHeight(int newHeight) {
        return resizeTo(width, newHeight);
    }

    public Point topLeft() {
        return position;
    }

    public Point topRight() {
        return position.add(new Point(width, 0));
    }

    public Point bottomLeft() {
        return position.add(new Point(0, height));
    }

    public Point bottomRight() {
        return position.add(new Point(width, height));
    }

    public int left() {
        return position.x;
    }

    public int top() {
        return position.y;
    }

    public int right() {
        return position.x + width;
    }

    public int bottom() {
        return position.y + height;
    }

    public void draw(int color) {
        GuiScreen.drawRect(
                position.x,
                position.y,
                position.x + width,
                position.y + height,
                color
        );
    }

}

