import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/* 
 * Program that solves interval partitioning problem in greedy manner. 
 * It gets list of lecture times and schedules them to classrooms using as few classrooms as possible.
 */ 
public class Schedule_Interval {

    public static class Lecture  {
       
       public int start;
       public int end;
       public int room;
       
       public Lecture(int startTime, int endTime) {
           start = startTime;
           end = endTime;
           room = -1;
       }
       
       public int getStart() {
           return this.start;
       }
       public int getEnd() {
           return this.end;
       }
       @Override
       public String toString() {
           return start + " " + end;
       }
    }
    
    public static class Room {
        
        //End indicates when last lecture assigned to room finishes
        public int end;
        public int id;
        
        
        public Room(int x) {
            id = x;
            end = 0;
        }
    }
    
    public static class LectureComparator implements Comparator<Lecture> {
        
        @Override
        public int compare(Lecture a, Lecture b) {
            if (a.getStart() == b.getStart()) {
                return a.getEnd() - b.getEnd();
            }
            return a.getStart() - b.getStart();
        }
      
    }
    
    
    
    
    /* Lectures are read from txt file called input.txt and lines are in format "s e"
    /  where the values s and e are non-negative integers that tell the starting time s
    /  and ending time e for a single lecture
    */ 
    public static ArrayList<Lecture> readLectures() {
        
        ArrayList<Lecture> lectures = new ArrayList<>();
        
        try {
           BufferedReader in = new BufferedReader(new FileReader("input.txt"));
           String line;
           String[] tmp;
           while((line = in.readLine()) != null) {
               
               tmp = line.split(" ");
               
               int a = Integer.parseInt(tmp[0]);
               int b = Integer.parseInt(tmp[1]);
        
               Lecture x = new Lecture(a,b);
               
               lectures.add(x);
           }
           
        }
        catch(IOException e) {
	    System.out.println("input.txt not found");
	}
        //Lectures are sorted to ascending order in order of their ending times
        Collections.sort(lectures, new LectureComparator());
        return lectures;
    }
    
    public static void checkRooms(ArrayList<Room> r, Lecture l) {
        
        /* If some room that exists in ArrayList has ending time
        /  before lectures starting time, we can assign that lecture
        /  to that room.
        */ 
        for( int i = 0; i < r.size(); i++) {
            if(r.get(i).end <= l.start) {
                l.room = i;
                r.get(i).end = l.end;
                return; 
            }
        }
        //If all rooms were occupied, we need new room and give
        //it ending time of lecture l.
        Room huone = new Room(r.size()+1);
        huone.end = l.end;
        l.room = r.size();
        r.add(huone);
    }
    
    
    /**
     *  Program that gets list of lectures are assigns them to lowest
     *  possible number of classrooms without conflicting lecture times
     */
    public static void main(String[] args) {
       
       ArrayList<Room> huoneet;
       huoneet = new ArrayList<>();
       Room r = new Room(0);
       huoneet.add(r);
       
       ArrayList<Lecture> luennot = readLectures();
       
       //Each lecture is given to checkRooms to see if it can be assigned to
       //existing room or do we need new room.
       for (int i = 0; i < luennot.size(); i++) {
           checkRooms(huoneet, luennot.get(i) );
       }
       
       
       //Output is printed so that first number of used classrooms is printed
       System.out.println(huoneet.size());
       
       //Then each line after that is lectures assigned in that spesific room
       //ie. second line in output contains lectures in first classroom  
       for(int i = 0; i < huoneet.size();i++) {
           boolean first = true;
           for(int j = 0; j < luennot.size(); j++) { 
               if(luennot.get(j).room == i) {
                   if (first) {
                       System.out.print(luennot.get(j).start);
                       System.out.print("-");
                       System.out.print(luennot.get(j).end);
                       first = false;
                   }
                   else {
                       System.out.print(" ");
                       System.out.print(luennot.get(j).start);
                       System.out.print("-");
                       System.out.print(luennot.get(j).end);
                   }
               }
           }
           System.out.println();
       }
        
    }
    
}
