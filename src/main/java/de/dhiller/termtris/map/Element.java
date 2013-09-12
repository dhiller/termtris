package de.dhiller.termtris.map;

import de.dhiller.termtris.tile.Tile;
import de.dhiller.termtris.tile.TileFactory;

import java.awt.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dhiller
 */
public class Element implements Comparable<Element>{

    private final int tileIndex;

    private Point coordinate;
    private Orientation orientation;

    public Element(int tileIndex, Point coordinate, Orientation orientation) {
        this.tileIndex = TileFactory.checkTileIndex(tileIndex);
        this.coordinate = checkNotNull(coordinate);
        this.orientation = checkNotNull(orientation);
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public int compareTo(Element o) {
        final int yDiff = coordinate.y - o.coordinate.y;
        if (yDiff!=0) {
            return yDiff;
        }
        return coordinate.x - o.coordinate.x;
    }

    @Override
    public String toString() {
        return "Element{" +
                "tileIndex=" + tileIndex +
                ", coordinate=" + coordinate +
                ", orientation=" + orientation +
                '}';
    }

    Tile tile() {
        return TileFactory.tile(getTileIndex(), getOrientation());
    }

    String lineFromTile(int row) {
        return tile().getLines().get(row- getCoordinate().x);
    }
}
