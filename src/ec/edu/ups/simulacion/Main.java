package ec.edu.ups.simulacion;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;
import ec.edu.ups.simulacion.models.Hospital;

public class Main {

    public static void main(String[] args) {
	// write your code here
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
    }
}
