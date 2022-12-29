package chatting.application;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener{
    JTextField text;
    static JFrame f = new JFrame();
    static JPanel text_field;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    
    Client(){
        
        
        f.setLayout(null);
        // Header of Chat Box
        JPanel hedder_panel = new JPanel();
          hedder_panel.setBackground(Color.decode("#F7F7F7"));
          hedder_panel.setBounds(0,0,450,60);
          hedder_panel.setLayout(null);
        f.add(hedder_panel);
                        
        // Arrow Button
        ImageIcon back_Icon = new ImageIcon(ClassLoader.getSystemResource("icons\\arrow.png"));
        Image back_Icon1 = back_Icon.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT);
        ImageIcon back_Icon2 = new ImageIcon(back_Icon1);
        JLabel back = new JLabel(back_Icon2);
        back.setBounds(5,15,25,25);
        hedder_panel.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        
        // Profile Picture
        ImageIcon img_Icon = new ImageIcon(ClassLoader.getSystemResource("icons\\dp3.png"));
        Image img_Icon1 = img_Icon.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT);
        ImageIcon img_Icon2 = new ImageIcon(img_Icon1);
        JLabel profile = new JLabel(img_Icon2);
        profile.setBounds(35,5,50,50);
        hedder_panel.add(profile);
        
        // Video Call Icone
        ImageIcon vc_Icon = new ImageIcon(ClassLoader.getSystemResource("icons\\video.png"));
        Image vc_Icon1 = vc_Icon.getImage().getScaledInstance(35, 30,Image.SCALE_DEFAULT);
        ImageIcon vc_Icon2 = new ImageIcon(vc_Icon1);
        JLabel videoCall = new JLabel(vc_Icon2);
        videoCall.setBounds(320,12,35,30);
        hedder_panel.add(videoCall);
        
        // Call Icone
        ImageIcon call_Icon = new ImageIcon(ClassLoader.getSystemResource("icons\\phone.png"));
        Image call_Icon1 = call_Icon.getImage().getScaledInstance(30, 25,Image.SCALE_DEFAULT);
        ImageIcon call_Icon2 = new ImageIcon(call_Icon1);
        JLabel AudioCall = new JLabel(call_Icon2);
        AudioCall.setBounds(370,12,35,30);
        hedder_panel.add(AudioCall);
        
        // Call more option menu
        ImageIcon morevent = new ImageIcon(ClassLoader.getSystemResource("icons\\3icon.png"));
        Image morevent1 = morevent.getImage().getScaledInstance(20, 25,Image.SCALE_DEFAULT);
        ImageIcon morevent2 = new ImageIcon(morevent1);
        JLabel threedot = new JLabel(morevent2);
        threedot.setBounds(420,14,10,25);
        hedder_panel.add(threedot);
        
        // User name
        JLabel name = new JLabel("Beta");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.BLACK);
        name.setFont(new Font("SAF_SERIF",Font.BOLD,18));
        hedder_panel.add(name);
        
        // Activity Status
        ImageIcon active = new ImageIcon(ClassLoader.getSystemResource("icons\\check.png"));
        Image active1 = active.getImage().getScaledInstance(8,8,Image.SCALE_DEFAULT);
        ImageIcon active2 = new ImageIcon(active1);
        JLabel activeStatus = new JLabel(active2);
        activeStatus.setBounds(110,40,8,8);
        hedder_panel.add(activeStatus);
        
        JLabel status = new JLabel("Active");
        status.setBounds(125,35,100,18);
        status.setForeground(Color.BLACK);
        status.setFont(new Font("SAF_SERIF",Font.BOLD,12));
        hedder_panel.add(status);
        
        // Message field
        text_field = new JPanel();
        text_field.setBounds(5,65,440,580);
        f.add(text_field);
        
        // Message Typing Area
        text = new JTextField();
        text.setBounds(2,647,310,50);
        text.setFont(new Font("SAF_SERIF",Font.PLAIN,16));
        f.add(text);
        
        // Message Send Button
        JButton send = new JButton("Send");
        send.setBounds(315,647,123,50);
        send.setBackground(Color.decode("#54B435"));
        send.setForeground(Color.white);
        send.addActionListener(this);
        send.setFont(new Font("SAF_SERIF",Font.PLAIN,16));
        f.add(send);
        
        f.setSize(450,700);
        f.setLocation(800,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        
        f.setVisible(true);
    }
    
    // Action Perfornimg Area
     public void actionPerformed(ActionEvent ae){
        try{
            String  out = text.getText(); 
        
            JPanel p1 = formatLabel(out);
        
            text_field.setLayout(new BorderLayout());
        
            JPanel right =new JPanel(new BorderLayout());
            right.add(p1,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(5));
        
            text_field.add(vertical, BorderLayout.PAGE_START);
        
            dout.writeUTF(out);
        
            text.setText("");
        
            f.repaint();
            f.invalidate();
            f.validate();
        } catch(Exception e){
            e.printStackTrace();
        }
     }
     
     public static JPanel formatLabel(String out){
         JPanel panel = new JPanel();
         panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
         
         JLabel output = new JLabel("<html><p style= \" width: 100px\">"+out+"</p></html>");
         output.setFont(new Font("Tahoma",Font.PLAIN,16));
         output.setBackground(Color.decode("#54B435"));
         output.setOpaque(true);
         output.setBorder(new EmptyBorder(2,10,2,10));
         
         panel.add(output);
         
         Calendar cal = Calendar.getInstance();
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
         
         JLabel time = new JLabel();
         time.setText(sdf.format(cal.getTime()));
         
         panel.add(time);
         
         return panel;
     }
    
    public static void main(String[] args){
        new Client();
        
        // Creacting Plug Client side
        try{
            Socket s = new Socket("127.0.0.1",6001);
            
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            while(true){
                text_field.setLayout(new BorderLayout ());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                
                vertical.add(Box.createVerticalStrut(15));
                text_field.add(vertical, BorderLayout.PAGE_START);
                
                f.validate();
            }
            
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
