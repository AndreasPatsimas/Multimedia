package director;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import server.SocketServerUi;

public class StreamingDirectorUi extends JFrame {

    private JButton directorProcessBtn;
    private JButton chooseFileBtn;

    public StreamingDirectorUi() {
        initComponents();
        setSize(400, 150);
    }

    private void initComponents() {

        chooseFileBtn = new JButton();
        directorProcessBtn = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Streaming Director");

        chooseFileBtn.setText("Choose File");
        chooseFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                chooseFileBtnActionPerformed(evt);
            }
        });

        directorProcessBtn.setText("Streaming Director Process");
        directorProcessBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	directorProcessBtnActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(chooseFileBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(directorProcessBtn, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(chooseFileBtn)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(directorProcessBtn)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void chooseFileBtnActionPerformed(ActionEvent evt) {                                              

    	JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File destDir = new File("C:\\ffmpeg\\bin\\raw_videos");
			File selectedFile = jfc.getSelectedFile();
			try {
				String fileExtension = FilenameUtils.getExtension(selectedFile.getName());
				if(fileExtension.equals("mkv") || fileExtension.equals("avi") || 
				   fileExtension.equals("mp4")) {
					
					FileUtils.copyFileToDirectory(selectedFile, destDir);
					
					infoBox("File " + selectedFile.getName() + " copied succesfully to path " + destDir.getAbsolutePath(), "Copy File");
				}
				else
					infoBox("File " + selectedFile.getName() + " was not copied. File's format must be mp4, avi, mkv", "Copy File");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    } 
    
    private void directorProcessBtnActionPerformed(ActionEvent evt) {
    	List<File> files = Arrays.asList(new File("C:\\ffmpeg\\bin\\raw_videos").listFiles());
    	
    	for(File file : files) {
    		
    		StreamingDirector.videoProcess(file, 200000, "mp4");
    		StreamingDirector.videoProcess(file, 500000, "mp4");
//    		StreamingDirector.videoProcess(file, 1000000, "mp4");
//    		StreamingDirector.videoProcess(file, 3000000, "mp4");
    		
    		StreamingDirector.videoProcess(file, 200000, "mkv");
    		StreamingDirector.videoProcess(file, 500000, "mkv");
//    		StreamingDirector.videoProcess(file, 1000000, "mkv");
//    		StreamingDirector.videoProcess(file, 3000000, "mkv");
    	}

        new SocketServerUi();

    	this.dispose();
    }
    
    private void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Info: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StreamingDirectorUi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(StreamingDirectorUi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(StreamingDirectorUi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(StreamingDirectorUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StreamingDirectorUi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(StreamingDirectorUi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(StreamingDirectorUi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(StreamingDirectorUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StreamingDirectorUi().setVisible(true);
            }
        });
    } 
}
