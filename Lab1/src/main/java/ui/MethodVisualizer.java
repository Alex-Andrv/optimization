package ui;

import javafx.scene.chart.XYChart;
import methods.OptimizationMethod;
import methods.Parabola;
import methods.ParabolicMethod;
import methods.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class MethodVisualizer {
    private static final double CALCULATION_STEP = 0.01;
    private static final double VISUALISATION_THRESHOLD = 1e-6;

    private final Function<Double, Double> f;
    private final Segment targetSegment;
    private final OptimizationMethod method;
    private final double minPoint;
    private final List<Segment> intermediateSegments;

    public MethodVisualizer(Function<Double, Double> f, Segment targetSegment, OptimizationMethod method) {
        this.f = f;
        this.targetSegment = targetSegment;
        this.method = method;
        this.minPoint = method.findMin();
        this.intermediateSegments = method.getIntermediateSegments();
    }

    private List<XYChart.Data<Double, Double>> calculateFunctionOnSegment(Function<Double, Double> function, Segment segment) {
        return DoubleStream.iterate(segment.getLeft(), x -> x <= segment.getRight(), x -> x + CALCULATION_STEP)
                .boxed().parallel().map(x -> new XYChart.Data<>(x, function.apply(x))).collect(Collectors.toList());
    }

    private List<XYChart.Series<Double, Double>> generateSeriesForParabolicMethod() {
        List<Parabola> parabolas = ((ParabolicMethod) method).getIntermediateParabolas();
        List<XYChart.Series<Double, Double>> seriesList = parabolas.parallelStream().map(parabola -> {
            XYChart.Series<Double, Double> series = new XYChart.Series<>();
            series.getData().addAll(calculateFunctionOnSegment(x -> parabola.getA() * x * x + parabola.getB() * x + parabola.getC(), targetSegment));
            return series;
        }).collect(Collectors.toList());
        XYChart.Series<Double, Double> mainSeries = new XYChart.Series<>();
        mainSeries.getData().addAll(calculateFunctionOnSegment(f, targetSegment));
        seriesList.add(mainSeries);
        return seriesList;
    }

    private List<XYChart.Series<Double, Double>> generateSeriesForOptimizationMethod() {
        List<XYChart.Series<Double, Double>> seriesList = new ArrayList<>();
        for (int i = 0; i + 1 < intermediateSegments.size(); ++i) {
            XYChart.Series<Double, Double> series = new XYChart.Series<>();
            Segment left = new Segment(intermediateSegments.get(i).getLeft(), intermediateSegments.get(i + 1).getLeft());
            Segment right = new Segment(intermediateSegments.get(i + 1).getRight(), intermediateSegments.get(i).getRight());
            if (left.getLength() >= VISUALISATION_THRESHOLD) {
                series.getData().addAll(calculateFunctionOnSegment(f, left));
            }
            if (right.getLength() >= VISUALISATION_THRESHOLD) {
                series.getData().addAll(calculateFunctionOnSegment(f, right));
            }
            if (!series.getData().isEmpty()) {
                seriesList.add(series);
            }
        }
        return seriesList;
    }

    public List<XYChart.Series<Double, Double>> generateSeries() {
        return method instanceof ParabolicMethod ? generateSeriesForParabolicMethod() : generateSeriesForOptimizationMethod();
    }

    public String getStatistics() {
        return String.format("Segment: [%.4f; %.4f]\nMinimum point: %.4f\nMinimum value: %.4f\nIterations: %d",
                targetSegment.getLeft(), targetSegment.getRight(), minPoint, f.apply(minPoint), intermediateSegments.size());
    }
}
