package Evolutionary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Loader loader = new Loader();
        Logger logger = new Logger();
        TSP tsp = new TSP();
        Mutation mutation = new Mutation();
        Crossover crossover = new Crossover();
        Selection selection = new Selection();

        File folder = new File("src/Evolutionary/TSP");
        File fileEntry = new File("src/Evolutionary/TSP/gr666.tsp");

//        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {

        int pop_size = 666;
        int gen = 2000;
        int i = 0;
        int tournament_size = 5;
        double px = 0.7;
        double pm = 0.7;

        logger.printWriter.println(fileEntry);
        logger.printWriter.println("Mutation swap, OX");
        logger.printWriter.println("pop_size " + pop_size);
        logger.printWriter.println("gen " + gen);
        logger.printWriter.println("px " + px);
        logger.printWriter.println("pm " + pm);
        logger.printWriter.println("tour_size " + tournament_size);
        logger.printWriter.println("best\t worst\t average");

        ArrayList<Node> nodes = loader.loadData(fileEntry.getPath());
        tsp.setNodes(nodes);
        double[][] adjacencyMatrix = tsp.createAdjacencyMatrix();

//        for (int k = 0; k < 10; k++) {

            Population[] populations = new Population[gen];

            for (int j = 0; j < gen; j++) {
                Population pop = new Population();
                pop.setIndividuals(new ArrayList<>());
                populations[j] = pop;
            }
//
//            populations[0].randomInitialize(tsp, pop_size);
        populations[0].greedyInitialize(tsp, adjacencyMatrix);
            populations[0].evaluate(adjacencyMatrix);
            TSP theBestSolution = populations[0].getIndividuals().get(0);

            while (i < gen && i + 1 < gen) {
                while (populations[i + 1].getIndividuals().size() != pop_size) {
                    TSP p1 = selection.tournament(populations[i].getIndividuals(), tournament_size, adjacencyMatrix);
                    TSP p2 = selection.tournament(populations[i].getIndividuals(), tournament_size, adjacencyMatrix);
//                    TSP p1 = selection.roulette(populations[i]);
//                    TSP p2 = selection.roulette(populations[i]);
                    TSP newIndividual = new TSP();
                    if (Math.random() < px) {
//                            newIndividual = crossover.pmxCrossover(p1.getNodes(), p2.getNodes(), adjacencyMatrix);
                        newIndividual.setNodes(crossover.orderedCrossover(p1.getNodes(), p2.getNodes()));
                    } else {
                        newIndividual.setNodes(new ArrayList<>(p1.getNodes()));
                    }
                    mutation.swap(newIndividual.getNodes(), pm);
                    newIndividual.evaluateScore(adjacencyMatrix);
                    populations[i + 1].getIndividuals().add(newIndividual);
                    if (theBestSolution.getScore() > newIndividual.getScore()) {
                        theBestSolution = newIndividual;
                    }
                }
                logger.printWriter.print(populations[i].getBestIndividual().score + "\t");
                logger.printWriter.print(populations[i].getWorstIndividual().score + "\t");
                logger.printWriter.print(populations[i].getAverage(adjacencyMatrix) + "\t \n");
                i++;
            }
//            logger.printWriter.println(fileEntry);


//        }
    }
//    }

}
