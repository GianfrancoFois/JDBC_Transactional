package com.gianfrancofois.test;

import com.gianfrancofois.exception.InjectConnectionNotFoundException;
import com.gianfrancofois.test.classes.ChildDao;
import com.gianfrancofois.test.classes.Dao;
import com.gianfrancofois.test.classes.DaoWithNoInjection;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConnectionTest {

    @Test
    public void testInjectConnection() throws SQLException {
        Dao dao = new Dao();
        dao.transactionalMethod();
        assertNotNull(dao.connection());
        assertTrue(dao.connection().isClosed());
    }

    @Test
    public void testInjectConnectionInSuperclass() throws SQLException {
        ChildDao childDao = new ChildDao();
        childDao.transactionalChildMethod();
        assertNotNull(childDao.connection());
        assertTrue(childDao.connection().isClosed());
    }

    @Test(expected = InjectConnectionNotFoundException.class)
    public void testClassWithNoInjection() {
        DaoWithNoInjection dao = new DaoWithNoInjection();
        dao.transactionalMethod();
    }


}
