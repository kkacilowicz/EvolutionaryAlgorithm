package Evolutionary;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;

@Getter
@Setter
public class Population {

    ArrayList<TSP> individuals;

    void randomInitialize(TSP firstIndividual, int size) {
        this.individuals = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TSP newIndividual = new TSP();
            ArrayList<Node> nodes = new ArrayList<>(firstIndividual.getNodes());
            newIndividual.setNodes(nodes);
            Collections.shuffle(newIndividual.nodes);
            this.individuals.add(newIndividual);
        }
    }

    void greedyInitialize(TSP firstIndividual, double[][] adjacencyMatrix){
        this.individuals = new ArrayList<>();
        for (int i = 0; i < firstIndividual.getNodes().size(); i++) {
            TSP newIndividual = new TSP();
            ArrayList<Node> nodes = new ArrayList<>(firstIndividual.getNodes());
            newIndividual.setNodes(nodes);
            newIndividual.greedyInitialize(i, adjacencyMatrix);
            this.individuals.add(newIndividual);
        }
    }

    public TSP getBestIndividual() {
        return Collections.min(this.individuals);
    }

    public TSP getWorstIndividual() {
        return Collections.max(this.individuals);
    }

    public double getAverage(double[][] adjacencyMatrix) {
        double sum = 0.0;
        for (TSP individual : this.individuals) {
            if (individual.getScore() == 0 || individual.getScore() == null) {
                individual.evaluateScore(adjacencyMatrix);
            }
            sum += individual.getScore();
        }
        return sum / this.individuals.size();
    }

    double evaluateSumOfFitness() {
        double sum = 0.0;
        for (TSP individual : this.individuals) {
            sum += individual.getFitness();
        }
        return sum;
    }


    void evaluate(double[][] adjacencyMatrix) {
        for (TSP tsp : this.getIndividuals()) {
            tsp.evaluateScore(adjacencyMatrix);
        }
    }

}
