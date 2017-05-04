package com.deppon.foss.module.pickup.creating.client.listener;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
/**
 * 给开单界面添加鼠标滚动事件 
 * 
 * @author 
 *
 */
public class PictureMouseWheelListener implements MouseWheelListener {

	private JScrollPane jsp;
	private final int TEN = 15;
	public PictureMouseWheelListener(JScrollPane jsp){
		this.jsp = jsp;
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		JScrollBar sbar = jsp.getVerticalScrollBar();
		int maximum = sbar.getMaximum();
		int minimum = sbar.getMinimum();
		int currnum = sbar.getValue();
		if(e.getUnitsToScroll() > 0){
			if((currnum + TEN) >= maximum){
				sbar.setValue(maximum);
			}else{
				sbar.setValue(currnum + TEN);
			}
		}
		if(e.getUnitsToScroll() < 0){
			if((currnum - TEN ) <= minimum){
				sbar.setValue(minimum);
			}else{
				sbar.setValue(currnum - TEN);
			}
		}
	}

}
