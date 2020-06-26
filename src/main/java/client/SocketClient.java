package client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.Video;

public class SocketClient {
		
	private static Socket socket = null;
	private static ObjectOutputStream oos = null;
	private static ObjectInputStream ois = null;
	
	public static List<Video> getList(double bandwidth, String format) {
		
		try {
			
			socket = new Socket("127.0.0.1", 5000);
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			String msg = "";				
	
			oos.writeObject("LIST");
	
			oos.writeObject(msg);
			ois = new ObjectInputStream(socket.getInputStream());
			
			List<Video> videos = (List<Video>) ois.readObject();
			
			ois.close();
			oos.close();
			socket.close();
			
			List<Video> videosCriteria = new ArrayList();
			
			for(Video video : videos) {
				
				if(bandwidth >= video.getBitrates() && format.equals(video.getFormat()))
					videosCriteria.add(video);
			}
			
			return videosCriteria;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
