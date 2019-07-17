import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddressBook {

    String addressBookFile;
    List<HashMap<String, String>> addressArray = new ArrayList<>();
    String[] csvHead;

    public AddressBook(String file) {
        readFromFile(file);
    }

    private void readFromFile(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            csvHead = reader.readLine().split(","); // clears the top line from being read later

            addressArray = new ArrayList<>(); // clears it

            String line;
            int arrayPos = 0; // i couldnt loop this cos it depends on file length
            while ((line = reader.readLine()) != null) { // for each line
                HashMap<String, String> contact = new HashMap<>();

                String[] splitLine = line.split(",");
                for (int i = 0; i < splitLine.length; i++) { // each item in the line
                    contact.put(csvHead[i], splitLine[i]); // adds it to the key of csvHead and the value in the line
                }
                addressArray.add(contact);

                arrayPos++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void saveToFile(String file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < addressArray.size(); i++) { // for every contact
                String line = ""; // line that gets written

                for (int j = 0; j < csvHead.length; j++) { // for each key
                    line += addressArray.get(i).get(csvHead[j]); // adds the value
                    if (j != csvHead.length - 1) { // not at end
                        line += ",";
                    }
                }
                writer.write(line);
            }
            writer.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<HashMap<String, String>> searchByKeyValue(String key, String surname) {
        List<HashMap<String, String>> returnObj = new ArrayList<>();

        for (int i = 0; i < addressArray.size(); i++) {
            if(addressArray.get(i).get(key.toUpperCase()).toUpperCase().equals(surname.toUpperCase())) {
                returnObj.add(addressArray.get(i));
            }
        }

        return returnObj;
    }

    public void addContact(HashMap<String, String> contact) {
        addressArray.add(contact);
    }

    public static void main(String[] args) {
        AddressBook book = new AddressBook("data.csv");
        List<HashMap<String, String>> searchResults = book.searchByKeyValue("SURNAME", "HODGKINSON");
        System.out.println("Showing results for SURNAME == HODGKINSON");
        displayResults(searchResults);
        
    }
    
    public static void displayResults(List<HashMap<String, String>> results) {
        for (int i = 0; i < results.size(); i++) {
            System.out.println("+++++++++++++++++++++ ITEM " + i + " +++++++++++++++++++++");
            for (String key : results.get(i).keySet()) {
                System.out.println(key + ": " + results.get(i).get(key));
            }
            System.out.println("\n\n");
        }
    }
}


