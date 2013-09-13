package de.dhiller.termtris.map;

import org.junit.Test;

import java.awt.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author dhiller.
 */
public class ElementTest {

    @Test
    public void width() {
        assertThat(new Element(0, new Point(0,0), Orientation.NORTH).width(),is(4));
    }

    @Test
    public void height() {
        assertThat(new Element(0, new Point(0,0), Orientation.NORTH).height(),is(4));
    }

    @Test
    public void exactSamePosition() {
        assertThat(new Element(0,new Point(0,0),Orientation.NORTH).collidesWith(new Element(0,new Point(0,0),Orientation.NORTH)),is(true));
    }

    @Test
    public void besides() {
        assertThat(new Element(0,new Point(0,0),Orientation.NORTH).collidesWith(new Element(0,new Point(1,0),Orientation.NORTH)),is(false));
    }
}
