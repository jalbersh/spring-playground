package com.galvanize.jalbersh.springplayground.service;

import com.galvanize.jalbersh.springplayground.model.OperationData;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("mathService")
public class MathService {
    public String calculate(OperationData data) {
        double x = 0.0;
        double y = 0.0;
        try {
            x = Double.parseDouble(data.getX());
            y = Double.parseDouble(data.getY());
        } catch (NumberFormatException e) {
            System.err.println("parsing problem encountered: "+e.getMessage());
        }
        switch(data.getOperation()) {
            case "add": return Double.toString(x+y)+"\n";
            case "subtract": return Double.toString(x-y)+"\n";
            case "multiply": return Double.toString(x*y)+"\n";
            case "divide": return Double.toString(x/y)+"\n";
            default: return Double.toString(x+y)+"\n";
        }
    }

    public String sum(String[] ns) {
        double sum = 0.0;
        for (String n : ns) {
            sum += Double.parseDouble(n);
        }
        return Double.toString(sum)+"\n";
    }
}
