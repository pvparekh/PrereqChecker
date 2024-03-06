package prereqchecker;
import java.util.*;


public class Digraph<T> {
    private T[] keys;
    private HashMap<T,ArrayList<T>> adjList;
    
    public void addKeys(T[] keys){
        this.keys = keys;
        adjList = new HashMap<>();
        for(T i : this.keys){
            adjList.put(i, new ArrayList<>());
        }
    }
    public void addEdge(T a, T b){
        adjList.get(a).add(b);

    }
    public ArrayList<T> getPreReqs(T key){
        return adjList.get(key);
    }

    public T[] getKeys(){
        return keys;
    }

    public ArrayList<T> BFS(T key){
        ArrayList<T> visited= new ArrayList<>();
        Queue<T> queue= new LinkedList<>();
       
        queue.add(key);
        
        while(!queue.isEmpty()){
            T temp= queue.poll();
            ArrayList<T> prereqs= getPreReqs(temp);
            if(prereqs!=null){
                for(T i:prereqs){
                    if(!visited.contains(i)){
                        queue.add(i);
                        visited.add(i);
                    }
                }
            }

        }

        return visited;
    }



}
