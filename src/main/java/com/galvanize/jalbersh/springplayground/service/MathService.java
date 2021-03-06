package com.galvanize.jalbersh.springplayground.service;

import com.galvanize.jalbersh.springplayground.model.OperationData;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MathService {
    public String calculate(OperationData data) {
        double x = 0.0;
        double y = 0.0;
        if (data != null) {
            try {
                x = Double.parseDouble(data.getX());
                y = Double.parseDouble(data.getY());
            } catch (NumberFormatException e) {
                System.err.println("parsing problem encountered: " + e.getMessage());
            }
            switch (data.getOperation()) {
                case "add":
                    return Double.toString(x + y) + "\n";
                case "subtract":
                    return Double.toString(x - y) + "\n";
                case "multiply":
                    return Double.toString(x * y) + "\n";
                case "divide":
                    return Double.toString(x / y) + "\n";
                default:
                    return Double.toString(x + y) + "\n";
            }
        } else {
            System.err.println("operation data was null");
            return "0.0";
        }
    }

    public String sum(String[] ns) {
        double sum = 0.0;
        for (String n : ns) {
            sum += Double.parseDouble(n);
        }
        return Double.toString(sum)+"\n";
    }

    public String volume(int width, int length, int height) {
        return Double.toString(width*length*height)+"\n";
    }

    public String area(String type, int width, int height, int radius) {
        System.out.println("in area with type="+type+";width="+width+";height="+height+";radius="+radius);
        if (!type.toLowerCase().equals("circle")) {
            // its a rectangle
            if (radius > 0) {
                return "Invalid";
            }
            return String.valueOf(width*height);
        } else {
            // its a circle
            if (width > 0 || height > 0) return "Invalid";
            return String.valueOf(Math.PI*Math.pow(radius,2.0));
        }
    }
}
