package com.github.qianniancc.notepad;

import java.io.File;

import javax.swing.UIManager;

import com.github.qianniancc.notepad.frame.Main;

public class Launch {
	
	public static void main(String[] args) throws Exception {
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		Main.getInstance();
		if(args.length>0)Main.openFile(new File(args[0]));
		
	}

}
