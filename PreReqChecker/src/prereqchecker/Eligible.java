package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */

public class Eligible {
    private HashMap<String, List<String>> graph = new HashMap<>();
    private HashSet<String> completedCourses = new HashSet<>();

public static void main(String[] args) {
        if (args.length < 3) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }
        Eligible eligible = new Eligible();
        eligible.readAdjacencyList(args[0]);
        eligible.readCompletedCourses(args[1]);
        List<String> eligibleCourses = eligible.findEligibleCourses();
        eligible.writeOutput(args[2], eligibleCourses);
    }
    private void readAdjacencyList(String fileName) {
        StdIn.setFile(fileName);
        int numOfCourses = StdIn.readInt();
        for (int i = 0; i <= numOfCourses; i++) {
            StdIn.readLine(); // Skip the course names
        }

        int numOfPrerequisites = StdIn.readInt();
    
        for (int i = 0; i < numOfPrerequisites; i++) {
            String course = StdIn.readString();
            String prerequisite = StdIn.readString();
            
            graph.putIfAbsent(course, new ArrayList<>());
            graph.get(course).add(prerequisite);
        }
    }
    private void readCompletedCourses(String fileName) { 
        StdIn.setFile(fileName);
        int numOfCompletedCourses = StdIn.readInt();
        for (int i = 0; i <= numOfCompletedCourses; i++) {      
            String completedCourse = StdIn.readLine();
            addCourseAndPrerequisites(completedCourse);
        }
    }
    
    private void addCourseAndPrerequisites(String course) {
        if (!completedCourses.contains(course)) {
            completedCourses.add(course);
            List<String> prerequisites = graph.getOrDefault(course, new ArrayList<>());
            for (String prereq : prerequisites) {
                addCourseAndPrerequisites(prereq);
            }
        }
    }
    
private void dfs(String course, Set<String> visited, List<String> sortedCourses) {
    if (visited.contains(course)) return;
    visited.add(course);

    // Recursively visit all prerequisites of the current course
    for (String prereq : graph.getOrDefault(course, new ArrayList<>())) {
        dfs(prereq, visited, sortedCourses);
    }

    // Add course to sorted list after visiting all its prerequisites
    sortedCourses.add(course);
}

private List<String> topologicalSort() {
    Set<String> visited = new HashSet<>();
    List<String> sortedCourses = new ArrayList<>();

    for (String course : graph.keySet()) {
        if (!visited.contains(course)) {
            dfs(course, visited, sortedCourses);
        }
    }

    return sortedCourses;
}

private List<String> findEligibleCourses() {
    List<String> sortedCourses = topologicalSort();
    List<String> eligibleCourses = new ArrayList<>();

    for (String course : sortedCourses) {
        List<String> prerequisites = graph.getOrDefault(course, new ArrayList<>());
        if (!completedCourses.contains(course) && completedCourses.containsAll(prerequisites)) {
            eligibleCourses.add(course);
        }
    }

    return eligibleCourses;
}


private void writeOutput (String filename, List<String> eligibleCourses) {
    StdOut.setFile(filename);
    for( String course : eligibleCourses) { 
        StdOut.println(course);
    }
}
}