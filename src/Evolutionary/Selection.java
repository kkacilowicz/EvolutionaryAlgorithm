package Evolutionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Selection {

    public TSP tournament(ArrayList<TSP> population, int size, double[][] adjacencyMatrix) {
        Collections.shuffle(population);
        for (int i = 0; i < size; i++) {
            population.get(i).evaluateScore(adjacencyMatrix);
        }
        return Collections.min(population);
    }


    public TSP roulette(Population population) {
        Random randomGenerator = new Random();
        double[] roulette = new double[population.getIndividuals().size() + 1];
        roulette[0] = 0.0;
        for (int i = 0; i < population.getIndividuals().size(); i++) {
            if (population.getIndividuals().get(i).fitness == null) {
                population.getIndividuals().get(i).evaluateFitness();
            }
             roulette[i +1] = roulette[i] + population.getIndividuals().get(i).score;
        }
        double draw = randomGenerator.nextDouble(roulette[population.getIndividuals().size()]);
        //binary search result is never 0
        int drawnPosition = Arrays.binarySearch(roulette, draw);

        if(drawnPosition < 0){
            return population.getIndividuals().get(Math.abs(drawnPosition) - 2);
        }else if( drawnPosition == 0){
            return population.getIndividuals().get(0);
        }
        return population.getIndividuals().get(drawnPosition - 1);
    }
}
