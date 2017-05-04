package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.OpenBatchCreateWaybillUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GUIPrintLabelDto;
import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintManager;
import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;
import com.deppon.foss.prt.PrintUtil;
import com.deppon.foss.util.UUIDUtils;
import com.google.inject.Injector;

public class PrintButtonBatchCreateWaybillQueryAction extends AbstractButtonActionListener<OpenBatchCreateWaybillUI> {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(PrintButtonBatchCreateWaybillQueryAction.class);
	
	Injector injector = GuiceContextFactroy.getInjector();

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(PrintButtonBatchCreateWaybillQueryAction.class);
	
	private static final String COMMA = ",";

	private static final String FORMATSTR = "%04d";
	
	private OpenBatchCreateWaybillUI ui;
	
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	//打印信息dto
    private BarcodePrintLabelDto printLabelBean = new BarcodePrintLabelDto();
    
    //存储根据运单号所查询
  	private List<BarcodePrintLabelDto> printLabelBeans = new ArrayList<BarcodePrintLabelDto>();

  	
	public OpenBatchCreateWaybillUI getUi() {
		return ui;
	}

	public void setUi(OpenBatchCreateWaybillUI ui) {
		this.ui = ui;
	}

	

	@Override
	public void setIInjectUI(OpenBatchCreateWaybillUI ui) {
		this.ui=ui;
	}

	/**
	 * 批量打印
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
			/**
			 * 选中的导出运单号
			 */
			List<String> selectExportWaybillNoList = new ArrayList<String>();
			selectExportWaybillNoList=ui.getSelectExportWaybillNoList();
			String pendingType = ui.getWaybillPringType();
			//不是已开单状态的直接返回
			if(! WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(pendingType)){
				MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.error.existNotWaybillNo"));
				return;
			}
			// 循环单号查询出运单的信息和流水号集合
			//德邦Logo是否显示“德邦物流”
			printLabelBean.setIsPrintLogo(WaybillConstants.YES);
			if (CollectionUtils.isNotEmpty(selectExportWaybillNoList)) {
				for (int i = 0; i < selectExportWaybillNoList.size(); i++) {
					// 运单实体信息不为空
					if ((selectExportWaybillNoList.get(i)) != null) {
						printLabelBean = waybillService.getLabelPrintInfos(selectExportWaybillNoList.get(i),pendingType);
						// 如果没有就报错
						if (printLabelBean == null && StringUtils.isEmpty(printLabelBean.getWaybillNumber())) {
							JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),i18n.get("foss.gui.creating.printSignUI.msgbox.error"),JOptionPane.WARNING_MESSAGE);
							break;
						}
						//如果是合伙人就调用综合的接口查询出对接外场
						String partner=waybillService.selectqueryWaybillForPrint(printLabelBean.getWaybillNumber());
						if("Y".equals(partner)){
							if(StringUtils.isEmpty(printLabelBean.getCustomerPickupOrgCode())){
								JOptionPane.showMessageDialog(null,
										i18n.get("foss.waybill.eWaybillService.destinationIsNull"),
										i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
										JOptionPane.WARNING_MESSAGE);
									return;
							}
							//对接外场
							String field=waybillService.selectqueryDeptDeptTransferForNameByCode(printLabelBean.getCustomerPickupOrgCode());
							//如果有对接外场就添加进打印信息,如果对接外场为空就设置对接外场为空字符串（按照原有的货物标签打印）
							if(StringUtils.isEmpty(field)){
								JOptionPane.showMessageDialog(null,
										i18n.get("foss.waybill.eWaybillService.partnerdestinationIsNull"),
										i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
										JOptionPane.WARNING_MESSAGE);
								printLabelBean.setPartnerBillingLogo("");
							}else{
								printLabelBean.setPartnerBillingLogo(field);
							}
						}else{
							printLabelBean.setPartnerBillingLogo("");
						}
						printLabelBeans.add(printLabelBean);
						
					} else {
						MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.error.existbuttenWaybillNo"));
						return;
					}
				}
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.error.existbuttenWaybillNo"));
				return;
			}
			
			// 获取流水号
			if (printLabelBeans != null && printLabelBeans.size() > 0) {
				//循环流水号进行打印
				for (int i = 0; i < printLabelBeans.size(); i++) {
					printLabelBean = printLabelBeans.get(i);
					// 先判定是否对应的总件数<0
					int totalPieces = Integer.parseInt(printLabelBean.getTotalPieces());
					String waybillNo = printLabelBean.getWaybillNumber();
					if (totalPieces <= 0) {
						JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.msgbox.error.Number"),
								i18n.get("foss.gui.creating.printSignUI.msgbox.error"),JOptionPane.WARNING_MESSAGE);
						selectExportWaybillNoList.clear();
						continue;
					} 
					//拼接流水号
					String s =getPrintSerialnoss(totalPieces).toString();
					printLabelBean.setPrintSerialnos(s);
					//获取用户信息
					UserEntity userEntity = (UserEntity)SessionContext.get(SessionContext.KEY_USER);
					if (! StringUtils.isEmpty(printLabelBeans.get(i).getPrintSerialnos())) {
						PrintUtil printUtil = new PrintUtil();
						LabelPrintContext ctx = printUtil.printLabelData(printLabelBeans.get(i));
						try {
							//调用打印模板
							LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_CommLabelPrintWorker, ctx);
						} catch (Exception e1) {
							selectExportWaybillNoList.clear();
							JOptionPane.showMessageDialog(null, e1.toString(), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
							return;
						} 
						/**
						 * 根据单号查询运单打印信息是否有数据，如果有就更新打印状态，否则就插入打印数据
						 */
						try {
							//查询运单打印信息表
							int printJudge=waybillService.queryPrintTimesByWaybill(waybillNo);
							printWaybillType(waybillNo, printJudge);
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, waybillNo+":"+i18n.get("foss.gui.creating.printSignUI.msgbox.updateError"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
							selectExportWaybillNoList.clear();
							return;
						}
						try {
							String[] printSerialnos =null;
							printSerialnos=printLabelBeans.get(i).getPrintSerialnos().substring(0,printLabelBeans.get(i).getPrintSerialnos().length()-1).split(",");
							for(int k=0; k<printSerialnos.length;k++){
								//记录打印日志
								GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
								printLabelEntity.setWaybillNo(waybillNo);
								printLabelEntity.setSerialNo(printSerialnos[k]);
								printLabelEntity.setPrintTime(new Date());
								printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
								printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
								printLabelEntity.setLableType(1);
								waybillService.addPrintLabelInfo(printLabelEntity);
							}
						} catch (Exception exp) {
							JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.numberPanel.waybillNo.label")+":"+waybillNo+i18n.get("foss.gui.creating.waybillEditUI.common.errorJournal"),i18n.get("foss.gui.creating.waybillEditUI.common.error"), JOptionPane.WARNING_MESSAGE);
							selectExportWaybillNoList.clear();
							return;
						}
					}
					
				}
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.printSuccessx"));
				return;
			}
			//打印完成后清空运单DTO
			printLabelBeans.clear();
			//打印完成后清空运单
			selectExportWaybillNoList.clear();
			//清空选中列表
			ui.getList().clear();
			//清空全选按钮
			ui.getAllSelectCheckBox().setSelected(false);
	}

	/**
	 * @param waybillNo
	 * @param PrintJudge
	 */
	private void printWaybillType(String waybillNo, int printJudge) {
		if(printJudge!=0){
			PrintInfoEntity record = new PrintInfoEntity();
			record.setWaybillNo(waybillNo);
			record.setPrintWaybillType("Y");
			//有数据就更新打印状态
			int UpdatePrintJudge=waybillService.updateWaybillPrimaryPrintTimes(record);
		}else{
			//没有数据就插入打印数据
			WaybillEntity waybillEntity = waybillService.queryWaybillByNumber(waybillNo);
			if(waybillEntity !=null ){
				PrintInfoEntity record = new PrintInfoEntity();
				record.setPrintType(WaybillConstants.PRINT_INFO_WAYBILL);
				record.setWaybillNo(waybillNo);
				record.setWaybillId(waybillEntity.getId());
				// record.setPrintTimes(); 
				UserEntity user = (UserEntity) SessionContext.getCurrentUser();
				record.setPrintUserCode(user.getEmployee().getEmpCode());
				record.setPrintUser(user.getEmployee().getEmpName());
				record.setPrintOrgCode(user.getEmployee().getDepartment()
						.getCode());
				record.setPrintOrg(user.getEmployee().getDepartment().getName());
				record.setPrintTime(new Date());
				record.setId(UUIDUtils.getUUID());
				record.setPrintWaybillType("Y");
				WaybillServiceFactory.getWaybillService().insertSelective(
						record);
			}
		}
	}
	
	/**
	 * 拼接流水号
	 * @param totalGoodsQty
	 * @return
	 */
	private StringBuffer getPrintSerialnoss(int totalGoodsQty) {
		StringBuffer serialnos = new StringBuffer();
		if (totalGoodsQty > 0) {
			for (int i = 1; i <= totalGoodsQty; i++) {
				serialnos.append(String.format(FORMATSTR, i)).append(COMMA);
			}
		}
		return serialnos;
	}
}
