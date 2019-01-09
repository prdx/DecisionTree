package com.cs6140.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class App
{
    public static void main(String[] args) {
        runHousingWithRegressionTree();
        runSpamWithDecisionTree();
    }

    private static void runSpamWithDecisionTree() {
        ArrayList<double[]> housingTrainingDataset = DataReader.loadCSV("dataset2/spambase.data", ',');
    }

    private static void runHousingWithRegressionTree() {
        ArrayList<double[]> housingTrainingDataset = DataReader.loadCSV("dataset1/housing_train.txt", ',');
        ArrayList<double[]> housingTestingDataset = DataReader.loadCSV("dataset1/housing_test.txt", ',');

        double[][] trainingMatrix = DataReader.convertArrayListMatrixToArrayMatrix(housingTrainingDataset);
        double[][] testingMatrix = DataReader.convertArrayListMatrixToArrayMatrix(housingTestingDataset);

        RegressionTree regressionTree = new RegressionTree(3, 10);
        Node root = regressionTree.fit(trainingMatrix);

        double[][] trainingX = (new Data(trainingMatrix)).getX();
        double[] trainingY = (new Data(trainingMatrix)).getY();

        double[][] testingX = (new Data(testingMatrix)).getX();
        double[] testingY = (new Data(testingMatrix)).getY();

        double[] predictedY = regressionTree.predict(testingX);

        List<Double> testingYList = Arrays.stream(testingY).boxed().collect(Collectors.toList());
        Iterator<Double> iterator = testingYList.iterator();

        double squaredError = 0;

        for (double y:predictedY) {
            squaredError += Math.pow((y - iterator.next()), 2);
        }

        System.out.print(squaredError / testingY.length);
    }
}
