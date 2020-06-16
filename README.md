# JDBC_Transactional

## Overview

JDBC_Transactional is a java library that manages database transactions and connections automatically.


## How to use
Create a file called ```connection.config.yml``` anywhere inside your project, and specify the database url, user and password.
Example:

```yml
url: jdbc:mysql://localhost:3306/my_database
username: my_username
password: my_password
```

When a method 
annotated with ```@Transactional``` is called, it will open a connection and inject the Connection object in the field annotated
with ```@InjectConnection```
When the method execution ends, it will commit all the changes and close the connection. If any errors ocurr, it will rollback the changes and throw 
the exception


```java
import com.gianfrancofois.annotation.InjectConnection;
import com.gianfrancofois.annotation.Transactional;
...

public class PersonsDao {

    @InjectConnection
    private Connection connection;

    @Transactional
    public void insertPersons(List<Person> list) throws SQLException {
        String sql = "insert into person (first_name) value (?)";
        for (Person person : list) {
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, person.getName());
                statement.execute();
            }
        }
    }

}
```
