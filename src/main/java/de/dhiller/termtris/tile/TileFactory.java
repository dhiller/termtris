package de.dhiller.termtris.tile;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import de.dhiller.termtris.map.Orientation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A tile occupying cells in a map.
 * A tile is laid out facing {@link de.dhiller.termtris.map.Orientation#NORTH} initially.
 *
 * @author dhiller.
 */
public class TileFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(TileFactory.class);
    private static final TileFactory TILE_FACTORY = new TileFactory();

    private final List<Tile> tiles = new ArrayList<>();

    private TileFactory() {
        int tileIndex = 1;
        URL tile = null;
        while((tile = getClass().getResource("/tiles/"+tileIndex)) != null) {
            LOGGER.debug("Reading tile {}",tile.getFile());
            List<String> lines = new ArrayList<>();
            try (BufferedReader tileReader = new BufferedReader(new FileReader(tile.getFile()));) {
                String line = null;
                int lineNo = 1;
                while ((line = tileReader.readLine()) != null) {
                    line = line.replace("|","");
                    lines.add(line);
                    LOGGER.debug("line {}: {}",lineNo,line);
                    lineNo++;
                }
            } catch (FileNotFoundException e) {
                Throwables.propagate(e);
            } catch (IOException e) {
                Throwables.propagate(e);
            }
            tiles.add(new Tile(lines));
            tileIndex++;
        }
    }

    /**
     * @param tileIndex the index for the tile
     * @return a tile in {@link de.dhiller.termtris.map.Orientation#NORTH}.
     * @throws IndexOutOfBoundsException if tile with index does not exist, i.e. either negative index or index above number of tiles
     */
    public static Tile tile(int tileIndex) {
        return TILE_FACTORY.tiles.get(tileIndex);
    }

    /**
     *
     * @param tileIndex the tile index
     * @param orientation the desired orientation
     * @return the tile in desired orientation
     * @throws IndexOutOfBoundsException if tile with index does not exist, i.e. either negative index or index above number of tiles
     * @throws NullPointerException if orientation is null
     */
    public static Tile tile(int tileIndex, Orientation orientation) {
        final Tile tileInNorthOrientation = tile(tileIndex);
        switch(checkNotNull(orientation)) {
            case EAST:
                return tileInNorthOrientation.rotate();
            case SOUTH:
                return tileInNorthOrientation.rotate().rotate();
            case WEST:
                return tileInNorthOrientation.rotate().rotate().rotate();
            case NORTH:
            default:
                return tileInNorthOrientation;
        }
    }

    public static int checkTileIndex(int tileIndex) {
        return checkElementIndex(tileIndex,TILE_FACTORY.tiles.size());
    }
}
