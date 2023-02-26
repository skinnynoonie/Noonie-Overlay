package me.skinnynoonie.overlay.stats;

import me.skinnynoonie.storage.Colors;
import me.skinnynoonie.storage.OverlayData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OverlayStatsTable extends JTable {

    public OverlayStatsTable(String[] columns) {
        super(new DefaultTableModel(new String[][]{}, columns));
        initiateTable();
        initiateTableHeader();
    }

    public void add(OverlayData data) {
        try {
            String[] overlayData = new String[]{
                    data.getLevel(),
                    data.getUsername(),
                    data.getWinstreak(),
                    data.getFKDR(),
                    data.getWinLossRatio(),
                    data.getWins(),
                    data.getLosses()
            };
            String dataNoHtmlFKDR = data.getFKDR().replaceAll("(<(\\S*?)[^>]>.?<\\1>|<.*?>)", "");
            if (dataNoHtmlFKDR.equals("?")) {
                ((DefaultTableModel) getModel()).insertRow(0, overlayData);
                return;
            }
            double fkdr = Double.parseDouble(dataNoHtmlFKDR);
            for (int x = 0; x < getRowCount(); x++) {
                String value = ((String) getModel().getValueAt(x, 3)).replaceAll("(<(\\S*?)[^>]>.?<\\1>|<.*?>)", "");
                if (value.equals("?")) continue;
                if (Double.parseDouble(value) < fkdr) {
                    ((DefaultTableModel) getModel()).insertRow(x, overlayData);
                    return;
                }
            }
            ((DefaultTableModel) getModel()).addRow(overlayData);
        }catch (Exception ignored) {}
    }

    public void clear() {
        try {
            ((DefaultTableModel) getModel()).setRowCount(0);
        }catch (Exception ignored) {}
    }

    public void remove(String username) {
        try {
            for (int x = 0; x < getRowCount(); x++) {
                String value = (String) getModel().getValueAt(x, 1);
                if (value.replaceAll("(<(\\S*?)[^>]>.?<\\1>|<.*?>)", "").equals(username)) { // This regex matches HTML tags! Not made by me.
                    ((DefaultTableModel) getModel()).removeRow(x);
                    return;
                }
            }
        }catch (Exception ignored){}
    }

    public boolean contains(String username) {
        for(int x = 0; x < getRowCount(); x++) {
            String value = (String) getModel().getValueAt(x, 1);
            if(value.replaceAll("(<(\\S*?)[^>]>.?<\\1>|<.*?>)", "").equals(username)) {
                return true;
            }
        }
        return false;
    }

    private void initiateTable() {
        setFocusable(false);
        setRowSelectionAllowed(false);
        setFillsViewportHeight(true);
        setShowVerticalLines(false);
        setShowHorizontalLines(false);
        setRowHeight(30);
        setBackground(Colors.TABLE_BACKGROUND);
        setFont(new Font("", Font.PLAIN, 20));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setForeground(Colors.TABLE_TEXT);
        for(int x = 0; x < getColumnCount(); x++) getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
    }

    private void initiateTableHeader() {
        getTableHeader().setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        getTableHeader().setOpaque(true);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(true);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, Color.BLACK));
                setHorizontalAlignment(JLabel.CENTER);
                setForeground(Colors.TABLEHEADER_TEXT);
                setBackground(Colors.TABLEHEADER_BACKGROUND);
                return component;
            }
        });
        //XD
        getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(100);
        getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(329);
        getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(99);
        getTableHeader().getColumnModel().getColumn(3).setPreferredWidth(121);
        getTableHeader().getColumnModel().getColumn(4).setPreferredWidth(100);
        getTableHeader().getColumnModel().getColumn(5).setPreferredWidth(125);
        getTableHeader().getColumnModel().getColumn(6).setPreferredWidth(124);
    }



    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }


}
