package Evolutionary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TSP implements Comparable<TSP> {

    ArrayList<Node> nodes;

    Double score;

    double fitness;

    public double[][] createAdjacencyMatrix(){
        double[][] adjacencyMatrix = new double[nodes.size()][nodes.size()];
        for (int i=0; i < nodes.size(); i++){
            for(int j=0; j < nodes.size(); j++){
                adjacencyMatrix[i][j] = nodes.get(i).evaluate_distance(nodes.get(j));
            }
        }
        return adjacencyMatrix;
    }

    //https://towardsdatascience.com/use-genetic-algorithms-and-evolutionary-computing-for-solving-the-travelling-salesman-problem-b9623ccc9427
    public double greedyApproach(int startNode, double[][] adjacencyMatrix){
        ArrayList<Integer> visitedPaths = new ArrayList<>();
        int[] route = new int[nodes.size()];
        double sum = 0, min = Integer.MAX_VALUE;
        int i = 0, j = 0, counter = 0;

        visitedPaths.add(startNode);
        while (i < adjacencyMatrix.length
                && j < adjacencyMatrix[i].length) {

            if (counter >= adjacencyMatrix[i].length - 1) {
                break;
            }

            if (j != i && !(visitedPaths.contains(j))) {
                if (adjacencyMatrix[i][j] < min) {
                    min = adjacencyMatrix[i][j];
                    route[counter] = j + 1;
                }
            }
            j++;

            if (j == adjacencyMatrix[i].length) {
                sum += min;
                min = Integer.MAX_VALUE;
                visitedPaths.add(route[counter] - 1);
                j = 0;
                i = route[counter] - 1;
                counter++;
            }

        }
        //to return to first node
       i = route[counter-1] - 1;

        for (j = 0; j < adjacencyMatrix.length; j++) {
            if ((i != j) && adjacencyMatrix[i][j] < min) {
                min = adjacencyMatrix[i][j];
                route[counter] = j + 1;
            }
        }
        sum += min;
        return sum;
    }

    public double randomSearch(int numberOfRepetitions){
        double result = Integer.MAX_VALUE, score;
        double[][] adjacencyMatrix = createAdjacencyMatrix();
        for (int i = 0; i < numberOfRepetitions; i++) {
            score = evaluateRandomPermutation(adjacencyMatrix);
            if(score < result){
                result = score;
            }
        }
        return result;
    }

    private double evaluateRandomPermutation(double[][] adjacencyMatrix){
        double sum = 0, score;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i<nodes.size(); i++) list.add(i);
        Collections.shuffle(list);

        for (int i = 1; i < list.size(); i++) {
            score = adjacencyMatrix[list.get(i-1)][list.get(i)];
            sum += score;
        }
        sum += adjacencyMatrix[list.get(0)][list.get(list.size()-1)];
        return sum;
    }

    public void evaluateScore(double[][] adjacencyMatrix){
        double sum = 0, score;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i<nodes.size(); i++) list.add(nodes.get(i).getId() - 1);

        for (int i = 1; i < list.size(); i++) {
            score = adjacencyMatrix[list.get(i-1)][list.get(i)];
            sum += score;
        }
        sum += adjacencyMatrix[list.get(0)][list.get(list.size()-1)];
        this.setScore(sum);
    }

    @Override
    public int compareTo(TSP o) {
        if (this.getScore() > o.getScore()) {
            return 1;
        } else if (this.getScore() < o.getScore()) {
            return -1;
        }
        return 0;
    }
}
