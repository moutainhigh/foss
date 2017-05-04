/**
 * 
 */
package com.deppon.foss.module.boot.client.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.util.ImageUtil;

/**
 * 初始化进度条
 *
 */
public class JWindowStart extends JWindow {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	private JProgressBar progress; // 进度条

	public JWindowStart() {
		Container container = getContentPane(); // 得到容器
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); // 设置光标
		ImageIcon icon = ImageUtil.getImageIcon(this.getClass(), "Login_ad.jpg");
		container.add(new JLabel(icon), BorderLayout.CENTER); // 增加图片
		
		progress = new JProgressBar(1, NumberConstants.NUMBER_100); // 实例化进度条
		progress.setStringPainted(true); // 描绘文字
		progress.setString("加载程序中,请稍候......"); // 设置显示文字
		progress.setBackground(Color.white); // 设置背景色
		container.add(progress, BorderLayout.SOUTH); // 增加进度条到容器上

		Dimension screen = getToolkit().getScreenSize(); // 得到屏幕尺寸
		pack(); // 窗口适应组件尺寸
		setLocation((screen.width - getSize().width) / 2,
				(screen.height - getSize().height) / 2); // 设置窗口位置
	}

	public void start() {
		this.toFront(); // 窗口前端显示开始运行线程
	}

	/**
	 * @return the progress
	 */
	public JProgressBar getProgress() {
		return progress;
	}

	/**
	 * @param progress the progress to set
	 */
	public void setProgress(JProgressBar progress) {
		this.progress = progress;
	}
	
}

