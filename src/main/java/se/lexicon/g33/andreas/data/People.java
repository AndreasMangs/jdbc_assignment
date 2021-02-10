package se.lexicon.g33.andreas.data;

import se.lexicon.g33.andreas.model.Person;

import java.util.Arrays;

public class People {
    private static Person[] peopleArray = new Person[0]; // Array with all Person objects
    private static Person[] peopleArrayEmpty = new Person[0]; //For clearing peopleArray

    public static int size() {
        return peopleArray.length;
    } // To know how big the arrays with persons are

    public static Person[] findAll() {
        return peopleArray; //Access all Persons
    }

    public static Person findById(int personId) {
        int answer = 0;
        for (int i = 0; i < peopleArray.length; i++) {
            if (peopleArray[i].getPersonId() == (personId)) {
                answer = i; // Saves the wanted Persons place in the peopleArray so we return the right one
                break;
            }
        }
        if (answer == 0) System.out.println("No person with that ID in database");
        return peopleArray[answer]; //Returns a specific wanted Person
    }

    public static Person add(String firstName, String lastName) {
        Person newPerson = new Person(); //Create a Person
        newPerson.setFirstName(firstName);
        newPerson.setLastName(lastName);
        Person[] newArray = Arrays.copyOf(peopleArray, peopleArray.length + 1); //Make bigger array
        newArray[newArray.length - 1] = newPerson; //Put the new Person in the bigger array
        People.peopleArray = newArray; //Makes the Person array same as the bigger array
        return newPerson; //Returns new Person
    }

    public static void clear() {
        if (peopleArray == null) {
            //System.out.println("Name list is not initialized, now initialized.");
        } else {
            //System.out.println("Name list had been emptied!");
        }
        PersonSequencer myPersonSequencer = new PersonSequencer();
        myPersonSequencer.reset(); // personId reset to value 0
        peopleArray = peopleArrayEmpty; //Empty Person array
    }

    public static boolean remove(int personId) {
        boolean isDeleted = false;
        if (peopleArray.length == 0) {
        } else if (personExist(personId)) {
            Person[] newArray = new Person[peopleArray.length - 1];
            for (int i = 0, j = 0; i < peopleArray.length; i++, j++) {
                if (peopleArray[i].getPersonId() == (personId)) {
                    --j; // Person is removed
                    isDeleted = true; //Remove action finished
                } else {
                    newArray[j] = peopleArray[i];// To save those persons that should not be removed
                }
            }
            peopleArray = newArray;// Not removed persons sent to persons array
        } else {
            //System.out.println("Could not find name to remove");
        }
        return isDeleted; //Tells if person was removed
    }

    public static boolean personExist(int personId) {
        boolean IsExisting = false;
        for (int i = 0; i < peopleArray.length; i++) {
            if (peopleArray[i].getPersonId() == (personId)) {
                IsExisting = true; //Wanted person exist
                break;
            }
        }
        return IsExisting; // Tells if wanted person exist
    }
}
