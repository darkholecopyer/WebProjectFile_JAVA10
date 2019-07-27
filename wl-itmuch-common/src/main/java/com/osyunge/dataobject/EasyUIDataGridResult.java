package com.osyunge.dataobject;

import java.util.List;

public class EasyUIDataGridResult {
    private List<?> rows;//每页显示的数量
    private long total;

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
