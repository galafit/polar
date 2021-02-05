package polar;

import data.DataList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TxtReader {
    private DataList[] data;

    public TxtReader(File file) {
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(file));
            System.out.println("Reading the file: " + file);
            String contentLine = br.readLine();

            // initiate data array
            String[] stringValues = contentLine.split(",");
            data = new DataList[stringValues.length];
            for (int i = 0; i < stringValues.length; i++) {
                data[i] = new DataList();
            }
            // read file and put data to the DataLists
            while (contentLine != null) {
                stringValues = contentLine.split(",");
                for (int i = 0; i < stringValues.length; i++) {
                    int value = Integer.parseInt(stringValues[i]);
                    data[i].add(value);
                }
                contentLine = br.readLine();
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {
            try {
                if (br != null)
                    br.close();
            }
            catch (IOException ioe)
            {
                System.out.println("Error in closing the BufferedReader");
            }
        }
    }

    public int getNumberOfDataColumns() {
        return data.length;
    }

    public DataList getData(int columnNumber) {
        return data[columnNumber];
    }
}
