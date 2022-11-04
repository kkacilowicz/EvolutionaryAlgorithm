package Evolutionary;

import java.util.ArrayList;
import java.util.Collections;

public class Selection {

    public TSP tournament(ArrayList<TSP> population, int size, double[][] adjacencyMatrix) {
        Collections.shuffle(population);
        for (int i = 0; i < size; i++) {
            population.get(i).evaluateScore(adjacencyMatrix);
        }
        return Collections.min(population);
    }


    //TODO: rozważyć wyłuskiwanie binarysearch i normalizacje
    public TSP roulette(ArrayList<TSP> population, int size, double[][] adjacencyMatrix) {
        int scale = 1000;
        for (int i = 0; i < size; i++) {
            if (population.get(i).getScore() == 0 || population.get(i).score == null) {
                population.get(i).evaluateScore(adjacencyMatrix);
            }
            population.get(i).fitness = 1 / population.get(i).score * scale;

        }
        //Collections.sort(tutaj jakaś lambda z fitnessem)
        return null;
    }
}
