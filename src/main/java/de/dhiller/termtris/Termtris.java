package de.dhiller.termtris;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @author dhiller.
 */
public class Termtris {

    private static final Logger LOGGER = LoggerFactory.getLogger(Termtris.class);

    private static Termtris termtris;
    private Terminal terminal;

    public static void main(String[] args) throws InterruptedException {
        termtris = new Termtris();
        termtris.start();
        termtris.stop();
    }

    private void start() {
        LOGGER.info("Starting");
        terminal = TerminalFacade.createTerminal();
        terminal.enterPrivateMode();
        TerminalSize screenSize = terminal.getTerminalSize();
        LOGGER.info("Terminal screen size is {}x{}", screenSize.getColumns(), screenSize.getRows());
        LOGGER.info("Started");

        terminal.setCursorVisible(true);
        char[] chars = "Hello World!".toCharArray();
        terminal.moveCursor(chars.length,0);
        for (char c: chars) {
            terminal.putCharacter(c);
        }
    }

    private void stop() throws InterruptedException {
        synchronized (this) {
            this.wait(5000);
        }
        LOGGER.info("Stopping");
        terminal.exitPrivateMode();
        LOGGER.info("Stopped");
    }
}
