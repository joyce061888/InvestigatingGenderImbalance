/**
 * Represents an object of type Actor. An Actor has a name and a gender.
 *
 * @author (Stella K., Joyce, Candice, Malahim)
 * @version (28 Nov 2021, 4:35pm, May 2022)
 */
public class Actor
{
    private String name;
    private String gender;

    /**
     * Constructor for objects of class Actor.
     * 
     * @param name of this actor
     * @param gender of this actor
     */
    public Actor(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }
    
    /**
     * Returns the name of this actor
     * 
     * @return the name of this actor
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the gender of this actor
     * 
     * @return the gender of this actor
     */
    public String getGender() {
        return this.gender;
    }
    
    /**
     * Sets the name of this actor
     * 
     * @param n name of this actor
     */
    public void setName(String n) {
        this.name = n;
    }
    
    /**
     * Sets the gender of this actor
     * 
     * @param g gender of this actor
     */
    public void setGender(String g) {
        this.gender = g;
    }
    
    /**
     * This method is defined here because Actor (mutable) is used as a key in a Hashtable.
     * It makes sure that same Actors have always the same hash code.
     * So, the hash code of any object that is used as key in a hash table,
     * has to be produced on an *immutable* quantity,
     * like a String (such a string is the name of the actor in our case)
     * 
     * @return an integer, which is the has code for the name of the actor
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Tests this actor against the input one and determines whether they are equal.
     * Two actors are considered equal if they have the same name and gender.
     * 
     * @param other Actor to compare to
     * 
     * @return true if both objects are of type Actor, 
     * and have the same name and gender, false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Actor) {
            return this.name.equals(((Actor) other).name) && 
            this.gender.equals(((Actor) other).gender); // Need explicit (Actor) cast to use .name
        } else {
            return false;
        }
    }
    
    /**
     * Returns a string representation of this Actor.
     * 
     * @return a reasonable string representation of this actor, containing their name and gender.
     */
    public String toString() {
        return "Actor " + name + " is a " + gender;
    }
    
    /**
     * Main method for testing purposes.
     */
    public static void main(String[] args) {
        Actor a1 = new Actor("Charlie", "man");
        Actor a2 = new Actor("Charlie", "man");
        Actor a3 = new Actor("Charles", "woman");
        
        System.out.println(a1.getName());
        System.out.println(a1.getGender());
        
        a3.setName("Em");
        a3.setGender("man");
        System.out.println(a3);
        
        System.out.println(a1.equals(a2));
        System.out.println(a1.equals(a3));
    }
}
