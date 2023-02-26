package me.skinnynoonie.overlay.settings;

import me.skinnynoonie.storage.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Settings extends JFrame {

    private final int width = 500;
    private final int height = 500;

    public Settings() {
        setUndecorated(true);
        setLayout(null);

        SettingsContainer settingsContainer = new SettingsContainer(width, height);
        settingsContainer.add(new SettingsMenuBar(this), BorderLayout.NORTH);
        settingsContainer.add(new SettingsFunctionContainer(), BorderLayout.CENTER);
        add(settingsContainer);

        setLocationRelativeTo(null);
        setPreferredSize(new Dimension(width, height));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                Config.save();
                dispose();
            }
        });

        pack();
    }



}
