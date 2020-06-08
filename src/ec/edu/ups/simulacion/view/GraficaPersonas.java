package ec.edu.ups.simulacion.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraficaPersonas extends JFrame {

    private List<Integer> total;
    private List<Integer> fallecidos;
    private List<Integer> sanos;
    private List<Integer> noAtendidos;
    private List<Integer> contagiados;

    public GraficaPersonas(String titulo, List<Integer> total, List<Integer> fallecidos, List<Integer> sanos, List<Integer> noAtendidos, List<Integer> contagiados) {
        super(titulo);
        this.total = total;
        this.fallecidos = fallecidos;
        this.sanos = sanos;
        this.noAtendidos = noAtendidos;
        this.contagiados = contagiados;

        DefaultCategoryDataset dataset = crearDataset();
        JFreeChart chart = ChartFactory.createLineChart("Analisis Totales", "Dias", "", dataset);
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(500, 720));
        setContentPane(panel);
    }

    private DefaultCategoryDataset crearDataset() {

        DefaultCategoryDataset data = new DefaultCategoryDataset();

        int dia = 1;
        for (int i = 0; i < this.total.size(); i ++) {

            data.addValue(i, "Total Personas", ""+dia);
            dia += 1;
        }
        for (int i = 0; i < this.fallecidos.size(); i ++) {
            data.addValue(i, "Total Fallecidos", ""+(i+dia));
            dia += 1;
        }

        for (int i = 0; i < this.sanos.size(); i ++) {
            data.addValue(i, "Total Sanos", ""+(i+dia));
            dia += 1;
        }

        for (int i = 0; i < this.noAtendidos.size(); i ++) {
            data.addValue(i, "Total no atendidos", ""+(i+dia));
            dia += 1;
        }

        for (int i = 0; i < this.contagiados.size(); i ++) {
            data.addValue(i, "Total Contagiados", ""+(i+dia));
            dia += 1;
        }
        return data;
    }
}
