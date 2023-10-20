import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.print("Veuillez choisir le nombre de reines : ");
        Scanner scanner = new Scanner(System.in);
        int command1 = scanner.nextInt();

        System.out.println();
        System.out.println("Algorithmes de recherche : ");
        System.out.println("1 - BFS");
        System.out.println("2 - DFS");
        System.out.println("3 - A*");
        System.out.println("4 - GA");
        System.out.println("5 - PSO");

        System.out.print("Veuillez choisir l'algorithme : ");


        Scanner scanner2 = new Scanner(System.in);
        int command2 = scanner2.nextInt();

        /*
        // BFS
        if (command2 == 1) {

            long start = System.currentTimeMillis();
            Node result = BFS.run(command1);
            long time = System.currentTimeMillis() - start;
            System.out.println("Temps d'exécution: " + time/1000);

            if(result == null)
                System.out.println("No solution.");

            else {
                result.print();
                System.out.println(" - Generated nodes : " + result.getGenerated());
                System.out.println(" - Visited nodes : " + result.getVisited());
            }
        }

        // DFS
        if (command2 == 2) {

            long start = System.currentTimeMillis();
            Node result = DFS.run(command1);
            long time = System.currentTimeMillis() - start;
            System.out.println("Temps d'exécution: " + time/1000);

            if(result == null)
                System.out.println("No solution.");

            else {
                result.print();
                System.out.println(" - Generated nodes : " + result.getGenerated());
                System.out.println(" - Visited nodes : " + result.getVisited());
            }
        }

        // A*
        if (command2 == 3) {

            System.out.println();
            System.out.println("1 - Heuristique 1 : comptage de conflits");
            System.out.println("2 - Heuristique 2 : comptage de cases libres");
            System.out.print("Veuillez choisir l'heuristique : ");

            command2 = scanner2.nextInt();

            // Heuristic 1
            if (command2 == 1) {

                long start = System.currentTimeMillis();
                Node result = AStar.run(command1, command2);
                long time = System.currentTimeMillis() - start;
                System.out.println("Temps d'exécution: " + time/1000);

                if(result == null)
                    System.out.println("No solution.");

                else {
                    result.print();
                    System.out.println(" - Generated nodes : " + result.getGenerated());
                    System.out.println(" - Visited nodes : " + result.getVisited());
                }
            }

            // Heuristic 2
            if (command2 == 2) {

                long start = System.currentTimeMillis();
                Node result = AStar.run(command1, command2);
                long time = System.currentTimeMillis() - start;
                System.out.println("Temps d'exécution: " + time/1000);

                if(result == null)
                    System.out.println("No solution.");

                else {
                    result.print();
                    System.out.println(" - Generated nodes : " + result.getGenerated());
                    System.out.println(" - Visited nodes : " + result.getVisited());
                }
            }
        }
        */


        if (command2 == 4) {

            double rates = 0;

            GA.setN(10000);
            GA.setM(101);
            GA.setMaxGen(15);
            GA.setRemplacement(true);
            GA.setCroisement(false);
            GA.setSelection(true);
            GA.setCroisementProba(0.9);
            GA.setMutationProba(0.9);
            int v = 0;
            for (int i = 0; i < 10; i += 1) {

                long start = System.currentTimeMillis();
                Node result = GA.run(command1);
                long time = System.currentTimeMillis() - start;
                System.out.println("Temps d'exécution: " + time / 1000);

                if (result == null)
                    System.out.println("No solution.");

                else {
                    result.print();
                    if (result.getF() == 0) {
                        v++;
                    }
                    rates += result.getF();
                }

                //}

            }

            System.out.println("Success rate = " + v*10);
            System.out.println("fitness moyenne = " + rates/10);


                                /*for (double l = 0; l < 1.01; l += 0.3) {
                                    GA.setMutationProba(l);
                                    for (int m = 0; m < 2; m++) {
                                        if (m == 0) GA.setSelection(true);
                                        else GA.setSelection(false);
                                        for (int n = 0; n < 1; n++) {
                                            if (n == 0) GA.setCroisement(true);
                                            else GA.setCroisement(false);
                                            for (double o = 0.3; o < 1; o += 0.3) {
                                                GA.setCroisementProba(o);
                                                for (int p = 0; p < 2; p++) {
                                                    if (p == 0) GA.setRemplacement(true);
                                                    else GA.setRemplacement(false);
                                                    System.out.println("PARAMETERS ARE :");
                                                    System.out.println(1000 + " pop, " + 100 + " maxGen, " + l + " mutationProba, " + m + " selection method, " + n + " croisement method, " + o + " croisementProba, " + p + " remplacement method");

                                                    int v=0;

                                                    //for (int i = 0; i < 10; i += 1) {

                                                        long start = System.currentTimeMillis();
                                                        Node result = GA.run(command1);
                                                        long time = System.currentTimeMillis() - start;
                                                        System.out.println("Temps d'exécution: " + time / 1000);

                                                        if (result == null)
                                                            System.out.println("No solution.");

                                                        else {
                                                            result.print();
                                                            if (result.getF() == 0) {
                                                                System.out.println("params : " + l + " mutationProba, " + m + " selection method, " + n + " croisement method, " + o + " croisementProba, " + p + " remplacement method");
                                                                v++;
                                                            }
                                                        }
                                                        rates.add(result.getF());

                                                    //}
                                                    System.out.println("Success rate = " + v);

                                                }

                                        }
                                    }
                                }
                            }
                        //}*/


            //Integer min = Collections.min(rates);
            //System.out.println("min : " + min);
        }



        if (command2 == 5) {

            for (double l = 0; l < 1.01; l += 0.2) {
                PSO.setC1(l);
                for (double m = 0; m < 1.01; m += 0.2) {
                    PSO.setC2(m);
                    for (double i = 0; i < 1.01; i += 0.2) {
                        PSO.setW(i);
                        PSO.setN(500);
                        PSO.setMaxIterations(500);
                        long start = System.currentTimeMillis();
                        Node result = PSO.run(command1);
                        long time = System.currentTimeMillis() - start;
                        System.out.println("Temps d'exécution: " + time / 1000);

                        if (result == null)
                            System.out.println("No solution.");

                        else {
                            System.out.println("Best evaluation : " + PSO.fitness(Node.getGbest()));
                            System.out.println("Succes rate = " + PSO.getSuccessRate() / PSO.getMaxIterations());

                        }

                    }
                }
            }
        }

    }
}