package com.galvanize.jalbersh.springplayground.model;

public class OperationDataBuilder {

    private String operation = "add";
    private String x;
    private String y;

    public OperationDataBuilder() {}

    public OperationDataBuilder operation(String operation) {
        if (operation != null) {
            if (operation.equals("null")) {
                operation = "add";
            }
        } else {
            operation = "add";
        }
        this.operation = operation;
        return this;
    }

    public OperationDataBuilder x(String x) {
        this.x = x;
        return this;
    }
    public OperationDataBuilder y(String y) {
        this.y = y;
        return this;
    }

    public OperationData build() {
        return new OperationData(operation,x,y);
    }
}
