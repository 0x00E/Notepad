package com.github.qianniancc.notepad.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.qianniancc.notepad.frame.Main;


public class JFontChooser extends JPanel {

	private static final long serialVersionUID = 1L;
	
    private String current_fontName = "宋体";
    private int current_fontStyle = Font.PLAIN;
    private int current_fontSize = 9;
    private Color current_color = Color.BLACK;
    private JDialog dialog;
    private FontHelper myfont;
    private JLabel lblFont;
    private JLabel lblStyle;
    private JLabel lblSize;
    private JLabel lblColor;
    private JTextField txtFont;
    private JTextField txtStyle;
    private JTextField txtSize;
    private JList<?> lstFont;
    private JList<?> lstStyle;
    private JList<?> lstSize;
    private JComboBox<?> cbColor;
    private JButton ok, cancel;
    private JScrollPane spFont;
    private JScrollPane spSize;
    private JLabel lblShow;
    private JPanel showPan;
    private Map<String, Integer> sizeMap;
    private Map<String, Color> colorMap;
	private Map<Color, String> colorNameMap=new HashMap<>();
	private Map<Integer, String> sizeNameMap=new HashMap<>();
    
    public JFontChooser() {
        init();
    }
    
    public int getFontStyle(String value){
    	int current_fontStyle = 0;
    	if (value.equals("常规")) {
             current_fontStyle = Font.PLAIN;
         }
         if (value.equals("斜体")) {
             current_fontStyle = Font.ITALIC;
         }
         if (value.equals("粗体")) {
             current_fontStyle = Font.BOLD;
         }
         if (value.equals("粗斜体")) {
             current_fontStyle = Font.BOLD | Font.ITALIC;
         }
         return current_fontStyle;
    }
    
    public String getFontStyleName(int value){
    	if (value== Font.PLAIN) {
             return "常规";
         }
         if (value==Font.ITALIC) {
             return "斜体";
         }
         if (value==Font.BOLD) {
             return "粗体";
         }
         if (value==(Font.BOLD | Font.ITALIC)) {
             return "粗斜体";
         }
         return null;
    }
    
    public String getColorName(Color c){
		return colorNameMap.get(c);
    }

    private void init() {
        
        lblFont = new JLabel("字体:");
        lblStyle = new JLabel("字型:");
        lblSize = new JLabel("大小:");
        lblColor = new JLabel("颜色:");
        lblShow = new JLabel("NotePad");
        txtFont = new JTextField("宋体");
        txtStyle = new JTextField("常规");
        txtSize = new JTextField("9");
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        Font f=Main.getEditorPane().getFont();
        lstFont = new JList<Object>(fontNames);
      
        lstStyle = new JList<Object>(new String[]{"常规", "斜体", "粗体", "粗斜体"});
        
        String[] sizeStr = new String[]{
            "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72",
            "初号", "小初", "一号", "小一", "二号", "小二", "三号", "小三", "四号", "小四", "五号", "小五", "六号", "小六", "七号", "八号"
        };
        int sizeVal[] = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72, 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 11, 9, 8, 7, 6, 5};
        sizeMap = new HashMap<String, Integer>();
        for (int i = 0; i < sizeStr.length; ++i) {
            sizeMap.put(sizeStr[i], sizeVal[i]);
        }
        
        for (int i = 0; i < sizeStr.length; ++i) {
            sizeNameMap.put(sizeVal[i], sizeStr[i]);
        }
        
        lstSize = new JList<Object>(sizeStr);
        spFont = new JScrollPane(lstFont);
        spSize = new JScrollPane(lstSize);

        String[] colorStr = new String[]{
            "黑色", "蓝色", "青色", "深灰", "灰色", "绿色", "浅灰", "洋红", "桔黄", "粉红", "红色", "白色", "黄色"
        };
        Color[] colorVal = new Color[]{
            Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW
        };
        colorMap = new HashMap<String, Color>();
        
        for (int i = 0; i < colorStr.length; i++) {
            colorMap.put(colorStr[i], colorVal[i]);
        }
        
        for (int i = 0; i < colorStr.length; i++) {
            colorNameMap.put(colorVal[i],colorStr[i] );
        }
        cbColor = new JComboBox<Object>(colorStr);
        showPan = new JPanel();
        ok = new JButton("确定");
        cancel = new JButton("取消");
        

        
        this.setLayout(null);
        add(lblFont);
        lblFont.setBounds(12, 10, 30, 20);
        txtFont.setEditable(false);
        add(txtFont);
        txtFont.setBounds(10, 30, 155, 20);
        lstFont.setSelectedValue(f.getFamily(), true);
        add(spFont);
        spFont.setBounds(10, 50, 155, 100);

        add(lblStyle);
        lblStyle.setBounds(175, 10, 30, 20);
        txtStyle.setEditable(false);
        add(txtStyle);
        txtStyle.setBounds(175, 30, 130, 20);
        lstStyle.setBorder(javax.swing.BorderFactory.createLineBorder(Color.gray));
        add(lstStyle);
        lstStyle.setBounds(175, 50, 130, 100);
        lstStyle.setSelectedValue(getFontStyleName(f.getStyle()), true);

        add(lblSize);
        lblSize.setBounds(320, 10, 30, 20);
        txtSize.setEditable(false);
        add(txtSize);
        txtSize.setBounds(320, 30, 60, 20);
        add(spSize);
        spSize.setBounds(320, 50, 60, 100);
		lstSize.setSelectedValue(f.getSize()+"", true);
		if(lstSize.getSelectedValue()==null)	lstSize.setSelectedValue(sizeNameMap.get(f.getSize()), true);


        add(lblColor);
        lblColor.setBounds(13, 170, 30, 20);
        add(cbColor);
        cbColor.setBounds(10, 195, 130, 22);
        cbColor.setMaximumRowCount(5);
      
        cbColor.setSelectedItem(getColorName(Main.getEditorPane().getForeground()));
        
        
        
        showPan.setBorder(javax.swing.BorderFactory.createTitledBorder("示例"));
        add(showPan);
        showPan.setBounds(150, 170, 230, 100);
        showPan.setLayout(new BorderLayout());
        lblShow.setBackground(Color.white);
        showPan.add(lblShow);
        add(ok);
        ok.setBounds(10, 240, 60, 20);
        add(cancel);
        cancel.setBounds(80, 240, 60, 20);
        

        
        lstFont.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                current_fontName = (String) lstFont.getSelectedValue();
                txtFont.setText(current_fontName);
                lblShow.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
            }
        });

        lstStyle.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                String value = (String) ((JList<?>) e.getSource()).getSelectedValue();
               
                current_fontStyle=getFontStyle(value);
                txtStyle.setText(value);
                lblShow.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
            }
        });

        lstSize.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                current_fontSize = (Integer) sizeMap.get(lstSize.getSelectedValue());
                txtSize.setText(String.valueOf(current_fontSize));
                lblShow.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
            }
        });

        cbColor.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                current_color = (Color) colorMap.get(cbColor.getSelectedItem());
                lblShow.setForeground(current_color);
            }
        });

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                myfont = new FontHelper();
                myfont.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
                myfont.setColor(current_color);
                dialog.dispose();
                dialog = null;
            }
        });

        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                myfont = null;
                dialog.dispose();
                dialog = null;
            }
        });
        current_fontName=  lstFont.getSelectedValue().toString();
       current_fontStyle=getFontStyle(lstStyle.getSelectedValue().toString());
        current_fontSize= sizeMap.get(lstSize.getSelectedValue().toString());
        current_color=colorMap.get(cbColor.getSelectedItem());
        txtFont.setText(current_fontName);
        txtStyle.setText(lstStyle.getSelectedValue().toString());
        txtSize.setText(lstSize.getSelectedValue().toString());
        lblShow.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
        lblShow.setForeground(current_color);
        
    }

    public FontHelper showDialog(Frame parent,String title) {
        if(title == null)
            title = "字体";
        dialog = new JDialog(parent, title,true);
       
        dialog.getContentPane().add(this);
        dialog.setResizable(false);
        dialog.setSize(400, 310);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                myfont = null;
                dialog.removeAll();
                dialog.dispose();
                dialog = null;
            }
        });
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        return myfont;
    }
    
    public class FontHelper{
        private Font font;
        private Color color;

        public Font getFont() {
            return font;
        }

        public void setFont(Font font) {
            this.font = font;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }
    
   
}  

