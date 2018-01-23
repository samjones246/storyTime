package storyEngine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StoryLoader {
    private BufferedReader reader;
    public void load(String fileName){
        try {
            reader = new BufferedReader(new FileReader("stories/"+fileName));
        } catch (FileNotFoundException e) {
            System.err.println("Story does not exist!");
        }
    }
    public String read(){
        try{
            return reader.readLine();
        }catch (IOException e){
            System.err.println("Could not read line.");
            return null;
        }
    }

}
