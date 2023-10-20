import java.util.*;

public class GA {

    private static int maxGen;

    private static int n;

    private static int m;

    private static double mutationProba;

    private static double croisementProba;

    private static boolean selection;

    private static boolean croisement;

    private static boolean remplacement;

    private static int pointCroisement1;

    private static int pointCroisement2;

    private static double successRate;


    /**
     * fonction de génération de Nodes aléatoires
     */
    public static ArrayList<Node> generationAleatoirePopulation(int n) {
        //System.out.println("generation");

        ArrayList<Node> pop = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int[] current = new int[Node.getSize()];
            for (int j = 0; j < Node.getSize(); j++) {
                // TODO: instead of 0 it needs to be a random value in {0, 1}
                current[j] = random(Node.getSize());
            }
            pop.add(new Node(current));
        }
        //System.out.println(pop.size());
        return pop;
    }

    /**
     * fonction de génération aléatoire d'un chiffre
     */
    public static int random(int x) {
        Random random = new Random();
        return random.nextInt(x);
    }

    /**
     * fonction d'évaluation des Nodes d'un ensemble
     */
    public static void fitness(ArrayList<Node> pop) {
        //System.out.println("fitness 1");
        //System.out.println(pop.size());
        for (Node Node : pop) {

            Node.setF(fitness(Node.getQueens()));
        }
    }

    /**
     * fonction d'évaluation
     */
    // TODO: change fitness
    public static int fitness(int[] positions) {
        int conflits = 0;

        //System.out.println("fitness 2");

        if (Objects.nonNull(positions)) {
            for (int i = 0; i < Node.getSize(); i++)
                for (int j = i + 1; j < Node.getSize(); j++)
                    // Incrementer si deux reines sont sur la même colonne ou diagonale
                    if (positions[i] == positions[j] || Math.abs(positions[i] - positions[j]) == Math.abs(i - j))
                        conflits++;
        }

        return conflits;
    }

    /**
     *  fonction d'amelioration
     */
    private static boolean isAttacking(int[] positions, int index) {
        int row = index;
        int col = positions[index];

        for (int i = 0; i < index; i++) {
            int otherQueenRow = i;
            int otherQueenCol = positions[i];

            if ((col == otherQueenCol) || (Math.abs(row - otherQueenRow) == Math.abs(col - otherQueenCol)))  // same column
                return true;

        }
        return false;
    }
    /**
     * fonction de sélectionnement des parents
     */
    public static ArrayList<Node> selectionElitiste(ArrayList<Node> pop) {

        //System.out.println("selection");

        Comparator<Node> fComparator = (p1, p2) -> Integer.compare(p2.getF(), p1.getF());

        pop.sort(fComparator);

    /*    PriorityQueue<Node> newPop = new PriorityQueue<>( new Node.OurCustomComparator());

        newPop.addAll(pop);

        return new Node(Objects.requireNonNull(newPop.poll()).sol1, Objects.requireNonNull(newPop.poll()).sol1);
    *//*
        return new Node(pop.get(0).sol1, pop.get(pop.size()-1).sol1);
        */

        if (m > pop.size())
            return null;
        else {
            ArrayList<Node> sols = new ArrayList<>();

            for (int i = 0; i < m ; i++)
                //sols.add(new Node(pop.get(i).getQueens()));
                sols.add(pop.get(i));
            return sols;
        }

    }

    public static ArrayList<Node> selectionUniforme(ArrayList<Node> pop) {

        //System.out.println("selection");

        if (m > pop.size())
            return null;
        else {
            ArrayList<Node> sols = new ArrayList<>();

            for (int i = 0; i < m ; i++)
                //sols.add(new Node(pop.get(i).getQueens()));
                sols.add(pop.get(i));
            return sols;
        }

    }

    /**
     * fonction de croisement des parents
     */

    public static ArrayList<Node> croisementUniPoint(ArrayList<Node> parents) {

        //System.out.println("croisement");

        ArrayList<Node> enfants = new ArrayList<>();

        pointCroisement1 = random(Node.getSize());

        int[] newS1 = new int[Node.getSize()];
        int[] newS2 = new int[Node.getSize()];

        for (int j = 0 ; j < parents.size() ; j++) {
            for (int k = j + 1; k < parents.size(); k++) {

                if (Math.random() < croisementProba) {

                    for (int i = 0; i < Node.getSize(); i++) {
                        if (i <= pointCroisement1) {
                            newS1[i] = parents.get(j).getQueens()[i];
                            newS2[i] = parents.get(k).getQueens()[i];
                        } else {
                            newS1[i] = parents.get(k).getQueens()[i];
                            newS2[i] = parents.get(j).getQueens()[i];
                        }
                    }

                    enfants.add(new Node(newS1));
                    enfants.add(new Node(newS2));
                }
            }
        }
        return enfants;
    }

    public static ArrayList<Node> croisementBiPoint(ArrayList<Node> parents) {

        //System.out.println("croisement");

        ArrayList<Node> enfants = new ArrayList<>();

        pointCroisement1 = random(Node.getSize());
        pointCroisement2 = random(Node.getSize());

        int[] newS1 = new int[Node.getSize()];
        int[] newS2 = new int[Node.getSize()];

        for (int j = 0 ; j < parents.size() ; j++) {
            for (int k = j + 1; k < parents.size(); k++) {

                if (Math.random() < croisementProba) {

                    for (int i = 0; i < Node.getSize(); i++) {
                        if ((i <= pointCroisement1) || (i > pointCroisement2)) {
                            newS1[i] = parents.get(j).getQueens()[i];
                            newS2[i] = parents.get(k).getQueens()[i];
                        } else if ((i > pointCroisement1) && (i <= pointCroisement2)) {
                            newS1[i] = parents.get(k).getQueens()[i];
                            newS2[i] = parents.get(j).getQueens()[i];
                        }
                    }

                    enfants.add(new Node(newS1));
                    enfants.add(new Node(newS2));
                }
            }
        }
        return enfants;
    }

    /**
     * fonction de mutation des enfants
     */
    public static ArrayList<Node> mutation(ArrayList<Node> enfantsC) {

        //System.out.println("mutation");
        ArrayList<Node> enfantsM = new ArrayList<>();

        for (Node enfant: enfantsC) {
            int[] newS1 = Arrays.copyOf(enfant.getQueens(), enfant.getQueens().length);

            for (int i = 0; i < newS1.length; i++)
                if (Math.random() < mutationProba) {
                    if (isAttacking(newS1, i)) {
                        int x = newS1[i];
                        int x2 = random(newS1.length);
                        newS1[i] = newS1[x2];
                        newS1[x2] = x;
                    }
                }

            enfantsM.add(new Node(newS1));
        }

        return enfantsM;
    }

    /**
     * fonction de calcul du meilleur individu(meilleure évaluation) parmis la population
     */
    // TODO: remove allocation in if statement
    public static Node meilleurIndividu(ArrayList<Node> pop) {

        //System.out.println("best individu");
        fitness(pop);
        int bestEval = pop.get(0).getF();
        //System.out.println("initial bestEval : " + bestEval);

        int[] ker = Arrays.copyOf(pop.get(0).getQueens(), pop.get(0).getQueens().length);

        for (Node node : pop)
            if (bestEval > fitness(node.getQueens())) {
                bestEval = fitness(node.getQueens());
                //System.out.println("bestEval changed : " + bestEval);
                ker = Arrays.copyOf(node.getQueens(), node.getQueens().length);
            }

            /*System.out.println("eval meilleur indivi : " + bestEval);
            System.out.print("final best sol of meilleur individu function ==");
            for( int i = 0 ; i < ker.length ; i++)
                    System.out.print(ker[i] +  " ");*/

        Node temp = new Node(ker);
        temp.setF(bestEval);
        return temp ;
    }

    /**
     * fonction de remplacement de la population
     */
    public static ArrayList<Node> remplacementBest(ArrayList<Node> pop, ArrayList<Node> enfantsC, ArrayList<Node> enfantsM) {

        //System.out.println("remplacement");
        ArrayList<Node> allChildren = new ArrayList<>();
        allChildren.addAll(enfantsC);
        allChildren.addAll(enfantsM);

        Comparator<Node> fComparator = (p1, p2) -> Integer.compare(p2.getF(), p1.getF());
        pop.sort(fComparator);
        allChildren.sort(fComparator);

        int len = pop.size();

        for (int i = 0 ; i < len / 2 ; i++)
            pop.remove(pop.size()-1);

        for (int i = 0 ; i < len / 2 ; i++)
            pop.add(allChildren.get(i));

        return pop;
    }

    public static ArrayList<Node> remplacementAleatoire(ArrayList<Node> pop, ArrayList<Node> enfantsC, ArrayList<Node> enfantsM) {

        //System.out.println("remplacement");
        ArrayList<Node> allChildren = new ArrayList<>();
        allChildren.addAll(enfantsC);
        allChildren.addAll(enfantsM);

        int len = pop.size();

        for (int i = 0 ; i < len / 2 ; i++)
            pop.remove(pop.size()-1);

        for (int i = 0 ; i < len / 2 ; i++)
            pop.add((allChildren.remove(random(allChildren.size()))));

        return pop;
    }

    /**
     * fonction de l'algorithme génétique
     */

    public static Node run(int size) {

        double successRate = 0;

        Node.setSize(size);
        //System.out.println("size 1 : " + Node.getSize());

        ArrayList<Node> pop = generationAleatoirePopulation(n);
        //System.out.println("size 3 : " + pop.size());

        fitness(pop);

            /*System.out.println("pop initiale");

            for (int j = 0 ; j < pop.size() ; j++)
                System.out.println(pop.get(j).getF());


            System.out.println("-------------------");*/

        Node bestSol = new Node(meilleurIndividu(pop).getQueens());
        bestSol.setF(fitness(bestSol.getQueens()));

        //System.out.println("\nbest sol - " + bestSol.getF());

        ArrayList<Node> parents;
        ArrayList<Node> enfantsC;
        ArrayList<Node> enfantsM;

        int i = 0;
        while (i < maxGen) {

                /*System.out.println("i" + i);
                System.out.println("maxgen" + maxGen);*/

            if (selection)
                parents = selectionUniforme(pop);
            else
                parents = selectionElitiste(pop);
            if (croisement)
                enfantsC = croisementUniPoint(parents);
            else
                enfantsC = croisementBiPoint(parents);

            fitness(enfantsC);
            enfantsM = mutation(enfantsC);
            fitness(enfantsM);

            if (remplacement)
                pop = remplacementAleatoire(pop, enfantsC, enfantsM);
            else
                pop = remplacementBest(pop, enfantsC, enfantsM);

            fitness(pop);
            if (bestSol.getF() > meilleurIndividu(pop).getF()) {
                bestSol = new Node(meilleurIndividu(pop).getQueens());
                bestSol.setF(fitness(bestSol.getQueens()));
            }
            if (meilleurIndividu(pop).getF() == 0) {
                return meilleurIndividu(pop);
            }
            i++;
        }

            /*for (int l = 0; l < 1; l++) {
                // Selection
                if (l == 0) {
                    parents = selectionUniforme(pop);
                } else {
                    parents = selectionElitiste(pop);
                }

                for (int cp = 0; cp < croisementProba.length; cp++) {
                    for (int cr = 0; cr < 2; cr++) {
                        // Croisement
                        if (cr == 0) {
                            enfantsC = croisementUniPoint(parents, croisementProba[cp]);
                        } else {
                            enfantsC = croisementBiPoint(parents, croisementProba[cp]);
                        }

                        fitness(enfantsC);
                        for (int k = 0; k < mutationProba.length; k++) {
                            // Mutation
                            enfantsM = mutation(enfantsC, mutationProba[k]);

                            fitness(enfantsM);
                            for (int remp = 0; remp < 2; remp++) {
                                // Remplacement

                                if (remp == 0) {
                                    pop = remplacementAleatoire(pop, enfantsC, enfantsM);
                                } else {
                                    pop = remplacementBest(pop, enfantsC, enfantsM);
                                }
                                fitness(pop);
                                if (bestSol.getF() > meilleurIndividu(pop).getF()) {
                                    bestSol = new Node(meilleurIndividu(pop).getQueens());
                                    bestSol.setF(fitness(bestSol.getQueens()));
                                }
                                if (meilleurIndividu(pop).getF() == 0) {
                                    successRate++;
                                    System.out.println("parameters are : " + l + ", " + croisementProba[cp] + ", " + cr + ", " + mutationProba[k] + ", " + remp);
                                }
                                i++;
                                /*if (i > maxGen) {
                                    setSuccessRate(successRate);
                                    return bestSol;
                                }
                            }

                        }

                    }

                }

            }*/

        return bestSol;
        //  }
        //return null;
    }


    public static void main(String[] args) {

        /*
        [3, 4, 3, 1, 3, 2, 3, 2, 1]
        [484, 114, 205, 288, 506, 503, 201, 127, 410]
        [2, 10, 3, 8, 5, 7, 9, 5, 3, 2]
        [771, 121, 281, 854, 885, 734,  486, 1003, 83, 62]
        [382745, 799601, 909247, 729069, 467902,  44328,  34610, 698150, 823460, 903959, 853665, 551830, 610856, 670702, 488960, 951111, 323046, 446298, 931161,  31385, 496951, 264724, 224916, 169684]
        * */

        System.out.print("Initial set : ");

            /*Node sol = algoGenetique(4, 6, 100);
            System.out.print("Best Node found : ");
            sol.print();

            fitness(sol);
            System.out.print("Its fitness score : " + sol.getF());*/
    }

    public static int getMaxGen() {
        return maxGen;
    }

    public static void setMaxGen(int MAXGEN) {
        GA.maxGen = MAXGEN;
    }

    public static int getN() {
        return n;
    }

    public static void setN(int n) {
        GA.n = n;
    }

    public static int getM() {
        return m;
    }

    public static void setM(int m) {
        GA.m = m;
    }

    public static double getMutationProba() {
        return mutationProba;
    }

    public static void setMutationProba(double mutationProba) {
        GA.mutationProba = mutationProba;
    }

    public static int getPointCroisement1() {
        return pointCroisement1;
    }

    public static void setPointCroisement1(int pointCroisement1) {
        GA.pointCroisement1 = pointCroisement1;
    }

    public static int getPointCroisement2() {
        return pointCroisement2;
    }

    public static void setPointCroisement2(int pointCroisement2) {
        GA.pointCroisement2 = pointCroisement2;
    }

    public static double getSuccessRate() {
        return successRate;
    }

    public static void setSuccessRate(double successRate) {
        GA.successRate = successRate;
    }

    public static double getCroisementProba() {
        return croisementProba;
    }

    public static void setCroisementProba(double croisementProba) {
        GA.croisementProba = croisementProba;
    }

    public static boolean isSelection() {
        return selection;
    }

    public static void setSelection(boolean selection) {
        GA.selection = selection;
    }

    public static boolean isCroisement() {
        return croisement;
    }

    public static void setCroisement(boolean croisement) {
        GA.croisement = croisement;
    }

    public static boolean isRemplacement() {
        return remplacement;
    }

    public static void setRemplacement(boolean remplacement) {
        GA.remplacement = remplacement;
    }
}

