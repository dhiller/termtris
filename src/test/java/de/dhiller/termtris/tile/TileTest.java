package de.dhiller.termtris.tile;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author dhiller.
 */
public class TileTest {

    @Test
    public void rotate() {
        Tile tile = TileFactory.tile(0);
        Tile rotated = tile.rotate();
        assertThat(rotated.getLines(), is(not(tile.getLines())));
    }

}
