package de.dhiller.termtris.tile;

import de.dhiller.termtris.map.Orientation;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

/**
 * 10.09.13 {USER}.
 */
public class TileFactoryTest {

    @Test
    public void getTiles() {
        assertThat(TileFactory.tile(0),is(notNullValue()));
        assertThat(TileFactory.tile(3),is(notNullValue()));
    }

    @Test
    public void doesNotContainPipes() {
        assertThat(TileFactory.tile(0).getLines(), not(hasItem(containsString("|"))));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void noMoreTiles() {
        TileFactory.tile(4);
    }

    @Test
    public void tileWithOrientation() {
        Tile tile = TileFactory.tile(0);
        assertThat(tile.rotate().getLines(), is(TileFactory.tile(0, Orientation.EAST).getLines()));
        assertThat(tile.rotate().rotate().getLines(), is(TileFactory.tile(0, Orientation.SOUTH).getLines()));
        assertThat(tile.rotate().rotate().rotate().getLines(), is(TileFactory.tile(0, Orientation.WEST).getLines()));
        assertThat(tile.getLines(), is(TileFactory.tile(0, Orientation.NORTH).getLines()));
    }

}
