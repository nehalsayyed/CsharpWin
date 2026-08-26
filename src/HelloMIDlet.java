import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class HelloMIDlet extends MIDlet implements CommandListener {

    private Display display;
    private Form mainForm;
    private Command exitCommand;

    public HelloMIDlet() {
        display = Display.getDisplay(this);
        mainForm = new Form("Hello J2ME");
        mainForm.append("Hello, World!");
        
        exitCommand = new Command("Exit", Command.EXIT, 0);
        mainForm.addCommand(exitCommand);
        mainForm.setCommandListener(this);
    }

    protected void startApp() {
        display.setCurrent(mainForm);
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == exitCommand) {
            destroyApp(false);
            notifyDestroyed();
        }
    }
}
