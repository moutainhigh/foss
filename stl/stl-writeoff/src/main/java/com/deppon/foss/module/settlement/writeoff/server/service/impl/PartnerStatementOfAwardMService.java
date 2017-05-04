package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerStatementOfAwardDao;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerStatementOfAwardMDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardMService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;

/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-2-17 下午2:13:57    
 */
public class PartnerStatementOfAwardMService implements
		IPartnerStatementOfAwardMService {

	/**
	 * 日志 
	 */
	private static final Logger logger = LoggerFactory.getLogger(PartnerStatementOfAwardMService.class);
	
	/**
	 * 注入对账单管理DAO
	 */
	private IPartnerStatementOfAwardMDao partnerStatementOfAwardMDao;
	
	/**
	 * 注入对账单新增DAO
	 */
	private IPartnerStatementOfAwardDao partnerStatementOfAwardDao; 
	
	/**
	 * 注入组织服务
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	

	/**
	 * 合伙人奖罚对账单查询
	 * @param partnerStatementOfAwardDto
	 * @param start
	 * @param limit
	 * @return dto
	 */
	@Override
	public PartnerStatementOfAwardDto queryPAwardStatement(
			PartnerStatementOfAwardDto queryDto, int start,int limit) {
		
		//记录日志
		logger.info("合伙人奖罚对账单查询开始...");
		
		//封装返回参数dto
		PartnerStatementOfAwardDto resultDto = new PartnerStatementOfAwardDto();
		
		//对账单查询集合
		List<PartnerStatementOfAwardEntity> psaEntityList = new ArrayList<PartnerStatementOfAwardEntity>();
		
		//对账单总条数
		Long totalCount = 0L;
		
		//1.按合伙人查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryTabType())){
			//获取部门
			validateDto(queryDto);
			
			//根据合伙人查询对账单信息
			psaEntityList = partnerStatementOfAwardMDao.queryPStatementsByPartner(queryDto,start,limit);
			//查询总条数
			totalCount = partnerStatementOfAwardMDao.queryPStatementsByPartnerNum(queryDto);
		}
		
		//2.按对账单号查询
		else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(queryDto.getQueryTabType())) {		
			//判断对账单集合参数是否为空
			List<String> statementBillNosList = queryDto.getStatementBillNos();
			if(CollectionUtils.isEmpty(statementBillNosList) ){
				throw new SettlementException("对账单号集合为空！");
			}
			psaEntityList = partnerStatementOfAwardMDao.queryPStatementsByStatementNos(statementBillNosList,start,limit);
			//查询总条数
			totalCount = partnerStatementOfAwardMDao.queryPStatementsNos(statementBillNosList);
		}
		
		//3.按运单号查询对账单
		else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(queryDto.getQueryTabType())) {
			//判断运单号集合参数是否为空
			List<String> wayBillNosList = queryDto.getWayBillNos();
			if (CollectionUtils.isEmpty(wayBillNosList)) {
				throw new SettlementException("运单号集合为空！");
			}
			//按运单号集合获取对账单明细集合
			List<PartnerStatementOfAwardDEntity> psadEntityList = partnerStatementOfAwardMDao.queryPSAwardDList(wayBillNosList);
			//非空判断对账单明细集合
			if(CollectionUtils.isNotEmpty(psadEntityList)){
				// 对账单号集合
				List<String> statementNos = new ArrayList<String>();
				//循环对账单明细
				for (PartnerStatementOfAwardDEntity psadEntity : psadEntityList) {
					//获取对账单号集合
					statementNos.add(psadEntity.getStatementBillNo());
				}
				//根据对账单号集合查询对账单
				psaEntityList = partnerStatementOfAwardMDao.queryPStatementsByStatementNos(statementNos,start,limit);
				//查询总条数
				totalCount = partnerStatementOfAwardMDao.queryPStatementsNos(statementNos);
			}
		}
		
		//4.按部门查询对账单
		else if(SettlementConstants.TAB_QUERY_BY_FAILING_INVOICE.equals(queryDto.getQueryTabType())){
			//根据部门查询对账单信息
			psaEntityList = partnerStatementOfAwardMDao.queryPStatementsBydep(queryDto,start,limit);
			//查询总条数
			totalCount = partnerStatementOfAwardMDao.queryPStatementsBydepNum(queryDto);
		}
		
		//设置返回参数
		resultDto.setPartnerStatementOfAwardList(psaEntityList);
		resultDto.setCount(totalCount);
		
		return resultDto;
	}
	
	/**
	 * 根据查询条件获取部门
	 * @param queryDto
	 */
	private void validateDto(PartnerStatementOfAwardDto queryDto) {
		//声明目标部门集合
		List<String> targetOrgCodeLsit = new ArrayList<String>();
		
		//如果部门存在，则获取当前部门与用户所管辖部门的交集
		if (StringUtils.isNotBlank(queryDto.getOrgCode())) {
			targetOrgCodeLsit.add(queryDto.getOrgCode());
		} else{
			//如果部门不存在，小区存在，则获取小区地下所有部门与该用户所管辖部门交集	
			if(StringUtils.isNotBlank(queryDto.getSmallRegion())){
				//调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(queryDto.getSmallRegion());
				//循环部门，获取用户所管辖部门与查询到部门的交集
				for(OrgAdministrativeInfoEntity en: orgList){
					targetOrgCodeLsit.add(en.getCode());
				}
			}else{
				//如果部门、小区都不存在，而大区存在，	则获取大区底下所有部门与该用户所管辖部门交集	
				if(StringUtils.isNotBlank(queryDto.getLargeRegion())){
					//调用综合方法查询
					List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(queryDto.getLargeRegion());
					//循环部门，获取用户所管辖部门与查询到部门的交集
					for(OrgAdministrativeInfoEntity en: orgList){
						targetOrgCodeLsit.add(en.getCode());
					}
				}
			}
		}
		// 设置可查询部门结果集
		queryDto.setOrgCodeList(targetOrgCodeLsit);
	}


	/**
	 * 根据对账单号查询对账单明细
	 * @param statementNo
	 * @return dto
	 */
	@Override
	public PartnerStatementOfAwardDto queryPAwardStatementDetail(PartnerStatementOfAwardDto dto) {
		if(StringUtils.isBlank(dto.getStatementBillNo())){
			throw new SettlementException("对账单号为空！");
		}
		
		//封装返回参数dto
		PartnerStatementOfAwardDto resultDto = new PartnerStatementOfAwardDto();
		//获取所有生成的对账单明细-根据对账单号查询
		List<PartnerStatementOfAwardDEntity> list = partnerStatementOfAwardDao.querypartnerDByStatementBillNo(dto);
/*		if(CollectionUtils.isEmpty(list)){
			throw new SettlementException("查询对账单为空！");
		}*/
		
		//将查询对账单明细结果封装dto并返回
		resultDto.setPartnerStatementOfAwardDList(list);	
		resultDto.setCount(list.size());
		return resultDto;
	}

	/**
	 * 根据对账单号查询对账单
	 * @param statementNo
	 * @return PartnerStatementOfAwardEntity
	 */
	@Override
	public PartnerStatementOfAwardEntity queryPStatementsByStaNoNPage(String statementBillNo) {
		if(StringUtils.isBlank(statementBillNo)){
			throw new SettlementException("对账单号为空！");
		}
		return this.partnerStatementOfAwardMDao.queryPStatementsByStaNoNPage(statementBillNo);
	}
	
	/**
	 * 更新合伙人奖罚对账单--对账单实体
	 * @param partnerStatementOfAwardEntity
	 */
	@Override
	public void updateStatementForWriteOff(PartnerStatementOfAwardEntity entity,CurrentInfo cInfo) {
		//对账单DTO
		PartnerStatementOfAwardDto queryDto = new PartnerStatementOfAwardDto();
		//本期已还金额
		queryDto.setPaidAmount(entity.getPaidAmount());
		//本期未还金额
		queryDto.setUnpaidAmount(entity.getUnpaidAmount());
		//结账次数
		queryDto.setSettleNum((short) (entity.getSettleNum()+1));
		//修改用户编号
		queryDto.setModifyUserCode(cInfo.getEmpCode());
		//修改用户名称
		queryDto.setModifyUserName(cInfo.getEmpName());
		//修改时间
		queryDto.setModifyTime(new Date());
		//对账单单号
		queryDto.setStatementBillNo(entity.getStatementBillNo());
		
		//更新合伙人奖罚对账单
		int updateNo = partnerStatementOfAwardMDao.updateStatementForWriteOff(queryDto);

	}
	
	/**
	 * 更新合伙人奖罚对账单--对账单实体集合
	 * @param pAwardEntityList
	 */
	@Override
	public void updateStatementForWriteOffByList(List<PartnerStatementOfAwardEntity> pAwardEntityList,
			CurrentInfo currentInfo) {
		//封装对账单dto
		List<PartnerStatementOfAwardDto> queryDtoList = new ArrayList<PartnerStatementOfAwardDto>();
		if(CollectionUtils.isNotEmpty(pAwardEntityList)){
			for (PartnerStatementOfAwardEntity pAwardEntity : pAwardEntityList) {
				PartnerStatementOfAwardDto dto = new PartnerStatementOfAwardDto();
				dto.setPeriodAmount(pAwardEntity.getPeriodAmount());
				dto.setPaidAmount(pAwardEntity.getPaidAmount());
				dto.setUnpaidAmount(pAwardEntity.getUnpaidAmount());
				dto.setPaidAmount(pAwardEntity.getPaidAmount());
				dto.setPeriodPayAmount(pAwardEntity.getPeriodPayAmount());
				dto.setPeriodUnverifyPayAmount(pAwardEntity.getPeriodUnverifyPayAmount());
				dto.setPeriodRecAmount(pAwardEntity.getPeriodRecAmount());
				dto.setPeriodUnverifyRecAmount(pAwardEntity.getPeriodUnverifyRecAmount());
				dto.setModifyUserCode(currentInfo.getEmpCode());
				dto.setModifyUserName(currentInfo.getEmpName());
				dto.setStatementBillNo(pAwardEntity.getStatementBillNo());
				queryDtoList.add(dto);
			}
			//更新合伙人奖罚对账单
			int updateNo = partnerStatementOfAwardMDao.updateStatementForWriteOffByList(queryDtoList);
			if(updateNo==0){
				throw new SettlementException("更新对账单失败！");
			}
		}else{
			throw new SettlementException("对账单实体为空！");
		}
		
	}
	
	/**
	 * 对账单确认/反确认
	 * @author foss结算-306579-guoxinru
	 * @date 2016-02-28 
	 */
	@Override
	@Transactional
	public void updateStatusByStatementNo(PartnerStatementOfAwardDto queryDto,CurrentInfo currentInfo) {
		
		//记录日志
		logger.info("更改对账单状态开始...");
		if(StringUtils.isBlank(queryDto.getStatementBillNo())){
			throw new SettlementException("对账单号为空！");
		}
		
		//根据对账单号查询对账单，判断对账单已还金额是否大于0，大于0则已付款或已扣款，则不能再确认、反确认
		PartnerStatementOfAwardEntity entity = this.partnerStatementOfAwardMDao.queryPStatementsByStaNoNPage(queryDto.getStatementBillNo());
		if(entity.getPaidAmount().compareTo(BigDecimal.ZERO) > 0){
			throw new SettlementException("对账单已付款或扣款，不能确认/反确认！");
		}
		
		//设置修改状态用户编码
		queryDto.setModifyUserCode(currentInfo.getEmpCode());
		//设置修改状态用户名称
		queryDto.setModifyUserName(currentInfo.getEmpName());
		
		//判断对账单状态
		if(StringUtils.isNotBlank(queryDto.getConfirmStatus())){
			if("Y".equals(queryDto.getConfirmStatus())){
				//设置未确认状态
				queryDto.setConfirmStatus("N");
			}else{
				//设置已确认状态
				queryDto.setConfirmStatus("Y");
			}
			//调用dao更改对账单状态
			int updateRow;
			try {
				updateRow = this.partnerStatementOfAwardMDao.updateStatusByStatementNo(queryDto);
			} catch (BusinessException e) {
				// 记录日志
				logger.error(e.getMessage(), e);
				throw new SettlementException(e.getErrorCode());
			}
			//判断更新行数
			if(updateRow == 0){
				throw new SettlementException("更新对账单状态失败！");
			}
		}else{
			throw new SettlementException("对账单状态为空！");
		}
		
		
	}
	/**
	 * 奖罚对账单明细删除
	 * @author foss结算-306579-guoxinru
	 * @date 2016-03-04 
	 */
	@Override
	@Transactional
	public PartnerStatementOfAwardDto delPAwardStatementD(PartnerStatementOfAwardDto dto) {
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//设置当前用户工号
		dto.setEmpCode(currentInfo.getEmpCode());
		//设置当前用户姓名
		dto.setEmpName(currentInfo.getEmpName());
		//设置当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//判断对账单的状态是否确认确认后不能删除
		PartnerStatementOfAwardEntity partnerStatementOfAwardEntityList = null;
		try{
			partnerStatementOfAwardEntityList = partnerStatementOfAwardMDao.queryPStatementsByStaNoNPage(dto.getStatementBillNo());
		}catch(SettlementException e){
			logger.error("查询对账单信息失败！"+e.getMessage());
			throw new SettlementException("查询对账单信息失败！");
		}
		if(null==partnerStatementOfAwardEntityList){
			throw new SettlementException("对账单信息已存在！");
		}
		if(partnerStatementOfAwardEntityList.getConfirmStatus().equals("Y")){
			throw new SettlementException("对账单已确认不能删除！");
		}
		//将对账单状态封装到dto返回前台
		dto.setConfirmStatus(partnerStatementOfAwardEntityList.getConfirmStatus());
		//按单号删除对账单明细
		int updateRows = partnerStatementOfAwardMDao.delPAwardStatementD(dto);
		//根据对账单号查询最新对账单明细
		List<PartnerStatementOfAwardDEntity> list = this.partnerStatementOfAwardDao.querypartnerDByStatementBillNo(dto);
		//重新根据对账单明细计算对账单金额
		new PartnerStatementOfAwardService().countPeriodAmount(list, dto);	
		//按对账单单号更新对账单金额、单据类型
		updateRows = partnerStatementOfAwardMDao.pAwardStatementUpdateAmountByStatementBillNo(dto);
		//判断更新行数
		if(updateRows != 1){
			throw new SettlementException("对账单单号存在重复！");
		}
		//更新应付单
		int updatePayRows = partnerStatementOfAwardMDao.updatePayStatementBillNo(dto);
		//更新应收单
		int updateRecRows = partnerStatementOfAwardMDao.updateRecStatementBillNo(dto);
		//判断更新行数
		if(updatePayRows == 0 && updateRecRows == 0){
			throw new SettlementException("对账单明细删除失败，应收应付单据更新条数同时为零！");
		}
		//返回参数
		return dto;
	}
	
	/**
	 * 根据对账单号集合查询对账单集合
	 * @author foss结算-306579-guoxinru
	 * @date 2016-03-05
	 */
	@Override
	public List<PartnerStatementOfAwardEntity> queryPStatementsByStaNoList(List<String> statementNoList) {
		if(CollectionUtils.isEmpty(statementNoList)){
			throw new SettlementException("对账单号集合为空！");
		}
		return this.partnerStatementOfAwardMDao.queryPStatementsByStaNoList(statementNoList);
	}
	
	/**
	 * 根据对账单号集合查询对账单明细集合
	 * @author foss结算-306579-guoxinru
	 * @date 2016-03-06
	 */
	@Override
	public List<PartnerStatementOfAwardDEntity> queryPStatementDByStaNoList(
			List<String> statementNoList) {
		if(CollectionUtils.isEmpty(statementNoList)){
			throw new SettlementException("对账单号集合为空！");
		}
		return this.partnerStatementOfAwardMDao.queryPStatementDByStaNoList(statementNoList);
	}
	
	/**
	 * 根据对账单号查询对账单 
	 * @author foss结算-367752
	 * @date 2016-09-03
	 */
	@Override
	public PartnerStatementOfAwardEntity queryPStatementsByStaNo(String statementNo) {
		if(StringUtil.isEmpty(statementNo)){
			throw new SettlementException("对账单号为空！");
		}
		List<PartnerStatementOfAwardEntity> list = this.partnerStatementOfAwardMDao.queryPStatementsByStaNo(statementNo);
		if(null !=list){
			logger.info("根据对账单号："+statementNo+",查询出来的结果条数："+list.size());
			return list.get(0);
		}else{
			logger.info("根据对账单号："+statementNo+",查询出来的结果为null！");
			return null;
		}
		
	}
	
	/**
	 * 根据对账单号查询对账单明细集合  
	 * @author foss结算-367752
	 * @date 2016-09-03
	 */
	@Override
	public List<PartnerStatementOfAwardDEntity> queryPStatementDByStaNo(String statementNo) {
		if(StringUtil.isEmpty(statementNo)){
			throw new SettlementException("对账单号为空！");
		}
		return this.partnerStatementOfAwardMDao.queryPStatementDByStaNo(statementNo);
	}

	/**
	 * 根据对账单号、应收应付单号添加对账单明细
	 * @param queryDto
	 * @return
	 */
	@Override
	@Transactional
	public PartnerStatementOfAwardDto addPAwardStatementD(PartnerStatementOfAwardDto queryDto) {
		//记录日志
		logger.info("合伙人奖罚添加对账单明细，enter service...");
		// 获取当前登录信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		queryDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		queryDto.setEmpName(currentInfo.getEmpName());
		//获取当前用户所在部门编码
		queryDto.setOrgCode(currentInfo.getCurrentDeptCode());		
		//判断对账单的状态是否确认确认后不能添加明细
		PartnerStatementOfAwardEntity partnerStatementOfAwardEntity= null;
		try{
			partnerStatementOfAwardEntity = partnerStatementOfAwardMDao.queryPStatementsByStaNoNPage(queryDto.getStatementBillNo());
		}catch(SettlementException e){
			logger.error("查询对账单信息失败！"+e.getMessage());
			throw new SettlementException("查询对账单信息失败！");
		}
		if(null==partnerStatementOfAwardEntity){
			throw new SettlementException("对账单信息已存在！");
		}
		if(partnerStatementOfAwardEntity.getConfirmStatus().equals("Y")){
			throw new SettlementException("对账单已确认不能添加明细！");
		}
		//将对账单状态封装到dto返回前台
		queryDto.setConfirmStatus(partnerStatementOfAwardEntity.getConfirmStatus());
		try {
			//插入行数
			int insertDRows = 0;
			//生成对账单明细
			insertDRows = this.partnerStatementOfAwardMDao.addPAwardStatementD(queryDto);
			if(insertDRows!=1){
				throw new SettlementException("添加对账单明细失败！");
			}
			//获取所有生成的对账单明细-根据对账单号查询
			List<PartnerStatementOfAwardDEntity> list = partnerStatementOfAwardDao.querypartnerDByStatementBillNo(queryDto);
			//设置对账单金额
			new PartnerStatementOfAwardService().countPeriodAmount(list,queryDto);
			//按对账单单号更新对账单金额
			int updateRows = partnerStatementOfAwardMDao.pAwardStatementUpdateAmountByStatementBillNo(queryDto);
			//判断更新行数
			if(updateRows != 1){
				throw new SettlementException("更新对账单金额失败！");
			}
			//更新应付单
			int updatePayRows = partnerStatementOfAwardDao.partnerPayUpdateByStatementBillNo(queryDto);
			//更新应收单
			int updateRecRows = partnerStatementOfAwardDao.partnerRecUpdateByStatementBillNo(queryDto);
			//判断更新的应收应付单据总条数是否和生成的对账单明细条数是否相等
			if((updatePayRows + updateRecRows) != insertDRows){
				throw new SettlementException("对账单明细生成条数和应付单应收单生成条数不一致，请重新查询！");
			}
		} catch (BusinessException e) {
			// 记录日志
			logger.error(e.getMessage(), e);
			throw new SettlementException(e.getErrorCode());
		}
		return queryDto;
	}
	
	/**
	 * 对账单确认/反确认
	 * @author 367752
	 * @date 2016-10-13
	 */
	@Override
	public void updateStatusByStatementNoAuto(String statementNo) {
		
		if(StringUtils.isBlank(statementNo)){
			throw new SettlementException("对账单号为空！");
		}
		
		PartnerStatementOfAwardDto queryDto = new PartnerStatementOfAwardDto();
		//设置修改状态用户编码
		queryDto.setModifyUserCode(SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPCODE);
		//设置修改状态用户名称
		queryDto.setModifyUserName(SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPNAME);
		
		queryDto.setConfirmStatus("Y");

		queryDto.setStatementBillNo(statementNo);
			//调用dao更改对账单状态
			int updateRow;
			try {
				updateRow = this.partnerStatementOfAwardMDao.updateStatusByStatementNo(queryDto);
			} catch (BusinessException e) {
				// 记录日志
				logger.error(e.getMessage(), e);
				throw new SettlementException(e.getErrorCode());
			}
			//判断更新行数
			if(updateRow == 0){
				throw new SettlementException("更新对账单状态失败！");
			}
				
	}
	/**
	 * 查询需要重推的合伙人奖罚对账单,查询条件为：
	 * 1.按照操作的人为：SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPCODE="partnerStatementOfAward_sysJob"
	 * 2.状态为未扣款
	 * 3.仅针对需要扣款的对账单
	 * @author 367752
	 * @return List<PartnerStatementOfAwardEntity>
	 */		
	@Override
	public List<PartnerStatementOfAwardEntity> querypartnerDNeedReDecduct(
			PartnerStatementOfAwardDto dto) {
		return partnerStatementOfAwardMDao.querypartnerDNeedReDecduct(dto);		 
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setPartnerStatementOfAwardMDao(
			IPartnerStatementOfAwardMDao partnerStatementOfAwardMDao) {
		this.partnerStatementOfAwardMDao = partnerStatementOfAwardMDao;
	}

	public void setPartnerStatementOfAwardDao(
			IPartnerStatementOfAwardDao partnerStatementOfAwardDao) {
		this.partnerStatementOfAwardDao = partnerStatementOfAwardDao;
	}

}
