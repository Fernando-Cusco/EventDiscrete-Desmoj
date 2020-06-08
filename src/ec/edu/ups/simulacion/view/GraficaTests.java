package ec.edu.ups.simulacion.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraficaTests extends JFrame {

    private List<Integer> tests;
    public GraficaTests(String titulo, List<Integer> tests) {
        super(titulo);
        this.tests = tests;
        DefaultCategoryDataset dataset = crearDataset();
        JFreeChart chart = ChartFactory.createLineChart("Variacion de test", "Dias", "", dataset);
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(500, 720));
        setContentPane(panel);
    }

    private DefaultCategoryDataset crearDataset() {

        DefaultCategoryDataset data = new DefaultCategoryDataset();
        int dia = 1;
        for(Integer test: this.tests) {
            data.addValue(test, "Cantidad", ""+dia);
            dia += 1;
        }
        return data;
    }
}
