package Concorrencia.semaforo;

import java.util.concurrent.Semaphore;

public class Conta {
    double saldo;
    private final Semaphore mutex = new Semaphore(1);

    public Conta(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public void depositar(double valor, String usuario) {
        try {
            mutex.acquire();
            System.out.println(usuario + " está realizando um depósito de " + valor);
            System.out.println("AVISO: Entrando na área crítica!!!!");
            //Simulação da região crítica
            Thread.sleep(1000);
            saldo += valor;
            System.out.println("Saldo atual após depósito: " + saldo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            mutex.release();
            System.out.println("Recurso liberado!");
        }
    }

    public void sacar(double valor, String usuario) {
        try {
            mutex.acquire();
            System.out.println(usuario + " está realizando um saque de " + valor);
            System.out.println("AVISO: Entrando na área crítica!!!!");
            //simuçaão da região crítica
            Thread.sleep(1000);
            if (valor <= saldo) {
                saldo -= valor;
                System.out.println("Saldo atual após saque " + saldo);
            } else {
                System.out.println("Saldo insuficiente para saque!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            System.out.println("Recurso liberado!");
        }
    }
}
