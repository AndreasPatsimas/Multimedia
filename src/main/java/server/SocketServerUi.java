package server;

import javax.swing.*;

import domain.Video;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class SocketServerUi extends JFrame {
	
    //frame 
    static JFrame frame; 
      
    //lists 
    static JList<String> videoList;
    
    public SocketServerUi() {
    	initComponents();
    }
    
    private void initComponents() {
    	//create a new frame  
        frame = new JFrame("Video List"); 
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //create a object 
        //SocketServerUi s = new SocketServerUi(); 
        
        //create a panel 
        JPanel p = new JPanel(); 
          
        //create a new label 
        //JLabel l = new JLabel("select the day of the week"); 
  
        //String array to store weekdays 
        String videos [] = new File("C:\\ffmpeg\\bin\\videos").list();
          
        //create list 
        videoList = new JList(videos); 
          
        //set a selected index 
        //videoList.setSelectedIndex(2); 
          
        //add list to panel 
        p.add(videoList); 
   
        frame.add(p); 
          
        //set the size of frame 
        frame.setSize(300,400); 
        
        frame.show();
        
        try {
        	SocketServer.startServer(videos);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        
    }
	
    public static void main(String args[]) {

    	new SocketServerUi();
    }                  
}
