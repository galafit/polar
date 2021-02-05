package polar;

import data.CompressionType;
import data.DataList;
import graph.GraphViewer;

import java.io.File;

public class PolarPresenter {
    private static String recordDirectory = "records";


    public static void createGraphs(GraphViewer  graphViewer) {
        int previewCompression = 80;
        int ecgFrequency = 130;
        int accFrequency = 50;

        graphViewer.setCompression(previewCompression);
        String filename = "Ecg.txt";
        File dataFile = new File(recordDirectory, filename);
        TxtReader cardioReader = new TxtReader(dataFile);

        DataList cardioData = cardioReader.getData(0);

        cardioData.setFrequency(ecgFrequency);

        graphViewer.addGraph(cardioData);

        filename = "Acc.txt";
        dataFile = new File(recordDirectory, filename);
        TxtReader accelerometerReader = new TxtReader(dataFile);

        DataList accData0 = accelerometerReader.getData(0);
        DataList accData1 = accelerometerReader.getData(1);
        DataList accData2 = accelerometerReader.getData(2);

        accData0.setFrequency(accFrequency);
        accData1.setFrequency(accFrequency);
        accData2.setFrequency(accFrequency);

        graphViewer.addGraphPanel(1,true);
        graphViewer.addGraph(accData0);
        graphViewer.addGraph(accData1);
        graphViewer.addGraph(accData2);

        DataList accDataWithCardioFrequency = accData0.reduceFrequency(5).increaseFrequency(13);

        graphViewer.addPreview(cardioData, CompressionType.AVERAGE);
        graphViewer.addPreviewPanel(1, true);
        graphViewer.addPreview(accDataWithCardioFrequency, CompressionType.AVERAGE);

    }
}
