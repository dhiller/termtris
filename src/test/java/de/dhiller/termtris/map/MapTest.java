package de.dhiller.termtris.map;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author dhiller.
 */
public class MapTest {

    @Mock
    private Screen screenWriter;

    @Mock
    private TerminalSize terminalSize;

    private final Map underTest = new Map();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(terminalSize.getColumns()).thenReturn(80);
        when(terminalSize.getRows()).thenReturn(25);

        when(screenWriter.getTerminalSize()).thenReturn(terminalSize);
    }

    @Test
    public void drawEmptyMap() throws IOException {
        new Map().draw(screenWriter);
        verifyScreen(screenWriter, "drawEmptyMap");
    }

    @Test
    public void getElementsForRowWithTile1FacingNorth() {
        Element element = new Element(0, new Point(0, 0), Orientation.NORTH);
        underTest.addElement(element);
        assertThat(underTest.getElementsOnRow(0),contains(element));
        assertThat(underTest.getElementsOnRow(3),contains(element));
        assertThat(underTest.getElementsOnRow(4).isEmpty(),is(true));
    }

    @Test
    public void getElementsForRowWithTile1FacingEast() {
        Element element = new Element(0, new Point(0, 0), Orientation.EAST);
        underTest.addElement(element);
        assertThat(underTest.getElementsOnRow(0).isEmpty(),is(true));
        assertThat(underTest.getElementsOnRow(1),contains(element));
        assertThat(underTest.getElementsOnRow(2).isEmpty(),is(true));
        assertThat(underTest.getElementsOnRow(3).isEmpty(),is(true));
    }

    @Test
    public void drawMapWithOneElement() throws IOException {
        underTest.addElement(new Element(0,new Point(0,0),Orientation.NORTH));
        underTest.draw(screenWriter);
        verifyScreen(screenWriter, "mapWithTile1");
    }

    private void verifyScreen(Screen screenWriter, String screenName) throws IOException {
        ArgumentCaptor<String> actualLine = ArgumentCaptor.forClass(String.class);
        try (BufferedReader reader = new BufferedReader(new FileReader(getClass().getResource("/screens/" + screenName + ".screen").getFile()));) {
            int lineNumber = 1;
            String expectedLine;
            while((expectedLine = reader.readLine())!=null) {
                verify(screenWriter).putString(eq(0), eq(lineNumber - 1), actualLine.capture(), eq(Terminal.Color.DEFAULT), eq(Terminal.Color.DEFAULT));
                assertThat(String.format("Line %d unexpected", lineNumber), actualLine.getValue(), is(expectedLine));
                lineNumber++;
            }
        }
    }
}
