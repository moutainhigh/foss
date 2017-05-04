package com.deppon.foss.module.pickup.common.client.ui.commonUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;

/**
 * 旋转按钮组件
 * ClassName: PictureRotateComp <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-10-27 下午7:42:12 <br/>
 *
 * @author 157229-zxy
 * @version 
 * @since JDK 1.6
 */
public class PictureRotateComp extends JPanel implements ActionListener{
	/** */
	private static final long serialVersionUID = 1L;
	
	private static final String LEFT_ICON_PATH = "rleft.png";
	
	private static final String RIGHT_ICON_PATH = "rright.png";
	
	private static final String TOP_ICON_PATH = "rtop.png";
	
	/**
	 * 当前角度
	 */
	private int curAngle = 0;
	
	private ImagePanel imagePanel;
	
	/**
	 * 下旋
	 */
	private static final String COMMAND_DOWN = "down"; 
	
	/**
	 * 上旋
	 */
	private static final String COMMAND_TOP = "top"; 
	
	/**
	 * 左旋
	 */
	private static final String COMMAND_LEFT = "left"; 
	
	/**
	 * 右旋
	 */
	private static final String COMMAND_RIGHT = "right";

	private static final int FOUR = 4;

	private static final int THREE = 3;

	private static final int NUM_46 = 46; 
	
	private JButton jbLeft;
	private JButton jbTop;
	private JButton jbRight;
	private JButton jbDown;
	
	/**
	 * @需求：智能开单项目
	 * @功能：保存旋转图片时间（二期删除）
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19上午10:01
	 */
	WaybillPanelVo bean;
	

	public PictureRotateComp(ImagePanel imagePanel) {
		// 设定布局器
//		super();
		this.imagePanel = imagePanel;
		// 设定窗体大小
		setPreferredSize(new Dimension(NumberConstants.NUMBER_37, NumberConstants.NUMBER_37));
		
		this.setLayout(null);
		Icon iconLeft = ImageUtil.getImageIcon(this.getClass(), LEFT_ICON_PATH);
		Icon iconTop = ImageUtil.getImageIcon(this.getClass(), TOP_ICON_PATH);
		Icon iconRight = ImageUtil.getImageIcon(this.getClass(), RIGHT_ICON_PATH);
		
		jbLeft = new JButton();
		jbLeft.setLocation(0, 0);
		jbLeft.setSize(NumberConstants.NUMBER_23, NumberConstants.NUMBER_25);
		jbTop = new JButton();
		jbTop.setLocation(NumberConstants.NUMBER_23, 0);
		jbTop.setSize(NumberConstants.NUMBER_23, NumberConstants.NUMBER_25);
		jbRight = new JButton();
		jbRight.setLocation(NUM_46, 0);
		jbRight.setSize(NumberConstants.NUMBER_23, NumberConstants.NUMBER_25);
		jbDown = new JButton();
		jbDown.setLocation(0, NumberConstants.NUMBER_24);
		jbDown.setSize(NumberConstants.NUMBER_36, NumberConstants.NUMBER_12);
		jbLeft.setIcon(iconLeft);
		jbLeft.addActionListener(this);
		jbLeft.setActionCommand(COMMAND_LEFT);
		jbTop.setIcon(iconTop);
		jbTop.addActionListener(this);
		jbTop.setActionCommand(COMMAND_TOP);
		jbRight.setIcon(iconRight);
		jbRight.addActionListener(this);
		jbRight.setActionCommand(COMMAND_RIGHT);
		
		//左旋按钮
		this.add(jbLeft);
		//上旋按钮
		this.add(jbTop);
		//右旋按钮
		this.add(jbRight);
		//下旋按钮
		this.add(jbDown);
		setOpaque(false);
		
	}

	/**
	 * 事件处理
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/**
		 * @需求：智能开单项目
		 * @功能：保存旋转图片时间（二期删除）
		 * @author:218371-foss-zhaoyanjun
		 * @date:2016-05-19上午10:01
		 */
		if(bean!=null&&bean.getIbtg()!=null){
			Date startTime=new Date();
			if(bean.getIbtg().getRotatePicture()==null){
				bean.getIbtg().setRotatePicture(startTime);
			}
		}
		int angleType = 0;
		//重置为0
		if(curAngle % NumberConstants.NUMBER_360 == 0)
			curAngle = 0;
		if(COMMAND_LEFT.equals(e.getActionCommand())){
			curAngle = curAngle - NumberConstants.NUMBER_90;
		}else if(COMMAND_TOP.equals(e.getActionCommand())){
			curAngle = curAngle + NumberConstants.NUMBER_180;
		}else if(COMMAND_RIGHT.equals(e.getActionCommand())){
			curAngle = curAngle + NumberConstants.NUMBER_90;
		}else if(COMMAND_DOWN.equals(e.getActionCommand())){
			curAngle = curAngle - NumberConstants.NUMBER_180;
		}
		if(Math.abs(curAngle) > NumberConstants.NUMBER_360){
			curAngle = 0;
		}
		if(curAngle == NumberConstants.NUMBER_90 || curAngle == -NumberConstants.NUMBER_270){
			angleType = 1;
		}else if(curAngle == -NumberConstants.NUMBER_90 || curAngle == NumberConstants.NUMBER_270){
			angleType = FOUR;
		}else if(curAngle == 0){
			angleType = 0;
		}else if(curAngle == NumberConstants.NUMBER_180){
			angleType = 2;
		}else if(curAngle == -NumberConstants.NUMBER_180){
			angleType = THREE;
		}
		double angle =(new BigDecimal(curAngle).divide(new BigDecimal(NumberConstants.NUMBER_180)).floatValue())*Math.PI;
		imagePanel.ratoteImage(angle,angleType);
	}

	public JButton getJbLeft() {
		return jbLeft;
	}

	public void setJbLeft(JButton jbLeft) {
		this.jbLeft = jbLeft;
	}

	public JButton getJbTop() {
		return jbTop;
	}

	public void setJbTop(JButton jbTop) {
		this.jbTop = jbTop;
	}

	public JButton getJbRight() {
		return jbRight;
	}

	public void setJbRight(JButton jbRight) {
		this.jbRight = jbRight;
	}

	public JButton getJbDown() {
		return jbDown;
	}

	public void setJbDown(JButton jbDown) {
		this.jbDown = jbDown;
	}
	
	public WaybillPanelVo getBean() {
		return bean;
	}

	public void setBean(WaybillPanelVo bean) {
		this.bean = bean;
	}
}
