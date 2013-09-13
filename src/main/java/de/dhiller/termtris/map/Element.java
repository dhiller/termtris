package de.dhiller.termtris.map;

import de.dhiller.termtris.tile.Tile;
import de.dhiller.termtris.tile.TileFactory;
import org.mockito.internal.util.collections.Sets;

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
        return tile().getLines().get(row - getCoordinate().y);
    }

    public int width() {
        return tile().getLines().get(0).length();
    }

    public int height() {
        return tile().getLines().size();
    }

    public boolean collidesWith(Element that) {
        int maxX = Math.max(getCoordinate().x, that.getCoordinate().x);
        int maxWidth = Math.max(width(), that.width());
        int maxY = Math.max(getCoordinate().y, that.getCoordinate().y);
        int maxHeight = Math.max(height(), that.height());
        Map.RawMapData rawMap = Map.createRawMap(Sets.newSet(this, that), maxX + maxWidth, maxY + maxHeight);
        return !rawMap.getCollisions().isEmpty();
    }

}
