/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package send.and.download.file;

/**
 *
 * @author Kavishka Heshan
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;



public class client { 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
            final File[] fileToSend = new File[1];
            
            JFrame jframe = new JFrame("Client");
            jframe.setSize(450, 450);
            jframe.setLayout(new BoxLayout(jframe.getContentPane(),BoxLayout.Y_AXIS));
            jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JLabel jltitle = new JLabel("File Sender");
            jltitle.setFont(new Font("Arial",Font.BOLD,25));
            jltitle.setBorder(new EmptyBorder(20,0,10,0));
            jltitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel jlfilename= new JLabel("Choose a file to send:" );
            jlfilename.setFont(new Font("Arial",Font.BOLD,20));
            jltitle.setBorder(new EmptyBorder(50,0,0,0));
            jltitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JPanel jpbutton = new JPanel();
            jpbutton.setBorder(new EmptyBorder(75,0,10,0));
            
            JButton jbsendfile =new JButton("Send File");
            jbsendfile.setPreferredSize(new Dimension(150,75));
            jbsendfile.setFont(new Font("Arial",Font.BOLD,20));
            
            JButton jbchoosefile =new JButton("Choose File");
            jbchoosefile.setPreferredSize(new Dimension(150,75));
            jbchoosefile.setFont(new Font("Arial",Font.BOLD,20));
            
            jpbutton.add(jbsendfile);
            jpbutton.add(jbchoosefile);
            
            jbchoosefile.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e){
                JFileChooser jfilechooser = new JFileChooser();
                jfilechooser.setDialogTitle("Choose a file to send");
                
                if (jfilechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    fileToSend[0] = jfilechooser.getSelectedFile();
                    jlfilename.setText("The file you want to send is: " + fileToSend[0].getName());
                }
            }
            });
            
            jbsendfile.addActionListener(new ActionListener () {
            
            public void actionPerformed(ActionEvent e) {
                if (fileToSend[0] == null) {
                    jlfilename.setText("Please choose a file first.");
                }else{
                    try{
                    FileInputStream fileinputstream = new FileInputStream(fileToSend[0].getAbsolutePath());
                    Socket socket = new Socket("localhost",1234);
                    
                    DataOutputStream dataoutputstream = new DataOutputStream(socket.getOutputStream());
                    
                    String filename =fileToSend[0].getName();
                    byte[] filenamebytes = filename.getBytes();
                    
                    byte[] filecontentbytes = new byte[(int)fileToSend[0].length()];
                    fileinputstream.read(filecontentbytes);
                    
                    dataoutputstream.writeInt(filenamebytes.length);
                    dataoutputstream.write(filenamebytes.length);
                    
                    dataoutputstream.writeInt(filecontentbytes.length);
                    dataoutputstream.write(filecontentbytes.length);
                    }catch (IOException error){
                        error.printStackTrace(); 
                    }
                    }
            }
            });
            
            jframe.add(jltitle);
            jframe.add(jlfilename);
            jframe.add(jpbutton);
            jframe.setVisible(true);
    }
    
}
