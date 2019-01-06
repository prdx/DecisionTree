package com.cs6140.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class App
{
    public static void main( String[] args )
    {
        ArrayList<double[]> housingTrainingDataset = DataReader.loadCSV("dataset1/housing_train.txt", ',');
        double[][] trainingMatrix = DataReader.convertArrayListMatrixToArrayMatrix(housingTrainingDataset);

        RegressionTree regressionTree = new RegressionTree(3, 10);
        Node root = regressionTree.fit(trainingMatrix);

        double[][] trainingX = (new Data(trainingMatrix)).getX();
        double[] trainingY = (new Data(trainingMatrix)).getY();

        double[] predictedY = regressionTree.predict(trainingX);

        List<Double> trainingYList = Arrays.stream(trainingY).boxed().collect(Collectors.toList());
        Iterator<Double> iterator = trainingYList.iterator();

        double squaredError = 0;

        for (double y:predictedY) {
            squaredError += Math.pow((y - iterator.next().doubleValue()), 2);
        }

        System.out.print(squaredError / trainingY.length);


    }
}
