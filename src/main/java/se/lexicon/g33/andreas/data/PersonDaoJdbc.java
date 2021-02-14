package se.lexicon.g33.andreas.data;

import se.lexicon.g33.andreas.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PersonDaoJdbc {

    private static final PersonDaoJdbc INSTANCE;

    static {
        INSTANCE = new PersonDaoJdbc();
    }

    private PersonDaoJdbc(){}

    public static PersonDaoJdbc getInstance(){
        return INSTANCE;
    }

    //READ
    public Optional<Person> findById(int person_id){
        Person person = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement("SELECT * FROM person WHERE person_id = ?");
            statement.setInt(1, person_id);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                person = resultSetToPerson(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }

        return Optional.ofNullable(person);
    }

    public Collection<Person> findAll(){
        Collection<Person> result = new ArrayList<>();
        try(
                Connection connection = DbConnection.getConnection();
                Statement statement =  connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM person")
        ) {
            while (resultSet.next()){
                result.add(resultSetToPerson(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private void closeAll(AutoCloseable...closeables){
        if(closeables != null){
            try{
                for(AutoCloseable closeable : closeables){
                    if(closeable != null){
                        closeable.close();
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private Person resultSetToPerson(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getInt("person_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
    }


    //CREATE
    public Person create(Person person){
        if(person == null) throw new NullPointerException("person = null");
        if(person.getPersonId() != 0) throw new IllegalArgumentException("Person already exist in DB");
        Person persisted = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keySet = null;
        try{
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO person (first_name, last_name) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getFirstName().trim());
            statement.setString(2, person.getLastName().trim());
            statement.execute();

            keySet = statement.getGeneratedKeys();
            while(keySet.next()){
                persisted = new Person(
                        keySet.getInt(1),
                        person.getFirstName(),
                        person.getLastName()
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeAll(keySet, statement, connection);
        }
        if(persisted == null){
            return person;
        }else {
            return persisted;
        }
    }



    //UPDATE
    public Person update(Person person){
        if(person == null) throw new NullPointerException("Person = null");
        if(person.getPersonId() == 0) throw new IllegalArgumentException("Person does not exist in DB");
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement("UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?");
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getPersonId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(statement, connection);
        }
        return person;
    }

    //REMOVE
    public boolean delete(int person_id){
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement("DELETE FROM person WHERE person_id = ?");
            statement.setInt(1, person_id);
            int records = statement.executeUpdate();
            deleted = records > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(statement, connection);
        }
        return deleted;
    }

}
