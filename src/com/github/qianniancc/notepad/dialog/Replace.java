package com.github.qianniancc.notepad.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.github.qianniancc.notepad.frame.Main;

public class Replace extends JDialog {


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField value1;
	private JTextField textField_1;
	private static Replace instance;
	private JCheckBox UpLow;
	
	public static Replace getInstance(Main main){
		if(instance==null)instance=new Replace(main);
		instance.requestFocus();
		return instance;
	}
	
	public Replace(Main main) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				instance=null;
			}
		});
		setResizable(false);
		
		setTitle("替换");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("查找内容：");
		lblNewLabel.setBounds(57, 41, 95, 15);
		contentPanel.add(lblNewLabel);
		
		value1 = new JTextField();
		value1.setBounds(118, 38, 66, 21);
		contentPanel.add(value1);
		value1.setColumns(10);
		
		JLabel label = new JLabel("替换为：");
		label.setBounds(57, 66, 95, 15);
		contentPanel.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(118, 66, 66, 21);
		contentPanel.add(textField_1);
		
		JButton btnNewButton = new JButton("查找下一个");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.where(value1.getText(),UpLow.isSelected());
			}
		});
		btnNewButton.setBounds(302, 26, 93, 23);
		contentPanel.add(btnNewButton);
		
		JButton button = new JButton("替换");
		button.setBounds(302, 58, 93, 23);
		contentPanel.add(button);
		
		JButton button_1 = new JButton("全部替换");
		button_1.setBounds(302, 91, 93, 23);
		contentPanel.add(button_1);
		
		JButton button_2 = new JButton("取消");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_2.setBounds(302, 124, 93, 23);
		contentPanel.add(button_2);
		
		UpLow = new JCheckBox("区分大小写");
		UpLow.setBounds(42, 172, 103, 23);
		contentPanel.add(UpLow);
		this.setLocationRelativeTo(main);
		this.setVisible(true);
	}
}
