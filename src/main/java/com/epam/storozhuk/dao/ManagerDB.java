package com.epam.storozhuk.dao;

import com.epam.storozhuk.operation.Operation;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ManagerDB {
    private static final Logger LOGGER = Logger.getLogger(ManagerDB.class);

    private DataSource dataSource;

    public ManagerDB(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T doTransactionQuery(Operation<T> operation) {
        T value = null;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                value = operation.doSomeOperation(connection);
                connection.commit();
            } catch (SQLException ex) {
                LOGGER.debug("Transaction hasn't done successfully because of exception: " + ex.getMessage());
                connection.rollback();
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.debug("Transaction hasn't done successfully because of exception: " + e.getMessage());
        }
        return value;
    }

    public <T> T doNonTransactionQuery(Operation<T> operation) {
        T value = null;
        try (Connection con = dataSource.getConnection()) {
            value = operation.doSomeOperation(con);
        } catch (SQLException e) {
            LOGGER.debug("Exception occured while processing DBManager's operation: " + e.getMessage());
        }
        return value;
    }

}
