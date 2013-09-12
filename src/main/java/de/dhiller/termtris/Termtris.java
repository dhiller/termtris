package de.dhiller.termtris;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.TerminalSize;
import de.dhiller.termtris.map.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dhiller.
 */
public class Termtris {

    private static final Logger LOGGER = LoggerFactory.getLogger(Termtris.class);

    private static Termtris termtris;
    private Screen screen;
    private Map map = new Map();

    public static void main(String[] args) throws InterruptedException {
        termtris = new Termtris();
        termtris.start();
        termtris.stop();
    }

    private void start() {
        LOGGER.info("Starting");
        screen = TerminalFacade.createScreen();
        screen.startScreen();
        TerminalSize screenSize = screen.getTerminalSize();
        LOGGER.info("Terminal screen size is {}x{}", screenSize.getColumns(), screenSize.getRows());
        LOGGER.info("Started");

        map.draw(screen);
        waitMSecs(5000);
    }

    private void waitMSecs(int timeout) {
        synchronized (this) {
            try {
                this.wait(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private void stop() throws InterruptedException {
        LOGGER.info("Stopping");
        screen.stopScreen();
        LOGGER.info("Stopped");
    }
}
