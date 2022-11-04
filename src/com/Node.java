package com;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Node {
    int id;
    double position_x;
    double position_y;


    public void printNode() {
        System.out.println("Id: " + this.id + " PosX: " + this.position_x + " PosY: " + this.position_y);
    }

    public double evaluate_distance(Node node) {
        return Math.sqrt(Math.pow((this.position_x - node.position_x), 2)
                + Math.pow((this.position_y - node.position_y), 2));
    }


}
