package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpReturnGoodsUIOperateButtonColumn;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpReturnedGoodsCheckBoxColumn;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpReturnedGoodsWaybillUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpReturnedGoodsWorkOrder;
import com.deppon.foss.module.pickup.creatingexp.client.ui.LinkTableMode;
import com.deppon.foss.module.pickup.creatingexp.client.vo.ExpReturnedGoodsWaybillVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDtoResult;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ExpReturnGoodsSearchAction extends
		AbstractButtonActionListener<ExpReturnedGoodsWaybillUI> {

	// 日志
	public static final Logger LOGGER = LoggerFactory
			.getLogger(ExpReturnGoodsSearchAction.class);

	// 国际化
	private static final II18n i18n = I18nManager
			.getI18n(ExpReturnGoodsSearchAction.class);

	// 返货管理界面
	private ExpReturnedGoodsWaybillUI ui;

	@Inject
	private IWaybillService wayBillService;

	@Override
	public void setIInjectUI(ExpReturnedGoodsWaybillUI ui) {
		this.ui = ui;
	}

	/**
	 * 查询按钮功能
	 * 
	 * @author 201287
	 * @date 2015年2月3日 18:42:47
	 * @param ActionEven
	 *            e
	 */
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {

	    wayBillService = WaybillServiceFactory.getWaybillService();
		// 封装查询条件
		Map<String, Object> rsmMap = loadQueryConditions();
		String msg = rsmMap.get("msg").toString();

		/* 把查询结果转换成前台显示的VO */
		List<ExpReturnedGoodsWaybillVo> tableView = null;
		/* 用来存放每个运单最新的返货申请时间 */
		Map<String, Date> createTimeMap = new HashMap<String, Date>();

		if (Boolean.valueOf(rsmMap.get("queryFlag").toString())) {

			CrmReturnedGoodsDtoResult dto = (CrmReturnedGoodsDtoResult) rsmMap
					.get("condition");

			List<CrmReturnedGoodsDtoResult> queryResult = wayBillService
					.queryReturnGoodsWorkOrderResult(dto);
			int size = 0;

			ExpReturnedGoodsWaybillVo vo = null;
			CrmReturnedGoodsDtoResult temp = null;
			if (null != queryResult && queryResult.size() > 0) {
				size = queryResult.size();
				tableView = new ArrayList<ExpReturnedGoodsWaybillVo>();
				for (int i = 0; i < size; i++) {
					temp = queryResult.get(i);
					if(null==temp){
						continue;
					}
					vo = new ExpReturnedGoodsWaybillVo();
					vo.setIsHandle(temp.getIsold());
					// 原单号
					vo.setWaybillNo(temp.getOriWaybill());
					// 根据原单号查询返单号
//					WaybillExpressEntity entity = wayBillService
//							.queryWaybillByOriginalWaybillNo(
//									temp.getOriWaybill(),
//									WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
					//子母件--查询不是返单的记录--206860
					List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo = wayBillService.queryWaybillByOriginalWaybillNo(temp.getOriWaybill());
					WaybillExpressEntity entity = null;
					if(CollectionUtils.isNotEmpty(queryWaybillByOriginalWaybillNo)){
						entity =  queryWaybillByOriginalWaybillNo.get(0);
					}
					if (null != entity) {
						vo.setReturnWaybillNo(entity.getWaybillNo());
					}
					// 工单号（即处理编号)
					vo.setWorkOrder(temp.getDealnumber());
					// 代收货款（返货单）
					vo.setGoodsPayment(temp.getReturnMoneyReceive());
					// 收货地址（返货单）
					StringBuilder returnBillReceiveAddress = new StringBuilder();
					returnBillReceiveAddress.append(temp.getReturnProvince()) ;// 省
					if(returnBillReceiveAddress.length()>0){
						returnBillReceiveAddress.append("-");
					}
					returnBillReceiveAddress.append(temp.getReturnCity());// 市
					if(returnBillReceiveAddress.length()>0){
						returnBillReceiveAddress.append("-");
					}		 
					returnBillReceiveAddress.append(temp.getReturnArea());// 区
					if(returnBillReceiveAddress.length()>0){
						returnBillReceiveAddress.append("-");
					}		 
					returnBillReceiveAddress.append(temp.getReturnDetailaddress());// 详细地址
					
					if(returnBillReceiveAddress.length()<=0){
						
						returnBillReceiveAddress.append(temp.getOriProvince()) ;// 省
						if(returnBillReceiveAddress.length()>0){
							returnBillReceiveAddress.append("-");
						}
						returnBillReceiveAddress.append(temp.getOriCity());// 市
						if(returnBillReceiveAddress.length()>0){
							returnBillReceiveAddress.append("-");
						}		 
						returnBillReceiveAddress.append(temp.getOriArea());// 区
						if(returnBillReceiveAddress.length()>0){
							returnBillReceiveAddress.append("-");
						}		 
						returnBillReceiveAddress.append(temp.getOriDetailaddress());// 详细地址
					}
					
					vo.setAddress(returnBillReceiveAddress.toString());
					// 返货类型
					vo.setReturnGoodsType(toDataDictionaryValueVo(
							temp.getReturnType(), "returnType"));
					// 受理状态
					vo.setAcceptanceStatus(toDataDictionaryValueVo(
							temp.getReturnStatus(), "handleStatus"));
					// 创建人
					vo.setCreatorName(temp.getManReport());
					// 创建时间(取crm实体类的上报时间)
					vo.setCreateTime(temp.getTimeReport());
					// 受理人
					vo.setHandlerName(temp.getUpdateUser());
					// 受理时间
					vo.setHandleTime(temp.getUpdateTime());
					//返货方式
					vo.setReturnMode(toDataDictionaryValueVo(
							temp.getReturnMode(), "returnMode"));
					//返货原因
					vo.setReturnReason(temp.getReturnReason());
					
					tableView.add(vo);

					if (createTimeMap.containsKey(vo.getWaybillNo())) {
						if (DateUtils.getSecondDiff(
								createTimeMap.get(vo.getWaybillNo()),
								vo.getCreateTime()) > 0) {
							createTimeMap.put(vo.getWaybillNo(),
									vo.getCreateTime());
						}
					} else {
						createTimeMap
								.put(vo.getWaybillNo(), vo.getCreateTime());
					}
				}
			} else {
				msg = i18n
						.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.queryResult.None");
			}
		}

		// 刷新表格
		LinkTableMode tableModel = new LinkTableMode(ui.getArray(tableView));
		JXTable table = ui.getTable();
		table.setModel(tableModel);
		// 复选框列
		new ExpReturnedGoodsCheckBoxColumn(
				table.getColumn(i18n
						.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.linkTableMode.column.zero")));

		// 操作按钮列
		new ExpReturnGoodsUIOperateButtonColumn(
				table.getColumn(i18n
						.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.linkTableMode.column.one")),
				ui.getStatusComboBox().getModel(), ui
						.getReturnGoodsTypeComboBox().getModel(), createTimeMap);

		// 返单号列
		new ExpReturnedGoodsWorkOrder(
				table.getColumn(i18n
						.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.linkTableMode.column.four")),
				tableModel);

		ui.refreshTable(table);

		if (StringUtil.isNotBlank(msg)) {
			JOptionPane.showMessageDialog(null, msg,
					i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 根据编码转换数据字典对象
	 * 
	 * @param returnType
	 * @return
	 */
	private DataDictionaryValueVo toDataDictionaryValueVo(String code,
			String type) {
		ComboBoxModel comboModel = null;
		if ("returnType".equals(type)) {
			comboModel = ui.getReturnGoodsTypeComboBox().getModel();
		} // 如果是受理状态
		else if ("handleStatus".equals(type)) {
			comboModel = ui.getStatusComboBox().getModel();
		}else if("returnMode".equals(type)){
			comboModel = initReturnGoodsMode();
		}
		return ExpCommon.castToDataDictionaryValueVoByValueCode(code,
				comboModel);
	}

	/**
	 * 封装查询条件 业务规则： 1、返货单号和原单号只能输入一个，即二者不可同时输入；
	 * 2、若输入的是返货单号，则先从FOSS查出原单号，然后再根据原单号从CRM那边查；
	 * 3、根据原单号从CRM精确查询，不受其他查询条件限制，返货类型、受理状态和创建时间组合查询；
	 * 
	 * @author 201287
	 * @date 2015年2月5日 17:18:32
	 * @return
	 */
	private Map<String, Object> loadQueryConditions() {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		// 标志是否查询
		boolean queryFlag = true;
		String msg = "";
		CrmReturnedGoodsDtoResult dto = null;
		String wayBillNo = ui.getWaybillNo().getText().trim();
		String returnWaybillNo = ui.getReturnWaybillNo().getText().trim();

		// 如果原单号不为空，则先根据原单号查询
		if (StringUtil.isNotBlank(wayBillNo)) {
			// 原单号与返单号不能同时输入
			if (StringUtil.isNotBlank(returnWaybillNo)) {
				queryFlag = false;
				msg = i18n
						.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.queryConditionTip.CanNotInputSameTime");
			}
			// 如果原单号为空，返单号不为空
		} else if (StringUtil.isNotBlank(returnWaybillNo)) {
			// 先根据返单号获取原单号，然后通过原单号从FOSS综合查询工单号
			WaybillExpressEntity expressentity = wayBillService
					.queryWaybillByWaybillNo(returnWaybillNo,
							WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
			if (null != expressentity) {
				wayBillNo = expressentity.getOriginalWaybillNo();
			}

			if (!StringUtil.isNotBlank(wayBillNo)) {
				queryFlag=false;
				msg = i18n
						.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.queryConditionTip.NotExistWayBill");
			}
		}

		if (queryFlag) {
			dto = new CrmReturnedGoodsDtoResult();
			// 如果原单号存在则按照原单号精准查询
			if (StringUtil.isNotBlank(wayBillNo)) {
				dto.setOriWaybill(wayBillNo);
				// 否则按照其他条件模糊查询
			} else {
				// 开始创建时间
				Date startTimeDate = ui.getZdStartDate().getDate();
				dto.setCreateStartTime(startTimeDate);
				// 结束创建时间
				Date endTimeDate = ui.getZdEndDate().getDate();
				dto.setCreateEndTime(endTimeDate);

				/* 验证时间区间是否超过30天 */
				if (null != startTimeDate && null != endTimeDate) {
					long zdiff = endTimeDate.getTime()
							- startTimeDate.getTime();
					// 查询天数
					double days = zdiff / (1000.0 * 60.0 * 60.0 * 24.0);
					// 查询天数不能超过30天
					if (days > NumberConstants.NUMBER_30) {
						msg = i18n
								.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.queryConditionTip.timeOverSpan");
						queryFlag = false;
					}
				} else {
					msg = i18n
							.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.queryConditionTip.completeTimeSpan");
					queryFlag = false;
				}
				if (queryFlag) {
					// 受理状态
					DataDictionaryValueVo status = (DataDictionaryValueVo) ui
							.getStatusComboBox().getSelectedItem();
					if (!"all".equals(status.getValueCode())) {
						dto.setReturnStatus(status.getValueCode());
					}
					// 返货类型
					DataDictionaryValueVo returnType = (DataDictionaryValueVo) ui
							.getReturnGoodsTypeComboBox().getSelectedItem();
					if (!"all".equals(returnType.getValueCode())) {
						dto.setReturnType(returnType.getValueCode());
					}
					//任务部门
					DataDictionaryValueVo taskDepartment = (DataDictionaryValueVo) ui
							.getTaskDepartmentComboBox().getSelectedItem();
					if (!"all".equals(taskDepartment.getValueCode())) {
						dto.setReportDepartmentCode(taskDepartment.getValueCode());
					}
				}
			}
		}
		rsMap.put("queryFlag", queryFlag);
		rsMap.put("condition", dto);
		rsMap.put("msg", msg);
		return rsMap;
	}
	
	//初始化返货方式
			private DefaultComboBoxModel initReturnGoodsMode(){
				Injector injector = GuiceContextFactroy.getInjector();
				//数据字典服务类
				DataDictionaryValueService dictionaryValueSerivce = injector.getInstance(DataDictionaryValueService.class);
				//返货方式
				DefaultComboBoxModel combWaibillReturnModeModel = new DefaultComboBoxModel();
				if(dictionaryValueSerivce != null){
					//获取运单开单类型的实体类
					List<DataDictionaryValueEntity> list = dictionaryValueSerivce.queryByTermsCode(DictionaryConstants.PKP_WAYBILLEXPRESS_TYPE);
					if(CollectionUtils.isNotEmpty(list)){
						for (DataDictionaryValueEntity dataDictionary : list) {
							//排除返单这种类型
							if(!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(dataDictionary.getValueCode())){
								DataDictionaryValueVo vo = new DataDictionaryValueVo();
								ValueCopy.valueCopy(dataDictionary, vo);
								combWaibillReturnModeModel.addElement(vo);
							}
						}
					}
				}
				//当从数据字典中获取不到数据时，获取自定义的返货方式
				if(combWaibillReturnModeModel.getSize() == 0){
					//按票返
					DataDictionaryValueVo returnCargo = new DataDictionaryValueVo();
					returnCargo.setValueCode(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
					returnCargo.setValueName(i18n
							.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.combWaibillReturnModeModel.returnCargo"));
					combWaibillReturnModeModel.addElement(returnCargo);
					
					//按件返
					DataDictionaryValueVo returnPiece = new DataDictionaryValueVo();
					returnPiece.setValueCode(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE);
					returnPiece.setValueName(i18n
							.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.combWaibillReturnModeModel.returnPiece"));
					combWaibillReturnModeModel.addElement(returnPiece);
				}
				return combWaibillReturnModeModel;
			}
}
