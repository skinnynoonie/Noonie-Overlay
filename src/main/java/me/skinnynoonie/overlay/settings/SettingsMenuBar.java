package me.skinnynoonie.overlay.settings;

import me.skinnynoonie.overlay.utils.SyncMovableJPanel;
import me.skinnynoonie.storage.Colors;
import me.skinnynoonie.storage.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class SettingsMenuBar extends SyncMovableJPanel {
    private int exitButtonMinX;
    private int exitButtonMaxY;
    public SettingsMenuBar(JFrame parent) {
        super(parent);
        setPreferredSize(new Dimension(0, 40));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                if(event.getX() < exitButtonMinX) return;
                if(event.getY() > exitButtonMaxY) return;
                Config.save();
                parent.dispose();
            }
        });
    }
    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Colors.SETTINGS_MENUBAR_BACKGROUND);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        graphics2D.setColor(Colors.SETTINGS_MENUBAR_TEXT);
        graphics2D.setFont(new Font("", Font.PLAIN, 50));
        FontMetrics metrics = graphics.getFontMetrics();
        graphics2D.drawString("⚙", 6, metrics.getHeight() - 27);

        graphics2D.setColor(Colors.SETTINGS_MENUBAR_TEXT);
        graphics2D.setFont(new Font("", Font.PLAIN, 30));
        metrics = graphics.getFontMetrics();
        graphics2D.drawString("Noonie Overlay Settings", 60, metrics.getHeight() - 10);

        Rectangle2D exitButtonBounds = metrics.getStringBounds("X", graphics2D);
        exitButtonMinX = getWidth() - (int) exitButtonBounds.getWidth() - 7;
        exitButtonMaxY = (int) exitButtonBounds.getHeight() - 9;

        graphics2D.drawString("X", exitButtonMinX, exitButtonMaxY);

        clearBlindSpots();
        //Hardcoded because the exit button won't change in size and for some reason font metrics just don't work???
        addBlindSpot(new Rectangle(exitButtonMinX, 0, 30, 30));
    }
}
