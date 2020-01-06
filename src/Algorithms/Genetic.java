package Algorithms;

import Problems.GraphColoringProblem;
import Problems.Problem;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.internal.chartpart.Chart;

import java.util.ArrayList;

/**
 * Created by eisak on 2018-12-28.
 */
public class Genetic extends Algorithm {

    public ArrayList<GraphColoringProblem.State> population;
    public ArrayList<GraphColoringProblem.State> newGen;
    public ArrayList<GraphColoringProblem.State> parents;
    public ArrayList<Float> best;
    public ArrayList<Float> worst;
    public ArrayList<Float> average;
    public int pSize = 100;
    public int k = 5;
    public double mRate = 0.01;
    public int nofGenerations = 100;

    public Genetic(Problem problem) {
        super(problem);
        population = new ArrayList<>();
        best = new ArrayList<>();
        worst = new ArrayList<>();
        average = new ArrayList<>();
        run();
    }

    @Override
    public void run() {
        createPopulation();
        getInformation();
        //test1();
        // AddtoBestAndWorst();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        for (int i = 0; i < nofGenerations; i++) {
            parentSelection();
            nexGeneration();
            mutation();
            population = newGen;
            getInformation();
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            AddtoBestAndWorst();
        }
        charts();
        // test1();
    }

    @Override
    public Problem.State getNextState() {
        return null;
    }

    @Override
    public void getInfo(Problem.State state) {

    }

    public void getInformation() {
        for (int i = 0; i < pSize; i++) {
            GraphColoringProblem.State a1 = (GraphColoringProblem.State) population.get(i);
            ArrayList<String> arr = a1.colors;
            System.out.println(arr);
            //    System.out.println(fitnessFunction(population.get(i)));
            //    System.out.println(test(population.get(i)));
            test1(population.get(i));
        }
    }


    @Override
    public void trap() {

    }

    public void createPopulation() {
        for (int i = 0; i < pSize; i++) {
            GraphColoringProblem.State temp = (GraphColoringProblem.State) problem.initialState();
            problem.update(temp);
            population.add(temp);
        }
    }

    public int fitnessFunction(Problem.State state) {
        GraphColoringProblem.State s = (GraphColoringProblem.State) state;
        int result = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i != j) {
                    if (!s.colors.get(i).equals(s.colors.get(j)))
                        result++;
                }
            }
        }
        return (result) / 20;

    }


    public int test(Problem.State state) {
        GraphColoringProblem.State s = (GraphColoringProblem.State) state;
        int result = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i != j) {
                    if (!s.colors.get(i).equals(s.colors.get(j)))
                        result++;
                }
            }
        }
        return (result);
    }

    public void AddtoBestAndWorst() {
        int index = 0;
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).distanceFromGoal < population.get(index).distanceFromGoal) {
                index = i;
            }
        }
        best.add(population.get(index).distanceFromGoal);

        int index2 = 0;
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).distanceFromGoal > population.get(index2).distanceFromGoal) {
                index2 = i;
            }
        }
        worst.add(population.get(index2).distanceFromGoal);

        int index3 = 0;
        for (int i = 0; i < population.size(); i++) {
            index3 += population.get(i).distanceFromGoal;
        }
        float temp = (float) (index3 / (population.size()));
        average.add(temp);
    }

    public void charts() {
        double[] time = new double[nofGenerations];
        for (int i = 0; i < time.length; i++)
            time[i] = i;
        // System.out.println(time.length);

        double[] best2 = new double[best.size()];
        for (int i = 0; i < best.size(); i++)
            best2[i] = best.get(i);
        //System.out.println(best2.length);

        double[] worst2 = new double[worst.size()];
        for (int i = 0; i < worst.size(); i++)
            worst2[i] = worst.get(i);

        double[] average2 = new double[average.size()];
        for (int i = 0; i < average.size(); i++)
            average2[i] = average.get(i);

        Chart chart = QuickChart.getChart("GA", "Time", "Best", "y(x)", time, best2);
        new SwingWrapper(chart).displayChart();

        Chart chart1 = QuickChart.getChart("GA", "Time", "Worst", "y(x)", time, worst2);
        new SwingWrapper(chart1).displayChart();

        Chart chart2 = QuickChart.getChart("GA", "Time", "Average", "y(x)", time, average2);
        new SwingWrapper(chart2).displayChart();

    }

    public void test1(Problem.State state) {
        GraphColoringProblem.State s = (GraphColoringProblem.State) state;
        System.out.println(s.distanceFromGoal);
    }

    public void parentSelection() {
        parents = new ArrayList<>();
        for (int i = 0; i < pSize / k; i++) {
            int index = (i * k);
            for (int j = 0; j < k; j++) {
                //if (fitnessFunction(population.get(i * k + j)) > fitnessFunction(population.get(index)))
                if (population.get(i * k + j).distanceFromGoal < population.get(index).distanceFromGoal)
                    index = i * k + j;
            }
            parents.add(population.get(index));
        }
    }

    public void nexGeneration() {
        newGen = new ArrayList<>();
        for (int i = 0; i < pSize; i++) {
            int r1 = (int) (Math.random() * pSize / k);
            int r2 = (int) (Math.random() * pSize / k);
            if (r1 != r2) {
                newGen.add(crossover(parents.get(r1), parents.get(r2)));
            } else {
                while (r1 == r2) {
                    r1 = (int) (Math.random() * pSize / k);
                    r2 = (int) (Math.random() * pSize / k);
                }
                newGen.add(crossover(parents.get(r1), parents.get(r2)));
            }
        }

    }

    public GraphColoringProblem.State crossover(GraphColoringProblem.State s1, GraphColoringProblem.State s2) {
        GraphColoringProblem.State s11 = (GraphColoringProblem.State) s1;
        GraphColoringProblem.State s12 = (GraphColoringProblem.State) s2;
        ArrayList<String> arr = new ArrayList<>();
        arr.add(s11.colors.get(0));
        arr.add(s11.colors.get(1));
        arr.add(s11.colors.get(2));
        arr.add(s11.colors.get(4));
        arr.add(s11.colors.get(7));
        arr.add(s12.colors.get(3));
        arr.add(s12.colors.get(5));
        arr.add(s12.colors.get(6));
        arr.add(s12.colors.get(8));
        arr.add(s12.colors.get(9));
        arr.add(s12.colors.get(10));
        GraphColoringProblem.State s3 = new GraphColoringProblem.State(arr, null);
        problem.update(s3);
        return s3;
    }

    public void mutation() {
        int counter = (int) Math.round(pSize * 11 * mRate);
        for (int i = 0; i < counter; i++) {
            int a = (int) (Math.random() * pSize);
            int b = (int) (Math.random() * 11);
            GraphColoringProblem.State a1 = (GraphColoringProblem.State) newGen.get(a);
            ArrayList<String> arr = a1.colors;
            String colors = "red";
            int color = (int) (Math.random() * 3);
            if (color == 0)
                colors = "red";
            else if (color == 1)
                colors = "blue";
            else colors = "yellow";
            while (colors.equals(arr.get(b))) {
                int color2 = (int) (Math.random() * 3);
                if (color2 == 0)
                    colors = "red";
                else if (color2 == 1)
                    colors = "blue";
                else colors = "yellow";
            }
            arr.set(b, colors);
            a1.colors = arr;
            problem.update(a1);
            newGen.set(a, a1);
        }
    }
}
