/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Rui
 */
public class TimeParse {
    
    /**
     * Receives a String wit minutes and seconds and return an int with time in seconds
     * @param time
     * @return 
     */
    public static int timeToInt(String time){
        String[] split = time.split(":");
        
        int minutes = Integer.parseInt(split[0]);
        int seconds = Integer.parseInt(split[1]);
        
        return minutes * 60 + seconds;
    }
    
    /**
     * Receives an int with time in seconds and creates a string in the format mm:ss
     * @param time
     * @return String
     */
    public static String timeToString(int time){
        int minutes = time/60;
        int seconds = time%60;
        
        return String.format("%02d:%02d", minutes, seconds);
    }
    
}
