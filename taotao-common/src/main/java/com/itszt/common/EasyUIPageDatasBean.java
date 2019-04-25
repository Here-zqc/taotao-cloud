package com.itszt.common;

import java.io.Serializable;
import java.util.List;

public class EasyUIPageDatasBean<T> implements Serializable {

    private long total;
    private List<T> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}