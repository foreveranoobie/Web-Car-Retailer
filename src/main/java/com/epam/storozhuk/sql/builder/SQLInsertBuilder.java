package com.epam.storozhuk.sql.builder;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLInsertBuilder {
    private static final Logger LOGGER = Logger.getLogger(SQLInsertBuilder.class);
    private String tableName;
    private List<String> columns;
    private int rowsCount;

    public SQLInsertBuilder() {
        clearBuilder();
    }

    public SQLInsertBuilder insertInto(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SQLInsertBuilder columns(String... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

    public SQLInsertBuilder setRowsCount(int count) {
        rowsCount = count;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("insert into " + tableName);
        result.append("(");
        for (String column : columns) {
            result.append(column + ", ");
        }
        result.delete(result.length() - 2, result.length());
        result.append(") VALUES ");
        for (int row = 0; row < rowsCount; row++) {
            result.append("(");
            for (int rowColumn = 0; rowColumn < columns.size(); rowColumn++) {
                result.append("?, ");
            }
            result.delete(result.length() - 2, result.length());
            result.append("), ");
        }
        result.delete(result.length() - 2, result.length());
        clearBuilder();
        return result.toString();
    }

    private void clearBuilder() {
        tableName = "";
        columns = new ArrayList<>();
        rowsCount = 0;
    }
}
