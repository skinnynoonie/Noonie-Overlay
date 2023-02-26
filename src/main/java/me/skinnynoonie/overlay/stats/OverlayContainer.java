package me.skinnynoonie.overlay.stats;

import me.skinnynoonie.storage.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class OverlayContainer extends JPanel {

    public OverlayContainer(JFrame parentFrame, int width, int height) {
        setLayout(new BorderLayout());
        setBounds(1, 1, width - 2, height - 2);
        setBackground(Colors.CONTAINER_BACKGROUND);
        parentFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                setBounds(1, 1, parentFrame.getWidth() - 2, parentFrame.getHeight() - 2);
            }
        });
    }

}
