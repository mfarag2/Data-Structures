package prereqchecker;
import java.util.*;


/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

        Graph g = new Graph();
        g.makeAdjList(args[0]);
        HashMap<String, LinkedList<String>> map = g.returnMap();

        StdOut.setFile(args[1]);
        for (String key : map.keySet()){
            StdOut.print(key+ " ");
            LinkedList<String> ll = map.get(key);
            for(int x=0; x<ll.size(); x++){
                StdOut.print(ll.get(x)+ " "); 
            }
            StdOut.println();
        }
    }
}
