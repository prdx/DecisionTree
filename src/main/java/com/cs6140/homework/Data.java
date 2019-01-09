package com.cs6140.homework;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Data
{
    private double[][] dataset;
    private double[][] X;
    private double[] Y;

    public Data(double[][] dataset)
    {
        this.dataset = dataset;
        splitDataIntoXAndY();
    }

    public Data(double[][] X, double[] Y)
    {
        this.X = X;
        this.Y = Y;
        this.dataset = combineXAndY();
    }

    public void setDataset(double[][] dataset) {
        this.dataset = dataset;
        splitDataIntoXAndY();
    }

    public double[][] getDataset()
    {
        return dataset;
    }

    public double[][] getX() {
        return X;
    }

    public double[] getY() {
        return Y;
    }

    public int getLengthOfX()
    {
        return X[0].length;
    }

    public int getLengthOfY()
    {
        return Y.length;
    }

    private void splitDataIntoXAndY()
    {
        double[][] X =  new double[dataset.length][];
        double[] Y = new double[dataset.length];
        int i = 0;

        for(double[] row:dataset)
        {
            X[i] = Arrays.copyOfRange(row, 0, row.length - 1);
            Y[i] = row[row.length - 1];
            i++;
        }

        this.X = X;
        this.Y = Y;
    }

    private double[][] combineXAndY()
    {
        int numberOfColumns = getLengthOfX() + 1;
        int numberOfRows = getLengthOfY();

        double[][] combinedXAndY = new double[numberOfRows][numberOfColumns];
        for(int i = 0; i < numberOfRows; i++)
        {
            List<Double> copyOfRow = Arrays.stream(X[i]).boxed().collect(Collectors.toList());
            copyOfRow.add(Y[i]);

            Iterator<Double> iterator = copyOfRow.iterator();
            for (int j = 0; j < copyOfRow.size(); j++)
            {
                combinedXAndY[i][j] = iterator.next().doubleValue();
            }

        }
        return combinedXAndY;
    }
}
