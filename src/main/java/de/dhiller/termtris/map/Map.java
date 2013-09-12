package de.dhiller.termtris.map;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import de.dhiller.termtris.tile.Tile;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dhiller
 */
public class Map {
    public static final String DEFAULT_MAP_BORDER = "|                              |";
    public static final String DEFAULT_MAP_BOTTOM_BORDER = "--------------------------------";
    private Set<Element> elements = new TreeSet<>();

    public void draw(Screen screen) {
        screen.clear();
        final ScreenWriter writer = new ScreenWriter(screen);
        for (int row = 0 ; row < 20 ; row++ ) {
            String line = (row<19?DEFAULT_MAP_BORDER:DEFAULT_MAP_BOTTOM_BORDER);
            for (Element e : getElementsOnRow(row)) {
                String lineOfTile = e.lineFromTile(row);
                int lineColIndex = 1 + e.getCoordinate().y;
                StringBuilder builder = new StringBuilder();
                builder.append(line);
                for (char c : lineOfTile.toCharArray()) {
                    if (c != " ".charAt(0)) {
                        builder.replace(lineColIndex,lineColIndex+1,"x");
                    }
                    lineColIndex++;
                }
                line = builder.toString();
            }
            writer.drawString(0, row, line);
        }
        screen.refresh();
    }

    public void addElement(Element element) {
        this.elements.add(checkNotNull(element));
    }

    public List<Element> getElementsOnRow(final int row) {
        return ImmutableList.copyOf(Collections2.filter(elements, new Predicate<Element>() {
            @Override
            public boolean apply(Element input) {
                Element element = checkNotNull(input);
                if(row < element.getCoordinate().x)
                    return false;
                Tile tile = element.tile();
                return row < tile.getLines().size() && element.lineFromTile(row).contains("x");
            }
        }));
    }

}
