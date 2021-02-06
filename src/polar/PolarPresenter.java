package polar;

import data.CompressionType;
import data.DataList;
import data.DataSeries;
import data.ScalingImpl;
import dreamrec.ApplicationException;
import functions.Composition;
import functions.Rising;
import graph.GraphType;
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

        graphViewer.addGraphPanel(2, true);
        graphViewer.addGraph(cardioData, GraphType.DOT);

        filename = "Acc.txt";
        dataFile = new File(recordDirectory, filename);
        TxtReader accelerometerReader = new TxtReader(dataFile);

        DataList accData0 = accelerometerReader.getData(0);
        DataList accData1 = accelerometerReader.getData(1);
        DataList accData2 = accelerometerReader.getData(2);

        accData0.setFrequency(accFrequency);
        accData1.setFrequency(accFrequency);
        accData2.setFrequency(accFrequency);
        DataSeries accMovement = accMovementData(accData0, accData1, accData2);

        graphViewer.addGraphPanel(2,false);
        graphViewer.addGraph(accMovement);

        DataList accMovementWithCardioFrequency = increaseFrequency(reduceFrequency(accMovement,5), 13);

        graphViewer.addPreviewPanel(2, true);
        graphViewer.addPreview(cardioData, CompressionType.AVERAGE);
        graphViewer.addPreviewPanel(2, false);
        graphViewer.addPreview(accMovementWithCardioFrequency, CompressionType.AVERAGE);

    }

    /**
     * Определяем величину пропорциональную движению головы
     * (дельта между max и min значением сигналов акселерометра на 3 точках).
     * Суммируем амплитуды движений по трем осям.
     * За ноль принят шумовой уровень.
     */
    public static DataSeries accMovementData(DataList acc1, DataList acc2, DataList acc3) {
        Composition accMovement = new Composition();
        try {
            accMovement.add(new Rising(acc1));
            accMovement.add(new Rising(acc2));
            accMovement.add(new Rising(acc3));
        } catch (ApplicationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return accMovement;
    }

    public static DataList increaseFrequency(DataSeries data, int multiplayer) {
        ScalingImpl sc = new ScalingImpl(data.getScaling());
        sc.setTimeSeries(true);
        sc.setSamplingInterval(data.getScaling().getSamplingInterval()/multiplayer);
        DataList resultData = new DataList(data.size() * multiplayer);
        for (int i = 0; i < data.size(); i++) {
            int value = data.get(i);
            for (int j = 0; j < multiplayer; j++) {
                resultData.add(value);
            }
        }
        resultData.setScaling(sc);
        return resultData;
    }

    public static DataList reduceFrequency(DataSeries data, int divider) {
        ScalingImpl sc = new ScalingImpl(data.getScaling());
        sc.setTimeSeries(true);
        sc.setSamplingInterval(data.getScaling().getSamplingInterval() * divider);
        int resultSize = data.size() / divider;
        DataList resultData = new DataList(resultSize);
        for (int i = 0; i < resultSize; i++) {
            resultData.add(data.get(i * divider));
        }
        resultData.setScaling(sc);
        return resultData;
    }
}
