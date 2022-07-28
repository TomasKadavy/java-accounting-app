package cz.muni.fi.pv168.seminar3.team3.model;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represent Project in an application
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public class Project implements Validable{

    private Long id;
    private String name;
    private Client client;
    private WorkType workType;
    private LocalDate startTime;
    private int hoursWorked;
    private double hourRate;
    private String description;

    public static final Project EMPTY_PROJECT;
    private static final I18N I18N = new I18N(Project.class);

    static {
        EMPTY_PROJECT = new Project("", Client.EMPTY_CLIENT, WorkType.EMPTY_TYPE, LocalDate.now(), 0, 0, "" );
        EMPTY_PROJECT.setId(-1L);
    }

    /**
     *
     * @param name String of the project
     * @param client Client of the project
     * @param workType Worktype of the project
     * @param startTime Localdate time of the start of the project
     * @param hoursWorked int hours worked on the project
     * @param hourRate double the number of hour rate
     * @param description String Description of the job
     */
    public Project(String name, Client client, WorkType workType, LocalDate startTime, int hoursWorked, double hourRate, String description) {
        this.name = name;
        this.client = client;
        this.workType = workType;
        this.startTime = startTime;
        this.hoursWorked = hoursWorked;
        this.hourRate = hourRate;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getHourRate() {
        return hourRate;
    }

    public void setHourRate(double hourRate) {
        this.hourRate = hourRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void validate() throws InvalidFormatException {
        if (name.equals("")) {
            throw new InvalidFormatException(I18N.getString("invalidName"));
        }
        if (Objects.isNull(client) || client.equals(Client.EMPTY_CLIENT)) {
            throw new InvalidFormatException(I18N.getString("clientNotSelected"));
        }
        if (Objects.isNull(workType) || workType.equals(WorkType.EMPTY_TYPE)) {
            throw new InvalidFormatException(I18N.getString("workTypeNotSelected"));
        }
        if (hoursWorked <= 0 ) {
            throw new InvalidFormatException(I18N.getString("hoursNegative"));
        }
        if (hourRate <= 0 ) {
            throw new InvalidFormatException(I18N.getString("rateNegative"));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public String toString() {
        return name + ' ' + " (id " + id + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
