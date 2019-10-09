package util;

import java.util.ArrayList;

public class ListUtil {

    public static double getMaxValue(ArrayList<Double> numbers) {

        double maxValue = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) > maxValue) {
                maxValue = numbers.get(i);
            }
        }
        return maxValue;
    }

    public static double getMinValue(ArrayList<Double> numbers) {
        double minValue = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) < minValue) {
                minValue = numbers.get(i);
            }
        }
        return minValue;
    }
}
