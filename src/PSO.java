import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class PSO {

    private static double successRate;
    private static int maxIterations;
    private static double c1;
    private static double c2;
    private static int n;

    private static double W;

    /**
     * fonction de génération de Nodes aléatoires
     */
    public static ArrayList<Node> generationAleatoirePopulation(int n) {
        //System.out.println("generation");

        ArrayList<Node> pop = new ArrayList<>();
        //System.out.println("pop size after creation" + pop.size());

        // System.out.println("n = " + n);

        for (int i = 0; i < n; i++) {
            int[] current = new int[Node.getSize()];
            double[] velo = new double[Node.getSize()];

            for (int j = 0; j < Node.getSize(); j++) {
                // TODO: instead of 0 it needs to be a random value in {0, 1}
                current[j] = random(Node.getSize());
                velo[j] = Math.random()*10;
            }
            pop.add(new Node(current,velo));
            pop.get(i).setPbest(current);
            //System.out.println("pop size" + pop.size());

        }
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
        for (Node Node : pop) {
            Node.setF(fitness(Node.getQueens()));
        }
    }

    /**
     * fonction d'évaluation
     */
    public static int fitness(int[] positions) {
        int conflits = 0;

        if (Objects.nonNull(positions)) {
            for (int i = 0; i < Node.getSize(); i++)
                for (int j = i + 1; j < Node.getSize(); j++)
                    // Incrementer si deux reines sont sur la même colonne ou diagonale
                    if (positions[i] == positions[j] || Math.abs(positions[i] - positions[j]) == Math.abs(i - j))
                        conflits++;
        }

        return conflits;
    }

    public static void update(Node particles) {

        //System.out.println("velocity size : " + particles.getVelocity().length);

        for (int i = 0; i < particles.getVelocity().length; i++) {
            // MAJ Velocity
            particles.getVelocity()[i] = (int) (W * particles.getVelocity()[i]
                    + c1 * Math.random() * (particles.getPbest()[i] - particles.getQueens()[i])
                    + c2 * Math.random() * (Node.getGbest()[i] - particles.getQueens()[i]));
            // MAJ Positions
            particles.getQueens()[i] += particles.getVelocity()[i];
            // Fixation en cas de debordements
            if (particles.getQueens()[i] < 0)
                particles.getQueens()[i] = 0;
            else if (particles.getQueens()[i] >= particles.getVelocity().length)
                particles.getQueens()[i] = particles.getVelocity().length - 1;
        }
        if (fitness(particles.getPbest()) > fitness(particles.getQueens()))
            particles.setPbest(particles.getQueens());
        //return particles;
    }




    /**
     * fonction de calcul du meilleur individu(meilleure évaluation) parmis la population
     */
    public static int[] meilleurIndividu(ArrayList<Node> pop) {

        fitness(pop);

        /*System.out.println("pop in meilleur individu");

        for (int j = 0 ; j < pop.size() ; j++)
            System.out.println(pop.get(j).getF());*/
        int[] ker = new int[Node.getSize()];

        int bestEval = pop.get(0).getF();

        for (Node node : pop)
            if (bestEval > fitness(node.getPbest())) {
                bestEval = fitness(node.getPbest());

                ker = Arrays.copyOf(node.getPbest(), node.getPbest().length);
            }
        return ker;

    }

    public static Node run(int size) {


        double successRate = 0;
        Node.setSize(size);

        // Initialisation des positions & velocites

        ArrayList<Node> pop = generationAleatoirePopulation(n);
        //System.out.println("size 3 : " + pop.size());


        fitness(pop);

        /*System.out.println("pop initiale");

        for (int j = 0 ; j < pop.size() ; j++)
            System.out.println(pop.get(j).getF());*/


        Node.setGbest(meilleurIndividu(pop));


        int i = 0;
        while (i < maxIterations) {
            for(Node node: pop)
                update(node);

            fitness(pop);
            Node.setGbest(meilleurIndividu(pop));
            if(fitness(Node.getGbest()) == 0)
                return new Node(Node.getGbest());
            i++;
        }
        return new Node(Node.getGbest());
    }

    public static double getC1() {
        return c1;
    }

    public static void setC1(double c1) {
        PSO.c1 = c1;
    }

    public static double getC2() {
        return c2;
    }

    public static void setC2(double c2) {
        PSO.c2 = c2;
    }

    public static int getMaxIterations() {
        return maxIterations;
    }

    public static void setMaxIterations(int maxIterations) {
        PSO.maxIterations = maxIterations;
    }

    public static void setN(int n) {
        PSO.n = n;
    }

    public static double getSuccessRate() {
        return successRate;
    }

    public static void setSuccessRate(double successRate) {
        PSO.successRate = successRate;
    }

    public static double getW() {
        return W;
    }

    public static void setW(double w) {
        W = w;
    }
}
