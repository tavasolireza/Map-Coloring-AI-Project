package Problems;

import com.sun.tools.javac.util.List;

import java.util.ArrayList;

public class MapColoringProblem implements Problem {

    public ArrayList<ArrayList<String>> nodes;

    public MapColoringProblem() {
        nodes = new ArrayList<>();
        nodes.add(new ArrayList<>(List.of("22", "20", "28", "23", "19")));
        nodes.add(new ArrayList<>(List.of("5", "31", "7")));
        nodes.add(new ArrayList<>(List.of("15", "16", "6", "10")));
        nodes.add(new ArrayList<>(List.of("12", "16", "15", "18")));
        nodes.add(new ArrayList<>(List.of("29", "2", "31")));
        nodes.add(new ArrayList<>(List.of("30", "12", "16", "3", "10", "13")));
        nodes.add(new ArrayList<>(List.of("2", "31", "22", "20")));
        nodes.add(new ArrayList<>(List.of("20", "25", "21")));
        nodes.add(new ArrayList<>(List.of("14", "17", "31", "22", "19", "18", "14")));
        nodes.add(new ArrayList<>(List.of("3", "6", "13", "26")));
        nodes.add(new ArrayList<>(List.of("15", "18", "14")));
        nodes.add(new ArrayList<>(List.of("23", "25", "27", "30", "6", "16", "4", "18", "19")));
        nodes.add(new ArrayList<>(List.of("6", "30", "27", "26", "10")));
        nodes.add(new ArrayList<>(List.of("17","9", "18", "11")));
        nodes.add(new ArrayList<>(List.of("3", "16", "4", "18", "11")));
        nodes.add(new ArrayList<>(List.of("6", "12", "4", "15", "3")));
        nodes.add(new ArrayList<>(List.of("29", "31","9", "14")));
        nodes.add(new ArrayList<>(List.of("11", "14","9", "19", "12", "4", "15")));
        nodes.add(new ArrayList<>(List.of("9", "22", "1", "28", "23", "12", "18")));
        nodes.add(new ArrayList<>(List.of("7", "22", "1", "28", "25", "8")));
        nodes.add(new ArrayList<>(List.of("8", "25", "24")));
        nodes.add(new ArrayList<>(List.of("7", "20", "1", "19","9", "31")));
        nodes.add(new ArrayList<>(List.of("19", "28", "25", "12")));
        nodes.add(new ArrayList<>(List.of("21", "25", "27")));
        nodes.add(new ArrayList<>(List.of("24", "21", "8", "20", "28", "23", "12", "27")));
        nodes.add(new ArrayList<>(List.of("10", "13", "27")));
        nodes.add(new ArrayList<>(List.of("24", "25", "12", "30", "13", "26")));
        nodes.add(new ArrayList<>(List.of("1", "20", "25", "23", "19")));
        nodes.add(new ArrayList<>(List.of("5", "31", "17")));
        nodes.add(new ArrayList<>(List.of("12", "27", "13", "6")));
        nodes.add(new ArrayList<>(List.of("17", "29", "5", "2", "7", "22","9")));
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
        for (int i = 0; i < 31; i++) {
            int a = (int) (Math.random() * 31);
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

        for (int i = 0; i < 31; i++) {
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
//        System.out.println("Distance From Goal: " + temp.distanceFromGoal);
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