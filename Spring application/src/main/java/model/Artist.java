package model;

public class Artist {

    private final long id;
    private final String firstName;
    private final String lastName;
    private final Integer age;

    public Artist(long id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getFistName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public Integer getAge() {
        return age;
    }
}
