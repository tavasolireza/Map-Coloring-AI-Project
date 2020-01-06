package Algorithms.HillClimbing;

import Algorithms.Algorithm;
import Problems.Problem;

import java.util.ArrayList;

public class StochasticHC extends Algorithm {

    public StochasticHC(Problem problem) {
        super(problem);
        run();
    }

    @Override
    public void run() {
        boolean status = true;
        Problem.State currentState = problem.initialState();
        Problem.State tempState = null;
        while (status) {
            visitedNodesNo++;
            ArrayList<Problem.State> neighbours = problem.neighbours(currentState);
            if (neighbours.size() == 0) {
                trap();
                getInfo(currentState);
                return;
            }
            expandedNodesNo++;
            ArrayList<Problem.State> bestNeighbours = new ArrayList<>();
            for (int i = 0; i < neighbours.size(); i++) {
                if (neighbours.get(i).distanceFromGoal < currentState.distanceFromGoal)
                    bestNeighbours.add(neighbours.get(i));
            }
            if (bestNeighbours.size() == 0) {
                trap();
                getInfo(currentState);
                return;
            }
            int random = (int) (Math.random() * bestNeighbours.size());
            tempState = bestNeighbours.get(random);
            if (problem.goalTest(tempState)) {
                getInfo(tempState);
                return;
            }
            currentState = tempState;
        }
    }

    @Override
    public Problem.State getNextState() {
        return null;
    }

    @Override
    public void getInfo(Problem.State state) {
        problem.showInfo(state);
        System.out.println("Visited Nodes: " + visitedNodesNo);
        System.out.println("Expanded Nodes " + expandedNodesNo);
    }

    @Override
    public void trap() {
        System.out.println("Local Minimum");
    }
}
