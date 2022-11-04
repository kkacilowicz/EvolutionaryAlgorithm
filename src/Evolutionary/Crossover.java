package Evolutionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Crossover {

    public ArrayList<Node> orderedCrossover(ArrayList<Node> parent1, ArrayList<Node> parent2) {
        Random randomGenerator = new Random();
        int cut1 = randomGenerator.nextInt(parent1.size());
        int cut2 = randomGenerator.nextInt(parent1.size());

        while (cut1 >= cut2) {
            cut1 = randomGenerator.nextInt(parent1.size());
            cut2 = randomGenerator.nextInt(parent1.size());
        }


        Node[] notInOrdered = new Node[parent1.size() - (cut2 - cut1 + 1)];
        Node[] arrParent2 = new Node[parent1.size() - (cut2 - cut1 + 1)];
        Node[] ordered = new Node[cut2 - cut1 + 1];
        Node[] resultArray = new Node[parent1.size()];


        for (int i = 0; i < ordered.length; i++) {
            ordered[i] = parent1.get(cut1 + i);
        }
        int counter = 0;
        for (int i = 0; i < cut1; i++) {
            notInOrdered[i] = parent1.get(i);
            counter++;
        }
        for (int i = cut2 + 1; i < parent1.size(); i++) {
            notInOrdered[counter] = parent1.get(i);
            counter++;
        }

        counter = 0;
        if (cut2 - cut1 + 1 < parent2.size()) {
            for (int i = 0; i < parent2.size(); i++) {
                if (notInOrdered.length > counter
                        && notInOrdered[counter].getId() == parent2.get(i).getId()) {
                    arrParent2[counter] = parent2.get(i);
                    counter++;
                }
            }
        }

        counter = 0;
        for (int i = 0; i < cut1; i++) {
            resultArray[i] = arrParent2[i];
            counter++;
        }
        counter--;
        for (int i = 0; i < cut2 - cut1 + 1; i++) {
            resultArray[cut1 + i] = ordered[i];
        }
        for (int i = cut2 + 1; i < parent1.size(); i++) {
            resultArray[i] = arrParent2[i - cut2 + counter];
        }

        List<Node> list = List.of(resultArray);
        return new ArrayList<Node>(list);
    }


    public TSP pmxCrossover(ArrayList<Node> parent1, ArrayList<Node> parent2,
                            double[][] adjacencyMatrix) {
        Random randomGenerator = new Random();
        int cut1 = randomGenerator.nextInt(parent1.size());
        int cut2 = randomGenerator.nextInt(parent1.size());

        while (cut1 >= cut2) {
            cut1 = randomGenerator.nextInt(parent1.size());
            cut2 = randomGenerator.nextInt(parent1.size());
        }

        HashMap<Integer, Node> mapOfParent1 = new HashMap<>();
        HashMap<Integer, Node> mapOfParent2 = new HashMap<>();
        HashMap<Node, Node> commonMap12 = new HashMap<>();
        HashMap<Node, Node> commonMap21 = new HashMap<>();

        for (int i = cut1; i < cut2 + 1; i++) {
            mapOfParent1.put(i, parent1.get(i));
            mapOfParent2.put(i, parent2.get(i));
            commonMap12.put(parent1.get(i), parent2.get(i));
            commonMap21.put(parent2.get(i), parent1.get(i));
        }

        TSP tsp1 = performPMX(parent1, parent2, mapOfParent1, commonMap12);
        TSP tsp2 = performPMX(parent2, parent1, mapOfParent2, commonMap21);

        tsp1.evaluateScore(adjacencyMatrix);
        tsp2.evaluateScore(adjacencyMatrix);

        return tsp1.getScore() < tsp2.getScore() ? tsp1 : tsp2;
    }

    private TSP performPMX(ArrayList<Node> parent1, ArrayList<Node> parent2,
                           HashMap<Integer, Node> mapOfParent1, HashMap<Node, Node> commonMap12) {

        Node[] child = new Node[parent1.size()];
        TSP tsp = new TSP();
        for (int i = 0; i < parent1.size(); i++) {
            if (mapOfParent1.containsKey(i)) {
                child[i] = mapOfParent1.get(i);
            } else {
                if (mapOfParent1.containsValue(parent2.get(i))) {
                    child[i] = commonMap12.get(parent2.get(i));
                } else {
                    child[i] = parent2.get(i);
                }
            }
        }
        tsp.setNodes(new ArrayList<>(List.of(child)));
        return tsp;
    }

}
