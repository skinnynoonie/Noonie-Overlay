package me.skinnynoonie.overlay.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

/**
 * By "Sync" it means that the moving syncs with the main frame of this component.
 * Moving this component moves everything else.
 */
public class SyncMovableJPanel extends JPanel {

    private final JFrame parentFrame;
    private Point originalClickPoint;
    private final ArrayList<Rectangle> blindSpots = new ArrayList<>();

    public SyncMovableJPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                for(Rectangle blindSpot : blindSpots) {
                    if(event.getX() > blindSpot.x && event.getX() < blindSpot.x + blindSpot.width) {
                        if (event.getY() > blindSpot.y && event.getY() < blindSpot.y + blindSpot.height) {
                            originalClickPoint = null;
                            return;
                        }
                    }
                }
                originalClickPoint = event.getLocationOnScreen();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                if(originalClickPoint == null) return;
                int deltaX = event.getXOnScreen() - originalClickPoint.x;
                int deltaY = event.getYOnScreen() - originalClickPoint.y;
                originalClickPoint = new Point(event.getXOnScreen(), event.getYOnScreen());
                parentFrame.setLocation(parentFrame.getLocationOnScreen().x + deltaX, parentFrame.getLocationOnScreen().y + deltaY);
            }
        });
    }

    public JFrame getParentFrame() {
        return parentFrame;
    }

    public ArrayList<Rectangle> getBlindSpots() {
        return blindSpots;
    }

    public void addBlindSpot(Rectangle blindSpot) {
        blindSpots.add(blindSpot);
    }

    public void removeBlindSpot(Rectangle blindSpot) {
        blindSpots.remove(blindSpot);
    }

    public void clearBlindSpots() {
        blindSpots.clear();
    }

}
