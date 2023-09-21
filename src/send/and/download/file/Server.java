/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package send.and.download.file;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 *
 * @author Kavishka Heshan
 */
public class Server {
    
    static ArrayList<MyFile> myfiles = new ArrayList<>();
    
    public static void main(String[] args, Object fileid) throws IOException{
        int field = 0;
        
        JFrame jframe = new JFrame();
        jframe.setSize(400,400);
        jframe.setLayout(new BoxLayout(jframe.getContentPane(), BoxLayout.Y_AXIS));
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
        
        JScrollPane jsp = new JScrollPane(jpanel);
        jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
        
        JLabel jltitle = new JLabel("File Receiver");
        jltitle.setFont(new Font("Arial",Font.BOLD,25));
        jltitle.setBorder(new EmptyBorder(20,0,10,0));
        jltitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        jframe.add(jltitle);
        jframe.add(jsp);
        jframe.setVisible(true);
        
        ServerSocket serversocket =new ServerSocket(1234);
        
        while (true){
        
            try{
            
                Socket socket = serversocket.accept();
                
                DataInputStream datainputstream = new DataInputStream(socket.getInputStream());
                
                int filenamelength = datainputstream.readInt();
                
                if(filenamelength > 0){
                    byte[] filenamebytes = new byte[filenamelength];
                    datainputstream.readFully(filenamebytes, 0, filenamebytes.length);
                    String filename = new String(filenamebytes);
                    
                    int filecontentlength = datainputstream.readInt();
                    
                    if(filecontentlength > 0){
                        byte[] filecontentbytes = new byte[filecontentlength];
                        datainputstream.readFully(filecontentbytes, 0, filecontentlength);
                        
                        JPanel jpfilerow = new JPanel();
                        jpfilerow.setLayout(new BoxLayout(jpfilerow, BoxLayout.Y_AXIS));
                        
                        JLabel jlfilename = new JLabel(filename);
                        jlfilename.setFont(new Font("Arial",Font.BOLD,20));
                        jlfilename.setBorder(new EmptyBorder(10,0,10,0));
                        jlfilename.setAlignmentX(Component.CENTER_ALIGNMENT);
                        
                        if(getFileExtension(filename).equalsIgnoreCase(".txt")){
                            jpfilerow.setName(String.valueOf(fileid));
                            jpfilerow.addMouseListener(getMyMouseListener());
                            
                            jpfilerow.add(jlfilename);
                            jpanel.add(jpfilerow);
                            jframe.validate();
                        }else{
                            jpfilerow.setName(String.valueOf(fileid));
                            jpfilerow.addMouseListener(getMyMouseListener());
                            
                            jpfilerow.add(jlfilename);
                            jpanel.add(jpfilerow);
                            
                            jframe.validate();
                        }
                        myfiles.add(new MyFile((int) fileid,filename,filecontentbytes, getFileExtension(filename)));
                      
                    }
                    
                   
                }
            }catch(IOException error){
                error.printStackTrace();
            }
        }
    }
    
    public static MouseListener getMyMouseListener(){
        return new MouseListener(){
        
        public void mouseClicked(MouseEvent e){
            JPanel jpanel = (JPanel) e.getSource();
            
            int fileid = Integer.parseInt(jpanel.getName());
            
            for(MyFile myfile: myfiles){
                JFrame jfpreview = createframe(myfile.getName(),myfile.getData(),myfile.getFileExtention());
                jfpreview.setVisible(true);
            }
        }
        
        
        public void mousePressed(MouseEvent e){
        
        }
        
       
        public void mouseReleased(MouseEvent e){
        
        }
        
        
        public void mouseEntered(MouseEvent e){
        
        }
        
        
        public void mouseExited(MouseEvent e){
        
        }
       
        };
    }
    
    public static JFrame createframe(String fileName, byte[] fileData, String fileExtension){
        JFrame jframe = new JFrame("File downloader");
        jframe.setSize(400,400);
        
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
        
        JLabel jltitle = new JLabel("File Downloader");
        jltitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        jltitle.setFont(new Font("Arial",Font.BOLD,25));
        jltitle.setBorder(new EmptyBorder(20,0,10,0));
        
        JLabel jlprompt = new JLabel("Are you sure you want to download" + fileName);
        jlprompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlprompt.setFont(new Font("Arial",Font.BOLD,20));
        jlprompt.setBorder(new EmptyBorder(20,0,10,0));
        
        JButton jbyes = new JButton("No");
        jbyes.setPreferredSize(new Dimension(150,75));
        jbyes.setFont(new Font("Arial",Font.BOLD,20));
        
        JButton jbno = new JButton("No");
        jbno.setPreferredSize(new Dimension(150,75));
        jbno.setFont(new Font("Arial",Font.BOLD,20));
        
        JLabel jlFileContent = new JLabel();
        jlFileContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel jpbuttons = new JPanel();
        jpbuttons.setBorder(new EmptyBorder(20,0,10,0));
        jpbuttons.add(jbyes);
        jpbuttons.add(jbno);
        
        if(fileExtension.equalsIgnoreCase("txt")){
            jlFileContent.setText("<html>"+ new String(fileData) +"</html>");
        }else{
            jlFileContent.setIcon(new ImageIcon(fileData));
    }
    
    jbyes.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        File fileToDownload = new File(fileName);
        
        try{
            FileOutputStream fileoutputstream = new FileOutputStream(fileToDownload);
            
            fileoutputstream.write(fileData);
            fileoutputstream.close();
            
            jframe.dispose();
        }catch(IOException error){
            error.printStackTrace();
        }
        }

            
    });

    jpanel.add(jltitle);
    jpanel.add(jlprompt);
    jpanel.add(jlFileContent);
    jpanel.add(jpbuttons);
    
    jframe.add(jpanel);
    
    return jframe;
    
    }
    
    public static String getFileExtension(String filename){
        int i = filename.lastIndexOf('.');
        
        if(i>0){
            return filename.substring(i+1);
        }else{
            return "No Extension found.";
        }
    } 
}

