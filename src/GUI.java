import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, ItemListener
{			
	private JLabel text1 = new JLabel("Wähle eine Datei.",SwingConstants.CENTER);
	private final JFileChooser fileselect = new JFileChooser();

	private JMenu menu = new JMenu("Wii-Optionen");
	private JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("Automatische Erfassung");

	private JMenuItem menuItem = new JMenuItem("Port ändern",
			KeyEvent.VK_T);
	private JMenuItem menuItem2 = new JMenuItem("Argumente setzen",
			KeyEvent.VK_T);
	private JMenuItem h1 = new JMenuItem("Standards wiederherstellen");
	private JMenuItem h2 = new JMenuItem("Online-Hilfe");
	private JMenuItem h3 = new JMenuItem("Über");	

	private JMenuItem browse = new JMenuItem("Öffnen...");
	private JButton send = new JButton("   Senden   ");
	private JButton button5= new JButton("Durchsuchen...");

	static JLabel filename = new JLabel("Keine Datei ausgewählt...",SwingConstants.LEFT);
	static TextField wiiip = new TextField("xxx.xxx.x.x");

	public GUI()
	{
		JMenu help = new JMenu("Hilfe");

		JFrame frame = new JFrame("jWiiload");

		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Datei");



		send.setEnabled(false);

		menuBar.add(file);
		menuBar.add(menu);
		menuBar.add(help);
		menu.add(cbMenuItem);
		menu.add(menuItem);

		help.add(h1);
		help.add(h2);
		help.add(h3);

		wiiip.setPreferredSize(new Dimension(10,1));

		file.add(browse);

		cbMenuItem.setMnemonic(KeyEvent.VK_C);

		if (JWiiLoad.autosend)
			cbMenuItem.setSelected(true);
		else
			cbMenuItem.setSelected(false);
		menu.add(menuItem2);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		text1.setPreferredSize(new Dimension(200, 20));

		Container content = frame.getContentPane();

		browse.addActionListener(this);
		h1.addActionListener(this);
		h2.addActionListener(this);
		h3.addActionListener(this);

		cbMenuItem.addActionListener(this);
		send.addActionListener(this);

		menuItem.addActionListener(this);

		menuItem2.addActionListener(this);

		button5.addActionListener(this);


		//		FlowLayout fl = new FlowLayout();
		GridBagLayout fl = new GridBagLayout();


		content.setLayout(fl);
		//
		//		content.add(text1);
		//		content.add(filename);
		//		content.add(button5);

		GridBagConstraints c = new GridBagConstraints();

		//		c.fill = GridBagConstraints.HORIZONTAL;
		//		c.weightx = 0;
		//		c.gridx = 0;
		//		c.gridy = 0;
		//		c.gridwidth=8;
		//		content.add(text1, c);

		c.fill = GridBagConstraints.WEST;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth=4;
		content.add(filename, c);

		c.fill = GridBagConstraints.EAST;
		c.weightx = 0.5;
		c.gridx = 4;
		c.gridy = 2;
		content.add(button5, c);

		c.fill = GridBagConstraints.WEST;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth=4;
		content.add(wiiip, c);

		c.fill = GridBagConstraints.EAST;
		c.weightx = 0.5;
		c.gridx = 4;
		c.gridy = 3;
		content.add(send, c);

		//		button = new JButton("Long-Named Button 4");
		//		c.fill = GridBagConstraints.HORIZONTAL;
		//		c.ipady = 40;      //make this component tall
		//		c.weightx = 0.0;
		//		c.gridwidth = 3;
		//		c.gridx = 0;
		//		c.gridy = 1;
		//		content.add(button, c);




		//		
		//		
		//		content.add(wiiip,c);
		//		content.add(send,c);


		frame.setSize(200,400);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		frame.setJMenuBar(menuBar);
		frame.pack();
		
		wiiip.setText(JWiiLoad.lastip);

	}

	public void setText(String s)
	{
		text1.setText(s);
	}

	public void setButton(boolean b)
	{
		send.setEnabled(b);
	}

	public File chooseFile()
	{
		fileselect.showOpenDialog(null);
		File filename = fileselect.getSelectedFile();
		
		return filename;
	}

	public int showLost()
	{
		String[] selections = {"Wiederholen","Stopp"};
		return JOptionPane.showOptionDialog(this,"Keine Wii gefunden.\nLäuft der Homebrewkanal?","Fehler",JOptionPane.ERROR_MESSAGE, 0, null,selections,null);

	}

	public int showRate()
	{
		String[] selections = {"Wiederholen","Stopp"};
		return JOptionPane.showOptionDialog(this,"Rate Limit überschritten.\nBitte warte etwas und versuche es dann noch einmal.","Fehler", JOptionPane.ERROR_MESSAGE, 0, null, selections, null);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == h1)
		{
			JWiiLoad.lastip = "0.0.0.0";
			JWiiLoad.autosend = true;	
			JWiiLoad.port = 4299;
			JWiiLoad.arguments = "";
			cbMenuItem.setSelected(true);
		}
		else if (e.getSource() == h2)
			goOnline("http://code.google.com/p/jwiiload/");
		else if (e.getSource() == h3)
		{
			String[] selections = {"Webseite besuchen","Schließen"};
			URL url =  getClass().getResource("jwiiload.png");

			Image image1 = Toolkit.getDefaultToolkit().getImage(url);

			ImageIcon image = new ImageIcon(image1);

			int a = JOptionPane.showOptionDialog(this,"JWiiload 1.0 - Deutsche Version\nOriginal von Ricky Ayoub (VGMoose)\nübersetzt von Andreas Bielawski (WiiDatabase.de)       ","Über jWiiload", 0, 0, image, selections, null);
			if (a==0) goOnline("http://www.rickyayoub.com");
		}
		else if (e.getSource() == cbMenuItem)
		{
			JWiiLoad.autosend = cbMenuItem.getState();
			//			if (JWiiLoad.autosend && JWiiLoad.filename!=null)
			//			{
			////				text1.setText("Finding Wii...");
			////				JWiiLoad.tripleScan();
			//			}
			//			else
			JWiiLoad.stopscan = true;
		}
		else if (e.getSource() == browse || e.getSource() == button5)
		{
			JWiiLoad.filename = chooseFile();

			if (JWiiLoad.filename!=null)
			{
				send.setEnabled(true);
				filename.setText(JWiiLoad.filename.getName()+" "+JWiiLoad.arguments);
				JWiiLoad.compressData();
				//				if (JWiiLoad.autosend)
				//				{				
				//					text1.setText("Finding Wii...");
				//					text1.invalidate();
				//					text1.validate();
				//					text1.repaint();
				//					JWiiLoad.tripleScan();
				//				}
			}
			else
				send.setEnabled(false);
		}
		else if (e.getSource() == menuItem)
		{
			String s = JOptionPane.showInputDialog("Gebe die neue Port-Nummer an:",JWiiLoad.port);
			if (s!=null)
				JWiiLoad.port = Integer.parseInt(s);
		}
		else if (e.getSource() == menuItem2)
		{
			String s = JOptionPane.showInputDialog("Gebe so viele Argumente wie nötig an:",JWiiLoad.arguments);
			if (s!=null)
			{
				JWiiLoad.arguments = s;
				if (JWiiLoad.filename!=null)
					filename.setText(JWiiLoad.filename.getName()+" "+s);
			}
		}
		else if (e.getSource() == send)
		{
			JWiiLoad.host = wiiip.getText();
			
			try {
				System.out.println("Begrüße Wii...");
				JWiiLoad.socket = new Socket(JWiiLoad.host,JWiiLoad.port);
				
				JWiiLoad.wiisend();
			} catch (Exception e1) {
				System.out.println("Keine Wii gefunden auf " + JWiiLoad.host+"!");
			}
			
		}

	}

	public void goOnline(String site)
	{
		try {
			java.awt.Desktop.getDesktop().browse(new URI(site));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
