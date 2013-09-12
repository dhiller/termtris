package de.dhiller.termtris.tile;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * 10.09.13 {USER}.
 */
public class Tile {
    private ImmutableList<String> lines;

    Tile(List<String> lines) {
        this.lines= ImmutableList.copyOf(lines);
    }

    public List<String> getLines() {
        return lines;
    }

    Tile rotate() {
        List<String> rotated = new ArrayList<>();
        for (int i = 0, n = lines.get(0).length(); i < n ; i++) {
            final StringBuilder builder = new StringBuilder();
            for (int k = 0, m = lines.size(); k < m; k++) {
                builder.append(lines.get(k).substring(i,i+1));
            }
            rotated.add(builder.toString());
        }
        return new Tile(rotated);
    }
}
