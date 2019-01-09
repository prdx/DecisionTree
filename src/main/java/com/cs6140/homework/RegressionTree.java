package com.cs6140.homework;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class RegressionTree
{
    private static int maxDepth;
    private static int minSamplesPerNode;
    private double[][] X;
    private double[] Y;
    private double[][] data;
    private Node root;

    public RegressionTree(int maxDepth,  int minSamplesPerNode)
    {
        RegressionTree.maxDepth = maxDepth;
        RegressionTree.minSamplesPerNode = minSamplesPerNode;
    }

    private static void makeChild(Node parent, ArrayDeque<Node> nodeQueue, double[][] _data)
    {
        Data data = new Data(_data);
        double[][] X = data.getX();
        double[] Y = data.getY();

        Node child = new Node(X, Y, parent);
        parent.addChild(child);

        if ((data.getLengthOfY() > minSamplesPerNode) && (child.getSplitDecision() > 0)) {
            nodeQueue.add(child);
        }

    }

    public double[] predict(double[][] X)
    {
        double[] Y = new double[X.length];

        if(root != null)
        {
            int i = 0;
            for (double[] row:X) {
                Node current = this.root;

                while(true)
                {
                    int indexOfFeature = current.getIndexOfFeature();
                    double splitValue = current.getSplitValue();

                    if(current.getChildren().isEmpty())
                    {
                      Y[i] = current.getAverage();
                      break;
                    }

                    ArrayList<Node> children = current.getChildren();

                    if(row[indexOfFeature] <= splitValue)
                    {
                        // Left
                        current = children.get(0);
                    }
                    else
                    {
                        // Right
                        current = children.get(1);
                    }
                }
                i++;
            }
        }
        return Y;
    }

    private static int[] getBestSplit(double[][] data)
    {
        int[] indexOfFeatureAndRow = new int[2];

        int numberOfFeatures = data[0].length - 1;
        int numberOfRows = data.length;
        int jump = 5;


        double bestMeanSquaredError = Double.POSITIVE_INFINITY;


        for (int i = 0; i < numberOfFeatures; i++)
        {
            int indexOfColumn = i;
            Arrays.sort(data, new Comparator<double[]>() {
                @Override
                public int compare(double[] o1, double[] o2) {
                    return Double.compare(o1[indexOfColumn], o2[indexOfColumn]);
                }
            });

            for (int j = jump; j < numberOfRows; j += jump)
            {
                if(numberOfRows - jump >= j)
                {
                    double[][] left = new double[j][];
                    double[][] right = new double[numberOfRows - j][];

                    for (int k = 0; k < left.length; k++)
                    {
                        left[k] = data[k];
                    }

                    for (int k = 0; k < right.length; k++)
                    {
                        right[k] = data[j + k];
                    }

                    Data leftData = new Data(left);
                    Data rightData = new Data(right);

                    double leftDataMeanSquaredError = Impurity.calculateMeanSquaredError(leftData.getY());
                    double rightDataMeanSquaredError = Impurity.calculateMeanSquaredError(rightData.getY());
                    double newMeanSquaredError = leftDataMeanSquaredError + rightDataMeanSquaredError;

                    if(newMeanSquaredError < bestMeanSquaredError)
                    {
                        bestMeanSquaredError = newMeanSquaredError;
                        indexOfFeatureAndRow[0] = i;
                        indexOfFeatureAndRow[1] = j;
                    }
                }

            }

        }

        return indexOfFeatureAndRow;
    }

    private static void splitData(double[][] currentData, int[] bestSplit,
                                  ArrayList<double[]> leftData, ArrayList<double[]> rightData) {
        for (int i = 0; i < currentData.length; i++) {
            if (i < bestSplit[1]) {
                leftData.add(currentData[i]);
            } else {
                rightData.add(currentData[i]);
            }
        }
    }

    public Node fit(double[][] _data) {
        Data data = new Data(_data);
        this.X = data.getX();
        this.Y = data.getY();
        this.data = data.getDataset();

        // Root node
        Node root = new Node(X, Y);
        ArrayDeque<Node> nodeQueue = new ArrayDeque<>();
        nodeQueue.add(root);

        while (!nodeQueue.isEmpty()) {
            Node currentNode = nodeQueue.remove();

            if (currentNode.getDepth() <= maxDepth) {
                double[][] currentX = currentNode.getX();
                double[] currentY = currentNode.getY();
                double[][] nodeData = (new Data(currentX, currentY)).getDataset();

                int[] split = getBestSplit(nodeData);

                currentNode.setIndexOfFeature(split[0]);
                currentNode.setIndexOfRow(split[1]);


                int indexOfFeature = split[0];
                int indexOfRow = split[1];

                Arrays.sort(this.data, new Comparator<double[]>() {
                    @Override
                    public int compare(double[] o1, double[] o2) {
                        return Double.compare(o1[indexOfFeature], o2[indexOfFeature]);
                    }
                });

                Arrays.sort(nodeData, new Comparator<double[]>() {
                    @Override
                    public int compare(double[] o1, double[] o2) {
                        return Double.compare(o1[indexOfFeature], o2[indexOfFeature]);
                    }
                });

                currentNode.setIndexOfFeature(indexOfFeature);
                currentNode.setIndexOfRow(indexOfRow);
                currentNode.setSplitValue(nodeData[indexOfRow][indexOfFeature]);

                ArrayList<double[]> leftData = new ArrayList<double[]>();
                ArrayList<double[]> rightData = new ArrayList<double[]>();

                // Splitting
                splitData(nodeData, split, leftData, rightData);

                double[][] left = DataReader.convertArrayListMatrixToArrayMatrix(leftData);
                double[][] right = DataReader.convertArrayListMatrixToArrayMatrix(rightData);

                makeChild(currentNode, nodeQueue, left);
                makeChild(currentNode, nodeQueue, right);

                System.out.println(currentNode.toString());

            }

        }

        this.root = root;
        return root;

    }



}
