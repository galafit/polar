package data;


import gnu.trove.list.array.TIntArrayList;


/**

 */
public class DataList  implements DataSeries {
    private TIntArrayList data;
    private Scaling scaling;

    public DataList increaseFrequency(int multiplayer) {
        ScalingImpl sc = new ScalingImpl(scaling);
        sc.setTimeSeries(true);
        sc.setSamplingInterval(scaling.getSamplingInterval()/multiplayer);
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

    public DataList reduceFrequency(int divider) {
        ScalingImpl sc = new ScalingImpl(scaling);
        sc.setTimeSeries(true);
        sc.setSamplingInterval(scaling.getSamplingInterval() * divider);
        int resultSize = data.size() / divider;
        DataList resultData = new DataList(resultSize);
        for (int i = 0; i < resultSize; i++) {
            resultData.add(data.get(i * divider));
        }
        resultData.setScaling(sc);
        return resultData;
    }

    public void setFrequency(int frequency) {
        ScalingImpl sc = new ScalingImpl(scaling);
        sc.setTimeSeries(true);
        sc.setSamplingInterval(1.0/frequency);
        scaling = sc;
    }

    public DataList(int size) {
        data = new TIntArrayList(size);
    }

    public DataList() {
        data = new TIntArrayList();
    }
    public DataList(int[] array) {
        data = TIntArrayList.wrap(array);
    }

    public void add(int value) {
        data.add(value);
    }

    public void set(int index, int value) {
        data.set(index, value);
    }

    public void setScaling(Scaling scaling) {
        this.scaling = scaling;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public int get(int index) {
        return data.get(index);
    }

    @Override
    public Scaling getScaling() {
        return scaling;
    }
}
