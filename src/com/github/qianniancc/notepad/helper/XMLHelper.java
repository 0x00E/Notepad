	package com.github.qianniancc.notepad.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.github.qianniancc.notepad.control.TextAreaMenu;
import com.github.qianniancc.notepad.frame.Main;

public class XMLHelper {
	
	
	public static Document parse(String str) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = null;
		document = reader.read(str);
        return document;
    }
	
	public static void createDocument() throws IOException  {
        Document document = DocumentHelper.createDocument();
        Element root=document.addElement("notepad");
        TextAreaMenu tam=Main.getEditorPane();
        int background=tam.getBackground().getRGB();
        root.addElement("font")
        		.addAttribute("name",tam.getFont().getFontName())
        		.addAttribute("size", tam.getFont().getSize()+"")
        		.addAttribute("style", tam.getFont().getStyle()+"")
        		.addAttribute("color", tam.getForeground().getRGB()+"");
        if(Main.getToolBar().isVisible())
        	root.addElement("status-bar").addAttribute("isShow", "1");
        else
        	root.addElement("status-bar").addAttribute("isShow", "0");
        root.addElement("background").addText(background+"");
        File dir=new File("config");
        if(!(dir.isDirectory() && dir.exists())){
        	dir.mkdir();
        }
    	FileWriter out = new FileWriter("config/config.xml");
		document.write(out);
		out.close();
    }
}
