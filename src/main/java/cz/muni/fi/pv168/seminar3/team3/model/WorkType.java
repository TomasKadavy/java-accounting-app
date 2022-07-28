package cz.muni.fi.pv168.seminar3.team3.model;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import java.util.Objects;

/**
 * @author Eduard Stefan Mlynarik
 *
 * Represents work types.
 * @since milestone-1
 */
public class WorkType implements Validable{

    private String type;
    private double defaultRate;
    private Long id;

    private static final I18N I18N = new I18N(WorkType.class);

    public static final WorkType EMPTY_TYPE;

    static {
        EMPTY_TYPE = new WorkType("", 0);
        EMPTY_TYPE.setId(-1L);
    }

    /**
     * Creates new instance of WorkType
     *
     * @param type work type
     */
    public WorkType(String type, double defaultRate) {
        this.type = type;
        this.defaultRate = defaultRate;
    }

    /**
     * Sets work type
     *
     * @param type work type
     */
    public void setType(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }

    /**
     * Gives id of WorkType instance
     * @return id of instance
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDefaultRate() {
        return defaultRate;
    }

    public void setDefaultRate(double defaultRate) {
        this.defaultRate = defaultRate;
    }

    @Override
    public void validate() throws InvalidFormatException {
        if (type.equals("") || type.matches("[0-9]*")) {
            throw new InvalidFormatException(I18N.getString("emptyName"));
        }
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkType workType = (WorkType) o;
        return Objects.equals(id, workType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
