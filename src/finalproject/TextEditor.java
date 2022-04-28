package finalproject;

import java.applet.Applet;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;

public class TextEditor extends Frame{

	UTextArea text;
	Frame frame;
	Font font;
	FileDialog fd;
	boolean b=true;
	Clipboard clip=getToolkit().getSystemClipboard();
	int style=Font.PLAIN;
	int fontsize=12;
	String filename,st,fn="untitled",dn;
	
	TextEditor() {
		// TODO Auto-generated constructor stub
		font=new Font("Courier",style,fontsize);
		setLayout(new GridLayout(1,1));
		text=new UTextArea(80,25);
		
		text.setFont(font);
		add(text);
		
		MenuBar statusbar = new MenuBar();
		
		Menu menu1 = new Menu("File");
		MenuItem newfile = new MenuItem("New",new MenuShortcut(KeyEvent.VK_N));
		MenuItem openfile = new MenuItem("Open",new MenuShortcut(KeyEvent.VK_O));
		MenuItem savefile = new MenuItem("Save",new MenuShortcut(KeyEvent.VK_S));
		MenuItem exit = new MenuItem("Exit",new MenuShortcut(KeyEvent.VK_E));
		
		newfile.addActionListener(new New());
		menu1.add(newfile);
		openfile.addActionListener(new Open());
		menu1.add(openfile);
		savefile.addActionListener(new Save());
		menu1.add(savefile);
		exit.addActionListener(new Exit());
		menu1.add(exit);
		
		statusbar.add(menu1);
		
		Menu menu2 = new Menu("Edit");
		MenuItem cut = new MenuItem("Cut",new MenuShortcut(KeyEvent.VK_X));
		MenuItem copy = new MenuItem("Copy",new MenuShortcut(KeyEvent.VK_C));
		MenuItem paste = new MenuItem("Paste",new MenuShortcut(KeyEvent.VK_V));
		
		cut.addActionListener(new Cut());
		menu2.add(cut);
		copy.addActionListener(new Copy());
		menu2.add(copy);
		paste.addActionListener(new Paste());
		menu2.add(paste);
		
		statusbar.add(menu2);
		
		Menu menu3 = new Menu("FontNames");
		MenuItem one=new MenuItem("TimesRoman");
		MenuItem two=new MenuItem("Arial");
		MenuItem three=new MenuItem("Courier");
		MenuItem four=new MenuItem("Century");
		MenuItem five=new MenuItem("Roboto Slab");
		MenuItem six=new MenuItem("Arial Black");
		
		one.addActionListener(new Pattern());
		menu3.add(one);
		two.addActionListener(new Pattern());
		menu3.add(two);
		three.addActionListener(new Pattern());
		menu3.add(three);
		four.addActionListener(new Pattern());
		menu3.add(four);
		five.addActionListener(new Pattern());
		menu3.add(five);
		six.addActionListener(new Pattern());
		menu3.add(six);

		statusbar.add(menu3);
		
		Menu menu4=new Menu("Size");
		MenuItem s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
		s1=new MenuItem("10");
		s2=new MenuItem("12");
		s3=new MenuItem("14");
		s4=new MenuItem("16");
		s5=new MenuItem("18");
		s6=new MenuItem("20");
		s7=new MenuItem("22");
		s8=new MenuItem("24");
		s9=new MenuItem("26");
		s10=new MenuItem("28");
		
		s1.addActionListener(new Size());
		menu4.add(s1);
		s2.addActionListener(new Size());
		menu4.add(s2);
		s3.addActionListener(new Size());
		menu4.add(s3);
		s4.addActionListener(new Size());
		menu4.add(s4);
		s5.addActionListener(new Size());
		menu4.add(s5);
		s6.addActionListener(new Size());
		menu4.add(s6);
		s7.addActionListener(new Size());
		menu4.add(s7);
		s8.addActionListener(new Size());
		menu4.add(s8);
		s9.addActionListener(new Size());
		menu4.add(s9);
		s10.addActionListener(new Size());
		menu4.add(s10);
		
		statusbar.add(menu4);
		
		Menu menu5 = new Menu("Formatting");
		MenuItem bold=new MenuItem("Bold");
		MenuItem plain=new MenuItem("Plain");
		MenuItem italic=new MenuItem("Italic");
		
		bold.addActionListener(new FM());
		menu5.add(bold);
		plain.addActionListener(new FM());
		menu5.add(plain);
		italic.addActionListener(new FM());
		menu5.add(italic);
		menu4.addActionListener(new FM());
		menu5.add(menu4);
		
		statusbar.add(menu5);
		
		Menu menu6 =new Menu("Color");
		MenuItem Bg = new MenuItem("Background");
		MenuItem Fg = new MenuItem("Foreground");
		
		Bg.addActionListener(new BC());
		menu6.add(Bg);
		Fg.addActionListener(new BC());
		menu6.add(Fg);
		
		statusbar.add(menu6);
		
		Menu menu7 =new Menu("Undo&Redo");
		MenuItem undo = new MenuItem("Undo");
		MenuItem redo = new MenuItem("Redo");
		undo.addActionListener(new WW());
		menu7.add(undo);
		redo.addActionListener(new WW());
		menu7.add(redo);
		
		statusbar.add(menu7);
		
		setMenuBar(statusbar);
		addWindowListener(new Win());
		mylistener mylist = new mylistener();
		addWindowListener(mylist);
	}
	
	
	class BC implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			String st=ae.getActionCommand();
			JFrame jf = new JFrame("JColorChooser");
			colorChooser c = new colorChooser();
			c.setSize(400,300);
			c.setVisible(true);
		}

	}
	
	Button ok;
	JColorChooser jcc;
	 class colorChooser extends JFrame{
			public colorChooser() {
				setTitle("JColorChooser");
				jcc=new JColorChooser();
				JPanel content=(JPanel)getContentPane();
				content.setLayout(new BorderLayout());
				content.add(jcc,"center");
				ok=new Button("OK");
				content.add(ok,"South");
				ok.addActionListener(new B());
			}
		}
	
		class B implements ActionListener{
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				String st=ae.getActionCommand();
				System.out.println("Color Is:"+jcc.getColor().toString());
				if(st.equals("Background"))
					text.setBackground(jcc.getColor());
				if(st.equals("Foreground"))
					text.setForeground(jcc.getColor());
				frame.setVisible(false);
			}
		}
	 
	 class Copy implements ActionListener  {
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				String sel=text.getSelectedText();
				StringSelection clipstring = new StringSelection(sel);
				clip.setContents(clipstring,clipstring);
			}

		}
	 
	 class Cut implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				String sel=text.getSelectedText();
				StringSelection ss = new StringSelection(sel);
				clip.setContents(ss,ss);
				text.replaceRange("",text.getSelectionStart(),text.getSelectionEnd());
			}

		}
	 
	 class Exit implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
		  
	}
	 
	 void readFile() {
		  BufferedReader d;
		  StringBuffer sb = new StringBuffer();
		  try {
			  d=new BufferedReader(new FileReader(filename));
			  String line;
			  while((line=d.readLine())!=null)
				  sb.append(line+"");
			  text.setText(sb.toString());
			  d.close();
		  }catch(FileNotFoundException e) {
			  System.out.println("File not found");
		  }catch(IOException e) {}
	  }
	  
	  public void writeFile() {
		  try {
			  DataOutputStream d =new DataOutputStream(new FileOutputStream(filename));
			  String line=text.getText();
			  BufferedReader br =new BufferedReader(new StringReader(line));
			  while((line=br.readLine())!=null) {
				  d.writeBytes(line+" ");
			  }d.close();
		  }catch(Exception e) {
			  System.out.println("File not found");
		  }
	  }

	 
	 class FM extends Applet implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			String b=ae.getActionCommand();
			if(b=="Bold") {
				font=new Font("Courier",Font.BOLD,fontsize);
				style=font.getStyle();
				text.setFont(font);
			}
			if(b=="PLain") {
				font=new Font("Courier",Font.PLAIN,fontsize);
				style=font.getStyle();
				text.setFont(font);
			}
			if(b=="Italic") {
				font=new Font("Courier",Font.ITALIC,fontsize);
				style=font.getStyle();	
				text.setFont(font);
			}
			repaint();
		}

	}
	 
	 class mylistener extends WindowAdapter{
			public void windowClosing(WindowEvent we) {
				if(!b)
					System.exit(0);
			}
		}
	 
	 class New implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			text.setText(" ");
			setTitle(filename);
			fn="Untitled";
			
		}

	}
	 
	 class Open implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			FileDialog fd = new FileDialog(TextEditor.this,"Select");
			fd.show();
			if((fn=fd.getFile())!=null)
			{
				filename=fd.getDirectory()+fd.getFile();
				dn=fd.getDirectory();
				setTitle(filename);
				readFile();
			}
			text.requestFocus();
		}

	}
	 
	 class Paste implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				Transferable cliptran=clip.getContents(TextEditor.this);
						try {
							String sel=(String)cliptran.getTransferData(DataFlavor.stringFlavor);
							text.replaceRange(sel,text.getSelectionStart(),text.getSelectionEnd());
						}catch(Exception e) {
							System.out.println("not starting flavor");
						}
			}

		}
	 
	 class Save implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			FileDialog fd = new FileDialog(TextEditor.this,"SaveFile",FileDialog.LOAD);
			fd.setFile(fn);
			fd.setDirectory(dn);
			fd.show();
			
			if(fd.getFile()!=null) {
				filename=fd.getDirectory()+fd.getFile();
				setTitle(filename);
				writeFile();
				text.requestFocus();
			}
		}
	}
	 
	 class Size implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			
			String w=ae.getActionCommand();
			if(w=="10") {
				font=new Font("Courier",style,10);
				text.setFont(font);
				fontsize=font.getSize();
				repaint();
			}
			if(w=="12") {
				font=new Font("Courier",style,12);
				text.setFont(font);
				fontsize=font.getSize();
				repaint();
			}
			if(w=="14") {
				font=new Font("Courier",style,14);
				text.setFont(font);
				fontsize=font.getSize();
				repaint();
			}
			if(w=="16") {
				font=new Font("Courier",style,16);
				text.setFont(font);
				fontsize=font.getSize();
				repaint();
			}
			if(w=="18") {
				font=new Font("Courier",style,18);
				text.setFont(font);
				fontsize=font.getSize();
				repaint();
			}
			if(w=="20") {
				font=new Font("Courier",style,20);
				text.setFont(font);
				fontsize=font.getSize();
				repaint();
			}
			if(w=="22") {
				font=new Font("Courier",style,22);
				text.setFont(font);
				fontsize=font.getSize();
				repaint();
			}
			if(w=="24") {
				font=new Font("Courier",style,24);
				text.setFont(font);
				fontsize=font.getSize();
				repaint();
			}
			if(w=="26") {
				font=new Font("Courier",style,26);
				text.setFont(font);
				fontsize=font.getSize();
				repaint();
		}
			if(w=="28") {
				font=new Font("Courier",style,28);
				text.setFont(font);
				fontsize=font.getSize();
				repaint();
			}
		}
	 }
	 
	 class Pattern implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			String label=ae.getActionCommand();
			if(label=="TimesRoman") {
				font = new Font("TimesRoman",style,fontsize);
				text.setFont(font);
			}
			if(label=="Arial") {
				font = new Font("Arial",style,fontsize);
				text.setFont(font);
			}
			if(label=="Courier") {
				font = new Font("Courier",style,fontsize);
				text.setFont(font);
			}
			if(label=="Century") {
				font = new Font("Century",style,fontsize);
				text.setFont(font);
			}
			if(label=="Roboto Slab") {
				font = new Font("Roboto Slab",style,fontsize);
				text.setFont(font);
			}
			if(label=="Arial Black") {
				font = new Font("Arial Black",style,fontsize);
				text.setFont(font);
			}
			
			repaint();
		}

	}
	 
	 class Win extends WindowAdapter{
		 public void windowClosing(WindowEvent we) {
//			 ConfirmDialog cd = new ConfirmDialog();
			 if(!b) {
//				 we.getWindow().action(TextEditor.this, we)
				 System.exit(0);
			 }
		 }
	}
	 
	 
	 class WW implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			String s = ae.getActionCommand();
			if(s.equals("Undo"))
				text.undo();
			if(s.equals("Redo"))
				text.redo();
			
		}
	}
	 
//	@Override
//	public void actionPerformed(ActionEvent ae) {
//		// TODO Auto-generated method stub
//		
//	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Frame frame=new TextEditor();
		frame.setSize(800,600);
		frame.setVisible(true);
		frame.show();
	}

}
