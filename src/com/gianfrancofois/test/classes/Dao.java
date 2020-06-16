package com.gianfrancofois.test.classes;

import com.gianfrancofois.annotation.InjectConnection;
import com.gianfrancofois.annotation.Transactional;

import java.sql.Connection;

public class Dao {

    @InjectConnection
    protected Connection connection;

    @Transactional
    public void transactionalMethod() {

    }

    public Connection connection() {
        return connection;
    }
}
