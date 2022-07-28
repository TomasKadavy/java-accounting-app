package cz.muni.fi.pv168.seminar3.team3.model;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import java.io.PrintWriter;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Map;


/**
 * Class for creating an invoice
 *
 * @author Vojtěch Sýkora
 * @since milestone-2
 *
 */
public class Invoice {

    private final SortedMap<Client, List<Project>> clientsMap;
    private double totalSum = 0.0;
    private double totalTime = 0.0;

    private static final int HASH_PADDING = 100;
    private static final I18N I18N = new I18N(Invoice.class);

    public Invoice(List<Project> projects) {
        if (projects.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.clientsMap = new TreeMap<>(new ClientComparator());

        projects.forEach(project -> {
            Client client = project.getClient();
            List<Project> clientProjects = clientsMap.getOrDefault(client, new LinkedList<>());
            clientProjects.add(project);
            clientsMap.put(client, clientProjects);

            this.totalSum += project.getHourRate() * project.getHoursWorked();
            this.totalTime += project.getHoursWorked();
        });
    }

    private double getTotalSumForClient(Client client) {
        List<Project> projects = clientsMap.get(client);
        double sum = 0;
        for (Project project: projects) {
            sum += project.getHourRate() * project.getHoursWorked();

        }
        return sum;
    }

    private double getTotalTimeForClient(Client client) {
        List<Project> projects = clientsMap.get(client);
        double sum = 0;
        for (Project project: projects) {
            sum += project.getHoursWorked();

        }
        return sum;
    }

    /**
     * Writes invoice with writer to a file
     *
     * @param writer writer
     */
    public void writeInvoice(PrintWriter writer) {
        writer.println(formatTitleString(I18N.getString("title"), true));
        writer.println();

        for (Map.Entry<Client, List<Project>> clientEntry: clientsMap.entrySet()) {
            Client client = clientEntry.getKey();
            writeClientInfo(writer, client);

            for (Project project: clientEntry.getValue()) {
               writeProjectInvoice(writer, project);
            }
            writer.println();
            writer.println(formatTitleString(I18N.getString("totalForTitle") + client.getName(), false));
            writer.println(I18N.getString("totalPrice") + ": " + getTotalSumForClient(client));
            writer.println(I18N.getString("totalTime") + ": " + getTotalTimeForClient(client));
            writer.println();
        }
        writer.println();
        writeTotals(writer);

    }

    /**
     * Writes project details into invoice
     *
     * @param writer writer
     * @param project project
     */
    private void writeProjectInvoice(PrintWriter writer, Project project) {
        writer.println(I18N.getString("projectName") + ": " + project.getName());
        writer.println(I18N.getString("type") + ": " + project.getWorkType());
        writer.println(I18N.getString("start") + ": " + project.getStartTime());
        writer.println(I18N.getString("worked") + ": " + project.getHoursWorked());
        writer.println(I18N.getString("rate") + ": " + project.getHourRate());
        writer.println(I18N.getString("description") + ": " + project.getDescription());
        writer.println(I18N.getString("price") + ": " + project.getHourRate() * project.getHoursWorked() + " czk");
        writer.println();
    }

    /**
     * Writes client details into invoice
     *
     * @param writer writer
     * @param client project
     */
    private void writeClientInfo(PrintWriter writer, Client client) {
        writer.println(formatTitleString(client.getName(), true));
        writer.println(I18N.getString("clientName") + ": " + client.getName());
        writer.println(I18N.getString("phone") + ": " + client.getPhoneNumber());
        writer.println(I18N.getString("mail") + ": " + client.getEmail());
        writer.println(I18N.getString("crn") + ": " + client.getCrn());
        writer.println();

        writer.println(formatTitleString(I18N.getString("clientsProjects"), false));
        writer.println();
    }

    /**
     * Writes summary information of invoice
     *
     * @param writer
     */
    private void writeTotals(PrintWriter writer) {
        writer.println(formatTitleString(I18N.getString("totalTitle"), true));
        writer.println();
        writer.println(I18N.getString("totalPrice") + totalSum);
        writer.println(I18N.getString("totalTime") + totalTime);
        writer.println();
    }

    /**
     * Pads string title with # symbols
     *
     * @param title
     * @param important
     * @return
     */
    private String formatTitleString(String title,  boolean important) {
        StringBuilder builder = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");

        if (important) {
            builder.append("#".repeat(HASH_PADDING)).append(lineSeparator);
        }
        int paddingDiff = HASH_PADDING -  (title.length() + 2);
        if (paddingDiff % 2 == 0) {
            builder.append("#".repeat(paddingDiff / 2))
                    .append(" ").append(title.toUpperCase()).append(" ")
                    .append("#".repeat(paddingDiff / 2))
                    .append(lineSeparator);
        }

        else {
            builder.append("#".repeat(paddingDiff / 2))
                    .append(" ").append(title.toUpperCase()).append(" ")
                    .append("#".repeat(paddingDiff / 2 + 1))
                    .append(lineSeparator);
        }
        if (important) {
            builder.append("#".repeat(HASH_PADDING)).append(lineSeparator);
        }

        return builder.toString();
    }
}
