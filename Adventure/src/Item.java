import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Item {
    private String itemName;
    private boolean movable;
    private String description;
    private boolean available;

    Item(String itemName){
        File filename = new File(itemName+".txt");
        try(Scanner s = new Scanner(filename).useDelimiter(":")){
            this.itemName = s.next();
            this.description = s.next();
            String str = s.next();
            this.movable = str.contains("movable");
            this.available = true;
        }catch(FileNotFoundException e){
            System.out.println("File: "+ filename + " not found");
        }

    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean isMovable() {
        return movable;
    }
    public String getName() {

        return this.itemName;
    }
    public boolean isAvailable() {
        return available;
    }
    public String getDescription() {

        return description;
    }

    public String toString(){
        return itemName;
    }

}
