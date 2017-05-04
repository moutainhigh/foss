package com.deppon.foss.module.pickup.creating.client.ui.order;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.AddWaybillUtils;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class BackPictureWaybillDialog  extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//日志
	public static final Logger LOGGER = LoggerFactory.getLogger(PendingCompleteDialog.class);

	/**
	 * 表格默认扩展
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 设置表格列的个数
	 */
	private static final int TEN = 25;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(PendingCompleteDialog.class); 

	/**
	 * 通过工厂类获得运单服务类
	 */
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 退回原因
	 * 
	 */
	private JTextField txtBackReason;
	
	PictureWaybillEditUI ui;

	public BackPictureWaybillDialog(PictureWaybillEditUI ui){
		this.ui = ui;
		init();
	}
	/**
	 * 初始化界面
	 * 
	 */
	private void init() {
		// 创建一个面板
		JPanel panel = new JPanel();
		panel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(DEFAULTGROW), }, new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		//退回原因
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.BackPictureWaybillDialog.BackReason")+"：");
		panel.add(label1, "1, 1, 7, 1");

		// 退回原因输入框
		txtBackReason = new JTextField();
		panel.add(txtBackReason, "2, 3, 7, 1, fill, default");
		txtBackReason.setColumns(TEN);

		// 确认按钮
		JButton btnConfirm = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.confirm"));
		panel.add(btnConfirm, "3, 5");

		// 取消按钮
		JButton btnCancel = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.cancel"));
		panel.add(btnCancel, "7, 5");

		// Dialog监听ESC事件
		panel.registerKeyboardAction(new EscHandler(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		// 添加“确定”按钮监听事件
		btnConfirm.addActionListener(new ConfirmHandler(ui));
		// 添加“取消”按钮监听事件
		btnCancel.addActionListener(new CancelHandler());

		// 设置模态
		setModal(true);
		// 将panel加入到容器中
		getContentPane().add(panel);
		// 自动撑展弹出框
		pack();
	}
	/**
	 * 定义一个一般内部类：设置dialog监听esc按键的处理事件
	 */
	private class EscHandler implements ActionListener {

		/**
		 * 关闭当前打开的dialog
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	/**
	 * 定义一般内部类：处理“取消”按钮事件
	 */
	private class CancelHandler implements ActionListener {

		/**
		 * 关闭当前打开的dialog
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	private class ConfirmHandler implements ActionListener {
		PictureWaybillEditUI ui;
		public ConfirmHandler(PictureWaybillEditUI ui){
			this.ui=ui;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				HashMap<String, IBinder<WaybillPanelVo>> map = ui.waybillEdit.getBindersMap();
				IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
				WaybillPanelVo bean = waybillBinder.getBean();
				//String waybillNo = ui.waybillEdit.basicPanel.getTxtWaybillNO().getText();
				if(bean.getPictureWaybillNo() == null || "".equals(bean.getPictureWaybillNo().trim())){
					MsgBox.showError(i18n.get("foss.gui.creating.BackPictureWaybillDialog.msgbox.label1"));
				}else{
					String remark = txtBackReason.getText().trim();
					if(StringUtils.isBlank(remark)){
						MsgBox.showError(i18n.get("foss.gui.creating.BackPictureWaybillDialog.msgbox.label"));
					}else{
						WaybillPictureEntity entity = new WaybillPictureEntity();
						entity.setId(bean.getPictureWaybillNo());
						entity.setActive(FossConstants.ACTIVE);
						WaybillPictureEntity pictureEntitys = waybillService.queryWaybillPictureByEntity(entity);
						if(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.equals(pictureEntitys.getPendgingType()) ||
								WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(pictureEntitys.getPendgingType())){
							MsgBox.showError(i18n.get("foss.gui.creating.BackPictureWaybillDialog.msgbox.label1"));
						}else{
							WaybillPictureEntity waybillPictureEntity = new WaybillPictureEntity();

							//获得当前用户信息
							UserEntity user = (UserEntity) SessionContext.getCurrentUser();
							String billingName  = user.getEmployee().getEmpName();
							String billingPhone = "";
							
							//取手机
							String mobile = user.getEmployee().getMobilePhone();
							if(!StringUtils.isEmpty(mobile)){
								billingPhone = mobile;
							}
							
							//把开单人、开单人电话、退回信息已json形式发送到短信平台（由短信平台处理）
							Map<String,String> jsonMap = new HashMap<String,String>();
							jsonMap.put(WaybillConstants.BILLING_PERSON_NAME, billingName);
							jsonMap.put(WaybillConstants.BILLING_PERSON_PHONE, billingPhone);
							jsonMap.put(WaybillConstants.WAYBILL_BACK_MESSAGE, remark);
							String jsonMsg = JSON.toJSONString(jsonMap);
							
							waybillPictureEntity.setRemark(jsonMsg);
							waybillPictureEntity.setId(bean.getPictureWaybillNo());
							waybillPictureEntity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN);
							//waybillService.updatePictureWaybillByWaybillno(waybillPictureEntity);
							waybillService.backPictureWaybill(waybillPictureEntity);
							waybillPictureEntity.setBaiDuId(bean.getBaiDuId());
							waybillPictureEntity.setOrderNo(bean.getOrderNo());
							waybillPictureEntity.setWaybillNo(bean.getWaybillNo());
							try {
								ui.backPushMessage(waybillPictureEntity, remark);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
							dispose();
							AddWaybillUtils utils = new AddWaybillUtils(ui);
							utils.newPictureWaybill();
						}
					}
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
				LOGGER.error("运单退回异常，原因：" + e2.getMessage(), e2);
			}
		}
	}
}
