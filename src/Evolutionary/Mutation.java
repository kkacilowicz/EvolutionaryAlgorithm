package Evolutionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mutation {

    public void swap(ArrayList<Node> nodes, double probability) {
        if (Math.random() < probability) {
            Random randomGenerator = new Random();
            int position1 = randomGenerator.nextInt(nodes.size());
            int position2 = randomGenerator.nextInt(nodes.size());

            while (position1 >= position2) {
                position1 = randomGenerator.nextInt(nodes.size());
                position2 = randomGenerator.nextInt(nodes.size());
            }
            Collections.swap(nodes, position1, position2);
        }
    }

    public ArrayList<Node> inverse(ArrayList<Node> nodes, double probability) {
        if (Math.random() < probability) {
            Random randomGenerator = new Random();
            int position1 = randomGenerator.nextInt(nodes.size());
            int position2 = randomGenerator.nextInt(nodes.size());

            while (position1 >= position2) {
                position1 = randomGenerator.nextInt(nodes.size());
                position2 = randomGenerator.nextInt(nodes.size());
            }

            Node[] copyArray = new Node[position2 - position1];
            for (int i = 0; i < position2 - position1 - 1; i++) {
                copyArray[i] = nodes.get(position1 + 1 + i);
            }
            for (int i = 0; i < position2 - position1 - 1; i++) {
                nodes.set(position2 - 1 - i, copyArray[i]);
            }
        }
        return nodes;
    }

}
