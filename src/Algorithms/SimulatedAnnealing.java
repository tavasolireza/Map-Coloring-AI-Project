package Algorithms;

import Problems.Problem;

import java.util.ArrayList;

public class SimulatedAnnealing extends Algorithm {

    float t = 1.0f;
    float alpha = 0.99f;
//    float tn = 0.001f;
    float t0 = 1.0f;
    float n = 50;

    int counter = 1;

    public SimulatedAnnealing(Problem problem) {
        super(problem);
        run();
    }

    @Override
    public void run() {
        visitedNodesNo = 0;
        expandedNodesNo = 0;
        boolean status = true;
        Problem.State currentState = problem.initialState();
        Problem.State tempState = null;
        while (status && counter < n) {
            visitedNodesNo++;
            if (problem.goalTest(currentState)) {
                getInfo(currentState.parent);
                return;
            }
            ArrayList<Problem.State> neighbours = problem.neighbours(currentState);
            if (neighbours.size() == 0) {
                trap();
                return;
            }
            expandedNodesNo++;
            while (true) {
                int random = (int) (Math.random() * neighbours.size());
                if (neighbours.get(random).distanceFromGoal < currentState.distanceFromGoal) {
                    tempState = neighbours.get(random);
                    break;
                }
                float temperature = getTemperature(currentState.distanceFromGoal - neighbours.get(random).distanceFromGoal, 0, counter);
                float rand = (float) Math.random();
                if (temperature > rand) {
                    tempState = neighbours.get(random);
                    break;
                }
            }
            currentState = tempState;
            counter++;
        }
        if (counter == n)
            getInfo(currentState);
    }

    @Override
    public Problem.State getNextState() {
        return null;
    }

    @Override
    public void getInfo(Problem.State state) {
        problem.showInfo(state);
//        System.out.println("Counter: " + counter);
//        System.out.println("Visited Nodes: " + visitedNodesNo);
//        System.out.println("Extended Nodes: " + expandedNodesNo);
    }

    @Override
    public void trap() {
        System.out.println("Local Minimum");
    }

    public float getTemperature(float distance, int i, int counter) {
        float temp = (float) Math.exp(distance / t);
        switch (i) {
            case 0:
                alpha = 0.85f;
                t = (float) (t0 * Math.pow(alpha, counter));
                break;
            case 1:
                alpha = 2.0f;
                t = (float) (t0 / (1.0f + alpha * Math.log(1 + counter)));
                break;
            case 2:
                alpha = 1.0f;
                t = t0 / (1 + alpha * counter);
                break;
            case 3:
                alpha = 1.0f;
                t = t0 / (1 + alpha * counter * counter);
                break;
        }
        return temp;
    }
}
