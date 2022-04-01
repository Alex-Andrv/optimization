package ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import methods.*;

import java.util.Map;
import java.util.function.Function;

public class FXMLController {

    @FXML
    private Label statisticsLabel;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private LineChart<Double, Double> chart;

    @FXML
    private ChoiceBox<String> selectedTestBox;

    @FXML
    private ChoiceBox<String> selectedMethodBox;

    private static final Map<String, TestData> testMapping = Map.of(
            "Test 7", new TestData(x -> Math.pow(Math.log10(x - 2), 2) + Math.pow(Math.log10(10 - x), 2) - Math.pow(x, 0.2), new Segment(6, 9.9)),
            "Test 8", new TestData(x -> -3 * x * Math.sin(0.75 * x) + Math.exp(-2 * x), new Segment(0, Math.PI * 2)),
            "Test 9", new TestData(x -> Math.exp(3 * x) + 5 * Math.exp(-2 * x), new Segment(0, 1)),
            "Test 10", new TestData(x -> 0.2 * x * Math.log10(x) + Math.pow(x - 2.3, 2), new Segment(0.5, 2.5)));

    public void initialize() {
        visualizeSelectedMethod();
    }

    private void visualizeSelectedMethod() {
        chart.getData().clear();

        TestData testData = testMapping.get(selectedTestBox.getValue());
        xAxis.setLowerBound(testData.getSegment().getLeft());
        xAxis.setUpperBound(testData.getSegment().getRight());
        OptimizationMethod optimizationMethod = getOptimizationMethod(testData);
        MethodVisualizer methodVisualizer = new MethodVisualizer(testData.getF(), testData.getSegment(), optimizationMethod);
        chart.getData().addAll(methodVisualizer.generateSeries());
        statisticsLabel.setText(methodVisualizer.getStatistics());
    }

    @FXML
    public void buttonClicked(Event e) {
        visualizeSelectedMethod();
    }

    private OptimizationMethod getOptimizationMethod(TestData testData) {
        String s = selectedMethodBox.getValue();
        Function<Double, Double> f = testData.getF();
        double left = testData.getSegment().getLeft();
        double right = testData.getSegment().getRight();
        if ("Dichotomy method".equals(s)) {
            return new DichotomyMethod("Метод дихотомии", 1e-6, left, right, f, 1e-6);
        } else if ("Golden ratio method".equals(s)) {
            return new GoldenRatioMethod("Метод золотого сечения", 1e-6, left, right, f);
        } else if ("Fibonacci method".equals(s)) {
            return new FibonacciMethod("Метод фибоначи", 1e-6, left, right, f);
        } else if ("Successive parabolic method".equals(s)) {
            return new SuccessiveParabolicMethod("Метод парабол", 1e-3, left, right, f);
        } else {
            return new BrentMethod("Метод Брента", 1e-6, left, right, f);
        }
    }
}