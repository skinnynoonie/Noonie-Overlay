package me.skinnynoonie.overlay.settings;

import me.skinnynoonie.storage.Colors;
import me.skinnynoonie.storage.Config;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class SettingsFunctionContainer extends JPanel {

    public SettingsFunctionContainer() {
        setBackground(Colors.SETTINGS_FUNCTION_BACKGROUND);

        add(new JLabel("<html><font color=white>Enter your API Key(s)</html>"));
        JTextArea textAreaHY = new JTextArea();
        JScrollPane scrollPaneHY = new JScrollPane(textAreaHY, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneHY.setPreferredSize(new Dimension(450, 330));
        textAreaHY.setText(String.join("\n", Config.getHypixelApiKeys()));
        textAreaHY.setForeground(new Color(255, 255, 255, 255));
        textAreaHY.setBackground(Colors.SETTINGS_TEXTAREA_BACKGROUND);
        textAreaHY.setCaretColor(Color.WHITE);
        add(scrollPaneHY);
        textAreaHY.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void changedUpdate(DocumentEvent e) {}
            @Override
            public void insertUpdate(DocumentEvent e) {
                Config.setHypixelApiKeys(List.of(textAreaHY.getText().split("\n")));
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                Config.setHypixelApiKeys(List.of(textAreaHY.getText().split("\n")));
            }
        });

        add(new JLabel("<html><font color=white>Enter your AntiSniper API Key(Leave blank to disable)</html>"));
        JTextField textFieldAS = new JTextField();
        textFieldAS.setText(String.join("\n", Config.getAntiSniperApiKey()));
        textFieldAS.setForeground(new Color(255, 255, 255, 255));
        textFieldAS.setBackground(Colors.SETTINGS_TEXTAREA_BACKGROUND);
        textFieldAS.setCaretColor(Color.WHITE);
        textFieldAS.setPreferredSize(new Dimension(450, 30));
        add(textFieldAS);
        textFieldAS.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void changedUpdate(DocumentEvent e) {}
            @Override
            public void insertUpdate(DocumentEvent e) {
                Config.setAntiSniperApiKey(textFieldAS.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                Config.setAntiSniperApiKey(textFieldAS.getText());
            }
        });
    }
}
