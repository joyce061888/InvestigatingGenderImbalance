import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents an object of type Movie.
 * A Movie object has a title, some Actors, and results for the twelve Bechdel testResultss.
 *
 * @author (Stella K., Joyce C., Candice X., Malahim T.)
 * @version (28 Nov 2021, 4:35pm; 04/29/2022)
 */
public class Movie implements Comparable<Movie>
{
    private String title;  //title of this movie
    private Hashtable<Actor, String> actors;  //collection of actors and their roles
    private LinkedList<String> names;  //list of all the actors in this movie
    private Vector<String> testResults;  //record of this movie's results for the 12 Bechdel tests
    
    //order on which the Movie was instanted; to break ties in priority queue
    private static int nextorder = 0; 
    private int order;
    /**
     * Constructor for objects of class Movie.
     * Initializes instance variables for this movie.
     * 
     * @param title of this movie
     */
    public Movie(String title) {
        this.title = title;
        actors = new Hashtable<Actor, String>();
        testResults = new Vector<String>();
        names = new LinkedList<String>();
        order = nextorder;
        nextorder++;
    }
    
    /**
     * Gets the order of this Movie
     * 
     * @return int of the order of this Movie 
     */
    public int getOrder() {
        return order;
    }

    /**
     * Gets the title of this Movie
     * 
     * @return the title of this Movie
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets this movie's actors (key) along with their 
     * roles (values) in a Hashtable
     * 
     * @return Hashtable with all the actors who played in this movie
     */
    public Hashtable<Actor, String> getAllActors() {
        return actors;
    }

    /**
     * Gets all the names of the actors in this movie 
     * in a Linked List.
     * 
     * @return LinkedList with the names of all the actors 
     * who played in this movie
     */
    public LinkedList<String> getActors() {
        return names;
    }

    /**
     * Returns a Vector with all the Bechdel test
     * results for this movie. A testResults result can be 
     * "1" or "0" indicating whether this move passed 
     * or not the corresponding testResults.
     * 
     * @return Vector with the Bechdel test results for this movie
     */
    public Vector<String> getAllTestResults() {
        return testResults;
    }

    /**
     * Populates the testResults vector with "0" and "1"s, 
     * each representing the result of the coresponding test 
     * on this movie. This information will be read from the file 
     * "nextBechdel_allTests.csv".
     * 
     * @param results  string consisting of of 0's and 1's. Each 
     * one of these values denotes the result of the corresponding 
     * test on this movie
     */
    public void setTestResults(String results) {
        String[] result = results.split(",");
        for(int n = 0; n < result.length; n++) {
            testResults.add(n, result[n]);
            //testResults.set(n, result[n]);  //replace current test results with given test results
        }
    }

    /**
     * Tests this movie object with the input one and determines whether they are equal.
     * 
     * @param other movie to compare to
     * 
     * @return true if both objects are movies and have the same title, 
     * false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Movie) {
            return this.title.equals(((Movie) other).title); // Need explicit (Movie) cast to use .title
        } else {
            return false;
        }
    }

    /**
     * Takes in a String, formatted as lines are in the input file 
     * ("nextBechdel_castGender.txt"), generates an Actor, and adds 
     * the object to the actors of this movie. 
     * 
     * Input String has the following formatting: "MOVIE", "ACTOR","CHARACTER_NAME",
     * "TYPE","BILLING","GENDER" 
     * 
     * Example of input: "Ricky Dillon","Aspen Heitz","Supporting","18","Male"
     * 
     * @param line of information about actor in this movie
     * 
     * @return Actor from inputted information
     */
    public Actor addOneActor(String line) {
        //clean the given line to have no quoations on tokens
        line = line.replaceAll("\"", "");
        
        String[] input = line.split(",");
        String name = input[1];
        String gender = input[5];
        String role = input[2];
        
        Actor a = new Actor(name, gender);  //create the Actor object with given info from line
        actors.put(a, role);  //add to hashtable of actors with their characters in this movie
        names.add(name);  //add to linked list of actors names in this movie
        return a;
    }
    
    /**
     * Reads the input file ("nextBechdel_castGender.txt"), and adds all its Actors 
     * to this movie. 
     * 
     * Each line in the movie has the following formatting: 
     * Input String has the following formatting: "MOVIE TITLE","ACTOR","CHARACTER_NAME",
     * "TYPE","BILLING","GENDER" 
     * 
     * Example of input: "Trolls","Ricky Dillon","Aspen Heitz","Supporting","18","Male"
     * 
     * @param actorsFile the file containing information on each actor who acted in the movie.
     */
    public void addAllActors(String actorsFile) {
        try {
            //read from file
            Scanner reader = new Scanner(new File(actorsFile));
            //line of info of actor in this movie
            String lineFromFile = ""; 
            //skip first line that has titles instead of info of actor
            reader.nextLine();
            while (reader.hasNextLine()) { //Continue until reach end of input file
                lineFromFile = reader.nextLine();
                //clean the given line to have no quoations on tokens
                lineFromFile = lineFromFile.replaceAll("\"", "");
                
                //get title 
                String[] infoLine = lineFromFile.split(",");
                String movieTitle = infoLine[0];
                //add the actor to this movie's list if the given line of information is from this movie 
                if(movieTitle.equals(this.title)) {
                    addOneActor(lineFromFile);
                }
            }
            reader.close(); // Close the file reader
        } catch (FileNotFoundException ex) {
            System.out.println(ex); // Handle FNF by displaying message
        }
    }

    /**
     * Returns a string representation of this movie for easier testing
     * 
     * @return a reasonable string representation of this movie: includes the title, 
     * the feminist score, and the number of actors who played in it.
     */
    public String toString() {
        String result = "Movie title: " + title 
        + ", Feminist Score: " + this.feministScore() 
        + ", Number of actors: ";
        if(this.getActors().isEmpty()) {
            result += "0";
        }
        else{
            result += this.getActors().size();
        }
        return result;
    }
    
    /**
     * Determines the feminist score of a Movie based on the combination 
     * of the Rees-Davies test, Villareal test,the Koeze-Dottle test, the Ko test,
     * the Peirce test, the Landau test, and the Feldman test.
     * 
     * The Movie will have a feminist score of ranging between 0 to 6.5 depending
     * on how many of the chosen alternative tests it passed.
     * 
     * @return feminist score of this movie based on 7 chosen alternative tests.
     */
    public double feministScore() {
        double score = 0.0;
        //indexes of each of the tests we chose to define movies as feminist or not
        int reesdaviesIndex = 12;
        int villarealIndex = 4;
        int koeze_dottleIndex = 9;
        int koIndex = 6;
        int peirceIndex = 1;
        int landauIndex = 2;
        int feldmanIndex = 3;
        if (testResults.get(reesdaviesIndex).equals("0")) {
            score += 1;
        }
        
        if (testResults.get(villarealIndex).equals("0")) {
            score += 1;
        }
        
        if (testResults.get(koeze_dottleIndex).equals("0")) {
            score += 1;
        }
        
        if (testResults.get(koIndex).equals("0")) {
            score += 1;
        }
        
        if (testResults.get(peirceIndex).equals("0")) {
            score += 1;
        }
        
        if (testResults.get(landauIndex).equals("0")) {
            score += 0.5;
        }
        
        if (testResults.get(feldmanIndex).equals("0")) {
            score += 1;
        }
        return score;
    } 
    
    /**
     * Compares this Movie to the other Movie (parameter) based
     * on their feminist scores that determines their priority. 
     * Uses the order in which the elements were added to break ties.
     * 
     * @param other Movie to compare to
     * 
     * @return int that determines if this Movie has a greater (positive #)
     * or less (negative #) priority than the other Movie
     */
    public int compareTo(Movie other) {
        if(this.feministScore() < other.feministScore()) {
            return -1;
        }
        else if(this.feministScore() > other.feministScore()) {
            return 1;
        }
        //if the Movies have the same priority
        //if this Movie was created & added after the other Movie
        else if(this.order > other.getOrder()){
            return -1;
        }
        //if this Movie was created & added before the other Movie
        else {
            return 1;
        }
    }

    /**
     * Main method for testResultsing.
     */
    public static void main(String[] args) {
        Movie m1 = new Movie("Boo! A Madea Halloween");
        Movie m2 = new Movie("Boo! A Madea Halloween");
        Movie m3 = new Movie("Sully");

        System.out.println("TESTING EQUALS() METHOD");
        System.out.println("EXPECTING TRUE: " + m1.equals(m2));  //true
        System.out.println("EXPECTING FALSE: " + m1.equals(m3));  //false 
        
        System.out.println();
        
        System.out.println("TESTING GETTITLE() METHOD");
        System.out.println(m1.getTitle()); //Boo! A Madea Halloween
        System.out.println(m3.getTitle()); //Sully

        System.out.println();

        System.out.println("ADDING ACTORS FROM MOVIE BOO! A MADEA HALLOWEEN");
        m1.addAllActors("nextBechdel_castGender.txt");
        System.out.println("HASHTABLE OF ACTORS: " + m1.getAllActors());
        System.out.println("LINKEDLIST OF ACTOR NAMES: " + m1.getActors());
        
        System.out.println();
        
        System.out.println("SETTING AND GETTING TEST RESULTS");
        m1.setTestResults("0,0,1,1,1,1,0,1,0,0,1,1,1");
        m3.setTestResults("1,1,0,1,0,1,1,1,1,0,1,1,1");
        System.out.println(m1.getAllTestResults());
        System.out.println(m3.getAllTestResults());
        
        System.out.println();
        
        System.out.println("TESTING TOSTRING() METHOD");
        System.out.println(m1);  //Movie title: Boo! A Madea Halloween, Number of actors: ##
        m3.addAllActors("nextBechdel_castGender.txt");
        System.out.println(m3);  //Movie title: Sully, Number of actors: ##

        System.out.println();
        
        System.out.println("TESTING ADDONEACTOR() METHOD");
        m2.addOneActor("'Central Intelligence','Sarah K. Thurber','Beautiful Restaurant Woman','Supporting','23','Female'");
        System.out.println("EXPECTING SARAH K. THURBER: " + m2.getActors());
        
        System.out.println();
        
        System.out.println("TESTING FEMINISTSCORE() METHOD: ");
        System.out.println("Boo Score (expecting 3.0): " + m1.feministScore());
        System.out.println("Sully Score (expecting 2.5): " + m3.feministScore());
        
        System.out.println();
        
        System.out.println("TESTING GETORDER() METHOD");
        System.out.println("Movie #1 was created first, expecting 0: " + m1.getOrder());
        System.out.println("Movie #3 was created third, expecting 2: " + m3.getOrder());
        
        System.out.println();
        
        System.out.println("TESTING COMPARETO() METHOD: expecting positive number");
        System.out.println(m1.compareTo(m3));
    }

}
