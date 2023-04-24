package com.company;

import java.util.ArrayList;
import java.util.List;

public class InputObject {

    private List<String> columns;

    public InputObject(List<String> columns) {
        this.columns = columns;
    }

    public List<String> getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return columns.toString();
    }
}
