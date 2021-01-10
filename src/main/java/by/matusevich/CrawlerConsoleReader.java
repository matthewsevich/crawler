package by.matusevich;

import java.util.Scanner;

public class CrawlerConsoleReader {

    public static String[] readTerms(){
        System.out.println("Enter terms to search divided by comma, please!");
        Scanner scanner= new Scanner(System.in);
        String terms = scanner.nextLine();
        return terms.split(",");
    }

    public static String readUrl(){
        System.out.println("Enter Url to parse, please!");
        Scanner scanner= new Scanner(System.in);
        return scanner.nextLine();
    }
}
