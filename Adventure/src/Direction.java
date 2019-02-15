import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Direction {
    private Vector<String> validDirectionsVect = new Vector<>();
    private Vector<String> dir = new Vector<>();
    private Vector<String> dest = new Vector<>();
    private String [] validDirections = {"north", "south", "east" , "west", "northeast", "northwest" , "southeast",
            "southwest", "n", "s", "e" , "w", "ne" , "nw" , "se", "sw" };

    public Direction(String dirName){
        File filename = new File( dirName + "Dest.txt");

        try (Scanner sc = new Scanner(filename).useDelimiter(":")){
            String tmpString;
            int numDirection = sc.nextInt();
            for (int i = 0; i < numDirection; i++){
                tmpString = sc.next();
                validDirectionsVect.add(tmpString);
                validDirectionsVect.add(tmpString);
                tmpString = sc.next();
                dir.add(tmpString);
                tmpString = sc.next();
                dir.add(tmpString);
                tmpString = sc.next();
                dest.add(tmpString);
                dest.add(tmpString);
            }
        } catch (FileNotFoundException e) {
           System.out.println("File: "+ filename + " not found");
        }

    }
    public boolean canGodir (String way){
        boolean right_command = false;
        for (String acceptDirection : validDirections) {
            if (acceptDirection.contentEquals(way)) {
                right_command = true;
            }
        }
        int indexWay;
        if (dir.contains(way) && right_command){
            indexWay = dir.indexOf(way);
            return (validDirectionsVect.get(indexWay).contains("exit"))
                    || (validDirectionsVect.get(indexWay).contains("both"));
        }
        else {
            return false;
        }
    }
    public String goDirection (String way){

        return dest.get(dir.indexOf(way));
    }

}
