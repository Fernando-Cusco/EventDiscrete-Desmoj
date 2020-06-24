package ec.edu.ups.simulacion.models;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

import java.util.Random;
// representa entidades con un ciclo de vida activo propio
public class Paciente extends SimProcess {

    private Hospital modelo;
    private int id;

    public Paciente(Model model, String s, int id, boolean b) {
        super(model, s, b);
        this.id = id;
        modelo = (Hospital) model;
    }
    ///Pasiva un SimProcess durante el período de tiempo muestreado de la distribución proporcionada al método
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
                //passivate();
                delay(10);
                if (validarEstado()) {
                    modelo.sanos.add(1);
                    sendTraceNote("El paciente: "+this.id+ " es dado de alta");
                } else {
                    sendTraceNote("El paciente: "+this.id+ " entra en etapa de neumonia");
                    if (modelo.disponibilidadRespiradores()) {
                        modelo.asignarRespirador();

                        sendTraceNote("Al paciente: "+this.id+ " se le asigna un respirador");

                        //passivate();
                        delay(10);
                        modelo.liberarRespirador();
                        if (validarEstadoFinal()) {
                            sendTraceNote("Al paciente: "+this.id+ " se cura y se va");
                            modelo.sanos.add(1);
                        } else {
                            sendTraceNote("Al paciente: "+this.id+ " no se cura y muere");
                            modelo.fallecidos.add(1);
                        }
                    } else {
                        sendTraceNote("El paciente: "+this.id+ " muere por falta de respiradores");
                        modelo.fallecidos.add(1);
                    }
                    modelo.liberarRespirador();
                }

            } else {
                modelo.colaPacientes.insert(this);
                passivate();
            }
        }
    }

    private void delay(long tiempo){
        try {
            sendTraceNote("Han pasado n dias y se reevaluara al paciente "+this.id);
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean validarEstado() {
        Random rnd = new Random();
        int probabilidad = ((int)(rnd.nextDouble() * 10 + 0));
        if (probabilidad >= 4) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validarEstadoFinal() {
        Random rnd = new Random();
        int probabilidad = ((int)(rnd.nextDouble() * 10 + 0));
        if (probabilidad >= 6) {
            return true;
        } else {
            return false;
        }
    }
}
