package com.galvanize.jalbersh.springplayground.model;

import java.util.Objects;

public class OperationData {
    private String operation;
    private String x;
    private String y;

    public OperationData() {}

    public OperationData(String operation, String x, String y) {
        this.operation = operation;
        this.x = x;
        this.y = y;
    }

    public OperationData(OperationDataBuilder builder) {
        this.operation = builder.build().getOperation();
        this.x = builder.build().getX();
        this.y = builder.build().getY();
    }

    public String getOperation() {
        return operation;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    @Override
    public String toString() {
        return "OperationData{" +
                "operation='" + operation + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationData that = (OperationData) o;
        return Objects.equals(operation, that.operation) &&
                Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, x, y);
    }
}
