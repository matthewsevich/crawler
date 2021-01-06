package by.matusevich;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class QuizCrawler {

        public static void main(String[] args) {
            new QuizCrawler().exec();
        }

        public void exec() {
            BufferedReader reader = null;
            try {
                URL site = new URL("http://www.quizful.net/");
                reader = new BufferedReader(new InputStreamReader(site.openStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
            } catch (IOException ex) {
                //...
            } finally {
                try {
                    reader.close();
                } catch (IOException ex) {
                    //...
                }
            }

        }
    }

