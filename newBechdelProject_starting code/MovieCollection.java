import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javafoundations.PriorityQueue;

/**
 * Write a description of class MovieCollection here.
 *
 * @author Candice, Joyce, Malahim
 * @version 05/02/2022
 */
public class MovieCollection 
{
    private LinkedList<Movie> allMovies;
    private LinkedList<Actor> allActors;
    private String testsFileName;
    private String castsFileName;

    /**
     * Constructor for objects of class MovieCollection
     */
    public MovieCollection(String testsFileName, String castsFileName)
    {
        allMovies = new LinkedList<Movie>();
        allActors = new LinkedList<Actor>();
        this.testsFileName = testsFileName;
        this.castsFileName = castsFileName;
    }

    /**
     * Returns all the movies in a LinkedList
     * 
     * @return a LinkedList with all the movies, each complete with its title, 
     * actors and Bechdel test results.
     */
    public LinkedList<Movie> getMovies() {
        return allMovies;
    }

    /**
     * Returns the titles of all movies in the collection
     * 
     * @return a LinkedList with the titles of all the movies
     */
    public LinkedList<String> getMovieTitles() {
        LinkedList<String> allTitles = new LinkedList<String>();
        for(int m=0; m < allMovies.size(); m++) {
            allTitles.add(allMovies.get(m).getTitle());
        }
        return allTitles;
    }

    /**
     * Returns all the Actors in the collection
     * 
     * @return a LinkedList with all the Actors, 
     * each complete with their name and gender.
     */
    public LinkedList<Actor> getActors() {
        return allActors;
    }

    /**
     * Returns the names of all actors in the collection
     * 
     * @return a LinkedList with the names of all actors
     */
    public LinkedList<String> getActorNames() {
        LinkedList<String> allNames = new LinkedList<String>();
        for(int n=0; n < allActors.size(); n++) {
            //for each Actor in this MovieCollection get their name and add to list
            allNames.add(allActors.get(n).getName());
        }
        return allNames;
    }

    /**
     * Returns a String representing this MovieCollection
     * 
     * @return a String representation of this collection, including the number 
     * of movies and the movies themselves.
     */
    public String toString() {
        String result = "Number of Movies: " + allMovies.size() + "\n";
        for(int i = 0; i < allMovies.size(); i++) {
            result += (allMovies.get(i)).toString();
            result += "\n";
        }
        return result;
    }

    /**
     * Reads the input file, and uses its first column (movie title) to 
     * create all movie objects. Adds the included information on the Bachdel 
     * test results to each movie. 
     */
    private void readMovies() {
        try {
            //read from file
            Scanner reader = new Scanner(new File(testsFileName));
            //line of info of actor in this movie
            String lineFromFile = ""; 
            //skip first line that has titles instead of info of actor
            reader.nextLine();
            while (reader.hasNext()) { //Continue until reach end of input file
                lineFromFile = reader.nextLine();

                //get index of first comma to separate title and testResults
                int index = lineFromFile.indexOf(",");

                //if the line of movie's info has test results
                if(index != -1) {
    
                    String title = lineFromFile.substring(0,index);
                    String results = lineFromFile.substring(index+1);
                    
                    //create a new movie 
                    Movie m = new Movie(title);
                    //set test results for that movie
                    m.setTestResults(results);

                    allMovies.add(m);
                }
            }
            reader.close(); // Close the file reader
        } catch (FileNotFoundException ex) {
            System.out.println(ex); // Handle FNF by displaying message
        }
    }

    //double check: how to add actor objects to allActors 
    /**
     * Reads the casts for each movie, from input casts file; 
     * 
     * Assume lines in this file are formatted as followes: "MOVIE","ACTOR","CHARACTER_NAME",
     * "TYPE","BILLING","GENDER" 
     * 
     * For example: "Trolls","Ricky Dillon","Aspen Heitz","Supporting","18","Male". 
     * 
     * Notes: 
     * 1) each movie will appear in (potentially) many consecutive lines, one line per actor. 
     * 2) Each token (title, actor name, etc) appears in double quotes, which need to be 
     *    removed as soon as the tokes are read. 
     * 3) If a movie does not have any test results, it is ignored and not included in the collection. 
     *    (There is actually one such movie)
     */
    private void readCasts() {
        //for all movies in this collection with test results
        for(int m = 0; m < allMovies.size(); m++) {
            Movie movie = allMovies.get(m);
            //add all actors in the movie we're reading
            movie.addAllActors(castsFileName);
            Hashtable<Actor, String> h = movie.getAllActors();
            //all the Actors (keys) from hashtable
            Enumeration<Actor> e = h.keys();
            while(e.hasMoreElements()) {
                Actor a = e.nextElement();
                //put all the Actors not in allActors list YET into allActors in all the movies in this collection (actors may be in multiple movies)
                if(!allActors.contains(a)) {
                    allActors.add(a);
                }
            }
        }
    }

    /**
     * Returns a list of all Movies that pass the n-th Bechdel test
     * 
     * @param n - integer identifying the n-th test in the list of 12 Bechdel 
     * alternatives, starting from zero
     * 
     * @return a list of all Movies which have passed the n-th test
     */
    public LinkedList<Movie> findAllMoviesPassedTestNum(int n) {
        LinkedList<Movie> passed = new LinkedList<Movie>();
        for(int m = 0; m < allMovies.size(); m++){
            //get the movie's list of test results in the list of all movies in this collection
            Vector results = allMovies.get(m).getAllTestResults();
            //check that movie's testResults and if it passed the nth test(is equal to zero), add to list
            if(results.get(n).equals("0")) {
                passed.add(allMovies.get(m));
            }
        }
        return passed;
    }
    
    /**
     * Returns a list of all Movies that passes the Peirce or Landau test.
     * 
     * @return LinkedList of all the movies that passes the Peirce or Landau test.
     */
    public LinkedList<Movie> findAllMoviesPassedTestPeirceOrLandau() {
        LinkedList<Movie> passed = new LinkedList<Movie>();
        int peirceIndex = 1;
        int landauIndex = 2;
        for(int m = 0; m < allMovies.size(); m++){
            Vector results = allMovies.get(m).getAllTestResults();
            //check that movie's testResults and if it passed the nth test, add to list
            if(results.get(peirceIndex).equals("0") || results.get(landauIndex).equals("0")) {
                passed.add(allMovies.get(m));
            }
        }
        return passed;
    }
    
    /**
     * Returns a list of all Movies that passes the White test but not the
     * Rees Davies test.
     * 
     * @return LinkedList of all the movies that passes the White test but not the
     * Rees Davies test.
     */
    public LinkedList<Movie> findAllMoviesPassedTestWhiteNotReesDavies() {
        LinkedList<Movie> passed = new LinkedList<Movie>();
        int whiteIndex = 11;
        int reesdaviesIndex = 12;
        for(int m = 0; m < allMovies.size(); m++){
            Vector results = allMovies.get(m).getAllTestResults();
            //check that movie's testResults and if it passed the nth test, add to list
            if(results.get(whiteIndex).equals("0") && results.get(reesdaviesIndex).equals("1")) {
                passed.add(allMovies.get(m));
            }
        }
        return passed;
    }
    
    /**
     * Returns a PriorityQueue of movies in the provided data based on their 
     * feminist score. That is, if you enqueue all the movies, they will be 
     * dequeued in priority order: from most feminist to least feminist. 
     * 
     * @return PriorityQueue of movies in the provided data based on their 
     * feminist score
     */ 
    public PriorityQueue<Movie> prioritizeMovies() {
        PriorityQueue<Movie> queue = new PriorityQueue<Movie>();
        for (int m = 0; m < allMovies.size(); m++) {
            queue.enqueue(allMovies.get(m));
        }
        return queue;
    }

    /**
     * Main method for testing purposes.
     */
    public static void main​(String[] args) {
        System.out.println("***TESTING MOVIECOLLECTION CLASS***");
        System.out.println("Creating a collection of movies; reading movies from file; reading casts from file");
        MovieCollection collection1 = new MovieCollection("nextBechdel_allTests.txt","nextBechdel_castGender.txt");
        collection1.readMovies();
        collection1.readCasts();
      
        System.out.println();
        
        System.out.println("***Testing getActors()***");
        System.out.println("All the actors from the data:\n" + collection1.getActors());
        
        System.out.println();
        
        System.out.println("***Testing getActorNames()***");
        System.out.println("All the actors' names from the data:\n" + collection1.getActorNames());
        
        System.out.println();
        
        System.out.println("***Testing getMovies()***");
        System.out.println("All the movies from the data:\n" + collection1.getMovies());
        
        System.out.println();
        
        System.out.println("***Testing getMovieTitles()***");
        System.out.println("All the movies' titles from the data:\n" + collection1.getMovieTitles());
        
        System.out.println();
        
        System.out.println("***Testing findAllMoviesPassedTestNum(n)***");
        System.out.println("Expected 32");
        System.out.println("Number of movies that passed Bechdel Test:  " + collection1.findAllMoviesPassedTestNum(0).size());
        System.out.println("Expected 0");
        System.out.println("Number of movies that passed Uphold Test:  " + collection1.findAllMoviesPassedTestNum(10).size());
        System.out.println("Expected 15");
        System.out.println("Number of movies that passed Rees Davis Test:  " + collection1.findAllMoviesPassedTestNum(12).size());
        System.out.println("Expected 0");
        System.out.println("Number of movies that passed White Test:  " + collection1.findAllMoviesPassedTestNum(11).size());
        System.out.println("Expected 5");
        System.out.println("Number of movies that passed Waithe Test:  " + collection1.findAllMoviesPassedTestNum(8).size());
        System.out.println("Expected 21");
        System.out.println("Number of movies that passed K0 Test:  " + collection1.findAllMoviesPassedTestNum(6).size());
        System.out.println("Expected 0");
        System.out.println("Number of movies that passed VILLAROBOS Test:  " + collection1.findAllMoviesPassedTestNum(7).size());
        System.out.println("Expected 40");
        System.out.println("Number of movies that passed PEIRCE Test:  " + collection1.findAllMoviesPassedTestNum(1).size());
        System.out.println("Expected 27");
        System.out.println("Number of movies that passed VILLARREAL Test:  " + collection1.findAllMoviesPassedTestNum(4).size());
        System.out.println("Expected 17");
        System.out.println("Number of movies that passed LANDAU Test:  " + collection1.findAllMoviesPassedTestNum(2).size());
        System.out.println("Expected 2 (3 that was supposed to pass did not in testing file)");
        System.out.println("Number of movies that passed HAGEN Test:  " + collection1.findAllMoviesPassedTestNum(5).size());
        System.out.println("Expected 17");
        System.out.println("Number of movies that passed KOEZE-DOTTLE Test:  " + collection1.findAllMoviesPassedTestNum(9).size());
        System.out.println("Expected 12");
        System.out.println("Number of movies that passed FELDMAN Test:  " + collection1.findAllMoviesPassedTestNum(3).size());
         
        System.out.println();
        
        System.out.println("***Testing findAllMoviesPassedTestPeirceOrLandau()***");
        System.out.println("Number of movies that passed the Peirce or Landau Test (expecting 47): " + collection1.findAllMoviesPassedTestPeirceOrLandau().size());
        System.out.println();
        
        System.out.println("***Testing findAllMoviesPassedTestWhiteNotReesDavies()***");
        System.out.println("Number of movies that passed the White and not ReesDavies Test (expecting 0): " + collection1.findAllMoviesPassedTestWhiteNotReesDavies().size());
        System.out.println();
        
        System.out.println();
    
        System.out.println("***Testing toString()***");
        System.out.println(collection1.toString());

        System.out.println();
        
        System.out.println("***Testing prioritizeMovies()***");
        System.out.println(collection1.prioritizeMovies());
    }
}
