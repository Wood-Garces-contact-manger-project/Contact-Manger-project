import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//import static java.nio.file.Files.readAllLines;

public class application {
    static Scanner scan = new Scanner(System.in);

    static Path namePath = Paths.get("data", "contactName.txt");
    static Path numberPath = Paths.get("data", "contactPhone.txt");

    static List<String> contactList;
    static List<String> contactNum;

    public static void refresh() {
        try {
            contactList = Files.readAllLines(namePath);
            contactNum = Files.readAllLines(numberPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void update() {
        System.out.printf("\n   Contacts: \n");

        for (int i = 0; i < contactList.size(); i++) {
            System.out.printf("%s %s\n", contactList.get(i), contactNum.get(i));
        }
    }

    public static void returnToMenu() throws IOException {
        System.out.println("\nWould you like to see the menu again? Y/N?:");
        String uInput = scan.next();
        if (uInput.equalsIgnoreCase("y") || uInput.equalsIgnoreCase("yes")) {
            menu();
        }
    }


    public static void menu() throws IOException {
        System.out.println("1. View contacts.\n" +
                "2. Add a new contact.\n" +
                "3. Search a contact by name or number.\n" +
                "4. Delete an existing contact.\n" +
                "5. Exit.\n" +
                "Enter an option (1, 2, 3, 4 or 5):");
        String userInput = scan.nextLine();
        if (userInput.equals("1")) {
            update();
            returnToMenu();

        } else if (userInput.equals("2")) {
            System.out.println("Enter contact Name");
            scan.nextLine();
            String newContact = scan.nextLine();
            System.out.println("Enter contact Number");
            String newNum = scan.nextLine();
            add(newContact, newNum);
            refresh();
            update();
            returnToMenu();

        } else if (userInput.equals("3")) {
            search();

        } else if (userInput.equals("4")) {
            update();
            remove();


        } else if (userInput.equals("5")) {

        } else {
            System.out.println("Invalid option please try again");
            menu();
        }

    }

    public static void add(String contact, String number) throws IOException {
        List<String> matches = new ArrayList<>();
        List<String> matchesNum = new ArrayList<>();
        List<Integer> indexToReplace = new ArrayList<>();
        String uInput = null;
        for (int i = 0; i < contactList.size(); i++) {
            String temp = contactList.get(i).toLowerCase();
            String tempNum = contactNum.get(i);
            if (temp.contains(contact.toLowerCase())) {
                matches.add(contactList.get(i));
                matchesNum.add(tempNum);
                indexToReplace.add(contactList.indexOf(contactList.get(i)));
            }
        }
        if (matches.size() >= 1) {
            List<String> newList = new ArrayList<>();
            List<String> newListNum = new ArrayList<>();
            int indexTR = contactList.size()+1;
            for (int i = 0; i < matches.size(); i++) {
                System.out.printf("Do you want to overwrite, %s %s? Y/N?:\n", matches.get(i), matchesNum.get(i));
                uInput = scan.nextLine();
                if (uInput.equalsIgnoreCase("y") || uInput.equalsIgnoreCase("yes")) {
                    indexTR = indexToReplace.get(i);
                    break;
                }
            }
            int counter = 0;
            for (int i = 0; i < contactList.size(); i++) {
                if (indexTR == i && counter < 1) {
                    newList.add(contact);
                    newListNum.add(number);
                    ++counter;
                    continue;
                }
                newListNum.add(contactNum.get(i));
                newList.add(contactList.get(i));
            }
            if(uInput.equalsIgnoreCase("y")|| uInput.equalsIgnoreCase("yes")){
                Files.write(namePath, newList);
                Files.write(numberPath, newListNum);
            }else{
                try {
                    Files.write(namePath, Arrays.asList(contact), StandardOpenOption.APPEND);
                    Files.write(numberPath, Arrays.asList(number), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    } else
            try {
                Files.write(namePath, Arrays.asList(contact), StandardOpenOption.APPEND);
                Files.write(numberPath, Arrays.asList(number), StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

}


    public static void search() throws IOException {
        System.out.println("Who are you looking for?:");
        scan.nextLine();
        String userSearch = scan.nextLine();
        List<String> matches = new ArrayList<>();
        List<String> matchesNum = new ArrayList<>();
        for (int i = 0; i < contactList.size(); i++) {
            String cont = contactList.get(i).toLowerCase();
            String num = contactNum.get(i);
            if (cont.contains(userSearch.toLowerCase()) || num.contains(userSearch)) {
                matches.add(contactList.get(i));
                matchesNum.add(contactNum.get(i));
            }
        }
        for (int i = 0; i < matches.size(); i++) {
            System.out.printf("%s %s\n", matches.get(i), matchesNum.get(i));
        }
        System.out.println("Would you like to search again? Y/N?:");
        String uInput = scan.next();
        if (uInput.equalsIgnoreCase("y") || uInput.equalsIgnoreCase("yes")) {
            search();
        } else {
            returnToMenu();
        }
    }

    public static void remove() throws IOException {

        System.out.println("Who would you like to remove?:");
        scan.nextLine();
        String userSearch = scan.nextLine();
        List<String> matches = new ArrayList<>();
        List<String> matchesNum = new ArrayList<>();
        List<Integer> indexToRemove = new ArrayList<>();
        int indexTR = contactList.size()+1;
        for (int i = 0; i < contactList.size(); i++) {
            String cont = contactList.get(i).toLowerCase();
            if (cont.contains(userSearch.toLowerCase())) {
                matches.add(contactList.get(i));
                matchesNum.add(contactNum.get(i));
                indexToRemove.add((i));
            }
        }
        for (int i = 0; i < matches.size(); i++) {
            System.out.printf("Would you like to remove; %s %s? Y/N?: \n", matches.get(i), matchesNum.get(i));
            String uInput = scan.nextLine();
            if (uInput.equalsIgnoreCase("y") || uInput.equalsIgnoreCase("yes")) {
                indexTR = indexToRemove.get(i);

                contactList.remove((indexTR));
                contactNum.remove((indexTR));

                Files.write(namePath, contactList);
                Files.write(numberPath, contactNum);
                refresh();
                update();
                break;
            } else {
            }
        }
        returnToMenu();
    }

    public static void main(String[] args) throws IOException {
        refresh();
        menu();
    }
}
