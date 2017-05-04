package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendExpressDeliveryRegionsInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressDeliveryRegionsException;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递派送区域的service实现类
 * 
 * @author 130566
 * 
 */
public class ExpressDeliveryRegionsService implements
		IExpressDeliveryRegionsService {
	
	private IExpressDeliveryRegionsDao expressDeliveryRegionsDao;
	/**
	 * 组织部门service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 数据字典Service
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
		
	/**
	 * 同步数据到官网
	 */
	private ISendExpressDeliveryRegionsInfoService syncExpressDeliveryRegionsInfoService;
	
	/*private IDataDictionaryService dataDictionaryService;
	
	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}*/
	public void setSyncExpressDeliveryRegionsInfoService(
			ISendExpressDeliveryRegionsInfoService syncExpressDeliveryRegionsInfoService) {
		this.syncExpressDeliveryRegionsInfoService = syncExpressDeliveryRegionsInfoService;
	}
	
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setExpressDeliveryRegionsDao(
			IExpressDeliveryRegionsDao expressDeliveryRegionsDao) {
		this.expressDeliveryRegionsDao = expressDeliveryRegionsDao;
	}
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * <P>
	 * 新增实体（从行政区域接口新增，调用该方法）-AC项目需求：快递派送区域界面无新增删除操作，
	 *        故不存在新增时调用快递派送属性、派件区域和
	 *        特殊区域的优化规则-dujunhui
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午2:00:22
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public ExpressDeliveryRegionsEntity addExpressDeliveryRegions(
			ExpressDeliveryRegionsEntity entity) {
		// 合法性检查
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			throw new ExpressDeliveryRegionsException("传入的参数为空!");
		}
		// 判断插入的对象，库中是否存在
		ExpressDeliveryRegionsEntity entity2 = expressDeliveryRegionsDao
				.queryExpressDeliveryRegionsEntityByCode(entity.getCode());
		if (entity2 != null) {
			throw new ExpressDeliveryRegionsException("库中已经存在该区域，无法新增");
		}
		// 若父级行政区域编码不为空
		if (StringUtils.isNotBlank(entity.getParentDistrictCode())) {
			// 设置父级区域名称
			entity.setParentDistrictName(queryexpressDeliveryRegionsNameByCode(entity
					.getParentDistrictCode()));
		}
		//加上营业部名称
		this.attachName(entity);
		// 实行新增操作
		ExpressDeliveryRegionsEntity addEntity= expressDeliveryRegionsDao.addExpressDeliveryRegions(entity);
		//调用新增的优化规则187862-dujunhui
//		if(StringUtils.equals(entity.getDegree(), DictionaryValueConstants.TOWN_STREET_AGENCY)){
//			optimizeParentDeliveryNature(entity);
//			optimizeParentDeliveryNature(this.queryExpressDeliveryRegionsByCode(entity.getParentDistrictCode()));
//		}
		if(addEntity!=null){
			//推送至CRM
			List<ExpressDeliveryRegionsEntity> syncEntitys = new ArrayList<ExpressDeliveryRegionsEntity>();
			syncEntitys.add(addEntity);
			synRegionsToCRM(syncEntitys, FossConstants.SYNCCRM_ADD);
		}
		//TODO
		if(null !=addEntity && StringUtils.equals(addEntity.getDegree(), "DISTRICT_COUNTY")){
			List<ExpressDeliveryRegionsEntity> entitys=new ArrayList<ExpressDeliveryRegionsEntity>();
			
			if(StringUtils.equals(addEntity.getDeliveryNature(), "DELIVERY_NATURE_ZTBPS")){
				addEntity.setRemark("抱歉，此区域暂时无法到达，请谅解 ！");
				addEntity.setRemarkDe("抱歉，此区域暂时未开展发货业务，请谅解 ！");
				
			}else if(StringUtils.isEmpty(addEntity.getRemark())){
				DataDictionaryValueEntity valueEntity=
						dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode("DELIVERY_NATURE",addEntity.getDeliveryNature());
				//数据字典非空校验
				if(null !=valueEntity){
					addEntity.setRemark(valueEntity.getValueName());
				}
				
			}
			entitys.add(addEntity);
			synRegionsToGW(entitys,ComnConst.SERVICE_OPERATE_ADD);
			
		}
		return addEntity;
	}
	/**
	 * <P>
	 * 作废操作（无方法调用，可废弃）-AC项目需求：快递派送区域界面无新增删除操作
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午3:56:42
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public ExpressDeliveryRegionsEntity deleteExpressDeliveryRegions(
			ExpressDeliveryRegionsEntity entity) {
		// 非空判断
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			throw new ExpressDeliveryRegionsException("传入的参数为空");
		}
		
		List<ExpressDeliveryRegionsEntity> entitys = new ArrayList<ExpressDeliveryRegionsEntity>();
		ExpressDeliveryRegionsEntity es = expressDeliveryRegionsDao.deleteExpressDeliveryRegions(entity);
		if(es!=null){
			entitys.add(entity);
			//推送CRM
			synRegionsToCRM(entitys, FossConstants.SYNCCRM_DELETE);
		}
		return es;
	}
	/**
	 * <P>
	 * 更新操作
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:20:10
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public ExpressDeliveryRegionsEntity updateExpressDeliveryRegions(
			ExpressDeliveryRegionsEntity entity) {
		// 非空行检查
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			throw new ExpressDeliveryRegionsException("传入的参数为空");
		}
		// 查询更新之前，库中的记录
		ExpressDeliveryRegionsEntity entity2 = expressDeliveryRegionsDao
				.queryExpressDeliveryRegionsEntityByCode(entity.getCode());

		if (StringUtils.isNotBlank(entity.getParentDistrictCode())) {
			// 设置父级名称
			entity.setParentDistrictName(queryexpressDeliveryRegionsNameByCode(entity
					.getParentDistrictCode()));
		}
		//加上营业部名称
		this.attachName(entity);
		// 执行修改操作
		ExpressDeliveryRegionsEntity result = expressDeliveryRegionsDao
				.updateExpressDeliveryRegions(entity);
		if (null == result) {
			return null;
		}
		// 判断更新前后，区域名称是否发生改变
		if (null != entity2
				&& !(StringUtils.equals(entity2.getName(), result.getName()))) {
			// 若修改了实体名称，则下级节点中的上级名称也要改变
			this.updateChildRegionsByParentRegins(result);
		}
		//优化：准备两实体用于派送属性优化过程中对当前修改实体的上级派送属性的修改，推送给CRM等系统
		ExpressDeliveryRegionsEntity countyEntity=null;
		ExpressDeliveryRegionsEntity cityEntity=null;
		//提取共用查询（供派件区域和特殊区域规则优化）
		if(null != entity2 && (!StringUtil.equals(entity2.getDeliveryNature(), result.getDeliveryNature())
				||(StringUtil.equals(result.getDegree(), "TOWN_STREET_AGENCY")
				&& (!StringUtil.equals(entity2.getSpecialArea(), result.getSpecialArea()))))){
			String parentCode=result.getParentDistrictCode();
			ExpressDeliveryRegionsEntity parentEntity= this.queryExpressDeliveryRegionsByCode(parentCode);
			//上一级的派送派件区域
			String premark =parentEntity.getRemark();
			List<ExpressDeliveryRegionsEntity> childEntities=this.queryByParentDistrictCode(parentCode);
			
			//调用新增的优化规则187862-dujunhui
			if(!StringUtil.equals(entity2.getDeliveryNature(), result.getDeliveryNature())){//派送属性作了修改则调用优化方法
				//修改上级派送属性
				countyEntity=optimizeParentDeliveryNature(result);
				cityEntity=optimizeParentDeliveryNature(this.queryExpressDeliveryRegionsByCode(result.getParentDistrictCode()));
				//快递派送区域完善需求20151126-187862
				//修改上级派件区域
				//String parentRemark1="【自有全境】：";
				StringBuffer parentRemark1 = new StringBuffer();
				StringBuffer parentRemark12 = new StringBuffer();
				parentRemark1.append("【自有全境】：");
				//String parentRemark2="【村组自提】：";
				int i=0;
				//int j=0;//统计全境与发全境派送的子节点个数
				if(CollectionUtils.isNotEmpty(childEntities)){
					for(ExpressDeliveryRegionsEntity childEntity:childEntities){
						if(StringUtil.equals(childEntity.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_QJPS) ){
							//parentRemark1=parentRemark1+childEntity.getName()+"，";
							parentRemark1.append(childEntity.getName());
							parentRemark1.append("，");
							i++;
						}
						/*if(StringUtil.equals(childEntity.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_FQJPS) ){
							parentRemark2=parentRemark2+childEntity.getName()+"，";
							j++;
						}*/
					}
					if(i>0){//全境派送四级地址截掉最后一个逗号
					//	parentRemark1=parentRemark1.substring(0, parentRemark1.length()-1)+" ";
						parentRemark12.append(parentRemark1.toString().substring(0, parentRemark1.toString().length()-1));
						parentRemark12.append(" ");
					}else{
					//	parentRemark1="";
						parentRemark12.append("");
					}
					/*if(j>0){//发全境派送四级地址截掉最后一个逗号
						parentRemark2=parentRemark2.substring(0, parentRemark2.length()-1);
					}else{
						parentRemark2="";
					}*/
				}
				parentEntity.setRemark(parentRemark12.toString());//派件区域拼接
				if(parentEntity.getRemark().length()>NumberConstants.NUMBER_666){//字段截取防止数据库无法插入
					parentEntity.setRemark(parentEntity.getRemark().substring(0, NumberConstants.NUMBER_666));
				}
				//派件区域添加手动修改内容,分号后面默认是手动添加内容
				String str2="";
				if(StringUtil.isNotBlank(premark)){
					parentEntity=getParentEntity(parentEntity, premark, str2);
				}
				
			}
			//313353 sonar
			this.sonarSplitOne(result, entity2, childEntities, parentEntity);
			
			//提取共用部分
			countyEntity=expressDeliveryRegionsDao.updateExpressDeliveryRegions(parentEntity);//修改上级派件区域和（或）特殊区域
		}
		
		//313353 sonar
		this.sonarSplitTwo(result, cityEntity, countyEntity);
		
		//TODO
		//313353 sonar
		this.sonarSplitThree(result);
		
		return result;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(ExpressDeliveryRegionsEntity result, ExpressDeliveryRegionsEntity entity2,
			List<ExpressDeliveryRegionsEntity> childEntities, ExpressDeliveryRegionsEntity parentEntity) {
		//快递派送区域完善需求20151126-187862
		if(StringUtil.equals(result.getDegree(), "TOWN_STREET_AGENCY")
				&& (!StringUtil.equals(entity2.getSpecialArea(), result.getSpecialArea()))){//四级地址特殊区域作了修改则调用优化方法
			//修改上级特殊区域
			//String parentSpecialArea="";//特殊区域汇总字段
			StringBuffer parentSpecialArea = new StringBuffer();
			StringBuffer parentSpecialAreaa = new StringBuffer();
			int i=0;//统计特殊区域的子节点个数
			if(CollectionUtils.isNotEmpty(childEntities)){
				for(ExpressDeliveryRegionsEntity childEntity:childEntities){
					if(StringUtil.isNotEmpty(childEntity.getSpecialArea())){
						//parentSpecialArea=parentSpecialArea+childEntity.getSpecialArea()+"；";
						parentSpecialArea.append(childEntity.getSpecialArea());
						parentSpecialArea.append("；");
						i++;
					}
				}
				if(i>0){//特殊区域字段四级地址截掉最后一个分号
					//parentSpecialArea=parentSpecialArea.substring(0, parentSpecialArea.length()-1);
					parentSpecialAreaa.append(parentSpecialArea.toString().substring(0, parentSpecialArea.toString().length()-1));
				}else{
					//parentSpecialArea="";
					parentSpecialAreaa.append("");
				}
			}
			//parentEntity.setSpecialArea(parentSpecialArea);//特殊区域拼接
			parentEntity.setSpecialArea(parentSpecialAreaa.toString());//特殊区域拼接
			if(parentEntity.getSpecialArea().length()>NumberConstants.NUMBER_1333){//字段截取防止数据库无法插入
				parentEntity.setSpecialArea(parentEntity.getSpecialArea().substring(0, NumberConstants.NUMBER_1333));
			}
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitTwo(ExpressDeliveryRegionsEntity result, ExpressDeliveryRegionsEntity cityEntity,
			ExpressDeliveryRegionsEntity countyEntity) {
		if(result!=null){//优化：将CRM与GIS的接口提前推送，防止给官网的收派件区域接口影响字段值-187862
			List<ExpressDeliveryRegionsEntity> es=new ArrayList<ExpressDeliveryRegionsEntity>();
			es.add(result);
			if(cityEntity!=null && countyEntity!=null){//只要派送属性修改就给下游推送对应上级实体
				es.add(cityEntity);
				es.add(countyEntity);
			}
			synRegionsToCRM(es, FossConstants.SYNCCRM_UPDATE);
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitThree(ExpressDeliveryRegionsEntity result) {
		if(null != result && StringUtils.equals(result.getDegree(), "DISTRICT_COUNTY")){
			List<ExpressDeliveryRegionsEntity> entitys=new ArrayList<ExpressDeliveryRegionsEntity>();
			
			if(StringUtils.equals(result.getDeliveryNature(), "DELIVERY_NATURE_ZTBPS")){
				result.setRemark("抱歉，此区域暂时无法到达，请谅解 ！");
				result.setRemarkDe("抱歉，此区域暂时未开展发货业务，请谅解 ！");
				
			}else if(StringUtils.isEmpty(result.getRemark())){
				DataDictionaryValueEntity valueEntity=
						dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode("DELIVERY_NATURE",result.getDeliveryNature());
				if(null !=valueEntity){
					result.setRemark(valueEntity.getValueName());
				}
			}
			entitys.add(result);
			synRegionsToGW(entitys,ComnConst.SERVICE_OPERATE_MODIFY);
		}
	}

	private ExpressDeliveryRegionsEntity getParentEntity(ExpressDeliveryRegionsEntity parentEntity,
			String premark, String str2) {
		String[] str =premark.split(";");
		StringBuilder bld = new StringBuilder();
		if(str!=null && str.length>=0){
			for(int l=0;l<str.length;l++){
				if(l==0){
					continue;
				}
				if(bld.length() == 0){
					bld.append(str[l]);
				}else{
					bld.append(";").append(str[l]);
				}
				
			}
			str2 = bld.toString();
			if(StringUtil.isNotBlank(str2)){
				parentEntity.setRemark(parentEntity.getRemark()+";"+str2);//派件区域拼接remark
			}else{
				parentEntity.setRemark(parentEntity.getRemark());//派件区域拼接remark
			}
			
		}
		return parentEntity;
	}
	/**
	 *<P>根据父节点 更新子节点上级区域名称的方法<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-26上午8:43:27
	 * @param result
	 */
	private void updateChildRegionsByParentRegins(ExpressDeliveryRegionsEntity result) {

		//获取给节点的子区域
		List<ExpressDeliveryRegionsEntity> results = queryByParentDistrictCode(result.getCode());
		//若子节点集合不为空
		if(!CollectionUtils.isEmpty(results)){
			for (ExpressDeliveryRegionsEntity entity : results) {
				//设置父级节点的名称和编码
				entity.setParentDistrictName(result.getName());
				entity.setParentDistrictCode(result.getCode());
				expressDeliveryRegionsDao.updateExpressDeliveryRegions(entity);
			}
		}		
	}
	/**
	 *<P>根据codes 批量作废区域（界面不可删除，故可废弃）<P>-AC项目需求：快递派送区域界面无新增删除操作
	 * @author :130566-zengJunfan
	 * @date : 2014-2-26下午4:23:20
	 * @param codes
	 * @param deleteUser
	 */
	@Override
	public int deleteRegionsByCodes(String[] codes, String deleteUser) {
		//非空验证
		if(codes.length ==0 ||StringUtils.isBlank(deleteUser)){
			throw new ExpressDeliveryRegionsException("传入的参数不能为空");
		}
		//调用新增的优化规则187862-dujunhui
		for(int i=0;i<codes.length;i++){
			ExpressDeliveryRegionsEntity result=this.queryExpressDeliveryRegionsByCode(codes[i]);
			ExpressDeliveryRegionsEntity parentEntity= this.queryExpressDeliveryRegionsByCode(result.getParentDistrictCode());
			if(StringUtils.equals(result.getDegree(), DictionaryValueConstants.TOWN_STREET_AGENCY)){
				//删除时使用的优化规则，传入的实体为TOWN_STREET_AGENCY一级
				optimizeParentDeliveryNatureWhenDel(result);
				//更新时使用的优化规则，传入的实体为DISTRICT_COUNTY一级
				optimizeParentDeliveryNature(parentEntity);
				
				//快递派送区域完善需求20151126-187862
				//修改上级派件区域
				String parentCode=result.getParentDistrictCode();
				//String parentRemark1="【自有全境】：";
				StringBuffer parentRemark1 = new StringBuffer();
				parentRemark1.append("【自有全境】：");
				StringBuffer parentRemark11 = new StringBuffer();
				//String parentRemark2="【村组自提】：";
				//String parentSpecialArea="";//特殊区域汇总字段
				StringBuffer parentSpecialArea = new StringBuffer();//特殊区域汇总字段
				StringBuffer parentSpecialAreaa = new StringBuffer();
				int m=0;
				//int n=0;//统计全境与发全境派送的子节点个数
				int k=0;//统计特殊区域的子节点个数
				List<ExpressDeliveryRegionsEntity> childEntities=this.queryByParentDistrictCode(parentCode);
				if(CollectionUtils.isNotEmpty(childEntities)){
					for(ExpressDeliveryRegionsEntity childEntity:childEntities){
						if(!StringUtil.equals(childEntity.getCode(), result.getCode()) 
								&& StringUtil.equals(childEntity.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_QJPS) ){
							//parentRemark1=parentRemark1+childEntity.getName()+"，";
							parentRemark1.append(childEntity.getName());
							parentRemark1.append("，");
							m++;
						}
						/*if(!StringUtil.equals(childEntity.getCode(), result.getCode()) 
								&& StringUtil.equals(childEntity.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_FQJPS) ){
							parentRemark2=parentRemark2+childEntity.getName()+"，";
							n++;
						}*/
						if(!StringUtil.equals(childEntity.getCode(), result.getCode()) ){
							//parentSpecialArea=parentSpecialArea+childEntity.getSpecialArea()+"；";
							parentSpecialArea.append(childEntity.getSpecialArea());
							parentSpecialArea.append("；");
							k++;
						}
					}
				}
				if(m>0){//全境派送四级地址截掉最后一个逗号
					//parentRemark1=parentRemark1.substring(0, parentRemark1.length()-1)+" ";
					parentRemark11.append(parentRemark1.toString().substring(0, parentRemark1.toString().length()-1));
					parentRemark11.append(" ");
				}else{
					//parentRemark1="";
					parentRemark11.append("");
				}
				/*if(n>0){//发全境派送四级地址截掉最后一个逗号
					parentRemark2=parentRemark2.substring(0, parentRemark2.length()-1);
				}else{
					parentRemark2="";
				}*/
				if(k>0){//特殊区域字段四级地址截掉最后一个分号
					//parentSpecialArea=parentSpecialArea.substring(0, parentSpecialArea.length()-1);
					parentSpecialAreaa.append(parentSpecialArea.toString().substring(0, parentSpecialArea.toString().length()-1));
				}else{
					//parentSpecialArea="";
					parentSpecialAreaa.append("");
				}
				//parentEntity.setRemark(parentRemark1);//派件区域拼接
				parentEntity.setRemark(parentRemark11.toString());//派件区域拼接
				//parentEntity.setSpecialArea(parentSpecialArea);//特殊区域拼接
				parentEntity.setSpecialArea(parentSpecialAreaa.toString());//特殊区域拼接
				if(parentEntity.getRemark().length()>NumberConstants.NUMBER_666){//字段截取防止数据库无法插入
					parentEntity.setRemark(parentEntity.getRemark().substring(0, NumberConstants.NUMBER_666));
				}
				if(parentEntity.getSpecialArea().length()>NumberConstants.NUMBER_1333){//字段截取防止数据库无法插入
					parentEntity.setSpecialArea(parentEntity.getSpecialArea().substring(0, NumberConstants.NUMBER_1333));
				}
				expressDeliveryRegionsDao.updateExpressDeliveryRegions(parentEntity);//修改上级派件区域
			}
		}
		
		syncDeleteExpressDeliveryRegionsToCRM(codes);
		return expressDeliveryRegionsDao.deleteRegionsByCodes(codes,deleteUser);
	}
	
	/**
	 * <P>
	 * 根据code 获取Name
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:04:33
	 * @param code
	 * @return
	 */
	@Override
	public String queryexpressDeliveryRegionsNameByCode(String code) {
		// 非空检查
		if (StringUtils.isBlank(code)) {
			return null;
		}
		ExpressDeliveryRegionsEntity entity = expressDeliveryRegionsDao
				.queryExpressDeliveryRegionsEntityByCode(code);
		// 得到的实体的非空检查
		if (null == entity) {
			throw new ExpressDeliveryRegionsException("根据code获取的数据,库中不存在！");
		}
		return entity.getName();
	}
	/**
	 * <P> 根据条件分页查询 <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:36:57
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<ExpressDeliveryRegionsEntity> queryExpressDeliveryRegionsEntities(
			ExpressDeliveryRegionsEntity entity, int start, int limit) {
		// 若实体对象为空，实例化一个对象
		if (null == entity) {
			entity = new ExpressDeliveryRegionsEntity();
		}
		List<ExpressDeliveryRegionsEntity> resultList =expressDeliveryRegionsDao.queryExpressDeliveryRegionsList(
				entity, start, limit);
		//判断该code节点是否叶子节点--187862-dujunhui
		if(CollectionUtils.isNotEmpty(resultList)){
			for(ExpressDeliveryRegionsEntity eachEntity:resultList){//循环封装，是否叶子节点
				String childCode=this.queryMaxCodeChildRegions(eachEntity.getCode());
				if(StringUtil.isEmpty(childCode)){
					eachEntity.setIsLeaf("Y");
				}else{
					eachEntity.setIsLeaf("N");
				}
			}
		}
		//给区域加上部门名称
		return attachName(resultList);
	}
	/**
	 * <P> 查询总数<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:43:20
	 * @param entity
	 * @return
	 */
	@Override
	public long queryCount(ExpressDeliveryRegionsEntity entity) {
		// 若实体对象为空，实例化一个对象
		if (null == entity) {
			entity = new ExpressDeliveryRegionsEntity();
		}
		return expressDeliveryRegionsDao.queryExpressDeliveryRegionsCount(entity);
	}
	/**
	 *<P>根据code来查询实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:53:39
	 * @param code
	 * @return
	 */
	@Override
	public ExpressDeliveryRegionsEntity queryExpressDeliveryRegionsByCode(
			String code) {
		//非空验证
		if(StringUtils.isBlank(code)){
			throw new ExpressDeliveryRegionsException("传入参数为空");
		}
		//查询实体
		ExpressDeliveryRegionsEntity result =expressDeliveryRegionsDao.queryExpressDeliveryRegionsEntityByCode(code);
		//设置名称
		return this.attachName(result);
	}
	/**
	 *<P>查询根节点<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午5:49:30
	 * @return
	 */
	@Override
	public List<ExpressDeliveryRegionsEntity> queryRoot() {
		return expressDeliveryRegionsDao.queryRoot();
	}
	/**
	 *<P>根据上级行政区域编码查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-18上午9:02:03
	 * @param parentDistrictCode
	 * @return
	 */
	@Override
	public List<ExpressDeliveryRegionsEntity> queryByParentDistrictCode(
			String parentDistrictCode) {
		//非空验证
		if(StringUtils.isBlank(parentDistrictCode)){
			throw new ExpressDeliveryRegionsException("传入的上级行政区域编码为空！");
		}
		//查询
		return expressDeliveryRegionsDao.queryExpressDeliveryRegionsByParentDistrictCode(parentDistrictCode);
	}
	/**
	 *<P>根据上级编码，查询子节点中的最大编码<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-24上午11:08:39
	 * @param parentDistrictCode
	 * @return
	 */
	@Override
	public String queryMaxCodeChildRegions(
			String parentDistrictCode) {
		if(StringUtils.isBlank(parentDistrictCode)){
			throw new ExpressDeliveryRegionsException("传入的上级行政区域编码为空！");
		}
		return expressDeliveryRegionsDao.queryMaxCodeChildRegions(parentDistrictCode);
	}
	/**
	 * 以下为工具方法
	 */
	/**
	 * <P>
	 * 给区域列表加上名称
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-20上午11:08:51
	 * @param list
	 * @return
	 */
	private List<ExpressDeliveryRegionsEntity> attachName(
			List<ExpressDeliveryRegionsEntity> list) {
		//非空验证
		if (CollectionUtils.isEmpty(list)) {
			return list;
		}
		for (ExpressDeliveryRegionsEntity expressDeliveryRegionsEntity : list) {
			this.attachName(expressDeliveryRegionsEntity);
		}
		return list;
	}
	/**
	 * <P>
	 * 该区域加上"部门"名称
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-20上午10:57:39
	 * @param entity
	 * @return
	 */
	private ExpressDeliveryRegionsEntity attachName(
			ExpressDeliveryRegionsEntity entity) {
		// 若实体为空或者所属快递营业部编码，返回实体
		if (null == entity
				|| StringUtils.isBlank(entity.getExpressSalesDeptCode())) {
			return entity;
		}
		// 若所属快递营业部名称为空，设置名称
		if (StringUtils.isEmpty(entity.getExpressSalesDeptName())) {
			String name = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoNameByCode(entity
							.getExpressSalesDeptCode());
			entity.setExpressSalesDeptName(name);
		}
		return entity;
	}
	/**
	 * 特殊方法，
	 */
	/**
	 *<P>用于更新删除行政区域中的节点，同步更新快递派送区域的节点和子节点<P>-需调用快递派送属性、
	 *		     派件区域和特殊区域的优化规则-dujunhui
	 * @author :130566-zengJunfan
	 * @date : 2014-2-28上午10:02:43
	 * @param codes
	 * @param deleteUser
	 * @return
	 */
	@Override
	public int syncDeleteExpressDeliveryRegions(
			String[] codes, String deleteUser) {
		//先定义一个集合
		List<String> list =new ArrayList<String>();
		
		//313353 sonar优化
		this.sonarSplit(codes, deleteUser, list);
		
		//获取list
		String[] arrCodes =list.toArray(new String[list.size()]);
		List<ExpressDeliveryRegionsEntity> regionsEntities=expressDeliveryRegionsDao.queryDeleteRegions(arrCodes);
		//删除操作
		//优化：准备两实体用于派送属性优化过程中对当前删除实体的上级派送属性的修改，推送给CRM等系统
		ExpressDeliveryRegionsEntity countyEntity=null;
		ExpressDeliveryRegionsEntity cityEntity=null;
		//调用新增的优化规则187862-dujunhui
		for(int i=0;i<codes.length;i++){
			ExpressDeliveryRegionsEntity result=this.queryExpressDeliveryRegionsByCode(codes[i]);
			//313353   空指针异常修复
			if(result == null){
				continue;
			}
			ExpressDeliveryRegionsEntity parentEntity= this.queryExpressDeliveryRegionsByCode(result.getParentDistrictCode());
			//313353   空指针异常修复
			if(parentEntity == null){
				continue;
			}
			if(StringUtils.equals(result.getDegree(), DictionaryValueConstants.TOWN_STREET_AGENCY)){//作废镇街道级别才优化-187862
				//删除时使用的优化规则，传入的实体为TOWN_STREET_AGENCY一级
				countyEntity=optimizeParentDeliveryNatureWhenDel(result);
				//更新时使用的优化规则，传入的实体为DISTRICT_COUNTY一级
				cityEntity=optimizeParentDeliveryNature(parentEntity);
				
				//快递派送区域完善需求20151126-187862
				//修改上级派件区域
				String parentCode=result.getParentDistrictCode();
				//String parentRemark1="【自有全境】：";
				StringBuffer parentRemark1 = new StringBuffer();
				parentRemark1.append("【自有全境】：");
				StringBuffer parentRemark11 = new StringBuffer();
				//String parentRemark2="【村组自提】：";
				//String parentSpecialArea="";//特殊区域汇总字段
				StringBuffer parentSpecialArea = new StringBuffer();
				StringBuffer parentSpecialAreaa = new StringBuffer();
				int m=0;
				//int n=0;//统计全境与发全境派送的子节点个数
				int k=0;//统计特殊区域的子节点个数
				List<ExpressDeliveryRegionsEntity> childEntities=this.queryByParentDistrictCode(parentCode);
				if(CollectionUtils.isNotEmpty(childEntities)){
					for(ExpressDeliveryRegionsEntity childEntity:childEntities){
						if(!StringUtil.equals(childEntity.getCode(), result.getCode()) 
								&& StringUtil.equals(childEntity.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_QJPS) ){
							//parentRemark1=parentRemark1+childEntity.getName()+"，";
							parentRemark1.append(childEntity.getName());
							parentRemark1.append("，");
							m++;
						}
						/*if(!StringUtil.equals(childEntity.getCode(), result.getCode()) 
								&& StringUtil.equals(childEntity.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_FQJPS) ){
							parentRemark2=parentRemark2+childEntity.getName()+"，";
							n++;
						}*/
						if(!StringUtil.equals(childEntity.getCode(), result.getCode()) 
								&& StringUtil.isNotEmpty(childEntity.getSpecialArea())){
							//parentSpecialArea=parentSpecialArea+childEntity.getSpecialArea()+"；";
							parentSpecialArea.append(childEntity.getSpecialArea());
							parentSpecialArea.append("；");
							k++;
						}
					}
				}
				if(m>0){//全境派送四级地址截掉最后一个逗号
					//parentRemark1=parentRemark1.substring(0, parentRemark1.length()-1)+" ";
					parentRemark11.append(parentRemark1.toString().substring(0, parentRemark1.toString().length()-1));
					parentRemark11.append(" ");
				}else{
					//parentRemark1="";
					parentRemark11.append("");
				}
				/*if(n>0){//发全境派送四级地址截掉最后一个逗号
					parentRemark2=parentRemark2.substring(0, parentRemark2.length()-1);
				}else{
					parentRemark2="";
				}*/
				if(k>0){//特殊区域字段四级地址截掉最后一个分号
					//parentSpecialArea=parentSpecialArea.substring(0, parentSpecialArea.length()-1);
					parentSpecialAreaa.append(parentSpecialArea.toString().substring(0, parentSpecialArea.toString().length()-1));
				}else{
					//parentSpecialArea="";
					parentSpecialAreaa.append("");
				}
				//parentEntity.setRemark(parentRemark1);//派件区域拼接
				//parentEntity.setSpecialArea(parentSpecialArea);//特殊区域拼接
				parentEntity.setRemark(parentRemark11.toString());//派件区域拼接
				parentEntity.setSpecialArea(parentSpecialAreaa.toString());//特殊区域拼接
				if(parentEntity.getRemark().length()>NumberConstants.NUMBER_666){//字段截取防止数据库无法插入
					parentEntity.setRemark(parentEntity.getRemark().substring(0, NumberConstants.NUMBER_666));
				}
				if(parentEntity.getSpecialArea().length()>NumberConstants.NUMBER_1333){//字段截取防止数据库无法插入
					parentEntity.setSpecialArea(parentEntity.getSpecialArea().substring(0, NumberConstants.NUMBER_1333));
				}
				countyEntity=expressDeliveryRegionsDao.updateExpressDeliveryRegions(parentEntity);//修改上级派件区域
			}
		}
		int num=expressDeliveryRegionsDao.deleteRegionsByCodes(arrCodes, deleteUser);
		if(regionsEntities.size()>0){
			synRegionsToGW(regionsEntities,ComnConst.SERVICE_OPERATE_DELETE);
		}
		if(cityEntity!=null && countyEntity!=null){//只要删除镇街道，则其派送属性变动就给下游推送对应上级实体
			List<ExpressDeliveryRegionsEntity> es=new ArrayList<ExpressDeliveryRegionsEntity>();
			es.add(cityEntity);
			es.add(countyEntity);
			synRegionsToCRM(es, FossConstants.SYNCCRM_UPDATE);
		}
		syncDeleteExpressDeliveryRegionsToCRM(codes);
		return num;
		 //TODO
	}
	
	//313353 sonar优化
	private void sonarSplit(String[] codes, String deleteUser, List<String> list){
		if(codes.length ==0 ||StringUtils.isBlank(deleteUser)){
			throw new ExpressDeliveryRegionsException("传入的参数不能为空");
		}
		for(int i=0;i<codes.length;i++){
			list.add(codes[i]);
			list.addAll(queryChildNode(codes[i]));
		}
	}
	
	private void syncDeleteExpressDeliveryRegionsToCRM(String[] codes){ 
		if(codes==null||codes.length==0){
			throw new ExpressDeliveryRegionsException("传入的参数不能为空");	
		}
		List<ExpressDeliveryRegionsEntity> es=new ArrayList<ExpressDeliveryRegionsEntity>();
		ExpressDeliveryRegionsEntity entity = null;
		//推送至crm
		for(String code:codes){
			entity = new ExpressDeliveryRegionsEntity();
			entity.setCode(code);
			es.add(entity);
		}
		synRegionsToCRM(es, FossConstants.SYNCCRM_DELETE);
	}
	
	/**
	 *<P>查询该节点的所有下级节点<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-28上午10:14:33
	 * @param results
	 * @return
	 */
	private List<String> queryChildNode(String code) {
		List<String> list =new ArrayList<String>();
		List<ExpressDeliveryRegionsEntity> results =queryByParentDistrictCode(code);
		//若不为空的话
		if(CollectionUtils.isNotEmpty(results)){
			for (ExpressDeliveryRegionsEntity entity : results) {
				list.add(entity.getCode());
				list.addAll(queryChildNode(entity.getCode()));
			}
		}
		return list;
	}
	/**
	 * 特殊方法，用于公共选择器，
	 */
	/**
	 *<P>分页查询实体列表，加上界别节点，<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-8上午10:14:30
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<ExpressDeliveryRegionsEntity> queryRegionsListAttachDegreeName(
			ExpressDeliveryRegionsEntity entity, int start, int limit) {
		//查询实体列表，
		List<ExpressDeliveryRegionsEntity> resultList =this.queryExpressDeliveryRegionsEntities(entity, start, limit);
		//当实体列表不为空时，
		if(CollectionUtils.isNotEmpty(resultList)){
			//查询数据字典的界别名称
			List<DataDictionaryValueEntity> values = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.DISTRICT_DEGREE);
			//设置操作行政级别的级别名称
			if (CollectionUtils.isNotEmpty(values)) {
				for (int i = 0; i < resultList.size(); i++) {
					for (int j = 0; j < values.size(); j++) {
						if (!StringUtil.isEmpty(resultList.get(i).getDegree()) && resultList.get(i).getDegree().equals(values.get(j).getValueCode())) {
							resultList.get(i).setDegreeName(values.get(j).getValueName());
						}
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * <P>
	 * 新增优化规则（新增或修改时调用）：最低一级TOWN_STREET_AGENCY（镇街道办事处）节点派送属性新增/修改后
	 * 按规则修改对应区县/市级派送属性
	 * <P>
	 * @author :187862-dujunhui
	 * @date : 2014-6-28 上午11:33:26
	 * @param entity
	 * @return
	 */
	@Override
	public ExpressDeliveryRegionsEntity optimizeParentDeliveryNature(ExpressDeliveryRegionsEntity entity){
		ExpressDeliveryRegionsEntity updateParentEntity=null;//返回值初始化
		if(StringUtils.equals(entity.getDegree(), DictionaryValueConstants.TOWN_STREET_AGENCY)||      
			StringUtils.equals(entity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)){        
			//获取该节点的父节点（即区县/市一级）
			String parentCode=entity.getParentDistrictCode();
			//获取父节点下的所有子节点
			List<ExpressDeliveryRegionsEntity> childResults = this.queryByParentDistrictCode(parentCode);
			if(!CollectionUtils.isEmpty(childResults)){
				//获取所有子节点的派送属性
				//统计子节点五种派送属性的个数
				int natureOne=0,natureThree=0,natureFour=0,natureFive=0;
				//313353 sonar优化
//				int natureTwo=0;
				for(ExpressDeliveryRegionsEntity child:childResults){
					if(StringUtils.equals(child.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_QJPS)){   
						natureOne++;
					}else if(StringUtils.equals(child.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_BFPS)){   
						//313353 sonar优化
//						natureTwo++;
					}else if(StringUtils.equals(child.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_FBFPS)){   
						natureThree++;
					}else if(StringUtils.equals(child.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_ZTBPS)){    
						natureFour++;
					}else if(StringUtils.equals(child.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_FQJPS)){    
						natureFive++;
						}
				}
				//获取父节点实体
				ExpressDeliveryRegionsEntity parentEntity=this.queryExpressDeliveryRegionsByCode(parentCode);
				//子节点全部为“全境派送“，则父节点派送属性为”全境派送“
				if(natureOne==childResults.size()){
					parentEntity.setDeliveryNature(DictionaryValueConstants.DELIVERY_NATURE_QJPS);   
				}else if(natureThree==childResults.size()){
					//子节点中全部为“发部分派送“，则父节点派送属性为”发部分派送“
					parentEntity.setDeliveryNature(DictionaryValueConstants.DELIVERY_NATURE_FBFPS);   
				}else if(natureFour==childResults.size()){
					//子节点全部为“只自提不派送“，则父节点派送属性为”只自提不派送“
					parentEntity.setDeliveryNature(DictionaryValueConstants.DELIVERY_NATURE_ZTBPS);   
				}else if(natureFive==childResults.size()){
					//子节点全部为“发全境派送“，则父节点派送属性为”发全境派送“
					parentEntity.setDeliveryNature(DictionaryValueConstants.DELIVERY_NATURE_FQJPS);   
				}else{
					//其他情况父节点派送属性为”部分派送“
					parentEntity.setDeliveryNature(DictionaryValueConstants.DELIVERY_NATURE_BFPS);
				}
				//更新父节点派送属性入数据库
				updateParentEntity=expressDeliveryRegionsDao.
						updateExpressDeliveryRegions(parentEntity);
			}
		}
		return updateParentEntity;
	}
	
	/**
	 * <P>
	 * 新增优化规则（在做删除操作时调用，不同于新增和修改时所调用的方法）：最低一级
	 * TOWN_STREET_AGENCY（镇街道办事处）节点被删除后
	 * 按规则修改对应区县/市级派送属性
	 * <P>
	 * @author :187862-dujunhui
	 * @date : 2014-6-30 下午1:54:13
	 * @param entity
	 * @return
	 */
	@Override
	public ExpressDeliveryRegionsEntity optimizeParentDeliveryNatureWhenDel(ExpressDeliveryRegionsEntity entity){
		ExpressDeliveryRegionsEntity updateParentEntity=null;//返回值初始化
		//删除操作只针对TOWN_STREET_AGENCY一级
		if(StringUtils.equals(entity.getDegree(), DictionaryValueConstants.TOWN_STREET_AGENCY)){        
			//获取该节点的父节点（即区县/市一级）
			String parentCode=entity.getParentDistrictCode();
			//获取父节点下的所有子节点
			List<ExpressDeliveryRegionsEntity> childResults = this.queryByParentDistrictCode(parentCode);
			if(!CollectionUtils.isEmpty(childResults)){
				//获取所有子节点的派送属性
				//统计子节点五种派送属性的个数
				int natureOne=0,natureTwo=0,natureThree=0,natureFour=0,natureFive=0;
				for(ExpressDeliveryRegionsEntity child:childResults){
					if(StringUtils.equals(child.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_QJPS)){   
						natureOne++;
					}else if(StringUtils.equals(child.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_BFPS)){   
						natureTwo++;
					}else if(StringUtils.equals(child.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_FBFPS)){   
						natureThree++;
					}else if(StringUtils.equals(child.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_ZTBPS)){    
						natureFour++;
					}else if(StringUtils.equals(child.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_FQJPS)){    
						natureFive++;
						}
				}
				//剔除即将删除的节点派送属性
				if(StringUtils.equals(entity.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_QJPS)){
					natureOne=natureOne-1;  //a=natureOne==0 ? 0:natureOne-1;
				}else if(StringUtils.equals(entity.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_BFPS)){
					natureTwo=natureTwo-1;  //b=natureTwo==0 ? 0:natureTwo-1;
				}else if(StringUtils.equals(entity.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_FBFPS)){
					natureThree=natureThree-1;  //c=natureThree==0 ? 0:natureThree-1;
				}else if(StringUtils.equals(entity.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_ZTBPS)){
					natureFour=natureFour-1;  //d=natureFour==0 ? 0:natureFour-1;
				}else if(StringUtils.equals(entity.getDeliveryNature(), DictionaryValueConstants.DELIVERY_NATURE_FQJPS)){
					natureFive=natureFive-1;
				}
				if(natureOne + natureTwo + natureThree + natureFour == NumberConstants.NUMBER_THE_3){ //其中3个值为-1，一个值为0
				   //要删除的是唯一TOWN_STREET_AGENCY节点，删除后，父节点及父节点的父节点派送属性不变，即不作操作
				}else{
					 //获取父节点实体
					ExpressDeliveryRegionsEntity parentEntity=this.queryExpressDeliveryRegionsByCode(parentCode);
					//子节点全部为“全境派送“，则父节点派送属性为”全境派送“
					if(natureOne==childResults.size()-1){
						parentEntity.setDeliveryNature(DictionaryValueConstants.DELIVERY_NATURE_QJPS);   
					}else if(natureThree==childResults.size()-1){
						//子节点中全部为“发部分派送“，则父节点派送属性为”发部分派送“
						parentEntity.setDeliveryNature(DictionaryValueConstants.DELIVERY_NATURE_FBFPS);    
					}else if(natureFour==childResults.size()-1){
						//子节点全部为“只自提不派送“，则父节点派送属性为”只自提不派送派送“
						parentEntity.setDeliveryNature(DictionaryValueConstants.DELIVERY_NATURE_ZTBPS);   
					}else if(natureFive==childResults.size()-1){
						//子节点全部为“发全境派送“，则父节点派送属性为”发全境派送“
						parentEntity.setDeliveryNature(DictionaryValueConstants.DELIVERY_NATURE_FQJPS);   
					}else{
						//其他情况父节点派送属性为”部分派送“
						parentEntity.setDeliveryNature(DictionaryValueConstants.DELIVERY_NATURE_BFPS);
					}
					//更新父节点派送属性入数据库
					expressDeliveryRegionsDao.updateExpressDeliveryRegions(parentEntity);
				}
			}
		}
		return updateParentEntity;
	}
	/**
	 *同步接口方法
	 * @param deliveryRegionsEntities
	 */
	private void synRegionsToGW(List<ExpressDeliveryRegionsEntity> entitys,String operateType){
		
		syncExpressDeliveryRegionsInfoService.synRegionsToGW(entitys,operateType);
		
	}
	
	/**
	 * 同步至CRM
	 * @param entitys
	 * @param operateType
	 */

	private void synRegionsToCRM(List<ExpressDeliveryRegionsEntity> entitys,int operateType){
		syncExpressDeliveryRegionsInfoService.synRegionsToCRM(entitys, operateType);
	}
	
	/**
	 * 根据上级行政区域编码查询下级行政区域，注意：此处查询的是行政区域表，非快递派送区域表
	 */
	@Override
	public List<ExpressDeliveryRegionsEntity> queryRegions(
			ExpressDeliveryRegionsEntity entity) {
		if(entity==null){
			entity = new ExpressDeliveryRegionsEntity();
			entity.setDegree(FossConstants.DISTRICT_PROVINCE_CODE);
		}
		if(entity.getDegree()==null&&entity.getParentDistrictCode().equals("")){
			return null;
		}
		return expressDeliveryRegionsDao.queryRegions(entity);
	}
	
}
