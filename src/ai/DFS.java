package ai;

import model.Node;

import java.util.Hashtable;
import java.util.ArrayList;

public class DFS {
    boolean goalFind = false;
    Hashtable <String,Boolean> hashtable = new Hashtable<>();
    public void search (Node startNode){
        //Hashtable <String,Boolean> hashtable = new Hashtable<>();
        if (goalFind){
            return;
        }
        hashtable.put(startNode.hash(),true);
        if (startNode.isGoal()){
            System.out.println("you win!");
            //startNode.drawState();
            printResult(startNode, 0);
            goalFind = true;
            return;
        }
        ArrayList<Node> children = startNode.successor();
        for (Node child : children){
            if (!(hashtable.containsKey(child.hash()))){
                search(child);
            }
        }

    }

    public void printResult (Node node, int depthCounter){
        if (node.getParent() == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter);
            return;
        }
        System.out.println(node.toString());
        node.drawState();
        printResult(node.getParent(), depthCounter + 1);
    }
}
