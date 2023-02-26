package com.epam.storozhuk.sql;

import com.epam.storozhuk.constant.Const;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class RequestWrapper {
    private Map<String, BiConsumer<String, String[]>> mapOfRequests;
    private Map<String, String[]> checkboxes;
    private Map<String, String[]> ranges;
    private Map<String, String[]> orders;
    private Map<String, String[]> pagination;

    public RequestWrapper() {
        initMaps();
        initRequestsMap();
    }

    public void putParam(String name, String[] values) {
        if (name.contains("_")) {
            String[] names = name.split("_");
            mapOfRequests.get(names[0]).accept(names[1], values);
        }
    }

    public Map<String, String[]> getCheckboxes() {
        return checkboxes;
    }

    public Map<String, String[]> getOrders() {
        return orders;
    }

    public Map<String, String[]> getRanges() {
        return ranges;
    }

    public Map<String, String[]> getPagination() {
        return pagination;
    }

    private void initMaps() {
        checkboxes = new HashMap<>();
        ranges = new HashMap<>();
        orders = new HashMap<>();
        pagination = new HashMap<>();
    }

    private void initRequestsMap() {
        mapOfRequests = new HashMap<>();
        mapOfRequests.put(Const.WRAPPER_CHECKBOX, (name, params) -> {
            checkboxes.put(name, params);
        });
        mapOfRequests.put(Const.WRAPPER_SORT, (name, params) -> {
            orders.put(name, params);
        });
        mapOfRequests.put(Const.WRAPPER_RANGE_MAX, (name, params) -> {
            if (!ranges.containsKey(name)) {
                ranges.put(name, new String[]{"0", params[0]});
            } else {
                ranges.get(name)[1] = params[0];
            }
        });
        mapOfRequests.put(Const.WRAPPER_RANGE_MIN, (name, params) -> {
            if (!ranges.containsKey(name)) {
                ranges.put(name, new String[]{params[0], "999999"});
            } else {
                ranges.get(name)[0] = params[0];
            }
        });
        mapOfRequests.put(Const.WRAPPER_LIMIT, (name, params) -> {
            if (!pagination.containsKey(Const.WRAPPER_PAGE)) {
                pagination.put(Const.WRAPPER_PAGE, new String[]{params[0], "0"});
            } else {
                pagination.get(Const.WRAPPER_PAGE)[0] = params[0];
            }
        });
        mapOfRequests.put(Const.WRAPPER_PAGE, (name, params) -> {
            if (!pagination.containsKey(Const.WRAPPER_PAGE)) {
                pagination.put(Const.WRAPPER_PAGE, new String[]{Const.PAGINATION_LIMIT_DEFAULT, params[0]});
            } else {
                pagination.get(Const.WRAPPER_PAGE)[1] = params[0];
            }
        });
    }
}
