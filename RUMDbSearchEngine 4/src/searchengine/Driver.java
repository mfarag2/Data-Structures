package searchengine;

import java.util.ArrayList;

public class Driver {
    
    public static void main (String[] args) {

		int hashTableSize = 20;
        double threshold = 8;
        String inputFile = "data.txt";
        String noiseWordsFile = "noisewords.txt";
        
        RUMDbSearchEngine rudb = new RUMDbSearchEngine(hashTableSize, threshold, noiseWordsFile);
		rudb.insertMoviesIntoHashTable(inputFile);
        rudb.print();
        

        String word1 = "man";
        String word2 = "son";

        ArrayList<MovieSearchResult> als = rudb.topTenSearch(word1, word2);
	
        if ( als != null && als.size() > 0 ) {
            
            StdOut.println("The shortest distance between " + word1 + " and " + word2 + " is located at:");
	
            for ( MovieSearchResult s : als ) {
                System.out.println(s.getTitle()+"\t["+s.getMinDistance()+"]"); 
            }
        } else {
            StdOut.println("There are no movies with the words " + word1 + " and " + word2 + " at their description.");
        }

        ArrayList<MovieSearchResult> x = rudb.createMovieSearchResult(word1, word2);

        if(x==null){
            System.out.println("null list");
        }
        else{
        for(int i=0; i<x.size(); i++){
            System.out.print("Title: "+x.get(i).getTitle());
            
            System.out.print(", List A: ");
           
            for(int j=0; j<x.get(i).getArrayListA().size();j++){
                System.out.print(x.get(i).getArrayListA().get(j));
            }
            System.out.print(", List B: ");
            for(int k=0; k<x.get(i).getArrayListB().size();k++){
                System.out.print(x.get(i).getArrayListB().get(k));
            }
            System.out.println();

        }
    }
}
        
    
    
}
        
     
    

