package core;

import ai.*;
import model.Board;
import model.Node;

import java.util.Hashtable;
import java.util.Scanner;

import static model.Movement.NONE;

public class main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(" please enter the goal value : \n then enter rows and columns and your board");
        int goalValue = Integer.parseInt(sc.nextLine());
        String mn = sc.nextLine();
        int rows = Integer.parseInt(mn.split(" ")[0]);
        int columns = Integer.parseInt(mn.split(" ")[1]);

        String[][] board = new String[rows][columns];
        String[] lines = new String[rows];
        for (int i = 0; i < rows; i++) {
            lines[i] = sc.nextLine();
            String[] line = lines[i].split(" ");
            System.arraycopy(line, 0, board[i], 0, columns);
        }

        Mapper mapper = new Mapper();
        int[][] cells = mapper.createCells(board, rows, columns);
        Board gameBoard = mapper.createBoard(cells, goalValue, rows, columns);
        Board.mode = Constants.MODE_NORMAL;
        //System.out.println(gameBoard.toString());

        //Hashtable<Node, Boolean> hashtable = new Hashtable<>();
        Node start = new Node(gameBoard, null, NONE,0);

//        DFS dfs = new DFS();
//        dfs.search(start);

//        BFS bfs = new BFS();
//        bfs.search(start);

//        int estimatedCutoff = (rows + columns) * 2;
//        IDS ids = new IDS();
//        ids.search(start , estimatedCutoff);

        UCS ucs = new UCS();
        ucs.search(start);
    }
}
