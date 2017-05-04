package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.util.DateUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.dao.IForkliftEfficientQueryDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IForkliftEfficientQueryService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ForkliftDriverEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ForkliftEfficientEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TransferFieldEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.ForkliftEfficientVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class ForkliftEfficientQueryService implements IForkliftEfficientQueryService{
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;//查询部门名称和部门编码
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService ;//查询上级部门名称和部门编码
	private IForkliftEfficientQueryDao forkliftEfficientQueryDao;
	private Logger LOGGER = LoggerFactory.getLogger(ForkliftEfficientQueryService.class);
	/**
	 * 获取当前部门编号
	 * @author zenghaibin
	 * @date 2014-7-08 上午9:50:53
	 * @return String
	 */
	@Override
	public String queryOrgCode() {
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity org =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(orgCode);
		if(null!=org){
			//校验是否是个营业部
			if(StringUtils.isNotBlank(org.getSalesDepartment())&&StringUtils.equals(org.getSalesDepartment(), FossConstants.YES) || 
					(StringUtils.isNotBlank(org.getTransferCenter())&&StringUtils.equals(org.getTransferCenter(), FossConstants.YES))){
				return orgCode;
		   //校验是否是个外场
			}/*else if(StringUtils.isNotBlank(org.getTransferCenter())&&StringUtils.equals(org.getTransferCenter(), FossConstants.YES)){
				return orgCode;
			}*/else{
	             List<String> bizTypes =new ArrayList<String>();
	             bizTypes.add("TRANSFER_CENTER");
	             //查询所属上级部门是否是个外场
	             OrgAdministrativeInfoEntity superOrg =orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(org.getCode(), bizTypes);
	             //若上级外场存在， 则返回上级外场，若不存在，则返回用户数据权限
	             if(null !=superOrg){
	            	 return superOrg.getCode();//
	             }else{
	             //默认为专业部门
	            	 return orgCode;
	             }
	         }
		}
		return "";
  }
	
	/**
	 *查询叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return List<ForkliftEfficientEntity> 叉车数量信息Entity
	 * @param forkliftEfficientVo 前台页面传递的查询条件 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	@Override
	public List<ForkliftEfficientEntity> queryForkliftData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start){
		List<ForkliftEfficientEntity> forkliftEfficientEntityList = new ArrayList();
		forkliftEfficientEntityList=forkliftEfficientQueryDao.queryForkliftData(forkliftEfficientVo, limit, start);
		
		return forkliftEfficientEntityList;
	}
	/**
	 *查询叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return 数据总数 
	 * @param forkliftEfficientVo 前台页面传递的查询条件 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	public Long queryForkliftDataCount(ForkliftEfficientVo forkliftEfficientVo){
		
		return forkliftEfficientQueryDao.queryForkliftDataCount(forkliftEfficientVo);
	}	
	/**
	 *添加叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return List<ForkliftEfficientEntity> 叉车数量信息Entity
	 * @param forkliftEfficientVo 前台页面传递参数 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	@Override
	public void addForkliftData(ForkliftEfficientVo forkliftEfficientVo){
		CurrentInfo user = FossUserContext.getCurrentInfo();
		ForkliftEfficientEntity forkliftEfficientEntity = new ForkliftEfficientEntity();
		if(forkliftEfficientVo.getForkliftEfficientEntity()==null){
			throw new TfrBusinessException("参数为空");   
		}
		ForkliftEfficientEntity forkliftEfficientEntitytemp = forkliftEfficientVo.getForkliftEfficientEntity();

		Date date = new Date();//日期
		forkliftEfficientEntity.setId(UUIDUtils.getUUID());//id
		if(forkliftEfficientEntitytemp.getTransfFieldCode()!=null&&!"".equals(forkliftEfficientEntitytemp.getTransfFieldCode())){
			String transfFieldCode=forkliftEfficientEntitytemp.getTransfFieldCode();
			if(this.queryTransfFieldExist(transfFieldCode)>0){
				throw new TfrBusinessException("该转运场已经存在，请修改");
			}
			forkliftEfficientEntity.setTransfFieldCode(transfFieldCode);//转运场编码
		}
		if(forkliftEfficientEntitytemp.getTransfFieldName()!=null&&!"".equals(forkliftEfficientEntitytemp.getTransfFieldName())){
			forkliftEfficientEntity.setTransfFieldName(forkliftEfficientEntitytemp.getTransfFieldName());//转运场名称
		}
		if(forkliftEfficientEntitytemp.getForkliftCount()!=null){
			forkliftEfficientEntity.setForkliftCount(forkliftEfficientEntitytemp.getForkliftCount());//电叉个数
		}
		forkliftEfficientEntity.setWorkFromDate(date);//生效日期
		forkliftEfficientEntity.setConfigDate(date);//配置日期
		forkliftEfficientEntity.setCreatePerson(user.getEmpName());//创建人
		forkliftEfficientEntity.setCreatePersonCode(user.getEmpCode());//创建人编码
		forkliftEfficientEntity.setCreateDate(date);//创建时间
		forkliftEfficientEntity.setActive("Y");
		
		forkliftEfficientQueryDao.addForkliftData(forkliftEfficientEntity);
		
	}
	/**
	 *查询该转运场是否存在 
	 * @author zenghaibin
	 * @date 2014-7-10 下上午9:50:53
	 * @return long
	 * @param transfFieldCode 转运场编码
	 ***/
	private Long queryTransfFieldExist(String transfFieldCode){
		
		return forkliftEfficientQueryDao.queryTransfFieldExist(transfFieldCode);
	}
	/**
	 *修改叉车数量信息 ，如果已有该转运场数据，并且生效日期不是当天，则需要把该数据的生效截止日期修改为当天，
	 *并新增一条该转运场的电叉数配置数据
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return void
	 * @param forkliftEfficientVo 前台页面传递参数 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	@Override
	@Transactional
	public void updateForkliftData(ForkliftEfficientVo forkliftEfficientVo){
		String type="update";//修改数据时，判断否需要新增,默认为update,不需要新增数据
		CurrentInfo user = FossUserContext.getCurrentInfo();
		ForkliftEfficientEntity forkliftEfficientEntity1 = new ForkliftEfficientEntity();//只需修改时的修改Entity
		ForkliftEfficientEntity forkliftEfficientEntity2 = new ForkliftEfficientEntity();//需要修改并新增时的修改Entity
		ForkliftEfficientEntity forkliftEfficientEntity3 = new ForkliftEfficientEntity();//需要修改并新增时的新增Entity

		if(forkliftEfficientVo.getForkliftEfficientEntity()==null){//参数为空
			throw new TfrBusinessException("参数为空");   
		}
		ForkliftEfficientEntity forkliftEfficientEntitytemp = forkliftEfficientVo.getForkliftEfficientEntity();//页面参数
		Date date = new Date();
		if(forkliftEfficientEntitytemp.getWorkFromDate()!=null){//生效提起不为空
			String workFromDate=DateUtils.convert(forkliftEfficientEntitytemp.getWorkFromDate(),DateUtils.DATE_SHORT_FORMAT);
			String dateTemp = DateUtils.convert(date,DateUtils.DATE_SHORT_FORMAT);
			if(!workFromDate.equals(dateTemp)){//代表需要新增数据，并修改当前数据的截止日期
				type="add";
			}
		}else{
			throw new TfrBusinessException("生效日期为空");
		}
		
		if(type.equals("update")){//只需要修改当天数据
			if(forkliftEfficientEntitytemp.getId()==null||"".equals(forkliftEfficientEntitytemp.getId())){
				
				throw new TfrBusinessException("参数数据异常");
			}else{
				forkliftEfficientEntity1.setId(forkliftEfficientEntitytemp.getId());//id
				if(forkliftEfficientEntitytemp.getTransfFieldName()!=null&&!"".equals(forkliftEfficientEntitytemp.getTransfFieldName())){
					forkliftEfficientEntity1.setTransfFieldName(forkliftEfficientEntitytemp.getTransfFieldName());//转运场名称
				}else{
					throw new TfrBusinessException("转运场不能为空");
				}
				if(forkliftEfficientEntitytemp.getTransfFieldCode()!=null&&!"".equals(forkliftEfficientEntitytemp.getTransfFieldCode())){
					
					forkliftEfficientEntity1.setTransfFieldCode(forkliftEfficientEntitytemp.getTransfFieldCode());//转运场编码
				}else{
					throw new TfrBusinessException("转运场不能为空");
				}
				if(forkliftEfficientEntitytemp.getForkliftCount()!=null){
					forkliftEfficientEntity1.setForkliftCount(forkliftEfficientEntitytemp.getForkliftCount());//电叉个数
				}else{
					throw new TfrBusinessException("电叉个数不能为空");
				}
				forkliftEfficientEntity1.setActive("Y");
				forkliftEfficientEntity1.setModifyPerson(user.getEmpName());//修改人
				forkliftEfficientEntity1.setModifyPersonCode(user.getEmpCode());//修改人编码
				forkliftEfficientEntity1.setModifyDate(date);//修改时间时间
				forkliftEfficientQueryDao.updateForkliftData(forkliftEfficientEntity1);
			}
			
		}else if(type.equals("add")){//新增数据
			
			if(forkliftEfficientEntitytemp.getId()==null||"".equals(forkliftEfficientEntitytemp.getId())){
				throw new TfrBusinessException("参数数据异常");
			}else{
				forkliftEfficientEntity2.setId(forkliftEfficientEntitytemp.getId());//id
				forkliftEfficientEntity2.setWorkToDate(date);//截止日期
				forkliftEfficientEntity2.setModifyPerson(user.getEmpName());//修改人
				forkliftEfficientEntity2.setModifyPersonCode(user.getEmpCode());//修改人编码
				forkliftEfficientEntity2.setModifyDate(date);//修改时间时间
				//作废之前数据，增加截止日期
				forkliftEfficientQueryDao.invalidForkliftData(forkliftEfficientEntity2);
				//新增转运长电叉配置信息
				if(forkliftEfficientEntitytemp.getTransfFieldName()!=null&&!"".equals(forkliftEfficientEntitytemp.getTransfFieldName())){
					forkliftEfficientEntity3.setTransfFieldName(forkliftEfficientEntitytemp.getTransfFieldName());//转运场名称
				}else{
					throw new TfrBusinessException("转运场不能为空");
				}
				if(forkliftEfficientEntitytemp.getTransfFieldCode()!=null&&!"".equals(forkliftEfficientEntitytemp.getTransfFieldCode())){
					
					forkliftEfficientEntity3.setTransfFieldCode(forkliftEfficientEntitytemp.getTransfFieldCode());//转运场编码
				}else{
					throw new TfrBusinessException("转运场不能为空");
				}
				if(forkliftEfficientEntitytemp.getForkliftCount()!=null){
					forkliftEfficientEntity3.setForkliftCount(forkliftEfficientEntitytemp.getForkliftCount());//电叉个数
				}else{
					throw new TfrBusinessException("电叉个数不能为空");
				}

				forkliftEfficientEntity3.setId(UUIDUtils.getUUID());//id
				forkliftEfficientEntity3.setCreatePerson(user.getEmpName());//创建人
				forkliftEfficientEntity3.setCreatePersonCode(user.getEmpCode());//创建人编码
				forkliftEfficientEntity3.setCreateDate(date);//创建时间 
				forkliftEfficientEntity3.setWorkFromDate(date);//生效日期
				forkliftEfficientEntity3.setActive("Y");
				forkliftEfficientEntity3.setConfigDate(date);//配置日期
				forkliftEfficientQueryDao.addForkliftData(forkliftEfficientEntity3);
			}
		}else{
			
			throw new TfrBusinessException("操作异常");
		}
	}
	
	/**
	 *电叉司机数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return List<ForkliftDriverEntity> 叉车司机数据信息
	 **/
	@Override
	public List<ForkliftDriverEntity>  queryForkliftDriverData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start){
		
		List<ForkliftDriverEntity> resultList= new ArrayList<ForkliftDriverEntity>();
		forkliftEfficientVo.setOrgCode(this.queryOrgCode());
		resultList= forkliftEfficientQueryDao.queryForkliftDriverData(forkliftEfficientVo,limit,start);
		return resultList;
	}

	/**
	 *电叉司机数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@param forkliftEfficientVo
	 *@return Long  叉车司机数据数， 分页用
	 **/
	@Override
	public Long queryForkliftDriverDataCount(ForkliftEfficientVo forkliftEfficientVo){
		
		return forkliftEfficientQueryDao.queryForkliftDriverDataCount(forkliftEfficientVo);
	}
	
	/**
	 *转运场数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return List<TransferFieldEntity>
	 **/
	@Override
	public List<TransferFieldEntity> queryTransferFieldData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start){
		
		List<TransferFieldEntity> transferFieldEntityList= new ArrayList<TransferFieldEntity>();
		transferFieldEntityList=forkliftEfficientQueryDao.queryTransferFieldData(forkliftEfficientVo,limit,start);
		if(transferFieldEntityList!=null&&transferFieldEntityList.size()>0){
			for(int i=0;i<transferFieldEntityList.size();i++){
				//每日车均票数=每日叉车票数除以每日电叉个数
				if(transferFieldEntityList.get(i).getDayCarCount()==null||transferFieldEntityList.get(i).getForkliftCount()==null
						||transferFieldEntityList.get(i).getForkliftCount().compareTo(new BigDecimal("0"))==0){
					transferFieldEntityList.get(i).setDayCarEfficiency("0");//如果每日叉车个数为0则车均票数为0
				}else{
					transferFieldEntityList.get(i).setDayCarEfficiency(transferFieldEntityList.get(i).getDayCarCount()
							.divide(transferFieldEntityList.get(i).getForkliftCount(), ConstantsNumberSonar.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP).toString());
				}
				//每月车票数：每月叉车票总送除以每月电叉总数
				if(transferFieldEntityList.get(i).getMonthCarCount()==null||transferFieldEntityList.get(i).getSumCount()==null
						||transferFieldEntityList.get(i).getSumCount().compareTo(new BigDecimal("0"))==0){
					transferFieldEntityList.get(i).setMonthCarEfficiency("0");//如果一个月电叉总量为0则为车均票数为0
				}else{
				transferFieldEntityList.get(i).setMonthCarEfficiency(transferFieldEntityList.get(i).getMonthCarCount()
						 .divide(transferFieldEntityList.get(i).getSumCount(), ConstantsNumberSonar.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP).toString());
				}
			}
		}
		
		
		return transferFieldEntityList;
	}

	/**
	 *转运场数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@param forkliftEfficientVo 参数数据
	 *@return Long 分页用
	 **/
	@Override
	public Long queryTransferFieldDataCount(ForkliftEfficientVo forkliftEfficientVo){
		return forkliftEfficientQueryDao.queryTransferFieldDataCount(forkliftEfficientVo);
	}
	
	/**
	 * @author niuly
	 * @date 2014-7-18下午3:16:36
	 * @function 查询叉车效率
	 * @return
	 */
	@Override
	public String queryForkliftEffiency() throws Exception{
		String exceptionInfo = "forkliftefficiency exception";
		try {
		Map<String,String> map = forkliftEfficientQueryDao.queryForkliftEfficiency();
		if(null != map && StringUtils.isNotEmpty(map.get("exceptionInfo"))) {
			exceptionInfo = map.get("exceptionInfo");
			LOGGER.info("叉车效率统计异常信息：" + exceptionInfo);
			} 
//		map = null;
//		map.get("a");
		}	catch (Exception e) {
			e.printStackTrace();
			throw new Exception(exceptionInfo);
		}
		return exceptionInfo;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setForkliftEfficientQueryDao(
			IForkliftEfficientQueryDao forkliftEfficientQueryDao) {
		this.forkliftEfficientQueryDao = forkliftEfficientQueryDao;
	}
	
	
}
