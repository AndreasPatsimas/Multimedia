package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import domain.Video;

public class SocketServer {
	
	private static ServerSocket server;
	private static int port = 5000;
	private static Hashtable<Integer, Video> videos;
	private static boolean isRunning = true;

	public static void startServer(String videosArray []) throws IOException, ClassNotFoundException {
		videos = new Hashtable<Integer, Video>();
		fillInVideos(videosArray);
		//System.out.println(getVideosData());
		server = new ServerSocket(port);
		while (true) {
			System.out.println("Listening for client requests");
			Socket socket = server.accept();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			String msgReceived = (String) ois.readObject();
			System.out.println("Message Received: " + msgReceived);
			List<Video> msgReply = null;;
			String[] msgParts = msgReceived.split("#");
			if (msgParts[0].equals("LIST")) {
				msgReply = getVideosData();

			} 
			
			else if (msgParts[0].equals("PLAY")) {
				//msgReply = "EXIT_OK";
			}
			
			else if (msgParts[0].equals("EXIT")) {
				//msgReply = "EXIT_OK";
			}
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(msgReply);
			ois.close();
			oos.close();
			socket.close();
			if (msgReply.equals("EXIT_OK") || !isRunning)
				break;
		}
		System.out.println("Closing Socket server");
		server.close();
	}
	private static void fillInVideos(String videosArray []) {
		int id = 1;
		for(String video : videosArray) {
			String format = video.substring(video.length()-3);
			String name = video.substring(0, video.length() - 12);
			String bitratesStr = video.substring(video.length()-11);
			Double bitrates = Double.valueOf(bitratesStr.substring(0, bitratesStr.length()-8));
			videos.put(id, new Video(id, name, format, bitrates));
			id++;
		}
	}

	private static List<Video> getVideosData() {
		List<Video> data = new ArrayList();
		Set<Integer> setOfVideos = videos.keySet();
		Iterator<Integer> iterator = setOfVideos.iterator();
		while (iterator.hasNext()) {
			Integer key = iterator.next();
			data.add(videos.get(key));
		}
		return data;
	}

	public static void main(String args[]) {

//		try {
//			startServer();
//		} 
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} 
//		catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
