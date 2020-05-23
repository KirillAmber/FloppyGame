package com.mygdx.floppydemo.properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class Record {
    private File file;
    Properties prop;
    FileInputStream fi;

    public Record() {
        file = new File("HighScore1.properties");
        prop = new Properties();
        try {
            loadProperties(prop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProperties(Properties p)throws IOException
    {
        FileInputStream fi=new FileInputStream(file);
        p.load(fi);
        fi.close();
    }

    public void saveProperties(Properties p) throws IOException
    {
        FileOutputStream fr = new FileOutputStream(file);
        p.store(fr, "HighScore1.properties");
        fr.close();
    }

    public void insertRecord(String highScore){
        prop.setProperty("highScore", highScore);
        try {
            saveProperties(prop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getRecord() {
        return prop;
    }
}

