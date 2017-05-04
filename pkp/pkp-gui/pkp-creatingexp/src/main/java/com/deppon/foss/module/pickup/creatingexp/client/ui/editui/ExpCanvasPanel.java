/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.editui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.util.ImageUtil;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpCanvasPanel  extends JPopupMenu{

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  设置窗口宽度
	 */
	private Integer windowWidth = 550; 
	
	/**
	 *  设置窗口高度
	 */
	private Integer windowHeight = 700; 


	/**
	 * 屏幕宽度
	 */
	private String title = "<>";

	/**
	 * 主面板
	 */
	private JPanel mainPanel; 

	/**
	 * 标题栏标签
	 */
	private JLabel titleLabel; 

	/**
	 *  标题栏面板
	 */
	private JComponent titleComponent; 

	/**
	 * 内容面板
	 */
	private JPanel contentPanel; 

	/**
	 * 构造方法
	 * @param parent
	 * @param parentPanel
	 * @param contentPanel
	 * @param titleComponent
	 * @param windowWidth
	 */
	public ExpCanvasPanel(JPanel contentPanel, JComponent titleComponent) {
		this.contentPanel = contentPanel;
		this.titleComponent = titleComponent;
		this.init();

	}

	/**
	 * 
	 * 显示画布
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-11 下午3:27:37
	 */
	public void showCanvas() {
		//指定显示位置
		this.show(titleComponent, -windowWidth - NumberConstants.NUMBER_5, -(windowHeight - titleComponent.getHeight())/2);
	}

	@Override
	public void setVisible(boolean b) {
		if(titleComponent instanceof JButton){
			JButton parentButton = (JButton)titleComponent;
			String imgSrc = b?"buttonOpen.png":"buttonClose.png";
			parentButton.setIcon(ImageUtil.getImageIcon(
					this.getClass().getClassLoader(),imgSrc));
		}
		super.setVisible(b);
	}
	
	/**
	 * 初始化界面信息
	 */
	private void init() {

		mainPanel = new JPanel(new BorderLayout());
		titleLabel = new JLabel(title);
		titleLabel.setForeground(Color.WHITE);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		
		//F12 hide window
		this.registerKeyboardAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		},
		KeyStroke.getKeyStroke("F12"),
		JComponent.WHEN_IN_FOCUSED_WINDOW);

	}


}
