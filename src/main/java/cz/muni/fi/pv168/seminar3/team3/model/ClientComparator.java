package cz.muni.fi.pv168.seminar3.team3.model;

import java.util.Comparator;

/**
 * Comparator for sorting clients by name
 *
 * @author Vojtěch Sýkora
 * @since milestone-2
 *
 */
public class ClientComparator implements Comparator<Client> {
    @Override
    public int compare(Client c1, Client c2) {
        return c1.getName().compareTo(c2.getName());
    }
}
