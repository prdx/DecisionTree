package com.cs6140.homework;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataReader
{
    public static ArrayList<double[]> loadCSV (String documentName, char separator)
    {
        CSVReader reader;
        DataReader dataReader = new DataReader();
        ClassLoader classLoader = dataReader.getClass().getClassLoader();

        ArrayList<double[]> loadedData = new ArrayList<>();

        try
        {
            reader = new CSVReader(new FileReader(classLoader.getResource(documentName).getFile()), separator);

            String[] line;
            while ((line = reader.readNext()) != null)
            {
                double[] lineAsDouble = new double[line.length];
                for (int i = 0; i < line.length; i++)
                {
                   lineAsDouble[i] = Double.parseDouble(line[i].trim());
                }
                loadedData.add(lineAsDouble);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return loadedData;
    }

    public static double[][] convertArrayListMatrixToArrayMatrix(ArrayList<double[]> data)
    {
        double[][] newMatrix = new double[data.size()][];
        int i = 0;
        for(double[] row:data)
        {
            newMatrix[i] = row;
            i++;
        }
        return newMatrix;
    }

}
