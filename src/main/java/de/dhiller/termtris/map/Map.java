package de.dhiller.termtris.map;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import de.dhiller.termtris.tile.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dhiller
 */
public class Map {
    public static final String DEFAULT_MAP_BOTTOM_BORDER = "--------------------------------";
    public static final int COLUMNS = 30;
    public static final int ROWS = 19;
    public static final char SPACE = " ".charAt(0);
    public static final String TILE_CELL_CHAR = "x";
    private Set<Element> elements = new TreeSet<>();

    static class RawMapData extends ArrayList<String> {
        private final List<Point> collisions = new ArrayList<>();

        boolean addCellCollision(int row, int lineColIndex) {
            return getCollisions().add(new Point(lineColIndex, row));
        }

        public List<Point> getCollisions() {
            return collisions;
        }
    }

    static RawMapData createRawMap(Set<Element> elementSet, int columns, int rows) {
        RawMapData map = new RawMapData();
        for (int row = 0 ; row < rows; row++ ) {
            StringBuilder builder = new StringBuilder();
            for(int column = 0; column < columns; column++)
                builder.append(" ");
            for (Element e : getElementsOnRow(row, elementSet)) {
                String lineOfTile = e.lineFromTile(row);
                int lineColIndex = e.getCoordinate().x;
                for (char c : lineOfTile.toCharArray()) {
                    if (c != SPACE) {
                        if(builder.charAt(lineColIndex)!=SPACE){
                            map.addCellCollision(lineColIndex, row);
                        }
                        builder.replace(lineColIndex,lineColIndex+1, TILE_CELL_CHAR);
                    }
                    lineColIndex++;
                }
            }
            map.add(builder.toString());
        }
        return map;
    }

    public void draw(Screen screen) {
        final RawMapData rawMap = createRawMap(elements, COLUMNS, ROWS);
        if(rawMap.getCollisions().isEmpty())
            drawMapWithDecorations(screen, rawMap);
        else
            throw new CollisionException(rawMap);
    }

    private void drawMapWithDecorations(Screen screen, List<String> map) {
        screen.clear();
        final ScreenWriter writer = new ScreenWriter(screen);
        for (int row = 0; row < map.size(); row++)
            writer.drawString(0, row, "|" + map.get(row) + "|");
        writer.drawString(0,map.size(),DEFAULT_MAP_BOTTOM_BORDER);
        writer.drawString(0,map.size(),DEFAULT_MAP_BOTTOM_BORDER);
        screen.refresh();
    }

    public void addElement(Element element) {
        HashSet<Element> union = Sets.newHashSet(elements);
        union.add(element);
        RawMapData rawMap = createRawMap(union, COLUMNS, ROWS);
        if(!rawMap.getCollisions().isEmpty())
            throw new CollisionException(rawMap);
        this.elements.add(checkNotNull(element));
    }

    public List<Element> getElementsOnRow(final int row) {
        return getElementsOnRow(row, elements);
    }

    public static List<Element> getElementsOnRow(int row, Set<Element> elementSet) {
        return ImmutableList.copyOf(Collections2.filter(elementSet, new ElementsOnRow(row)));
    }

    private static class ElementsOnRow implements Predicate<Element> {
        private final int row;

        public ElementsOnRow(int row) {
            this.row = row;
        }

        @Override
        public boolean apply(Element input) {
            Element element = checkNotNull(input);
            if(row < element.getCoordinate().y)
                return false;
            Tile tile = element.tile();
            return row < tile.getLines().size() && element.lineFromTile(row).contains("x");
        }
    }
}
