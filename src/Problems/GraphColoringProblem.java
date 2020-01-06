package Problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GraphColoringProblem implements Problem {

    public ArrayList<ArrayList<String>> nodes;

    public GraphColoringProblem() {
        nodes = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            nodes.add(new ArrayList<>());
        }
        Path pathToFile = Paths.get("graph.txt");
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            int temp = 0;
            while (line != null) {
                String[] edges = line.split(",");
                nodes.get(Integer.parseInt(edges[0]) - 1).add(edges[1]);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static class State extends Problem.State {
        public ArrayList<String> colors;

        public State(ArrayList<String> c, State state) {
            colors = c;
            parent = state;
            distanceFromGoal = 0;
        }

    }


    @Override
    public State initialState() {
        ArrayList<String> c = new ArrayList<String>();
        for (int i = 0; i < 11; i++) {
            int a = (int) (Math.random() * 11);
            if (a % 3 == 0)
                c.add("red");
            else if (a % 3 == 1)
                c.add("blue");
            else
                c.add("yellow");

        }
        State state = new State(c, null);
        update(state);
        return state;
    }

    @Override
    public float distanceFromGoal(Problem.State state) {
        State s = (State) state;
        float result = 0;
        ArrayList<String> a = s.colors;
        for (int i = 0; i < a.size(); i++) {
            String string = a.get(i);
            for (int j = 0; j < nodes.get(i).size(); j++) {
                if (a.get(Integer.parseInt(nodes.get(i).get(j)) - 1).equals(string)) {
                    result++;
                    break;
                }
            }
        }
        return result;
    }


    @Override
    public ArrayList<Problem.State> neighbours(Problem.State state) {

        State temp = (State) state;
        ArrayList<Problem.State> output = new ArrayList<>();
        ArrayList<String> tempPosition = (ArrayList<String>) temp.colors.clone();

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 3; j++) {
                tempPosition = (ArrayList<String>) temp.colors.clone();
                String temps = "";
                if (j % 3 == 0)
                    temps = "red";
                else if (j % 3 == 1)
                    temps = "blue";
                else
                    temps = "yellow";
                if (!tempPosition.get(i).equals(temps)) {
                    tempPosition.set(i, temps);
                    State neighbour = new State(tempPosition, temp);
                    update(neighbour);
                    output.add(neighbour);
                }
            }
        }
        return output;
    }

    @Override
    public boolean goalTest(Problem.State state) {
        if (state.distanceFromGoal == 0)
            return true;
        return false;
    }

    @Override
    public void showInfo(Problem.State state) {
        State temp = (State) state;
        if (temp == null)
            return;
        showInfo(state.parent);
        System.out.println(temp.colors);
        System.out.println("Distance From Goal: " + temp.distanceFromGoal);
    }

    @Override
    public void update(Problem.State state) {
        state.distanceFromGoal = distanceFromGoal(state);
    }

    @Override
    public boolean isExist(ArrayList<Problem.State> input, Problem.State second) {
        State secondState = (State) second;
        for (int i = 0; i < input.size(); i++) {
            State firstState = (State) input.get(i);
            if (firstState.colors.equals(secondState.colors)) {
                return true;
            }
        }
        return false;
    }
}