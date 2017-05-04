package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI;

public class TopPicTureWaybillAction implements ActionListener {

	private PictureWaybillEditUI ui;
	
	public TopPicTureWaybillAction(PictureWaybillEditUI ui){
		this.ui = ui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JScrollPane sp = this.ui.sp;
		JScrollBar sbar = sp.getVerticalScrollBar();
		sbar.setValue(0);
	}

}
