/**
 *  initial comments.
 */
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.mainframe.client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXCollapsiblePane;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.util.ImageUtil;

/**
 * 
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:47:29,
 * </p>
 * 
 * @author dp-yangtong
 * @date 2012-10-12 上午11:47:29
 * @since
 * @version
 */
public class JOutlookBar extends JPanel implements ActionListener {
	/**
	 * 
	 * 序列号
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private static final long serialVersionUID = -3359341832298304787L;

	/**
	 * icon url 创建String型变量iconurl
	 */
	private static String iconurl = "home2_13.png";

	/**
	 * A LinkedHashMap of bars: we use a linked hash map to preserve the order
	 * of the bars 创建Map集合bars
	 */
	@SuppressWarnings("rawtypes")
	private Map bars = new LinkedHashMap();

	/**
	 * The currently visible bar (zero-based index)
	 */
//	private int visibleBar = 0;
	
	/**
	 * 定义一个静态的BarInfo类型的对象selectedBarInfo
	 */
	public static BarInfo selectedBarInfo;
	/**
	 * 定义一个ActionListener类型的对象menuActionListener
	 */
	private ActionListener menuActionListener;

	/**
	 * Creates a new JOutlookBar; after which you should make repeated calls to
	 * addBar() for each bar
	 */
	public JOutlookBar() {
		//set up layout
	    	/**
		 * 设置Layout
		 */
		this.setLayout(new VFlowLayout(0, 0, 0, true, false));
		//north panel
//		this.add(topPanel, BorderLayout.NORTH);
//		//south panel
//		this.add(bottomPanel, BorderLayout.SOUTH);
		//set Opaque
		setBackground(Color.white);// 设置背景颜色
	}

	/**
	 * Adds the specified component to the JOutlookBar and sets the bar's name
	 * 
	 * @param name
	 *            The name of the outlook bar
	 * @param componenet
	 *            The component to add to the bar
	 */
	@SuppressWarnings("unchecked")
	public BarInfo addBar(String name, JComponent component) {
		//create bar info
	    	/**
	    	 * 创建一个BarInfo类型的对象barInfo
	    	 */
		BarInfo barInfo = new BarInfo(name, component);
		//get bar info button to add event listener
		/**
	    	 * 获取barInfo按钮添加对象本身
	    	 */
		barInfo.getButton().addActionListener(this);
		//put class into bars list 
		/**
	    	 * 向Map集合bars添加元素
	    	 */
		this.bars.put(name, barInfo);
		//render component 
		/**
	    	 * 调用render()方法
	    	 */
		render();
		//return 
		/**
	    	 * 返回barInfo
	    	 */
		return barInfo;
	}

//	@Override
//	protected void paintComponent(Graphics g) {
//		//opagus is not valid
//		setOpaque(false);
//		//size
//		Dimension d = getSize();
//		//get picture
//		ImageIcon ic = ImageUtil.getImageIcon(this.getClass(), iconurl);
//		//draw the picture
//		for (int x = 0; x < d.width; x += ic.getIconWidth()) {
//			for (int y = 0; y < d.height; y += ic.getIconHeight()) {
//				g.drawImage(ic.getImage(), x, y, null, null);
//			}
//		}
//	
//		//call super class to render component
//		super.paintComponent(g);
//	}

	/**
	 * Adds the specified component to the JOutlookBar and sets the bar's name
	 * 
	 * @param name
	 *            The name of the outlook bar
	 * @param icon
	 *            An icon to display in the outlook bar
	 * @param componenet
	 *            The component to add to the bar
	 */
	@SuppressWarnings("unchecked")
	public BarInfo addBar(String name, Icon icon, JComponent component) {
		//create bar info
	    	/**
	    	 * 创建一个BarInfo类型的对象barInfo
	    	 */
		BarInfo barInfo = new BarInfo(name, icon, component);
		//add event listen to bar button
		/**
	    	 * 获取barInfo按钮添加对象本身
	    	 */
		barInfo.getButton().addActionListener(this);
		//put barinfo into bar list
		/**
	    	 * 向Map集合bars添加元素
	    	 */
		this.bars.put(name, barInfo);
		//render component
		/**
	    	 * 调用render()方法
	    	 */
		render();
		//return
		/**
	    	 * 返回barInfo
	    	 */
		return barInfo;
	}

	/**
	 * Removes the specified bar from the JOutlookBar
	 * 
	 * @param name
	 *            The name of the bar to remove
	 */
	public void removeBar(String name) {
		//remove bar
	    	/**
	    	 * 向Map集合bars删除元素name
	    	 */
		this.bars.remove(name);
		//render component
		/**
	    	 * 调用render()方法
	    	 */
		render();
	}

//	/**
//	 * Returns the index of the currently visible bar (zero-based)
//	 * 
//	 * @return The index of the currently visible bar
//	 */
//	public int getVisibleBar() {
//		//visible status
//		return this.visibleBar;
//	}

//	/**
//	 * Programmatically sets the currently visible bar; the visible bar index
//	 * must be in the range of 0 to size() - 1
//	 * 
//	 * @param visibleBar
//	 *            The zero-based index of the component to make visible
//	 */
//	public void setVisibleBar(int visibleBar) {
//		if (visibleBar > 0 && visibleBar < this.bars.size() - 1) {
//			//set visible bar
//			this.visibleBar = visibleBar;
//			//render component
//			render();
//		}
//	}

//	/**
//	 * Causes the outlook bar component to rebuild itself; this means that it
//	 * rebuilds the top and bottom panels of bars as well as making the
//	 * currently selected bar's panel visible
//	 */
//	@SuppressWarnings("rawtypes")
//	public void render() {
//		// Compute how many bars we are going to have where
//		int totalBars = this.bars.size();
//		//top bars
//		int topBars = this.visibleBar + 1;
//		//bottom bars
//		int bottomBars = totalBars - topBars;
//
//		// Get an iterator to walk through out bars with
//		Iterator itr = this.bars.keySet().iterator();
//
//		// Render the top bars: remove all components, reset the GridLayout to
//		// hold to correct number of bars, add the bars, and "validate" it to
//		// cause it to re-layout its components
//		this.topPanel.removeAll();
//		GridLayout topLayout = (GridLayout) this.topPanel.getLayout();
//		topLayout.setRows(topBars);
//		BarInfo barInfo = null;
//		//iterator over bars
//		for (int i = 0; i < topBars; i++) {
//			String barName = (String) itr.next();
//			barInfo = (BarInfo) this.bars.get(barName);
//			this.topPanel.add(barInfo.getButton());
//		}
//		this.topPanel.validate();
//
//		// Render the center component: remove the current component (if there
//		// is one) and then put the visible component in the center of this
//		// panel
//		if (this.visibleComponent != null) {
//			this.remove(this.visibleComponent);
//		}
//		this.visibleComponent = barInfo.getComponent();
//		this.add(visibleComponent, BorderLayout.CENTER);
//
//		// Render the bottom bars: remove all components, reset the GridLayout
//		// to
//		// hold to correct number of bars, add the bars, and "validate" it to
//		// cause it to re-layout its components
//		this.bottomPanel.removeAll();
//		GridLayout bottomLayout = (GridLayout) this.bottomPanel.getLayout();
//		bottomLayout.setRows(bottomBars);
//		for (int i = 0; i < bottomBars; i++) {
//			String barName = (String) itr.next();
//			barInfo = (BarInfo) this.bars.get(barName);
//			this.bottomPanel.add(barInfo.getButton());
//		}
//		this.bottomPanel.validate();
//
//		// Validate all of our components: cause this container to re-layout its
//		// subcomponents
//		this.validate();
//	}
	
	/**
	 * Causes the outlook bar component to rebuild itself; this means that it
	 * rebuilds the top and bottom panels of bars as well as making the
	 * currently selected bar's panel visible
	 */
	@SuppressWarnings("rawtypes")
	public void render() {
		// Compute how many bars we are going to have where
	    	/**
	    	 * 获取bars的size赋给totalBars
	    	 */
		int totalBars = this.bars.size();

		// Get an iterator to walk through out bars with
		/**
	    	 * 获取bars的键集合并赋给itr
	    	 */
		Iterator itr = this.bars.keySet().iterator();

		// Render the total bars: remove all components
		// hold to correct number of bars, add the bars, and "validate" it to
		// cause it to re-layout its components
		/**
	    	 * 删除当前对象的所有元素
	    	 */
		this.removeAll();
		/**
	    	 * 定义BarInfo类型的对象
	    	 */
		BarInfo barInfo = null;
		//iterator over bars-+
		/**
	    	 * 循环遍历totalBars
	    	 */
		for (int i = 0; i < totalBars; i++) {
		    	//获取barName
			String barName = (String) itr.next();
			//根据barName获取barInfo信息
			barInfo = (BarInfo) this.bars.get(barName);
			//将barInfo按钮添加到当前对象中
			this.add(barInfo.getButton());
			/**
			 * 判断barInfo按钮是否展开
			 */
			if(barInfo.getButton().isExpand()){
				//展开
				this.add(barInfo.getComponent());
			}
		}
		//调用updateUI()方法更新UI信息
		this.updateUI();

//		// Render the center component: remove the current component (if there
//		// is one) and then put the visible component in the center of this
//		// panel
//		if (this.visibleComponent != null) {
//			this.remove(this.visibleComponent);
//		}
//		this.visibleComponent = barInfo.getComponent();
//		this.add(visibleComponent, BorderLayout.CENTER);
//
//		// Render the bottom bars: remove all components, reset the GridLayout
//		// to
//		// hold to correct number of bars, add the bars, and "validate" it to
//		// cause it to re-layout its components
//		this.bottomPanel.removeAll();
//		GridLayout bottomLayout = (GridLayout) this.bottomPanel.getLayout();
//		bottomLayout.setRows(bottomBars);
//		for (int i = 0; i < bottomBars; i++) {
//			String barName = (String) itr.next();
//			barInfo = (BarInfo) this.bars.get(barName);
//			this.bottomPanel.add(barInfo.getButton());
//		}
//		this.bottomPanel.validate();

		// Validate all of our components: cause this container to re-layout its
		// subcomponents
		//调用validate()方法验证
		this.validate();
	}

	/**
	 * Invoked when one of our bars is selected
	 */
	@SuppressWarnings("rawtypes")
	public void actionPerformed(final ActionEvent e) {
//		int currentBar = 0;
	    	/**
	    	 * 循环遍历Map集合bars的键集合
	    	 */
		for (Iterator i = this.bars.keySet().iterator(); i.hasNext();) {
			//iterator over different bar
		    	/**
		    	 * 获取barName
		    	 */
			String barName = (String) i.next();
			/**
		    	 * 根据barName获取BarInfo信息
		    	 */
			BarInfo barInfo = (BarInfo) this.bars.get(barName);
			/**
		    	 * 判断barInfo.getButton()与e.getSource()是否相等
		    	 */
			if (barInfo.getButton() == e.getSource()) {
				// Found the selected button
//				this.visibleBar = currentBar;
			    	/**
			    	 * 判断selectedBarInfo是否为空
			    	 */
				if(selectedBarInfo != null){
				    	/**
				    	 * 设置selectedBarInfo的按钮不是选中状态
				    	 */
					selectedBarInfo.getButton().setSelected(false);
				}
				selectedBarInfo = barInfo;
				//get bar button
				/**
			    	 * 获取barInfo的按钮是否展开
			    	 */
				boolean isExpand = barInfo.getButton().isExpand();
				/**
			    	 * 设置barInfo按钮不是展开的
			    	 */
				barInfo.getButton().setExpand(!isExpand);
				/**
			    	 * 设置barInfo按钮为选中状态
			    	 */
				barInfo.getButton().setSelected(true);
				/**
			    	 * 调用render()方法
			    	 */
				render();
				break;	//跳出
			}
//			currentBar++;
		}
		//call menu action listener
		/**
	    	 * 判断menuActionListener是否为空
	    	 */
		if(menuActionListener != null){
		    	/**
		    	 * 在工作进程中新开一个线程
		    	 */
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
				    	//调用run()方法执行menuActionListener.actionPerformed(e);
					menuActionListener.actionPerformed(e);
				}
			});
		}
	}

	/**
	 * Debug, dummy method
	 */
	@SuppressWarnings("serial")
	public static JComponent getDummyPanel(String name) {
		//panel is collapsible
	    	/**
	    	 * 创建JXCollapsiblePane对象
	    	 */
		JXCollapsiblePane panel = new JXCollapsiblePane() {
			@Override
			protected void paintComponent(Graphics g) {
			    	//设置控件透明
				setOpaque(false);
				//创建Dimension对象，封装了一个构件的高度和宽度
				Dimension d = getSize();
				//创建ImageIcon对象，它根据 Image绘制Icon
				ImageIcon ic = ImageUtil
						.getImageIcon(this.getClass(), iconurl);
				/**
				 * 循环遍历对象d的宽度
				 */
				for (int x = 0; x < d.width; x += ic.getIconWidth()) {
				    	/**
					 * 循环遍历对象d的高度
					 */
					for (int y = 0; y < d.height; y += ic.getIconHeight()) {					    
					    	/**
						 * 调用drawImage()方法，在指定位置并且按指定大小绘制指定的 Image
						 */
						g.drawImage(ic.getImage(), x, y, null, null);
					}
				}
				//调用父类的paintComponent方法进行界面重绘
				super.paintComponent(g);
			}
		};

		//create panel
		/**
		 * 创建JPanel对象
		 */
		JPanel p = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
			    	//设置控件透明
				setOpaque(false);
				//创建Dimension对象，封装了一个构件的高度和宽度
				Dimension d = getSize();
				//创建ImageIcon对象，它根据 Image绘制Icon
				ImageIcon ic = ImageUtil
						.getImageIcon(this.getClass(), iconurl);
				/**
				 * 循环遍历对象d的宽度
				 */
				for (int x = 0; x < d.width; x += ic.getIconWidth()) {
				    	/**
					 * 循环遍历对象d的高度
					 */
					for (int y = 0; y < d.height; y += ic.getIconHeight()) {
					    	/**
						 * 调用drawImage()方法，在指定位置并且按指定大小绘制指定的 Image
						 */
						g.drawImage(ic.getImage(), x, y, null, null);
					}
				}
				//调用父类的paintComponent方法进行界面重绘
				super.paintComponent(g);
			}
		};
		//set up layout
		/**
		 * 设置JPanel对象的layout
		 */
		p.setLayout(panel.getContentPane().getLayout());

		/**
		 * inner class to add tree
		 */
		panel.add(new JTree() {
			protected void paintComponent(Graphics g) {
			    	//设置控件透明
				setOpaque(false);
				//创建Dimension对象，封装了一个构件的高度和宽度
				Dimension d = getSize();
				//创建ImageIcon对象，它根据 Image绘制Icon
				ImageIcon ic = ImageUtil.getImageIcon(this.getClass(), iconurl);
				/**
				 * 循环遍历对象d的宽度
				 */
				for (int x = 0; x < d.width; x += ic.getIconWidth()) {
				    	/**
					 * 循环遍历对象d的高度
					 */
					for (int y = 0; y < d.height; y += ic.getIconHeight()) {
					    	/**
						 * 调用drawImage()方法，在指定位置并且按指定大小绘制指定的 Image
						 */
						g.drawImage(ic.getImage(), x, y, null, null);
					}
				}
				//调用父类的paintComponent方法进行界面重绘
				super.paintComponent(g);
			}
		});

		/**
		 * inner class to add tree
		 */
		panel.add(new JTree() {
			protected void paintComponent(Graphics g) {
			    	//设置控件透明
				setOpaque(false);
				//创建Dimension对象，封装了一个构件的高度和宽度
				Dimension d = getSize();
				//创建ImageIcon对象，它根据 Image绘制Icon
				ImageIcon ic = ImageUtil.getImageIcon(this.getClass(), iconurl);
				/**
				 * 循环遍历对象d的宽度
				 */
				for (int x = 0; x < d.width; x += ic.getIconWidth()) {
				    	/**
					 * 循环遍历对象d的高度
					 */
					for (int y = 0; y < d.height; y += ic.getIconHeight()) {
					    	/**
						 * 调用drawImage()方法，在指定位置并且按指定大小绘制指定的 Image
						 */
						g.drawImage(ic.getImage(), x, y, null, null);
					}
				}
				//调用父类的paintComponent方法进行界面重绘
				super.paintComponent(g);
			}
		});

		/**
		 * inner class to add tree
		 */
		panel.add(new JTree() {
			protected void paintComponent(Graphics g) {
			    	//设置控件透明
				setOpaque(false);
				//创建Dimension对象，封装了一个构件的高度和宽度
				Dimension d = getSize();
				//创建ImageIcon对象，它根据 Image绘制Icon
				ImageIcon ic = ImageUtil.getImageIcon(this.getClass(), iconurl);
				/**
				 * 循环遍历对象d的宽度
				 */
				for (int x = 0; x < d.width; x += ic.getIconWidth()) {
				    	/**
					 * 循环遍历对象d的高度
					 */
					for (int y = 0; y < d.height; y += ic.getIconHeight()) {
					    	/**
						 * 调用drawImage()方法，在指定位置并且按指定大小绘制指定的 Image
						 */
						g.drawImage(ic.getImage(), x, y, null, null);
					}
				}
				//调用父类的paintComponent方法进行界面重绘
				super.paintComponent(g);
			}
		});
		return panel;
	}

	/**
	 * Internal class that maintains information about individual Outlook bars;
	 * specifically it maintains the following information:
	 * 
	 * name The name of the bar button The associated JButton for the bar
	 * component The component maintained in the Outlook bar
	 */
	static class BarInfo {
	    
	    //定义一个静态的HashMap集合对象
	    private static HashMap< RenderingHints.Key, Object> renderingHints;
		
		 static {
		     	//创建一个HashMap对象renderingHints
		        renderingHints = new HashMap();
		        /**
		         * 向集合renderingHints添加元素
		         */
		        renderingHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION,
		                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		        renderingHints.put(RenderingHints.KEY_ANTIALIASING,
		                RenderingHints.VALUE_ANTIALIAS_ON);
		        renderingHints.put(RenderingHints.KEY_COLOR_RENDERING,
		                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		        renderingHints.put(RenderingHints.KEY_DITHERING,
		                RenderingHints.VALUE_DITHER_DISABLE);
		        renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS,
		                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		        renderingHints.put(RenderingHints.KEY_INTERPOLATION,
		                RenderingHints.VALUE_INTERPOLATION_BICUBIC );
		        renderingHints.put(RenderingHints.KEY_STROKE_CONTROL,
		                RenderingHints.VALUE_STROKE_PURE);
		        renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
		                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		    }
		    
		/**
		 * The name of this bar 名称
		 */
		private String name;

		/**
		 * The JButton that implements the Outlook bar itself 按钮
		 */
		private BarButton button;

		/**
		 * The component that is the body of the Outlook bar 组件
		 */
		private JComponent component;
		
		/**
		 * obj value 对象
		 */
		private Object obj;

		/**
		 * Creates a new BarInfo
		 * 
		 * @param name
		 *            The name of the bar
		 * @param component
		 *            The component that is the body of the Outlook Bar
		 */
		public BarInfo(String name, JComponent component) {
			//bar name 将参数name赋给当前对象属性name
			this.name = name;
			//inner component 将参数component赋给当前对象属性component
			this.component = component;
			//button 创建一个BarButton对象
			this.button = new BarButton();
			//设置对象文本，将name赋给文本
			this.button.setText(name);
			//border 设置对象边框为空
			this.button.setBorder(null);

		}

		/**
		 * Creates a new BarInfo
		 * 
		 * @param name
		 *            The name of the bar
		 * @param icon
		 *            JButton icon
		 * @param component
		 *            The component that is the body of the Outlook Bar
		 */
		public BarInfo(String name, final Icon icon, JComponent component) {
		    	//将参数name赋给当前对象属性name
			this.name = name; 
			//将参数component赋给当前对象属性component
			this.component = component;
			//button 创建一个BarButton对象
			this.button = new BarButton();
			//设置对象文本，将name赋给文本
			this.button.setText(name);
			//设置button样式
			this.button.setFont(new Font("微软雅黑", 0, NumberConstants.NUMBER_14));
			//border 设置对象边框为空
			this.button.setBorder(null);

		}
		
		/**
		 * 
		 * 设置菜单高度
		 * @author 102246-foss-shaohongliang
		 * @date 2013-2-28 上午11:12:36
		 */
		public void setHeight(int height){
		    	//创建Dimension对象，封装了一个构件的高度和宽度
			Dimension dimension = new Dimension(NumberConstants.NUMBER_180, height);
			//设置最好的大小
			this.button.setPreferredSize(dimension);
//			this.button.setMaximumSize(dimension);
			//设置最小的尺寸
			this.button.setMinimumSize(dimension);
			//重绘此组件
			this.button.repaint();
		}
		 
		/**
		 * Returns the name of the bar
		 * 
		 * @return The name of the bar
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * Sets the name of the bar
		 * 
		 * @param The
		 *            name of the bar
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Returns the outlook bar JButton implementation
		 * 
		 * @return The Outlook Bar JButton implementation
		 */
		public BarButton getButton() {
			return this.button;
		}

		
		/**
		 * @return the obj
		 */
		public Object getObject() {
			return obj;
		}

		
		/**
		 * @param obj the obj to set
		 */
		public void setObject(Object obj) {
			this.obj = obj;
		}

		/**
		 * Returns the component that implements the body of this Outlook Bar
		 * 
		 * @return The component that implements the body of this Outlook Bar
		 */
		public JComponent getComponent() {
			return this.component;
		}

		/**
		 * 
		 * set menu bar background
		 * @author 102246-foss-shaohongliang
		 * @date 2013-2-28 上午10:47:47
		 */
		public void setBackground(ImageIcon unSelected, ImageIcon selected) {
		    	//设置非选中状态的背景为传来的参数unSelected
			button.setUnselectedBackground(unSelected);
			//设置选中状态的背景为传来的参数selected
			button.setSelectedBackground(selected);
			/**
			 * 判断unSelected是否为空，如果unSelected为空，则设置高度为36，否则为非选中状态的图标高度
			 */
			if(unSelected == null){
				setHeight(NumberConstants.NUMBER_36);
			}else{
				setHeight(unSelected.getIconHeight());
			}
		}
		

		/**
		 * 
		 * set menu bar icon
		 * @author 102246-foss-shaohongliang
		 * @date 2013-2-28 上午10:47:47
		 */
		public void setIcon(ImageIcon expand, ImageIcon shrink) {
		    	//设置展开图标
			button.setExpandIcon(expand);
			//设置收缩图标
			button.setShrinkIcon(shrink);
		}
		
		/**
		 * 
		 * 工具栏按钮
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-1 下午1:07:28
		 */
		public class BarButton extends JButton{
		    	//创建一个序列号
			private static final long serialVersionUID = -7207871794627927793L;
			//定义一个int型的常量
			//根据sonar(Final属性可以使用Static)修改
			private static final int TEXT_MARGIN = NumberConstants.NUMBER_40;

		    /**
		     * 默认的按钮被禁止时文本的颜色
		     */
		    private final Color DEFAULT_DISABLED_FOREGROUND = new Color(NumberConstants.NUMBER_192, NumberConstants.NUMBER_192, NumberConstants.NUMBER_192);

		    /**
		     * 未选中背景
		     */
			private ImageIcon unselectedBackground;

			/**
			 * 选中背景
			 */
			private ImageIcon selectedBackground;

			/**
			 * 展开图标
			 */
			private ImageIcon expandIcon;

			/**
			 * 收缩图标
			 */
			private ImageIcon shrinkIcon;
			
			/**
			 * 是否展开
			 */
			private boolean isExpand;
		    
			@Override
			public void paintComponent(Graphics g) {
			//创建Graphics2D对象 
		        Graphics2D g2d = (Graphics2D) g.create();
		        g2d.addRenderingHints(renderingHints);
		        //paint background
		        /**
		         * 调用isSelected()方法判断其返回值是否选中，如果是，则将selectedBackground赋给bgIcon，否则将unselectedBackground赋给bgIcon
		         */
		        ImageIcon bgIcon = isSelected()?selectedBackground:unselectedBackground;
		        /**
		         * 检查bgIcon是否由内存生成,如果不是，则调用drawImage()方法，在指定位置并且按指定大小绘制指定的 Image
		         */
		        if(!isImageRGB(bgIcon)){
		        	g2d.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
		        }
		        /**
		         * 调用isExpand()方法判断是否展开，如果是，则将expandIcon赋给imageIcon，否则将shrinkIcon赋给imageIcon
		         */
		        ImageIcon imageIcon = isExpand()?expandIcon:shrinkIcon;
		        /**
		         * 检查imageIcon是否由内存生成,如果不是，则调用drawImage()方法，在指定位置并且按指定大小绘制指定的 Image
		         */
		        if(!isImageRGB(imageIcon)){
		        	g2d.drawImage(imageIcon.getImage(), NumberConstants.NUMBER_8, (getHeight()-imageIcon.getIconHeight())/2, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
		        }
		        	/**
		        	 * 在按钮上绘制文本
		        	 */
				drawText(g2d);
			}

			/**
			 * 
			 * 收缩按钮图标
			 * @author 102246-foss-shaohongliang
			 * @date 2013-2-28 下午12:12:11
			 */
			public void setShrinkIcon(ImageIcon shrinkIcon) {
				this.shrinkIcon = shrinkIcon;
			}

			/**
			 * 
			 * 展开按钮图标
			 * @author 102246-foss-shaohongliang
			 * @date 2013-2-28 下午12:12:11
			 */
			public void setExpandIcon(ImageIcon expandIcon) {
				this.expandIcon = expandIcon;
			}
			
			/**
			 * 
			 * 是否展开
			 * @author 102246-foss-shaohongliang
			 * @date 2013-2-28 下午12:12:11
			 */
			public boolean isExpand() {
				return isExpand;
			}

			/**
			 * 
			 * 选中后背景色
			 * @author 102246-foss-shaohongliang
			 * @date 2013-2-28 下午12:11:51
			 */
			public void setSelectedBackground(ImageIcon selectedBackground) {
				this.selectedBackground = selectedBackground;
			}
			
			/**
			 * 
			 * 未选中时背景色
			 * @author 102246-foss-shaohongliang
			 * @date 2013-2-28 下午12:11:51
			 */
			public void setUnselectedBackground(ImageIcon unselectedBackground) {
				this.unselectedBackground = unselectedBackground;
				
			}

			/**
			 * 
			 * 设置是否展开
			 * @author 102246-foss-shaohongliang
			 * @date 2013-2-28 下午12:11:37
			 */
			public void setExpand(boolean isExpand) {
				this.isExpand = isExpand;
			}
			/**
			 * 在按钮上绘制文本
			 * @param g2d
			 */
			private void drawText(final Graphics2D g2d) {
			    	//获取文本
				String text = getText();
			/**
			 * 判断文本是否为空，如果为空或为空字符串就返回
			 */
		        if(text == null || text.isEmpty()) {
		            return;
		        }
		        //获取字体样式
		        Font font = getFont();
		        //创建FontMetrics对象fm，该对象封装将在特定屏幕上呈现特定字体的有关信息
		        FontMetrics fm = getFontMetrics(font);
//		        int x = (getWidth() - fm.stringWidth(text)) / 2;
		        int x = TEXT_MARGIN;
		        int y = getHeight() / 2 + fm.getHeight() / NumberConstants.NUMBER_4;
		        /**
		         * 调用isEnabled()判断是否启用，然后根据相应的条件调用setPaint()方法设置色彩模式
		         */
		        if(isEnabled()) {
		            g2d.setPaint(getForeground());
		        } else {
		            g2d.setPaint(DEFAULT_DISABLED_FOREGROUND);
		        }
		        //调用drawString()方法在指定位置并且用指定的对象绘制指定的文本字符串
		        g2d.drawString(text, x, y);
		    }


			/**
			 * 
			 * 检查图片是否由内存生成
			 * @author 102246-foss-shaohongliang
			 * @date 2013-2-28 下午3:16:19
			 */
			public boolean isImageRGB(ImageIcon imageIcon) {
			    	/**
			    	 * 判断参数imageIcon是否为空，如果为空，则返回true
			    	 */
				if(imageIcon == null)
					return true;
				//获取Image信息
				Image image = imageIcon.getImage();
				/**
				 * 判断image是否为空并且image是否是BufferedImage的一个实例
				 */
				if(image!= null && image instanceof BufferedImage){
				    	//获取图片类型
					int imageType = ((BufferedImage)imageIcon.getImage()).getType();
					/**
					 * 判断图片是否为红色，如果是，则返回true，否则返回false
					 */
					return imageType == BufferedImage.TYPE_INT_RGB;
				}else{
				    	//返回false
					return false;
				}
			}

		
		}
	}

	/**
	 * 
	 * 菜单点击事件
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-1 下午1:11:20
	 */
	public void addMenuActionListener(ActionListener menuActionListener) {
		this.menuActionListener = menuActionListener;
	}
}