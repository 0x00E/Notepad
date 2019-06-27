package com.github.qianniancc.notepad.dialog;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.qianniancc.notepad.frame.Main;

public class ColorChooserDemo extends JPanel {

	private static final long serialVersionUID = 1L;
	protected JColorChooser tcc=new JColorChooser();

    public ColorChooserDemo() {
        super(new BorderLayout());
        tcc.getSelectionModel().addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				 Color newColor = tcc.getColor();
			     Main.getEditorPane().setBackground(newColor);
			}
		});
        tcc.setBorder(BorderFactory.createTitledBorder(
                                             "选择颜色"));
        add(tcc, BorderLayout.PAGE_END);
        
    }


    public static void createAndShowGUI(String title) {
        JDialog frame = new JDialog();
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JComponent newContentPane = new ColorChooserDemo();
        newContentPane.setOpaque(true); 
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setModal(true);
        frame.setLocationRelativeTo(Main.getInstance());
        frame.setVisible(true);
    }

}
