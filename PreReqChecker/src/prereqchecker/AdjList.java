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
    public Digraph<String> courseGraph;

    public void createAdjList(String inputFile, String outputFile){
        StdIn.setFile(inputFile);
        StdOut.setFile(outputFile);
        int numberOfCourses = StdIn.readInt();
        String[] courseIDs = new String[numberOfCourses];
        for(int i = 0; i < numberOfCourses; i++){
            courseIDs[i] = StdIn.readString();
        }
        courseGraph = new Digraph<String>();
        courseGraph.addKeys(courseIDs);
        int numberOfEdges = StdIn.readInt();
        for(int i = 0; i < numberOfEdges; i++){
            courseGraph.addEdge(StdIn.readString(), StdIn.readString());
        }

        for(String course : courseIDs){
            ArrayList<String> prerequisites = courseGraph.getPreReqs(course);
            StdOut.print(course + " ");
            for(String prereq : prerequisites){
                StdOut.print(prereq + " ");
            }
            StdOut.println();
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list Input file> <adjacency list Output file>");
            return;
        }

        AdjList adjListInstance = new AdjList();
        adjListInstance.createAdjList(args[0], args[1]);
    }
}
