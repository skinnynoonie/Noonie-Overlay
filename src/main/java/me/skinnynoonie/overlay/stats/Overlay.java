package me.skinnynoonie.overlay.stats;

import me.skinnynoonie.overlay.utils.ComponentResizer;
import me.skinnynoonie.storage.Colors;
import me.skinnynoonie.storage.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Overlay extends JFrame {

    private final int width = 1000;
    private final int height = 600;

    public OverlayStatsContainer statsContainer = new OverlayStatsContainer();

    public Overlay() {
        setPreferredSize(new Dimension(width, height));
        setUndecorated(true);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Colors.BORDER);
        new ComponentResizer(this);

        JPanel panel = new OverlayContainer(this, width, height);
        panel.add(new OverlayMenuBar(this), BorderLayout.NORTH);
        panel.add(statsContainer, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Config.save();
                System.exit(1);
            }
        });

        add(panel);
        pack();
    }

    public OverlayStatsContainer getStatsContainer() {
        return statsContainer;
    }

}
