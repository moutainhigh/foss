package com.deppon.foss.print.jasper;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Toolkit;
import java.util.Locale;

import javax.swing.JDialog;

import net.sf.jasperreports.engine.JasperPrint;


public class ModifyFormatViewer extends JDialog {
	private static final long serialVersionUID = 1L;

	private ModifyFormatJRViewer viewer;

	private boolean isExitOnClose = true;
	private javax.swing.JPanel pnlMain;

	public ModifyFormatViewer(JasperPrint jasperPrint) throws Exception {
		this(jasperPrint, null, true, false);
	}

	public ModifyFormatViewer(JasperPrint fulljp, JasperPrint noimgjp,
			boolean isExitOnClose, boolean isPrintHalf) throws Exception {
		this(fulljp, noimgjp, isExitOnClose, null, isPrintHalf);
	}

	public ModifyFormatViewer(JasperPrint fulljp, JasperPrint noimgjp,
			boolean isExitOnClose, Locale locale, boolean isPrintHalf) throws Exception {
		if (locale != null) {
			setLocale(locale);
		}
		this.isExitOnClose = isExitOnClose;
		initComponents();

		this.viewer = new ModifyFormatJRViewer(fulljp, noimgjp, locale, isPrintHalf);
		this.pnlMain.add(this.viewer, BorderLayout.CENTER);
		this.setModal(true);
		this.setVisible(true);
	}
	private static final int FIX_HEIGHT = 550;
	private static final int FIX_WIDTH = 750;
	private void initComponents() {
		pnlMain = new javax.swing.JPanel();

		setIconImage(new javax.swing.ImageIcon(getClass().getResource(
				"/net/sf/jasperreports/view/images/jricon.GIF")).getImage());
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				exitForm();
			}
		});

		pnlMain.setLayout(new java.awt.BorderLayout());

		getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

		pack();

		Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		java.awt.Dimension screenSize = toolkit.getScreenSize();
		int screenResolution = toolkit.getScreenResolution();
		float zoom = ((float) screenResolution)
				/ ModifyFormatJRViewer.REPORT_RESOLUTION;

		int height = (int) (FIX_HEIGHT * zoom);
		if (height > screenSize.getHeight()) {
			height = (int) screenSize.getHeight();
		}
		int width = (int) (FIX_WIDTH * zoom);
		if (width > screenSize.getWidth()) {
			width = (int) screenSize.getWidth();
		}

		java.awt.Dimension dimension = new java.awt.Dimension(width, height);
		setSize(dimension);
		setLocation((screenSize.width - width) / 2,
				(screenSize.height - height) / 2);
	}

	void exitForm() {
		if (isExitOnClose) {
			System.exit(0);
		} else {
			this.setVisible(false);
			this.viewer.clear();
			this.viewer = null;
			this.getContentPane().removeAll();
			this.dispose();
		}
	}

	public void setZoomRatio(float zoomRatio) {
		viewer.setZoomRatio(zoomRatio);
	}

	public void setFitWidthZoomRatio() {
		viewer.setFitWidthZoomRatio();
	}

	public void setFitPageZoomRatio() {
		viewer.setFitPageZoomRatio();
	}

	public static void main(String args[]) {
	}
	
}
