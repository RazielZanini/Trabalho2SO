package Concorrencia.semaforo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainA {
    public static void main(String Args[]) throws Exception {
        Scanner in = new Scanner(System.in);
        int Escolha;
        System.out.println(
                "Bem vindo ao sistema MutualBank. \nUm sistema desenvolvido para demonstrar como funciona a exclusão mútua de uma tarefa: \nVamos começar! \nEscolha a opção que deseja realizar: \n1 - Criar conta; \n2- Analise de Crédito; \n3 - Sair");
        Escolha = in.nextInt();
        List<Thread> filaProcessos = new ArrayList<>();

        if (Escolha == 1) {
            Conta conta1 = new Conta(0);
            System.out.println("Conta criada com sucesso! Seu saldo disponível é de: " + conta1.saldo);
            System.out.println(
                    "Vamos adicionar saldo a sua conta! \nDigite o nome do usuário e quantos depositos gostaria de agendar ");
            System.out.println("Usuário: ");
            String usuario = in.next();
            System.out.println("Quantidade de depósitos quer agendar: ");
            int quantDep = in.nextInt();

            for (int i = 1; i <= quantDep; i++) {
                System.out.println("Digite o valor do " + i + "° deposito: ");
                int valor = in.nextInt();
                Thread deposito = new Thread(() -> {
                    conta1.depositar(valor, usuario);
                });
                filaProcessos.add(deposito);
            }

            System.out.println(
                    "Agora, vamos agendar alguns saques! \nDigite a quantidade de saques que gostaria de agendar: ");
            int quantSaq = in.nextInt();
            for (int i = 1; i <= quantSaq; i++) {
                System.out.println("Digite o valor do " + i + "° saque: ");
                int valor = in.nextInt();
                Thread saque = new Thread(() -> {
                    conta1.sacar(valor, usuario);
                });
                filaProcessos.add(saque);
            }

            for (Thread t : filaProcessos) {
                t.start();

                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (Escolha == 2) {
            System.out.println(
                    "Bem vindo a análise de crédito do MutualBank. \nAqui mostramos como funciona o processo de inanição de um processo. \nDigite os dados solicitados para continuar \nDigite quantas solicitações de analise deseja fazer: ");
            int quantAnalise = in.nextInt();
            List<Analise> listaAnalises = new ArrayList<>();
            for (int i = 1; i <= quantAnalise; i++) {
                System.out.println("Informe o nome do usuário para quem será feita a analise:");
                String nome = in.next();
                System.out.println(
                        "Agora nos diga se você possui benefício do programa VIP \nDigite 1 para sim e 2 para não: ");
                int vip = in.nextInt();

                Analise analise = new Analise(nome, vip);

                listaAnalises.add(analise);

                listaAnalises.sort((p1, p2) -> Integer.compare(p1.getPrioridade(), p2.getPrioridade()));
            }
            for (Analise a : listaAnalises) {
                Thread analiseThread = new Thread(a);
                analiseThread.start();
            }
        }

        else
            System.out.println("Certo! Obrigado por utilizar o sistema MutualBank! :)");

        in.close();
    }
}
