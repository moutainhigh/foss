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
 * FILE PATH        	: information/src/main/java/com/deppon/foss/module/information/client/message/RichJLabel.java
 * 
 * FILE NAME        	: RichJLabel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.information.client.message;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import com.deppon.foss.base.util.define.NumberConstants;
  
 
/**
 * 复杂Label控件类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:56:51, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:56:51
 * @since
 * @version
 */
public class RichJLabel extends JLabel   
{   
    private static final long serialVersionUID = 6726176528338618387L;   
  
    /**  
     * 每个字之间的距离  
     */  
    private int tracking;   
  
    /**  
     * 返回一个简单轮廓样式的JLabel  
     *   
     * @param text  
     *            文字  
     * @param fontSize  
     *            字体大小  
     */  
    public static RichJLabel getOutlineLabel(String text, float fontSize)   
    {   
        return getOutlineLabel(text, 0, Color.WHITE, Color.BLACK, fontSize);   
    }   
  
    /**  
     * 返回一个简单轮廓样式的JLabel  
     *   
     * @param text  
     *            文字  
     * @param tracking  
     *            文字间距  
     * @param fontColor  
     *            字的颜色  
     * @param lineColor  
     *            边框样色  
     * @param fontSize  
     *            字体大小  
     * @return  
     */  
    public static RichJLabel getOutlineLabel(String text, int tracking, Color fontColor, Color lineColor, float fontSize)   
    {   
        RichJLabel label = new RichJLabel(text, tracking, fontSize);   
  
        label.setLeftShadow(1, 1, Color.BLACK);   
        label.setRightShadow(1, 1, Color.BLACK);   
        label.setForeground(Color.WHITE);   
  
        return label;   
    }   
  
    /**  
     * 返回一个阴影遮蔽样式的JLabel  
     *   
     * @param text  
     *            文字  
     * @param fontSize  
     *            字体大小  
     */  
    public static RichJLabel getShadowLabel(String text, float fontSize)   
    {   
        return getShadowLabel(text, 0, Color.WHITE, Color.GRAY, Color.BLACK, fontSize);   
    }   
  
    /**  
     * 返回一个阴影遮蔽样式的JLabel  
     *   
     * @param text  
     *            文字  
     * @param tracking  
     *            字间距  
     * @param fontColor  
     *            字体颜色  
     * @param leftColor  
     *            阴影颜色  
     * @param rightColor  
     *            字体侧边颜色  
     * @param fontSize  
     *            字体大小  
     */  
    public static RichJLabel getShadowLabel(String text, int tracking, Color fontColor, Color leftColor, Color rightColor, float fontSize)   
    {   
        RichJLabel label = new RichJLabel(text, tracking, fontSize);   
  
        label.setLeftShadow(2, 2, leftColor);   
        label.setRightShadow(2, NumberConstants.NUMBER_3, rightColor);   
        label.setForeground(fontColor);   
  
        return label;   
    }   
  
    /**  
     * 返回一个3D样式的JLabel  
     *   
     * @param text  
     *            文字  
     * @param fontSize  
     *            字体大小  
     */  
    public static RichJLabel get3DLabel(String text, float fontSize)   
    {   
        return get3DLabel(text, 0, Color.WHITE, Color.GRAY, fontSize);   
    }   
  
    /**  
     * 返回一个阴影遮蔽样式的JLabel  
     *   
     * @param text  
     *            文字  
     * @param tracking  
     *            字间距  
     * @param fontColor  
     *            字体颜色  
     * @param sideColor  
     *            字体侧边颜色  
     * @param fontSize  
     *            字体大小  
     */  
    public static RichJLabel get3DLabel(String text, int tracking, Color fontColor, Color sideColor, float fontSize)   
    {   
        RichJLabel label = new RichJLabel(text, tracking, fontSize);   
  
        label.setLeftShadow(NumberConstants.NUMBER_5, NumberConstants.NUMBER_5, sideColor);   
        label.setRightShadow(NumberConstants.NUMBER_THE_3, NumberConstants.NUMBER_THE_3, sideColor);   
        label.setForeground(fontColor);   
  
        return label;   
    }   
  
    /**  
     * 构造方法  
     *   
     * @param text  
     * @param tracking  
     *            每个字之间的距离  
     */  
    private RichJLabel( String text, int tracking, float fontSize )   
    {   
        super(text);   
        this.tracking = tracking;   
        setFont(getFont().deriveFont(fontSize));   
    }   
  
    //根据sonar(成员名 )修改
    private int leftX, leftY, rightX, rightY;   
    //根据sonar(成员名 )修改
    private Color leftColor, rightColor;   
  
    public void setLeftShadow(int x, int y, Color color)   
    {   
    	leftX = x;   
    	leftY = y;   
    	leftColor = color;   
    }   
  
    public void setRightShadow(int x, int y, Color color)   
    {   
    	rightX = x;   
    	rightY = y;   
    	rightColor = color;   
    }   
  
    public Dimension getPreferredSize()   
    {   
        String text = getText();   
        if(text == null){
        	return new Dimension(0, 0);
        }
        FontMetrics fm = this.getFontMetrics(getFont());   
  
        int w = fm.stringWidth(text);   
        w += (text.length() - 1) * tracking;   
        w += leftX + rightX;   
  
        int h = fm.getHeight();   
        h += leftY + rightY;   
  
        return new Dimension(w, h);   
    }   
  
    public void paintComponent(Graphics g)   
    {   
    	//根据sonar(高危 - 未检查/未证实的类型转换 )修改
    	if(g!=null){
	    		// 打开文字抗锯齿   
	        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);   
	        char[] chars = getText().toCharArray();   
	  
	        FontMetrics fm = this.getFontMetrics(getFont());   
	        int h = fm.getAscent();   
	        g.setFont(getFont());   
	  
	        int x = 0;   
	  
	        for (int i = 0; i < chars.length; i++)   
	        {   
	            char ch = chars[i];   
	            int w = fm.charWidth(ch) + tracking;   
	  
	            g.setColor(leftColor);   
	            g.drawString("" + chars[i], x - leftX, h - leftY);   
	  
	            g.setColor(rightColor);   
	            g.drawString("" + chars[i], x + rightX, h + rightY);   
	  
	            g.setColor(getForeground());   
	            g.drawString("" + chars[i], x, h);   
	  
	            x += w;   
	        }   
    	}
    }   
  

    
}