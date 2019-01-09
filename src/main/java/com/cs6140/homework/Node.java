package com.cs6140.homework;

import java.util.ArrayList;
import java.util.stream.DoubleStream;

public class Node {
    private Node parent = null;
    private ArrayList<Node> children = new ArrayList<>();
    private double average;
    private double[][] X;
    private double[] Y;
    private double splitDecision;
    private int depth = 0;
    private int indexOfFeature = 0;
    private int indexOfRow = 0;
    private double splitValue;

    public Node (double[][] X, double[] Y)
    {
        this.X = X;
        this.Y = Y;
        this.average = DoubleStream.of(Y).sum() / Y.length;
        this.splitDecision = Impurity.calculateMeanSquaredError(Y);
        this.depth = this.parent == null ? 0 : this.parent.getDepth() + 1;
    }


    public Node (double[][] X, double[] Y, Node parent)
    {
        this.X = X;
        this.Y = Y;
        this.parent = parent;
        this.average = DoubleStream.of(Y).sum() / Y.length;
        this.splitDecision = Impurity.calculateMeanSquaredError(Y);
        this.depth = this.parent == null ? 0 : this.parent.getDepth() + 1;
    }

    public double getSplitDecision() {
        return splitDecision;
    }

    public double getAverage() {
        return average;
    }

    public double getSplitValue() {
        return splitValue;
    }

    public void setSplitValue(double splitValue)
    {
        this.splitValue = splitValue;
    }

    public int getDepth() {
        return depth;
    }

    public double[][] getX()
    {
        return X;
    }

    public double[] getY()
    {
        return Y;
    }

    public void setIndexOfFeature(int indexOfFeature) {
        this.indexOfFeature = indexOfFeature;
    }

    public int getIndexOfFeature() {
        return indexOfFeature;
    }

    public void setIndexOfRow(int indexOfRow)
    {
        this.indexOfRow = indexOfRow;
    }

    public int getIndexOfRow() {
        return indexOfRow;
    }

    public void addChild(Node node)
    {
       children.add(node);
    }

    public ArrayList<Node> getChildren()
    {
        return children;
    }

    public String toString()
    {
        String text = "";
        for (int i = 1; i <= depth; i++) {
            text += "|_____";
        }
        return text + indexOfFeature + " " + indexOfRow + " " + depth + " " + splitDecision;
    }
}
