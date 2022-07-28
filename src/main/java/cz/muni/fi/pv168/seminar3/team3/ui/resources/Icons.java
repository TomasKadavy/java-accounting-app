package cz.muni.fi.pv168.seminar3.team3.ui.resources;

import javax.swing.ImageIcon;
import javax.swing.Icon;

import java.net.URL;

/**
 * Enumeration of Icons used in application
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public enum Icons {
    DELETE_ICON("delete.png"),
    EDIT_ICON ("pencil.png"),
    ADD_ICON("plus.png"),
    DETAIL_ICON("magnify.png"),
    INVOICE_ICON("receipt.png");

    public Icon icon;

    Icons(String iconPath) {
        URL url = Icons.class.getResource(iconPath);
        if (url == null) {
            throw new IllegalArgumentException("Icon resource not found on classpath: " + iconPath);
        }

        this.icon = new ImageIcon(url);
    }
}
