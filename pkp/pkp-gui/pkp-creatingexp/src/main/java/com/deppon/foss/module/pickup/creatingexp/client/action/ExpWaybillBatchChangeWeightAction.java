package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillBatchChangeWeightUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ewaybill.BatchChangeEWaybilInfoDialog;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;

/**
 * 
 * 批量更改重量
 * @author 136334-foss-bailei
 * @date 2015-1-29 下午1:54:16
 */
public class ExpWaybillBatchChangeWeightAction implements IButtonActionListener<ExpWaybillBatchChangeWeightUI> {
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpWaybillBatchChangeWeightAction.class);
	
	private static final Logger LOG = Logger.getLogger(ExpWaybillBatchChangeWeightAction.class);

	//初始化运单服务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	//private IWaybillRfcService rfcService = WaybillRfcServiceFactory.getWaybillRfcService();
	
	private ExpWaybillBatchChangeWeightUI ui;
	
	/**
	 * 
	 * 执行更改
	 * @author 136334-foss-bailei
	 * @date 2015-1-24 下午5:27:34
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
    	if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(ui, i18n.get("foss.gui.changingexp.waybillRfcCommitAction.confirmDialog.waybillRfcApply"), i18n.get("foss.gui.changingexp.waybillRFCUI.common.prompt"), JOptionPane.OK_CANCEL_OPTION)){
    		
    		//更改单提交DTO
    		//WaybillRfcSubmitDto submitDto = getSubmitDto();
    		
    		try {
    			BatchChangeEWaybilInfoDialog dialog = new BatchChangeEWaybilInfoDialog();
    			// 剧中显示弹出窗口
    			WindowUtil.centerAndShow(dialog);
    			/*List<ExpBatchChangeWeightDto> expBatchChangeWeightDtoList = new ArrayList<ExpBatchChangeWeightDto>();
    			
    			expBatchChangeWeightDtoList = getExpBatchChangeWeightDto();
    			if(expBatchChangeWeightDtoList!=null){
    				commitBatchChangeWeight(expBatchChangeWeightDtoList);
    			}*/
				//WaybillRfcEntity rfcEntity = rfcService.commitWaybillRfc(submitDto);
				//ui.setOk(true);
				///提交成功
				//ui.getWaybillRFCUI().setCommited(rfcEntity);
				//ui.dispose();
				
			} catch (BusinessException e1) {
				//LOG.error("运单号"+submitDto.getRfcEntity().getWaybillNo()+"提交更改出现NullPointerException"+e1.getMessage(), e1);
				//MsgBox.showInfo("运单号"+submitDto.getRfcEntity().getWaybillNo()+"提交更改时出现业务异常："+e1.getMessage());
				throw e1;
			} catch (Exception e1) {
				//LOG.error("运单号"+submitDto.getRfcEntity().getWaybillNo()+"提交更改出错"+e1.getMessage(),e1);
				//MsgBox.showInfo(e1.getMessage());
			}
    	}
	}

	/**
	 * 
	 * 获取更改DTO
	 * @author 136334-foss-bailei
	 * @date 2015-1-24 下午5:27:34
	 * 
	 */
	private  List<ExpBatchChangeWeightDto> getExpBatchChangeWeightDto(){
		List<ExpBatchChangeWeightDto> expBatchChangeWeightDtoList = new ArrayList<ExpBatchChangeWeightDto>();
		
		return expBatchChangeWeightDtoList;
	}
	
	/**
	 * 
	 * 执行更改
	 * @author 136334-foss-bailei
	 * @date 2015-1-24 下午5:27:34
	 * 
	 */
	private void commitBatchChangeWeight(List<ExpBatchChangeWeightDto>  expBatchChangeWeightDto) {
		waybillService.commitBatchChangeWeight(expBatchChangeWeightDto);
		
	}


	@Override
	public void setInjectUI(ExpWaybillBatchChangeWeightUI ui) {
		this.ui = ui;
		
	}
	
}