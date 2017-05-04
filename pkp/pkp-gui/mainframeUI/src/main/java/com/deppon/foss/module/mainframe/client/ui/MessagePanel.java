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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;

import com.deppon.foss.base.util.define.NumberConstants;

 
/**
 * @功能：消息板控件
 * @1、可以通过鼠标滚动轴进行上下查看信息
 * @2、可以通过鼠标拖拽滚动条上下查看信息
 * @3、实现边框圆角效果
 * @4、实现透明效果
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:49:59,content:
 * @author dp-yangtong
 * @date 2012-10-12 上午11:49:59
 * @since
 * @version
 */
public class MessagePanel extends JComponent {

	/**
	 * 40
	 */
	private static final int FORTY = 40;
	/**
	 * 300
	 */
	private static final int THREEHUNDRED = 300;
	/**
	 * 8
	 */
	private static final int EIGHT = 8;
	/**
	 * 0.5 FOR COLOR DEGREE
	 */
	private static final float COLORDEGREE = 0.5f;
	/**
	 * 18
	 */
	private static final int EIGHTEEN = 18;
	/**
	 * 113
	 */
	private static final int ONEONETHREE = 113;
	/**
	 * 90
	 */
	private static final int NINETY = 90;
	/**
	 * 74
	 */
	private static final int SEVENTYFOUR = 74;
	/**
	 * 15
	 */
	private static final int FIFTEEN = 15;
	/**
	 * 30
	 */
	private static final int THIIRTY = 30;
	/**
	 * THREE
	 */
	private static final int THREE = 3;
	/**
	 * TEN
	 */
	private static final int TEN = 10;
	/**
	 * SIX
	 */
	private static final int SIX = 6;
	/**
	 * 20
	 */
	private static final int TWENTY = 20;
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 7049192876836921123L;
	/**
	 * 控制title
	 */
	private String title;// 控制title
	/**
	 *  控制宽度
	 */
	private int width;// 控制宽度
	/**
	 * 控制长度
	 */
	private int height;// 控制长度
	/**
	 * 内部：控制循环初始值
	 */
	private int listLocation = 0;// 内部：控制循环初始值
	/**
	 * 内部：控制指针位置
	 */
	private int location = 0;// 内部：控制指针位置
	/**
	 * 控制x坐标位置
	 */
	private int x;// 控制x坐标位置
	/**
	 * 控制y坐标位置
	 */
	private int y;// 控制y坐标位置
	/**
	 * title 字体
	 */
	private Font titleFont;// title 字体
	/**
	 * title 颜色
	 */
	private Color titleColor;// title 颜色
	/**
	 * 消息 字体
	 */
	private Font messageFont;// 消息 字体
	/**
	 * 消息 颜色
	 */
	private Color messageColor;// 消息 颜色
	/**
	 * 控制滚动条
	 */
	private int controlScrollBar = 0;// 控制滚动条
	/**
	 * x length positon
	 */
	private int recl;
	/**
	 * y postion
	 */
	private int recy = NumberConstants.NUMBER_90;
	/**
	 * x postion
	 */
	private int recx;

	/**
	 * length list 创建String型的List集合
	 */
	private List<String> l = new ArrayList<String>();

	

	/**
	 * 
	 * @功能：默认messagePanel值
	 * @时间：2012-8-11
	 * @作者：linws
	 */
	public MessagePanel() {
	    	/**
	    	 * 加载初始值
	    	 */
		init("message", NumberConstants.NUMBER_450, NumberConstants.NUMBER_50, NumberConstants.NUMBER_9, NumberConstants.NUMBER_100);
	}
	
	/**
	 * 构造出有题目的消息板
	 * 
	 * @param title
	 */
	public MessagePanel(String title) {
	    	/**
	    	 * 加载初始值
	    	 */
		init(title, NumberConstants.NUMBER_450, NumberConstants.NUMBER_50, NumberConstants.NUMBER_9, NumberConstants.NUMBER_100);
	}

	/**
	 * 
	 * @功能：消息框，提供标题和高度
	 * @title：消息框标题
	 * @height:消息框高度
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public MessagePanel(String title, final int height) {
	    	/**
	    	 * 加载初始值
	    	 */
		init(title, NumberConstants.NUMBER_450, height, NumberConstants.NUMBER_9, NumberConstants.NUMBER_100);
	}

	/**
	 * 
	 * @功能：消息框，构造方法
	 * @title：消息框标题
	 * @x:框所在X坐标轴
	 * @y:框所在Y坐标轴
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public MessagePanel(String title, int x, int y) {
	    	/**
	    	 * 加载初始值
	    	 */
		init(title, NumberConstants.NUMBER_450, NumberConstants.NUMBER_50, x, y);
	}

	/**
	 * 
	 * @功能：消息框，构造方法
	 * @title：消息框标题
	 * @height:消息框高度
	 * @x:框所在X坐标轴
	 * @y:框所在Y坐标轴
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public MessagePanel(String title, final int height, int x, int y) {
	    	/**
	    	 * 加载初始值
	    	 */
		init(title, NumberConstants.NUMBER_450, height, x, y);
	}

	/**
	 * 
	 * @功能：消息框，构造方法
	 * @title：消息框标题
	 * @width：消息框大小
	 * @height:消息框高度
	 * @x:框所在X坐标轴
	 * @y:框所在Y坐标轴
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public MessagePanel(String title, final int width, final int height, int x,
			int y) {
	    	/**
	    	 * 加载初始值
	    	 */
		init(title, width, height, x, y);
	}

	/**
	 * 添加显示信息
	 * 
	 * @param message
	 */
	public void addMessage(String message) {
	    	/**
	    	 * 将参数message添加到集合里面
	    	 */
		l.add(message);
	}

	/**
	 * 
	 * @功能：添加一个集合
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public void addMessages(Collection<String> c) {
	    	/**
	    	 * 将参数c集合里面的所有元素添加到l集合里面
	    	 */
		l.addAll(c);

	}

	/**
	 * 
	 * @功能：删除所有信息
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public void deleteAllMessage() {
		/**
		 * 如果你想把集合内的所有的元素都删除掉，请用集合的clear方法，
		 * 而不是c removeAll 方法。调用c removeAll去清空集合，
		 * 会清除的不干净，容易产生错误，可能会抛出ConcurrentModificationException异常。
		 */
		l.clear();//removeAll
	}

	/**
	 * 
	 * @功能：删除指定信息
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public void deleteMessage(int index) {
	    	/**
	    	 * 根据索引index删除元素
	    	 */
		l.remove(index);
	}

	/**
	 * 
	 * @功能：通过对象删除信息
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public void deleteMessage(Object o) {
	    	/**
	    	 * 删除对象o
	    	 */
		l.remove(o);
	}

	/**
	 * 
	 * @功能：获取标题的字体
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public Font getTitleFont() {	
	    	/**
	    	 * 返回titleFont
	    	 */
		return titleFont;
	}

	/**
	 * 
	 * @功能：设置标题的字体
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public void setTitleFont(Font titleFont) {
	    	/**
	    	 * 将参数titleFont的值赋给当前属性titleFont
	    	 */
		this.titleFont = titleFont;
	}

	/**
	 * 
	 * @功能：获取标题字体颜色
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public Color getTitleColor() {
	    	/**
	    	 * 返回titleColor
	    	 */
		return titleColor;
	}

	/**
	 * 
	 * @功能：设置标题字体颜色
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public void setTitleColor(Color titleColor) {
	    	/**
	    	 * 将参数titleColor的值赋给当前属性titleColor
	    	 */
		this.titleColor = titleColor;
	}

	/**
	 * 
	 * @功能：获取消息字体
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public Font getMessageFont() {
	    	/**
	    	 * 返回messageFont
	    	 */
		return messageFont;
	}

	/**
	 * 
	 * @功能：设置消息字体
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public void setMessageFont(Font messageFont) {
	    	/**
	    	 * 将参数messageFont赋给当前属性messageFont
	    	 */
		this.messageFont = messageFont;
	}

	/**
	 * 
	 * @功能：获取消息字体颜色
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public Color getMessageColor() {
	    	/**
	    	 * 返回messageColor
	    	 */
		return messageColor;
	}

	/**
	 * 
	 * @功能：设置消息字体颜色
	 * @时间：2012-8-13
	 * @作者：linws
	 */
	public void setMessageColor(Color messageColor) {
	    	/**
	    	 * 将参数messageColor赋给当前属性messageColor
	    	 */
		this.messageColor = messageColor;
	}

	/**
	 * 
	 * @功能：加载初始值
	 * @title：标题
	 * @width：大小
	 * @height:高度
	 * @x:框所在X坐标轴
	 * @y:框所在Y坐标轴
	 * @时间：2012-8-11
	 * @作者：linws
	 */
	private void init(String title, final int width, final int height,
			final int x, final int y) {
	    	/**
	    	 * 设置背景
	    	 */
		setBackground(Color.white);
		/**
	    	 * 将参数的值title、width等赋给当前属性title、width等
	    	 */
		this.title = title;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.recy = y + TWENTY;
		
		/**
		 * inner class listener for mouse event
		 */
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
			    	mouseWheelMovedAction(width, height, x, y, e);
			}
		});
		// 添加鼠标监听器
		addMouseListener(new MouseAdapter() {

			public void mouseExited(MouseEvent e) {
			    	//为controlScrollBar赋值
				controlScrollBar = 0;
				/**
			    	 * 调用父节点的repaint()重绘此组件
			    	 */
				getParent().repaint();

			}

			public void mouseEntered(MouseEvent e) {
			    		//为controlScrollBar赋值
					controlScrollBar = 1;
					/**
				    	 * 调用父节点的repaint()重绘此组件
				    	 */
					getParent().repaint();
	
			}

		});
		// 添加鼠标运动事件监听器
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
			    	/**
			    	 * 调用父类的鼠标拖拽事件
			    	 */
				super.mouseDragged(e);
				mouseDraggedAction(height, y, e);

			}

		});

	}

	/**
	 * 
	 * @功能：绘画消息板控件
	 * @时间：2012-8-11
	 * @作者：linws
	 */
	public void paint(Graphics g) {
	    	/**
	    	 * 创建Graphics2D对象g2
	    	 */
		Graphics2D g2=(Graphics2D) g;
		
		/**
	    	 * 设置对象g2的RenderingHint属性
	    	 */
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		/**
	    	 * 创建Font对象plainFont
	    	 */
		Font plainFont = new Font("宋体", Font.BOLD, FIFTEEN);
		/**
	    	 * 判断获取标题的字体getTitleFont()是否为空如果，如果不为空，则赋给plainFont
	    	 */
		if (getTitleFont() != null) {
			plainFont = getTitleFont();
		}
		/**
	    	 * 创建一个AttributedString对象as
	    	 */
		AttributedString as;
		as = new AttributedString(title);
		/**
	    	 * 为对象as添加属性TextAttribute.FONT
	    	 */
		as.addAttribute(TextAttribute.FONT, plainFont);
		/*
		 * //设置下滑线
		 */
		/**
	    	 * 判断获取标题字体颜色getTitleColor()是否为空，如果不为空，则设置g的颜色为getTitleColor()，否则，新建一个Color对象
	    	 */
		if (getTitleColor() != null) {
			g.setColor(getTitleColor());// 画线颜色
		} else {
			g.setColor(new Color(SEVENTYFOUR, NINETY, ONEONETHREE));// 画线颜色
		}
		/**
	    	 * 调用drawString绘制指定的文本字符串
	    	 */
		g2.drawString(as.getIterator(), x, y - TWENTY);
		listLocation = location;
		/**
	    	 * 从0一直循环到SIX
	    	 */
		for (int i = 0; i < SIX; i++) {
			String s = "   ";
			/**
		    	 * 判断listLocation是否小于集合l的size()
		    	 */
			if (listLocation < l.size()) {
			    	/**
			    	 * 判断集合l是否包含listLocation，如果是，则获取并赋给字符串s
			    	 */
				if (l.get(listLocation) != null) {					
					//s = l.get(listLocation).toString();
					//根据sonar(性能 - 方法调用了String的toString()方法 )修改
					s = l.get(listLocation);
				}
			}
			/**
		    	 * 创建一个AttributedString对象
		    	 */
			as = new AttributedString(s);
			/**
		    	 * 判断获取消息字体getMessageFont()是否为空，如果不为空，则赋给plainFont，否则，创建一个新的Font对象
		    	 */
			if (getMessageFont() != null) {
				plainFont = getMessageFont();// 设置字体
			} else {
				plainFont = new Font("宋体", Font.BOLD, FIFTEEN);
			}
			/**
		    	 * 为对象as添加属性
		    	 */
			as.addAttribute(TextAttribute.FONT, plainFont);
			/**
		    	 * 判断获取消息字体颜色getMessageColor()是否为空，如果不为空，则设置对象g的Color为getMessageColor()
		    	 */
			if (getMessageColor() != null) {
				g.setColor(getMessageColor());// 画线颜色
			} else {
				g.setColor(Color.BLACK);// 画线颜色
			}
			/**
		    	 * 调用drawString绘制指定的文本字符串
		    	 */
			g2.drawString(as.getIterator(), x + 1, height * (i + 1) + y - FIFTEEN);// 画String字符
			/**
		    	 * 判断i是否小于5,
		    	 */
			if (i < NumberConstants.NUMBER_5) {
			    	/**
			    	 * 为g对象设置Color
			    	 */
				g.setColor(Color.lightGray);// 画线颜色
				/**
			    	 * 调用对象g2的drawLine()方法
			    	 */
				g2.drawLine(x, height * (i + 1) + y, width + x, height
						* (i + 1) + y);
			}
			//listLocation加1
			listLocation++;
		}
		/**
	    	 * 设置对象g的Color
	    	 */
		g.setColor(Color.lightGray);// 设置边框颜色
		g.drawRoundRect(x, y, width, height * SIX, EIGHTEEN, EIGHTEEN); // 画一个圆角矩形
		// 消除锯齿
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.addRenderingHints(rh);
		// 半透明
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				COLORDEGREE));
		/**
	    	 * 设置获取变量recx
	    	 */
		recx = width + x - EIGHT;
		/**
	    	 * 判断controlScrollBar是否等于1
	    	 */
		if (controlScrollBar == 1) {
		    	
			recl = THREEHUNDRED / (l.size() - 1);// 根据项确认位移
			/**
		    	 * 绘画进度条
		    	 */
			paintScrollBar(g, recx, y + TWENTY, height * SIX, recy);
		}
	}

	/**
	 * 
	 * @功能：绘画进度条
	 * @时间：2012-8-11
	 * @作者：linws
	 */
	private void paintScrollBar(Graphics g, int lineX, int lineY, int height,
			int recY) {
	    	/**
	    	 * 创建 Graphics2D对象可用于捕获对存储器的呈现操作并存入图形元文件
	    	 */
		Graphics2D g2 = (Graphics2D) g;
		//为对象g2设置Color
		g2.setColor(Color.BLACK);
		/**
	    	 * 调用对象g2的drawLine()方法
	    	 */
		g2.drawLine(lineX, lineY, lineX, lineY + height - FORTY);
		/**
		 * 调用fillRect()方法使用 fillStyle 属性所指定的颜色、渐变和模式来填充指定的矩形
		 */
		g2.fillRect(lineX - THREE, recY, SIX, TEN);
	}

	private void mouseWheelMovedAction(int width, int height,
						final int x, final int y, MouseWheelEvent e) {
					/**
					 * 获取事件触发的指向
					 */
			Point p = e.getPoint();
			/**
					 * 判断指向的x、y坐标是否大于参数x、y
					 */
			if (p.x > x && p.x < x + width && p.y > y
						&& p.y < y + height * SIX) {
						/**
						 * 判断滚轮转动的方向与单位数是否小于0
						 */
					if (e.getWheelRotation() < 0) {
					    	/**
					    	 * 如果location大于0，则location减1
					    	 */
						if (location > 0) {
							location--;
						}
					/**
					* 判断滚轮转动的方向与单位数是否大于0
					*/
					}else if (e.getWheelRotation() > 0) {
					    	/**
					    	 * 如果location大于集合类l的size()，则location加1
					    	 */
						if (location < l.size() - 1) {
							location++;
						}
					}
			}
			/**
					 * 计算变量recl和recy
					 */
			recl = (height * SIX - TEN) / l.size() ;
			recy = (y + TWENTY + recl * location);
			/**
					 * 调用父节点的repaint()重绘此组件
					 */
			getParent().repaint();
		}
	
	private void mouseDraggedAction(final int height, final int y, MouseEvent e) {
		/**
			 * 获取当事件触发使获取的指向
			 */
		Point p = e.getPoint();
		/**
			 * 判断指向的x与y坐标和计算后的数据相比较
			 */
		if (p.x >= recx - THREE && p.x <= recx + THREE && p.y >= recy - TEN
				&& p.y <= recy + TEN && p.y <= y + height * SIX - THIIRTY
				&& p.y >= y + TWENTY) {

			recy = p.y;
			/**
		    	 * 判断指向的y坐标是否大于等于recl乘以(location + 1)加参数y加TWENTY并且指向的y坐标是否小于等于recl乘以(l.size() - 1)加参数y加TWENTY
		    	 */
			if (p.y >= recl * (location + 1) + y + TWENTY
					&& p.y <= recl * (l.size() - 1) + y + TWENTY) {
			    	//Location加1
				location++;
			/**
			* 判断指向的y坐标是否小于等于recl乘以(location - 1)加参数y加TWENTY并且指向的y坐标是否大于等于y加TWENTY
			*/
			} else if (p.y <= recl * (location - 1) + y + TWENTY
					&& p.y >= y + TWENTY) {
			    	//Location减1
				location--;
			}
			/**
		    	 * 调用父节点的repaint()重绘此组件
		    	 */
			getParent().repaint();
		}
	}
}