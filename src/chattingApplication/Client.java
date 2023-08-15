package chattingApplication;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.text.*;
import javax.swing.border.*;



public class Client  implements ActionListener{
	
	JTextField text;
	static JPanel a1;
	static DataOutputStream dout;
	
	static JFrame f = new JFrame(); 
	
	static Box vertical = Box.createVerticalBox();
	Client(){
		
//		to remove header
		f.setUndecorated(true);
		
		
//		to set Green Header
		f.setLayout(null);
		JPanel p1  = new JPanel();
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0, 0, 450, 70);
		p1.setLayout(null);
		f.add(p1);
		
//		Back arrow icon
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel back = new JLabel(i3);
		back.setBounds(5, 20, 25, 25);
		p1.add(back);
		
		
//		exiting program on clicking back arrow icon
		back.addMouseListener(
				new MouseAdapter()
				{
					public void mouseClicked(MouseEvent ae) 
					{
						System.exit(0);
					}
				}
			);
		
//		Adding Profile pic
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
		Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(40, 10, 50, 50);
		p1.add(profile);
		
		
		
//		Adding video icon
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel video = new JLabel(i9);
		video.setBounds(300, 20, 30, 30);
		p1.add(video);
		
		
//		adding dialer icon
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
		Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel phone = new JLabel(i12);
		phone.setBounds(360, 20, 35, 30);
		p1.add(phone);
		
		
//		adding three dots icon
		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
		Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel morevert = new JLabel(i15);
		morevert.setBounds(420, 20, 10, 25);
		p1.add(morevert);
		
		
//		Setting profile name
		JLabel name = new JLabel("Client");
		name.setBounds(110, 15, 100, 18);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
		p1.add(name);
		
		
//		Setting active status
		JLabel status = new JLabel("Active Now");
		status.setBounds(110, 35, 100, 18);
		status.setForeground(Color.WHITE);
		status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
		p1.add(status);
		
		
//		Adding chat panel
		a1 = new JPanel();
		a1.setBounds(5, 75, 440, 570);
		f.add(a1);
		
		
//		adding text field
		text = new JTextField();
		text.setBounds(5, 655, 310, 40);
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f.add(text);
		
		
//		adding send button
		JButton send = new JButton("Send");
		send.setBounds(320, 655, 123, 40);
		send.setForeground(Color.WHITE);
		send.addActionListener(this);  //performs click action
		send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		send.setBackground(new Color(7, 94, 94));
		f.add(send);
		
//		setting frame size
		f.setSize(450, 700);
		f.setLocation(800, 50);
		f.getContentPane().setBackground(Color.WHITE);
		
		
//		making frame visible
		f.setVisible(true);
	}
	
	
//	Overriding abstract method of ActionListener
	public void actionPerformed(ActionEvent ae) {
		try {
		String out = text.getText();
//		System.out.println(out);

//		JLabel output = new JLabel(out);
		JPanel p2 = formatLabel(out);
//		p2.add(out);
		
		a1.setLayout(new BorderLayout());
		
		JPanel right =  new JPanel(new BorderLayout());
		right.add(p2, BorderLayout.LINE_END);		//adding text box at right extreme
		vertical.add(right);
		vertical.add(Box.createVerticalStrut(15));	//vertical space between two messages
		
		
		a1.add(vertical, BorderLayout.PAGE_START);		//displaying messages from start of page
	
		dout.writeUTF(out);
		
		text.setText(" ");
		f.repaint();
		f.invalidate();
		f.validate();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
		
	
	public static JPanel formatLabel(String out) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel output = new JLabel("<html><p style=\"width:150px\">"+ out + "</p></html>");
		
		output.setFont((new Font("Tahoma", Font.PLAIN, 16)));
		output.setBackground(new Color(37, 211, 102));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder (15, 15, 15, 50));
		
		panel.add(output);
		
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		
		panel.add(time);
		return panel;
	}
	
	
	public static void main(String[] args) {
		new Client();
		
		
		try {
			Socket s = new Socket("127.0.0.1",3006 );
			DataInputStream din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			
			while(true) {
				a1.setLayout(new BorderLayout());
				
				String msg = din.readUTF();
				
				JPanel panel = formatLabel(msg);
				
				
				JPanel left = new JPanel(new BorderLayout());
				left.add(panel, BorderLayout.LINE_START);
				vertical.add(left);
				
				vertical.add(Box.createVerticalStrut(15));
				a1.add(vertical, BorderLayout.PAGE_START);
				f.validate();
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
