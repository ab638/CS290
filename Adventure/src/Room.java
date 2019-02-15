import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Room {
    private String roomName;
    private String shortDescription;
    private String lookDescription;
    private String longDescription;
    private Vector<Item> items;
    private Vector<Item> keyItem;
    private int numRoomItems;
    private boolean key;
    private boolean firstTime;
    private boolean open;
    private boolean finalRoom;
    private boolean sDescTrue = false;

    public Room(String roomName) {
        File filename = new File(roomName+".txt"); // filename of room
        int keyRequired;

        try (Scanner sc = new Scanner(filename).useDelimiter(":")){
            this.roomName = sc.next();
            this.longDescription = sc.next();
            this.lookDescription = this.longDescription;
            this.numRoomItems = sc.nextInt();
            this.items = new Vector<>();
            Item tmpItem;
            String tmpString;
            for (int i = 0; i < numRoomItems; i++) {
                tmpItem = new Item(sc.next());
                items.add(tmpItem); // add the items in the room to the collection
            }

            tmpString = sc.next();
            this.shortDescription = tmpString;
            tmpString = sc.next();
            if (tmpString.contains("key")) {
                this.key = true;
                keyRequired = sc.nextInt();
                for (int i = 0; i < keyRequired; i++) {
                    tmpItem = new Item(sc.next());
                    keyItem.add(tmpItem);
                }
            } else
                this.key = false;
            firstTime = true;
            tmpString = sc.next();
            finalRoom = tmpString.contains("yes");
            //open = !key;
        } catch(FileNotFoundException e){
            System.out.println("File: "+ filename + " not found");
        }


    }

    public String getRoomName() {
        return roomName;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = false;
    }
    public void setOpen(boolean open){
        this.open = open;
    }
    public void setNumRoomItems(int numRoomItems){
        this.numRoomItems = numRoomItems;
    }

    public void setShortDescription(String shortDescription) {
        sDescTrue = true;
        this.shortDescription = shortDescription;
    }

    public void setItems(Vector<Item> items) {
        this.items = items;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
    public boolean isKey(){
        return key;
    }
    public boolean isFirstTime(){
        return firstTime;
    }
    public boolean isOpen(){
        return open;
    }
    public boolean isFinalRoom(){
        return finalRoom;
    }

    public String getShortDescription() {
        if(isFirstTime() == true)
            return shortDescription;
        else
            return getRoomName();
    }

    public String getLongDescription() {
        return longDescription;
    }
    public String getLookDescription(){
        if(isShortDescriptionTrue())
            return longDescription;
        return lookDescription;}

    public boolean isShortDescriptionTrue(){
        return sDescTrue;
    }

    public Vector<Item> getItems() {
        return items;
    }
    public void setItemAvailabilityTrue(Item item){
        int i = -1;
        for(Item each : items){
            if(each.getName().contentEquals(item.getName())){
                i = items.indexOf(each);
            }
        }
        items.get(i).setAvailable(true);
        items.get(i).setMovable(true);
    }
    public void removeItemFromRoom(String itemName){
        int i = 0;
        for(Item each : items){
            if(each.getName().contentEquals(itemName))
                break;
            i++;
        }

        items.remove(i);
    }
    public boolean canOpen(Vector<Item> inventory){
        Vector<Item>  itemCopy = new Vector<>(keyItem);
        for(int i = 0; i < inventory.size(); i++){
            for(int j = 0; j < itemCopy.size(); j++){
                if(inventory.get(i).getName().contains(itemCopy.get(j).getName()))
                    itemCopy.remove(j);
            }
        }
        return itemCopy.isEmpty();
    }
    public boolean containsItem(String itemName){
        for(Item each : items){
            if(each.getName().contentEquals(itemName))
                return true;
        }
        return false;
    }
    public boolean itemMovable(String itemName){
        int i = 0;
        if(containsItem(itemName)){
            for(Item each : items){
                if(each.getName().contentEquals(itemName)){
                    break;
                }
                i++;
            }
            return items.get(i).isMovable();
        }
        else
            return false;
    }
    public Vector<Item> getKeyItem() {
        return keyItem;
    }

    public int getNumRoomItems() {
        return numRoomItems;
    }

    public String getItemDescription(String itemName){
        String itemDescription = "";
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getName().contains(itemName)) {
                itemDescription = items.get(i).getDescription();
                break;
            }
            else
                itemDescription = "Item not found";
        }
        return itemDescription;
    }

    public void setLookDescription(String description) {
        this.lookDescription = description;
    }
}
