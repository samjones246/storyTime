package storyEngine;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

public class StoryGUI implements KeyListener{
    private JPanel panel1;
    private JTextPane textPane;
    private JLabel inputLabel;
    private JLabel errorLabel;
    private String inputAction;
    private int correct;
    private ArrayList<Integer> accepted;
    private boolean acceptInput = false;
    private String key;
    private File[] titles;
    private StoryTeller storyTeller;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Story Time");
        frame.setContentPane(new StoryGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        StoryGUI storyGUI = new StoryGUI();
    }
    StoryGUI(){
        textPane.addKeyListener(this);
        titles = new File("stories").listFiles();
        menu();
    }
    void menu(){
        cls();
        String[] menuText = {"---STORY TIME---", "Available Stories:"};
        accepted = new ArrayList<>();
        for(int i=0;i<menuText.length;i++){
            print(menuText[i]);
        }
        for(int i=0;i<titles.length;i++){
            String title = titles[i].getName();
            print("    "+(i+1)+") "+title.substring(0, title.length()-4));
            accepted.add(i+1);
        }
        acceptInput = true;
        inputAction = "LOAD";
    }
    void ending(int code){

    }
    void nextScene(){
        accepted = new ArrayList<>();
        cls();
        acceptInput = true;
        inputAction = "CHOICE";
        storyTeller.nextLine();
        String[] sceneText = storyTeller.getText();
        if(sceneText == null){
            menu();
        }
        else {
            for (String s : sceneText) {
                print(s);
            }
            correct = storyTeller.getCorrect();
            for (int i = 1; i <= storyTeller.getOptionCount(); i++) {
                accepted.add(i);
            }
        }
    }
    void startStory(int keyVal){
        storyTeller = new StoryTeller(titles[keyVal-1].getName());
        nextScene();
    }
    void choice(int keyVal){
        cls();
        if(keyVal == correct) {
            inputAction = "CONTINUE";
        }else{
            inputAction = "END";
        }
        acceptInput = false;
        print(storyTeller.getOutcome(keyVal));
    }
    void sendInput(){
        if(key.toLowerCase().equals("q")){
            System.exit(0);
        }else{
            int keyVal;
            try{
                keyVal = Integer.parseInt(key);
                boolean isAccepted = false;
                for(int i:accepted){
                    if(keyVal==i){
                        isAccepted = true;
                    }
                }
                if(isAccepted){
                    switch (inputAction){
                        case "LOAD":
                            startStory(keyVal);
                            break;
                        case "CHOICE":
                            choice(keyVal);
                            break;
                        case "CONTINUE":
                            nextScene();
                            break;
                        case "END":
                            menu();
                            break;
                    }
                }else{
                    //Invalid number error
                }
            }catch (NumberFormatException nfe){
                //Not a number error
            }
            key = "";
            inputLabel.setText("> ");
        }
    }
    void print(String s){
        String paneText = textPane.getText();
        textPane.setText(paneText+s+"\n");
    }
    void print(String s, boolean newLine){
        String paneText = textPane.getText();
        if(newLine){
            textPane.setText(paneText + s + "\n");
        }else{
            textPane.setText(paneText+s);
        }
    }
    void cls(){
        textPane.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(acceptInput) {
            if(e.getKeyChar() == '\n'){
                sendInput();
            }
            key = String.valueOf(e.getKeyChar());
            inputLabel.setText("> " + key);
        }else{
            if(inputAction=="CONTINUE"){
                nextScene();
            }else if(inputAction=="END"){
                menu();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
