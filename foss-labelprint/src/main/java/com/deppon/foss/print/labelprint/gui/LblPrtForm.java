package com.deppon.foss.print.labelprint.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListDataListener;
import javax.swing.plaf.FontUIResource;

import com.deppon.foss.print.labelprint.service.DefaultSetupUtil;
import com.deppon.foss.print.labelprint.service.LblPrtService;
import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;
import com.deppon.foss.print.labelprint.util.PropertiesUtil;

abstract class LblPrtForm extends JDialog {
	
	private static final long serialVersionUID = 7816998539213410145L;
	
	private final String String_Version = "<p> DEPPON FOSS Label Printer Version 1.01 <p> Copyright (c) 2012  Deppon All Rights Reserved.";
	private List<String> lstLocalPrinter;
	private List<Map> lstPrinterType;
	private List<String> lstLabelSpec;
	private LblPrtService service;

	private JTabbedPane tabbedPaneSetup = null;
	private JPanel panelMain = null;
	private JPanel panelTitle = null;
	private JPanel panelSetupLeft = null;
	private JPanel panelSetupRight = null;
	private JPanel panelMasterPrtSet = null;
	private JPanel panelSubPrtSet = null;
	private JPanel panelPrinterTypeSet = null;
	//private JPanel panelLabelSpecSet = null;
	private JPanel panelBottom = null;
	
	private JPanel panelVersion = null;
	
	JLabel lblLocalPrinterMaster = null;
	JComboBox cbxLocalPrinterMaster = null;
	//JCheckBox chkLocalPrinterMasterSetDef = null;
	
	JLabel lblLocalPrinterSub = null;
	JComboBox cbxLocalPrinterSub = null;
	//JCheckBox chkLocalPrinterSubSetDef = null;
	
	JLabel lblPrinterType = null;
	JComboBox cbxPrinterType = null;
	//JCheckBox chkPrtPrgSetDef = null;
	
	JLabel lblTop = null;
	JLabel lblLeft = null;
	JTextField txtTop = null;
	JTextField txtLeft = null;
	
	JLabel lblversion = null;
	JLabel lblJettyStatus = null;
	
	public String getUIResource(String key ){
		return (String)PropertiesUtil.get(key);
	}
	
	public LblPrtForm(){
		try{
			initLocalPrinter();
			
			//initPrintProgram();
			initPrinterType();
			
			initLabelSpecification();
			
			// initSetDefaultValues
			DefaultSetupUtil _DefaultSetupUtil = DefaultSetupUtil.getInstance();

			InitGlobalFont(new Font(LblPrtServiceConst.key_font_name,Font.PLAIN,11));
			
			initUI();
			
			// init default value
			String masterprter = _DefaultSetupUtil.loadSetDefaultValue(LblPrtServiceConst.key_set_default_local_printer_master);
			if(masterprter!=null){
				cbxLocalPrinterMaster.setSelectedItem(masterprter);
			}
			
			String subprter = _DefaultSetupUtil.loadSetDefaultValue(LblPrtServiceConst.key_set_default_local_printer_sub);
			if(subprter!=null){
				cbxLocalPrinterSub.setSelectedItem(subprter);
			}
			
			String prtertype = _DefaultSetupUtil.loadSetDefaultValue(LblPrtServiceConst.key_set_default_printer_type);
			if(prtertype!=null){
				
				for(int i=0;i<lstPrinterType.size();i++ ){
					Map m = (Map)lstPrinterType.get(i);
					String key = (String)m.get("key");
					if(key.equals(prtertype)){
						cbxPrinterType.setSelectedIndex(i);
						break;
					}
				}
			}
			
			String left = _DefaultSetupUtil.loadSetDefaultValue(LblPrtServiceConst.key_set_default_printer_left);
			if(left!=null){
				try{
					txtLeft.setText(""+Integer.parseInt(left));
				}catch (Exception e) {	}
			}
			
			String top = _DefaultSetupUtil.loadSetDefaultValue(LblPrtServiceConst.key_set_default_printer_top);
			if(top!=null){
				try{
					txtTop.setText(""+Integer.parseInt(top));
				}catch (Exception e) {	}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initLabelSpecification() {
		lstLabelSpec = new ArrayList<String>();
		String labelspecs = PropertiesUtil.get(LblPrtServiceConst.key_lblprt_label_sepcification);
		String[] arr = labelspecs.split(",");
		for(String str : arr){
			lstLabelSpec.add(str);
		}
	}

	private void initUI(){
		
		panelSetupLeft = new JPanel();
		panelSetupLeft.setSize(new Dimension(200,210));
		panelSetupLeft.setLayout(new GridLayout(3,1));
		
		panelMasterPrtSet = new JPanel();
		panelMasterPrtSet.setLayout(new FlowLayout(FlowLayout.LEFT));
		//panelPrtSet.setSize(new Dimension(400,200));
		
		lblLocalPrinterMaster = new JLabel(getUIResource(LblPrtServiceConst.key_ui_label_local_printer_master));
		lblLocalPrinterMaster.setPreferredSize(new Dimension(60,20));
		panelMasterPrtSet.add(lblLocalPrinterMaster);
		
		cbxLocalPrinterMaster = new JComboBox(loadPrinterNames());
		cbxLocalPrinterMaster.setPreferredSize(new Dimension(200,20));
		panelMasterPrtSet.add(cbxLocalPrinterMaster);
		panelSetupLeft.add(panelMasterPrtSet);
		
		panelSubPrtSet = new JPanel();
		panelSubPrtSet.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		lblLocalPrinterSub = new JLabel(getUIResource(LblPrtServiceConst.key_ui_label_local_printer_sub));
		lblLocalPrinterSub.setPreferredSize(new Dimension(60,20));
		panelSubPrtSet.add(lblLocalPrinterSub);
		
		cbxLocalPrinterSub = new JComboBox(loadPrinterNames());
		cbxLocalPrinterSub.setPreferredSize(new Dimension(200,20));
		panelSubPrtSet.add(cbxLocalPrinterSub);
		panelSetupLeft.add(panelSubPrtSet);
		
		panelPrinterTypeSet = new JPanel();
		panelPrinterTypeSet.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		lblPrinterType = new JLabel(getUIResource(LblPrtServiceConst.key_ui_label_printer_program));
		lblPrinterType.setPreferredSize(new Dimension(60,20));
		panelPrinterTypeSet.add(lblPrinterType);
		
		cbxPrinterType = new JComboBox(new PrinterTypeComboBoxModel(lstPrinterType));
		cbxPrinterType.setPreferredSize(new Dimension(200,20));
		cbxPrinterType.addItemListener(new PrinterTypeComboxItemListener());
		panelPrinterTypeSet.add(cbxPrinterType);
		panelSetupLeft.add(panelPrinterTypeSet);
		
		panelSetupRight = new JPanel();
		panelSetupRight.setPreferredSize(new Dimension(200,210));
		panelSetupRight.setLayout(new GridLayout(3,1));
		
		JPanel panelTopSet = new JPanel();
		panelTopSet.setLayout(new FlowLayout(FlowLayout.LEFT));
		lblTop = new JLabel(getUIResource(LblPrtServiceConst.key_ui_label_top));
		panelTopSet.add(lblTop);
		txtTop = new JTextField();
		txtTop.setPreferredSize(new Dimension(70,20));
		panelTopSet.add(txtTop);
		panelSetupRight.add(panelTopSet);
		
		JPanel panelLeftSet = new JPanel();
		panelLeftSet.setLayout(new FlowLayout(FlowLayout.LEFT));
		lblLeft = new JLabel(getUIResource(LblPrtServiceConst.key_ui_label_left));
		panelLeftSet.add(lblLeft);
		txtLeft = new JTextField();
		txtLeft.setPreferredSize(new Dimension(70,20));
		panelLeftSet.add(txtLeft);
		panelSetupRight.add(panelLeftSet);
		
		// label specification set 
		//panelLabelSpecSet = makeLabelSpecSetPanel();
		//panelSetup.add(panelLabelSpecSet);
		// ---
		
		panelBottom = makeBottomControlPanel();
		
		panelTitle = new JPanel();
		panelTitle.setPreferredSize(new Dimension(400,20));

		panelMain = new JPanel();
		panelMain.setBorder(BorderFactory.createEmptyBorder());
		panelMain.setLayout(new BorderLayout());
		panelMain.add(panelTitle,BorderLayout.NORTH);
		panelMain.add(panelSetupLeft,BorderLayout.WEST);
		panelMain.add(panelSetupRight,BorderLayout.CENTER);
		panelMain.add(panelBottom,BorderLayout.SOUTH);

		tabbedPaneSetup = new JTabbedPane();
		tabbedPaneSetup.addTab(getUIResource(LblPrtServiceConst.key_ui_setup_panel_title), panelMain);
		
		panelVersion = new JPanel();
		panelVersion.setLayout(new BorderLayout());
		
		ImageIcon logoicon = new ImageIcon(this.getClass().getResource("/com/deppon/foss/print/labelprint/service/images/dp-foss.jpg")); 
		BackgroundImagePane logoPanel = new BackgroundImagePane(logoicon.getImage(), BackgroundImagePane.CENTRE);
		logoPanel.setPreferredSize(new Dimension(500,67));
		logoPanel.setBorder(BorderFactory.createEmptyBorder());
		panelVersion.add(logoPanel, BorderLayout.NORTH);
	
		
		lblversion = new JLabel("<html><div style='margin-left:10px;font-family:Arial;font-size:12pt;'>"+String_Version+"</div></html>");
		lblJettyStatus = new JLabel();
		JPanel innpanel = new JPanel(new GridLayout(2,1));
		innpanel.add(lblversion);
		innpanel.add(lblJettyStatus);
		panelVersion.add(innpanel,BorderLayout.CENTER);
		tabbedPaneSetup.addTab(getUIResource(LblPrtServiceConst.key_ui_version_panel_title), panelVersion);
		
		this.getContentPane().add(tabbedPaneSetup);
		
		URL url = this.getClass().getResource("/com/deppon/foss/print/labelprint/service/images/printer16.png");
		ImageIcon icon = new ImageIcon(url); 
		Image image = icon.getImage();
		this.setIconImage(image);
		this.setTitle(getUIResource(LblPrtServiceConst.key_ui_frame_title));
	}
	
	public JPanel makeLabelSpecSetPanel(){
		JLabel lblLabelSpec = null;
		final JComboBox cbxLabelSpec = new JComboBox(loadLabelSpecs());
		final JCheckBox chkLabelSpecSetDef = new JCheckBox();
		
		JPanel _panelLabelSpecSet = new JPanel();
		_panelLabelSpecSet.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		lblLabelSpec = new JLabel(getUIResource(LblPrtServiceConst.key_ui_label_specifcation));
		lblLabelSpec.setPreferredSize(new Dimension(60,20));
		_panelLabelSpecSet.add(lblLabelSpec);
		
		cbxLabelSpec.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				try{
					DefaultSetupUtil _DefaultSetupUtil = DefaultSetupUtil.getInstance();
					
					String _spec = (String)cbxLabelSpec.getSelectedItem();
					String defvalue = _DefaultSetupUtil.loadSetDefaultValue(LblPrtServiceConst.key_set_default_label_specification);
					if(_spec.equals(defvalue)){
						chkLabelSpecSetDef.setSelected(true);
					}
					else {
						chkLabelSpecSetDef.setSelected(false);
					}
				}catch (Exception exp) {
					exp.printStackTrace();
				}
			}
		});
		cbxLabelSpec.setPreferredSize(new Dimension(200,20));
		_panelLabelSpecSet.add(cbxLabelSpec);
		
		chkLabelSpecSetDef.setText(getUIResource(LblPrtServiceConst.key_ui_chkbox_setasdef));
		chkLabelSpecSetDef.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					DefaultSetupUtil _DefaultSetupUtil = DefaultSetupUtil.getInstance();
					
					String lblspec = lstLabelSpec.get(cbxLabelSpec.getSelectedIndex());
					if(chkLabelSpecSetDef.isSelected()){
						_DefaultSetupUtil.doSetDefaultValue(LblPrtServiceConst.key_set_default_label_specification,lblspec);
					}
					else {
						String deflblspec = _DefaultSetupUtil.loadSetDefaultValue(LblPrtServiceConst.key_set_default_label_specification);
						if(lblspec.equals(deflblspec)){
							_DefaultSetupUtil.doSetDefaultValue(LblPrtServiceConst.key_set_default_label_specification,null);	
						}
					}
				}catch (Exception exp) {
					exp.printStackTrace();
				}
			}
		});
		
		_panelLabelSpecSet.add(chkLabelSpecSetDef);
		return _panelLabelSpecSet;
	}
	
	public JPanel makeBottomControlPanel(){
		JPanel _panel = new JPanel();
		_panel.setSize(new Dimension(400,30));
		
		JButton btnSaveSetting = null;
		btnSaveSetting = new JButton();
		btnSaveSetting.setText(getUIResource(LblPrtServiceConst.key_ui_button_save_setting));
		btnSaveSetting.addActionListener(new SaveSettingActionListener());
		_panel.add(btnSaveSetting);
		
		/*
		JButton btnClose = null;
		btnClose = new JButton();
		btnClose.setText(getUIResource(LblPrtServiceConst.key_ui_button_close));
		btnClose.addActionListener(new HideConsoleActionListener());
		_panel.add(btnClose);
		*/
		
		JButton btnHide = null;
		btnHide = new JButton();
		btnHide.setText(getUIResource(LblPrtServiceConst.key_ui_button_hide));
		btnHide.addActionListener(new HideConsoleActionListener());
		_panel.add(btnHide);
		
		return _panel;
	}
	
	private void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
	
	class SaveSettingActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				DefaultSetupUtil _DefaultSetupUtil = DefaultSetupUtil.getInstance();
				
				String masterprter = (String)cbxLocalPrinterMaster.getSelectedItem();
				_DefaultSetupUtil.doSetDefaultValue(LblPrtServiceConst.key_set_default_local_printer_master, masterprter);
				
				String subprter = (String)cbxLocalPrinterSub.getSelectedItem();
				_DefaultSetupUtil.doSetDefaultValue(LblPrtServiceConst.key_set_default_local_printer_sub, subprter);
				
				PrinterTypeComboBoxModel _model = (PrinterTypeComboBoxModel)cbxPrinterType.getModel();
				String prtertype = _model.getSelectObjectKey();
				_DefaultSetupUtil.doSetDefaultValue(LblPrtServiceConst.key_set_default_printer_type, prtertype);
				
				String left = (String)txtLeft.getText();
				_DefaultSetupUtil.doSetDefaultValue(LblPrtServiceConst.key_set_default_printer_left, left);
				String top = (String)txtTop.getText();
				_DefaultSetupUtil.doSetDefaultValue(LblPrtServiceConst.key_set_default_printer_top, top);
				
				_DefaultSetupUtil.storeSetDefaultValuesToFile();
				
				JOptionPane.showMessageDialog(tabbedPaneSetup, (Object)getUIResource(LblPrtServiceConst.key_ui_msg_save_success));
			}catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}
	
	class HideConsoleActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			hideWindow();
		}
	}
	
	private void initPrinterType(){
		lstPrinterType = new ArrayList<Map>();
		String str = PropertiesUtil.get(LblPrtServiceConst.key_lblprt_print_type);
		String[] printertypearr = str.split(",");
		for(int i=0;i<printertypearr.length;i++){
			Map<String,String> m = new HashMap<String,String>();
			m.put("key", printertypearr[i].split(":")[0]);
			m.put("name", printertypearr[i].split(":")[1]);
			m.put("left", printertypearr[i].split(":")[2]);
			m.put("top", printertypearr[i].split(":")[3]);
			
			lstPrinterType.add(m);
		}
	}
	
	private void initLocalPrinter(){
		lstLocalPrinter = new ArrayList<String>();
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService[] pss = PrintServiceLookup.lookupPrintServices(flavor, pras);
		for(int i = 0; i < pss.length; i++ ) {
			String name = pss[i].getName();
			lstLocalPrinter.add(name);
		}
	}
	
	class PrinterTypeComboBoxModel implements ComboBoxModel {
		
		Map selecteditem = null;
		List<Map> data = null;
		public PrinterTypeComboBoxModel(List<Map> pdata ){
			data = pdata;
		}

		@Override
		public int getSize() {
			return data.size();
		}

		@Override
		public Object getElementAt(int index) {
			Map m = data.get(index);
			return m.get("name");
		}

		@Override
		public void addListDataListener(ListDataListener l) { }

		@Override
		public void removeListDataListener(ListDataListener l) { }

		@Override
		public void setSelectedItem(Object anItem) {
			if(data!=null){
				for(Map m : data){
					if(anItem.equals(m.get("name"))){
						selecteditem = m;
						break;
					}
				}
			}
		}

		@Override
		public Object getSelectedItem() {
			if(selecteditem!=null){
				return selecteditem.get("name");
			}
			return null;
		}
		
		public Map getSelectObject(){
			return selecteditem;
		}
		
		public String getSelectObjectKey(){
			if(selecteditem!=null){
				return (String)selecteditem.get("key");
			}
			return null;
		}
	}
	
	public void setLeftAndTopByItemChange(int left,int top){
		if(txtTop!=null)
			txtTop.setText(""+top);
			txtLeft.setText(""+left);
	}
	
	class PrinterTypeComboxItemListener implements ItemListener {
		
		public void itemStateChanged(ItemEvent e) {
			PrinterTypeComboBoxModel _model = (PrinterTypeComboBoxModel)((JComboBox)e.getSource()).getModel();
			Map<String,String> m = (Map<String,String>)_model.getSelectObject();
			if(m!=null){
				int left = Integer.parseInt((String)m.get("left"));
				int top = Integer.parseInt((String)m.get("top"));
				setLeftAndTopByItemChange(left,top);
			}
		};
	}
	
	private String[] loadPrinterNames(){
		if(lstLocalPrinter!=null)
			return lstLocalPrinter.toArray(new String[0]);
		else {
			return new String[0];
		}
	}
	
	private String[] loadLabelSpecs(){
		if(lstLabelSpec!=null)
			return lstLabelSpec.toArray(new String[0]);
		else {
			return new String[0];
		}
	}

	public void openWindow(){
		LblPrtService _service =  getLblPrtService();
		if(_service!=null){
			// get jetty seriice status 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date jdata = new Date();
			
			if(_service.getLblServiceStatus()==1){
				lblJettyStatus.setText("<html><div style='margin-left:10px;;'>Service is Ok at "+sdf.format(jdata)+" </div></html");
			}
			else {
				lblJettyStatus.setText("<html><div style='margin-left:10px;;'>Service is Error."+ _service.getLblServiceStatus()+" at "+sdf.format(jdata)+"</div></html");
			}
		}
		
		this.setSize(new Dimension(500, 220));
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		this.setModal(true);
		this.setVisible(true); 
	}
	
	public void hideWindow(){
		this.setVisible(false); 
	}
	
	public void setLblService(LblPrtService pservice){
		service = pservice;
	}
	
	public LblPrtService getLblPrtService(){
		return this.service;
	}
}
