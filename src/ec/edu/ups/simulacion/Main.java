package ec.edu.ups.simulacion;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;
import ec.edu.ups.simulacion.models.Hospital;
import ec.edu.ups.simulacion.view.GraficaPacientes;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args) throws IOException {
//	 write your code here
        Hospital hospital = new Hospital(null, 20,30 , 100, 25,
                            20, 30, 12, true, true);
        Experiment experiment = new Experiment("Simulacion Coid-19");
        hospital.connectToExperiment(experiment);

        experiment.setShowProgressBar(true);
        experiment.stop(new TimeInstant(3600));

        experiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(3600));

        experiment.start();

        experiment.report();
        experiment.finish();

        System.out.println(hospital.totalPersonas.size());
        System.out.println(hospital.fallecidos.size());
        System.out.println(hospital.sanos.size());
        System.out.println(hospital.noAtendidos.size());
        System.out.println(hospital.contagiados.size());
        for (Integer i: hospital.tests){
            System.out.println("Cantidad de tests: "+i);
        }

        for (String tipo: hospital.tipo) {
            System.out.println(tipo);
        }

        GraficaPacientes gp = new GraficaPacientes("Total muestras", hospital.tipo);
        gp.pack();
        gp.setVisible(true);

    }
}
