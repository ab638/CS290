import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Gameplay {
    private String[] room = {"r1", "r2", "r3", "r4", "r5", "r6"};
    private Vector<Item> inventory = new Vector<>();
    private Vector<Room> localEnv = new Vector<>();
    private Vector<Direction> wayPoint = new Vector<>();
    private Map<String, String> keyRing = new HashMap<>();
    private boolean win = false;
    private boolean lose = false;
    private boolean untie_jon = false;
    private Room currentRoom;
    private int actionCount = 0;
    private boolean leverPulled = false;

    public Gameplay() {
        Room tmpRoom;
        Direction tmpDirection;
        for (String someRoom : room) {
            tmpRoom = new Room(someRoom);
            tmpDirection = new Direction(someRoom);
            localEnv.add(tmpRoom);
            wayPoint.add(tmpDirection);
        }
        currentRoom = localEnv.get(0);
        currentRoom.setOpen(false);
        currentRoom.setFirstTime(true);
        System.out.println(currentRoom.getRoomName() + "\n" +
                currentRoom.getLongDescription());
        keyholderMaker();
    }

    public void use(String userAnswer) {
        Vector<Item> tmpItem = currentRoom.getItems();
        boolean actionDone = false;

        boolean actionDone2 = false;
        try {
            Item item = new Item(userAnswer);
            if (userAnswer.equalsIgnoreCase("lever")) {
                for (int j = 0; j < tmpItem.size(); j++) {
                    if (tmpItem.get(j).getName().contentEquals(item.getName())) {
                        System.out.println("After pulling the lever, two walls shift and reveal passages to the southeast and northwest.");
                        tmpItem.get(j).setAvailable(true);
                        tmpItem.get(j).setMovable(true);
                        actionDone2 = true;
                        leverPulled = true;
                        currentRoom.setLookDescription("There are passages to the southeast and the northwest.");
                        tmpItem.remove(item);
                        currentRoom.setItems(tmpItem);
                    }
                }
                if (!actionDone2) {
                    System.out.println("No lever to pull.");
                } else {
                    tmpItem.get(tmpItem.indexOf(item)).setAvailable(false);
                    currentRoom.setItems(tmpItem);
                }
            } else if (userAnswer.equalsIgnoreCase("gold_coin")) {
                Item pedestal = new Item("pedestal");
                Item coin = new Item(userAnswer);
                int coinInd = getInventoryIndex(coin);
                for (int j = 0; j < tmpItem.size(); j++) {
                    if (tmpItem.get(j).getName().contentEquals(pedestal.getName())) {
                        System.out.println("After placing the coin, the pedestal opened up to reveal a bronze_key!");
                        tmpItem.get(j).setAvailable(true);
                        tmpItem.get(j).setMovable(true);
                        actionDone = true;
                        currentRoom.setLookDescription("This room would make Smaug proud. There doesn't seem to be " +
                                "anything more for you to do here");
                        Item bronze_key = new Item("bronze_key");
                        tmpItem.remove(pedestal);
                        inventory.remove(coinInd);
                        tmpItem.add(bronze_key);
                        tmpItem.get(tmpItem.indexOf(bronze_key)).setMovable(true);
                        tmpItem.get(tmpItem.indexOf(bronze_key)).setAvailable(true);
                        currentRoom.setItems(tmpItem);
                    }
                }
                if (!actionDone) {
                    System.out.println("No pedestal to use.");
                }
            }
        } catch (Exception e) {
            if (leverPulled)
                return;
            System.out.println("You cannot use that here.");
        }
    }

    public void Go(String command) {
        int roomIndex = localEnv.indexOf(currentRoom);
        String destination;
        Room temp;
        if (wayPoint.get(roomIndex).canGodir(command)) {
            destination = wayPoint.get(roomIndex).goDirection(command);
            for (Room r : localEnv) {
                if (r.getRoomName().contains(destination)) {
                    roomIndex = localEnv.indexOf(r);
                }
            }

        } else {
            System.out.println("The direction is wrong or doesn't exist");
            return;
        }

        temp = localEnv.get(roomIndex);
        if (!temp.isOpen()) {
            if (temp.isKey()) {
                if (temp.canOpen(inventory)) {
                    if (temp.isFinalRoom()) {
                        if (!temp.getItems().contains(new Item("axe")))
                            System.out.println("The keys have opened the chest");
                        currentRoom = temp;
                        changeCurrentRoom();
                    }
                } else {
                    System.out.println("The door is locked");
                }
            }
        }
        if (!temp.isFinalRoom()) {
            currentRoom = temp;
            changeCurrentRoom();
        } else {
            currentRoom = temp;
            changeCurrentRoom();
        }
    }

    public void Look() {
        if (currentRoom.getRoomName().equalsIgnoreCase("Ogre's Room"))
            actionCount++;
        System.out.println(currentRoom.getLookDescription());
    }

    public void examineItem(String userAnswer) {
        if (currentRoom.getRoomName().equalsIgnoreCase("Ogre's Room"))
            actionCount++;
        System.out.println(currentRoom.getItemDescription(userAnswer));
    }

    public void examineInventory() {
        StringBuilder itemList = new StringBuilder();
        if (currentRoom.getRoomName().equalsIgnoreCase("Ogre's Room"))
            actionCount++;
        for (Item item : inventory) {
            if (item.getName().contains("key"))
                itemList.append(item.getDescription()).append(" ");
            else
                itemList.append(item.getName()).append(" ");
        }
        if (itemList.length() == 0)
            System.out.println("The inventory is empty. Nothing to show.");
        else
            System.out.println(itemList);
    }

    public void getItem(String userAnswer) {
        if (currentRoom.getRoomName().equalsIgnoreCase("Ogre's Room"))
            actionCount++;
        String temp = userAnswer;
        userAnswer = getKeyFilename(userAnswer);
        if (userAnswer == null)
            userAnswer = temp;
        boolean found = false;
        String newDescription;
        try {
            Item item = new Item(userAnswer);
            if (currentRoom.containsItem(userAnswer)
                    && (currentRoom.itemMovable(userAnswer))) {
                if (inventory.size() != 7) {
                    inventory.add(item);
                    System.out.println("Item successfully put into inventory.");
                    currentRoom.removeItemFromRoom(userAnswer);
                    found = true;
                    newDescription = currentRoom.getLongDescription().replace(userAnswer, "something, but there is nothing now");
                    currentRoom.setLongDescription(newDescription);
                } else {
                    System.out.println("Item found in the room, but inventory is full. " + "Remove an item from your inventory and try to add again");
                    found = true;
                    currentRoom.getItems().get(currentRoom.getItems().indexOf(item)).setAvailable(true);
                }
            } else if (currentRoom.containsItem(userAnswer)
                    && !currentRoom.itemMovable(userAnswer)) {
                System.out.println("Item cannot be moved from the location.");
                found = true;
            }

            if (!found) {
                System.out.println("The item cannot be found in the room.");
            }

        } catch (Exception e) {
            System.out.println("Item not available to create, please spell correctly");
        }
    }

    public void removeItem(String userAnswer) {
        if (currentRoom.getRoomName().equalsIgnoreCase("Ogre's Room"))
            actionCount++;
        String temp = userAnswer;
        userAnswer = getKeyFilename(userAnswer);
        if (userAnswer == null)
            userAnswer = temp;
        int itemIndex = -1;
        Vector<Item> roomItems;
        int numItemsInRoom;

        for (Item item : inventory) {
            if (item.getName().contains(userAnswer)) {
                itemIndex = inventory.indexOf(item);
            }
        }
        if (itemIndex < 0) {
            System.out.println("The " + userAnswer + " was not found in inventory");
        } else {
            System.out.println("The " + userAnswer + " was successfully removed");
            roomItems = new Vector<>(currentRoom.getItems());
            numItemsInRoom = currentRoom.getNumRoomItems();
            roomItems.add(inventory.get(itemIndex));
            roomItems.get(roomItems.indexOf(inventory.get(itemIndex)))
                    .setMovable(true);
            numItemsInRoom += 1;
            inventory.remove(itemIndex);
            currentRoom.setItems(roomItems);
            currentRoom.setNumRoomItems(numItemsInRoom);
            currentRoom.setLongDescription(currentRoom.getLongDescription()
                    .concat(" There is a " + userAnswer + " laying there now."));
            if (currentRoom.isShortDescriptionTrue())
                currentRoom.setShortDescription(currentRoom.getShortDescription()
                        .concat(" There is a " + userAnswer + " laying there now."));
        }


    }

    public void kill(String userAnswer) {
        try {
            boolean actionDone = false;
            boolean axeFound = false;
            boolean ogreFound = false;
            Item axe = new Item("axe");
            Item ogre = new Item(userAnswer);
            if (actionCount < 3) {
                Vector<Item> tmpItem = currentRoom.getItems();
                for (int i = 0; i < inventory.size(); i++) {
                    if (inventory.get(i).getName().contentEquals(axe.getName())) {
                        axeFound = true;
                        for (int j = 0; j < tmpItem.size(); j++) {
                            if (tmpItem.get(j).getName().contentEquals(ogre.getName())) {
                                // tmpItem.remove(j);
                                System.out.println("You have killed the Ogre and saved Georgie!");
                                ogreFound = true;
                                actionDone = true;
                                win = true;
                            }
                        }
                    }
                }
                if (!actionDone && !ogreFound && !actionDone && !axeFound) {
                    System.out.println("Cannot kill the Ogre without an axe or maybe the Ogre is not here.");
                } else if (!actionDone && actionCount == 3) {
                    System.out.println("Cannot kill the Ogre and save Georgie without an axe and he has killed you.");
                    lose = true;
                }
            }
        } catch (Exception e) {
            System.out.println("This object cannot be found in this room.");
        }
    }

    public void burn(String userAnswer) {
        if (currentRoom.getRoomName().equalsIgnoreCase("Ogre's Room"))
            actionCount++;
        try {
            boolean actionDone = false;
            boolean woodDoorFound = false;
            Item torch = new Item("torch");
            Item door = new Item(userAnswer);
            Item woodKey = new Item("wooden_key");
            Vector<Item> tmpItem = currentRoom.getItems();
            for (Item Inv : inventory) {
                if (Inv.getName().contentEquals(torch.getName())) {
                    for (int j = 0; j < tmpItem.size(); j++) {
                        if (tmpItem.get(j).getName().contentEquals(door.getName())) {
                            tmpItem.remove(j);
                            System.out.println("Burning the door down has revealed a path and a wooden key");
                            woodDoorFound = true;

                        }
                    }
                }
            }
            for (int j = 0; j < tmpItem.size(); j++) {
                if ((tmpItem.get(j).getName().contentEquals(woodKey.getName()) && (woodDoorFound))) {
                    currentRoom.setItemAvailabilityTrue(tmpItem.get(j));
                    currentRoom.setItems(tmpItem);
                    currentRoom.setNumRoomItems(currentRoom.getNumRoomItems() - 1);
                    currentRoom.setLookDescription("Without the door, it is just a hall to the east leading to an Ogre's lair");
                    actionDone = true;
                }
            }

            if (!actionDone) {
                System.out.println("Cannot burn a wood door without a torch or maybe there is no wood door to burn.");
            }
        } catch (Exception e) {
            System.out.println("This object cannot be found in this room.");
        }
    }

    public void openItem(String userAnswer) {
        if (currentRoom.getRoomName().equalsIgnoreCase("Ogre's Room"))
            actionCount++;
        try {
            boolean actionDone = false;
            boolean chestFound = false;
            boolean foundWoodKey;
            boolean foundIronKey;
            boolean foundBronzeKey;
            Item wKey = new Item("wooden_key");
            Item iKey = new Item("iron_key");
            Item bKey = new Item("bronze_key");
            Item chest = new Item(userAnswer);
            Item axe = new Item("axe");
            Vector<Item> tmpItem = currentRoom.getItems();
            foundWoodKey = checkInventory(wKey);
            foundBronzeKey = checkInventory(bKey);
            foundIronKey = checkInventory(iKey);

            if (foundWoodKey && foundIronKey && foundBronzeKey) {


                for (int j = 0; j < tmpItem.size(); j++) {
                    if (tmpItem.get(j).getName().contentEquals(chest.getName())) {
                        actionDone = true;
                        System.out.println("The chest has revealed an Axe!");
                        tmpItem.add(axe);

                        tmpItem.get(tmpItem.indexOf(axe)).setAvailable(true);
                        tmpItem.get(tmpItem.indexOf(axe)).setMovable(true);
                        currentRoom.setItems(tmpItem);
                    }
                }
                int wInd = getInventoryIndex(wKey);
                inventory.remove(wInd);
                int bInd = getInventoryIndex(bKey);
                inventory.remove(bInd);
                int iInd = getInventoryIndex(iKey);
                inventory.remove(iInd);

            }
            if (!actionDone) {
                System.out.println("Cannot open the chest without all 3 keys");
            }
        } catch (Exception e) {
            System.out.println("This object cannot be found in this room.");
        }
    }

    public void lightTorch(String userAnswer) {
        if (currentRoom.getRoomName().equalsIgnoreCase("Ogre's Room"))
            actionCount++;
        try {
            boolean actionDone = false;
            Item torch = new Item(userAnswer);

            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i).getName().contentEquals(torch.getName())) {
                    System.out.println("The torch has been lit");
                    currentRoom.setLongDescription(currentRoom.getShortDescription());
                    actionDone = true;
                }
            }
            if (!actionDone) {
                System.out.println("You can only light a torch.");
            }
        } catch (Exception e) {
            System.out.println("That item is not in this room.");
        }
    }

    public void untieJon(String userAnswer) {
        if (currentRoom.getRoomName().equalsIgnoreCase("Ogre's Room"))
            actionCount++;
        if (userAnswer.equalsIgnoreCase("Jon")) {
            userAnswer = "Jon";
        }
        try {
            boolean actionDone = false;
            Item Jon = new Item(userAnswer);
            Item ironKey = new Item("iron_key");
            Item goldCoin = new Item("gold_coin");
            Vector<Item> tmpItem = currentRoom.getItems();
            for (int j = 0; j < tmpItem.size(); j++) {
                if (tmpItem.get(j).getName().contentEquals(Jon.getName())) {
                    System.out.println("Jon has been untied and dropped an iron key and a mysterious gold coin!.");
                    tmpItem.get(j).setAvailable(true);
                    tmpItem.get(j).setMovable(true);
                    actionDone = true;
                    untie_jon = true;
                    currentRoom.setLookDescription("After untying Jon, you notice that there is an exit to the south.");


                }
            }
            if (!actionDone) {
                System.out.println("Jon isn't here to untie");
            } else {
                tmpItem.add(ironKey);
                tmpItem.add(goldCoin);
                tmpItem.get(tmpItem.indexOf(ironKey)).setMovable(true);
                tmpItem.get(tmpItem.indexOf(ironKey)).setAvailable(true);
                tmpItem.get(tmpItem.indexOf(goldCoin)).setMovable(true);
                tmpItem.get(tmpItem.indexOf(goldCoin)).setAvailable(true);
                currentRoom.setItems(tmpItem);
                currentRoom.removeItemFromRoom(Jon.getName());
            }
        } catch (Exception e) {
            System.out.println("Jon cannot be found in this room.");
        }
    }

    public boolean lost() {
        return lose;
    }

    public boolean won() {
        return win;
    }

    private String getKeyFilename(String userName) {

        return keyRing.get(userName);
    }

    private void changeCurrentRoom() {
        if (currentRoom.isFirstTime()) {
            System.out.println(currentRoom.getRoomName());
            System.out.println(currentRoom.getLongDescription());
            currentRoom.setFirstTime(false);
            currentRoom.setOpen(false);
        } else {
            System.out.println(currentRoom.getRoomName());
            currentRoom.setOpen(true);
        }
    }

    private void keyholderMaker() {
        keyRing.put("wood", "wooden_key");
        keyRing.put("iron", "k2");
        keyRing.put("bronze", "k3");
    }

    private boolean checkInventory(Item item) {
        for (int i = 0; i < inventory.size(); i++)
            if (inventory.get(i).getName().contentEquals(item.getName()))
                return true;
        return false;
    }

    private int getInventoryIndex(Item item) {
        int itemIndex = -1;
        for (Item i : inventory) {
            if (i.getName().contains(item.getName())) {
                itemIndex = inventory.indexOf(i);
            }
        }

        return itemIndex;
    }

    public void showItemsInRoom() {
        StringBuffer s = new StringBuffer();
        for (Item item : currentRoom.getItems()) {
            s.append(item.getName()).append("\n");
        }
        System.out.print(s);
    }

    public int getActionCount() {
        return this.actionCount;
    }
}
