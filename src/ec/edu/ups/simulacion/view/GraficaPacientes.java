package ec.edu.ups.simulacion.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraficaPacientes extends JFrame {

    public List<String> tipoPersona;

    public GraficaPacientes(String titulo, List<String> tipoPersona){
        super(titulo);
        this.tipoPersona = tipoPersona;
        PieDataset dataset = grafica();
        JFreeChart chart = crearGrafico(dataset, titulo);
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(500, 720));
        setContentPane(panel);
    }

    private PieDataset grafica() {
        DefaultPieDataset df = new DefaultPieDataset();
        int h = 0;
        int m = 0;
        int n = 0;
        for (String tipo: this.tipoPersona) {
            if (tipo.equals("Hombre")) {
                h += 1;
            }
            if (tipo.equals("Mujer")) {
                m += 1;
            }
            if (tipo.equals("Niños")) {
                n += 1;
            }
        }
        df.setValue("Mujeres: "+m, m);
        df.setValue("Hombres: "+h, h);
        df.setValue("Niños: "+n, n);
        return df;
    }
     private JFreeChart crearGrafico(PieDataset dataset, String titulo) {
        JFreeChart chart = ChartFactory.createPieChart3D(
                titulo,
                dataset,
                true,
                true,
                false
        );
         PiePlot3D plot = (PiePlot3D) chart.getPlot();
         plot.setStartAngle(180);
         plot.setDirection(Rotation.ANTICLOCKWISE);
         plot.setForegroundAlpha(0.5f);
         return chart;
     }

}
