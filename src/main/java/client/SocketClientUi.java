package client;

import javax.swing.*;

import domain.Video;

import java.awt.EventQueue;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;


public class SocketClientUi extends JFrame {
	
	private JFrame frame;
	private JTextField bandwidth;
	private JRadioButton avi;
	private JRadioButton mp4;
	private JRadioButton mkv;
	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SocketClientUi window = new SocketClientUi();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SocketClientUi() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Form Client");
		frame.setBounds(100, 100, 730, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		bandwidth = new JTextField();
		bandwidth.setBounds(188, 30, 86, 17);
		frame.getContentPane().add(bandwidth);
		bandwidth.setColumns(10);
		
		JLabel lblName = new JLabel("Bandwidth (Mbps)");
		lblName.setBounds(65, 31, 106, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblBitwidth = new JLabel("Format");
		lblBitwidth.setBounds(65, 78, 46, 14);
		frame.getContentPane().add(lblBitwidth);
		
		JLabel lblAvi = new JLabel("mp4");
		lblAvi.setBounds(128, 78, 46, 14);
		frame.getContentPane().add(lblAvi);
		
		JLabel lblMp4 = new JLabel("avi");
		lblMp4.setBounds(292, 78, 46, 14);
		frame.getContentPane().add(lblMp4);
		
		JLabel lblMkv = new JLabel("mkv");
		lblMkv.setBounds(472, 78, 46, 14);
		frame.getContentPane().add(lblMkv);
		
		avi = new JRadioButton("");
		avi.setBounds(337, 74, 109, 23);
		frame.getContentPane().add(avi);
		
		mp4 = new JRadioButton("");
		mp4.setBounds(162, 74, 109, 23);
		frame.getContentPane().add(mp4);
		
		mkv = new JRadioButton("");
		mkv.setBounds(502, 74, 109, 23);
		frame.getContentPane().add(mkv);
		
		JLabel lblOccupation = new JLabel("Occupation");
		lblOccupation.setBounds(65, 128, 67, 14);
		frame.getContentPane().add(lblOccupation);
		
		comboBox = new JComboBox<String>();
		comboBox.addItem("Select");
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		comboBox.setBounds(180, 125, 200, 20);
		frame.getContentPane().add(comboBox);
		
		avi.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(avi.isSelected()) {
					mp4.setSelected(false);
					mkv.setSelected(false);
				}
			}
		});
		
		mp4.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(mp4.isSelected()) {
					avi.setSelected(false);
					mkv.setSelected(false);
				}
			}
		});
		
		mkv.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(mkv.isSelected()) {
					avi.setSelected(false);
					mp4.setSelected(false);
				}
			}
		});
		
		JButton btnSubmit = new JButton("Get List");
		btnSubmit.setBounds(65, 287, 89, 23);
		frame.getContentPane().add(btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Double.valueOf(bandwidth.getText());
					if(bandwidth.getText().isEmpty()||((!mp4.isSelected())&&(!avi.isSelected())&&(!mkv.isSelected())))
						JOptionPane.showMessageDialog(null, "Data Missing");
					else {		
						JOptionPane.showMessageDialog(null, "Data Submitted");
						
						comboBox.removeAllItems();
						comboBox.addItem("Select");
						
						double bitrate = Double.valueOf(bandwidth.getText());
						String format = null;
						if(mp4.isSelected())
							format = "mp4";
						else if(mkv.isSelected())
							format = "mkv";
						else if(avi.isSelected())
							format = "avi";
																		
						List<Video> output = SocketClient.getList(bitrate, format);
						for(Video video : output) {
							comboBox.addItem(video.getName() + "-" + video.getBitrates() + "Mbps." + video.getFormat());
						}
					}
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, "Bandwidth must be a number.");
				}
			}
		});
		
		JButton btnPlay = new JButton("Play Video");
		btnPlay.setBounds(185, 287, 99, 23);
		frame.getContentPane().add(btnPlay);
		
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedItem().equals("Select"))
					JOptionPane.showMessageDialog(null, "Data Missing");
				else {
					JOptionPane.showMessageDialog(null, "Data Submitted");
					String fileName = (String)comboBox.getSelectedItem();
					play(fileName);
				}
			}
		});
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(312, 287, 89, 23);
		frame.getContentPane().add(btnClear);
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bandwidth.setText(null);
				avi.setSelected(false);
				mp4.setSelected(false);
				mkv.setSelected(false);
				comboBox.setSelectedItem("Select");
			}
		});
		
	}
	
	private void play(String fileName) {
		try {
			
			 ProcessBuilder builder = new ProcessBuilder(
			            "cmd.exe", "/c", "cd \"C:\\ffmpeg\\bin\" && ffplay videos/" + fileName);
			        builder.redirectErrorStream(true);
			        Process p = builder.start();
			        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			        String line;
			        while (true) {
			            line = r.readLine();
			            if (line == null) { break; }
			            System.out.println(line);
			        }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
