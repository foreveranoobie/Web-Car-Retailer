package com.epam.storozhuk.operation;

import java.sql.Connection;
import java.sql.SQLException;

public interface Operation<T> {
    T doSomeOperation(Connection connection) throws SQLException;
}
