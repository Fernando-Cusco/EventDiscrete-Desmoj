package ec.edu.ups.simulacion.models;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;
//Proporciona la clase para eventos definidos por el usuario para cambiar el estado del modelo en un punto distinto en el tiempo de simulación.
public class Pacientes extends ExternalEvent {

    private int cantidad;

    public Pacientes(Model model, boolean b) {
        super(model, "´Pacientes", b);
        cantidad = 0;
    }
//    implemente este método para expresar la semántica de este evento externo.
    @Override
    public void eventRoutine() throws SuspendExecution {
        cantidad += 1;
        Hospital model = (Hospital) getModel();
        Paciente paciente = new Paciente(model, "Paciente", cantidad, true);
        paciente.activate(new TimeSpan(0.0));
        schedule(new TimeSpan(model.llegadas.sample()));
    }
}
