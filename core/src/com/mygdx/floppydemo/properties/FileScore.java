package com.mygdx.floppydemo.properties;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;


public class FileScore {
    public static final String PATH_TO_PROPERTIES = "HighScore.txt";
    private String [] splitted;

    public FileScore() {
        readFile();
    }

    public void readFile(){
        try {
            Scanner sc = new Scanner(new File("HighScore.txt"));
            while(sc.hasNext()) {
                splitted = sc.nextLine().split(" ");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String s){
        try(FileWriter writer = new FileWriter("HighScore.txt", false))
        {
            writer.write(s);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

        public static void saveProperties(Properties p) throws IOException
    {

    }
    public String getRecord() {
        return splitted[2];
    }
}
