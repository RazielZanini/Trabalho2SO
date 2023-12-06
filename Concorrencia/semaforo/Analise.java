package Concorrencia.semaforo;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Analise implements Runnable {
    private String Nome;
    private int Vip;
    private int Prioridade;
    private Semaphore mutex = new Semaphore(1);
    private Random rand = new Random();

    public Analise(String nome, int escolha) {
        this.Nome = nome;
        this.Vip = escolha;
    }

    public int getPrioridade() {
        return Prioridade;
    }

    @Override
    public void run() {
        try{
            mutex.acquire();
        if(Vip == 1)
            Prioridade = rand.nextInt(1,2);
        else
        Prioridade = rand.nextInt(3,5);
        }catch(InterruptedException e){
            e.printStackTrace();
        } finally{
            mutex.release();
            System.out.println("Olá "+Nome+" sua análise de crédito foi finalizada!");
        }
    }
}
