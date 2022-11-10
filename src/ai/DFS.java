package ai;

import model.Node;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Stack;

public class DFS { // contains two equivalent implementation
    boolean goalFind = false;
    Hashtable <String,Boolean> hashtable1 = new Hashtable<>();
    public void search (Node startNode){ // implementation with recursive function

        //Hashtable <String,Boolean> hashtable = new Hashtable<>();
        if (goalFind){
            return;
        }
        hashtable1.put(startNode.hash(),true);
        if (startNode.isGoal()){
            System.out.println("you win!");
            //startNode.drawState();
            printResult(startNode, 0);
            goalFind = true;
            return;
        }
        ArrayList<Node> children = startNode.successor(false);
        for (Node child : children){
            if (!(hashtable1.containsKey(child.hash()))){
                search(child);
            }
        }
        //hashtable.remove(startNode.hash());
        if (hashtable1.isEmpty()){
            System.out.println("no solution");
            return;
        }
    }

    public void search2 (Node startNode){ //implementation with LIFO stack
        Stack<Node> frontier = new Stack<Node>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> exp = new Hashtable<>();

        if (startNode.isGoal()) {
            System.out.println("you win!");
            printResult(startNode, 0);
            return;
        }
        frontier.push(startNode);
        inFrontier.put(startNode.hash(), true);

        while (!frontier.isEmpty()) {
            Node temp = frontier.pop();

            exp.put(temp.hash(), true);

            ArrayList<Node> children = temp.successor(false);
            for (Node child : children) {
                if (!((inFrontier.containsKey(child.hash()))|| (exp.containsKey(child.hash())) ) ){
                    if (child.isGoal()) {
                        printResult(child, 0);
                        System.out.println("you win !!!");
                        return;
                    }
                    frontier.push(child);
                    inFrontier.put(child.hash(), true);
                }
            }
        }
        System.out.println("no solution");
    }

    public void printResult (Node node, int depthCounter){
        if (node.getParent() == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter);
            return;
        }
        System.out.println(node.toString());
        node.drawState(false);
        printResult(node.getParent(), depthCounter + 1);
    }
}
