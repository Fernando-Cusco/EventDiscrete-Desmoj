package ec.edu.ups.simulacion.models;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Hospital extends Model {

    public ProcessQueue colaPacientes;

    public ContDistExponential llegadas;
    public ContDistExponential estancias;

    public int numeroTests;
    public int numeroCamas;
    public int camas;
    public int numeroEnfermeras;
    public int numeroDoctores;
    public int numeroRespiradores;

    public double mediaLlegadas;
    public double mediaEstancia;

    public List<Integer> totalPersonas;
    public List<Integer> contagiados;
    public List<Integer> sanos;
    public List<Integer> noAtendidos;
    public List<Integer> fallecidos;

    public Hospital(Model model, double mediaLlegadas, double mediaEstancia, int numeroTests, int numeroCamas,
                    int numeroEnfermeras, int numeroDoctores, int numeroRespiradores, boolean reporte, boolean traza) {
        super(model, "Hospital", reporte, traza);
        this.mediaLlegadas = mediaLlegadas;
        this.mediaEstancia = mediaEstancia;
        this.numeroCamas = numeroCamas;
        this.numeroTests = numeroTests;

        this.numeroEnfermeras = numeroEnfermeras;
        this.numeroDoctores = numeroDoctores;
        this.numeroRespiradores = numeroRespiradores;

        this.contagiados = new ArrayList<>();
        this.sanos = new ArrayList<>();
        this.totalPersonas = new ArrayList<>();
        this.noAtendidos = new ArrayList<>();
        this.fallecidos = new ArrayList<>();
    }

    @Override
    public void init() {
        colaPacientes = new ProcessQueue(this, "Sala de espera", true, true);
        llegadas = new ContDistExponential(this, "Llegada de nuevos pacientes", mediaLlegadas, true, true);
        estancias = new ContDistExponential(this, "Pacientes en estancia", mediaEstancia, true, true);
        camas = numeroCamas;

    }

    @Override
    public String description() {
        return "Seguro Social Guayas";
    }

    @Override
    public void doInitialSchedules() {
        Pacientes pacientes = new Pacientes(this, true);
        pacientes.schedule(new TimeSpan(0.0));
    }

    public boolean disponibilidadTest() {

        return (this.numeroTests > 0);
    }

    public boolean disponibilidadCamas() {
        return (this.numeroCamas > 0);
    }

    public boolean disponibilidadEnfermeras() {
        return (this.numeroEnfermeras > 0);
    }

    public boolean disponibilidadDoctores() {
        return (this.numeroDoctores > 0);
    }

    public boolean disponibilidadRespiradores() {
        return (this.numeroRespiradores > 0);
    }

    public void asignarCama() {
        this.camas -= 1;
    }

    public void liberarCama() {
        this.camas += 1;
    }

    public boolean realizarTest() {

        this.totalPersonas.add(1);
        Random rnd = new Random();
        boolean estado = false;
        if (disponibilidadTest()) {
            int probabilidad = ((int)(rnd.nextDouble() * 10 + 0));
            if (probabilidad >= 6 ) {
                msj("La persona presenta Covid-19");
                sendTraceNote("La persona presenta Covid-19");
                this.contagiados.add(1);
                estado = true;
            } else {
                msj("La persona NO presenta Covid-19");
                sendTraceNote("La persona NO presenta Covid-19");
                this.sanos.add(1);
                estado = false;
            }
            this.numeroTests -= 1;
        } else if(this.numeroTests < 1){
            sendTraceNote("No hay disponibilidad de Pruebas de Covid-19");
            msj("No hay disponibilidad de Pruebas de Covid-19");
            int probabilidad = ((int)(rnd.nextDouble() * 10 + 0));
            if (probabilidad >= 7) {
                sendTraceNote("Paciente Fallece por falta de tests");
                msj("aciente Fallece por falta de tests");
                this.fallecidos.add(1);
                this.noAtendidos.add(1);
            } else {
                sendTraceNote("Paciente regresa a su casa por falta de tests");
                msj("Paciente regresa a su casa por falta de tests");
                this.noAtendidos.add(1);
            }
        }

        return estado;
    }


    public void msj(String msj) {
        System.out.println(msj);
    }


}
