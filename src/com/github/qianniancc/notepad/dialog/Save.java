package com.github.qianniancc.notepad.dialog;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import com.github.qianniancc.notepad.frame.Main;

public class Save extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFileChooser jfc;
	private boolean saveExit=false;
	
	public Save(String title, boolean saveExit) {
		
		setResizable(false);
		this.saveExit=saveExit;
		setModal(true);
		setTitle(title);
		setBounds(100, 100, 681, 545);
		jfc=new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setSelectedFile(new File("*.txt"));
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addActionListener(this);
		jfc.setApproveButtonText("保存(S)");
		jfc.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "文本文档(*.txt)";
			}
			
			@Override
			public boolean accept(File f) {
				if(f.isDirectory()){
					return true;
				}
				if(f.getName().endsWith(".txt")){
					return true;
				}
				return false;
			}
		});
		contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPane.add(jfc);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ac=e.getActionCommand();
		if(ac.equals(JFileChooser.APPROVE_SELECTION)){
			File f=jfc.getSelectedFile();
			Main.saveFile(f);
			if(saveExit)System.exit(0);
		}
		dispose();
		
	}

}
