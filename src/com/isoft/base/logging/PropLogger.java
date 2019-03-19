package com.isoft.base.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PropLogger {
    
    private static FileHandler fh = null;
    public static void initLogger()
    {
        try {
             fh = new FileHandler("src/com/isoft/base/resources/logs/prop_val.log", true); //set path for log file
             } catch (SecurityException | IOException e) {
             e.printStackTrace();
             }
         Logger l = Logger.getLogger("");
         fh.setFormatter(new SimpleFormatter());
         l.addHandler(fh);
         l.setLevel(Level.CONFIG);
    }
    
    public static void closeHandler()
    {
        fh.close();
    }
    
}
