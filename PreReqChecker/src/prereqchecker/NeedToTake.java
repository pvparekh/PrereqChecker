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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    private String adjListOutput="adjList.out";
    private AdjList list= new AdjList();
    private Digraph<String> a;
    
    
    public void setUpFile(String inputFile){
        list.createAdjList(inputFile, adjListOutput);
        a=list.courseGraph;
    }
    public void needToTake(String input, String output){
        StdIn.setFile(input);
        StdOut.setFile(output);
        String courseToTake=StdIn.readLine();
        int numOfCourses= StdIn.readInt();
        StdIn.readLine();
        ArrayList<String> coursesImported= new ArrayList<>();
        ArrayList<String> coursesTaken= new ArrayList<>();
        ArrayList<String> coursesNeeded= new ArrayList<>();
        
       //read input file
        for(int i=0; i<numOfCourses;i++){
            coursesImported.add(StdIn.readLine());
            coursesTaken.add(coursesImported.get(i));
        }

        //adds all prereqs to courses taken
        for(String i: coursesImported){
            ArrayList<String> prereqs= a.BFS(i);
            for(String j:prereqs){
                if(!coursesTaken.contains(j))
                    coursesTaken.add(j);
            }

        }
        //checks what classes are taken
        ArrayList<String> prereqs= a.BFS(courseToTake);
        for(String i: prereqs){
            if(!coursesTaken.contains(i)){
                if(!coursesNeeded.contains(i)) coursesNeeded.add(i);
            }
        }

        //prints into file
        for(int i=0;i<coursesNeeded.size();i++){
            StdOut.println(coursesNeeded.get(i));
        }

    }
    public static void main(String[] args) {
        
        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

    // WRITE YOUR CODE HERE
    NeedToTake a= new NeedToTake();
    a.setUpFile(args[0]);
    a.needToTake(args[1],args[2]);
    }
}









