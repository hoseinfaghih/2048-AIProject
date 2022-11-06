package ai;

import model.Node;

import java.util.Hashtable;
import java.util.ArrayList;

public class IDS {
    boolean goalFind = false;
    Hashtable<String,Boolean> hashtable = new Hashtable<>();
    public void search (Node startNode , int cutOff){
        for (int i = 0; i <= cutOff ;i++){
            hashtable.clear();
            dfsWithDepth(startNode,0,i);
            if (goalFind){
                break;
            }
        }
        if (!goalFind) {
            System.out.println("no solution");
            return;
        }
    }
    public void dfsWithDepth (Node startNode ,int depth , int maxDepth){
        if (goalFind){
            return;
        }
        if (startNode.isGoal()){
            System.out.println("you win!");
            printResult(startNode, 0);
            goalFind = true;
            return;
        }
        hashtable.put(startNode.hash(),true);
        ArrayList<Node> children = startNode.successor();
        for (Node child : children){
            if (!(hashtable.containsKey(child.hash())) && depth+1 <= maxDepth){
                dfsWithDepth(child,depth+1,maxDepth);
            }
        }
        hashtable.remove(startNode.hash());
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
