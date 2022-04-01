package ui;

import methods.Segment;

import java.util.function.Function;

public class TestData {
    private final Function<Double, Double> f;
    private final Segment segment;

    public TestData(Function<Double, Double> f, Segment segment) {
        this.f = f;
        this.segment = segment;
    }

    public Function<Double, Double> getF() {
        return f;
    }

    public Segment getSegment() {
        return segment;
    }
}
