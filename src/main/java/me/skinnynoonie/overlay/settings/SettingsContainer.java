package me.skinnynoonie.overlay.settings;

import javax.swing.*;
import java.awt.*;

public class SettingsContainer extends JPanel {
    public SettingsContainer(int width, int height) {
        setLayout(new BorderLayout());
        setBounds(1, 1, width - 2, height - 2);
    }
}
