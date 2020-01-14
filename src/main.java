import Algorithms.SimulatedAnnealing;
import Problems.MapColoringProblem;

public class main {

    public static void main(String[] args) {

        MapColoringProblem gcp = new MapColoringProblem();

        new SimulatedAnnealing(gcp);
//        new Genetic(gcp);
//        Genetic g = new Genetic(gcp);
//        for (int i = 0; i < g.pSize; i++) {
//            Problem.State s = g.population.get(i);
//            s = (GraphColoringProblem.State) s;
//            for (int j = 0; j < 31; j++) {
//                System.out.println(((GraphColoringProblem.State) s).colors.get(j));
//            }
//            System.out.println("---------------------------");
//        }
//        System.out.println(g.parents.size());
//
    }
}
