package storyEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StoryTeller {
    StoryLoader story;
    StoryTeller(String name){
        story.load(name);
    }
    String getAnswer(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String answer;
        try {
            System.out.println(">");
            answer = reader.readLine();
        }catch (IOException e){
            answer = "";
        }
        return answer;
    }
    boolean play(){
        String[] line;
        boolean dead = false;
        while((line=story.read().split(","))!=null&&!dead){
            int text = 0, correct = line.length - 1;
            System.out.println(line[0]);
            for(int i = 1;i<correct;i++){
                System.out.println(i+") "+line[i].split(":")[0]);
            }
            String answer = getAnswer();
            try {
                while (Integer.parseInt(answer) < 0 || Integer.parseInt(answer) >= correct) {
                    System.out.println("Please select a valid option.");
                    answer = getAnswer();
                }
            }catch(NumberFormatException e){
                System.out.println("Please enter an option number.");
            }
            if(answer!=line[correct]){
                dead = true;
                String deathMessage = line[Integer.parseInt(answer)].split(":")[1];
                System.out.println(deathMessage);
                System.out.println("THE END");
            }
        }
        if(dead) {
            return false;
        }else{
            return true;
        }
    }
}
