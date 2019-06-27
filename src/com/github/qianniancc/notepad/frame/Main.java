package com.github.qianniancc.notepad.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.dom4j.Element;
import org.dom4j.Node;

import com.github.qianniancc.notepad.control.JFontChooser;
import com.github.qianniancc.notepad.control.JFontChooser.FontHelper;
import com.github.qianniancc.notepad.control.TextAreaMenu;
import com.github.qianniancc.notepad.dialog.About;
import com.github.qianniancc.notepad.dialog.Open;
import com.github.qianniancc.notepad.dialog.Replace;
import com.github.qianniancc.notepad.dialog.Save;
import com.github.qianniancc.notepad.dialog.Setting;
import com.github.qianniancc.notepad.helper.XMLHelper;
import com.github.qianniancc.notepad.util.FileUtil;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuItem selectAllMenuItem;
	private JMenuItem replaceMenuItem;
	private JMenuItem datetimeMenuItem;
	private JMenu sizeMenu;
	private JMenuItem fontMenuItem;
	private JMenuItem autoLine;
	private JMenu helpMenu;
	private JMenuItem aboutMenuItem;
	private JMenuItem exit;
	private JMenu fileMenu;
	private JMenuBar menuBar;
	private JMenuItem newMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem othersaveMenuItem;
	private JMenu editMenu;	
	private static Main instance;
	private static TextAreaMenu editorPane;
	private static File nowFile;
	private static boolean notSave = false;
	private static JToolBar toolBar;
	private static JLabel lineL;
	private static JMenuItem toggleMenuItem;
	private static JMenuItem cut;
	private static JMenuItem copy;
	private static JMenuItem paste;
	private static JMenuItem delete;
	private JMenuItem setting;
	
	public static JToolBar getToolBar() {
		return toolBar;
	}
	
	public void setKeyStroke(){
		fileMenu.setMnemonic('F');
		aboutMenuItem.setMnemonic('A');
		setting.setMnemonic('S');
		cut.setAccelerator(KeyStroke.getKeyStroke('X',InputEvent.CTRL_DOWN_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke('C',InputEvent.CTRL_DOWN_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke('V',InputEvent.CTRL_DOWN_MASK));
		replaceMenuItem.setAccelerator(KeyStroke.getKeyStroke('H',InputEvent.CTRL_DOWN_MASK));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke('A',InputEvent.CTRL_DOWN_MASK));
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke('N',InputEvent.CTRL_DOWN_MASK));
		datetimeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke('O',InputEvent.CTRL_DOWN_MASK));
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_DOWN_MASK));
	}
	
	public static void saveFile(File f){
		f.delete();
		if(!f.getName().endsWith(".txt"))f=new File(f.getAbsoluteFile()+".txt");
		FileUtil.write(f, Main.getEditorPane().getText());
		Main.setNowFile(f);
		Main.setNotSave(false);
		
	}
	
	public static void openFile(File f){
		Main.setNowFile(f);
		Main.getEditorPane().setText(FileUtil.read(f));
		Main.setNotSave(false);
	}
	
	public static void init(){
		Element doc;
		try {
			doc=XMLHelper.parse("config/config.xml").getRootElement();
			Node node = doc.selectSingleNode("//font");
		    editorPane.setFont(new Font(node.valueOf("@name"), node.numberValueOf("@style").intValue(), node.numberValueOf("@size").intValue()));
		  int foreground=node.numberValueOf("@color").intValue();
		    node = doc.selectSingleNode("//status-bar");
		    if(node.numberValueOf("@isShow").intValue()!=1){
		    	toolBar.setVisible(false);
		    	toggleMenuItem.setText("显示 状态栏");
		    }
		    editorPane.setForeground(new Color(foreground));
		    String background=doc.selectSingleNode("//background").getText();
		    Main.getEditorPane().setBackground(new Color(Integer.parseInt(background)));
		
		} catch (Exception e) {
			return;
		}
		
	}
	
	public static void setNotSave(boolean notSave) {
		Main.notSave = notSave;
	}
	
	public static void setNowFile(File nowFile) {
		Main.nowFile = nowFile;
		Main.getInstance().setTitle(nowFile.getName()+" - 记事本");
	}
	
	public static File getNowFile() {
		return nowFile;
	}
	
	public static TextAreaMenu getEditorPane() {
		return editorPane;
	}
	
	public static Main getInstance() {
		if(instance==null)instance=new Main();
		return instance;
	}
	
	public static boolean ifNotSave(boolean isExit){
		try {
			XMLHelper.createDocument();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(editorPane.getText().equals("") || !notSave){
			if(isExit)System.exit(0);
			else return false;
		}
		int i=0;
		if(nowFile!=null)
			i=JOptionPane.showConfirmDialog(getInstance(), "是否将更改保存到\n"+nowFile.getAbsolutePath(), "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		else
			i=JOptionPane.showConfirmDialog(getInstance(), "是否将更改保存到\n"+"无标题", "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if(i==JOptionPane.CANCEL_OPTION  || i==JOptionPane.CLOSED_OPTION){
			return true;
		}else if(i==JOptionPane.NO_OPTION){
			if(isExit)System.exit(0);
			else return false;
		}
		else{
			if(nowFile==null){
				if(isExit)
					new Save("保存",true);
				else
					new Save("保存",false);
				return true;
			}
			nowFile.delete();
			FileUtil.write(getNowFile(), editorPane.getText());
			System.exit(0);
		}
		return false;
	}


	public Main() {
		this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					ifNotSave(true);
				}
		});
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\logo.ico"));
		setTitle("无标题 - 记事本");
		setBounds(100, 100, 800, 500);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("文件(F)");
		menuBar.add(fileMenu);
		
		exit = new JMenuItem("退出(X)");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ifNotSave(true);
			}
		});
		
		newMenuItem = new JMenuItem("新建(N)");
		newMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ifNotSave(false)){
					return;
				}
				setTitle("无标题 - 记事本");
				editorPane.setText("");
				notSave=false;
			}
		});
		fileMenu.add(newMenuItem);
		
		openMenuItem = new JMenuItem("打开(O)");
		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Open();
				
			}
		});
		fileMenu.add(openMenuItem);
		
		saveMenuItem = new JMenuItem("保存(S)");
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nowFile==null){
					new Save("保存",false);
					return;
				}
				Main.setNotSave(false);
				FileUtil.write(getNowFile(), editorPane.getText());
			}
		});
		fileMenu.add(saveMenuItem);
		
		othersaveMenuItem = new JMenuItem("另存为(A)");
		othersaveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Save("另存为",false);
			}
		});
		fileMenu.add(othersaveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		
		editMenu = new JMenu("编辑(E)");
		editMenu.setMnemonic('E');
		menuBar.add(editMenu);
		
		selectAllMenuItem = new JMenuItem("全选(A)");
		selectAllMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 editorPane.selectAll();
			}
		});
		
		replaceMenuItem = new JMenuItem("替换(R)");
		replaceMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main main=Main.getInstance();
				Replace.getInstance(main);
			}
		});
		
		cut = new JMenuItem("剪切(T)");
		
		cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorPane.cut();	
			}
		});
		editMenu.add(cut);
		
		copy = new JMenuItem("复制(C)");
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorPane.copy();
			}
		});
		editMenu.add(copy);
		
		paste = new JMenuItem("粘贴(P)");
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorPane.paste();
			}
		});
		editMenu.add(paste);
		
		delete = new JMenuItem("删除(L)");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String start=editorPane.getText().substring(0,editorPane.getSelectionStart());
				String end=editorPane.getText().substring(editorPane.getSelectionEnd());
				editorPane.setText(start+end);
			}
		});
		editMenu.add(delete);
		editMenu.addSeparator();
		editMenu.add(replaceMenuItem);
		editMenu.add(selectAllMenuItem);
		
		datetimeMenuItem = new JMenuItem("时间/日期(D)");
		datetimeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String prefix=editorPane.getText().substring(0,editorPane.getCaretPosition());
				String suffix=editorPane.getText().substring(editorPane.getCaretPosition());
				editorPane.setText(prefix+LocalDateTime.now()+suffix);
			}
		});
		editMenu.addSeparator();
		editMenu.add(datetimeMenuItem);
		
		editMenu.addSeparator();
		
		setting = new JMenuItem("选项(S)");
		setting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Setting(instance);
			}
		});
		editMenu.add(setting);
		
		sizeMenu = new JMenu("格式(O)");
		sizeMenu.setMnemonic('O');
		menuBar.add(sizeMenu);
		
		fontMenuItem = new JMenuItem("字体(F)...");
		fontMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFontChooser one = new JFontChooser();
				FontHelper lhf = one.showDialog(Main.getInstance(),"字体");
		        if(lhf==null)return;
		        if(lhf.getColor()!=null)editorPane.setForeground(lhf.getColor());
		        if(lhf.getFont()!=null)editorPane.setFont(lhf.getFont());
			}
		});
		
		autoLine = new JCheckBoxMenuItem("自动换行(W)");
		autoLine.setSelected(true);
		autoLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(autoLine.isSelected()){
					editorPane.setLineWrap(true);
				}else{
					editorPane.setLineWrap(false);
				}
			}
		});
		
		sizeMenu.add(autoLine);
		sizeMenu.add(fontMenuItem);
		
		JMenu showMenu = new JMenu("查看(V)");
		showMenu.setMnemonic('V');
		menuBar.add(showMenu);
		
		toggleMenuItem = new JCheckBoxMenuItem("状态栏(S)");
		toggleMenuItem.setSelected(true);
		toggleMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(toggleMenuItem.isSelected()){
					toolBar.setVisible(true);
				}else{
					toolBar.setVisible(false);
				}
			}
		});
		showMenu.add(toggleMenuItem);
		
		helpMenu = new JMenu("帮助(H)");
		helpMenu.setMnemonic('H');
		menuBar.add(helpMenu);
		
	 aboutMenuItem = new JMenuItem("关于记事本(A)");
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new About(Main.getInstance());
			}
		});
		helpMenu.add(aboutMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		editorPane = new TextAreaMenu();
		editorPane.setFont(new Font("宋体", Font.PLAIN, 14));
		editorPane.setLineWrap(true);
		editorPane.addCaretListener(new CaretListener() {  
	        public void caretUpdate(CaretEvent e) {  
	            try {  
	                int offset = e.getDot() ;  
	               
	                int row = editorPane.getLineOfOffset(offset);  
	                  
	                int column = e.getDot() - editorPane.getLineStartOffset(row);  

	                lineL.setText("第 "+(row + 1)+" 行，第 "+(column+1)+" 列");
	                
	                  
	            } catch (Exception ex) {  
	                ex.printStackTrace();  
	            }  
	        }  
	}); 
		
		editorPane.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				changedUpdate(arg0);
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				changedUpdate(arg0);
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				notSave=true;
			}
		});
		 toolBar = new JToolBar();	
		 toolBar.setFloatable(false);
		
		contentPane.add(toolBar, BorderLayout.SOUTH);
		
		lineL = new JLabel("第 1 行，第 1 列");
		toolBar.add(lineL);
		
		JScrollPane scrollPane = new JScrollPane(editorPane);
		
		contentPane.add(scrollPane, BorderLayout.CENTER);
		setKeyStroke();
		init();
		setVisible(true);
	}

	public static void where(String text, boolean b) {
		editorPane.select(0, 0);
		if(b)
			editorPane.select(editorPane.getText().indexOf(text,editorPane.getCaret().getDot()), editorPane.getText().indexOf(text,editorPane.getCaret().getDot())+text.length());		
		else
			editorPane.select(editorPane.getText().toLowerCase().indexOf(text.toLowerCase(),editorPane.getCaret().getDot()), editorPane.getText().toLowerCase().indexOf(text.toLowerCase(),editorPane.getCaret().getDot())+text.length());		
		if(editorPane.getSelectedText()==null)JOptionPane.showMessageDialog(instance, "找不到\""+text+"\"或已到达结尾");
	}
}