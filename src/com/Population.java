package com;

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

    public TSP getBestIndividual() {
        return Collections.min(this.individuals);
    }

    void evaluate(double[][] adjacencyMatrix) {
        for (TSP tsp : this.getIndividuals()) {
            tsp.evaluateScore(adjacencyMatrix);
        }
    }

}
