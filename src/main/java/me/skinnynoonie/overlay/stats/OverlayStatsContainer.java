package me.skinnynoonie.overlay.stats;

import me.skinnynoonie.storage.Colors;

import javax.swing.*;
import java.awt.*;

public class OverlayStatsContainer extends JPanel {

    OverlayStatsTable table = new OverlayStatsTable(
            new String[]{"LVL", "Username", "WS", "FKDR", "WLR", "Wins", "Losses"}
    );

    public OverlayStatsContainer() {
        setLayout(new BorderLayout());
        setBackground(Colors.STATS_CONTAINER_BACKGROUND);
        add(table.getTableHeader(), BorderLayout.NORTH);
        add(table);
    }

    public OverlayStatsTable getStatTable() { return table; }

}
