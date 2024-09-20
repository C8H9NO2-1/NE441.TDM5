package fr.esisar.exercice4;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SommeTCP extends Thread {
    private Integer value;
    private Integer port;

    public SommeTCP(int port) {
        this.port = port;
        value = 0;
    }

    @Override
    public void run() {
        InetSocketAddress adrDest;
        OutputStream os;
        InputStream is;
        byte[] bufE;
        byte[] bufR = new byte[2048];
        int lenBufR;
        String reponse;
        adrDest = new InetSocketAddress("127.0.0.1", port);
        reponse = new String();
        try {
            Socket socket = new Socket();
            socket.connect(adrDest);
            bufE = new String("COMBIEN").getBytes();
            os = socket.getOutputStream();
            os.write(bufE);

            is = socket.getInputStream();
            lenBufR = is.read(bufR);

            while (lenBufR != -1) {
                reponse += new String(bufR, 0, lenBufR);
                lenBufR = is.read(bufR);
            }

            int i = reponse.indexOf('=');
            int j = reponse.indexOf('E');

            value = Integer.parseInt(reponse.substring(i + 1, j));

            socket.close();
        } catch (Exception e) {
            System.out.println("Problème lors de la connexion au port: " + port);
            System.out.println("Erreur dans un thread: " + e);
            return;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("Début de la recherche...");

        int numberOfThread = 2001;

        List<SommeTCP> threads = new ArrayList<>();

        int finalMax = 0;
        int finalPort = 0;
        int finalSum = 0;

        for (int i = 0; i < numberOfThread; i++) {
            int port = 21000 + i;
            threads.add(new SommeTCP(port));
            threads.get(i).start();
        } 

        for (SommeTCP thread: threads) {
            thread.join();
        }


        for (SommeTCP thread: threads) {
            if (thread.value > finalMax) {
                finalMax = thread.value;
                finalPort = thread.port;
            }

            finalSum += thread.value;
        }

        System.out.println("Le montant maximum est " + finalMax + " euros");
        System.out.println("Le port d'écoute correspondant à ce maximum est " + finalPort);
        System.out.println("La somme des montants retournés par tous les ports est " + finalSum);
        long stop = System.currentTimeMillis();
        System.out.println("Elapsed Time = "+(stop-start)+" ms");
        
    }
}
