package com.epam.storozhuk.sql.builder;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLSelectBuilder {
    private static final Logger LOGGER = Logger.getLogger(SQLSelectBuilder.class);

    private String tableName;
    private String orderBy;
    private String limit;
    private boolean isAllSelected;
    private List<String> columns;
    private List<String> wheres;

    public SQLSelectBuilder() {
        clearBuilder();
    }

    public SQLSelectBuilder select(String... param) {
        columns.addAll(Arrays.asList(param));
        return this;
    }

    public SQLSelectBuilder selectAll() {
        isAllSelected = true;
        return this;
    }

    public SQLSelectBuilder from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SQLSelectBuilder where(String param) {
        wheres.add(param + "=?");
        return this;
    }

    public SQLSelectBuilder whereLike(String param) {
        wheres.add(param + "like ?");
        return this;
    }

    public SQLSelectBuilder whereIn(String param, String... values) {
        StringBuilder clause = new StringBuilder();
        clause.append(param + " in (");
        for (String value : values) {
            clause.append(String.format("'%s', ", value));
        }
        clause.delete(clause.length() - 2, clause.length());
        clause.append(")");
        wheres.add(clause.toString());
        return this;
    }

    public SQLSelectBuilder count(String param) {
        if (param != null) {
            columns.add(String.format(" COUNT (%s) ", param));
        } else {
            columns.add(" COUNT(*) ");
        }
        return this;
    }

    public SQLSelectBuilder selectDistinct(String column, String alias) {
        if (alias != null) {
            columns.add(String.format("DISTINCT(%s) AS %s", column, alias));
        } else {
            columns.add(String.format("DISTINCT(%s)", column));
        }
        return this;
    }

    public SQLSelectBuilder selectMax(String column, String alias) {
        if (alias != null) {
            columns.add(String.format("MAX(%s) AS %s", column, alias));
        } else {
            columns.add(String.format("MAX(%s)", column));
        }
        return this;
    }

    public SQLSelectBuilder selectMin(String column, String alias) {
        if (alias != null) {
            columns.add(String.format("MIN(%s) AS %s", column, alias));
        } else {
            columns.add(String.format("MIN(%s)", column));
        }
        return this;
    }

    public SQLSelectBuilder whereBetween(String param, String... values) {
        if (values == null) {
            wheres.add(param + " between ? and ?");
        } else {
            wheres.add(String.format("%s between %s and %s", param, values[0], values[1]));
        }
        return this;
    }

    public SQLSelectBuilder orderBy(String name) {
        orderBy = " ORDER BY " + name;
        return this;
    }

    public SQLSelectBuilder limit(String... limits) {
        if (limits.length == 2 && limits[1] != "") {
            int offset = Integer.parseInt(limits[1]);
            if (offset >= 1) {
                offset *= Integer.parseInt(limits[0]);
                limit = String.format(" LIMIT %s OFFSET %s ", limits[0], offset);
                return this;
            }
        }
        limit = " LIMIT " + limits[0];
        return this;
    }

    @Override
    public String toString() {
        StringBuilder query = new StringBuilder();
        query.append("select ");
        if (columns.size() == 0 || isAllSelected) {
            query.append("* ");
        } else {
            for (String column : columns) {
                query.append(column + ", ");
            }
            query.delete(query.length() - 2, query.length());
        }
        query.append(" from " + tableName);
        if (wheres.size() != 0) {
            query.append(" where ");
            for (String param : wheres) {
                query.append(param + " and ");
            }
            query.delete(query.length() - 4, query.length());
        }
        query.append(orderBy);
        query.append(limit);
        LOGGER.debug("Returning builded query: " + query);
        clearBuilder();
        return query.toString();
    }

    private void clearBuilder() {
        isAllSelected = false;
        limit = "";
        tableName = "";
        orderBy = "";
        columns = new ArrayList<>();
        wheres = new ArrayList<>();
    }
}
