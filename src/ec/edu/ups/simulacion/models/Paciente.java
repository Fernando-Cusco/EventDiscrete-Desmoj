package ec.edu.ups.simulacion.models;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Paciente extends SimProcess {

    private Hospital modelo;
    private int id;

    public Paciente(Model model, String s, int id, boolean b) {
        super(model, s, b);
        this.id = id;
        modelo = (Hospital) model;
    }

    @Override
    public void lifeCycle() throws SuspendExecution {
        sendTraceNote("El paciente: "+this.id+ "llega al hospital");
        if(modelo.realizarTest()){
            if(modelo.disponibilidadCamas()) {
                modelo.asignarCama();
                sendTraceNote("El paciente: "+this.id+ "se le asigna una cama");
                hold(new TimeSpan(modelo.estancias.sample()));
                modelo.liberarCama();
                if(modelo.colaPacientes.length() > 0) {
                    Paciente paciente = (Paciente) modelo.colaPacientes.first();
                    modelo.colaPacientes.remove(paciente);
                    paciente.activateAfter(this);
                }
                modelo.sanos.add(1);
                sendTraceNote("El paciente: "+this.id+ " es dado de alta");
            } else {
                modelo.colaPacientes.insert(this);
                passivate();
            }
        }
    }
}
