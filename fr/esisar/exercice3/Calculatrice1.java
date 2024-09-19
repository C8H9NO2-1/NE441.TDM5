package fr.esisar.exercice3;

public class Calculatrice1 extends Thread {
    private Somme somme;

    public Calculatrice1(Somme somme) {
        this.somme = somme;
    }

    @Override
    public void run() {
        int res = 0;
        for (int i = 0; i < 1000; i++) {
            res= somme.somme(res, i);
        }
        System.out.println("La somme 1+2+3+4+...+998+999 est égale à :"+res);
    }


    public static void main(String[] args) throws InterruptedException {
        Somme somme1 = new Somme();
        Somme somme2 = new Somme();
        Calculatrice1 c1 = new Calculatrice1(somme1);
        Calculatrice1 c2 = new Calculatrice1(somme2);

        c1.start();
        c2.start();
    }


    static  class Somme {

        int c;

        public int somme(int a, int b) {
            c=a+b;
            System.out.println("c="+c);
            return c;
        }

    }

}
