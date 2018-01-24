package storyEngine;

public class StoryTeller {
    StoryLoader story;
    String[] line;
    StoryTeller(String name){
        story = new StoryLoader();
        story.load(name);
    }
    void nextLine(){
        String temp = story.read();
        if(temp!=null){
            line=temp.split(";");
        }else{
            line=null;
        }
    }
    int getOptionCount(){
        return line.length-2;
    }
    String[] getText(){
        if(line==null){
            return null;
        }
        String[] text = new String[line.length -1];
        text[0] = line[0];
        for(int i = 1;i<line.length-1;i++){
            text[i] = i + ") " + line[i].split(":")[0];
        }
        return text;
    }
    int getCorrect(){
        return Integer.parseInt(line[line.length-1]);
    }
    String getOutcome(int option){
        return line[option].split(":")[1];
    }
}
