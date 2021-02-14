package se.lexicon.g33.andreas;

import se.lexicon.g33.andreas.data.DbConnection;
import se.lexicon.g33.andreas.data.PersonDaoJdbc;
import se.lexicon.g33.andreas.model.Person;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println(PersonDaoJdbc.getInstance().findAll());
        System.out.println("--------------------------------------");
        //System.out.println(PersonDaoJdbc.getInstance().findById(1));

        //Person olle = new Person("Olle", "Bengtsson");

        //System.out.println(PersonDaoJdbc.getInstance().create(olle));

        /*
        Person olle = PersonDaoJdbc.getInstance().findById(2).orElseThrow(RuntimeException::new);
        olle.setLastName("Frid");
        PersonDaoJdbc.getInstance().update(olle);
        System.out.println(PersonDaoJdbc.getInstance().findAll());
         */

        /*
        Person snip = PersonDaoJdbc.getInstance().findById(2).orElseThrow(RuntimeException::new);
        PersonDaoJdbc.getInstance().delete(2);
        System.out.println(PersonDaoJdbc.getInstance().findAll());
         */
    }
}
