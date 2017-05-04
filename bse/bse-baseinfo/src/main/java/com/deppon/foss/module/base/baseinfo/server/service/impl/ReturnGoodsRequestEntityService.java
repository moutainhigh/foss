package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IReturnGoodsRequestEntityDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.common.api.server.dao.IMsgOnlineDao;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineDto;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 返货工单Service
 * @author 198771
 *
 */
public class ReturnGoodsRequestEntityService implements IReturnGoodsRequestEntityService{
	
	private static final Logger logger = LoggerFactory.getLogger(ReturnGoodsRequestEntityService.class);


	private IReturnGoodsRequestEntityDao returnGoodsRequestEntityDao;
	
	private ICalculateTransportPathService calculateTransportPathService;
	
	private IDataDictionaryValueService dataDictionaryValueService;
	
	private IStockService stockService;
	
	private final int NUM_ONE=1;
	
	/**
	 * 在线通知Dao
	 */
	private IMsgOnlineDao msgOnlineDao;
	
	/**
	 * 声明行政组织service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	public void setReturnGoodsRequestEntityDao(
			IReturnGoodsRequestEntityDao returnGoodsRequestEntityDao) {
		this.returnGoodsRequestEntityDao = returnGoodsRequestEntityDao;
	}

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setMsgOnlineDao(IMsgOnlineDao msgOnlineDao) {
		this.msgOnlineDao = msgOnlineDao;
	}


	//根据原单号查询返货信息
	@Override
	public 	ReturnGoodsRequestEntity queryReturnGoodsRequestEntityByWayBillNo(String wayBillNo){
			ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
			condition.setOriWaybill(wayBillNo);
			condition.setActive(FossConstants.ACTIVE);
			
			List<ReturnGoodsRequestEntity> entitys = returnGoodsRequestEntityDao.queryReturnGoodsRequestEntityByCondition(condition);
			int index = 0;
			if(entitys!=null&&entitys.size()!=0){
				for(int i = NUM_ONE;i<entitys.size();i++){
					if(entitys.get(index).getTimeReport().getTime()<
							entitys.get(i).getTimeReport().getTime()){
						index = i;
					}
				}
				//只返回已受理的返单
				if(entitys.get(index)!=null
						&&MessageConstants.ACCEPTSTATUS_HANDLED.equals(entitys.get(index).getReturnStatus())){
				return entitys.get(index);
				}
			}
			return null;
		}
	
	
	//根据原单号查询返货信息(上报)
	@Override
	public 	ReturnGoodsRequestEntity queryReturnGoodsEntityByWayBillNo(String wayBillNo){
				ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
				condition.setOriWaybill(wayBillNo);
				condition.setActive(FossConstants.ACTIVE);
				
				List<ReturnGoodsRequestEntity> entitys = returnGoodsRequestEntityDao.queryReturnGoodsRequestEntityByCondition(condition);
				int index = 0;
				if(entitys!=null&&entitys.size()!=0){
					return entitys.get(index);					
				}
				return null;
			}
	
	//根据原单号查询返货信息（只要有一条返回已受理则返回true，false）
		@Override
		public 	boolean queryIsHandleByWayBillNo(String wayBillNo){
			if(null==wayBillNo||"".equals(wayBillNo)){
				logger.info("未传入原单号，无法做相关判断...........");
				return false;
			}
					ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
					condition.setOriWaybill(wayBillNo);
					condition.setActive(FossConstants.ACTIVE);
					
					Long bool = returnGoodsRequestEntityDao.queryIsHandleByWayBillNo(condition);					
					if(bool==0){
						return false;					
					}
					return true;
				}
	
	
	
	
	@Override
	public void addReturnGoodsRequestEntity(ReturnGoodsRequestEntity entity) {
		if(entity==null){
			return;
		}
		if(!FossConstants.ACTIVE.equals(entity.getActive())){
			entity.setActive(FossConstants.ACTIVE);
		}
		
		//默认新增是未受理的,Y表示受理,N表示未受理
		if(entity.getIsHandle()==null){
			entity.setIsHandle(FossConstants.INACTIVE);
			entity.setCreateTime(new Date());
		}
		if(entity.getReturnStatus()==null){
			entity.setReturnStatus(MessageConstants.ACCEPTSTATUS_REFUSED);
		}
		
		returnGoodsRequestEntityDao.addReturnGoodsRequestEntity(entity);
	}
	@Override
	public List<ReturnGoodsRequestEntity> queryReturnGoodsRequestEntityByCondition(
			ReturnGoodsRequestEntity condition) {
		if(condition!=null){
			condition.setActive(FossConstants.ACTIVE);
		}
		List<ReturnGoodsRequestEntity>  returnGoodsRequestEntitys = returnGoodsRequestEntityDao.queryReturnGoodsRequestEntityByCondition(condition);
		return returnGoodsRequestEntitys;
	}

	@Override
	public void updateReturnGoodsRequestEntity(
			ReturnGoodsRequestEntity entity){
		//313353 sonar 
		this.sonarSplitOne(entity);

		ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
		//先根据处理编号进行查询,如果能查到说明这条信息已经被受理,就不再进行受理
		condition.setDealnumber(entity.getDealnumber());
		boolean checkDealnumber=this.queryExistsReturnGoodsRequestEntityByDealnumber(entity.getDealnumber());
		if(checkDealnumber){
			return;
		}
		condition = new ReturnGoodsRequestEntity();
		condition.setFossId(entity.getFossId());
		List<ReturnGoodsRequestEntity>  entitys = this.queryReturnGoodsRequestEntityByCondition(condition);
		if(entitys!=null&&entitys.size()>0){
			for(ReturnGoodsRequestEntity entityTemp:entitys){
				//处理编号为空说明是从接送货推送过来的数据，获取到上报人部门就可以了
				if(entityTemp!=null&&"".equals(entityTemp.getDealnumber())){
					entity.setReportDepartmentCode(entityTemp.getReportDepartmentCode());
					break;
				}
			}
		}
		//先作废掉
		condition.setActive(FossConstants.INACTIVE);
		returnGoodsRequestEntityDao.updateReturnGoodsRequestEntity(condition);
		long tss = System.currentTimeMillis();
		String arrveCode="";
		String startCode="";
		
		OrgAdministrativeInfoEntity orgEntity = new OrgAdministrativeInfoEntity();
		List<OrgAdministrativeInfoEntity> orgEntitysArrive =null;
		List<OrgAdministrativeInfoEntity> orgEntitysStart=null;
		
		if(StringUtils.isBlank(entity.getDeptArrive())){
			arrveCode="";
		}else{
			orgEntity.setName(entity.getDeptArrive());
			orgEntitysArrive=  orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByEntity(orgEntity, 0, NUM_ONE);
		}
		if(null != orgEntitysArrive && CollectionUtils.isNotEmpty(orgEntitysArrive)&&
				orgEntitysArrive.get(0)!=null){
			arrveCode=orgEntitysArrive.get(0).getCode();
		}
		if(StringUtils.isBlank(entity.getDeptStart())){
			startCode="";
		}else{
			orgEntity.setName(entity.getDeptStart());
			orgEntitysStart =  orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByEntity(orgEntity, 0, NUM_ONE);
		}
		if(null != orgEntitysStart && CollectionUtils.isNotEmpty(orgEntitysStart)
				&&orgEntitysStart.get(0)!=null){
			startCode=orgEntitysStart.get(0).getCode();
		}
		this.addOnLineMsg(entity, arrveCode, startCode);
		logger.info("foss综合添加在线通知["+entity.getOriWaybill()+"]["+entity.getDealnumber()+"]所耗的时间]:"+(System.currentTimeMillis()-tss)+"");
		//返货处理结果:未处理
		entity.setHandleResult(MessageConstants.HANDLE_RESULT_UNHANDLE);
		if(entity.getIsHandle()==null){
			entity.setIsHandle(FossConstants.ACTIVE);
		}
		entity.setFossId(UUID.randomUUID().toString());
		this.addReturnGoodsRequestEntity(entity);
		//同城转寄或者七天返货才推给中转
		if("SAME_CITY".equals(entity.getReturnType())||"SEVEN_DAYS_RETURN".equals(entity.getReturnType())){
			long t = System.currentTimeMillis();
			this.syncWayBillNoToTfr(entity,arrveCode);
			logger.info("foss综合将同城转寄或者七天返货["+entity.getOriWaybill()+"]["+entity.getDealnumber()+"]推给中转所耗的时间]:"+(System.currentTimeMillis()-t)+"");

		}
		if("OUTBOUND_THREE_DAYS_RETURN".equals(entity.getReturnType())){
			long ts = System.currentTimeMillis();
			this.syncWayBillNoToTfr(entity.getOriWaybill());
			logger.info("foss综合将外发三天的返货["+entity.getOriWaybill()+"]["+entity.getDealnumber()+"]推给中转所耗的时间]:"+(System.currentTimeMillis()-ts)+"");
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(ReturnGoodsRequestEntity entity) {
		if(entity==null||entity.getFossId()==null){
			throw new BusinessException("数据错误！");
		}
	}
	
	@Override
	public void updateHandleResult(ReturnGoodsRequestEntity entity){
		if(entity==null||entity.getDealnumber()==null){
			logger.info("中转修改返货处理结果失败...........");
			return;
		}
		if(entity.getHandleResult()==null){
			entity.setHandleResult(MessageConstants.HANDLE_RESULT_COMPLEMENT);
		}
		returnGoodsRequestEntityDao.updateHandleResult(entity);
	}
	/**
	 * 将运单号和到达部门编码同步给中转
	 * @param arrveCode 
	 */
	private void syncWayBillNoToTfr(ReturnGoodsRequestEntity entity, String arrveCode){
		try{
			String deptArrive = entity.getDeptArrive();
			if(deptArrive==null||deptArrive.equals("")){
				//如果到达部门为空（异常情况），直接传空给中转，由中转处理抛出异常
				calculateTransportPathService.changePathForExpressToArrivalFirstOrg(entity.getOriWaybill(), null);
			}else{
				calculateTransportPathService.changePathForExpressToArrivalFirstOrg(entity.getOriWaybill(), arrveCode);
				logger.info("推送数据到中转成功.....["+entity.getOriWaybill()+"]["+entity.getDeptArrive()+"]");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("推送数据到中转失败.....["+entity.getOriWaybill()+"]["+entity.getDeptArrive()+"]");
		}
	}
	/**
	 * 返货类型为外发3天返货时，推送给中转，判断原单号是否在外场库存
		a.	若在外场库存，则系统自动将该单入库到异常货区
		b.	若不在外场库存，不进行操作
	 */
	private void syncWayBillNoToTfr(String wayBillNo){
		try{
			stockService.isInTransferCenter(wayBillNo);
			logger.info("推送运单号到中转成功.....["+wayBillNo+"]");
		}catch(Exception e){
			e.printStackTrace();
			logger.info("推送运单号到中转失败.....["+wayBillNo+"");
		}
	}
	
	/**
	 * 添加在线通知
	 */
	private void addOnLineMsg(ReturnGoodsRequestEntity entity,
			String arrveCode,
			String startCode) {
		try{
			MsgOnlineDto msgOnlineDto = new MsgOnlineDto();
			boolean bool = true;
			if(StringUtils.isBlank(arrveCode)){
				bool = false;
			}			
			String returnStatus = entity.getReturnStatus();
			if("HANDLED".equals(returnStatus)){
				String returnType = entity.getReturnType();
				//在线消息
				msgOnlineDto=this.getMsgOnlineDto(returnType,arrveCode,startCode,bool,msgOnlineDto,entity);
			}			
			if("RETURNED".equals(returnStatus)){
				String reportDepartmentCode = entity.getReportDepartmentCode();
				if(reportDepartmentCode==null||reportDepartmentCode.equals("")){
					return ;
				}
				//上报
				OrgAdministrativeInfoEntity orgEntityReport = new OrgAdministrativeInfoEntity();	
				orgEntityReport.setCode(reportDepartmentCode);
				String reportDepartmentName=orgAdministrativeInfoService.querySimpleNameByCode(reportDepartmentCode);
				msgOnlineDto.setReceiveOrgCode(reportDepartmentCode);
				msgOnlineDto.setReceiveOrgName(reportDepartmentName);
				msgOnlineDto.setWaybillNo(entity.getOriWaybill());
				//获取出发部门
				msgOnlineDto.setSendOrgCode(startCode);
				msgOnlineDto.setSendOrgName(entity.getDeptStart());
				msgOnlineDto.setSendUserName(entity.getManReport());
				msgOnlineDto.setSendUserCode(entity.getManReportCode());				
				String returnType = "不同意";
				msgOnlineDto.setContext("您部门存在一条【"+returnType+"】返货工单，请及时处理！");
				msgOnlineDto.setAcceptStatus(MessageConstants.MSG__STATUS__PROCESSING);
				msgOnlineDao.addOnlineMsg(msgOnlineDto,0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("增加在线通知失败...............");
		}
	}
	
	/**
	 * 在线消息
	 * @param returnType
	 * @param arrveCode
	 * @param startCode
	 * @param bool
	 * @param msgOnlineDto
	 * @param entity
	 * @return
	 */
	private MsgOnlineDto getMsgOnlineDto(String returnType,
			String arrveCode,
			String startCode, boolean bool, MsgOnlineDto msgOnlineDto, ReturnGoodsRequestEntity entity) {
		if("RETURN_BACK".equals(returnType)||"SAME_CITY".equals(returnType)||"OTHER_CITY".equals(returnType)){									
			msgOnlineDto.setReceiveOrgCode(arrveCode);
			msgOnlineDto.setReceiveOrgName(entity.getDeptArrive());
			msgOnlineDto.setWaybillNo(entity.getOriWaybill());
			//获取出发部门
			msgOnlineDto.setSendOrgCode(startCode);
			msgOnlineDto.setSendOrgName(entity.getDeptStart());
			msgOnlineDto.setSendUserName(entity.getManReport());
			msgOnlineDto.setSendUserCode(entity.getManReportCode());
			List<DataDictionaryValueEntity> dataDictionaryValueEntitys = dataDictionaryValueService.
					queryDataDictionaryValueByTermsCode(DictionaryConstants.RETURN_GOODS_TYPE);
			
			returnType=this.getReturnType(dataDictionaryValueEntitys,returnType);
			
			msgOnlineDto.setContext("您部门存在一条【"+returnType+"】返货工单，请及时处理！");
			msgOnlineDto.setAcceptStatus(MessageConstants.MSG__STATUS__PROCESSING);
		if(bool){	
			msgOnlineDao.addOnlineMsg(msgOnlineDto,0);
		}
			msgOnlineDto.setReceiveOrgCode(startCode);
			msgOnlineDto.setReceiveOrgName(entity.getDeptStart());
			msgOnlineDao.addOnlineMsg(msgOnlineDto,0);
	}else{
		if(bool){
			msgOnlineDto.setReceiveOrgCode(arrveCode);
			msgOnlineDto.setReceiveOrgName(entity.getDeptArrive());
	
		}else{
			msgOnlineDto.setReceiveOrgCode(startCode);
			msgOnlineDto.setReceiveOrgName(entity.getDeptStart());
			
		}
		msgOnlineDto.setSendOrgCode(startCode);
		msgOnlineDto.setSendOrgName(entity.getDeptStart());
		msgOnlineDto.setWaybillNo(entity.getOriWaybill());
		msgOnlineDto.setSendUserName(entity.getManReport());
		msgOnlineDto.setSendUserCode(entity.getManReportCode());
		List<DataDictionaryValueEntity> dataDictionaryValueEntitys = dataDictionaryValueService.
				queryDataDictionaryValueByTermsCode(DictionaryConstants.RETURN_GOODS_TYPE);
		returnType=this.getReturnType(dataDictionaryValueEntitys,returnType);
		msgOnlineDto.setContext("您部门存在一条【"+returnType+"】返货工单，请及时处理！");
		msgOnlineDto.setAcceptStatus(MessageConstants.MSG__STATUS__PROCESSING);
		msgOnlineDao.addOnlineMsg(msgOnlineDto,0);
	}
		return msgOnlineDto;
	}

	/**
	 * 
	 * @param dataDictionaryValueEntitys
	 * @param returnType 
	 * @return
	 */
	private String  getReturnType(
			List<DataDictionaryValueEntity> dataDictionaryValueEntitys, String returnType) {
		if(null!=dataDictionaryValueEntitys&&dataDictionaryValueEntitys.size()>0){
			for(int i=0;i<dataDictionaryValueEntitys.size();i++){
				if(returnType.equals(dataDictionaryValueEntitys.get(i).getValueCode())){
					returnType = dataDictionaryValueEntitys.get(i).getValueName();
					break;
				}
			}
		}
		
		return returnType;
				
	}

	/**
	 * 验证处理编号是否存在
	 * @param dealNumber
	 * @return
	 */
	@Override
	public boolean queryExistsReturnGoodsRequestEntityByDealnumber(
			String dealNumber) {
		if(dealNumber==null||"".equals(dealNumber)){
			logger.info("处理编号不鞥能为空");
			return false;
		}
		return returnGoodsRequestEntityDao.queryExistsReturnGoodsRequestEntityByDealnumber(dealNumber);
	}
	
}
