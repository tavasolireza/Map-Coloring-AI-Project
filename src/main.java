import Algorithms.SimulatedAnnealing;
import Problems.GraphColoringProblem;

public class main {

    public static void main(String[] args) {

        GraphColoringProblem gcp = new GraphColoringProblem();

        new SimulatedAnnealing(gcp);
//          new Genetic(gcp);
        // new FirstChoiceHC(gcp);
        //  new RandomRestartHC(gcp, 4);
        //  new SimpleHC(gcp);
        //  new StochasticHC(gcp);
//        Genetic g = new Genetic(gcp);
//        for (int i = 0; i < g.pSize; i++) {
//            Problem.State s = g.population.get(i);
//            s = (GraphColoringProblem.State) s;
//            for (int j = 0; j < 11; j++) {
//                System.out.println(((GraphColoringProblem.State) s).colors.get(j));
//            }
//            System.out.println("---------------------------");
//        }
//        System.out.println(g.parents.size());
//
    }
}
