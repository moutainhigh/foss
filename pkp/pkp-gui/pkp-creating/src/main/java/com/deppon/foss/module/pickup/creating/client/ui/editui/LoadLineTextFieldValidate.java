package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.Color;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.RoundedBalloonStyle;

import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;

public class LoadLineTextFieldValidate extends JTextFieldValidate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1906009733158821045L;

	private static final int FIVE = 5;
	
	private static final int TEN = 10;

	private static final String PreLoadLineHighAccuracyRate = "高兑现";

	private static final int NUM_16777154 = 16777154;

	public LoadLineTextFieldValidate() {
		super();
		init();
	}
	
	private void init() {
		RoundedBalloonStyle balloonStyle = new RoundedBalloonStyle(TEN, FIVE,
				new Color(NUM_16777154), Color.BLACK);
		setBalloonTip(new BalloonTip(this, "", balloonStyle, true));
		getBalloonTip().setVisible(false);
		getBalloonTip().setIconTextGap(TEN);

		this.addAncestorListener(new AncestorListener() {

			public void ancestorAdded(AncestorEvent event) {
			}

			public void ancestorRemoved(AncestorEvent event) {
				getBalloonTip().setVisible(false);
			}

			public void ancestorMoved(AncestorEvent event) {
				getBalloonTip().refreshLocation();
			}
		});
	}
	
	public void verifyWrong(String message) {
		if (message.equals("success")) {
			verifyPass();
		} else {
			init();
			if (message.contains(PreLoadLineHighAccuracyRate)) {
				this.setBackground(Color.GREEN);
			} else {
				this.setBackground(Color.PINK);
			}
			getBalloonTip().setText(message);
			getBalloonTip().setVisible(true);
			setIsEnable(false);
			setIsPassed(false);
		}
	}
	
}
