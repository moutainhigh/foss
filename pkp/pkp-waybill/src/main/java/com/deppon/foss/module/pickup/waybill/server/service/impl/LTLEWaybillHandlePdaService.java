package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEWaybillMessageDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPdaAppInfoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirePendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsPgDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillHandlePdaService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOmsOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.server.utils.StringHandlerUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaAppInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequireEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsPgEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaAppInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WoodenRequirePdaDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class LTLEWaybillHandlePdaService implements ILTLEWaybillHandlePdaService{
	
	private ILabelPushService labelPushService;
	
	private IWaybillPendingService waybillPendingService;
	
	private IWaybillProcessLogDao waybillProcessLogDao;
	
	private IPdaAppInfoDao pdaAppInfoDao;
	
	private ILTLEWaybillProcessService lTLEWaybillProcessService;
	
	private IOmsOrderService omsOrderService;
	
	private IWoodenRequirePendingDao woodenRequirePendingDao;
	
	private IWaybillDao waybillDao;
	
	private IWoodenRequirementsPgDao woodenRequirementsPgDao;
	
	private IEWaybillMessageDao eWaybillMessageDao;
	
	private ILabeledGoodDao labeledGoodDao;
	
	private static final String OPERATION_TYPE_PDA = "PDA";
	
	private static final String OPERATION_TYPE_APP = "APP";
	
	private static final int oneHundred = 100;
	
	
	public void setLabelPushService(ILabelPushService labelPushService) {
		this.labelPushService = labelPushService;
	}


	public void setWaybillPendingService(
			IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}


	public void setWaybillProcessLogDao(IWaybillProcessLogDao waybillProcessLogDao) {
		this.waybillProcessLogDao = waybillProcessLogDao;
	}


	public void setPdaAppInfoDao(IPdaAppInfoDao pdaAppInfoDao) {
		this.pdaAppInfoDao = pdaAppInfoDao;
	}


	public void setlTLEWaybillProcessService(
			ILTLEWaybillProcessService lTLEWaybillProcessService) {
		this.lTLEWaybillProcessService = lTLEWaybillProcessService;
	}


	public void setOmsOrderService(IOmsOrderService omsOrderService) {
		this.omsOrderService = omsOrderService;
	}


	public void setWoodenRequirePendingDao(
			IWoodenRequirePendingDao woodenRequirePendingDao) {
		this.woodenRequirePendingDao = woodenRequirePendingDao;
	}


	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setWoodenRequirementsPgDao(
			IWoodenRequirementsPgDao woodenRequirementsPgDao) {
		this.woodenRequirementsPgDao = woodenRequirementsPgDao;
	}
	
	public void seteWaybillMessageDao(IEWaybillMessageDao eWaybillMessageDao) {
		this.eWaybillMessageDao = eWaybillMessageDao;
	}

	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}

	/**
	 * @description APP/DOP扫描后返回标签信息
	 * @param waybill 运单号
	 * @param originateOrgCode 始发部门
	 * @return
	 */
	@Override
	public LabelInfoDto appScan(String waybillNo, String originateOrgCode)throws Exception{
		String isSuccess = WaybillConstants.LTLEWAYBILL_PROCESS_LOG_SUCCESS;
		String failReason = "";
		String exceptionCode = "";
		String orderNo = "";
		try{
			PdaAppInfoEntity pdaAppInfo = pdaAppInfoDao.queryPdaAppInfoByWaybillNO(waybillNo);
			if(pdaAppInfo == null){
				throw new WaybillValidateException(WaybillValidateException.LTLEWAYBILL_OMS_ORDER_IS_NULL);
			}
			/**
			 * App扫描只允许在存在待补录及运单记录时候才能进行记录，TODO（暂时设置成这样子）
			 */
			WaybillEntity entity = waybillDao.queryWaybillByNo(waybillNo);
			WaybillPendingEntity pendingEntity = waybillPendingService.queryPendingByNo(waybillNo);
			if(null == pendingEntity ){
				throw new WaybillValidateException(WaybillValidateException.UNEXIST_WAYBILL_PENDING);
			}
			if(null == entity){
				throw new WaybillValidateException(WaybillValidateException.UNEXIST_WAYBILL);
			}
			orderNo = entity.getOrderNo();
			/**
			 * 查询PdaAppInfo信息,将PdaAppInfo修改成以扫描,OMS订单收货部门（开单部门）与app扫描的部门一致时候保存App的始发部门
			 */
			pdaAppInfo.setScan(WaybillConstants.LTLEWAYBILL_NOT_SCAN);
			pdaAppInfo.setActive(WaybillConstants.YES);
			pdaAppInfo.setModifyTime(new Date());
			if(StringUtils.isNotEmpty(originateOrgCode)){
				pdaAppInfo.setOriginateOrgCode(originateOrgCode);
			}
			pdaAppInfoDao.updateSelectiveByWaybillNo(pdaAppInfo);
			/**
			 * 查询标签信息
			 */
			LabelInfoDto result = labelPushService.queryLabelInfos(entity);
			return result;
		}catch(BusinessException be){
			failReason = ExceptionUtils.getFullStackTrace(be);
			exceptionCode = be.getErrorCode();
			isSuccess = WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE;
			String exceptionMessage = eWaybillMessageDao.getWVMessageByFailCode(exceptionCode);
			throw new BusinessException(exceptionMessage);
		}catch(Exception e){
			failReason = ExceptionUtils.getFullStackTrace(e);
			exceptionCode = WaybillValidateException.EWAYBILL_UNKOWN_EXCEPTION;
			isSuccess = WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE;
			throw e;
		}finally{
			String content = "WaybillNo = " + waybillNo +  " , OrderNo = " + orderNo + " , ReceiveOrgCode = " + originateOrgCode;
			waybillProcessLogDao.saveLog(content, orderNo, waybillNo, new Date() , WaybillConstants.LTLEWAYBILL_OPERATION_TYPE_APPSCAN_LOG , isSuccess, failReason);
		}
	}

	
	
	
	/**
	 * @description 处理重量体积，添加激活运单线程
	 * @param pdaAppInfo
	 */
	@Transactional
	public void handleAppInfo(PdaAppInfoDto pdaAppInfo,String operationType,WoodenRequirePdaDto woodenPdaDto) throws BusinessException{
		if(pdaAppInfo==null||StringUtils.isEmpty(pdaAppInfo.getWaybillNo())){
			throw new WaybillValidateException("pdaAppInfo对象为空或缺少运单号");
		}
		String waybillNo = pdaAppInfo.getWaybillNo();
		/**
		 * 从流程设计上说OMSORDER一定会存在，但还是增加个判断保险一点
		 */
		OmsOrderEntity omsOrderEntity = omsOrderService.queryOmsOrderByWaybillNo(waybillNo);
		if(omsOrderEntity == null){
			throw new WaybillValidateException("未查询到OMS订单信息");
		}
		//查询
		PdaAppInfoEntity paiEntity = pdaAppInfoDao.queryPdaAppInfoByWaybillNO(waybillNo);
		/**
		 * PDA和APP进入都处理重量体积信息
		 */
		paiEntity.setWaybillNo(pdaAppInfo.getWaybillNo());
		paiEntity.setActive(WaybillConstants.YES);
		
		/**
		 * 重量体积都不允许为空，都不允许小于0，与原有FOSS客户端零担开单限制一致,
		 * 不符合条件重量体积都将不被录入
		 */
		if(pdaAppInfo.getGoodsVolumeTotal()!=null 
	  	 &&pdaAppInfo.getGoodsWeightTotal()!=null 
	  	 &&pdaAppInfo.getGoodsVolumeTotal().compareTo(new BigDecimal(0)) == 1
	  	 &&pdaAppInfo.getGoodsWeightTotal().compareTo(new BigDecimal(0)) == 1)
		{
			paiEntity.setGoodsVolumeTotal(pdaAppInfo.getGoodsVolumeTotal());
			paiEntity.setGoodsWeightTotal(pdaAppInfo.getGoodsWeightTotal());
		}
		/**
		 * 与上面判断一致分两步原因如下：
		 * GUI端可以导入重量体积，现零担电子运单业务支持,客户端在有待补录数据时候导入重量体积，
		 * 当非集中接货完成任务时候没有重量体积这个时候也可以触发激活电子运单
		 */
		boolean isActive = false;
		if(paiEntity.getGoodsVolumeTotal()!=null 
	  	 &&paiEntity.getGoodsWeightTotal()!=null 
	  	 &&paiEntity.getGoodsVolumeTotal().compareTo(new BigDecimal(0)) == 1
	  	 &&paiEntity.getGoodsWeightTotal().compareTo(new BigDecimal(0)) == 1)
		{
			isActive = true;
		}
		/**
		 * APP进入设置内容，
		 * PDA不包含此内容
		 * 获取总件数PDA进入的时候获取查询APP提交出来的数量
		 * APP进来时候直接获取总数量
		 */
		Date date = new Date();
		int goodsTotal = 0;
		paiEntity.setScan(WaybillConstants.LTLEWAYBILL_SCAN);
		if(OPERATION_TYPE_APP.equals(operationType))
		{
			goodsTotal = pdaAppInfo.getGoodsQtyTotal();
			paiEntity.setGoodsQtyTotal(pdaAppInfo.getGoodsQtyTotal());
			paiEntity.setScan(WaybillConstants.LTLEWAYBILL_SCAN);
			paiEntity.setDriverCode(pdaAppInfo.getDriverCode());
		    paiEntity.setTruckCode(pdaAppInfo.getTruckCode());
		    paiEntity.setTaskCode(pdaAppInfo.getTaskCode());
		    paiEntity.setSubmissionTime(pdaAppInfo.getSubmissionTime());
		  	paiEntity.setOverTaskTime(date);
		  	paiEntity.setPackInfo(pdaAppInfo.getPackInfo());
		}else{
			goodsTotal = paiEntity.getGoodsQtyTotal();
		}
		paiEntity.setModifyTime(date);
		paiEntity.setIsBigUp(pdaAppInfo.getIsBigUp());
		/**
		 * 更新
 		 * 备注信息：app存在重复提交任务问题,pdaAppInfo信息更新时候,只要事物开启
		 * 将可以对本条信息进行锁定,这样可以保证handlePackageInfo中APP提交时候，只需要查询
		 * 流水号信息便可知道流水号是否已经进行了创建
		 */
		pdaAppInfoDao.updateSelectiveByWaybillNo(paiEntity);
		/**
		 * 
		 * 流水号处理，因零担电子运单模式上的修改,
		 * 在待补录生成的时候未生成流水号进行入库，又因零担电子运单支持数量变化.
		 * 中转在卸车入库前，需要流水号存在，但如果多出来的件数就无法入库，现将流水号入库挪移至APP完成接货插入流水号及
		 * 中转场在补录重量体积时存在木托木架时候需要重新修改流水号(此时取的数量已APP传入的数量为主)
		 */
		
		handlePackagInfo(pdaAppInfo, operationType, woodenPdaDto, waybillNo,
				date, goodsTotal,omsOrderEntity.getWaybillStatus());
		
		
		
		/**
		 * 重量体积都不许不为null且大于0,且OMS中的订单中的运单状态非已退回,开单，和撤销时候，进行插入激活线程动作
		 * 已退回时候有可能是在生成代补录时候进行退回的也有可能是在激活运单时候进行退回的所有PKP.T_SRV_WAYBILL_PENDING可能不存在数据
		 * 但退回时候一定会同时更新OMS订单数据中的运单状态，所以我们可以不关心PKP.T_SRV_WAYBILL_PENDING是否存在数据
		 * WaybillConstants.LTLEWAYBILL_SCAN.equals(paiEntity.getScan()) 必须App扫描
		 * 提示,集中接货时候可能会出现以下情况,PDA数据先APP数据到达FOSS数据库，此时不会添加激活线程,
		 * 只有当APP数据过来时候,才被视为扫描，此时APP过来时候还带了重量体积时候，将会已APP的重量体积为准（但业务设计集中接货区域时候APP按道理是不会传重量体积至FOSS）
		 */
		if(WaybillConstants.LTLEWAYBILL_SCAN.equals(paiEntity.getScan())
		&&!WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.contains(omsOrderEntity.getWaybillStatus()) 
		&&!WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.contains(omsOrderEntity.getWaybillStatus())
		&&!WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL.contains(omsOrderEntity.getWaybillStatus())
		&&isActive){
			lTLEWaybillProcessService.addWaybillProcessEntity( pdaAppInfo.getWaybillNo() , omsOrderEntity.getOrderNo() , WaybillConstants.LTLEWAYBILL_PDA_ACTIVE_PROCESS);
		}
	}

	/**
	 * 添加流水号，中转PDA扫描时候如果已开单，那么将不会更改流水号的打木架的相关信息
	 * @param pdaAppInfo
	 * @param operationType
	 * @param woodenPdaDto
	 * @param waybillNo
	 * @param date
	 * @param goodsTotal
	 * @param waybillStatus
	 */
	private void handlePackagInfo(PdaAppInfoDto pdaAppInfo,
			String operationType, WoodenRequirePdaDto woodenPdaDto,
			String waybillNo, Date date, int goodsTotal,String waybillStatus) {
		/**
		 * 判断是否已经开单
		 */
		if(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.contains(waybillStatus)){
			return;
		}
		if(woodenPdaDto!=null&&StringUtils.equals(OPERATION_TYPE_PDA,operationType)){
			/**
			 * 更新
			 * 提示：当PDA数据先APP到FOSS时候，更新打木架打木托时候将会报错。
			 */
			List<LabeledGoodEntity> list = labeledGoodDao.queryAllSerialByWaybillNo(pdaAppInfo.getWaybillNo());
			int woodenStockNum = woodenPdaDto.getWoodenStockNum();
			if( list != null && goodsTotal > 0 && woodenStockNum > 0 ){
				for(int i = 0; i < woodenStockNum; i++){
					LabeledGoodEntity labeled = list.get(i);
					labeled.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
					labeledGoodDao.updateByPrimaryKeySelective(labeled);
				}
			}
		}else if(StringUtils.equals(OPERATION_TYPE_APP,operationType) && goodsTotal > 0){
			WaybillEntity entity = waybillDao.queryWaybillByNo(waybillNo);
			/**
			 * 优化 '2016-10-10' 
			 * 当APP多人同时提交任务且造成并发时候，因外层调用了updateSelectiveByWaybillNo所以程序会阻塞，直到当前程序完成提交
			 * 程序才会往下执行,不支持多次添加流水号,第一次APP扫描数据为准
			 */
			List<LabeledGoodEntity> list = labeledGoodDao.queryAllSerialByWaybillNo(pdaAppInfo.getWaybillNo());
			if(CollectionUtils.isEmpty(list)){
				for(int i = 0; i < goodsTotal ; i++){
					LabeledGoodEntity labeledGoodEntity = new LabeledGoodEntity();
					labeledGoodEntity.setBillTime(entity.getBillTime());
					labeledGoodEntity.setWaybillNo(waybillNo);
					labeledGoodEntity.setActive(WaybillConstants.YES);
					labeledGoodEntity.setCreateTime(date);
					labeledGoodEntity.setModifyTime(date);
					String serialNo = StringHandlerUtil.lpad(String.valueOf(i + 1), NumberConstants.NUMBER_4, "0");
					labeledGoodEntity.setSerialNo(serialNo);
					//方法内设置了ID为UUID
					labeledGoodDao.insertSelective(labeledGoodEntity);
				}
			}
		}
	}
	
	
	
	
	/**
	 * @description 处理外场Pda扫描信息
	 * @param woodenPdaDto
	 * @throws BusinessException
	 */
	@Transactional
	public void handlePdaInfo(WoodenRequirePdaDto woodenPdaDto)throws BusinessException{
		String waybillNo = woodenPdaDto.getWaybillNo();
		if(StringUtils.isEmpty(waybillNo)){
			throw new WaybillValidateException();
		}
		WaybillPendingEntity pendingEntity = waybillDao.queryPendingByNo(waybillNo);
		//根据运单号查询d待打木架信息
		WoodenRequirePendingEntity  woodenRequirePend =woodenRequirePendingDao.queryWoodenRequireByNo(waybillNo);
		//打木架信息
		WoodenRequirementsPgEntity  woodenEntity = getWoodenRequirementsEntity(woodenPdaDto,pendingEntity,woodenRequirePend);
		if(woodenEntity!=null){
			/**
			 * 存入临时的打木架信息中
			 */
			woodenEntity.setWaybillNo(waybillNo);
			woodenEntity.setActive(WaybillConstants.YES);
			woodenEntity.setId(UUIDUtils.getUUID());
			woodenRequirementsPgDao.insertSelective(woodenEntity);
		}
		
		/**
		 * 处理AppInfo
		 */
		PdaAppInfoDto pdaAppInfoDto = new PdaAppInfoDto(); 
		pdaAppInfoDto.setWaybillNo(woodenPdaDto.getWaybillNo());
		pdaAppInfoDto.setIsBigUp(woodenPdaDto.getIsBigUp());
		pdaAppInfoDto.setGoodsVolumeTotal(woodenPdaDto.getGoodsVolumeTotal());
		pdaAppInfoDto.setGoodsWeightTotal(woodenPdaDto.getGoodsWeightTotal());
		handleAppInfo(pdaAppInfoDto,OPERATION_TYPE_PDA,woodenPdaDto);
		
	}
	
	/**
     * 获取打木架信息
     * @param woodenPdaDto (复制)
     * @return
     * @author 076234 PanGuoYang
     */
    private WoodenRequirementsPgEntity getWoodenRequirementsEntity(WoodenRequirePdaDto woodenPdaDto,WaybillPendingEntity pendingEntity,WoodenRequirePendingEntity  woodenPend){
    	
    	//打木架信息(存于临时表)
		WoodenRequirementsPgEntity woodenEntity=new WoodenRequirementsPgEntity();
    	//打木箱总体积
		BigDecimal allBoxVolume=BigDecimal.ZERO;
		//打木箱总件数
		Integer allBoxNum=0;
		StringBuilder boxGoodsSize=new StringBuilder("");
		//打木架总体积
		BigDecimal allStandVolume=BigDecimal.ZERO;
		//打木架总件数
		Integer allStandNum=0;
		//打木架备注
		StringBuilder standGoodsSize = new StringBuilder("");
		/**
		 * 新增非包装货物尺寸
		 */
		StringBuilder noPackGoodsSize = new StringBuilder("");
		List<WoodenRequireEntity> woodeList= woodenPdaDto.getWoodenRequireEntityLis();
		if(CollectionUtils.isNotEmpty(woodeList)){
			
			for(WoodenRequireEntity woode:woodeList){
				
				String packType = woode.getPackType();
				//件数
				BigDecimal  num = new BigDecimal(woode.getGoodsNum());
				//长
				BigDecimal length = woode.getLength().divide(new BigDecimal(oneHundred));
				//宽
				BigDecimal width = woode.getWidth().divide(new BigDecimal(oneHundred));
				//高
				BigDecimal height = woode.getHeight().divide(new BigDecimal(oneHundred));
				//打木箱
				if(WaybillConstants.WAYBILL_WOODEN_BOX.equals(packType)){
					BigDecimal oneBoxVolume =length.multiply(width).multiply(height).multiply(num);
					allBoxVolume=allBoxVolume.add(oneBoxVolume);
					allBoxNum=allBoxNum+woode.getGoodsNum();
					if("".equals(boxGoodsSize.toString())){
						boxGoodsSize.append(length.multiply(new BigDecimal(oneHundred))).append("*").append(width.multiply(new BigDecimal(oneHundred))).append("*").append(height.multiply(new BigDecimal(oneHundred))).append("*").append(num);
					}else{
						boxGoodsSize.append("+").append(length.multiply(new BigDecimal(oneHundred))).append("*").append(width.multiply(new BigDecimal(oneHundred))).append("*").append(height.multiply(new BigDecimal(oneHundred))).append("*").append(num);	
					}
					
				}
				//打木架
				if(WaybillConstants.WAYBILL_WOODEN_STAND.equals(packType)){
					BigDecimal oneStandVolume =length.multiply(width).multiply(height).multiply(num);
					allStandVolume=allStandVolume.add(oneStandVolume);
					allStandNum =allStandNum+(woode.getGoodsNum());
					if("".equals(standGoodsSize.toString())){
						standGoodsSize.append(length.multiply(new BigDecimal(oneHundred))).append("*").append(width.multiply(new BigDecimal(oneHundred))).append("*").append(height.multiply(new BigDecimal(oneHundred))).append("*").append(num);
					}else{
						standGoodsSize.append("+").append(length.multiply(new BigDecimal(oneHundred))).append("*").append(width.multiply(new BigDecimal(oneHundred))).append("*").append(height.multiply(new BigDecimal(oneHundred))).append("*").append(num);
					}
				}
				/**
				 * 非包装部分尺寸（297064，新增，存于PKP.T_SRV_WOODEN_REQUIREMENTS_PG）
				 */
				if (WaybillConstants.WAYBILL_WOODEN_NOPACK.equals(packType)) {
					if (StringUtil.isBlank(noPackGoodsSize.toString())) {
						noPackGoodsSize.append(length).append("*").append(width).append("*").append(height).append("*").append(num);
					} else {
						noPackGoodsSize.append("+").append(length).append("*").append(width).append("*").append(height).append("*").append(num);
					}
				}
			}
		}
		
		if((allBoxNum+allStandNum)>0){
			
 			/*//走货路径
 			FreightRouteDto routeDto =	waybillManagerService.queryFreightRouteBySourceTarget(orginalOrganizationCode,
 			pendingEntity.getCustomerPickupOrgCode(), pendingEntity.getProductCode(), new Date());
 			*/
			//理货员所属外场			
			woodenEntity.setPackageOrgCode(woodenPdaDto.getOuterCode());
			//运单号
			woodenEntity.setWaybillNo(woodenPdaDto.getWaybillNo());
			// 代打木架要求
			woodenEntity.setStandRequirement(pendingEntity.getPackageRemark());
			// 代打木箱要求
			woodenEntity.setSalverRequirement(pendingEntity.getPackageRemark());
			//打木架件数
			woodenEntity.setStandGoodsNum(allStandNum);
			//打木架尺寸
			woodenEntity.setStandGoodsSize(standGoodsSize.toString());
			//打木架体积=原体积*1.4
			woodenEntity.setStandGoodsVolume(allStandVolume.setScale(2, BigDecimal.ROUND_HALF_UP));
			//打木箱件数
			woodenEntity.setBoxGoodsNum(allBoxNum);
			//打木箱尺寸
			woodenEntity.setBoxGoodsSize(boxGoodsSize.toString());
			//打木箱体积 
			woodenEntity.setBoxGoodsVolume(allBoxVolume.setScale(2, BigDecimal.ROUND_HALF_UP));
			woodenEntity.setActive(FossConstants.ACTIVE);
			woodenEntity.setCreateTime(new Date());
			woodenEntity.setModifyTime(new Date());
		}
		woodenEntity.setStandGoodsNum(woodenEntity.getStandGoodsNum()!=null?woodenEntity.getStandGoodsNum():0);
		woodenEntity.setBoxGoodsNum(woodenEntity.getBoxGoodsNum()!=null?woodenEntity.getBoxGoodsNum():0);
		woodenEntity.setStandGoodsVolume(woodenEntity.getStandGoodsVolume()!=null?woodenEntity.getStandGoodsVolume():BigDecimal.ZERO);
		woodenEntity.setBoxGoodsVolume(woodenEntity.getBoxGoodsVolume()!=null?woodenEntity.getBoxGoodsVolume():BigDecimal.ZERO);
		
		/**
		 * 设置到woodenEntity中，非包装尺寸
		 */
		woodenEntity.setNoPackGoodsSize(noPackGoodsSize.toString());
		
		//外场打木托
		woodenEntity.setSalverGoodsNum(woodenEntity.getSalverGoodsNum()!=null?woodenEntity.getSalverGoodsNum():0);
        if(woodenPdaDto.getWoodenStockNum()!=null  &&  woodenPdaDto.getWoodenStockNum()>0){
        	//理货员所属外场
	    	woodenEntity.setPackageOrgCode(woodenPdaDto.getOuterCode());
	    	woodenEntity.setSalverGoodsNum(woodenPdaDto.getWoodenStockNum());
	    	woodenEntity.setSalverRequirement(woodenPdaDto.getWoodenStockNum()+"木托");
	    	//注释木托价格直接给0,此为临时数据，在激活时候进行计算
	    	woodenEntity.setSalverGoodsAmount(BigDecimal.ZERO);
	    	woodenEntity.setActive(FossConstants.ACTIVE);
	    	woodenEntity.setCreateTime(new Date());
	    	woodenEntity.setModifyTime(new Date());
	    } 
        
		if(0==woodenEntity.getStandGoodsNum()&&
		   0==woodenEntity.getBoxGoodsNum()&&
		   0==woodenEntity.getSalverGoodsNum()){
			return null;
		}
		return woodenEntity;
		 
    }
}
