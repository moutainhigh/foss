package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodChangeDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingTodoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IReModifyRouteDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcTodoJobService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodChangeEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReModifyRouteEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodStockStatusDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLabeledGoodStockDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class WaybillRfcTodoJobService implements IWaybillRfcTodoJobService{
	
	private static final int NUMBER_3000 = 3000;
	
    // 日志信息
    public static final Logger LOGGER = LoggerFactory.getLogger(WaybillRfcTodoJobService.class);
    
    public static final String DELIVERY = "送货";
	/**
	 * 代办dao
	 */
	private IPendingTodoDao pendingTodoDao;
	/**
	 * 更改单dao
	 */
	private IWaybillRfcDao waybillRfcDao;
	/**
	 * 运单管理接口
	 */
	private IWaybillManagerService waybillManagerService;
	/** 
	 * 更改单服务接口
	 * */
	private IWaybillRfcService waybillRfcService;
	 /**
     * TODO待办 dao 
     */
    private ILabeledGoodTodoDao labeledGoodTodoDao;
    /**
     *生成代办
     */
    private ILabeledGoodChangeDao labeledGoodChangeDao;
    /**
	 * 更改单受理相关查询接口
	 */
	private IWaybillRfcVarificationDao waybillRfcVarificationDao;
  
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 计算&调整走货路径类
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	 * 自身Service
	 */
	private IWaybillRfcTodoJobService waybillRfcTodoJobService;
	
	/**
	 * 签收明细表
	 */
	private ISignDetailService signDetailService;
	/**
	 * 签收结果表
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 外场接口service
	 */
	private IOutfieldService outfieldService;
	/**
	 * 库存接口
	 */
	private IStockService stockService;	
		
	/**
	 * 营业部 Service接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 运单冗余信息Service
	 */
	private IActualFreightService actualFreightService;
	/**
	 * 组织信息Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 产品类型Dao
	 */
	private IProductDao productDao;	
	 /**	 
	  * 快递代理理代理服务接口
	  */	
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	private IReModifyRouteDao reModifyRouteDao;
	
	private ILabeledGoodService labeledGoodService;
	
	/**
	 * 库存Service
	 * @description: 交接单模块service接口
	 */
	private IHandOverBillService handOverBillService;
	
	private IEmployeeService employeeService;
	
	private IWaybillExpressService waybillExpressService;

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}
	
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	/**
	 *异步生成代办
     * 
	 */
	public void sendSingleTodo(PendingTodoEntity pendingTodoEntity){
		pendingTodoEntity.setFailReason(null);
		String waybillRfcId = pendingTodoEntity.getWaybillRfcId();
		//查询更改详情
		WaybillRfcEntity waybillRfcEntity = waybillRfcDao.selectByPrimaryKey(waybillRfcId);
		if(waybillRfcEntity == null){
			pendingTodoEntity.setFailReason("rfcEntity not exists");
		}else{
			LOGGER.warn(" pendingTodoEntity id : "+pendingTodoEntity.getId());
			//TODO 作废的不要生成代办了
			//当actualFeeight不存在的时候也不要再生成代办了  因为这可能是改单号引起的 
			ActualFreightEntity a = actualFreightService.queryByWaybillNo(waybillRfcEntity.getWaybillNo());
			//查询运单具体信息
			WaybillEntity newWaybill = this.waybillManagerService.queryWaybillById(waybillRfcEntity.getNewVersionWaybillId());
			//运单冗余信息
			ActualFreightEntity a2 = actualFreightService.queryByWaybillNo(newWaybill.getWaybillNo());
			if(a2!=null && (a==null ||(a!=null && !WaybillConstants.OBSOLETE.equals(a.getStatus())) )){
				try{
					//添加待办具体信息
					addToDoActionByWaybillRfcChangeDetail(waybillRfcEntity, newWaybill);
					pendingTodoEntity.setFailReason(null);
				}catch(BusinessException e2){
					
					LOGGER.error("sendSingleTodo Exception", e2);
					pendingTodoEntity.setFailReason("BusinessException : "+e2.getClass().getName());
					
				}catch(Exception e){
					LOGGER.error("Exception", e);
					pendingTodoEntity.setFailReason("Exception : "+e.getClass().getName() );
				}catch(Throwable e){
					LOGGER.error("Throwable", e);
					pendingTodoEntity.setFailReason("Throwable : "+e.getClass().getName() );
				}
			}
		}
		if(null==pendingTodoEntity.getFailReason()){
			pendingTodoDao.delete(pendingTodoEntity);	
		}else{
			pendingTodoDao.update(pendingTodoEntity);
		}
	}
	
	
	/**
	 *异步生成代办
     * 
	 */
	public void prepareSendTodo(){		
		int start = 0;
		int limited = NumberConstants.NUMBER_5000;
		//查询5000条异常信息为N，状态为N的待办数据
		List<PendingTodoEntity>  list = pendingTodoDao.queryAllTodo(start, limited);	
		if(list==null || list.size()==0){
			return;
		}		
		while (list!=null && list.size()>0){
			//循环处理该数据
			for (Iterator<PendingTodoEntity> iterator = list.iterator(); iterator.hasNext();) {				
				PendingTodoEntity pendingTodoEntity = (PendingTodoEntity) iterator.next();
				//单个处理待办
				waybillRfcTodoJobService.sendSingleTodo(pendingTodoEntity);		
			}		
			start ++;
			list = pendingTodoDao.queryAllTodo(start*limited, limited);
		}
		
	}
	
	/**
	 * 
	 * <p>根据变更项生成待办事项</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-5 上午10:48:08
	 * @param waybillRfcEntity
	 * @param notes
	 * @see
	 */
	
	private void addToDoActionByWaybillRfcChangeDetail(WaybillRfcEntity waybillRfcEntity, WaybillEntity newWaybill) throws Exception{
		String rfcType = waybillRfcEntity.getRfcType();
		//变更类型为转运单和反货单
		if(WaybillRfcConstants.RETURN.equals(rfcType)||WaybillRfcConstants.TRANSFER.equals(rfcType)){
			boolean needInvokeTfr = true;//需要调用中转
			addTodoActionForOtherChanged(waybillRfcEntity, newWaybill, needInvokeTfr);
		}else{
			//内部变更和外部变更
			List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntityList = 
					waybillRfcVarificationDao.queryWayBillRfcChangeDetailByRfcId(waybillRfcEntity.getId());
    		
			//判断是否需要调用中转库存和走货路径，在生成待办
			boolean needInvokeTfr = false;
			for(WaybillRfcChangeDetailEntity changeDetailEntity : waybillRfcChangeDetailEntityList){
    		    String changeDetailItem = changeDetailEntity.getRfcItems();
    		    //标签信息变更
    		    if(checkWaybillRfcChangedItemNeedInvoke(changeDetailItem)){
    		    	needInvokeTfr = true;//需要调用中转
    		    	break;
    		    }
    		}	
			//判断是否需要生成待办
			boolean isContinue = isContinueAddTodoAction(needInvokeTfr, waybillRfcChangeDetailEntityList,newWaybill);
			
			if(needInvokeTfr || isContinue){//中转
				addTodoActionForOtherChanged(waybillRfcEntity, newWaybill,needInvokeTfr);
			}else{
				//判断是否就就是提货方式还是送货方式改成了送货方式
				if(checkWaybillRfcChangedDeliverMethod(waybillRfcChangeDetailEntityList)){
					addTodoActionForOtherChanged(waybillRfcEntity, newWaybill,needInvokeTfr);
				}
			}	
		}
	}
	
	/**
	 * <p>判断是否继续生成待办 根据新需求优化方案，当发生更改为只更改提货方式 并且都是送货改送货不需要生成待办</p>
	 * @param needInvokeTfr
	 * @param waybillRfcChangeDetailEntityList
	 * @return
	 */
	private boolean isContinueAddTodoAction(boolean needInvokeTfr,
			List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntityList,WaybillEntity newWaybill) {
		boolean isContinue = false;
		for(WaybillRfcChangeDetailEntity changeDetailEntity : waybillRfcChangeDetailEntityList){
		    String changeDetailItem = changeDetailEntity.getRfcItems();
		    //标签信息变更
		    if(checkWaybillRfcChangedItem(changeDetailItem)){
		    	isContinue = true;//需要继续生成待办
		    	break;
		    }
		    /**
		     * @author 200945 - wutao
		     * @date 2014-11-11
		     *<p>当收货人地址【区县】发生改变;并且 【提货网点】是驻地营业部的时候生成【待办事项】</p>
		     */
		    if("receiveCustomerArea".equals(changeDetailItem) &&checkDeptIsStation(newWaybill)){
		    	isContinue = true;//需要生成带处理待办
		    	break;
		    }
		}
		return isContinue;
	}

	/**
	 * @author 200945 - wutao
	 * @date 2014-11-11
	 *       <p>
	 *       当收货人地址发生改变并且 提货网点是驻地营业部的时候生成待办事项
	 *       </p>
	 *       <p>
	 *       排除偏线，空运，快递
	 *       </p>
	 * @param newVersionDto
	 * @return
	 */
	protected boolean checkDeptIsStation(WaybillEntity newWaybill) {
		if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(newWaybill.getProductCode())
				&& !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(newWaybill.getProductCode())
				&& !waybillExpressService.onlineDetermineIsExpressByProductCode(newWaybill.getProductCode(), newWaybill.getBillTime())) {
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(newWaybill.getCustomerPickupOrgCode());
			// 判断是否为驻地部门
			if (saleDepartmentEntity != null && FossConstants.YES.equals(saleDepartmentEntity.getStation())) {
				// 如果【驻地营业部】,并且更改后的方式为非自提的话，那就满足生成待办事项的前提
				if (!WaybillConstants.SELF_PICKUP.equals(newWaybill.getReceiveMethod())
						&& !WaybillConstants.INNER_PICKUP.equals(newWaybill.getReceiveMethod())
						&& !WaybillConstants.AIR_PICKUP_FREE.equals(newWaybill.getReceiveMethod())
						&& !WaybillConstants.AIRPORT_PICKUP.equals(newWaybill.getReceiveMethod())
						&& !WaybillConstants.AIR_SELF_PICKUP.equals(newWaybill.getReceiveMethod())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkWaybillRfcChangedDeliverMethod(
			List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntityList) {
		boolean isFlag = false;
		WaybillRfcChangeDetailEntity entity = new WaybillRfcChangeDetailEntity();
		for(WaybillRfcChangeDetailEntity changeDetailEntity : waybillRfcChangeDetailEntityList){
		    String changeDetailItem = changeDetailEntity.getRfcItems();
		    //标签信息变更
		    if("receiveMethod".equals(changeDetailItem)){
		    	isFlag = true;//需要继续生成待办
		    	entity = changeDetailEntity;
		    	break;
		    }
		}
		//只有修改了提货方式才生成待办
		if(isFlag){
			if((entity.getBeforeRfcInfo().indexOf(DELIVERY)>=0) 
					&& entity.getAfterRfcInfo().indexOf(DELIVERY)>=0){
				isFlag = false;
			}
		}
		return isFlag;
	}

	private boolean checkWaybillRfcChangedItemNeedInvoke(String item){
		//改变类型:提货网点、包装(纸木纤托膜其他)、件数、货物类型、运输性质、单号、提货方式
	    String items = "customerPickupOrgName+goodsQtyTotal+";
	    return (items.indexOf(item+'+')>=0);
	}
	
	
	private boolean checkWaybillRfcChangedItem(String item){
	    //改变类型:提货网点、包装(纸木纤托膜其他)、件数、货物类型、运输性质、单号、提货方式
	    String items = "customerPickupOrgName+paper+wood+fibre+salver+membrane+otherPackage+goodsQtyTotal+goodsType+productCode+waybillNo+isExhibitCargo+";
	    return (items.indexOf(item+'+')>=0);
	}

	/**
	 * 
	 * <p>审核通过，系统自动受理生成待办事项(更改项为除最终目的地变更外的其他更改项)</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-3 上午11:51:00
	 * @param waybillRfc
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#addTodoActionForOtherChanged(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity)
	 */
	
	public void addTodoActionForOtherChanged(WaybillRfcEntity waybillRfc, WaybillEntity waybill, boolean needInvokeTfr) throws Exception{
		//整车不生成代办
		if(FossConstants.YES.equals(waybill.getIsWholeVehicle())){
			return;
		}
		if(needInvokeTfr){
			//生成代办 并且调用中转接口生成新的走货路径
			generateLabeledGoodAndInvokeTfr(waybillRfc, waybill);
		}else{
			//只生成代办   不调用中转生成走货路径
			generateLabelGoodTodo(waybillRfc,waybill);
		}
	}


	/**
	 * 只生成代办   不调用中转生成走货路径
	 * @param waybillRfc
	 * @throws Exception
	 */
	private void generateLabelGoodTodo(WaybillRfcEntity waybillRfc, WaybillEntity waybill)
			throws Exception {
		LOGGER.info("只生成代办   不调用中转生成走货路径,waybillNo:" + waybillRfc.getWaybillNo() + ",waybillRfcId:" + waybillRfc.getId());
		WaybillLabeledGoodStockDto labeledGoodStockDto = waybillRfcService.queryWaybillLabeledGoodStock(waybillRfc.getWaybillNo());
		if(labeledGoodStockDto==null){//更改运单号有用s
			labeledGoodStockDto = waybillRfcService.queryWaybillLabeledGoodStock(waybill.getWaybillNo());
		}		
		List<LabeledGoodStockStatusDto> labeledGoodStockStatusDtos = labeledGoodStockDto.getLabeledGoodStockStatusDtos();
		if(labeledGoodStockStatusDtos!=null){
			for(LabeledGoodStockStatusDto labeledGoodStockStatusDto:labeledGoodStockStatusDtos){
				List<LabeledGoodTodoEntity> labeledGoodTodoEntitys = new ArrayList<LabeledGoodTodoEntity>();
				LabeledGoodEntity labeledGoodEntity = labeledGoodStockStatusDto.getLabeledGoodEntity();
				LabeledGoodTodoEntity labeledGoodTodoEntity = null;
				try{
					labeledGoodTodoEntity = addLabeledGoodTodo(waybillRfc,labeledGoodEntity,labeledGoodStockStatusDto.getCurrentStockOrgCode(), labeledGoodStockStatusDto.isInStock(),null);
					
					//判断是否为签收状态，如果被签收，需要更新所有的待办信息
					//TODO 这里是否需要判断是否为快递快递代理的这种
					if( ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getProductCode())  
							|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())  ){
						WaybillSignResultEntity resultEntity = new WaybillSignResultEntity();
				    	resultEntity.setWaybillNo(waybill.getWaybillNo());
				    	resultEntity.setActive(FossConstants.ACTIVE);
				    	WaybillSignResultEntity result2 = waybillSignResultService.queryWaybillSignResultByWaybillNo(resultEntity);
				    	//若存在数据，则更新该待办为已处理状态
				    	if(result2!=null){
				    		labeledGoodTodoEntity.setExceptionMsg(FossConstants.NO);
				    		labeledGoodTodoEntity.setStatus(FossConstants.ACTIVE);
				    		labeledGoodTodoEntity.setPrinted(FossConstants.YES);
				    		labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
				    		labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
				    		continue;
				    	}
					}else{
						//查询签收结果
						String signResult = 
								signDetailService.querySerialNoIsSign(waybill.getWaybillNo(), labeledGoodTodoEntity.getSerialNo() );
						
						//已经签收 不用判断判断库存  汽运情况
						if(FossConstants.YES.equals(signResult)){
							labeledGoodTodoEntity.setExceptionMsg(FossConstants.NO);
				    		labeledGoodTodoEntity.setStatus(FossConstants.ACTIVE);
				    		labeledGoodTodoEntity.setPrinted(FossConstants.YES);
				    		labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);//设置是否需要重新入库为否
				    		labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
				    		continue;
						}
					}
					
					//判断在库存中的待办
		    	    if(labeledGoodStockStatusDto.isInStock()){
		    	    	//只有走货路径全部成功的时候才是Y
						labeledGoodTodoEntity.setStatus(FossConstants.ACTIVE);
						labeledGoodTodoEntity.setHandleOrgCode(labeledGoodStockStatusDto.getCurrentStockOrgCode());//如果已经生成了走货路径 代办强制到该库存部门去打标签
						String orgName = null;
						try{
							OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
									.queryOrgAdministrativeInfoByCode(labeledGoodStockStatusDto.getCurrentStockOrgCode());
							orgName= org.getName();
						}catch(Exception e){
							e.printStackTrace();
						}
						labeledGoodTodoEntity.setHandleOrgName(orgName);
						labeledGoodTodoEntity.setRemindTime(new Date());
						labeledGoodTodoEntity.setExceptionMsg(FossConstants.NO);
		    	    }
		    	    
		    		if(labeledGoodTodoEntity!=null){
		    			labeledGoodTodoEntitys.add(labeledGoodTodoEntity);
		    		}
		    		
		    		//TODO 这里其实可以做优化的，这里的批量 插入不是真正的批量插入
		    		if(!labeledGoodTodoEntitys.isEmpty()){
		    			labeledGoodTodoDao.batchAddLabeledGoodTodo(labeledGoodTodoEntitys);
		    		}
					LOGGER.info("生成代办成功  不调用中转生成走货路径,waybillNo:" + waybillRfc.getWaybillNo() + ",waybillRfcId:" + waybillRfc.getId());
				}catch(Exception e){
					LOGGER.warn("生成代办异常 不调用中转生成走货路径,waybillNo:" + waybillRfc.getWaybillNo() + ",waybillRfcId:" + waybillRfc.getId());
					LOGGER.error("add label good generate new transfer path exception",e);
//					throw e;

				}
			}
		}
	}
	  
	


	/**
	 * 生成代办 并且调用中转接口生成新的走货路径
	 * @param waybillRfc
	 * @param waybill
	 * @throws Exception
	 */
	private void generateLabeledGoodAndInvokeTfr(WaybillRfcEntity waybillRfc,
			WaybillEntity waybill) throws Exception {
		LOGGER.info("thread-开始生成代办并且调用中转接口生成新的走货路径,waybillNo:" + waybillRfc.getWaybillNo() + ",waybillRfcId:" + waybillRfc.getId());
		List<LabeledGoodChangeEntity> changeList=  labeledGoodChangeDao.queryLastApprovedChange(waybillRfc.getWaybillNo());
	    
		WaybillLabeledGoodStockDto labeledGoodStockDto = null;
		
		//对修改单号的需要进行判断,不然实际货物信息为空了
		if(FossConstants.YES.equals(waybillRfc.getIsChangeWaybillNo())){
			labeledGoodStockDto = waybillRfcService.queryWaybillLabeledGoodStock(waybill.getWaybillNo());
		}else{
			labeledGoodStockDto = waybillRfcService.queryWaybillLabeledGoodStock(waybillRfc.getWaybillNo());	
		}	    
	    List<LabeledGoodStockStatusDto> labeledGoodStockStatusDtos = labeledGoodStockDto.getLabeledGoodStockStatusDtos();
	    
	    //生成当前部门或下一环节(在途)的待办事项	 
	    if(labeledGoodStockStatusDtos != null){	    	
	    	for(LabeledGoodStockStatusDto labeledGoodStockStatusDto : labeledGoodStockStatusDtos){
	    		List<LabeledGoodTodoEntity> labeledGoodTodoEntitys = new ArrayList<LabeledGoodTodoEntity>();
	    		LabeledGoodEntity labeledGoodEntity = labeledGoodStockStatusDto.getLabeledGoodEntity();
	    		LabeledGoodTodoEntity labeledGoodTodoEntity = null;
	    		try{
	    			String exceptionMsg = null;
	    			labeledGoodTodoEntity = addLabeledGoodTodo(waybillRfc,labeledGoodEntity,labeledGoodStockStatusDto.getCurrentStockOrgCode(), labeledGoodStockStatusDto.isInStock(),changeList);
		    		
		    		//TODO 如果这个labeledGoddTodo有库存的话，就自动从该库存生成走货路径 
		    	   // StockEntity stockEntity = stockService.queryUniqueStock(waybill.getWaybillNo(), labeledGoodTodoEntity.getSerialNo());
		    	   
		    	    if(labeledGoodStockStatusDto.isInStock()){
		    	    	//如果有库存的话，就生成新的走货路径
		    	    	//每个labelgood单独生成自己的走货路径
		    	    	List<String> modefiyTransportPathSerialNos = new ArrayList<String>();
		    	    	modefiyTransportPathSerialNos.add(labeledGoodTodoEntity.getSerialNo());
		    	    	
		    	    	// 最终配载部门
						String destOrgCode = waybill.getCustomerPickupOrgCode();
						BigDecimal totalWeight = waybill.getGoodsWeightTotal();//总重量
						BigDecimal totalVolume = waybill.getGoodsVolumeTotal();//总体积
						Integer goodsQtyTotal = waybill.getGoodsQtyTotal();//运单总货件数量
						String active = waybill.getActive();//运单状态
						String transType = waybill.getProductCode();//运输类型
						
						TransportPathEntity transportPath = new TransportPathEntity();
						transportPath.setWaybillNo(waybill.getWaybillNo());//运单号
						transportPath.setBillingTime(waybill.getBillTime());// 开单时间// 开单时间
						transportPath.setBillingOrgCode(waybill.getReceiveOrgCode());//开单所属部门编号
						
						
						String stockOrgCode= labeledGoodStockStatusDto.getCurrentStockOrgCode();
						
						stockOrgCode = queryStationDeliverOrgCode(stockOrgCode);
						if( ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)  ){							
							String outFieldCode =outfieldService.queryTransferCenterByAirDispatchCode(stockOrgCode);
							if(outFieldCode!=null && StringUtils.isNotEmpty(outFieldCode)){
								stockOrgCode = outFieldCode;
							}
						}
						
						//从当前库存部门生成走货路径 --new code
						transportPath.setCurrentOrgCode(stockOrgCode);//现所在部门编号
						
						transportPath.setDestOrgCode(destOrgCode);// 最终到达部门编号
						transportPath.setTotalWeight(totalWeight);//总重量
						transportPath.setTotalVolume(totalVolume);//总体积
						transportPath.setGoodsQtyTotal(goodsQtyTotal);//运单总货件数量
						transportPath.setAction(active);//现所处状态
						transportPath.setTransportModel(transType);//运输类型
						//如果生成过走货路径直接打印
						boolean findFreightRoute = false;
						InOutStockEntity inOutStockEntity = null;
						boolean isAirDispather = false;
						String currentOrgCode = labeledGoodStockStatusDto.getCurrentStockOrgCode();
						if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
					          SaleDepartmentEntity salesEntity =saleDepartmentService.querySaleDepartmentByCode(stockOrgCode);
					          //只有驻地出发部门货物才会在外场
					          if(salesEntity != null && StringUtils.equals(salesEntity.getStation(), FossConstants.YES)){
					            OutfieldEntity outfieldEntity = outfieldService.querySimpleOutfieldByOrgCode(salesEntity.getTransferCenter());
					            //如果不为空，才会找到对应的空运总调
					            if(outfieldEntity != null){
					            	//配置了空运总调才走这个方法
					            	if(StringUtils.isNotEmpty(outfieldEntity.getAirDispatchCode())){
						            	inOutStockEntity = new InOutStockEntity();
										// 设置运载单号
										inOutStockEntity.setWaybillNO(transportPath.getWaybillNo());
										// 设置当前部门
										inOutStockEntity.setOrgCode(outfieldEntity.getAirDispatchCode());
										// 设置操作部门
										inOutStockEntity.setOperatorCode(waybillRfc.getDrafterCode());
										// 设置操作部门名称
										inOutStockEntity.setOperatorName(waybillRfc.getDrafter());
										// 设置更改单类型
										inOutStockEntity.setInOutStockType(StockConstants.WAYBILL_RFC_IN_STOCK_TYPE);
										isAirDispather = true;
					            	}
					            }
					        }
					    }
						String operatorCode = waybillRfc.getDrafterCode();
						String operatorName = waybillRfc.getDrafter();
						
						//应要求，需要把编码转成对应的详细部门信息
//						String currentOrgName = saleDepartmentService.querySaleDepartmentByCode(currentOrgCode).getName();//当前部门简称
//						String destOrgName = saleDepartmentService.querySaleDepartmentByCode(destOrgCode).getName();//目的部门简称
//						String receiveOrgName = saleDepartmentService.querySaleDepartmentByCode(waybill.getReceiveOrgCode()).getName();//收货部门简称
						
						/**
						 * 将查询部门名称从部门表中获取，避免获取部门为空的现象
						 */
						String currentOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(currentOrgCode);	//当前部门名称
						//经发现，处理待办异常出现为空的情况，初步判断此处出现问题，故订正，货物如果是汽运、快递这样会出现问题，故订正。
						String destOrgName = waybillManagerService.getCustomerPickUpNameByCode(waybill.getCustomerPickupOrgCode(), waybill.getProductCode(), waybill.getBillTime());
						//增加对是否集中开单组进行校验,如果是开单组，则返回开单组对应的Code
						String receiveOrgName = null;
						if(FossConstants.YES.equals(waybill.getPickupCentralized())){
							receiveOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybill.getCreateOrgCode());
						}else{
							receiveOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybill.getReceiveOrgCode());//收货部门名称
						}
						
						try{
							LOGGER.info("thread- 调整走货路径 ,waybillNo" + transportPath.getWaybillNo());
							calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos,
									currentOrgCode, waybillRfc.getDrafterCode(), waybillRfc.getDrafter());
							//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
							if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
								reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
							}
							findFreightRoute = true;
						}catch(TfrBusinessException e){
							LOGGER.error("thread- 调整走货路径 TFR Exception",e);
							findFreightRoute = false;
						}
						
//						ProductItemEntity productItemEntity = productItemService.getProductItemByCache(transType,new Date());
						Map<Object, Object> maps = new HashMap<Object, Object>();
						maps.put("productCode", transType);
						maps.put("active", FossConstants.YES);
						maps.put("levels", NumberConstants.NUMBER_3);
						ProductEntity productItemEntity = productDao.getLevel3ProductInfo(maps);
						//如果当前productCode为空，最好还是给他们一些数据
						String product = null;
						if(productItemEntity == null){
							product = transType;
						}else{
							product = productItemEntity.getName();
						}
						if( ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(transType)  
								|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)  ){
							
							if(!findFreightRoute){
								//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
								exceptionMsg = "运输性质" + product +
										"没有找到走货路径 ， 设置为失败，只查询一次就跳出, 库存部门"
										+currentOrgName+"最终到达部门"+destOrgName+"开单所属部门"+receiveOrgName
										+"运单号"+waybill.getWaybillNo();
							}
							
						}
						
						if(!findFreightRoute){//找不到走货路径 使用汽运短途再查询一次
							
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT);
							try{
								LOGGER.info("thread-汽运短途 调整走货路径 ：" + transportPath.getWaybillNo());
								calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
									,operatorCode, operatorName);
								//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
								if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
									reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
								}
								findFreightRoute = true;
							}catch(TfrBusinessException e){
								LOGGER.error("thread-汽运短途 调整走货路径 异常：",e);
								findFreightRoute = false;
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用精准城运再查一次
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT);
							try{
								LOGGER.info("thread-精准城运 调整走货路径 ：" + transportPath.getWaybillNo());
								calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
										,operatorCode, operatorName);
								//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
								if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
									reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
								}
								findFreightRoute = true;
							}catch(TfrBusinessException e){
								LOGGER.error("thread-精准城运 调整走货路径 异常：",e);
								findFreightRoute = false;
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用汽运长途再查一次
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT);
							try{
								LOGGER.info("thread-汽运长途 调整走货路径 ：" + transportPath.getWaybillNo());
								calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
										,operatorCode, operatorName);
								//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
								if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
									reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
								}
								findFreightRoute = true;
							}catch(TfrBusinessException e){
								LOGGER.error("thread-汽运长途 调整走货路径 异常：",e);
								findFreightRoute = false;
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用精准卡航再查一次
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
							try{
								LOGGER.info("thread-精准卡航 调整走货路径 ：" + transportPath.getWaybillNo());
								calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
										,operatorCode, operatorName);
								//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
								if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
									reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
								}
								findFreightRoute = true;
							//这次如果再找不到 就会抛出异常
							}catch(TfrBusinessException e){
								LOGGER.error("thread-精准卡航 调整走货路径 异常：",e);
								findFreightRoute = false;
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用精准包裹再查一次  zhangwei
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_PCP);
							try{
								LOGGER.info("thread-精准包裹 调整走货路径 ：" + transportPath.getWaybillNo());
								calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
										,operatorCode, operatorName);
								//如果是空运总调
								if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
									reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
								}
								findFreightRoute = true;
							//这次如果再找不到 就会抛出异常
							}catch(TfrBusinessException e){
								LOGGER.error("thread-精准包裹 TFR Exception",e);
								findFreightRoute = false;
								//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
								exceptionMsg = "运输性质" + product +
										"没有找到走货路径 ， 设置为失败，只查询一次就跳出, 库存部门"
										+currentOrgName+"最终到达部门"+destOrgName+"开单所属部门"+receiveOrgName
										+"运单号"+waybill.getWaybillNo();
								
							}
						}
						
						//对新的新增加的货签调用中转接口 生成走货路径  并且入库  （这里面会自动入库 不用我们外部调用）
						
						if(exceptionMsg==null){
						
							String productCode = waybill.getProductCode();
							/**
							 * 如果不是偏线或者不是空运时且如果最终目的部门是驻地部门且对应的外场在走货路径内
							 * 新增经济快递的判断，因为可快递代理递代理的这种营业部   2013-11-26 9:42:38  zhangxingwang
							 */
							if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
									.equals(productCode)
									&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
											.equals(productCode)) {
								// 根据编码查询
								SaleDepartmentEntity saleDepartment = saleDepartmentService
										.querySaleDepartmentByCode(waybill
												.getCustomerPickupOrgCode());
	
								if (saleDepartment == null) {
									//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
									exceptionMsg ="营业部已经不存在了"+waybill
											.getCustomerPickupOrgCode();
								}
								//新增经济快递判断，不让其报错
								if(waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, waybill.getBillTime())){
									if(saleDepartment == null){
										OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybill.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
										 if (companyDto != null) {
											 // 代理公司编码	wayEntity.setAgentCode(StringUtil.defaultIfNull(companyDto.getAgentCompany()));    } 
											 exceptionMsg = null;
										 }
									}
								}
								// 是否驻地部门
								if (saleDepartment != null && saleDepartment.checkStation()) {
									
									String transferCenter=	saleDepartment.getTransferCenter();//外场编码
									if(StringUtils.equals(transferCenter, currentOrgCode)){
										/**
										 * 调用中转库区
										 */
										stockService.adjustStockToStation(waybill.getWaybillNo(), waybill
												.getCustomerPickupOrgCode(), modefiyTransportPathSerialNos, operatorCode, operatorName);
									}
									
								}
							}
							//走货路径生成成功oh yeh!
							if(findFreightRoute) {
								LOGGER.info("thread- 调整走货路径成功！");
								labeledGoodTodoEntity.setStatus(FossConstants.ACTIVE);//只有走货路径全部成功的时候才是Y
								labeledGoodTodoEntity.setHandleOrgCode(currentOrgCode);//如果已经生成了走货路径 代办强制到该库存部门去打标签
								String orgName = null;
								try{
									 OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentOrgCode);
							    	 orgName= org.getName();
								}catch(Exception e){
									e.printStackTrace();
								}
								labeledGoodTodoEntity.setHandleOrgName(orgName);
								labeledGoodTodoEntity.setRemindTime(new Date());
							}
							
							labeledGoodTodoEntity.setExceptionMsg(FossConstants.NO);
						}else{
							labeledGoodTodoEntity.setExceptionMsg(exceptionMsg);
							
						}
		    	    }else{		    	    	
		    	    	createPrePathDetail(labeledGoodStockStatusDto, labeledGoodTodoEntity);
		    	    }
		    	    
		    	    if(labeledGoodTodoEntity!=null){
		    			labeledGoodTodoEntitys.add(labeledGoodTodoEntity);
		    		}
		    	    
		    	    if(!labeledGoodTodoEntitys.isEmpty()){
		   	    	 	labeledGoodTodoDao.batchAddLabeledGoodTodo(labeledGoodTodoEntitys);
		    	    }
		    	    LOGGER.info("生成代办事项成功:" + labeledGoodEntity.getWaybillNo());
	    		}catch(Exception e){
	    			LOGGER.error("add label good generate new transfer path exception",e);
//	    			throw e;

	    		}
	    		
	    	}
	    }
	    LOGGER.info("thread-生成代办 并且调用中转接口生成新的走货路径,waybillNo:"+waybillRfc.getWaybillNo() + ",waybillRfcId:" + waybillRfc.getId());
	}

	/**
	 * 对于在交接单上面的运单需要生成预走货路径
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-2 23:58:48
	 * @param labeledGoodStockStatusDto
	 * @param labeledGoodTodoEntity
	 */
	private void createPrePathDetail(LabeledGoodStockStatusDto labeledGoodStockStatusDto, LabeledGoodTodoEntity labeledGoodTodoEntity) {
		LOGGER.info("thread-对于在交接单上面的运单需要生成预走货路径:" + labeledGoodStockStatusDto.getLabeledGoodEntity().getWaybillNo());
		LabeledGoodEntity labeledGoodEntity = labeledGoodStockStatusDto.getLabeledGoodEntity();
		String needCalculatePathDetail = calculateTransportPathService.queryNewHandoverNoByWaybillNo(labeledGoodStockStatusDto.getLabeledGoodEntity().getWaybillNo(), labeledGoodStockStatusDto.getLabeledGoodEntity().getSerialNo());
		LOGGER.info("thread-查询最近的交接单号：" + needCalculatePathDetail);
    	if(StringUtils.isNotEmpty(needCalculatePathDetail)){
    		HandOverBillEntity handoverbill = handOverBillService.queryHandOverBillByNo(needCalculatePathDetail);
    		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(labeledGoodEntity.getWaybillNo());
    		boolean findFreightRoute = false;	
    		String exceptionMsg = null;
    		
    		//判定是否交接单为空
    		if(handoverbill == null){
    			exceptionMsg = "交接单号"+needCalculatePathDetail+"信息不存在了";
    			//只是为了不走下面的方法把值设置为true
    			findFreightRoute = true;
    		}
    		//判定当前库存部门是否当前目的站与交接单到达部门一致，如果一致不需要调整走货路径，否则走货路径会往多加一条路由而错,
    		//如:A-B-C-D,现在在D，如果此时调整走货路径会出现A-B-C-D-C-D
    		if(findFreightRoute && StringUtils.equals(needCalculatePathDetail, waybillEntity.getCustomerPickupOrgCode())){	
    			//帮中转判断是否目的站和当前库存部门是否一致，如果一致，则不走调整走货路径
    			findFreightRoute = true;
    		}else{
    			if(handoverbill != null){
        			try{
        				LOGGER.info("thread-在途中修改对应的走货路径,waybillNo:" + labeledGoodEntity.getWaybillNo());
        				calculateTransportPathService.alterPathDetailForNOStore(labeledGoodEntity.getWaybillNo(), labeledGoodEntity.getSerialNo(),
        	    				handoverbill.getArriveDeptCode(), waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getCreateOrgCode(), waybillEntity.getProductCode());
        				findFreightRoute = true;
        			}catch(TfrBusinessException e){
        				LOGGER.error("thread-在途中 TFR Exception",e);
        				findFreightRoute = false;
        			}
        			
        			//开单部门名称
        			OrgAdministrativeInfoEntity createOrgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillEntity.getCreateOrgCode());
        			String createOrgName = null;
        			//开单部门
        			if(createOrgInfo != null){
        				createOrgName = createOrgInfo.getName();
        			}else{
        				createOrgName = waybillEntity.getCreateOrgCode();
        			}
        			//库存部门
        			String stockName = handoverbill.getArriveDept();
        			//到达部门
        			String arriveOrgName = waybillManagerService.getCustomerPickUpNameByCode(waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getProductCode(), waybillEntity.getBillTime());
        			//产品类型
        			Map<Object, Object> maps = new HashMap<Object, Object>();
        			maps.put("productCode", waybillEntity.getProductCode());
        			maps.put("active", FossConstants.YES);
        			maps.put("levels", NumberConstants.NUMBER_3);
        			ProductEntity productItemEntity = productDao.getLevel3ProductInfo(maps);
        			String product = null;
        			if(productItemEntity == null){
        				product = waybillEntity.getProductCode();
        			}else{
        				product = productItemEntity.getName();
        			}
        			if( ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode())  
        					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())  ){
        				if(!findFreightRoute){
        					//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
        					exceptionMsg = "运输性质" + product +"没有找到走货路径 ， 设置为失败，只查询一次就跳出, 交接单到达部门"
        					+stockName+"交接单"+handoverbill.getHandOverBillNo()+"最终到达部门"+arriveOrgName+"开单所属部门"+createOrgName+"运单号"+waybillEntity.getWaybillNo();
        				}
        			}
        			
        			if(!findFreightRoute){//找不到走货路径 使用汽运短途再查询一次    				
        				try{
        					LOGGER.info("thread-汽运短途 在途中修改对应的走货路径waybillNo:" + labeledGoodEntity.getWaybillNo());
        					calculateTransportPathService.alterPathDetailForNOStore(labeledGoodEntity.getWaybillNo(), labeledGoodEntity.getSerialNo(),
            	    				handoverbill.getArriveDeptCode(), waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getCreateOrgCode(), ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT);
        					findFreightRoute = true;
        				}catch(TfrBusinessException e){
        					LOGGER.error("thread-汽运短途 在途中修改对应的走货路径异常：",e);
        					findFreightRoute = false;
        				}
        			}
        			
        			if(!findFreightRoute){//如果找不到 就用精准城运再查一次
        				try{
        					LOGGER.info("thread-精准城运 在途中修改对应的走货路径waybillNo:" + labeledGoodEntity.getWaybillNo());
        					calculateTransportPathService.alterPathDetailForNOStore(labeledGoodEntity.getWaybillNo(), labeledGoodEntity.getSerialNo(),
            	    				handoverbill.getArriveDeptCode(), waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getCreateOrgCode(), ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT);
        					findFreightRoute = true;
        				}catch(TfrBusinessException e){
        					LOGGER.error("thread-精准城运 在途中修改对应的走货路径异常：",e);
        					findFreightRoute = false;
        				}
        			}
        			
        			if(!findFreightRoute){//如果找不到 就用汽运长途再查一次
        				try{
        					LOGGER.info("thread-汽运长途 在途中修改对应的走货路径waybillNo:" + labeledGoodEntity.getWaybillNo());
        					calculateTransportPathService.alterPathDetailForNOStore(labeledGoodEntity.getWaybillNo(), labeledGoodEntity.getSerialNo(),
            	    				handoverbill.getArriveDeptCode(), waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getCreateOrgCode(), ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT);
        					findFreightRoute = true;
        				}catch(TfrBusinessException e){
        					LOGGER.error("thread-汽运长途 在途中修改对应的走货路径异常：",e);
        					findFreightRoute = false;
        				}
        			}
        			
        			if(!findFreightRoute){//如果找不到 就用精准卡航再查一次
        				try{
        					LOGGER.info("thread-精准卡航 在途中修改对应的走货路径waybillNo:" + labeledGoodEntity.getWaybillNo());
        					calculateTransportPathService.alterPathDetailForNOStore(labeledGoodEntity.getWaybillNo(), labeledGoodEntity.getSerialNo(),
            	    				handoverbill.getArriveDeptCode(), waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getCreateOrgCode(), ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
        					findFreightRoute = true;
        				//这次如果再找不到 就会抛出异常
        				}catch(TfrBusinessException e){
        					LOGGER.error("thread-精准卡航 在途中修改对应的走货路径异常：",e);
        					findFreightRoute = false;
        				}
        			}
        			
        			if(!findFreightRoute){//如果找不到 就用精准包裹再查一次  zhangwei
        				try{
        					LOGGER.info("thread-精准包裹 在途中修改对应的走货路径waybillNo:" + labeledGoodEntity.getWaybillNo());
        					calculateTransportPathService.alterPathDetailForNOStore(labeledGoodEntity.getWaybillNo(), labeledGoodEntity.getSerialNo(),
            	    				handoverbill.getArriveDeptCode(), waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getCreateOrgCode(), ProductEntityConstants.PRICING_PRODUCT_PCP);
        					findFreightRoute = true;
        				//这次如果再找不到 就会抛出异常
        				}catch(TfrBusinessException e){
        					LOGGER.error("thread-精准包裹 TFR Exception",e);
        					findFreightRoute = false;
        					//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
        					exceptionMsg = "运输性质" + product +"没有找到走货路径 ， 设置为失败，只查询一次就跳出, 交接单到达部门"
        					+stockName+"最终到达部门"+arriveOrgName+"开单所属部门"+createOrgName+"运单号"+waybillEntity.getWaybillNo();
        					
        				}
        			}
    			}
    		}
    		if(exceptionMsg == null){
    			if(findFreightRoute) {
					labeledGoodTodoEntity.setStatus(FossConstants.NO);//只有走货路径全部成功的时候才是Y
					labeledGoodTodoEntity.setNeedRestock(FossConstants.ACTIVE);
				}				
				labeledGoodTodoEntity.setExceptionMsg(FossConstants.NO);
    		}else{
    			labeledGoodTodoEntity.setExceptionMsg(exceptionMsg);
    		}
    	}	
		LOGGER.info("thread-在交接单上面的运单生成走货路径成功,waybillNo:" + labeledGoodEntity.getWaybillNo());
	}


	public String queryStationDeliverOrgCode(String orgCode) {
		//根据外场部门编码，查询该外场的驻地可出发营业部对象
		SaleDepartmentEntity deliverDepartment = orgAdministrativeInfoComplexService.queryStationLeaveOrgByOutfieldCode(orgCode);
		if(deliverDepartment == null){
			//如果当前营业部是驻地部门，取对应外场有出发的驻地部门
			//ISSUE-2122 如果当前营业部不能做出发，也取对应外场有出发的驻地部门
			SaleDepartmentEntity dept = saleDepartmentService.querySaleDepartmentByCode(orgCode);
			if(dept == null){
				return orgCode;
			}else if(FossConstants.YES.equals(dept.getStation()) || !FossConstants.YES.equals(dept.getLeave())){
				String transferCenter = dept.getTransferCenter();
				if(transferCenter == null){
//					throw new WaybillRfcChangeException(i18n.get("foss.gui.creating.OnLineWaybillService.nullTransferCenter",dept.getName()));
					return null;
				}
				SaleDepartmentEntity department = orgAdministrativeInfoComplexService.queryStationLeaveOrgByOutfieldCode(transferCenter);
				if(department == null){
					return orgCode;
				}else{
					return department.getCode();
				}
			}
			return orgCode;
		}else{
			return deliverDepartment.getCode();
		}
	}
	
	/**
	 * 
	 * <p>待办事项</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-3 下午8:51:19
	 * @param waybillRfc
	 * @param orgCode 
	 * @param isInStock 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#addTodoAction(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity, java.lang.String)
	 */
	@Transactional
	public LabeledGoodTodoEntity addLabeledGoodTodo(WaybillRfcEntity waybillRfc, LabeledGoodEntity labeledGoodEntity,
			String orgCode, boolean isInStock, List<LabeledGoodChangeEntity> changeList) {
	    String orgCode2 = orgCode;
	    String orgName = null;
	    if(changeList!=null && changeList.size()>0){
	    	for(int i =0 ;i <changeList.size(); i ++){
	    		LabeledGoodChangeEntity changeEntity = changeList.get(i);
	    		if(changeEntity==null){
	    			continue;
	    		}
	    		if(changeEntity.getSerialNo().equals(labeledGoodEntity.getSerialNo()) 
	    				&&  StringUtils.isNotEmpty(changeEntity.getReceiveOrgCode())){
	    			orgCode2 = changeEntity.getReceiveOrgCode();
	    			orgName  = changeEntity.getReceiveOrgName();
	    		}
	    	}
	    }
	    
	    if(StringUtils.isNotEmpty(orgCode2) && StringUtils.isEmpty(orgName)){
	    	 OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode2);
	    	 orgName= org.getName();
	    }
	    //拼接数据
    	LabeledGoodTodoEntity labeledGoodTodoEntity = new LabeledGoodTodoEntity();
    	//货物ID
	    labeledGoodTodoEntity.setLabeledGoodId(labeledGoodEntity.getId());
	    //货物对应的流水号编码
	    labeledGoodTodoEntity.setSerialNo(labeledGoodEntity.getSerialNo());
	    //是否打印
	    labeledGoodTodoEntity.setPrinted(FossConstants.NO);
	    //更改单ID
	    labeledGoodTodoEntity.setWaybillRfcId(waybillRfc.getId());
	    //如果在库存中,则设置对应的受理部门
	    if(isInStock){
		    labeledGoodTodoEntity.setHandleOrgCode(orgCode2);
		    labeledGoodTodoEntity.setHandleOrgName(orgName);
			labeledGoodTodoEntity.setRemindTime(waybillRfc.getOperateTime());
	    }
	    //设置创建时间
	    labeledGoodTodoEntity.setCreateTime(new Date());
	    //设置状态默认为未处理
	    labeledGoodTodoEntity.setStatus(FossConstants.NO);
	    //生成执行节点
	    // labeledGoodTodoEntity.setActuatingNode(addActuatingNode(waybillRfc,labeledGoodEntity,orgCode2));
	    labeledGoodTodoEntity.setIsSendRemind(FossConstants.NO);
	    labeledGoodTodoEntity.setExceptionMsg(FossConstants.NO);//异常信息默认为N
	    labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);//设置需要重新入库为否
	    return labeledGoodTodoEntity;
	}
	
	
	
	/**
	 * 另一个job 定期扫描所有没有生成走货路径的代办 进行补充处理
	 */
	public void handlerTodoJob(){
		
		//分页查询所有走货路径生成status='N'并且没有异常的代办
		LabeledGoodTodoEntity labeledGoodTodoEntity = new LabeledGoodTodoEntity();
		labeledGoodTodoEntity.setExceptionMsg(FossConstants.NO);
		labeledGoodTodoEntity.setStatus(FossConstants.NO);
		int start = 0;
		int limited = NumberConstants.NUMBER_5000;
		List<LabeledGoodTodoEntity > list = labeledGoodTodoDao.queryTodoByStatusAndExceptionMsg(labeledGoodTodoEntity, start, limited);
		LOGGER.warn("启动第一次处理历史代办 这次的createTime为Null");
		while (list!=null && list.size()>0){
			//自动处理代办
			boolean relax = false;
			Date createDate = null;
			for (Iterator<LabeledGoodTodoEntity> iterator = list.iterator(); iterator.hasNext();) {
				LabeledGoodTodoEntity labeledGoodTodoEntity2 = iterator.next();
				
				LOGGER.warn(" 处理历史代办详细id:  "+labeledGoodTodoEntity2.getId() 
						+",  rfcid: "+labeledGoodTodoEntity2.getWaybillRfcId() + 
						" createTime: "
						+ labeledGoodTodoEntity2.getCreateTime());
				try{
					waybillRfcTodoJobService.handleSingleTodo(labeledGoodTodoEntity2);
				}catch(Exception e){
					LOGGER.error("see handle自动处理单个代办Expcetion", e);
					labeledGoodTodoEntity2.setExceptionMsg(StringUtils.substring(e.getMessage()+":" +
			    					ExceptionUtils.getFullStackTrace(e),0, NUMBER_3000));
					
					labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity2);
					
				}catch(Throwable e){
					LOGGER.error("see handle自动处理单个代办Expcetion", e);
					labeledGoodTodoEntity2.setExceptionMsg(StringUtils.substring(e.getMessage()+":" +
			    					ExceptionUtils.getFullStackTrace(e),0, NUMBER_3000));
					
					labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity2);
					relax = true;
					break;//这里可能数据库死锁 不能再处理了 等下个循环再说
				}
			}
			
			if(relax){
				break; //等待下次处理
			}
			LabeledGoodTodoEntity todo = list.get(list.size()-1);
			createDate = todo.getCreateTime(); 
			labeledGoodTodoEntity.setCreateTime(createDate);
			LOGGER.warn("开始处理历史代办 这次的createTime为"+createDate);
			
			list = labeledGoodTodoDao.queryTodoByStatusAndExceptionMsg(labeledGoodTodoEntity,
					start, limited);
		}
		
	}
	
	/**
	 * 
	 * <p>根据走货路径生成待办操作节点</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-14 上午11:25:57
	 * @param waybillRfc
	 * @param labeledGoodEntity
	 * @param orgCode
	 * @return
	 * @see
	 */
	/**private String addActuatingNode(WaybillRfcEntity waybillRfc, LabeledGoodEntity labeledGoodEntity, String orgCode){
		List<PathDetailEntity> pathDetailEntityList = null;
		try {
			pathDetailEntityList = calculateTransportPathService.queryTotalPath(waybillRfc.getWaybillNo(), labeledGoodEntity.getSerialNo());
		} catch (TfrBusinessException e) {
			//新增件数没有走货路径
			return orgCode;
		}
		StringBuffer actuatingNode = new StringBuffer();
		for(PathDetailEntity pathDetailEntity : pathDetailEntityList){
			//找到当前部门为出发部门
			if(orgCode.equals(pathDetailEntity.getOrigOrgCode())){
				actuatingNode.append(pathDetailEntity.getOrigOrgCode());
			}
			//找到新的起点后，保存之后的全部走货路径
			if(actuatingNode.length()>0){
				actuatingNode.append(",").append(pathDetailEntity.getObjectiveOrgCode());
			}
		}
		// 走货路径上未找到当前部门直接加入执行节点
		if(actuatingNode.length()==0){
			actuatingNode.append(orgCode);
		}
		return actuatingNode.toString();
	}*/
	
	/**
	 * @return the waybillRfcTodoJobService
	 */
	public IWaybillRfcTodoJobService getWaybillRfcTodoJobService() {
		return waybillRfcTodoJobService;
	}

	/**
	 * TODO
	 * 自动处理单个代办
	 * @param labeledGoodTodoEntity2
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,
			noRollbackFor=com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException.class)
	public void handleSingleTodo(LabeledGoodTodoEntity labeledGoodTodoEntity){
		String exceptionMsg = null;
		LOGGER.info("job开始处理代办,waybillRfc:" + labeledGoodTodoEntity.getWaybillRfcId());
		//判定货签信息数据是否存在
		LabeledGoodEntity labelEntity = labeledGoodService.queryByPrimaryKey(labeledGoodTodoEntity.getLabeledGoodId());
		if(labelEntity == null){
			LOGGER.info("货签实体数据已经不存在了");
			labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
			labeledGoodTodoEntity.setStatus(FossConstants.YES);//设置状态为已经 处理
			labeledGoodTodoEntity.setPrinted(FossConstants.YES);
			labeledGoodTodoEntity.setExceptionMsg("LabeledGood entity is null");
			labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
			return;
		}
		
		String rfcid = labeledGoodTodoEntity.getWaybillRfcId();
		WaybillRfcEntity waybillRfc = waybillRfcService.selectByPrimaryKey(rfcid);
		//判定是否更改单实体是否为空
		if(waybillRfc == null){
			LOGGER.info("更改单实体数据已经不存在了");
			labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
			labeledGoodTodoEntity.setStatus(FossConstants.YES);//设置状态为已经 处理
			labeledGoodTodoEntity.setPrinted(FossConstants.YES);
			labeledGoodTodoEntity.setExceptionMsg("waybillRfc entity is null");
			labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
			return;
		}
		String newWayillId = waybillRfc.getNewVersionWaybillId();
		/** WaybillDto dto = waybillManagerService.getWaybillDtoById(newWayillId);
		WaybillEntity waybill = dto.getWaybillEntity(); */
		WaybillEntity waybill = this.waybillManagerService.queryWaybillById(newWayillId);
		
		//判定运单实体数据是否为空
		if(waybill == null){
			LOGGER.info("运单实体数据已经不存在了");
			labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
			labeledGoodTodoEntity.setStatus(FossConstants.YES);//设置状态为已经 处理
			labeledGoodTodoEntity.setPrinted(FossConstants.YES);
			labeledGoodTodoEntity.setExceptionMsg("waybill entity is null");
			labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
			return;
		}
		
		//再次需要判定已经作废中止,作废中止不需要进行数据的生成
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybill.getWaybillNo());
		if(actualFreightEntity == null || WaybillConstants.OBSOLETE.equals(actualFreightEntity.getStatus())
				|| WaybillConstants.ABORTED.equals(actualFreightEntity.getStatus())){
			LOGGER.info("实际承运信息实体数据已经不存在了或者已经作废中止");
			labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
			labeledGoodTodoEntity.setStatus(FossConstants.YES);//设置状态为已经 处理
			labeledGoodTodoEntity.setPrinted(FossConstants.YES);
			labeledGoodTodoEntity.setExceptionMsg("actualFreight entity is null or obsolete or aborted");
			labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
			return;
		}
		
		if( ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getProductCode())  
				|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())  ){
			LOGGER.info("job-PLP||AF的签收结果,waybillNo:" + waybill.getWaybillNo());
			WaybillSignResultEntity resultEntity = new WaybillSignResultEntity();
	    	resultEntity.setWaybillNo(waybill.getWaybillNo());
	    	resultEntity.setActive(FossConstants.ACTIVE);
	    	WaybillSignResultEntity result2 = waybillSignResultService.queryWaybillSignResultByWaybillNo(resultEntity);
	    	if(result2 != null){
	    		labeledGoodTodoEntity.setExceptionMsg(FossConstants.NO);
	    		labeledGoodTodoEntity.setStatus(FossConstants.ACTIVE);
	    		labeledGoodTodoEntity.setPrinted(FossConstants.YES);
	    		labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
	    		return;
	    	}
		}else{
			LOGGER.info("job-查询签收结果,waybillNo:" + waybill.getWaybillNo());
			//查询签收结果
			String signResult = 
					signDetailService.querySerialNoIsSign(waybill.getWaybillNo(), labeledGoodTodoEntity.getSerialNo() );
			
			//已经签收 不用判断判断库存  汽运情况
			if(FossConstants.YES.equals(signResult)){
				labeledGoodTodoEntity.setExceptionMsg(FossConstants.NO);
	    		labeledGoodTodoEntity.setStatus(FossConstants.ACTIVE);
	    		labeledGoodTodoEntity.setPrinted(FossConstants.YES);
	    		labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
	    		labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
	    		return;
			}
		}
		
		StockEntity stockEntity = stockService.queryUniqueStock(waybill.getWaybillNo(), labeledGoodTodoEntity.getSerialNo());
 	   
		if(stockEntity!=null){
			LOGGER.info("job-存在库存，生成新的走货路径,waybillNo:" + stockEntity.getWaybillNO() + ",所在部门编号:" + stockEntity.getOrgCode());
	    	//如果有库存的话，就生成新的走货路径
	    	//每个labelgood单独生成自己的走货路径
	    	List<String> modefiyTransportPathSerialNos = new ArrayList<String>();
	    	modefiyTransportPathSerialNos.add(labeledGoodTodoEntity.getSerialNo());
	    	
	    	// 最终配载部门
			String destOrgCode = waybill.getCustomerPickupOrgCode();
			BigDecimal totalWeight = waybill.getGoodsWeightTotal();//总重量
			BigDecimal totalVolume = waybill.getGoodsVolumeTotal();//总体积
			Integer goodsQtyTotal = waybill.getGoodsQtyTotal();//运单总货件数量
			String active = waybill.getActive();//运单状态
			String transType = waybill.getProductCode();//运输类型
			
			TransportPathEntity transportPath = new TransportPathEntity();
			transportPath.setWaybillNo(waybill.getWaybillNo());//运单号
			transportPath.setBillingTime(waybill.getBillTime());// 开单时间// 开单时间
			transportPath.setBillingOrgCode(waybill.getReceiveOrgCode());//开单所属部门编号
			
			//从当前库存部门生成走货路径 --new code
			
			String stockOrgCode= stockEntity.getOrgCode() ;
			stockOrgCode = queryStationDeliverOrgCode(stockOrgCode);
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
				String outFieldCode =outfieldService.queryTransferCenterByAirDispatchCode(stockOrgCode);
				if(outFieldCode!=null && StringUtils.isNotEmpty(outFieldCode)){
					stockOrgCode = outFieldCode;
				}
			}
			
			transportPath.setCurrentOrgCode(stockOrgCode);//现所在部门编号
			
			transportPath.setDestOrgCode(destOrgCode);// 最终到达部门编号
			transportPath.setTotalWeight(totalWeight);//总重量
			transportPath.setTotalVolume(totalVolume);//总体积
			transportPath.setGoodsQtyTotal(goodsQtyTotal);//运单总货件数量
			transportPath.setAction(active);//现所处状态
			transportPath.setTransportModel(transType);//运输类型
			//如果生成过走货路径直接打印
			boolean findFreightRoute = false;
			InOutStockEntity inOutStockEntity = null;
			String currentOrgCode = stockEntity.getOrgCode();
			boolean isAirDispather = false;
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
		          SaleDepartmentEntity salesEntity =saleDepartmentService.querySaleDepartmentByCode(stockOrgCode);
		          //只有驻地出发部门货物才会在外场
		          if(salesEntity != null && StringUtils.equals(salesEntity.getStation(), FossConstants.YES)){
		            OutfieldEntity outfieldEntity = outfieldService.querySimpleOutfieldByOrgCode(salesEntity.getTransferCenter());
		            //如果不为空，才会找到对应的空运总调
		            if(outfieldEntity != null){
		            	//配置了空运总调才走这个方法
		            	if(StringUtils.isNotEmpty(outfieldEntity.getAirDispatchCode())){
			            	inOutStockEntity = new InOutStockEntity();
							// 设置运载单号
							inOutStockEntity.setWaybillNO(transportPath.getWaybillNo());
							// 设置当前部门
							inOutStockEntity.setOrgCode(outfieldEntity.getAirDispatchCode());
							// 设置操作部门
							inOutStockEntity.setOperatorCode(waybillRfc.getDrafterCode());
							// 设置操作部门名称
							inOutStockEntity.setOperatorName(waybillRfc.getDrafter());
							// 设置更改单类型
							inOutStockEntity.setInOutStockType(StockConstants.WAYBILL_RFC_IN_STOCK_TYPE);
							isAirDispather = true;
		            	}
		            }
		        }
		    }
			String operatorCode = waybillRfc.getDrafterCode();
			String operatorName = waybillRfc.getDrafter();
			/**
			 * 将查询部门名称从部门表中获取，避免获取部门为空的现象
			 */
			String currentOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(currentOrgCode);	//当前部门名称
			//经发现，处理待办异常出现为空的情况，初步判断此处出现问题，故订正，货物如果是汽运、快递这样会出现问题，故订正。
			String destOrgName = waybillManagerService.getCustomerPickUpNameByCode(waybill.getCustomerPickupOrgCode(), waybill.getProductCode(), waybill.getBillTime());
			//增加对是否集中开单组进行校验,如果是开单组，则返回开单组对应的Code
			String receiveOrgName = null;
			if(StringUtils.isNotEmpty(waybill.getPickupCentralized()) && StringUtils.equals(FossConstants.YES, waybill.getPickupCentralized())){
				receiveOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybill.getCreateOrgCode());
			}else{
				receiveOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybill.getReceiveOrgCode());//收货部门名称
			}
			
			//内部变更和外部变更
			List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntityList = 
					waybillRfcVarificationDao.queryWayBillRfcChangeDetailByRfcId(rfcid);
    		
			boolean needInvokeTfr = false;
			for(WaybillRfcChangeDetailEntity changeDetailEntity : waybillRfcChangeDetailEntityList){
    		    String changeDetailItem = changeDetailEntity.getRfcItems();
    		    //标签信息变更
    		    if(checkWaybillRfcChangedItemNeedInvoke(changeDetailItem)){
    		    	needInvokeTfr = true;//需要调用中转
    		    	break;
    		    }
    		}
			
			//如果涉及件数和目的站更改需要调用走货路径，其他不走走货路径
			if(!needInvokeTfr || StringUtils.equals(transportPath.getCurrentOrgCode(), transportPath.getDestOrgCode())){
				findFreightRoute = true;
			}else{
				LOGGER.info("JOB开始生成走货路径...");
				try{
					LOGGER.info("job-调用中转生成走货路径接口,waybillNo:" + transportPath.getWaybillNo());
					calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
							, waybillRfc.getDrafterCode(), waybillRfc.getDrafter());
					//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
					if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
						reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
					}
					findFreightRoute = true;
				}catch(TfrBusinessException e){
					LOGGER.error("job-TFR Exception",e);
					findFreightRoute = false;
				}
				
//					ProductItemEntity productItemEntity = productItemService.getProductItemByCache(transType,new Date());
				Map<Object, Object> maps = new HashMap<Object, Object>();
				maps.put("productCode", transType);
				maps.put("active", FossConstants.YES);
				maps.put("levels", NumberConstants.NUMBER_3);
				ProductEntity productItemEntity = productDao.getLevel3ProductInfo(maps);
				String product = null;
				if(productItemEntity == null){
					product = transType;
				}else{
					product = productItemEntity.getName();
				}
				if( ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(transType)  
						|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)  ){
					
					if(!findFreightRoute){
						//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
						exceptionMsg = "运输性质" + product +
								"没有找到走货路径 ， 设置为失败，只查询一次就跳出, 库存部门"
								+currentOrgName+"最终到达部门"+destOrgName+"开单所属部门"+receiveOrgName
								+"运单号"+waybill.getWaybillNo();
						
					}
					
				}
				
				if(!findFreightRoute){//找不到走货路径 使用汽运短途再查询一次
					
					transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT);
					try{
						LOGGER.info("job-汽运短途 调用中转生成走货路径接口,waybillNo:" + transportPath.getWaybillNo());
						calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
							,operatorCode, operatorName);
						//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
						if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
							reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
						}
						findFreightRoute = true;
					}catch(TfrBusinessException e){
						LOGGER.error("job-汽运短途  TFR Exception",e);
						findFreightRoute = false;
					}
				}
				
				if(!findFreightRoute){//如果找不到 就用精准城运再查一次
					transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT);
					try{
						LOGGER.info("job-精准城运 调用中转生成走货路径接口,waybillNo:" + transportPath.getWaybillNo());
						calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
								,operatorCode, operatorName);
						//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
						if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
							reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
						}
						findFreightRoute = true;
					}catch(TfrBusinessException e){
						LOGGER.error("job-精准城运 TFR Exception",e);
						findFreightRoute = false;
					}
				}
				
				if(!findFreightRoute){//如果找不到 就用汽运长途再查一次
					transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT);
					try{
						LOGGER.info("job-汽运长途 调用中转生成走货路径接口,waybillNo:" + transportPath.getWaybillNo());
						calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
								,operatorCode, operatorName);
						//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
						if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
							reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
						}
						findFreightRoute = true;
					}catch(TfrBusinessException e){
						LOGGER.error("job-汽运长途 TFR Exception",e);
						findFreightRoute = false;
					}
				}
				
				if(!findFreightRoute){//如果找不到 就用精准卡航再查一次
					transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
					try{
						LOGGER.info("job-精准卡航 调用中转生成走货路径接口");
						calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
								,operatorCode, operatorName);
						if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType) && inOutStockEntity != null){
							reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
						}
						findFreightRoute = true;
					//这次如果再找不到 就会抛出异常
					}catch(TfrBusinessException e){
						LOGGER.error("job-精准卡航 TFR Exception",e);
						findFreightRoute = false;
					}
				}
				
				if(!findFreightRoute){//如果找不到 就用精准包裹再查一次    zhangwei
					transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_PCP);
					try{
						LOGGER.info("job-精准包裹 调用中转生成走货路径接口");
						calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
								,operatorCode, operatorName);
						if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType) && inOutStockEntity != null){
							reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
						}
						findFreightRoute = true;
					//这次如果再找不到 就会抛出异常
					}catch(TfrBusinessException e){
						LOGGER.error("job-精准包裹  TFR Exception",e);
						findFreightRoute = false;
						//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
						exceptionMsg = "运输性质" + product +
								"没有找到走货路径 ， 设置为失败，只查询一次就跳出, 库存部门"
								+currentOrgName+"最终到达部门"+destOrgName+"开单所属部门"+receiveOrgName
								+"运单号"+waybill.getWaybillNo();
						
					}
				}
			}
			
			
			//对新的新增加的货签调用中转接口 生成走货路径  并且入库  （这里面会自动入库 不用我们外部调用）
			
			if(exceptionMsg==null){
			
				String productCode = waybill.getProductCode();
				/**
				 * 如果不是偏线或者不是空运，或者经济快递时且如果最终目的部门是驻地部门且对应的外场在走货路径内
				 */
				if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode)
						&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)
						&& !waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, waybill.getBillTime())) {
					// 根据编码查询
					SaleDepartmentEntity saleDepartment = saleDepartmentService
							.querySaleDepartmentByCode(waybill
									.getCustomerPickupOrgCode());

					if (saleDepartment == null) {
						//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
						exceptionMsg ="营业部已经不存在了"+waybill
								.getCustomerPickupOrgCode();
					}
					// 是否驻地部门
					if (saleDepartment != null && saleDepartment.checkStation()) {
						
						String transferCenter=	saleDepartment.getTransferCenter();//外场编码
						if(StringUtils.equals(transferCenter, currentOrgCode)){
							/**
							 * 调用中转库区
							 */
							stockService.adjustStockToStation(waybill.getWaybillNo(), waybill
									.getCustomerPickupOrgCode(), modefiyTransportPathSerialNos, operatorCode, operatorName);
						}
						
					}
				}
				//走货路径生成成功oh yeh!
				if(findFreightRoute) {
					LOGGER.info("job-生成走货路径成功...");
					labeledGoodTodoEntity.setStatus(FossConstants.ACTIVE);//只有走货路径全部成功的时候才是Y
					labeledGoodTodoEntity.setHandleOrgCode(currentOrgCode);//如果已经生成了走货路径 代办强制到该库存部门去打标签
					String orgName = null;
					try{
						 OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentOrgCode);
				    	 orgName= org.getName();
					}catch(Exception e){
						e.printStackTrace();
					}
					labeledGoodTodoEntity.setHandleOrgName(orgName);
					labeledGoodTodoEntity.setRemindTime(new Date());
				}
				

				labeledGoodTodoEntity.setExceptionMsg(FossConstants.NO);
			}else{
				labeledGoodTodoEntity.setExceptionMsg(exceptionMsg);
			}
			// 设置为不再提醒
			labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
			labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
			LOGGER.info("job-代办事项生成成功...");
	    }
		LOGGER.info("job处理代办结束,waybillNO:" + waybill.getWaybillNo());
	}
	

	/**
	 * 
	 * <p>根据走货路径生成待办操作节点</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-14 上午11:25:57
	 * @param waybillRfc
	 * @param labeledGoodEntity
	 * @param orgCode
	 * @return
	 * @see
	 */
	/**private String addActuatingNode(WaybillRfcEntity waybillRfc, LabeledGoodEntity labeledGoodEntity, String orgCode){
		List<PathDetailEntity> pathDetailEntityList = null;
		try {
			pathDetailEntityList = calculateTransportPathService.queryTotalPath(waybillRfc.getWaybillNo(), labeledGoodEntity.getSerialNo());
		} catch (TfrBusinessException e) {
			//新增件数没有走货路径
			return orgCode;
		}
		StringBuffer actuatingNode = new StringBuffer();
		for(PathDetailEntity pathDetailEntity : pathDetailEntityList){
			//找到当前部门为出发部门
			if(orgCode.equals(pathDetailEntity.getOrigOrgCode())){
    			actuatingNode.append(pathDetailEntity.getOrigOrgCode());
			}
    		//找到新的起点后，保存之后的全部走货路径
			if(actuatingNode.length()>0){
				actuatingNode.append(",").append(pathDetailEntity.getObjectiveOrgCode());
			}
		}
		// 走货路径上未找到当前部门直接加入执行节点
		if(actuatingNode.length()==0){
			actuatingNode.append(orgCode);
		}
		return actuatingNode.toString();
	}*/
	
	private void reInstockAirWaybill(InOutStockEntity inOutStockEntity,
			List<String> modefiyTransportPathSerialNos) {
		if(CollectionUtils.isEmpty(modefiyTransportPathSerialNos)){
			return;
		}
		for(int i=0; i<modefiyTransportPathSerialNos.size(); i++){
			inOutStockEntity.setSerialNO(modefiyTransportPathSerialNos.get(i));
			// 入库
			stockService.inStockPC(inOutStockEntity);
		}
	}
	


	/**
	 * 为需要重新入库的待办而准备的
	 * @author 105888
	 * @date 2014-4-12 17:38:37
	 * @param labeledGoodTodoEntity
	 */
	@Override
	public void handleSingleRestockTodo(LabeledGoodTodoEntity labeledGoodTodoEntity) {
		LOGGER.warn("重新调整对应待办流水号入库。id:  "+ labeledGoodTodoEntity.getId()+ ",  rfcId: "+ labeledGoodTodoEntity.getWaybillRfcId()+
				" createTime: "+ new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(labeledGoodTodoEntity.getCreateTime()));
		String rfcId = labeledGoodTodoEntity.getWaybillRfcId();
		String operatorCode = null;
		String operatorName = null;
		//判定流水号是否存在有效信息
		LabeledGoodEntity labeledGoodEntity = labeledGoodService.queryByPrimaryKey(labeledGoodTodoEntity.getLabeledGoodId());
		if(labeledGoodEntity == null){
			labeledGoodTodoEntity.setExceptionMsg("labeledGood Entity is not exists");
			labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
			labeledGoodTodoEntity.setStatus(FossConstants.YES);//设置状态为已经 处理
			labeledGoodTodoEntity.setPrinted(FossConstants.YES);
			labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
			return;
		}
		//查询签收结果
		String signResult = 
				signDetailService.querySerialNoIsSign(labeledGoodEntity.getWaybillNo(), labeledGoodTodoEntity.getSerialNo());
		
		//已经签收 不用判断判断库存  汽运情况
		if(FossConstants.YES.equals(signResult)){
			labeledGoodTodoEntity.setExceptionMsg(FossConstants.NO);
    		labeledGoodTodoEntity.setStatus(FossConstants.ACTIVE);
    		labeledGoodTodoEntity.setPrinted(FossConstants.YES);
    		labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
    		labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
    		return;
		}
		//更改单ID
		WaybillRfcEntity waybillRfc = waybillRfcService.selectByPrimaryKey(rfcId);
		if (waybillRfc == null) {
			labeledGoodTodoEntity.setExceptionMsg("更改单信息已经不存在了");
			labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
			labeledGoodTodoEntity.setStatus(FossConstants.YES);//设置状态为已经 处理
			labeledGoodTodoEntity.setPrinted(FossConstants.YES);
			labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
			return;
		} else {
			operatorCode = waybillRfc.getDrafterCode();
			operatorName = waybillRfc.getDrafter();
		}
		String newWayillId = waybillRfc.getNewVersionWaybillId();
		WaybillEntity waybill = this.waybillManagerService.queryWaybillById(newWayillId);
		if (waybill == null) {
			labeledGoodTodoEntity.setExceptionMsg("查询不到可用的运单信息");
			labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
			labeledGoodTodoEntity.setStatus(FossConstants.YES);//设置状态为已经 处理
			labeledGoodTodoEntity.setPrinted(FossConstants.YES);
			labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
			return;
		}

		//再次需要判定已经作废中止,作废中止不需要进行数据的生成
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybill.getWaybillNo());
		if(actualFreightEntity == null || WaybillConstants.OBSOLETE.equals(actualFreightEntity.getStatus())
				|| WaybillConstants.ABORTED.equals(actualFreightEntity.getStatus())){
			LOGGER.info("实际承运信息实体数据已经不存在了或者已经作废中止");
			labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
			labeledGoodTodoEntity.setStatus(FossConstants.YES);//设置状态为已经 处理
			labeledGoodTodoEntity.setPrinted(FossConstants.YES);
			labeledGoodTodoEntity.setExceptionMsg("actualFreight entity is null or obsolete or aborted");
			labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
			return;
		}
		
		StockEntity stockEntity = stockService.queryUniqueStock(waybill.getWaybillNo(), labeledGoodTodoEntity.getSerialNo());
		if (stockEntity != null) {
			List<String> modefiyTransportPathSerialNos = new ArrayList<String>();
			modefiyTransportPathSerialNos.add(labeledGoodTodoEntity.getSerialNo());
			String stockOrgCode = stockEntity.getOrgCode();
			// 空运入库
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())) {
				SaleDepartmentEntity salesEntity =saleDepartmentService.querySaleDepartmentByCode(stockOrgCode);
				//因为集中开单组是没有库存的，所以要么在外场、要么在营业部
				if(salesEntity != null){
//					if(FossConstants.YES.equals(salesEntity.checkStation())){
//						OutfieldEntity outfieldEntity = outfieldService.queryOutfieldByCode(stockEntity.getOrgCode());
//						// 如果不为空，才会找到对应的空运总调
//						if (outfieldEntity != null) {
//							stockOrgCode = StringUtils.isEmpty(outfieldEntity.getAirDispatchCode()) ? stockEntity.getOrgCode() : outfieldEntity.getAirDispatchCode();
//						}
//					}else{
						stockOrgCode = stockEntity.getOrgCode();
//					}						
				}
			} 
			
			//进行调整入库
			InOutStockEntity inOutStockEntity = new InOutStockEntity();
			// 设置运载单号
			inOutStockEntity.setWaybillNO(waybill.getWaybillNo());
			// 设置当前部门
			inOutStockEntity.setOrgCode(stockOrgCode);
			// 设置操作部门
			inOutStockEntity.setOperatorCode(operatorCode);
			// 设置操作部门名称
			inOutStockEntity.setOperatorName(operatorName);
			// 设置更改单类型
			inOutStockEntity.setInOutStockType(StockConstants.WAYBILL_RFC_IN_STOCK_TYPE);
			reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);

			// 如果不是偏线或者不是空运，或者经济快递时且如果最终目的部门是驻地部门且对应的外场在走货路径内\
			if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getProductCode())
					&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())
					&& !waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(), waybill.getBillTime())) {
				// 根据编码查询
				SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(waybill.getCustomerPickupOrgCode());

				if (saleDepartment == null) {
					// 这里是job调用的代码 日志记录在数据库中 不做国际化 数据库内的日志信息更加直观
					labeledGoodTodoEntity.setExceptionMsg("营业部已经不存在了"+ waybill.getCustomerPickupOrgCode());
				}
				// 是否驻地部门
				if (saleDepartment != null && saleDepartment.checkStation()) {
					String transferCenter = saleDepartment.getTransferCenter();// 外场编码
					if (StringUtils.equals(transferCenter,stockEntity.getOrgCode())) {
						// 调用中转库区
						stockService.adjustStockToStation(waybill.getWaybillNo(),waybill.getCustomerPickupOrgCode(),
								modefiyTransportPathSerialNos, operatorCode,operatorName);
					}

				}
			}
			// 设置为不再提醒
			labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);
			labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
		}
	}


	/**
	 * 查询所有需要重新入库的待办
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-4-11 09:17:38
	 */
	@Override
	public void handleRestockTodo() {
		int start = 0;
		int limited = NumberConstants.NUMBER_5000;
		List<LabeledGoodTodoEntity> list = labeledGoodTodoDao.queryNeedRestockTodoInfo(FossConstants.YES, start, limited);
		LOGGER.warn("启动第一次处理历史代办 这次的createTime为Null");
		while (list != null && list.size() > 0) {
			// 释放处理待办
			boolean relax = false;
			LabeledGoodTodoEntity labeledGoodTodoEntity = null;
			for (Iterator<LabeledGoodTodoEntity> iterator = list.iterator(); iterator.hasNext();) {
				labeledGoodTodoEntity = iterator.next();
				try {
					// 单个调整库存
					handleSingleRestockTodo(labeledGoodTodoEntity);
				} catch (Exception e) {
					LOGGER.error("see handle自动处理单个代办Expcetion", e);
					labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);//设置是否需要重新入库为否
					labeledGoodTodoEntity.setExceptionMsg(StringUtils.substring(e.getMessage()+ ":"+ ExceptionUtils.getFullStackTrace(e), 0,NUMBER_3000));
					//待办出现异常
					labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
				} catch (Throwable e) {
					LOGGER.error("see handle自动处理单个代办Expcetion", e);
					labeledGoodTodoEntity.setNeedRestock(FossConstants.NO);//设置是否需要重新入库为否
					labeledGoodTodoEntity.setExceptionMsg(StringUtils.substring(e.getMessage()+ ":"+ ExceptionUtils.getFullStackTrace(e), 0,NUMBER_3000));
					//待办出现异常
					labeledGoodTodoDao.updateLabeledGoodTodoEntityById(labeledGoodTodoEntity);
					relax = true;
					break;
				}
			}
			if (relax) {
				break; // 等待下次处理
			}
			list = labeledGoodTodoDao.queryNeedRestockTodoInfo(FossConstants.YES, start, limited);
		}
	}

	@Override
	public void modifyPathDetailAtInstock() {
		int start = 0;
		int limited = NumberConstants.NUMBER_5000;	
		//update受影响的条数，为了初始化进入循环默认为5000，如果小于5000，说明数据库中已不足5000，不再循环
		int resultNum = NumberConstants.NUMBER_5000;	
		while (resultNum==limited){
			String jobId = UUIDUtils.getUUID();
			//查询5000条异常信息为N，状态为N的待办数据
			resultNum=waybillRfcTodoJobService.updateReModifyRouteByJobId(jobId, limited);
			List<ReModifyRouteEntity>  list = reModifyRouteDao.searchReModifyRouteRecordByJobId(start, limited, jobId);	
			if(CollectionUtils.isNotEmpty(list)){
				//循环处理该数据
				for (Iterator<ReModifyRouteEntity> iterator = list.iterator(); iterator.hasNext();) {				
					ReModifyRouteEntity reModifyRouteEntity = (ReModifyRouteEntity) iterator.next();
					//单个处理待办
					waybillRfcTodoJobService.modifySinglePathDetailAtInstock(reModifyRouteEntity);		
				}
			}			
			//list = reModifyRouteDao.searchReModifyRouteRecordByJobId(start, limited, jobId);
		}
	}
	
	/**
	 * 特此说明，目前该方法只为PDA修改目的站走货路径没有被调整过来所用
	 * 设计思路:首先得判断流水号为空，如果不为空，单独处理，流水号为空，说明这个还没有被处理过了，
	 * 目前调整走货路径的数据比较少，我不想和待办一样安排俩job跑，感觉挺浪费的
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 17:24:22	
	 */
	public void modifySinglePathDetailAtInstock(ReModifyRouteEntity reModifyRouteEntity) {
		if(StringUtils.isEmpty(reModifyRouteEntity.getLabeleGoodId())){
			//当actualFeeight不存在的时候也不要再生成代办了  因为这可能是改单号引起的 
			ActualFreightEntity a = actualFreightService.queryByWaybillNo(reModifyRouteEntity.getWaybillNo());
			//运单冗余信息
			if(a != null && !WaybillConstants.OBSOLETE.equals(a.getStatus())){
				try{
					TransportPathEntity transportPath = new TransportPathEntity();
					transportPath.setWaybillNo(reModifyRouteEntity.getWaybillNo());
					//使用新增的方法，主要是分两种情况：当货物在库存中，直接进行走货路径的调整，如果走货路径调整失败，新增一条失败记得记录pkp.t_srv_re_modify_route
					//如果不在库存中，新增一条记录至pkp.t_srv_re_modify_route，由后台job执行
					WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(transportPath.getWaybillNo());
					//判定运单数据是否存在
					if(waybill != null){
						this.updateWaybillPathDetail(transportPath,waybill);
					}
					reModifyRouteDao.deleteReModifyRouteRecord(reModifyRouteEntity.getId());
				}catch(BusinessException e2){					
					LOGGER.error("sendSingleTodo Exception", e2);					
				}
			}
			//运单已经作废了不需要再调整走货路径，直接删除数据-
			reModifyRouteEntity.setFailReason("运单已经作废");
			reModifyRouteDao.updateReModifyRouteRecord(reModifyRouteEntity);
		}else{
			//不用说，这个是已经生成的单个数据
			ActualFreightEntity a = actualFreightService.queryByWaybillNo(reModifyRouteEntity.getWaybillNo());
			LabeledGoodEntity labeledGoodEntity = labeledGoodService.queryByPrimaryKey(reModifyRouteEntity.getLabeleGoodId());
			boolean isContinue = true;
			//若存在数据，则更新该待办为已处理状态
	    	if(labeledGoodEntity == null){
	    		//运单已经作废了不需要再调整走货路径，直接删除数据
	    		reModifyRouteEntity.setFailReason("货签信息已经不存在了");
				reModifyRouteDao.updateReModifyRouteRecord(reModifyRouteEntity);
				return;
	    	}
			//运单冗余信息
			if(a!=null && !WaybillConstants.OBSOLETE.equals(a.getStatus()) && labeledGoodEntity != null){
				//判断是否为签收状态，如果被签收，需要更新所有的待办信息
				if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(reModifyRouteEntity.getProductCode())  
						|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(reModifyRouteEntity.getProductCode())  ){
					WaybillSignResultEntity resultEntity = new WaybillSignResultEntity();
			    	resultEntity.setWaybillNo(reModifyRouteEntity.getWaybillNo());
			    	resultEntity.setActive(FossConstants.ACTIVE);
			    	WaybillSignResultEntity result2 = waybillSignResultService.queryWaybillSignResultByWaybillNo(resultEntity);
			    	//若存在数据，则更新该待办为已处理状态
			    	if(result2!=null){
			    		//运单已经作废了不需要再调整走货路径，直接删除数据
			    		reModifyRouteEntity.setFailReason("运单已经被签收");
						reModifyRouteDao.updateReModifyRouteRecord(reModifyRouteEntity);
						isContinue = false;
			    	}
				}else{
					//查询签收结果
					String signResult = signDetailService.querySerialNoIsSign(reModifyRouteEntity.getWaybillNo(), labeledGoodEntity.getSerialNo());
					//已经签收 不用判断判断库存  汽运情况
					if(signResult != null &&  FossConstants.YES.equals(signResult)){
						//运单已经作废了不需要再调整走货路径，直接删除数据
						reModifyRouteEntity.setFailReason("运单已经被签收");
						reModifyRouteDao.updateReModifyRouteRecord(reModifyRouteEntity);
						isContinue = false;
					}
				}	
				//看是否需要继续
				if(isContinue){
					StockEntity stockEntity = stockService.queryUniqueStock(reModifyRouteEntity.getWaybillNo(), labeledGoodEntity.getSerialNo());
					//判断在库存中的数据，不在库存的数据不做处理
		    	    if(stockEntity != null){
		    	    	//只有走货路径全部成功的时候才是Y
						handlePathDetail(stockEntity, reModifyRouteEntity);
						//如果失败原因为Y，则表示已经生成成功，否则需要记录对应的失败原因
						if(FossConstants.YES.equals(reModifyRouteEntity.getFailReason())){
							reModifyRouteDao.deleteReModifyRouteRecord(reModifyRouteEntity.getId());
						}else{
							reModifyRouteDao.updateReModifyRouteRecord(reModifyRouteEntity);
						}
		    	    }else{
		    	    	//需要重置对应的JobId
		    	    	reModifyRouteEntity.setJobId("N/A");
		    	    	//设置对应的异常信息状态为N
		    	    	reModifyRouteEntity.setFailReason(FossConstants.NO);
		    	    	reModifyRouteDao.updateReModifyRouteRecord(reModifyRouteEntity);
		    	    }
				}
			
			}
		}
	}

	private void handlePathDetail(StockEntity stockEntity, ReModifyRouteEntity reModifyRouteEntity) {
		String exceptionMsg = null;
    	//如果有库存的话，就生成新的走货路径
    	//每个labelgood单独生成自己的走货路径
    	List<String> modefiyTransportPathSerialNos = new ArrayList<String>();
    	modefiyTransportPathSerialNos.add(stockEntity.getSerialNO());
    	WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(reModifyRouteEntity.getWaybillNo());
    	// 最终配载部门
		String destOrgCode = waybill.getCustomerPickupOrgCode();
		BigDecimal totalWeight = waybill.getGoodsWeightTotal();//总重量
		BigDecimal totalVolume = waybill.getGoodsVolumeTotal();//总体积
		Integer goodsQtyTotal = waybill.getGoodsQtyTotal();//运单总货件数量
		String active = waybill.getActive();//运单状态
		String transType = waybill.getProductCode();//运输类型
		
		TransportPathEntity transportPath = new TransportPathEntity();
		transportPath.setWaybillNo(waybill.getWaybillNo());//运单号
		transportPath.setBillingTime(waybill.getBillTime());// 开单时间// 开单时间
		transportPath.setBillingOrgCode(waybill.getReceiveOrgCode());//开单所属部门编号
		
		String stockOrgCode= stockEntity.getOrgCode();		
		stockOrgCode = queryStationDeliverOrgCode(stockOrgCode);
		if( ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)  ){							
			String outFieldCode =outfieldService.queryTransferCenterByAirDispatchCode(stockOrgCode);
			if(outFieldCode!=null && StringUtils.isNotEmpty(outFieldCode)){
				stockOrgCode = outFieldCode;
			}
		}
		
		//从当前库存部门生成走货路径 --new code
		transportPath.setCurrentOrgCode(stockOrgCode);//现所在部门编号		
		transportPath.setDestOrgCode(destOrgCode);// 最终到达部门编号
		transportPath.setTotalWeight(totalWeight);//总重量
		transportPath.setTotalVolume(totalVolume);//总体积
		transportPath.setGoodsQtyTotal(goodsQtyTotal);//运单总货件数量
		transportPath.setAction(active);//现所处状态
		transportPath.setTransportModel(transType);//运输类型
		//如果生成过走货路径直接打印
		boolean findFreightRoute = false;
		InOutStockEntity inOutStockEntity = null;
		boolean isAirDispather = false;
		String currentOrgCode = stockEntity.getOrgCode();
		if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
	          SaleDepartmentEntity salesEntity =saleDepartmentService.querySaleDepartmentByCode(stockOrgCode);
	          //只有驻地出发部门货物才会在外场
	          if(salesEntity != null && StringUtils.equals(salesEntity.getStation(), FossConstants.YES)){
	            OutfieldEntity outfieldEntity = outfieldService.querySimpleOutfieldByOrgCode(salesEntity.getTransferCenter());
	            //如果不为空，才会找到对应的空运总调
	            if(outfieldEntity != null){
	            	//配置了空运总调才走这个方法
	            	if(StringUtils.isNotEmpty(outfieldEntity.getAirDispatchCode())){
		            	inOutStockEntity = new InOutStockEntity();
						// 设置运载单号
						inOutStockEntity.setWaybillNO(transportPath.getWaybillNo());
						// 设置当前部门
						inOutStockEntity.setOrgCode(outfieldEntity.getAirDispatchCode());
						// 设置操作部门
						inOutStockEntity.setOperatorCode(waybill.getCreateUserCode());
						// 设置操作部门名称
						inOutStockEntity.setOperatorName(waybill.getCreateUserDeptName());
						// 设置更改单类型
						inOutStockEntity.setInOutStockType(StockConstants.WAYBILL_RFC_IN_STOCK_TYPE);
						isAirDispather = true;
	            	}
	            }
	        }
	    }
		String operatorCode = waybill.getCreateUserCode();
		String operatorName = waybill.getCreateUserName();
		if(StringUtils.isNotEmpty(operatorCode)){
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(operatorCode);
			operatorName = emp==null ? operatorCode : emp.getEmpName();
		}else{
			operatorCode = "system";
			operatorName = "system";
		}
		String currentOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(currentOrgCode);	//当前部门名称
		//经发现，处理待办异常出现为空的情况，初步判断此处出现问题，故订正，货物如果是汽运、快递这样会出现问题，故订正。
		String destOrgName = waybillManagerService.getCustomerPickUpNameByCode(waybill.getCustomerPickupOrgCode(), waybill.getProductCode(), waybill.getBillTime());
		//增加对是否集中开单组进行校验,如果是开单组，则返回开单组对应的Code
		String receiveOrgName = null;
		if(StringUtils.isNotEmpty(waybill.getPickupCentralized()) && StringUtils.equals(FossConstants.YES, waybill.getPickupCentralized())){
			receiveOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybill.getCreateOrgCode());
		}else{
			receiveOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybill.getReceiveOrgCode());//收货部门名称
		}						
		
		try{
			calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode, operatorCode, operatorName);
			//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
			if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
				reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
			}
			findFreightRoute = true;
		}catch(TfrBusinessException e){
			LOGGER.error("TFR Exception",e);
			findFreightRoute = false;
		}
		
		Map<Object, Object> maps = new HashMap<Object, Object>();
		maps.put("productCode", transType);
		maps.put("active", FossConstants.YES);
		maps.put("levels", NumberConstants.NUMBER_3);
		ProductEntity productItemEntity = productDao.getLevel3ProductInfo(maps);
		//如果当前productCode为空，最好还是给他们一些数据
		String product = null;
		if(productItemEntity == null){
			product = transType;
		}else{
			product = productItemEntity.getName();
		}
		if( ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(transType)  
				|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)  ){
			
			if(!findFreightRoute){
				//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
				exceptionMsg = "运输性质" + product + "没有找到走货路径 ， 设置为失败，只查询一次就跳出, 库存部门"+currentOrgName
						+ "最终到达部门"+destOrgName+"开单所属部门"+receiveOrgName+"运单号"+waybill.getWaybillNo();
			}
		}
		if(!findFreightRoute){//找不到走货路径 使用汽运短途再查询一次
			transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT);
			try{
				calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
					,operatorCode, operatorName);
				//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
				if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
					reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
				}
				findFreightRoute = true;
			}catch(TfrBusinessException e){
				findFreightRoute = false;
			}
		}
		
		if(!findFreightRoute){//如果找不到 就用精准城运再查一次
			transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT);
			try{
				calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
						,operatorCode, operatorName);
				//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
				if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
					reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
				}
				findFreightRoute = true;
			}catch(TfrBusinessException e){
				findFreightRoute = false;
			}
		}
		
		if(!findFreightRoute){//如果找不到 就用汽运长途再查一次
			transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT);
			try{
				calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
						,operatorCode, operatorName);
				//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
				if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
					reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
				}
				findFreightRoute = true;
			}catch(TfrBusinessException e){
				findFreightRoute = false;
			}
		}
		
		if(!findFreightRoute){//如果找不到 就用精准卡航再查一次
			transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
			try{
				calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
						,operatorCode, operatorName);
				//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
				if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
					reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
				}
				findFreightRoute = true;
			//这次如果再找不到 就会抛出异常
			}catch(TfrBusinessException e){
				findFreightRoute = false;
			}
		}	
		
		if(!findFreightRoute){//如果找不到 就用精准包裹再查一次  zhangwei
			transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_PCP);
			try{
				calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
						,operatorCode, operatorName);
				//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
				if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
					reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
				}
				findFreightRoute = true;
			}catch(TfrBusinessException e){
				LOGGER.error("TFR Exception",e);
				findFreightRoute = false;
				//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
				exceptionMsg = "运输性质" + product + "没有找到走货路径 ， 设置为失败，只查询一次就跳出, 库存部门"
				+ currentOrgName+"最终到达部门" + destOrgName + "开单所属部门"+receiveOrgName + "运单号" + waybill.getWaybillNo();				
			}
		}		
		//对新的新增加的货签调用中转接口 生成走货路径  并且入库  （这里面会自动入库 不用我们外部调用）		
		if(exceptionMsg==null){		
			String productCode = waybill.getProductCode();
			if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode)
					&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)) {
				// 根据编码查询
				SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(waybill.getCustomerPickupOrgCode());

				if (saleDepartment == null) {
					//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
					exceptionMsg ="营业部已经不存在了"+waybill.getCustomerPickupOrgCode();
				}
				//新增经济快递判断，不让其报错
				if(waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, waybill.getBillTime())){
					if(saleDepartment == null){
						OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybill.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
						 if (companyDto != null) {
							 // 代理公司编码	wayEntity.setAgentCode(StringUtil.defaultIfNull(companyDto.getAgentCompany()));    } 
							 exceptionMsg = null;
						 }
					}
				}
				// 是否驻地部门
				if (saleDepartment != null && saleDepartment.checkStation()) {					
					String transferCenter=	saleDepartment.getTransferCenter();//外场编码
					if(StringUtils.equals(transferCenter, currentOrgCode)){
						try{
							stockService.adjustStockToStation(waybill.getWaybillNo(), waybill
									.getCustomerPickupOrgCode(), modefiyTransportPathSerialNos, operatorCode, operatorName);
						}catch (Exception e) {
							exceptionMsg = "调整走货路径成功,调整库区失调整外场库存至派送库区失败.详情：运单号"+waybill.getWaybillNo()+ "提货网点"+waybill.getCustomerPickupOrgCode()+ "流水号"+modefiyTransportPathSerialNos.get(0)+ "操作人工号"+operatorCode+"操作人"+operatorName;
							reModifyRouteEntity.setFailReason(exceptionMsg);
							findFreightRoute = false;
						}
					}
					
				}
			}
			//走货路径生成成功oh yeh!
			if(findFreightRoute) {
				reModifyRouteEntity.setFailReason(FossConstants.YES);
			}			
		}else{
			reModifyRouteEntity.setFailReason(exceptionMsg);			
		}
    }
	
	/**
	 * 使用新增的方法，主要是分两种情况：当货物在库存中，直接进行走货路径的调整，如果走货路径调整失败，新增一条失败记得记录pkp.t_srv_re_modify_route
	 * 如果不在库存中，新增一条记录至pkp.t_srv_re_modify_route，由后台job执行
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-26 15:04:26
	 */
	public void updateWaybillPathDetail(TransportPathEntity transportPath, WaybillEntity waybill){
		WaybillLabeledGoodStockDto labeledGoodStockDto = null;
		//对修改单号的需要进行判断,不然实际货物信息为空了
		labeledGoodStockDto = waybillRfcService.queryWaybillLabeledGoodStock(transportPath.getWaybillNo());    
	    List<LabeledGoodStockStatusDto> labeledGoodStockStatusDtos = labeledGoodStockDto.getLabeledGoodStockStatusDtos();
	    
	    //生成当前部门或下一环节(在途)的待办事项	 
	    if(CollectionUtils.isNotEmpty(labeledGoodStockStatusDtos) && waybill != null){	    	
	    	for(LabeledGoodStockStatusDto labeledGoodStockStatusDto:labeledGoodStockStatusDtos){
	    		LabeledGoodEntity labeledGoodEntity = labeledGoodStockStatusDto.getLabeledGoodEntity();
	    		ReModifyRouteEntity reModifyRouteEntity = null;
	    		try{
	    			if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getProductCode())  
	    					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())  ){
	    				WaybillSignResultEntity resultEntity = new WaybillSignResultEntity();
	    		    	resultEntity.setWaybillNo(transportPath.getWaybillNo());
	    		    	resultEntity.setActive(FossConstants.ACTIVE);
	    		    	WaybillSignResultEntity result2 = waybillSignResultService.queryWaybillSignResultByWaybillNo(resultEntity);
	    		    	//若存在数据，则更新该待办为已处理状态
	    		    	if(result2!=null){
	    		    		//运单已经作废了不需要再调整走货路径，直接删除数据
	    		    		continue;
	    		    	}
	    			}else{
	    				//查询签收结果
	    				String signResult = signDetailService.querySerialNoIsSign(transportPath.getWaybillNo(), labeledGoodEntity.getSerialNo() );
	    				
	    				//已经签收 不用判断判断库存  汽运情况
	    				if(FossConstants.YES.equals(signResult)){
	    					//运单已经作废了不需要再调整走货路径，直接删除数据
	    		    		continue;
	    				}
	    			}
	    			String exceptionMsg = null;
	    			reModifyRouteEntity = addReModifyRouteEntity(labeledGoodEntity, waybill);
		    		if(labeledGoodStockStatusDto.isInStock()){
		    	    	//如果有库存的话，就生成新的走货路径
		    	    	//每个labelgood单独生成自己的走货路径
		    	    	List<String> modefiyTransportPathSerialNos = new ArrayList<String>();
		    	    	modefiyTransportPathSerialNos.add(labeledGoodEntity.getSerialNo());
		    	    	
		    	    	// 最终配载部门
						String destOrgCode = waybill.getCustomerPickupOrgCode();
						BigDecimal totalWeight = waybill.getGoodsWeightTotal();//总重量
						BigDecimal totalVolume = waybill.getGoodsVolumeTotal();//总体积
						Integer goodsQtyTotal = waybill.getGoodsQtyTotal();//运单总货件数量
						String active = waybill.getActive();//运单状态
						String transType = waybill.getProductCode();//运输类型
						
						transportPath.setWaybillNo(waybill.getWaybillNo());//运单号
						transportPath.setBillingTime(waybill.getBillTime());// 开单时间// 开单时间
						transportPath.setBillingOrgCode(waybill.getReceiveOrgCode());//开单所属部门编号
						
						
						String stockOrgCode= labeledGoodStockStatusDto.getCurrentStockOrgCode();
						
						stockOrgCode = queryStationDeliverOrgCode(stockOrgCode);
						if( ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)  ){							
							String outFieldCode =outfieldService.queryTransferCenterByAirDispatchCode(stockOrgCode);
							if(outFieldCode!=null && StringUtils.isNotEmpty(outFieldCode)){
								stockOrgCode = outFieldCode;
							}
						}						
						//从当前库存部门生成走货路径 --new code
						transportPath.setCurrentOrgCode(stockOrgCode);//现所在部门编号						
						transportPath.setDestOrgCode(destOrgCode);// 最终到达部门编号
						transportPath.setTotalWeight(totalWeight);//总重量
						transportPath.setTotalVolume(totalVolume);//总体积
						transportPath.setGoodsQtyTotal(goodsQtyTotal);//运单总货件数量
						transportPath.setAction(active);//现所处状态
						transportPath.setTransportModel(transType);//运输类型
						//如果生成过走货路径直接打印
						boolean findFreightRoute = false;
						InOutStockEntity inOutStockEntity = null;
						boolean isAirDispather = false;
						String currentOrgCode = labeledGoodStockStatusDto.getCurrentStockOrgCode();
						if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
					          SaleDepartmentEntity salesEntity =saleDepartmentService.querySaleDepartmentByCode(stockOrgCode);
					          //只有驻地出发部门货物才会在外场
					          if(salesEntity != null && StringUtils.equals(salesEntity.getStation(), FossConstants.YES)){
					            OutfieldEntity outfieldEntity = outfieldService.querySimpleOutfieldByOrgCode(salesEntity.getTransferCenter());
					            //如果不为空，才会找到对应的空运总调
					            if(outfieldEntity != null){
					            	//配置了空运总调才走这个方法
					            	if(StringUtils.isNotEmpty(outfieldEntity.getAirDispatchCode())){
						            	inOutStockEntity = new InOutStockEntity();
										// 设置运载单号
										inOutStockEntity.setWaybillNO(transportPath.getWaybillNo());
										// 设置当前部门
										inOutStockEntity.setOrgCode(outfieldEntity.getAirDispatchCode());
										// 设置操作部门
										inOutStockEntity.setOperatorCode(waybill.getCreateUserCode());
										// 设置操作部门名称
										inOutStockEntity.setOperatorName(waybill.getCreateUserName());
										// 设置更改单类型
										inOutStockEntity.setInOutStockType(StockConstants.WAYBILL_RFC_IN_STOCK_TYPE);
										isAirDispather = true;
					            	}
					            }
					        }
					    }
						String operatorCode = waybill.getCreateUserCode();
						String operatorName = waybill.getCreateUserName();
						String currentOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(currentOrgCode);	//当前部门名称
						//经发现，处理待办异常出现为空的情况，初步判断此处出现问题，故订正，货物如果是汽运、快递这样会出现问题，故订正。
						String destOrgName = waybillManagerService.getCustomerPickUpNameByCode(waybill.getCustomerPickupOrgCode(), waybill.getProductCode(), waybill.getBillTime());
						//增加对是否集中开单组进行校验,如果是开单组，则返回开单组对应的Code
						String receiveOrgName = null;
						if(StringUtils.isNotEmpty(waybill.getPickupCentralized()) && StringUtils.equals(FossConstants.YES, waybill.getPickupCentralized())){
							receiveOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybill.getCreateOrgCode());
						}else{
							receiveOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybill.getReceiveOrgCode());//收货部门名称
						}						
						
						try{
							calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
									, waybill.getCreateUserCode(), waybill.getCreateUserName());
							//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
							if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
								reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
							}
							findFreightRoute = true;
						}catch(TfrBusinessException e){
							LOGGER.error("TFR Exception",e);
							findFreightRoute = false;
						}
						
//						ProductItemEntity productItemEntity = productItemService.getProductItemByCache(transType,new Date());
						Map<Object, Object> maps = new HashMap<Object, Object>();
						maps.put("productCode", transType);
						maps.put("active", FossConstants.YES);
						maps.put("levels", NumberConstants.NUMBER_3);
						ProductEntity productItemEntity = productDao.getLevel3ProductInfo(maps);
						//如果当前productCode为空，最好还是给他们一些数据
						String product = null;
						if(productItemEntity == null){
							product = transType;
						}else{
							product = productItemEntity.getName();
						}
						if( ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(transType)  
								|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)  ){
							
							if(!findFreightRoute){
								//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
								exceptionMsg = "运输性质" + product + "没有找到走货路径 ， 设置为失败，只查询一次就跳出, 库存部门"+currentOrgName
										+"最终到达部门"+destOrgName+"开单所属部门"+receiveOrgName+"运单号"+waybill.getWaybillNo();
							}
							
						}
						
						if(!findFreightRoute){//找不到走货路径 使用汽运短途再查询一次
							
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT);
							try{
								calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode,operatorCode, operatorName);
								//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
								if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
									reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
								}
								findFreightRoute = true;
							}catch(TfrBusinessException e){
								findFreightRoute = false;
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用精准城运再查一次
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT);
							try{
								calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode,operatorCode, operatorName);
								//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
								if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
									reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
								}
								findFreightRoute = true;
							}catch(TfrBusinessException e){
								findFreightRoute = false;
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用汽运长途再查一次
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT);
							try{
								calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode
										,operatorCode, operatorName);
								//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
								if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
									reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
								}
								findFreightRoute = true;
							}catch(TfrBusinessException e){
								findFreightRoute = false;
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用精准卡航再查一次
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
							try{
								calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode,operatorCode, operatorName);
								//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
								if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
									reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
								}
								findFreightRoute = true;
							//这次如果再找不到 就会抛出异常
							}catch(TfrBusinessException e){
								findFreightRoute = false;
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用精准包裹再查一次   zhangwei
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_PCP);
							try{
								calculateTransportPathService.modifyTransportPathForAmendmentBill(transportPath, modefiyTransportPathSerialNos, currentOrgCode,operatorCode, operatorName);
								//如果是空运总调，需要重新入库  BUG-55802  zhangxingwang
								if(isAirDispather && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
									reInstockAirWaybill(inOutStockEntity,modefiyTransportPathSerialNos);
								}
								findFreightRoute = true;
							//这次如果再找不到 就会抛出异常
							}catch(TfrBusinessException e){
								LOGGER.error("TFR Exception",e);
								findFreightRoute = false;
								exceptionMsg = "运输性质" + product +"没有找到走货路径 ， 设置为失败，只查询一次就跳出, 库存部门"+
								currentOrgName+"最终到达部门"+destOrgName+"开单所属部门"+receiveOrgName+"运单号"+waybill.getWaybillNo();
								
							}
						}						
						//对新的新增加的货签调用中转接口 生成走货路径  并且入库  （这里面会自动入库 不用我们外部调用）						
						if(exceptionMsg==null){						
							String productCode = waybill.getProductCode();
							if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode)
									&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)) {
								// 根据编码查询
								SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(waybill.getCustomerPickupOrgCode());
								if (saleDepartment == null) {
									//这里是job调用的代码  日志记录在数据库中  不做国际化 数据库内的日志信息更加直观
									exceptionMsg ="营业部已经不存在了"+waybill.getCustomerPickupOrgCode();
								}
								//新增经济快递判断，不让其报错
								if(waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, waybill.getBillTime())){
									if(saleDepartment == null){
										OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybill.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
										 if (companyDto != null) {
											 exceptionMsg = null;
										 }
									}
								}
								// 是否驻地部门
								if (saleDepartment != null && saleDepartment.checkStation()) {									
									String transferCenter=	saleDepartment.getTransferCenter();//外场编码
									if(StringUtils.equals(transferCenter, currentOrgCode)){
										stockService.adjustStockToStation(waybill.getWaybillNo(), waybill.getCustomerPickupOrgCode(), modefiyTransportPathSerialNos, operatorCode, operatorName);
									}									
								}
							}
							//走货路径生成成功oh yeh!
							if(findFreightRoute) {
								reModifyRouteEntity.setFailReason(FossConstants.YES);
							}							
						}else{
							reModifyRouteEntity.setFailReason(exceptionMsg);							
						}
		    	    }else{ 
		    	    	reModifyRouteEntity = addReModifyRouteEntity(labeledGoodEntity, waybill);
		    	    }
		    	    if(reModifyRouteEntity != null && !FossConstants.YES.equals(reModifyRouteEntity.getFailReason())){
		    	    	List<ReModifyRouteEntity> reModifyList = reModifyRouteDao.queryIsExistRecordByWaybillSerial(reModifyRouteEntity.getWaybillNo(), reModifyRouteEntity.getLabeleGoodId());
		    	    	if(CollectionUtils.isEmpty(reModifyList)){
		    	    		reModifyRouteDao.addReModifyRouteRecord(reModifyRouteEntity);
		    	    	}
		    	    }		    	    
	    		}catch(Exception e){
	    			LOGGER.error("add label good generate new transfer path exception",e);
	    		}	    		
	    	}
	    }
	}
	
	private ReModifyRouteEntity addReModifyRouteEntity(
			LabeledGoodEntity labeledGoodEntity, WaybillEntity waybill) {
		ReModifyRouteEntity entity = new ReModifyRouteEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setWaybillNo(labeledGoodEntity.getWaybillNo());
		entity.setLabeleGoodId(labeledGoodEntity.getId());
		entity.setCreateTime(new Date());
		entity.setModifyTime(entity.getCreateTime());
		entity.setProductCode(waybill.getProductCode());
		entity.setFailReason(FossConstants.NO);
		return entity;
	}
	/**
	 * 根据JobId更新一定数据的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-11 15:18:31
	 */
	@Transactional
	public int updateReModifyRouteByJobId(String jobId, int updateNum){
		return reModifyRouteDao.updateReModifyRouteByJobId(jobId,updateNum);
	}


	/**
	 * @param waybillRfcTodoJobService the waybillRfcTodoJobService to set
	 */
	public void setWaybillRfcTodoJobService(
			IWaybillRfcTodoJobService waybillRfcTodoJobService) {
		this.waybillRfcTodoJobService = waybillRfcTodoJobService;
	}

	/**
	 * @return the orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @return the calculateTransportPathService
	 */
	public ICalculateTransportPathService getCalculateTransportPathService() {
		return calculateTransportPathService;
	}

	/**
	 * @param calculateTransportPathService the calculateTransportPathService to set
	 */
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	/**
	 * @return the labeledGoodTodoDao
	 */
	public ILabeledGoodTodoDao getLabeledGoodTodoDao() {
		return labeledGoodTodoDao;
	}

	/**
	 * @param labeledGoodTodoDao the labeledGoodTodoDao to set
	 */
	public void setLabeledGoodTodoDao(ILabeledGoodTodoDao labeledGoodTodoDao) {
		this.labeledGoodTodoDao = labeledGoodTodoDao;
	}

	/**
	 * @return the waybillRfcService
	 */
	public IWaybillRfcService getWaybillRfcService() {
		return waybillRfcService;
	}

	/**
	 * @param waybillRfcService the waybillRfcService to set
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * @return the waybillManagerService
	 */
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	/**
	 * @param waybillManagerService the waybillManagerService to set
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @return the waybillRfcDao
	 */
	public IWaybillRfcDao getWaybillRfcDao() {
		return waybillRfcDao;
	}

	/**
	 * @param waybillRfcDao the waybillRfcDao to set
	 */
	public void setWaybillRfcDao(IWaybillRfcDao waybillRfcDao) {
		this.waybillRfcDao = waybillRfcDao;
	}

	/**
	 * @return the pendingTodoDao
	 */
	public IPendingTodoDao getPendingTodoDao() {
		return pendingTodoDao;
	}

	/**
	 * @param pendingTodoDao the pendingTodoDao to set
	 */
	public void setPendingTodoDao(IPendingTodoDao pendingTodoDao) {
		this.pendingTodoDao = pendingTodoDao;
	}


	/**
	 * @return the labeledGoodChangeDao
	 */
	public ILabeledGoodChangeDao getLabeledGoodChangeDao() {
		return labeledGoodChangeDao;
	}


	/**
	 * @param labeledGoodChangeDao the labeledGoodChangeDao to set
	 */
	public void setLabeledGoodChangeDao(ILabeledGoodChangeDao labeledGoodChangeDao) {
		this.labeledGoodChangeDao = labeledGoodChangeDao;
	}


	/**
	 * @return the saleDepartmentService
	 */
	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}
	/**
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	/**
	 * @return the stockService
	 */
	public IStockService getStockService() {
		return stockService;
	}


	/**
	 * @return the waybillRfcVarificationDao
	 */
	public IWaybillRfcVarificationDao getWaybillRfcVarificationDao() {
		return waybillRfcVarificationDao;
	}

	/**
	 * @param waybillRfcVarificationDao the waybillRfcVarificationDao to set
	 */
	public void setWaybillRfcVarificationDao(
			IWaybillRfcVarificationDao waybillRfcVarificationDao) {
		this.waybillRfcVarificationDao = waybillRfcVarificationDao;
	}
	
	/**	快递代理快递代理服务接口	 */	
	public void setLdpAgencyDeptService(
			ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}
	
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}


	/**
	 * @param outfieldService the outfieldService to set
	 */
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}


	/**
	 * @param actualFreightService the actualFreightService to set
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}


	/**
	 * @param stockService the stockService to set
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * @param waybillSignResultService the waybillSignResultService to set
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}


	/**
	 * @return the signDetailService
	 */
	public ISignDetailService getSignDetailService() {
		return signDetailService;
	}

	

	/**
	 * @param signDetailService the signDetailService to set
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}


	/**
	 * @return the waybillSignResultService
	 */
	public IWaybillSignResultService getWaybillSignResultService() {
		return waybillSignResultService;
	}

	public void setReModifyRouteDao(IReModifyRouteDao reModifyRouteDao) {
		this.reModifyRouteDao = reModifyRouteDao;
	}
	
}
