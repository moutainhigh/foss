package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.Insets;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.ShowPickupStationDialogAction;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * (运输信息面板)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:19:55,content:
 * </p>
 * 
 * @author 025000-FOSS
 * @date 2012-10-17 上午11:19:55
 * @since
 * @version
 */
@ContainerSeq(seq=4)
public class PictureTransferInfoPanel extends JPanel {
	/**
	 * 10
	 */
	private static final int TEN = 10;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(TransferInfoPanel.class); 

	@Bind("customerPickupOrgName")
	@BindingArgs({ @BindingArg(name = "fieldName", value = "提货网点") })
	private JTextFieldValidate txtPickBranch;// 提货网点

	@Bind("targetOrgCode")
	@BindingArgs({ @BindingArg(name = "fieldName", value = "目的站") })
	@FocusSeq(seq = 3)
	private JTextFieldValidate txtDestination;// 目的站

	@Bind("productCode")
	@FocusSeq(seq = 1)
	private JComboBox combProductType;// 产品类型

	@Bind("receiveMethod")
	@FocusSeq(seq = 2)
	private JComboBox combPickMode;// 提货方式

	//查询目的站放大镜
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = ShowPickupStationDialogAction.class)
	@FocusSeq(seq = 4)
	private JButton btnQueryBranch;

	@Bind("loadLineName")
	private JTextFieldValidate txtPredictLine;// 预配线路

	//合票方式
	@Bind("freightMethod")
	@FocusSeq(seq = 6)
	private JComboBox combFreightMethod;
	
	//航班类型
	@Bind("flightNumberType")
	@FocusSeq(seq = 7)
	private JComboBox combFlightNumberType;
	
	//航班时间
	@Bind("flightShift")
	private JTextFieldValidate txtFlightShift;
	
	//公里
	@Bind("kilometer")
	@FocusSeq(seq = 5)
	private JTextFieldValidate txtKilometer;
	
	//商业区
	@Bind("businessZone")
	private JCheckBox JBusinessZone = new JCheckBox(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.businessZone.label"));
	//住宅区
    @Bind("residentialDistrict")
	private JCheckBox JResidentialDistrict = new JCheckBox(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.residentialDistrict.label"));
	//是否是图片开单
	//private String pictureWaybillType;
    
    /**
	 * @项目：智能开单项目
	 * @功能：保存操作该下拉框的时间
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19下午14:28
	 */
	WaybillPanelVo bean;
	double combProductTypeTime=0;
	double combPickModeTime=0;
	
	public PictureTransferInfoPanel(/*String pictureWaybillType*/) {
		//this.pictureWaybillType = pictureWaybillType;
		init();
	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 上午10:49:11
	 * @see
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.label")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		//运输性质
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.productCode.label"));
		add(label1, "2, 1, right, default");
		
		combProductType = new JComboBox();
		add(combProductType, "4, 1, 3, 1, fill, default");

		//提货方式
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.pickMode.label"));
		add(label4, "10, 1, right, default");
		
		combPickMode = new JComboBox();
		add(combPickMode, "12, 1, 3, 1, fill, default");

		//目的站
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.destination.label"));
		add(label5, "18, 1, right, default");
		
		txtDestination = new JTextFieldValidate();
		add(txtDestination, "20, 1, 3, 1, fill, default");
		txtDestination.setColumns(TEN);
		
		/**
		 * 禁用目的站编辑  防止客户校验后修改目的站内容
		 * @author Foss-278328-hujinyang
		 * @time 20151226 13:06
		 */
		txtDestination.setEditable(false);
		
		//匹配提货网点
		btnQueryBranch = new JButton();
		add(btnQueryBranch, "23, 1");
		btnQueryBranch.setMargin(new Insets(-2, 1, -2, 1));
		
		//提货网点
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.pickBranch.label"));
		add(label2, "27, 1, right, default");

		txtPickBranch = new JTextFieldValidate();
		add(txtPickBranch, "28, 1, 4, 1, fill, default");
		txtPickBranch.setColumns(TEN);
		txtPickBranch.setEditable(false);
		
		//公里数
		JLabel labelG1 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.kilometer.label"));
		add(labelG1, "33, 1, right, default");
		FloatDocument kilometer = new FloatDocument(NumberConstants.NUMBER_9,NumberConstants.NUMBER_3);
		
		txtKilometer = new JTextFieldValidate();
		add(txtKilometer, "35, 1, 3, 1, fill, default");
		txtKilometer.setColumns(TEN);
		txtKilometer.setDocument(kilometer);
		add(JBusinessZone, "39, 1");
		JBusinessZone.setVisible(false);
		//合票方式
		JLabel label = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.freightMethod.label"));
		add(label, "2, 3, right, default");
		
		combFreightMethod = new JComboBox();
		add(combFreightMethod, "4, 3, 3, 1, fill, default");
		combFreightMethod.setEnabled(false);

		//航班类型
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.flightNumberType.label"));
		add(label6, "10, 3, right, default");
		
		combFlightNumberType = new JComboBox();
		add(combFlightNumberType, "12, 3, 3, 1, fill, default");
		combFlightNumberType.setEnabled(false);
		
		//航班时间
		JLabel label7 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.flightShift.label"));
		add(label7, "18, 3, right, default");
				
		txtFlightShift = new JTextFieldValidate();
		add(txtFlightShift, "20, 3, 4, 1, fill, default");
		txtFlightShift.setColumns(TEN);
		txtFlightShift.setEditable(false);
		
		
		//预配路线
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.predictLine.label"));
		add(label3, "27, 3, right, default");

		txtPredictLine = new JTextFieldValidate();
		add(txtPredictLine, "28, 3, 10, 1, fill, default");
		txtPredictLine.setColumns(TEN);
		txtPredictLine.setEditable(false);
		
		add(JResidentialDistrict, "39, 3");
		JResidentialDistrict.setVisible(false);
	}
/*	private void pictureWaybill(){
		//运输性质
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.productCode.label"));
		add(label1, "2, 1, right, default");
		
		combProductType = new JComboBox();
		add(combProductType, "4, 1, 3, 1, fill, default");

		//提货方式
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.pickMode.label"));
		add(label4, "10, 1, right, default");
		
		combPickMode = new JComboBox();
		add(combPickMode, "12, 1, 3, 1, fill, default");

		//目的站
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.destination.label"));
		add(label5, "18, 1, right, default");
		
		txtDestination = new JTextFieldValidate();
		add(txtDestination, "20, 1, 3, 1, fill, default");
		txtDestination.setColumns(TEN);
		
		//匹配提货网点
		btnQueryBranch = new JButton();
		add(btnQueryBranch, "23, 1");
		btnQueryBranch.setMargin(new Insets(-2, 1, -2, 1));
		
		//提货网点
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.pickBranch.label"));
		add(label2, "27, 1, right, default");

		txtPickBranch = new JTextFieldValidate();
		add(txtPickBranch, "28, 1, 4, 1, fill, default");
		txtPickBranch.setColumns(TEN);
		txtPickBranch.setEditable(false);
		
		//公里数
		JLabel label_1 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.kilometer.label"));
		add(label_1, "33, 1, right, default");
		FloatDocument kilometer = new FloatDocument(9,3);
		
		txtKilometer = new JTextFieldValidate();
		add(txtKilometer, "35, 1, 3, 1, fill, default");
		txtKilometer.setColumns(10);
		txtKilometer.setDocument(kilometer);
		add(JBusinessZone, "39, 1");
		JBusinessZone.setVisible(false);
		//合票方式
		JLabel label = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.freightMethod.label"));
		add(label, "2, 3, right, default");
		
		combFreightMethod = new JComboBox();
		add(combFreightMethod, "4, 3, 3, 1, fill, default");
		combFreightMethod.setEnabled(false);

		//航班类型
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.flightNumberType.label"));
		add(label6, "10, 3, right, default");
		
		combFlightNumberType = new JComboBox();
		add(combFlightNumberType, "12, 3, 3, 1, fill, default");
		combFlightNumberType.setEnabled(false);
		
		//航班时间
		JLabel label7 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.flightShift.label"));
		add(label7, "18, 3, right, default");
				
		txtFlightShift = new JTextFieldValidate();
		add(txtFlightShift, "20, 3, 4, 1, fill, default");
		txtFlightShift.setColumns(TEN);
		txtFlightShift.setEditable(false);
		
		
		//预配路线
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.predictLine.label"));
		add(label3, "27, 3, right, default");

		txtPredictLine = new JTextFieldValidate();
		add(txtPredictLine, "28, 3, 10, 1, fill, default");
		txtPredictLine.setColumns(TEN);
		txtPredictLine.setEditable(false);
		
		add(JResidentialDistrict, "39, 3");
		JResidentialDistrict.setVisible(false);
	}
	private void otherWaybill(){
		//运输性质
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.productCode.label"));
		add(label1, "2, 1, right, default");
		
		combProductType = new JComboBox();
		add(combProductType, "4, 1, 3, 1, fill, default");

		//提货方式
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.pickMode.label"));
		add(label4, "10, 1, right, default");
		
		combPickMode = new JComboBox();
		add(combPickMode, "12, 1, 3, 1, fill, default");

		//目的站
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.destination.label"));
		add(label5, "18, 1, right, default");
		
		txtDestination = new JTextFieldValidate();
		add(txtDestination, "20, 1, 3, 1, fill, default");
		txtDestination.setColumns(TEN);
		
		//匹配提货网点
		btnQueryBranch = new JButton();
		add(btnQueryBranch, "23, 1");
		btnQueryBranch.setMargin(new Insets(-2, 1, -2, 1));
		
		//提货网点
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.pickBranch.label"));
		add(label2, "27, 1, right, default");

		txtPickBranch = new JTextFieldValidate();
		add(txtPickBranch, "28, 1, 4, 1, fill, default");
		txtPickBranch.setColumns(TEN);
		txtPickBranch.setEditable(false);
		
		//公里数
		JLabel label_1 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.kilometer.label"));
		add(label_1, "33, 1, right, default");
		FloatDocument kilometer = new FloatDocument(9,3);
		txtKilometer = new JTextFieldValidate();
		add(txtKilometer, "35, 1, 3, 1, fill, default");
		txtKilometer.setColumns(10);
		txtKilometer.setDocument(kilometer);

		//合票方式
		JLabel label = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.freightMethod.label"));
		add(label, "2, 3, right, default");
		
		combFreightMethod = new JComboBox();
		add(combFreightMethod, "4, 3, 3, 1, fill, default");
		combFreightMethod.setEnabled(false);

		//航班类型
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.flightNumberType.label"));
		add(label6, "10, 3, right, default");
		
		combFlightNumberType = new JComboBox();
		add(combFlightNumberType, "12, 3, 3, 1, fill, default");
		combFlightNumberType.setEnabled(false);
		
		//航班时间
		JLabel label7 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.flightShift.label"));
		add(label7, "18, 3, right, default");
				
		txtFlightShift = new JTextFieldValidate();
		add(txtFlightShift, "20, 3, 4, 1, fill, default");
		txtFlightShift.setColumns(TEN);
		txtFlightShift.setEditable(false);
		
		
		//预配路线
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.predictLine.label"));
		add(label3, "27, 3, right, default");

		txtPredictLine = new JTextFieldValidate();
		add(txtPredictLine, "28, 3, 10, 1, fill, default");
		txtPredictLine.setColumns(TEN);
		txtPredictLine.setEditable(false);
	}*/
	/**
	 * getTxtPickBranch
	 * @return JTextFieldValidate
	 */
	public JTextFieldValidate getTxtPickBranch() {
		return txtPickBranch;
	}
	/**
	 * getCombPickMode
	 * @return JComboBox
	 */
	public JComboBox getCombPickMode() {
		return combPickMode;
	}
	/**
	 * getCombProductType
	 * @return JComboBox
	 */
	public JComboBox getCombProductType() {
		return combProductType;
	}
	/**
	 * getCombFreightMethod
	 * @return JComboBox
	 */
	public JComboBox getCombFreightMethod() {
		return combFreightMethod;
	}
	/**
	 * getCombFlightNumberType
	 * @return JComboBox
	 */
	public JComboBox getCombFlightNumberType() {
		return combFlightNumberType;
	}
	
	public JTextFieldValidate getTxtFlightShift() {
		return txtFlightShift;
	}
	
	public JTextFieldValidate getTxtPredictLine() {
		return txtPredictLine;
	}
	
	public JTextFieldValidate getTxtDestination() {
		return txtDestination;
	}
	
	/**
	 * 
	 * 公里数
	 * @author 025000-FOSS-helong
	 * @date 2013-2-28 上午10:50:17
	 * @return
	 */
	public JTextFieldValidate getTxtKilometer() {
		return txtKilometer;
	}
	
	/**
	 * 
	 * 获得查询网点按钮对象
	 * @author 025000-FOSS-helong
	 * @date 2013-1-21 下午02:25:21
	 * @return
	 */
	public JButton getBtnQueryBranch() {
		return btnQueryBranch;
	}
	public JCheckBox getJBusinessZone() {
		return JBusinessZone;
	}
	public void setJBusinessZone(JCheckBox jBusinessZone) {
		JBusinessZone = jBusinessZone;
	}
	public JCheckBox getJResidentialDistrict() {
		return JResidentialDistrict;
	}
	public void setJResidentialDistrict(JCheckBox jResidentialDistrict) {
		JResidentialDistrict = jResidentialDistrict;
	}
	
	public WaybillPanelVo getBean() {
		return bean;
	}

	public void setBean(WaybillPanelVo vo) {
		combPickModeTime=0;
		combProductTypeTime=0;
		this.bean = vo;
		/**
		 * @项目：智能开单项目
		 * @功能：保存操作该下拉框的时间
		 * @author:218371-foss-zhaoyanjun
		 * @date:2016-05-19下午14:28
		 */
		if(bean!=null&&bean.getIbtg()!=null){
			combProductType.addPopupMenuListener(new PopupMenuListener() {
				Date startTime=null;
				Date endTime=null;
				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					try {
						startTime=new Date();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				
				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					try {
						endTime=new Date();
						if(bean!=null&&startTime!=null){
							combProductTypeTime=(endTime.getTime()-startTime.getTime())/NumberConstants.NUMBER_1000+bean.getIbtg().getCombProductTypeTime();
							bean.getIbtg().setCombProductTypeTime(combProductTypeTime);
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				
				@Override
				public void popupMenuCanceled(PopupMenuEvent e) {
					// TODO Auto-generated method stub
//					endTime=new Date();
//					if(bean!=null){
//						combProductTypeTime=(endTime.getTime()-startTime.getTime())/1000+bean.getIbtg().getCombProductTypeTime();
//						bean.getIbtg().setCombProductTypeTime(combProductTypeTime);
//					}
				}
			});
		}
		/**
		 * @项目：智能开单项目
		 * @功能：保存操作该下拉框的时间
		 * @author:218371-foss-zhaoyanjun
		 * @date:2016-05-19下午14:28
		 */
		if(bean!=null&&bean.getIbtg()!=null){
			combPickMode.addPopupMenuListener(new PopupMenuListener() {
				Date startTime=null;
				Date endTime=null;
				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					try {
						startTime=new Date();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				
				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					try {
						endTime=new Date();
						if(bean!=null&&startTime!=null){
							combPickModeTime=(endTime.getTime()-startTime.getTime())/NumberConstants.NUMBER_1000+bean.getIbtg().getCombPickModeTime();
							bean.getIbtg().setCombPickModeTime(combPickModeTime);
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				
				@Override
				public void popupMenuCanceled(PopupMenuEvent e) {
					// TODO Auto-generated method stub
//					endTime=new Date();
//					if(bean!=null){
//						combPickModeTime=(endTime.getTime()-startTime.getTime())/1000+bean.getIbtg().getCombPickModeTime();
//						bean.getIbtg().setCombPickModeTime(combPickModeTime);
//					}
				}
			});
		}
	}
}