package ai;

import model.Node;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Queue;

public class IDAstar {
    boolean goalFind = false;
    Hashtable<String, Boolean> hashtable = new Hashtable<>();

    public void search(Node startNode, int cutOff) {
        for (int i = 0; i <= cutOff; i++) {
            hashtable.clear();
            astarWithDepth(startNode, i);
            if (goalFind) {
                break;
            }
        }
        if (!goalFind) {
            System.out.println("no solution");
            return;
        }
    }

    public void astarWithDepth(Node startNode, int maxdepth) {
        Queue<Node> fringe = new PriorityQueue<>();


        if (startNode.heuristic() + startNode.pathCost() > maxdepth) {
            return;
        }
        fringe.add(startNode);
        hashtable.put(startNode.hash(), true);

        while (!fringe.isEmpty()) {
            Node temp = fringe.poll();
            if (temp.isGoal()) {
                System.out.println("you win!");
                printResult(temp, 0, maxdepth);
                goalFind = true;
                return;
            }
            hashtable.remove(temp.hash());

            ArrayList<Node> children = temp.successor(false);
            for (Node child : children) {
                if (!(hashtable.containsKey(child.hash()))) {
                    if (child.heuristic() + child.pathCost() <= maxdepth)
                        fringe.add(child);
                        hashtable.put(child.hash(), true);
                }
            }
        }
    }

    public void printResult(Node node, int depthCounter, int cutOff) {
        if (node.getParent() == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter + " and cutoff of : " + cutOff);
            return;
        }
        System.out.println(node.toString());
        node.drawState(false);
        printResult(node.getParent(), depthCounter + 1, cutOff);
    }
}
