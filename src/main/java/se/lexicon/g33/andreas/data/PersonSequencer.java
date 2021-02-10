package se.lexicon.g33.andreas.data;

public class PersonSequencer {
    private static int personId = 0;

    public static int nextPersonId() {
        PersonSequencer.personId = ++personId;
        return personId;
    }

    public void reset() {
        PersonSequencer.personId = 0;
    }
}