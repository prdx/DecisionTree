package com.cs6140.homework;

import java.util.stream.DoubleStream;

public class Impurity {
    public static double calculateMeanSquaredError (double[] Y)
    {
        double average;
        double squaredSumError = 0;
        double meanSquaredError;
        double sum;
        int n = Y.length;

        sum = DoubleStream.of(Y).sum();
        average = sum / n;

        for (double y:Y)
        {
            double delta = average - y;
            squaredSumError += Math.pow(delta, 2);
        }

        meanSquaredError = squaredSumError / n;

        return meanSquaredError;
    }
}
