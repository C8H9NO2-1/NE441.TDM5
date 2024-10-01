package fr.esisar.exercice5;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SommeTCPUnique {
    private void execute() throws Exception {
        List<Socket> sockets = new ArrayList<>();

        int portMax = 0;
        int max = 0;
        int somme = 0;

        for (int port = 21000; port <= 23000; port++) {
            int i = port - 21000;
            sockets.add(new Socket());
            InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", port);
            
            sockets.get(i).connect(adrDest);
            byte[] bufE = new String("COMBIEN").getBytes();

            OutputStream os = sockets.get(i).getOutputStream();
            os.write(bufE);
        }

        for (int port = 21000; port <= 23000; port++) {
            int i = port - 21000;

            InputStream is = sockets.get(i).getInputStream();

            byte[] bufR = new byte[2048];
            int lenBufR = is.read(bufR);
            String reponse = new String();

            while (lenBufR != -1) {
                reponse += new String(bufR, 0, lenBufR);
                lenBufR = is.read(bufR);
            }

            int j = reponse.indexOf('=');
            int k = reponse.indexOf('E');

            int value = Integer.parseInt(reponse.substring(j + 1, k));

            if (value > max) {
                max = value;
                portMax = port;
            }

            somme += value;

            sockets.get(i).close();

        }

        System.out.println("Le montant maximum est " + max + " euros");
        System.out.println("Le port d'écoute correspondant à ce maximum est " + portMax);
        System.out.println("La somme des montants retournés par tous les ports est " + somme);
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        SommeTCPUnique somme = new SommeTCPUnique();
        somme.execute();

        long stop = System.currentTimeMillis();
        System.out.println("Elapsed Time = "+(stop-start)+" ms");
        
    }
}
