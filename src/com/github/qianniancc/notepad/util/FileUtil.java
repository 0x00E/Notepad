package com.github.qianniancc.notepad.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
	
	public static String read(File f) {
		StringBuilder str=new StringBuilder();
		try {
			FileReader fr=new FileReader(f);
			char[] c=new char[1024];
			int count;
			while((count=fr.read(c))>0){
				str.append(c, 0, count);
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}
	
	public static void write(File f,String str){
		try {
			if(!(f.isFile()&&f.exists()))f.createNewFile();
			FileWriter fw=new FileWriter(f);
			fw.write(str);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
