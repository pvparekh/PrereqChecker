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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    private HashMap<String, List<String>> graph;


    public ValidPrereq() {
        graph = new HashMap<>();
    }


    private void readAdjacencyList(String fileName) {
        StdIn.setFile(fileName);
    
        // Read the number of courses, which we don't use directly, so just read and discard it
        int numOfCourses = StdIn.readInt();
        
        // Skip the course list as we only need the relationships
        for (int i = 0; i < numOfCourses+1; i++) {
            StdIn.readLine(); // Skip the course names
        }
        
        // Read the number of prerequisite connections
        int numOfPrerequisites = StdIn.readInt();
        
        // Read each prerequisite pair
        for (int i = 0; i < numOfPrerequisites; i++) {
            String course = StdIn.readString();
            String prerequisite = StdIn.readString();
            
            // Initialize the list for the course if it doesn't exist
            graph.putIfAbsent(course, new ArrayList<>());
            
            // Add the prerequisite to the course's adjacency list
            graph.get(course).add(prerequisite);
        }
    }
    
    


    private String[] readPrereqQuery(String fileName) {
        StdIn.setFile(fileName);
        String[] courses = new String[2];
        courses[0] = StdIn.readLine();
        courses[1] = StdIn.readLine();
        return courses;
    }


    private void addPrerequisite(String course1, String course2) {
        if (!graph.containsKey(course1)) {
            graph.put(course1, new ArrayList<>());
        }
        graph.get(course1).add(course2);
    }


    private boolean checkForCycle() {
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>();


        for (String course : graph.keySet()) {
            if (!visited.contains(course)) {
                if (isCyclicUtil(course, visited, recStack)) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean isCyclicUtil(String course, Set<String> visited, Set<String> recStack) {
        if (recStack.contains(course)) {
            return true;
        }
        if (visited.contains(course)) {
            return false;
        }


        visited.add(course);
        recStack.add(course);


        if (graph.containsKey(course)) {
            for (String prereq : graph.get(course)) {
                if (isCyclicUtil(prereq, visited, recStack)) {
                    return true;
                }
            }
        }


        recStack.remove(course);
        return false;
    }


    private void writeOutput(String fileName, String content) {
        StdOut.setFile(fileName);
        StdOut.println(content);
    }


    public static void main(String[] args) {
        if (args.length < 3) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }


        ValidPrereq checker = new ValidPrereq();


        checker.readAdjacencyList(args[0]);
        String[] prereqCourses = checker.readPrereqQuery(args[1]);
        checker.addPrerequisite(prereqCourses[0], prereqCourses[1]);


        boolean hasCycle = checker.checkForCycle();
        checker.writeOutput(args[2], hasCycle ? "NO" : "YES");
    }
}
