package sample.models;

/**
 * Employee Entity
 */
public class Employee{
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String passport;
    private String adress;
    private Position position;

    /**
     * Empty initializer
     */
    public Employee(){}

    /**
     * return the id value
     * @return Long id
     */
    public Long getId() {
        return id;
    }

    /**
     * return the firstName value
     * @return String firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * return the lastName value
     * @return String lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * return the phone value
     * @return String phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * return the email value
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * return the passport value
     * @return String passport
     */
    public String getPassport() {
        return passport;
    }

    /**
     * return the adress value
     * @return String adress
     */
    public String getAdress() {
        return adress;
    }

    /**
     * Sets the value for id field
     * @param id Long value for id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the value for id field
     * @param firstName String value for id
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the value for lastName field
     * @param lastName String value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the value for phone field
     * @param phone String value for phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets the value for email field
     * @param email String value for email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the value for passport field
     * @param passport String value for passport
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    /**
     * Sets the value for adress field
     * @param adress String value for adress
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }

    /**
     * return the id value
     * @return Long id
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the value for position field
     * @param position Position value for position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Converts information to String object
     * @return String representation of the Employee object
     */
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", passport='" + passport + '\'' +
                ", adress='" + adress + '\'' +
                ", position=" + position +
                '}';
    }
}
