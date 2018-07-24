/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analise;

/**
 *
 * @author zezedcarvalho
 */
public class Little {
    private double numeroEventos;
    private double somaAreas;
    private double tempoAnterior;
    
    Little(){
        this.numeroEventos = 0.0;
        this.somaAreas = 0.0;
        this.tempoAnterior = 0.0;
    }
    
    public void adicionarArea(double area, double tempo, String tipo){
        this.somaAreas += area;
        this.tempoAnterior = tempo;
        if (tipo.equals("-"))
            this.numeroEventos--;
        else
            this.numeroEventos++;
    }

    public double getNumeroEventos() {
        return numeroEventos;
    }

    public double getSomaAreas() {
        return somaAreas;
    }

    public double getTempoAnterior() {
        return tempoAnterior;
    }
    
    public void setAreaRestante(double restante){
        this.somaAreas = restante;
    }

    public void setAtributos(double somaAreas, double numeroEventos, double tempoAnterior) {
        this.somaAreas = somaAreas;
        this.numeroEventos = numeroEventos;
        this.tempoAnterior = tempoAnterior;
    }
    
}
