import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class application {

    public static void main(String[] args) {

        List<String> contactList = Arrays.asList("", "", "");
        Path filepath = Paths.get("data", "contacts.txt");
        Files.write(filepath, contactList);

        contactList Files.readAllLines(Path filepath);

    }



}
