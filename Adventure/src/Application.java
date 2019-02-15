import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        boolean win = false;
        boolean lost = false;
        int i = 0;

        try (BufferedReader br = new BufferedReader(
                new FileReader("startGame.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Could not open file");
        }

        Gameplay quest = new Gameplay();
        System.out.print("> ");
        Scanner input = new Scanner(System.in);
        String argument = input.nextLine();
        Scanner scanAnswer = new Scanner(argument.toLowerCase()).useDelimiter(" ");
        String userAnswer = scanAnswer.next();


        while (!lost) {

            if ((userAnswer.contentEquals("quit")) ||
                    (userAnswer.contains("exit"))) {
                System.out.println("You have lost the game.");
                break;
            } else if (userAnswer.contentEquals("go")) {
                try {
                    userAnswer = scanAnswer.next();
                    quest.Go(userAnswer);
                } catch (NoSuchElementException e) {
                    System.out.println("Wrong Usage. 'go <direction>");
                }
            } else if (userAnswer.contentEquals("look")) {
                if (scanAnswer.hasNext()) {
                    userAnswer = scanAnswer.next();
                    quest.examineItem(userAnswer);
                } else
                    quest.Look();
            } else if (userAnswer.contentEquals("help")) {
                try (BufferedReader br = new BufferedReader(
                        new FileReader("help.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println("Could not open file");
                }
            } else if (userAnswer.contentEquals("inventory")) {
                quest.examineInventory();
            } else if (userAnswer.contentEquals("get")) {
                try {
                    userAnswer = scanAnswer.next();
                    quest.getItem(userAnswer);
                } catch (NoSuchElementException e) {
                    System.out.println("Wrong Usage. 'get <Object>");
                }
            } else if(userAnswer.contentEquals("use")){
                try{
                    userAnswer = scanAnswer.next();
                    quest.use(userAnswer);
                }catch (NoSuchElementException e){
                    System.out.println("Wrong Usage. 'use <Object>");
                }
            } else if ((userAnswer.contentEquals("kill")) ||
                    (userAnswer.contentEquals("burn")) ||
                    (userAnswer.contentEquals("open")) ||
                    (userAnswer.contentEquals("light")) ||
                    (userAnswer.contentEquals("untie"))){


                 if ((userAnswer.contentEquals("kill"))) {
                    try {
                        userAnswer = scanAnswer.next();
                        quest.kill(userAnswer);
                    } catch (NoSuchElementException e) {
                        System.out.println("Wrong Usage. 'kill <object>");
                    }
                } else if ((userAnswer.contentEquals("burn"))) {
                    try {
                        userAnswer = scanAnswer.next();
                        quest.burn(userAnswer);
                    } catch (NoSuchElementException e) {
                        System.out.println("Wrong Usage. 'burn <object>");
                    }
                } else if ((userAnswer.contentEquals("open"))) {
                    try {
                        userAnswer = scanAnswer.next();
                        quest.openItem(userAnswer);
                    } catch (NoSuchElementException e) {
                        System.out.println("Wrong Usage. 'open <object>");
                    }
                } else if ((userAnswer.contentEquals("light"))) {
                    try {
                        userAnswer = scanAnswer.next();
                        quest.lightTorch(userAnswer);
                    } catch (NoSuchElementException e) {
                        System.out.println("Wrong Usage. 'light <object>");
                    }
                } else if ((userAnswer.contentEquals("untie"))) {
                    try {
                        userAnswer = scanAnswer.next();
                        quest.untieJon(userAnswer);
                    } catch (NoSuchElementException e) {
                        System.out.println("Wrong Usage. 'untie <object>");
                    }
                }
            } else if (userAnswer.contentEquals("examine")) {
                try {
                    userAnswer = scanAnswer.next();
                    quest.examineItem(userAnswer);
                } catch (NoSuchElementException e) {
                    System.out.println("Wrong Usage. 'Examine <object>'");
                }
            } else if (userAnswer.contentEquals("remove")) {
                try {
                    userAnswer = scanAnswer.next();
                    quest.removeItem(userAnswer);
                } catch (NoSuchElementException e) {
                    System.out.println("Wrong Usage. 'remove <object>'");
                }
            } else if (userAnswer.contentEquals("items")) {
                quest.showItemsInRoom();
            } else if (i > 150) {
                System.out.println("You took too much time to kill the Ogre and save Georgie.");
                break;
            } else {
                System.out.println("Command not found. Type" +
                        " help " + "to get the list of commands ");
            }

            win = quest.won();
            lost = quest.lost();
            if (quest.getActionCount() == 3)
                lost = quest.lost();
            if (win) {
                break;
            }
            System.out.print("> ");
            input = new Scanner(System.in);
            argument = input.nextLine();
            scanAnswer = new Scanner(argument.toLowerCase()).useDelimiter(" ");
            userAnswer = scanAnswer.next();
            i++;


        }
        if (win) {
            System.out.print("You saved Georgie!");
        }
        if (lost) {
            System.out.print("You died and Georgie was eaten for dinner by the Ogre.");
        }
    }

}

