package sample.models;

/**
 * Position Entity
 */
public class Position{
    private Long id;
    private String name;
    private int salary;
    private int numberOfDaysOff;
    private int numberOfWorkingDays;

    /**
     * Empty initializer
     */
    public Position(){}

    /**
     * return the id value
     * @return Long id
     */
    public Long getId() {
        return id;
    }

    /**
     * return the name value
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * return the salary value
     * @return Integer salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * return the numberOfDaysOff value
     * @return Integer numberOfDaysOff
     */
    public int getNumberOfDaysOff() {
        return numberOfDaysOff;
    }

    /**
     * return the numberOfWorkingDays value
     * @return Integer numberOfWorkingDays
     */
    public int getNumberOfWorkingDays() {
        return numberOfWorkingDays;
    }

    /**
     * Sets the value for id field
     * @param id Long value for id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the value for name field
     * @param name String value for name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the value for salary field
     * @param salary Integer value for salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * Sets the value for numberOfDaysOff field
     * @param numberOfDaysOff Integer value for numberOfDaysOff
     */
    public void setNumberOfDaysOff(int numberOfDaysOff) {
        this.numberOfDaysOff = numberOfDaysOff;
    }

    /**
     * Sets the value for numberOfWorkingDays field
     * @param numberOfWorkingDays Integer value for numberOfWorkingDays
     */
    public void setNumberOfWorkingDays(int numberOfWorkingDays) {
        this.numberOfWorkingDays = numberOfWorkingDays;
    }

    /**
     * Converts information to String object
     * @return String representation of the Position object
     */
    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", numberOfDaysOff=" + numberOfDaysOff +
                ", numberOfWorkingDays=" + numberOfWorkingDays +
                '}';
    }
}
