package me.skinnynoonie.overlay.stats;

import me.skinnynoonie.overlay.settings.Settings;
import me.skinnynoonie.overlay.utils.SyncMovableJPanel;
import me.skinnynoonie.storage.Colors;
import me.skinnynoonie.storage.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class OverlayMenuBar extends SyncMovableJPanel {

    private int exitButtonMinX;
    private int exitButtonMaxY;

    public OverlayMenuBar(JFrame parentFrame) {
        super(parentFrame);
        setPreferredSize(new Dimension(0, 40));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                if(event.getX() < exitButtonMinX) return;
                if(event.getY() > exitButtonMaxY) return;
                Config.save();
                System.exit(1);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                Rectangle settingsButtonBounds = new Rectangle(12, 5, 30, 30);
                if(event.getX() < settingsButtonBounds.x || event.getX() > settingsButtonBounds.x + settingsButtonBounds.width) {
                    return;
                }
                if (event.getY() < settingsButtonBounds.y || event.getY() > settingsButtonBounds.y + settingsButtonBounds.height) {
                    return;
                }
                new Settings().setVisible(true);
            }
        });
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Colors.MENUBAR_BACKGROUND);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        graphics2D.setColor(Colors.MENUBAR_TEXT);
        graphics2D.setFont(new Font("", Font.PLAIN, 50));
        FontMetrics metrics = graphics.getFontMetrics();
        graphics2D.drawString("⚙", 6, metrics.getHeight() - 27);

        graphics2D.setFont(new Font("", Font.PLAIN, 30));
        metrics = graphics.getFontMetrics();
        graphics2D.drawString("Noonie Overlay", 60, metrics.getHeight() - 10);

        Rectangle2D exitButtonBounds = metrics.getStringBounds("X", graphics2D);
        exitButtonMinX = getWidth() - (int) exitButtonBounds.getWidth() - 7;
        exitButtonMaxY = (int) exitButtonBounds.getHeight() - 9;

        graphics2D.drawString("X", exitButtonMinX, exitButtonMaxY);

        clearBlindSpots();
        //Hardcoded because the exit button won't change in size and for some reason font metrics just don't work???
        addBlindSpot(new Rectangle(exitButtonMinX, 0, 30, 30));
        addBlindSpot(new Rectangle(12, 5, 30, 30));
    }

}
