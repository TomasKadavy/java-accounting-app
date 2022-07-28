package cz.muni.fi.pv168.seminar3.team3.model;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import java.util.Objects;

/**
 * Represent Client in an application
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public class Client implements Validable {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String crn;

    public static final Client EMPTY_CLIENT;
    public static final String CRN_FORMAT = "[0-9]+";
    public static final String PHONE_FORMAT = "[0-9]+";
    public static final String EMAIL_FORMAT = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]{2,3}";

    private static final I18N I18N = new I18N(Client.class);

    static {
        EMPTY_CLIENT = new Client("", "", "", "");
        EMPTY_CLIENT.setId(-1L);
    }

    /**
     * Creates a new client object
     *
     * @param name String of client
     * @param phoneNumber String of client
     * @param email String of client
     * @param crn String of client
     */
    public Client(String name, String phoneNumber, String email, String crn) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.crn = crn;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    @Override
    public void validate() {
        if (!crn.matches(CRN_FORMAT)) {
            throw new InvalidFormatException(I18N.getString("invalidCRN"));
        }
        if (name.equals("")) {
            throw new InvalidFormatException(I18N.getString("invalidName"));
        }
        if (!email.matches(EMAIL_FORMAT)) {
            throw new InvalidFormatException(I18N.getString("invalidEmail"));
        }
        if (!phoneNumber.matches(PHONE_FORMAT)) {
            throw new InvalidFormatException(I18N.getString("invalidPhone"));
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(client.getId(), this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
