import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class HelloMIDlet extends MIDlet implements CommandListener {

    private Display display;
    private CustomMenuCanvas menuCanvas;
    private Command exitCommand;

    public HelloMIDlet() {
        display = Display.getDisplay(this);
        menuCanvas = new CustomMenuCanvas();
        
        exitCommand = new Command("Exit", Command.EXIT, 0);
        menuCanvas.addCommand(exitCommand);
        menuCanvas.setCommandListener(this);
    }

    protected void startApp() {
        display.setCurrent(menuCanvas);
    }

    protected void pauseApp() {}

    protected void destroyApp(boolean unconditional) {}

    public void commandAction(Command c, Displayable d) {
        if (c == exitCommand) {
            destroyApp(false);
            notifyDestroyed();
        }
    }
}

class CustomMenuCanvas extends Canvas {

    private int selectedIndex = 0;
    private final String[] menuItems = { "Start Game", "High Scores", "Settings", "About" };

    public CustomMenuCanvas() {
        // Enable full screen mode on devices that support it
        setFullScreenMode(true);
    }

    protected void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        // 1. Draw Background Gradient
        for (int y = 0; y < height; y++) {
            // Dark blue to dark purple smooth gradient
            int r = 20 + (y * 30 / height);
            int b = 60 + (y * 80 / height);
            g.setColor(r, 20, b);
            g.drawLine(0, y, width, y);
        }

        // 2. Draw Header Banner
        g.setColor(0x1B, 0x1F, 0x38);
        g.fillRect(0, 0, width, 40);
        g.setColor(0x00, 0xE5, 0xFF); // Cyan accent bar
        g.fillRect(0, 38, width, 2);

        // Header Text
        g.setColor(0xFF, 0xFF, 0xFF);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        g.drawString("RETRO DASH", width / 2, 10, Graphics.TOP | Graphics.HCENTER);

        // 3. Draw Menu Items
        int itemHeight = 32;
        int startY = 60;
        int itemWidth = width - 30;
        int itemX = 15;

        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));

        for (int i = 0; i < menuItems.length; i++) {
            int currentY = startY + (i * (itemHeight + 8));

            if (i == selectedIndex) {
                // Highlighted Card (Glowing Gold)
                g.setColor(0xFF, 0xAB, 0x00);
                g.fillRoundRect(itemX - 2, currentY - 2, itemWidth + 4, itemHeight + 4, 10, 10);
                g.setColor(0x21, 0x21, 0x21);
                g.fillRoundRect(itemX, currentY, itemWidth, itemHeight, 8, 8);
                
                // Text Highlight
                g.setColor(0xFF, 0xD7, 0x00);
                g.drawString("> " + menuItems[i], itemX + 15, currentY + 8, Graphics.TOP | Graphics.LEFT);
            } else {
                // Normal Card
                g.setColor(0x2A, 0x2E, 0x4B);
                g.fillRoundRect(itemX, currentY, itemWidth, itemHeight, 8, 8);
                
                // Normal Text
                g.setColor(0xB0, 0xBE, 0xC5);
                g.drawString(menuItems[i], itemX + 15, currentY + 8, Graphics.TOP | Graphics.LEFT);
            }
        }

        // 4. Footer Status Bar
        g.setColor(0x00, 0xE5, 0xFF);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_SMALL));
        g.drawString("Use D-Pad to Navigate", width / 2, height - 18, Graphics.TOP | Graphics.HCENTER);
    }

    protected void keyPressed(int keyCode) {
        int action = getGameAction(keyCode);

        if (action == UP || keyCode == KEY_NUM2) {
            selectedIndex--;
            if (selectedIndex < 0) {
                selectedIndex = menuItems.length - 1;
            }
            repaint();
        } else if (action == DOWN || keyCode == KEY_NUM8) {
            selectedIndex++;
            if (selectedIndex >= menuItems.length) {
                selectedIndex = 0;
            }
            repaint();
        }
    }
        }
