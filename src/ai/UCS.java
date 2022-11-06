package ai;

import model.Node;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Queue;

public class UCS {

    public void search (Node startNode){
        Queue <Node> fringe = new PriorityQueue<>();
        Hashtable<String,Boolean> hashtable = new Hashtable<>();

        fringe.add(startNode);
        hashtable.put(startNode.hash(),true);

        while (!fringe.isEmpty()){
            Node temp = fringe.poll();
            if (temp.isGoal()){
                System.out.println("you win!");
                printResult(temp, 0,temp.cost);
                return;
            }
            hashtable.remove(temp.hash());

            ArrayList<Node> children = temp.successor();
            for (Node child : children) {
                if (!(hashtable.containsKey(child.hash()))) {
                    fringe.add(child);
                    hashtable.put(child.hash(), true);
                }
            }
        }
    }

    public void printResult (Node node, int depthCounter,int costpass){
        if (node.getParent() == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter + "   \nand cost of   : " + costpass);
            return;
        }
        System.out.println(node.toString());
        node.drawState(true);
        printResult(node.getParent(), depthCounter + 1,costpass);

    }
}
