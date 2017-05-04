package com.deppon.foss.print.labelprint;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import com.deppon.foss.print.labelprint.impl.zebra.Zebra888TTLabelPrintWorker;

public class WSLabelPrinter extends JFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args!=null && args.length>0){
			System.out.println("Start Label Print UI for way bill number : " + args[0]);
			WSLabelPrinter wsLabelPrinter = new WSLabelPrinter(args);
			
			wsLabelPrinter.showWSPrinter();
		}
		else {
			System.out.println("Start Label Print UI for way bill ");
			String[] _args = {"101010101010"};
			WSLabelPrinter wsLabelPrinter = new WSLabelPrinter(_args);
			wsLabelPrinter.showWSPrinter();
		}
		
		
	}

	public WSLabelPrinter(String[] args){
		initUI(args);
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	final String lbl_key_Top_Title = "Way Bill Label Printer:";
	final String lbl_key_Number = "Way Bill Number:";
	final String lbl_key_PrtService = "Choose Printer:";
	final String btn_key_Print = "Print";
	final String btn_key_Close = "Close";
	
	JPanel panelMain = null;
	JPanel panelTop = null;
	JPanel panelInput = null;
	JPanel panelWaybill = null;
	JPanel panelPrtService = null;
	JPanel panelButton = null;
	JLabel lblTopTitle = null;
	JLabel lblNumber = null;
	JLabel lblPrtService = null;
	JTextField txtNumber = null;
	JComboBox cbxPrtService = null;
	JButton btnPrint = null;
	JButton btnClose = null;
	
	private void initUI(String[] args){
		
		String def_WayBillNumber = args[0];
		
		lblTopTitle = new JLabel(lbl_key_Top_Title);
		panelTop = new JPanel();
		panelTop.setPreferredSize(new Dimension(200,30));
		panelTop.setLayout(new FlowLayout());;
		panelTop.add(lblTopTitle);
		
		lblNumber = new JLabel(lbl_key_Number);
		txtNumber = new JTextField();
		txtNumber.setText(def_WayBillNumber);
		txtNumber.setPreferredSize(new Dimension(150,20));
		panelWaybill = new JPanel();
		panelWaybill.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelWaybill.add(lblNumber);
		panelWaybill.add(txtNumber);
		
		lblPrtService = new JLabel(lbl_key_PrtService);
		String[] names = loadPrtServiceNames();  
		cbxPrtService = new JComboBox(names);
		panelPrtService = new JPanel();
		panelPrtService.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelPrtService.add(lblPrtService);
		panelPrtService.add(cbxPrtService);
		
		panelInput = new JPanel();
		panelInput.setPreferredSize(new Dimension(200,60));
		panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
		panelInput.add(panelWaybill);
		panelInput.add(panelPrtService);
		
		btnPrint = new JButton();
		btnPrint.setText(btn_key_Print);
		btnPrint.addActionListener(new LabelPrintActionListener());
		
		btnClose = new JButton();
		btnClose.setText(btn_key_Close);
		btnClose.addActionListener(new LabelPrinterCloseActionListener());
		panelButton = new JPanel();
		panelButton.setPreferredSize(new Dimension(200,40));
		panelButton.setLayout(new FlowLayout());
		panelButton.add(btnPrint);
		panelButton.add(btnClose);
		
		panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.add(panelTop, BorderLayout.NORTH);
		panelMain.add(panelInput, BorderLayout.CENTER);
		panelMain.add(panelButton, BorderLayout.SOUTH);
		
		this.getContentPane().add(panelMain);
	}
	
	class LabelPrintActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				// print label 
				
				// get way bill label form
				String waybillnumber = txtNumber.getText();
				LabelPrintContext lblctx = new LabelPrintContext();
				lblctx.put("waybillnumber", waybillnumber);
				lblctx.setmDeviceName((String)cbxPrtService.getSelectedItem());
				// do print service 
				LabelPrintWorker workder = new Zebra888TTLabelPrintWorker();
				workder.executePrintProcess(lblctx);
			}catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}
	
	class LabelPrinterCloseActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			closeWSPrinter();
		}
	}
	
	private String[] loadPrtServiceNames(){
		List<String> servicenames = new ArrayList<String>();
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService[] pss = PrintServiceLookup.lookupPrintServices(flavor, pras);
		for(int i = 0; i < pss.length; i++ ) {
			String name = pss[i].getName();
			//System.out.println(name);
			servicenames.add(name);
		}
		return servicenames.toArray(new String[0]);
	}
	
	public void showWSPrinter(){
		this.setSize(new Dimension(400, 400));
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.setVisible(true);
	}
	
	public void closeWSPrinter(){
		System.exit(0);
	}
}
