package data;


import gnu.trove.list.array.TIntArrayList;


/**

 */
public class DataList  implements DataSeries {
    private TIntArrayList data;
    private Scaling scaling;

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
