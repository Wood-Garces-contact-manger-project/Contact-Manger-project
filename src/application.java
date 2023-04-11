import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//import static java.nio.file.Files.readAllLines;

public class application {
    static Scanner scan = new Scanner(System.in);

    static Path filepath = Paths.get("data", "contacts.txt");

    static List<String> contactList;

    public static void search() throws IOException {
        System.out.println("Who are you looking for?:");
        scan.nextLine();
        String userSearch = scan.nextLine();
        List<String> matches = new ArrayList<>();
        for (int i = 0; i < contactList.size(); i++) {
            String cont = contactList.get(i).toLowerCase();
            if (cont.contains(userSearch.toLowerCase())) {
                matches.add(contactList.get(i));
            }
        }
        System.out.println(matches);

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
        for (int i = 0; i < contactList.size(); i++) {
            String cont = contactList.get(i).toLowerCase();
            if (cont.contains(userSearch.toLowerCase())) {
                matches.add(contactList.get(i));
            }
        }
        for (int i = 0; i < matches.size(); i++) {
            System.out.printf("Would you like to remove; %s? Y/N? \n", matches.get(i));
            String uInput = scan.nextLine();
            if (uInput.equalsIgnoreCase("y") || uInput.equalsIgnoreCase("yes")) {
                contactList.remove(matches.get(i));
                Files.write(filepath, contactList);
                refresh();
                System.out.println(contactList);
                break;
            } else {
            }
        }
        returnToMenu();
    }


    public static void refresh() {
        try {
            contactList = Files.readAllLines(filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void add(String contact) throws IOException {
        List<String> matches = new ArrayList<>();

            if (contactList.contains(contact.toLowerCase())) {

                    System.out.printf("There's already a contact named %s. Do you want to overwrite it? (Y/N)", contact);
                    String duplicateContact = scan.nextLine();
                    if (duplicateContact.equalsIgnoreCase("y") || duplicateContact.equalsIgnoreCase("yes")) {
                        List<String> newList = new ArrayList<>();

                        for (String line : contactList) {
                            if (line.contains(contact)) {
                                newList.add(contact);
                                continue;
                            }
                            newList.add(line);
                        }

                        Files.write(filepath, newList);
                    }
                }
             else {
                try {
                    Files.write(filepath, Arrays.asList(contact), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    }



    public static void returnToMenu() throws IOException {
        System.out.println("\n Would you like to see the menu again? Y/N?:");
        String uInput = scan.next();
        if (uInput.equalsIgnoreCase("y") || uInput.equalsIgnoreCase("yes")) {
            menu();
        }
    }

    public static void menu() throws IOException {
        System.out.println("1. View contacts.\n" +
                "2. Add a new contact.\n" +
                "3. Search a contact by name.\n" +
                "4. Delete an existing contact.\n" +
                "5. Exit.\n" +
                "Enter an option (1, 2, 3, 4 or 5):");
        int userInput = scan.nextInt();
        if (userInput == 1) {
            System.out.printf("\n Contacts: \n");

            for (int i = 0; i < contactList.size(); i++) {
                System.out.println(contactList.get(i));
            }
            returnToMenu();

        } else if (userInput == 2) {
            System.out.println("Enter contact information (Name and Phone Number:");
            scan.nextLine();
            String newContact = scan.nextLine();
            add(newContact);
            refresh();
            System.out.println(contactList);
            returnToMenu();

        } else if (userInput == 3) {
            search();

        } else if (userInput == 4) {
            System.out.println(contactList);
            remove();


        } else if (userInput == 5) {

        } else {
            System.out.println("Invalid option please try again");
            menu();
        }

    }

    public static void main(String[] args) throws IOException {
        refresh();
        menu();
    }
}
