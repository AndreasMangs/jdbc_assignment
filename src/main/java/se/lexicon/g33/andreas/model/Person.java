package se.lexicon.g33.andreas.model;

import se.lexicon.g33.andreas.data.PersonSequencer;

public class Person {

    PersonSequencer myPersonSequencerObject = new PersonSequencer();

    //Fields
    //private final int personId;
    private int personId;
    private String firstName;
    private String lastName;

    //Constructor
    public Person(){
        this.personId = myPersonSequencerObject.nextPersonId();
    }

    public Person(int personId, String firstName, String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //Getters and Setters
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public int getPersonId() {
        return this.personId;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
