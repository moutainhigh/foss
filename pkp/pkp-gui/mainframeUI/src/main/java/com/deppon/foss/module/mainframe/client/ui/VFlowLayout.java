/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: mainframeUI/src/main/java/com/deppon/foss/module/mainframe/client/ui/VFlowLayout.java
 * 
 * FILE NAME        	: VFlowLayout.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.mainframe.client.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.deppon.foss.base.util.define.NumberConstants;

/**
 * 垂直流布局
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:DP-shaohongliang,date:2012-10-15 上午9:40:02, </p>
 * @author DP-shaohongliang
 * @date 2012-10-15 上午9:40:02
 * @since
 * @version
 */
public class VFlowLayout extends FlowLayout
{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Specify alignment top.
	 */
	public static final int TOP = 0;

	/**
	 * Specify a middle alignment.
	 */
	public static final int MIDDLE = 1;

	/**
	 * Specify the alignment to be bottom.
	 */
	public static final int BOTTOM = 2;

	/**
	 * 声明变量hgap、vgap、hfill、vfill
	 */
	int hgap;
	int vgap;
	boolean hfill;
	boolean vfill;

	public static void main(String[] args)
	{
	    	
		System.out.println("Just for test ...");
		/**
		 * 创建JFrame对象frame
		 */
		JFrame frame = new JFrame();
		/**
		 * 设置布局
		 */
		frame.setLayout(new FlowLayout());
		/**
		 * 创建JPanel对象panel
		 */
		JPanel panel = new JPanel();
		/**
		 * 设置默认的关闭窗口
		 */
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/**
		 * 设置frame的Bounds属性
		 */
		frame.setBounds(0, 0, NumberConstants.NUMBER_600, NumberConstants.NUMBER_600);
		/**
		 * 设置panel对象的布局
		 */
		panel.setLayout(new VFlowLayout());
		/**
		 * 向frame里面添加元素panel
		 */
		frame.add(panel);
		
		//声明int型变量，默认值为0
		int i = 0;
		/**
		 * 创建JButton对象向panel里面添加元素
		 */
		panel.add(new JButton(String.valueOf(i++)));
		panel.add(new JButton(String.valueOf(i++)));
		panel.add(new JButton(String.valueOf(i++)));
		panel.add(new JButton(String.valueOf(i++)));
		panel.add(new JButton(String.valueOf(i++)));
		panel.add(new JButton(String.valueOf(i++)));
		panel.add(new JButton(String.valueOf(i++)));
		panel.add(new JButton(String.valueOf(i++)));
		panel.add(new JButton(String.valueOf(i++)));
		panel.add(new JButton(String.valueOf(i++)));
		panel.add(new JButton(String.valueOf(i++)));
		panel.setBorder(BorderFactory.createLineBorder(Color.yellow));
		/**
		 * 设置frame为可显示的
		 */
		frame.setVisible(true);
	}
	
	/**
	 * Construct a new VerticalFlowLayout with a middle alignment, and the fill
	 * to edge flag set.
	 */
	public VFlowLayout()
	{
	    	/**
		 * 调用构造函数
		 */
		this(TOP, NumberConstants.NUMBER_5, NumberConstants.NUMBER_5, true, false);
	}

	/**
	 * Construct a new VerticalFlowLayout with a middle alignment.
	 * 
	 * @param hfill
	 *            the fill to edge flag
	 * @param vfill
	 *            the vertical fill in pixels.
	 */
	public VFlowLayout(boolean hfill, boolean vfill)
	{
	    	/**
		 * 调用构造函数
		 */
		this(TOP, NumberConstants.NUMBER_5, NumberConstants.NUMBER_5, hfill, vfill);
	}

	/**
	 * Construct a new VerticalFlowLayout with a middle alignment.
	 * 
	 * @param align
	 *            the alignment value
	 */
	public VFlowLayout(int align)
	{
	    	/**
		 * 调用构造函数
		 */
		this(align, NumberConstants.NUMBER_5, NumberConstants.NUMBER_5, true, false);
	}

	/**
	 * Construct a new VerticalFlowLayout.
	 * 
	 * @param align
	 *            the alignment value
	 * @param hfill
	 *            the horizontalfill in pixels.
	 * @param vfill
	 *            the vertical fill in pixels.
	 */
	public VFlowLayout(int align, boolean hfill, boolean vfill)
	{
	    	/**
		 * 调用构造函数
		 */
		this(align, NumberConstants.NUMBER_5, NumberConstants.NUMBER_5, hfill, vfill);
	}

	/**
	 * Construct a new VerticalFlowLayout.
	 * 
	 * @param align
	 *            the alignment value
	 * @param hgap
	 *            the horizontal gap variable
	 * @param vgap
	 *            the vertical gap variable
	 * @param hfill
	 *            the fill to edge flag
	 * @param vfill
	 *            true if the panel should vertically fill.
	 */
	public VFlowLayout(int align, int hgap, int vgap, boolean hfill, boolean vfill)
	{
	    	/**
	    	 * 设置标签的对齐方式
	    	 */
		setAlignment(align);
		/**
	    	 * 将参数hgap、vgap、hfill、vfill赋给当前属性hgap、vgap、hfill、vfill
	    	 */
		this.hgap = hgap;
		this.vgap = vgap;
		this.hfill = hfill;
		this.vfill = vfill;
	}

	/**
	 * Returns the preferred dimensions given the components in the target
	 * container.
	 * 
	 * @param target
	 *            the component to lay out
	 */
	public Dimension preferredLayoutSize(Container target)
	{
	    	/**
		 * 创建Dimension对象tarsiz，该对象封装了一个构件的高度和宽度
		 */
		Dimension tarsiz = new Dimension(0, 0);
		/**
		 * 遍历循环target.getComponentCount()
		 */
		for (int i = 0; i < target.getComponentCount(); i++)
		{
		    	/**
			 * 获取Component组件
			 */
			Component m = target.getComponent(i);
			/**
			 * 判断m.isVisible()是否为true
			 */
			if (m.isVisible())
			{
			    	/**
				 * 获取Dimension对象d
				 */
				Dimension d = m.getPreferredSize();
				/**
				 * 获取tarsiz宽度
				 */
				tarsiz.width = Math.max(tarsiz.width, d.width);
				/**
				 * 判断 i是否大于0，如果大约0，则tarsiz宽度=tarsiz高度加vgap
				 */
				if (i > 0)
				{
					tarsiz.height += vgap;
				}
				//tarsiz高度=tarsiz高度加对象d的高度
				tarsiz.height += d.height;
			}
		}
		/**
		 * 获取注入对象Insets
		 */
		Insets insets = target.getInsets();
		/**
		 * 计算并设置tarsiz的宽和高
		 */
		tarsiz.width += insets.left + insets.right + hgap * 2;
		tarsiz.height += insets.top + insets.bottom;
		/**
		 * 返回tarsiz
		 */
		return tarsiz;
	}

	/**
	 * Returns the minimum size needed to layout the target container.
	 * 
	 * @param target
	 *            the component to lay out.
	 * @return the minimum layout dimension.
	 */
	public Dimension minimumLayoutSize(Container target)
	{
	    	/**
		 * 创建Dimension对象tarsiz
		 */
		Dimension tarsiz = new Dimension(0, 0);
		/**
		 * 循环遍历target.getComponentCount()
		 */
		for (int i = 0; i < target.getComponentCount(); i++)
		{
		    	/**
			 * 获取Component组件
			 */
			Component m = target.getComponent(i);
			/**
			 * 判断m.isVisible()是否为true
			 */
			if (m.isVisible())
			{
			    	/**
				 * 获取Dimension对象d
				 */
				Dimension d = m.getMinimumSize();
				/**
				 * 获取tarsiz宽度
				 */
				tarsiz.width = Math.max(tarsiz.width, d.width);
				/**
				 * 判断 i是否大于0，如果大约0，则tarsiz宽度=tarsiz高度加vgap
				 */
				if (i > 0) 
				{
					tarsiz.height += vgap;
				}
				//tarsiz高度=tarsiz高度加对象d的高度
				tarsiz.height += d.height;
			}
		}
		/**
		 * 获取注入对象Insets
		 */
		Insets insets = target.getInsets();
		/**
		 * 计算并设置tarsiz的宽和高
		 */
		tarsiz.width += insets.left + insets.right + hgap * 2;
		tarsiz.height += insets.top + insets.bottom;
		/**
		 * 返回tarsiz
		 */
		return tarsiz;
	}

	/**
	 * Set true to fill vertically.
	 * 
	 * @param vfill
	 *            true to fill vertically.
	 */
	public void setVerticalFill(boolean vfill)
	{
	    	/**
		 * 将参数vfill赋给当前属性vfill
		 */
		this.vfill = vfill;
	}

	/**
	 * Returns true if the layout vertically fills.
	 * 
	 * @return true if vertically fills the layout using the specified.
	 */
	public boolean getVerticalFill()
	{
	    	/**
		 * 返回当前属性vfill
		 */
		return vfill;
	}

	/**
	 * Set to true to enable horizontally fill.
	 * 
	 * @param hfill
	 *            true to fill horizontally.
	 */
	public void setHorizontalFill(boolean hfill)
	{
	    	/**
		 * 将参数hfill赋给当前属性hfill
		 */
		this.hfill = hfill;
	}

	/**
	 * Returns true if the layout horizontally fills.
	 * 
	 * @return true if horizontally fills.
	 */
	public boolean getHorizontalFill()
	{
	    	/**
		 * 返回当前属性hfill
		 */
		return hfill;
	}

	/**
	 * places the components defined by first to last within the target
	 * container using the bounds box defined.
	 * 
	 * @param target
	 *            the container.
	 * @param x
	 *            the x coordinate of the area.
	 * @param y
	 *            the y coordinate of the area.
	 * @param width
	 *            the width of the area.
	 * @param height
	 *            the height of the area.
	 * @param first
	 *            the first component of the container to place.
	 * @param last
	 *            the last component of the container to place.
	 */
	private void placethem(Container target, int x, int y, int width, int height, int first, int last)
	{
	    	//获取设置标签的对齐方式并赋给align
		int align = getAlignment();
		/**
		 * 判断align是否等于MIDDLE，如果是，则参数y等于height除以2
		 */
		if (align == MIDDLE)
		{
			y += height / 2;
		}
		/**
		 * 判断align是否等于BOTTOM，如果是，则参数y等于y加height
		 */
		if (align == BOTTOM)
		{
			y += height;
		}
		/**
		 * 循环从first到last
		 */
		for (int i = first; i < last; i++)
		{
		    	/**
			 * 获取Component组件
			 */
			Component m = target.getComponent(i);
			/**
			 * 获取Dimension对象md
			 */
			Dimension md = m.getSize();
			/**
			 * 判断m.isVisible()是否为true
			 */
			if (m.isVisible())
			{
			    	/**
				 * 变量px等于参数x加(width - md.width)除以2
				 */
				int px = x + (width - md.width) / 2;
				/**
				 * 设置m对象的路径
				 */
				m.setLocation(px, y);
				/**
				 * 获取参数y的值为y加vgap加md.height
				 */
				y += vgap + md.height;
			}
		}
	}

	/**
	 * Lays out the container.
	 * 
	 * @param target
	 *            the container to lay out.
	 */
	public void layoutContainer(Container target)
	{
	    	/**
	    	 * 获取注入对象Insets
	    	 */
		Insets insets = target.getInsets();
		/**
		 * 设置最大高度
		 */
		int maxheight = target.getSize().height	- (insets.top + insets.bottom);
		/**
		 * 设置最大宽度
		 */
		int maxwidth = target.getSize().width - (insets.left + insets.right + hgap * 2);
		/**
		 * 获取目标组件数并赋给numcomp
		 */
		int numcomp = target.getComponentCount();
		/**
		 * 设置x坐标
		 */
		int x = insets.left + hgap, y = 0;
		//初始化int型变量colw、start为0
		int colw = 0, start = 0;
		/**
		 * 循环从0到numcomp
		 */
		for (int i = 0; i < numcomp; i++)
		{
		    	/**
			 * 获取Component组件
			 */
			Component m = target.getComponent(i);
			/**
			 * 判断m.isVisible()是否为true
			 */
			if (m.isVisible()) 
			{
			    	/**
				 * 获取Dimension对象d
				 */
				Dimension d = m.getPreferredSize();
				
				// fit last component to remaining height
				/**
				 * 判断当前属性vfill是否为true并且i是否等于(numcomp - 1)
				 */
				if ((this.vfill) && (i == (numcomp - 1))) 
				{
				    	/**
					 * 设置d对象的height
					 */
					d.height = Math.max((maxheight - y), m.getPreferredSize().height);
				}

				// fit component size to container width
				/**
				 * 判断当前属性hfill是否为true
				 */
				if (this.hfill)
				{
				    	/**
					 * 设置m对象的大小和d对象的宽度
					 */
					m.setSize(maxwidth, d.height);
					d.width = maxwidth;
				} 
				else
				{
				    	/**
					 * 设置m对象的大小
					 */
					m.setSize(d.width, d.height);
				}
				/**
				 * 判断y坐标加d对象的高度是否大于maxheight
				 */
				if (y + d.height > maxheight)
				{
				    	/**
					 * 调用placethem()方法
					 */
					placethem(target, x, insets.top + vgap, colw, maxheight - y, start, i);
					/**
					 * 设置x、y坐标和colw和start
					 */
					y = d.height;
					x += hgap + colw;
					colw = d.width;
					start = i;
				} 
				else
				{
				    	/**
					 * 判断y坐标是否大于0,如果是，则y坐标等于y加vgap
					 */
					if (y > 0)
					{
						y += vgap;
					}
					/**
					 * 设置y坐标和变量colw
					 */
					y += d.height;
					colw = Math.max(colw, d.width);
				}
			}
		}
		/**
		 * 调用placethem()方法
		 */
		placethem(target, x, insets.top, colw, maxheight - y, start,	numcomp);
	}
}