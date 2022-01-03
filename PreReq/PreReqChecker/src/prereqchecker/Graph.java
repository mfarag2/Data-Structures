package prereqchecker;
import java.util.*;

public class Graph {

    //private int v;
    //private int e; 

    private  HashMap<String, LinkedList<String>> map = new HashMap<>();
  
    public Graph (){
        this.map = new HashMap<>();
    }

    public HashMap<String, LinkedList<String>> returnMap(){
        return map;
    }

    public void makeAdjList(String inputFile){
        StdIn.setFile(inputFile);

        int vertices = StdIn.readInt(); 
        StdIn.readLine();

       //add vertices
        for(int i=0; i<vertices; i++){
            String current = StdIn.readLine();
            map.put(current, new LinkedList<String>());
        }

        //add edges
        int preReqs = StdIn.readInt(); 
        StdIn.readLine();
        
        for(int i=0; i<preReqs; i++){
            String current = StdIn.readLine();
            String[] tokens = current.split(" ");

            String course = tokens[0];
            String prereq = tokens[1];

            map.get(course).add(prereq);
        }

    }

    public HashSet<String> bfs (String s){
        Queue<String> q = new LinkedList<>();
        HashSet<String> marked = new HashSet<>();

        q.add(s);
        marked.add(s);

        while(!q.isEmpty()){
            String v = q.remove();
            LinkedList<String> ll = map.get(v);
            for(int x=0; x<ll.size(); x++){
                if(!marked.contains(ll.get(x)))
                    q.add(ll.get(x));
                    marked.add(ll.get(x));
                }
            }
          
        return marked;
    }

    public HashSet<String> bfsPreReqs (String s){
        Queue<String> q = new LinkedList<>();
        HashSet<String> marked = new HashSet<>();

        q.add(s);

        while(!q.isEmpty()){
            String v = q.remove();
            LinkedList<String> ll = map.get(v);
            for(int x=0; x<ll.size(); x++){
                if(!marked.contains(ll.get(x)))
                    q.add(ll.get(x));
                    marked.add(ll.get(x));
                }
            }
          
        return marked;

    }
 
    public String validPreReq(String inputFile){
        String answer = "NO";
        StdIn.setFile(inputFile);

        String course = StdIn.readLine();
        String prereq = StdIn.readLine();

        if(course.equals(prereq)){
            return answer; 
        }
       
       HashSet<String> set = bfsPreReqs(course);
       HashSet<String> preReqSet = bfsPreReqs(prereq);
       set.addAll(preReqSet);

       // if (set.contains(prereq))
        if(!(set.contains(course))){
            answer = "YES";
        }

        return answer; 
    }

    public String eligible (String inputFile){
        StdIn.setFile(inputFile);
        int numClasses = StdIn.readInt();
        StdIn.readLine();
        
        String output = "";
        HashSet<String> allClasses = new HashSet<String>();
        
        for(int i=0; i<numClasses; i++){
            HashSet<String> set = bfs(StdIn.readLine());
            allClasses.addAll(set);
        }
       
        for (String key : map.keySet()){
            LinkedList<String> current = map.get(key);
            if(allClasses.containsAll(current)&&!allClasses.contains(key)){
                output+=key+" ";
            }
        }
        return output;
    }

    public String needtoTake(String inputFile){
        StdIn.setFile(inputFile);
        String target = StdIn.readLine();
        int numClasses = StdIn.readInt();
        StdIn.readLine();

        String output = "";
        HashSet<String> allClasses = new HashSet<String>();
        HashSet<String> needToTake = bfs(target);

        for(int i=0; i<numClasses; i++){
            HashSet<String> set = bfs(StdIn.readLine());
            allClasses.addAll(set);
        }

        needToTake.removeAll(allClasses);
        needToTake.remove(target);
        for(String key : needToTake){
            output+=key+" ";
        }

        return output;
    }

    public HashSet<String> needToTake(HashSet<String> taken, String target){
        HashSet<String> needToTake = bfs(target);
        needToTake.removeAll(taken);
        needToTake.remove(target);
        return needToTake;
    }

    public HashSet<String> eligible(HashSet<String> set){
        HashSet<String> output = new HashSet<String>();
        for (String key : map.keySet()){
            LinkedList<String> current = map.get(key);
            if(set.containsAll(current)&&!set.contains(key)){
                output.add(key);
            }
        }
        return output;
    }

// given target and courses taken, find classes needed to be taken (need to take)
// then find next eligible courses with current courses taken
// add to hashmap courses that are needed + eligible, remove them from needed
// find next eligible courses with courses just added 

public HashMap<Integer, LinkedList<String>> schedulePlan(String inputFile){
    StdIn.setFile(inputFile);
    String target = StdIn.readLine();
    int numClasses = StdIn.readInt();
    StdIn.readLine();
    HashSet<String> classesTaken = new HashSet<String>();
    HashMap<Integer, LinkedList<String>> organizedSchedule = new HashMap<>();

    for(int i=0; i<numClasses; i++){
        HashSet<String> set = bfs(StdIn.readLine());
        classesTaken.addAll(set);
    }

    HashSet<String> needToTake = needToTake(classesTaken, target);
    HashSet<String> current = new HashSet<>();
    HashSet<String> eligibleToTake = new HashSet<>();
    int i=1;

    while(!needToTake.isEmpty()){
        current = needToTake(classesTaken, target);
        eligibleToTake = eligible(classesTaken);
        current.retainAll(eligibleToTake);
   
        organizedSchedule.put(i, new LinkedList<String>());
            for(String key : current){
                organizedSchedule.get(i).add(key);
            }
        needToTake.removeAll(current);
        classesTaken.addAll(current);
        i++;
    }
           
    return organizedSchedule;
    }
}







    

