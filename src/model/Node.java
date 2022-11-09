package model;

import core.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.lang.Math;

public class Node implements Comparable<Node>{
    Board board;
    Node parent;
    Movement previousMovement;
    private int cost;
    public int search_mode; // 0 DFS , BFS , IDS
                            // 1 UCS
                            // 2 A* , IDA*
                            // 3 GBFS

    public Node(Board board, Node parent, Movement previousMovement , int cost , int search_mode) {
        this.parent = parent;
        this.board = board;
        this.previousMovement = previousMovement;
        this.cost = cost;
        this.search_mode = search_mode;
    }

    public ArrayList<Node> successor(boolean ucsFlag) {
        if (ucsFlag){
            ArrayList<Node> result = new ArrayList<Node>();
            result.add(new Node(board.moveLeft(), this, Movement.LEFT,this.cost + 1,this.search_mode));
            result.add(new Node(board.moveRight(), this, Movement.RIGHT , this.cost + 3,this.search_mode));
            result.add(new Node(board.moveDown(), this, Movement.DOWN , this.cost + 5,this.search_mode));
            result.add(new Node(board.moveUp(), this, Movement.UP , this.cost + 7,this.search_mode));
            return result;
        }
        else{
            ArrayList<Node> result = new ArrayList<Node>();
            result.add(new Node(board.moveLeft(), this, Movement.LEFT,this.cost + 1,this.search_mode));
            result.add(new Node(board.moveRight(), this, Movement.RIGHT , this.cost + 1,this.search_mode));
            result.add(new Node(board.moveDown(), this, Movement.DOWN , this.cost + 1,this.search_mode));
            result.add(new Node(board.moveUp(), this, Movement.UP , this.cost + 1,this.search_mode));
            return result;
        }
    }

    public void drawState(boolean ucsFlag) {
        System.out.println("moved to : " + this.previousMovement);
        for (int i = 0; i < board.row; i++) {
            for (int j = 0; j < board.col; j++) {
                System.out.print(Constants.ANSI_BRIGHT_GREEN + board.cells[i][j] + spaceRequired(board.cells[i][j]));
            }
            System.out.println();
        }
        if (ucsFlag) {
            switch (this.previousMovement){
                case LEFT :
                    System.out.println("Cost of this move  :  1");
                    break;
                case RIGHT:
                    System.out.println("Cost of this move  :  3");
                    break;
                case UP :
                    System.out.println("Cost of this move  :  7");
                    break;
                case DOWN :
                    System.out.println("Cost of this move  :  5");
                    break;
            }

        }
        System.out.println("-----------------------------------------");
    }

    public boolean isGoal() {
        return board.isGoal();
    }

    public int pathCost() {
        return this.cost;
    }

    public int heuristic() {
        if(Board.mode==Constants.MODE_ADVANCE) {
            int minimum = 9999999;
            ArrayList<Integer> numbers = new ArrayList<Integer>();
            for (int i = 0; i < this.board.row; i++) {
                for (int j = 0; j < this.board.col; j++) {
                    minimum = Math.min(minimum,this.board.cells[i][j]);
                }
            }
            int x = 1;
            int result = 0;
            while (x < this.board.goalValue){
                result++;
                x*=2;
            }
            return result;
        }
        else{
            System.out.println("man injam");
            this.drawState(false);
            ArrayList<Integer> numbers = new ArrayList<Integer>();
            for (int i = 0; i < this.board.row; i++) {
                for (int j = 0; j < this.board.col; j++) {
                    numbers.add(this.board.cells[i][j]);
                }
            }
            Collections.sort(numbers);
            int sum = 0,x = 0;
            for (int i = numbers.size() - 1 , j = 1; i >= 0; i--,j++) {
                System.out.print(numbers.get(i) + " ");
                sum += numbers.get(i);
                if (sum >= this.board.goalValue) {
                    x = j;
                    break;
                }
            }
            int y = (int)Math.sqrt(x);
            System.out.println("this is x  : "+x + " and this is y : " + y);
            if (y * y == x){
                return 2*(y-1);
            }
            else if (Math.abs(x - (y*y)) < Math.abs(x - ((y+1)*(y+1)))  ){
                return (2*y) - 1;
            }
            else {
                return 2*y;
            }
        }
    }

    public String hash() {
        StringBuilder hash = new StringBuilder();
        hash.append(board.hash()).append("/PM=").append(previousMovement.toString());
        return hash.toString();
    }

    private String spaceRequired(int cell) {
        int length = String.valueOf(cell).length();
        String result = " ".repeat(5 - length);
        result += " ";
        return result;
    }

    public Node getParent() {
        return parent;
    }

    @Override
    public int compareTo(Node node) {
        if (node.search_mode == 0){
            // whatever
        }
        if (node.search_mode == 1){
            return node.pathCost() < this.pathCost() ? 1 : -1;
        }
        if (node.search_mode == 2){
            return (node.pathCost() + node.heuristic()) < (this.pathCost() + this.heuristic()) ? 1 : -1;
        }
       else {
            return node.heuristic() < this.heuristic() ? 1 : -1;
        }
    }
}
