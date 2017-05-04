package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import com.deppon.foss.base.util.define.NumberConstants;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.BatchCheckBoxColumn;
import com.deppon.foss.module.pickup.creating.client.ui.BatchCreateWaybillTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.BatchWaybillButtonColumn;
import com.deppon.foss.module.pickup.creating.client.ui.OpenBatchCreateWaybillUI;
import com.deppon.foss.module.pickup.waybill.server.utils.StringHandlerUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillStoreException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 批量提交
 * dp-foss-dongjialing
 * @author 225131
 *
 */
public class BatchWaybillSubmitAction implements IButtonActionListener<OpenBatchCreateWaybillUI>{
	//主界面
	OpenBatchCreateWaybillUI ui;
	/**
	 * 批量开单远程接口
	 */
	IWaybillHessianRemoting waybillHessianRemoting;
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillSubmitAction.class);
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	private static final Log LOG = LogFactory.getLog(WaybillSubmitConfirmAction.class);

	private static final double TWOPOINTFIVE = 2.5;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
				JTable table = ui.getTable();
				BatchCreateWaybillTableModel model = (BatchCreateWaybillTableModel) table.getModel();
				int[] indexs = table.getSelectedRows();
				if(indexs.length > NumberConstants.NUMBER_200) {
					MsgBox.showInfo("运单提交不能超过200条");
				}
				List<String> waybillNos = new ArrayList<String>();
				if(indexs.length<1) {
					MsgBox.showInfo("没有选择操作行");
					return;
				} else {
					//ui閲岀殑琛岃浆鍖栦负model閲岀殑琛�
					for (int i = 0; i < indexs.length; i++) {
						int index = indexs[i];
						if (index < 0) {
							return;
						}
						int row = table.convertRowIndexToModel(index);
						String waybillStaus = (String) model.getValueAt(row, NumberConstants.NUMBER_25);
						String waybillNo = (String) model.getValueAt(row, NumberConstants.NUMBER_4);
						if(!WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillStaus)) {
							waybillNos.add(waybillNo);
						} 
					}
				}
				if(waybillNos.size()>=1) {
					batchWaybillSubmit(waybillNos);
				}else{
					MsgBox.showInfo("请选择要提交的数据");
				}
		// TODO Auto-generated method stub
		/*List<String> waybillNos  = ui.getSelectExportWaybillNoList();
		if(waybillNos.size()>=1) {
			batchWaybillSubmit(waybillNos);
		}else{
			MsgBox.showInfo("请选择要提交的数据");
		}*/
	}

	
	@Override
	public void setInjectUI(OpenBatchCreateWaybillUI ui) {
		// TODO Auto-generated method stub
		this.ui = ui;
	}
	/**
	 * 批量提交
	 * dp-foss-dongjialing
	 * 225131
	 * @param waybillNos
	 */
	public void batchWaybillSubmit(List<String> waybillNos) {
		boolean flag = false ;
		StringBuilder badWaybillNo = new StringBuilder() ;
		for (String waybillNo : waybillNos) {
			try {
				submit(waybillNo);
			} catch (Exception e) {
				// 不做任何操作
				flag = true ;
				badWaybillNo.append(waybillNo).append(" ");
			}
		}
		if(flag){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillSubmitConfirmAction.msgBox.submitWaybillError",badWaybillNo));
		}else {
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillSubmitConfirmAction.msgBox.submitWaybillSuccess"));
		}
		waybillHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		 //通过仓管组 查询对应开单营业部
		String code = waybillHessianRemoting.queryBillingByStore(dept.getCode());
		if(null==code){
			code=dept.getCode();
		}
		/**
		 * 查询条件
		 */
		String pendingType = ((DataDictionaryValueVo) ui.getWaybillStatus().getSelectedItem()).getValueCode();
	    Date startDate = ui.getZdStartDate().getDate();
	    Date endDate = ui.getZdEndDate().getDate();
	    WaybillPendingDto waybillPendingDto = new WaybillPendingDto();
	    waybillPendingDto.setBillStartTime(startDate);
	    waybillPendingDto.setBillEndTime(endDate);
	    WaybillPendingEntity pending =new WaybillPendingEntity();
	    pending.setPendingType(pendingType);
	    pending.setIsBatchCreateWaybill(FossConstants.ACTIVE);
	    pending.setCreateOrgCode(code);
	    waybillPendingDto.setWaybillPending(pending);
	    
		List<Object> list = new ArrayList<Object>();
		if(WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(pendingType)){
			List<WaybillPendingEntity> plist =  waybillHessianRemoting.queryPending(waybillPendingDto);
			if(CollectionUtils.isNotEmpty(plist)){
				list.addAll(plist);
			}
			pending.setPendingType(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION);
			plist =  waybillHessianRemoting.queryPending(waybillPendingDto);
			if(CollectionUtils.isNotEmpty(plist)){
				list.addAll(plist);
			}
	    }else{
	    	WaybillDto dto = new WaybillDto();
	    	dto.setBillStartTime(startDate);
	    	dto.setBillEndTime(endDate);
	    	WaybillEntity entity = new WaybillEntity();
	    	entity.setPendingType(pendingType);
	    	entity.setActive(FossConstants.ACTIVE);
	    	entity.setCreateOrgCode(code);
	    	ActualFreightEntity aft = new ActualFreightEntity();
	    	aft.setIsBatchCreateWaybill(FossConstants.ACTIVE);
	    	dto.setWaybillEntity(entity);
	    	dto.setActualFreightEntity(aft);
	    	List<WaybillEntity>  wlist =waybillHessianRemoting.queryWaybill(dto);
	    	if(CollectionUtils.isNotEmpty(wlist)){
	    		list.addAll(wlist);
	    	}
	    	
	    }
		
		
		//如果结果集数据小于等于0，则提示查询为空对话框
		if(list.size() <= 0){
			MsgBox.showInfo(i18n.get("foss.gui.creating.BatchCreateWaybillQueryAction.MsgBox.nullListQuery"));
		}
		
		final JXTable table = ui.getTable();
		BatchCreateWaybillTableModel tableModel;
		
		Object[][] datas = ui.getArray(list);
		// 刷新表格
		tableModel = new BatchCreateWaybillTableModel(datas);
		table.setModel(tableModel);
		
		//new BatchCreateWaybillSearchButtonColumn(table.getColumn(1), ui, tableModel);
		BatchCheckBoxColumn checkColumn = new BatchCheckBoxColumn(table.getColumn(i18n.get("foss.gui.creating.BatchCreateWaybillQueryAction.BatchCheckBoxColumn.choice")), ui);
		List<JCheckBox> lit = checkColumn.getRenderButtonList();
		// send all check box to ui for select all
		ui.setList(lit);
		ui.setCheckBoxColumn(checkColumn);
		
		new BatchWaybillButtonColumn(table.getColumn(i18n.get("foss.gui.creating.BatchWaybillButtonColumn.column")), ui, tableModel,list);
		
		ui.refreshTable(table);
	
	}
	public void submit(String waybillNo) throws Exception {
		
		//待补录运单信息
		WaybillPendingEntity pendingEntity=null;
		//代打木架信息
		WoodenRequirePendingEntity woodenRequire = null;
		try{
			
			pendingEntity =waybillService.queryPendingByNo(waybillNo);
			if(pendingEntity == null) {
				throw new WaybillValidateException("运单信息不存在");
			}
			//获取代打木架信息
			woodenRequire =waybillService.queryWoodenRequireByNo(waybillNo);
			//获取标签信息
			List<LabeledGoodEntity>   labeledGoodEntitys =addLabeledGood(pendingEntity,woodenRequire);
			WaybillDto  waybillDto =new WaybillDto();
			WoodenRequirementsEntity woodenRequirement = getWoodenRequirementsEntity(woodenRequire);			
			//运单信息
			WaybillEntity waybillEntity = getWaybillEntity(pendingEntity);
			//运单信息基本校验
			validate(waybillEntity);
			//实际承运信息
			ActualFreightEntity actuaf= getActualFreightEntity(waybillEntity,pendingEntity);
			
			waybillDto.setWaybillEntity(waybillEntity);
			waybillDto.setWoodenRequirementsEntity(woodenRequirement);
			waybillDto.setActualFreightEntity(actuaf);
			waybillDto.setLabeledGoodEntities(labeledGoodEntitys);
			waybillDto.setOldWaybillNo(waybillEntity.getWaybillNo());
			//设置是否快递字段  add  by   yangkang
			waybillDto.setIsExpress(FossConstants.NO);
			waybillService.submitWaybill(waybillDto);
			//业务监控
			try {
				waybillService.businessMonitor(waybillDto);
			} catch (Exception e) {
				LOG.error("运单提交业务监控数据处理失败：",e);
			}
		}catch (BusinessException e){
			String message="批量开单业务异常:";
			String gsgsg="";
			String errorArgument ="";
			Object[] ob =e.getErrorArguments();
			if(ob!=null && ob.length>0){
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<ob.length;i++){
					sb.append(ob[i]);
				}
				errorArgument = sb.toString();
			}
			String messagee =e.getMessage();
			String localizedMessage =e.getLocalizedMessage();
			String nativeMessage =e.getNativeMessage();
			String errorCode = e.getErrorCode();
			if(StringUtil.isNotBlank(errorArgument)){
				gsgsg=gsgsg+errorArgument;
			}
			if(StringUtil.isNotBlank(messagee)){
				gsgsg=gsgsg+messagee;
			}
			if(StringUtil.isNotBlank(localizedMessage)){
				gsgsg=gsgsg+localizedMessage;
			}
			if(StringUtil.isNotBlank(nativeMessage)){
				gsgsg=gsgsg+nativeMessage;
			}
			if(StringUtil.isNotBlank(errorCode)){
				gsgsg=gsgsg+errorCode;
			}
			message=message+gsgsg;			
			/**
			 * 更新运单和pengding表信息
			 */						
			pendingEntity =waybillService.queryPendingByNo(waybillNo);
		    WaybillPendingEntity pend = new WaybillPendingEntity();
		    pend.setId(pendingEntity.getId());
		    pend.setPendingType(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION);
		    pend.setExceptionNote(messagee);
		    pend.setModifyTime(new Date());
			waybillService.updatePengdingWaybill(pend);
			LOG.error("批量开单业务异常:"+"栈信息："+ExceptionUtils.getFullStackTrace(e));
			 throw new Exception(e);
		}catch(Exception ee){
			String messagee="批量开单系统异常Exception:";
			
			String excepMessage = ExceptionUtils.getFullStackTrace(ee);
			 if(StringUtil.isNotBlank(excepMessage)){
				 messagee=messagee+excepMessage;
			 }
			 /**
				 * 更新运单和pengding表信息
				 */
			   pendingEntity =waybillService.queryPendingByNo(waybillNo);
			   WaybillPendingEntity pend = new WaybillPendingEntity();
			   pend.setId(pendingEntity.getId());
			   pend.setPendingType(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION);
			   pend.setExceptionNote(messagee);
			   pend.setModifyTime(new Date());
			   waybillService.updatePengdingWaybill(pend);
			   LOG.error("批量开单系统异常:"+"栈信息："+ExceptionUtils.getFullStackTrace(ee));
			 throw new Exception(ee);
		}catch(Throwable able){
			 
			String message="批量开单系统异常Throwable:";
			
			String throwable =ExceptionUtils.getFullStackTrace(able);
			if(StringUtil.isNotBlank(throwable)){
				message=message+throwable;
			}
			/**
			 * 更新运单和pengding表信息
			 */			
			pendingEntity =waybillService.queryPendingByNo(waybillNo);
			WaybillPendingEntity pend = new WaybillPendingEntity();
			pend.setId(pendingEntity.getId());
		    pend.setPendingType(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION);
		    pend.setExceptionNote(message);
		    pend.setModifyTime(new Date());
			waybillService.updatePengdingWaybill(pend);
			LOG.error("批量开单系统异常:"+"栈信息："+ExceptionUtils.getFullStackTrace(able));
			throw new Exception(able);
		}
		
	}
	
	
	 /**
     * dp-foss-dongjialing
     * 225131
     * 暂存标签信息
     */
    public List<LabeledGoodEntity> addLabeledGood(WaybillPendingEntity pendingEntity,WoodenRequirePendingEntity wood) {
    	List<LabeledGoodEntity> labeledGoodList = new ArrayList<LabeledGoodEntity>();
    	if(pendingEntity !=null) {
    		if(pendingEntity.getGoodsQtyTotal()>0) {
    			for (int i = 1; i <= pendingEntity.getGoodsQtyTotal(); i++) {
    				
    			 LabeledGoodEntity	labeledGood = new LabeledGoodEntity();
    				// id
    				labeledGood.setId(UUIDUtils.getUUID());
    				labeledGood.setWaybillNo(pendingEntity.getWaybillNo());
    				labeledGood.setActive(FossConstants.ACTIVE);
    				labeledGood.setBillTime(pendingEntity.getBillTime());
    				labeledGood.setCreateTime(new Date());
    				labeledGood.setModifyTime(new Date());
    				// 流水号
    				labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, "0"));
    				labeledGood.setInitalVale(FossConstants.YES);
    				if(null!=wood && wood.getStandGoodsNum()>0 && wood.getStandGoodsNum()>=i) {
    					labeledGood.setIsNeedWooden(FossConstants.YES);
    				}    				
    				//labeledGood.setNumberChangItems(labeled.getNumberChangItems());
    				//labeledGood.setOldSerialNo(labeled.getOldSerialNo());
    				//zxy 20131118 ISSUE-4391 包装类型，目前只有木托=SALVER
    				if(null!=wood &&wood.getSalverGoodsNum()>0 && wood.getSalverGoodsNum()>=i){
    					labeledGood.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
    				}    								    				    				
    				
    				labeledGoodList.add(labeledGood);
    			}
    		}
    	}
		return labeledGoodList;
    }
	
	
	
	/**
	 * 封装运单信息
	 * dp-foss-dongjialing
	 * 225131
	 * @param pendingEntity
	 * @return
	 */
	private WaybillEntity getWaybillEntity(WaybillPendingEntity pendingEntity) {

		// 拷贝属性值
		WaybillEntity waybillEntity = new WaybillEntity();
		try {
			if (pendingEntity != null) {
				PropertyUtils.copyProperties(waybillEntity, pendingEntity);
			}
		} catch (Exception e) {
			// 添加异常日志
			LOG.error("对象拷贝失败！\n原因：" + e.getMessage());
			// 抛出异常信息
			throw new WaybillValidateException("对象拷贝失败！\n原因：" + e.getMessage());
		}
		String goodsTypeCode = waybillEntity.getGoodsTypeCode();
		if (pendingEntity != null && ("A".equals(goodsTypeCode) || "B".equals(goodsTypeCode))) {
			// 总重量
			BigDecimal goodsWeightTotal = pendingEntity.getGoodsWeightTotal();
			// 件数
			BigDecimal goodsQtyTotal = new BigDecimal(
					pendingEntity.getGoodsQtyTotal());
			// 单件>50 是B货
			BigDecimal pjzl = goodsWeightTotal.divide(goodsQtyTotal,
					BigDecimal.ROUND_HALF_UP);
			if (pjzl.compareTo(new BigDecimal(NumberConstants.NUMBER_50)) > 0) {
				waybillEntity.setGoodsTypeCode("B");
			}
			// 木包装
			Integer woodNum = pendingEntity.getWoodNum();
			if (woodNum != null && woodNum > 0) {
				waybillEntity.setGoodsTypeCode("B");
			}
		}
		if(waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION)){
			waybillEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PC_PENDING);
		}
		waybillEntity.setCreateTime(new Date());
		return waybillEntity;

	}

	private ActualFreightEntity getActualFreightEntity(
			WaybillEntity waybillEntity, WaybillPendingEntity pendingEntity
			) {
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		// 开始开单时间
		actualFreightEntity.setStartBillTime(waybillEntity.getBillTime());
		// 运单号
		actualFreightEntity.setWaybillNo(waybillEntity.getWaybillNo());
		// 货物名称
		actualFreightEntity.setGoodsName(waybillEntity.getGoodsName());
		// 重量
		actualFreightEntity.setWeight(waybillEntity.getBillWeight());
		// 体积
		actualFreightEntity.setVolume(waybillEntity.getGoodsVolumeTotal());
		// 件数
		actualFreightEntity.setGoodsQty(waybillEntity.getGoodsQtyTotal());
		// 尺寸
		actualFreightEntity.setDimension(StringUtils.isNotEmpty(waybillEntity
				.getGoodsSize()) ? waybillEntity.getGoodsSize() : "1*1*1*1");
		// 保险声明价值
		actualFreightEntity.setInsuranceValue(waybillEntity
				.getInsuranceAmount());
		// 包装费
		actualFreightEntity
				.setPackingFee(waybillEntity.getPackageFee() != null ? waybillEntity
						.getPackageFee() : BigDecimal.valueOf(0));
		// 送货费
		actualFreightEntity
				.setDeliverFee(waybillEntity.getDeliveryGoodsFee() != null ? waybillEntity
						.getDeliveryGoodsFee() : BigDecimal.valueOf(0));
		// 装卸费
		actualFreightEntity
				.setLaborFee(waybillEntity.getServiceFee() != null ? waybillEntity
						.getServiceFee() : BigDecimal.valueOf(0));
		// 代收货款
		actualFreightEntity
				.setCodAmount(waybillEntity.getCodAmount() != null ? waybillEntity
						.getCodAmount() : BigDecimal.valueOf(0));
		// 增值费
		actualFreightEntity
				.setValueaddFee(waybillEntity.getValueAddFee() != null ? waybillEntity
						.getValueAddFee() : BigDecimal.valueOf(0));
		// 公布价运费
		actualFreightEntity
				.setFreight(waybillEntity.getTransportFee() != null ? waybillEntity
						.getTransportFee() : BigDecimal.valueOf(0));
		// 结清状态
		actualFreightEntity.setSettleStatus(FossConstants.NO);
		// 结清时间
		actualFreightEntity.setSettleTime(new Date());
		// 实际送货方式
		actualFreightEntity.setActualDeliverType(waybillEntity
				.getReceiveMethod());
		// 运单状态
		actualFreightEntity.setStatus(WaybillConstants.EFFECTIVE);
		actualFreightEntity
				.setStartStockOrgCode(queryStartStocksDepartmentService(waybillEntity));
		actualFreightEntity
				.setEndStockOrgCode(queryEndStocksDepartmentService(waybillEntity));
		// 已生效
		actualFreightEntity.setStatus(WaybillConstants.EFFECTIVE);
		// 是否是大票货
		if (waybillEntity.getGoodsWeightTotal().compareTo(new BigDecimal(NumberConstants.NUMBER_500)) > 0
				|| waybillEntity.getGoodsVolumeTotal().compareTo(
						new BigDecimal(TWOPOINTFIVE)) > 0) {
			actualFreightEntity.setBigTicketGoods(FossConstants.YES);
		}
		// 保存官网用户名
		actualFreightEntity.setChannelCustId(pendingEntity.getChannelCustId());
		// 住宅区
		actualFreightEntity.setBusinessZone(pendingEntity.getBusinessZone());
		// 商业区
		actualFreightEntity.setResidentialDistrict(pendingEntity
				.getResidentialDistrict());
		// 包装备注
		actualFreightEntity.setPackageRemark(pendingEntity.getPackageRemark());
		// 发票标记
		if (StringUtil.isNotEmpty(pendingEntity.getInvoice())) {
			actualFreightEntity.setInvoice(pendingEntity.getInvoice());
		} else {
			actualFreightEntity.setInvoice(WaybillConstants.INVOICE_02);
		}
		//是否批量开单
		actualFreightEntity.setIsBatchCreateWaybill(pendingEntity.getIsBatchCreateWaybill());
		return actualFreightEntity;
	}
	/**
	 * 获取始发部门库存
	 * @param waybillEntry
	 * @return
	 */
	private String queryStartStocksDepartmentService(WaybillEntity waybillEntry) {
		if (waybillEntry == null) {
			throw new WaybillStoreException("运单为空");
		}
		if (waybillEntry.getCreateOrgCode() == null) {
			throw new WaybillStoreException("创建部门为空");
		}

		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = waybillService
				.queryOrgAdministrativeInfoByCode(waybillEntry.getReceiveOrgCode());

		if (orgAdministrativeInfoEntity == null) {
			throw new WaybillStoreException("查询创建部门为空");
		}

		//在集中开单的情况下  配载部门取法不同
		if (FossConstants.YES.equals(waybillEntry.getPickupCentralized())) {
			if (waybillEntry.getLoadOrgCode() == null) {
				throw new WaybillStoreException("配载部门为空");
			}

			return waybillEntry.getLoadOrgCode();// 返回配载部门
		} else {

			if (FossConstants.YES.equals(orgAdministrativeInfoEntity
					.getSalesDepartment()))// 是否营业部
			{

				SaleDepartmentEntity saleDepartmentEntity = waybillService
						.querySaleDepartmentByCode(waybillEntry.getReceiveOrgCode());

				if (saleDepartmentEntity == null) {
					throw new WaybillStoreException("查询营业部为空:" + waybillEntry.getCreateOrgCode());
				}

				if (FossConstants.YES.equals(saleDepartmentEntity.getStation()))// 是否驻地部门
				{
					return saleDepartmentEntity.getTransferCenter();// 如果是，返回驻地营业部
				} else {
					return waybillEntry.getCreateOrgCode();
				}

			} else {
				throw new WaybillStoreException("不是营业部" + ReflectionToStringBuilder.toString(orgAdministrativeInfoEntity));
			}

		}

	}
	
	/**
	 * 获取到达部门库存
	 * @param waybillEntry
	 * @return
	 */
	private String queryEndStocksDepartmentService(WaybillEntity waybillEntry) {
		if (waybillEntry == null) {
			throw new WaybillStoreException("运单基础信息不能为空");
		}
		if (waybillEntry.getLastLoadOrgCode() == null) {
			throw new WaybillStoreException("最终配载部门为空");
		}
		SaleDepartmentEntity saleDepartmentEntity = waybillService
				.querySaleDepartmentByCode(waybillEntry.getLastLoadOrgCode());
		// 如果是驻地部门，返回驻地部门外场
		if (saleDepartmentEntity != null && FossConstants.YES.equals(saleDepartmentEntity.getStation())) {
			return saleDepartmentEntity.getTransferCenter();// 如果是，返回驻地营业部
		}

		return waybillEntry.getLastLoadOrgCode();// 如果不是驻地部门，返回最终配载部门

	}
	private void validate(WaybillEntity entity) {
		//运单号校验
		validateWaybillNo(entity);
		//校验联系方式
		validatePhone(entity);
		//校验走货路径
		validateFreightRout(entity);
		//校验收货人地址
		//validateAddress(entity);
		//校验提货方式
		validateReciveMethod(entity);
		//校验货物包装信息
		validatePack(entity);
		//货物名称校验
		validateGoodsName(entity);
		//货物时效校验
		validateEffective(entity);
		//运输性质校验
		validateProductCode(entity);
		//收货和发货联系人校验
		validateLinkMan(entity);
		//校验提货网点单票以及单件重量与体积上限
		validateVW(entity);
	}
	/**
	 * 校验联系方式
	 * @param bean
	 */
	private void validatePhone(WaybillEntity entity) {
		if(StringUtils.isEmpty(entity.getReceiveCustomerMobilephone()) 
				&& StringUtils.isEmpty(entity.getReceiveCustomerPhone())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullMobilephoneOrTel"));
		}
		
		if(StringUtils.isEmpty(entity.getDeliveryCustomerMobilephone()) 
				&& StringUtils.isEmpty(entity.getDeliveryCustomerPhone())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullMobilephoneOrTel"));
		}
	}

	/**
	 * 走货路径校验
	 * @param entity
	 */
	private void validateFreightRout(WaybillEntity entity){
    		if(StringUtil.isEmpty(entity.getLoadLineCode())){
    			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.one"));
    		}
		
		if(StringUtil.isEmpty(entity.getLoadOrgCode())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.two"));
		}
		
		if(StringUtil.isEmpty(entity.getLastLoadOrgCode())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.three"));
		}
		
	}
	/**
	 * 收货人地址校验
	 * 
	 */
	private void validateAddress(WaybillEntity entity) {
		if(StringUtil.isEmpty(entity.getReceiveCustomerNationCode())
				|| StringUtil.isEmpty(entity.getReceiveCustomerProvCode())
				|| StringUtil.isEmpty(entity.getReceiveCustomerCityCode())
				|| StringUtil.isEmpty(entity.getReceiveCustomerDistCode())
				|| StringUtil.isEmpty(entity.getReceiveCustomerAddress()))
		{
			throw new WaybillValidateException("收货人地址不能为空");
		}
	}
	
	/**
	 * 校验提货方式
	 */
	private void validateReciveMethod(WaybillEntity entity) {
		if(!WaybillConstants.INNER_PICKUP.equals(entity.getReceiveMethod())
				&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(entity.getReceiveMethod())) {
			throw new WaybillValidateException("内部带货提货方式只能为‘内部带货自提’或‘内部带货送货’");
		}
	}
	/**
	 * 验证货物包装信息
	 */
	private void validatePack(WaybillEntity entity) {
		Integer qtyTotal = entity.getGoodsQtyTotal();// 总件数

		// 木架信息判空操作
		if (null == entity.getPaperNum() || null == entity.getWoodNum()
				|| null == entity.getFibreNum() || null == entity.getSalverNum()
				|| null == entity.getMembraneNum()) {
			// 抛出异常信息
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.validatePack.packIsNotNull"));
		}

		/**
		 * 判断其他信息是否为空或者为空格或者为0
		 */
		if (entity.getOtherPackage() == null
				|| "".equals(entity.getOtherPackage())
				|| (entity.getOtherPackage() != null && "0".equals(entity
						.getOtherPackage()))) {
			// 木架信息判空操作
			if (0 == entity.getPaperNum().intValue()
					&& 0 == entity.getWoodNum().intValue()
					&& 0 == entity.getFibreNum().intValue()
					&& 0 == entity.getSalverNum().intValue()
					&& 0 == entity.getMembraneNum().intValue()) {
				// 抛出异常信息
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.caculateAction.validatePack.isZero"));
			}
		}

		Integer paper = Integer.valueOf(entity.getPaperNum());// 纸
		Integer wood = Integer.valueOf(entity.getWoodNum());// 木
		Integer fibre = Integer.valueOf(entity.getFibreNum());// 纤
		Integer salver = Integer.valueOf(entity.getSalverNum());// 托
		Integer membrane = Integer.valueOf(entity.getMembraneNum());// 膜
		Integer packTotal = paper + wood + fibre + salver + membrane;

		if (packTotal > qtyTotal) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.validatePack"));
		}

		/*// 判断包装体积是否大于货物总体积
		if (entity.getWoodNum() != null && entity.getWoodNum() > 0) {
			BigDecimal standGoodsVol = entity.getStandGoodsVolume() == null ? BigDecimal.ZERO
					: bean.getStandGoodsVolume();
			BigDecimal boxGoodsVol = bean.getBoxGoodsVolume() == null ? BigDecimal.ZERO
					: bean.getBoxGoodsVolume();
			if (standGoodsVol.add(boxGoodsVol).compareTo(
					bean.getGoodsVolumeTotal()) > 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validatePack.volume"));
			}
		}*/
	}
	/**
	 * 货物名称
	 */
     private void validateGoodsName(WaybillEntity entity) {
    	 if(StringUtil.isEmpty(entity.getGoodsName())) {
    		 throw new WaybillValidateException("货物名称为空");
    	 }
    	 boolean flag = waybillService.isProhibitGoods(entity.getGoodsName());
    	 if(flag) {
    		 throw new WaybillValidateException("该货物为禁运品");
    	 }
     }
     /**
      * 货物时效校验
      */
     private void validateEffective(WaybillEntity entity) {
    	 if(entity.getPreArriveTime()==null || entity.getPreCustomerPickupTime()==null ||
    			 entity.getPreDepartureTime() == null) {
    		 throw new WaybillValidateException("时效信息为空");
    	 }
    	 
     }
     /**
      * 运单号校验
      */
     private void validateWaybillNo(WaybillEntity entity) {
    	 if(StringUtil.isEmpty(entity.getWaybillNo())) {
    		 throw new WaybillValidateException("运单号为空");
    	 }
    	 boolean flag = waybillService.checkWaybillNo(entity.getWaybillNo());
    	 if(flag) {
    		 throw new WaybillValidateException("运单信息已存在"); 
    	 }
     }
     /**
      * 运输性质校验
      */
     private void validateProductCode(WaybillEntity entity) {
    	 if(StringUtil.isEmpty(entity.getProductCode())) {
    		 throw new WaybillValidateException("运输性质为空");
    	 }
     }
     /**
      * 收货联系人和发货联系人校验
      * @param entity
      */
     private void validateLinkMan(WaybillEntity entity) {
    	 if(StringUtil.isEmpty(entity.getDeliveryCustomerContact())
    			 || StringUtil.isEmpty(entity.getReceiveCustomerContact())) {
    		 throw new WaybillValidateException("收货联系人 和 发货联系人不能为空");
    	 }
     }
     /**
 	 * 
 	 * 校验提货网点单票以及单件重量与体积上限
 	 * 
 	 */
 	private void validateVW(WaybillEntity entity) {
 	// 业务工具类
 		BusinessUtils businessUtils = new BusinessUtils();
 		BigDecimal goodsWeight = entity.getGoodsWeightTotal();// 总重量
 		BigDecimal goodsVolume = entity.getGoodsVolumeTotal();// 总体积
 		BigDecimal goodsQty = new BigDecimal(entity.getGoodsQtyTotal());// 总件数

 		BigDecimal pieceWeight = goodsWeight.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN);// 单件重量
 		BigDecimal pieceVolume = goodsVolume.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN);// 单件体积
 		
 		BranchVo selectedSaleDepartmentInfo = businessUtils.getCustomerPickupOrg(
 				entity.getCustomerPickupOrgCode(),
 				entity.getProductCode(),
 				entity.getBillTime());
 		/**
 		 * 整车不校验重量和体积
 		 */
 		if (selectedSaleDepartmentInfo != null) {
 			if (selectedSaleDepartmentInfo.getSinglePieceLimitkg() != null) {
 				// 单件重量上限
 				BigDecimal singlePieceLimitkg = BigDecimal.valueOf(selectedSaleDepartmentInfo.getSinglePieceLimitkg()).divide(new BigDecimal(NumberConstants.NUMBER_100));
 				if (pieceWeight.compareTo(singlePieceLimitkg) > 0) {
 					throw new WaybillValidateException("当前运单单件重量超出该提货网点单件重量上限，请选择其他网点（网点单件重量上限为" + singlePieceLimitkg.toString() +"）");
 				}
 			}

 			if (selectedSaleDepartmentInfo.getSinglePieceLimitvol() != null) {
 				// 单件体积上限
 				BigDecimal singlePieceLimitvol = BigDecimal.valueOf(selectedSaleDepartmentInfo.getSinglePieceLimitvol()).divide(new BigDecimal(NumberConstants.NUMBER_100));
 				if (pieceVolume.compareTo(singlePieceLimitvol) > 0) {
 					throw new WaybillValidateException("当前运单单件体积超出该提货网点单件体积上限，请选择其他网点");
 				}
 			}

 			if (selectedSaleDepartmentInfo.getSingleBillLimitkg() != null) {
 				// 单票重量上限
 				BigDecimal singleBillLimitkg = BigDecimal.valueOf(selectedSaleDepartmentInfo.getSingleBillLimitkg()).divide(new BigDecimal(NumberConstants.NUMBER_100));
 				if (goodsWeight.compareTo(singleBillLimitkg) > 0) {
 					throw new WaybillValidateException("当前运单总重量超出该提货网点单票重量上限，请选择其他网点");
 				}
 			}

 			if (selectedSaleDepartmentInfo.getSingleBillLimitvol() != null) {
 				// 单票体积上限
 				BigDecimal singleBillLimitvol = BigDecimal.valueOf(selectedSaleDepartmentInfo.getSingleBillLimitvol()).divide(new BigDecimal(NumberConstants.NUMBER_100));
 				if (goodsVolume.compareTo(singleBillLimitvol) > 0) {
 					throw new WaybillValidateException("当前运单总体积超出该提货网点单票体积上限，请选择其他网点");
 				}
 			}
 		}
 	}
     /**
      * 
      * @param woodenRequire
      * @return
      */
     private WoodenRequirementsEntity getWoodenRequirementsEntity(WoodenRequirePendingEntity woodenRequire) {
    	 WoodenRequirementsEntity woodenEntity=  new WoodenRequirementsEntity();
    	 if(woodenRequire != null) {
    		 woodenEntity.setId(UUIDUtils.getUUID());
    		 woodenEntity.setWaybillNo(woodenRequire.getWaybillNo());
    		 woodenEntity.setBoxGoodsNum(woodenRequire.getBoxGoodsNum()!=null?woodenRequire.getBoxGoodsNum():0);
    		 woodenEntity.setSalverGoodsNum(woodenRequire.getSalverGoodsNum()!=null?woodenRequire.getSalverGoodsNum():0);
    		 woodenEntity.setStandGoodsNum(woodenRequire.getStandGoodsNum()!=null?woodenRequire.getStandGoodsNum():0);
    		 woodenEntity.setStandGoodsVolume(woodenRequire.getStandGoodsVolume()!=null?woodenRequire.getStandGoodsVolume():BigDecimal.ZERO);
    		 woodenEntity.setBoxGoodsVolume(woodenRequire.getBoxGoodsVolume()!=null?woodenRequire.getBoxGoodsVolume():BigDecimal.ZERO);
    		 woodenEntity.setPackageOrgCode(woodenRequire.getPackageOrgCode());
    		 woodenEntity.setSalverGoodsAmount(BigDecimal.ZERO);
    		 woodenEntity.setActive(FossConstants.ACTIVE);
 			 woodenEntity.setCreateTime(new Date());
 			 woodenEntity.setModifyTime(new Date());
 			 woodenEntity.setSalverRequirement(woodenRequire.getSalverRequirement()!=null?woodenRequire.getSalverRequirement():"导入表格没有写对内备注");
 			 woodenEntity.setBoxRequirement(woodenRequire.getBoxRequirement()!=null?woodenRequire.getBoxRequirement():"导入表格没有写对内备注");
 			 woodenEntity.setStandRequirement(woodenRequire.getStandRequirement()!=null?woodenRequire.getStandRequirement():"导入表格没有写对内备注");
 			 return woodenEntity;
    	 }
    	 return null;
     }
}
