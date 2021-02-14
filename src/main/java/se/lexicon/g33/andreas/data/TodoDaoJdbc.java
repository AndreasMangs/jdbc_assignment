package se.lexicon.g33.andreas.data;

import se.lexicon.g33.andreas.model.Person;
import se.lexicon.g33.andreas.model.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class TodoDaoJdbc {

        private static final TodoDaoJdbc INSTANCE;

        static {
            INSTANCE = new TodoDaoJdbc();
        }

        private TodoDaoJdbc() {}

        public static TodoDaoJdbc getInstance(){
            return INSTANCE;
        }

        //READ
        public Optional<Todo> findById(int todo_id){
            Todo todo = null;
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try{
                connection = DbConnection.getConnection();
                statement = connection.prepareStatement("SELECT * FROM todo_item WHERE todo_id = ?");
                statement.setInt(1, todo_id);
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    todo = resultSetToTodo(resultSet);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }finally {
                closeAll(resultSet, statement, connection);
            }

            return Optional.ofNullable(todo);
        }

        public Collection<Todo> findAll(){
            Collection<Todo> result = new ArrayList<>();
            try(
                    Connection connection = DbConnection.getConnection();
                    Statement statement =  connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM todo_item")
            ) {
                while (resultSet.next()){
                    result.add(resultSetToTodo(resultSet));
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
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        private Todo resultSetToTodo(ResultSet resultSet) throws SQLException {
            return new Todo(
                    resultSet.getInt("todo_id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("deadline"),
                    resultSet.getBoolean("done"),
                    resultSet.getInt("assignee_id")
            );
        }


        //CREATE
        public Todo create(Todo todo){
            if(todo == null) throw new NullPointerException("Todo item = null");
            if(todo.getTodo_id() != 0) throw new IllegalArgumentException("Todo item already exist in DB");
            Todo persisted = null;
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet keySet = null;
            try{
                connection = DbConnection.getConnection();
                statement = connection.prepareStatement("INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, todo.getTitle().trim());
                statement.setString(2, todo.getDescription().trim());
                statement.setString(3, todo.getDeadline().trim());
                statement.setBoolean(4, todo.getDone());
                statement.setInt(5, todo.getAssignee_id());
                statement.execute();

                keySet = statement.getGeneratedKeys();
                while(keySet.next()){
                    persisted = new Todo(
                            keySet.getInt(1),
                            todo.getTitle(),
                            todo.getDescription(),
                            todo.getDeadline(),
                            todo.getDone(),
                            todo.getAssignee_id()
                    );
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                closeAll(keySet, statement, connection);
            }
            if(persisted == null){
                return todo;
            }else {
                return persisted;
            }
        }



        //UPDATE
        public Todo update(Todo todo){
            if(todo == null) throw new NullPointerException("Todoitem = null");
            if(todo.getTodo_id() == 0) throw new IllegalArgumentException("Todo item does not exist in DB");
            Connection connection = null;
            PreparedStatement statement = null;
            try{
                connection = DbConnection.getConnection();
                statement = connection.prepareStatement("UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?");
                statement.setString(1, todo.getTitle());
                statement.setString(2, todo.getDescription());
                statement.setString(3, todo.getDeadline());
                statement.setBoolean(4, todo.getDone());
                statement.setInt(5, todo.getAssignee_id());
                statement.setInt(6, todo.getTodo_id());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                closeAll(statement, connection);
            }
            return todo;
        }

        //REMOVE
        public boolean delete(int todo_id){
            boolean deleted = false;
            Connection connection = null;
            PreparedStatement statement = null;
            try{
                connection = DbConnection.getConnection();
                statement = connection.prepareStatement("DELETE FROM todo_item WHERE todo_id = ?");
                statement.setInt(1, todo_id);
                int records = statement.executeUpdate();
                deleted = records > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                closeAll(statement, connection);
            }
            return deleted;
        }
}

