import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Node {
    private static int size;
    private int[] queens;
    private static int[] Gbest;
    private int[] Pbest;
    private double[] velocity;
    private int f;

    Node(int[] queens) {
        this.queens = Arrays.copyOf(queens, queens.length);
    }

    Node(int[] queens, double[] velocity) {
        this.queens = Arrays.copyOf(queens, size);
        this.velocity = Arrays.copyOf(velocity, size);
    }

    public static void setSize(int s) {
        size = s;
    }

    public static int getSize() {
        return size;
    }

    public int[] getQueens() {
        return this.queens;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.queens[i] == j) {
                    System.out.print("R ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public void setVelocity(double[] velocity) {
        this.velocity = Arrays.copyOf(velocity, size);
    }

    public int[] getPbest() {
        return Pbest;
    }

    public void setPbest(int[] pbest) {
        Pbest = Arrays.copyOf(pbest, size);
    }

    public static int[] getGbest() {
        return Gbest;
    }

    public static void setGbest(int[] gbest) {
        Gbest = Arrays.copyOf(gbest, size);
    }

    // Comparateur personnalisé qui utilise la fonction d'évaluation f
    static class OurCustomComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2){
            //   We are returning the object in descending order of their age.
            if (n1.getF() > n2.getF())
                return 1;
            else if (n1.getF() < n2.getF())
                return -1;
            return 0;
        }
    }


}