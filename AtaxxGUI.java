package ataxx;

import ucb.gui2.TopLevel;
import ucb.gui2.LayoutSpec;

import java.util.Observable;
import java.util.Observer;

import java.io.Writer;
import java.io.PrintWriter;
class AtaxxGUI extends TopLevel implements Observer, Reporter {
.
    

    /** Minimum size of board in pixels. */
    private static final int MIN_SIZE = 300;

    /** A new display observing MODEL, with TITLE as its window title.
     *  It uses OUTCOMMANDS to send commands to a game instance, using the
     *  same commands as the text format for Ataxx. */
    AtaxxGUI(String title, Board model, Writer outCommands) {
        super(title, true);
        addMenuButton("Game->Quit", this::quit);
        addMenuButton("Options->Seed...", this::setSeed);
        _model = model;
        _widget = new AtaxxBoardWidget(model);
        _out = new PrintWriter(outCommands, true);
        add(_widget,
            new LayoutSpec("height", "1",
                           "width", "REMAINDER",
                           "ileft", 5, "itop", 5, "iright", 5,
                           "ibottom", 5));
        setMinimumSize(MIN_SIZE, MIN_SIZE);
        _widget.addObserver(this);
        _model.addObserver(this);
    }

    /** Execute the "Quit" button function. */
    private synchronized void quit(String unused) {
        _out.printf("quit%n");
        setChanged();
        notifyObservers("Quit");
    }

    /** Execute Seed... command. */
    private synchronized void setSeed(String unused) {
        String resp =
            getTextInput("Random Seed", "Get Seed", "question", "");
        if (resp == null) {
            return;
        }
        try {
            long s = Long.parseLong(resp);
            _out.printf("seed %d%n", s);
        } catch (NumberFormatException excp) {
            return;
        }
    }

    @Override
    public void errMsg(String format, Object... args) {
        System.out.println(format);
    }

    @Override
    public void outcomeMsg(String format, Object... args) {
        System.out.println(format);
    }

    @Override
    public void moveMsg(String format, Object... args) {
        System.out.println(format);
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs == _model) {
            return;
        } else if (obs == _widget) {
            return;
        }
    }

//    /** Return mouse's row at last click (may be out of range if mouse off
//     *  the board). */
//    int mouseRow() {
//        return _widget.mouseRow();
//    }
//
//    /** Return mouse's column at last click (may be out of range if mouse off
//     *  the board). */
//    int mouseCol() {
//        return _widget.mouseCol();
//    }

    /** Respond to a click on SQ while in "play" mode. */
    private void movePiece(String sq) {
        System.out.println(sq);
    }

    /** Contains the drawing logic for the Ataxx model. */
    private AtaxxBoardWidget _widget;
    /** The model of the game. */
    private Board _model;
    /** Output sink for sending commands to a game. */
    private PrintWriter _out;

}
