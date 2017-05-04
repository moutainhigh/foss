package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryAddressDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SyncEDARequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SyncEDAResponseEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.util.define.FossConstants;

/**
 * FOSS地址库录入实现类Service
 * 主要包括新增，修改，删除，批量修改
 * 新增，修改，批量修改的时候都要对地址进行相应的校验
 * 	  主要校验是生效时间和失效时间交叉的情况下，门牌号不能交叉
 * @author 198771
 *
 */

public class ExpressDeliveryAddressService implements
		IExpressDeliveryAddressService {
	
	//日志
	private Logger logger = LoggerFactory.getLogger(ExpressDeliveryAddressService.class);
	
	/**
	 * 地址库Dao
	 */
	private IExpressDeliveryAddressDao expressDeliveryAddressDao;
	
	/**
	 * ESB开关
	 */
	@Autowired
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 推送给GIS的URL
	 */
	private String syncToGisUrl;

	public void setSyncToGisUrl(String syncToGisUrl) {
		this.syncToGisUrl = syncToGisUrl;
	}
	
	public void setExpressDeliveryAddressDao(
			IExpressDeliveryAddressDao expressDeliveryAddressDao) {
		this.expressDeliveryAddressDao = expressDeliveryAddressDao;
	}
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	/**
	 * 根据条件分页查询
	 * @param condition
	 * @param rowBound
	 * @return
	 */
	@Override
	public List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitys(
			ExpressDeliveryAddressEntity condition, int start, int limit) {
		if(condition!=null&&condition.getStreet()!=null){
			condition.setStreet(condition.getStreet().trim());
		}
		//设置分页参数
		RowBounds rb = new RowBounds(start, limit);
		return expressDeliveryAddressDao.queryExpressDeliveryAddressEntitys(condition, rb);
	}
	/**
	 * 获取总条数
	 * @param condition
	 * @return
	 */
	@Override
	public long getCountByCondition(ExpressDeliveryAddressEntity condition) {
		return expressDeliveryAddressDao.getCountByCondition(condition);
	}
	/**
	 * 插入数据
	 * @param entity
	 */
	@Override
	public void addExpressDeliveryAddressEntity(
			ExpressDeliveryAddressEntity entity) {
		if(entity==null){
			return;
		}
		//启用
		if(!FossConstants.ACTIVE.equals(entity.getActive())){
			entity.setActive(FossConstants.ACTIVE);
		}
		//设置唯一标示ID
		if("".equals(entity.getId())){
			entity.setId(UUID.randomUUID().toString());
		}
		//设置创建时间
		if(entity.getCreateDate()==null){
			entity.setCreateDate(new Date());
		}
		//设置修改时间
		if(entity.getModifyDate()==null){
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		}
		//校验（不通过会报异常）
		this.check(entity, null);
		//推送（失败会报异常）
		this.sync(entity);
		expressDeliveryAddressDao.addExpressDeliveryAddressEntity(entity);
	}
	/**
	 * 修改数据
	 * @param entity
	 */
	@Override
	public void updateExpressDeliveryAddressEntity(
			List<String> ids,ExpressDeliveryAddressEntity entity) {
		//如果实体为空，则抛出异常
		if(entity==null){
			throw new BusinessException("参数为空，修改失败");
		}
		//如果id为空（根据id进行修改），则抛出异常
		if(entity.getId()==null||"".equals(entity.getId())){
			throw new BusinessException("参数ID为空，修改失败");
		}
		//设置修改时间
		if(entity.getModifyDate()==null){
			entity.setModifyDate(new Date());
		}
		//校验（不通过会报异常）
		this.check(entity, ids);
		List<ExpressDeliveryAddressEntity> entitys = new ArrayList<ExpressDeliveryAddressEntity>();
		List<ExpressDeliveryAddressEntity> updateEntitys = this.queryExpressDeliveryAddressEntitysByCodes(ids);
		ExpressDeliveryAddressEntity updateEntity = null;
		if(updateEntitys!=null&&updateEntitys.get(0)!=null){
			updateEntity = updateEntitys.get(0);
			//Id
			updateEntity.setId(entity.getId());
			//修改时间
			updateEntity.setModifyDate(entity.getModifyDate());
			//修改人
			updateEntity.setModifyUser(entity.getModifyUser());
			//操作人
			updateEntity.setOperatorName(entity.getOperatorName());
			//设置为作废
			updateEntity.setActive(FossConstants.INACTIVE);
		}
		//新增数据重新生成ID
		entity.setId(UUID.randomUUID().toString());
		//设置创建人
		entity.setCreateUser(entity.getModifyUser());
		//设置创建时间
		entity.setCreateDate(entity.getModifyDate());
		//设置修改人
		entity.setModifyUser(entity.getModifyUser());
		//设置创建时间
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		//设置启用
		entity.setActive(FossConstants.ACTIVE);
		if(updateEntity!=null&&updateEntity.getId()!=null){
			entitys.add(updateEntity);
		}
		entitys.add(entity);
		//推送（失败会报异常）
		this.sync(entitys);
		//校验通过，并且推送给GIS成功，先作废再新增
		expressDeliveryAddressDao.updateExpressDeliveryAddressEntity(updateEntity);
		expressDeliveryAddressDao.addExpressDeliveryAddressEntity(entity);
	}
	/**
	 * 根据条件查询
	 * @param condition
	 * @return
	 */
	@Override
	public List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitysByCondition(
			ExpressDeliveryAddressEntity condition) {
		return expressDeliveryAddressDao.queryExpressDeliveryAddressEntitysByCondition(condition);
	}
	
	/**
	 * 删除
	 * 根据id可做批量删除
	 */
	@Override
	public void deleteExpressDeliveryAddressEntityByCodes(List<String> ids,ExpressDeliveryAddressEntity expressDeliveryAddressEntity) {
		Map<String,Object> params = new HashMap<String,Object>();
		//设置修改时间
		expressDeliveryAddressEntity.setModifyDate(new Date());
		//设置修改人
		params.put("modifyUser", expressDeliveryAddressEntity.getModifyUser());
		//设置操作人（操作人为创建人货修改人的姓名）
		params.put("operatorName", expressDeliveryAddressEntity.getOperatorName());
		//设置修改时间
	    params.put("modifyDate", expressDeliveryAddressEntity.getModifyDate());
		params.put("ids", ids);
		List<ExpressDeliveryAddressEntity> expressDeliveryAddressEntitys = this.queryExpressDeliveryAddressEntitysByCodes(ids);
		if(expressDeliveryAddressEntitys!=null&&expressDeliveryAddressEntitys.size()!=0){
			for(ExpressDeliveryAddressEntity entity:expressDeliveryAddressEntitys){
				entity.setActive(FossConstants.INACTIVE);
				entity.setModifyUser(expressDeliveryAddressEntity.getModifyUser());
				entity.setModifyDate(expressDeliveryAddressEntity.getModifyDate());
				entity.setOperatorName(expressDeliveryAddressEntity.getOperatorName());
			}
			this.sync(expressDeliveryAddressEntitys);
		}
		//this.sync(expressDeliveryAddressEntitys);
		expressDeliveryAddressDao.deleteExpressDeliveryAddressEntityByCodes(params);
	}

	/**
	 * 批量修改
	 */
	@Override
	public void updateBatchExpressDeliveryAddressEntity(List<String> ids,
			ExpressDeliveryAddressEntity expressDeliveryAddressEntity) {
		Map<String,Object> params = new HashMap<String,Object>();
		//设置修改时间
		expressDeliveryAddressEntity.setModifyDate(new Date());
		//设置修改人
		params.put("modifyUser", expressDeliveryAddressEntity.getModifyUser());
		//设置修改时间
	    params.put("modifyDate", expressDeliveryAddressEntity.getModifyDate());
		//设置操作人（操作人为创建人货修改人的姓名）
		params.put("operatorName", expressDeliveryAddressEntity.getOperatorName());
		params.put("ids", ids);
		List<ExpressDeliveryAddressEntity> expressDeliveryAddressEntitys = this.queryExpressDeliveryAddressEntitysByCodes(ids);
		//如果实体为空，则抛出异常
		if(expressDeliveryAddressEntitys==null){
			throw new BusinessException("参数为空，修改失败");
				}
		for(ExpressDeliveryAddressEntity entity:expressDeliveryAddressEntitys){
			//设置生效时间
			entity.setStartTime(expressDeliveryAddressEntity.getStartTime());
			//设置失效时间
			entity.setEndTime(expressDeliveryAddressEntity.getEndTime());
		}
		//批量修改进行校验
		for(ExpressDeliveryAddressEntity entity:expressDeliveryAddressEntitys){
			this.check(entity, ids);
		}
		for(ExpressDeliveryAddressEntity entity:expressDeliveryAddressEntitys){
			//设置ID
			entity.setId(UUID.randomUUID().toString());
			//设置启用
			entity.setActive(FossConstants.ACTIVE);
			//设置创建时间
			entity.setCreateDate(expressDeliveryAddressEntity.getModifyDate());
			//设置创建人
			entity.setCreateUser(expressDeliveryAddressEntity.getModifyUser());
			//设置操作人
			entity.setOperatorName(expressDeliveryAddressEntity.getOperatorName());
			//设置修改人
			entity.setModifyUser(expressDeliveryAddressEntity.getModifyUser());
			//设置修改时间
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		}
		List<ExpressDeliveryAddressEntity> entitys = this.queryExpressDeliveryAddressEntitysByCodes(ids);
		if(expressDeliveryAddressEntitys!=null&&expressDeliveryAddressEntitys.size()!=0){
			for(ExpressDeliveryAddressEntity entity:entitys){
				entity.setActive(FossConstants.INACTIVE);
				entity.setModifyUser(expressDeliveryAddressEntity.getModifyUser());
				entity.setModifyDate(expressDeliveryAddressEntity.getModifyDate());
				entity.setOperatorName(expressDeliveryAddressEntity.getOperatorName());
			}
		}
		entitys.addAll(expressDeliveryAddressEntitys);
		//推送（失败会报异常）
		this.sync(entitys);
		expressDeliveryAddressDao.updateBatchExpressDeliveryAddressEntity(params);
		expressDeliveryAddressDao.addExpressDeliveryAddressEntity(expressDeliveryAddressEntitys);
	}
	/**
	 * 根据id查询
	 */
	@Override
	public List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitysByCodes(
			List<String> ids) {
		return expressDeliveryAddressDao.queryExpressDeliveryAddressEntitysByCodes(ids);
	}
	
	/**
	 * 
	 * @param expressDeliveryAddressEntitys
	 * @param ids
	 * @return
	 */
	private void sync(ExpressDeliveryAddressEntity expressDeliveryAddressEntity){
		List<ExpressDeliveryAddressEntity> expressDeliveryAddressEntitys = new ArrayList<ExpressDeliveryAddressEntity>();
		expressDeliveryAddressEntitys.add(expressDeliveryAddressEntity);
		this.sync(expressDeliveryAddressEntitys);
	}
	/**
	 * 检验跟推送
	 * @param expressDeliveryAddressEntitys
	 * @param ids
	 * @return
	 */
	private void sync(List<ExpressDeliveryAddressEntity> expressDeliveryAddressEntitys){
		if (configurationParamsService.queryExpressDeliveryAddress()) {
			List<SyncEDARequestEntity> requestEntitys = this
					.convertor(expressDeliveryAddressEntitys);
			SyncEDAResponseEntity responseEntity = this
					.syncExpressDeliveryAddressToGis(requestEntitys);
			if (responseEntity == null) {
				throw new BusinessException("接口异常，操作失败，请稍后重试[异常信息:拒绝连接]！！！");
			}
			// 如果返回failed则说明推送失败
			if (MessageConstants.FAILED.equals(responseEntity.getIs_success())) {
				throw new BusinessException("接口异常，操作失败，请稍后重试["
						+ responseEntity.getExptionMSG() + "]！！！");
			}
		}
	}
	
	
	//同步快递派送区域地址库基础资料
	private SyncEDAResponseEntity syncExpressDeliveryAddressToGis(List<SyncEDARequestEntity> requestEntitys){
	    String jsonString = JSON.toJSONString(requestEntitys);
	    logger.info("同步快递派送地址库基础资料至GIS开始:"+syncToGisUrl);
	    logger.info("同步快递派送地址库基础资料至GIS请求JSON:"+jsonString);
	    HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod(syncToGisUrl);
	    //设置响应超时时间
	    method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NumberConstants.NUMBER_15000);
	    //设置连接超时时间
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(NumberConstants.NUMBER_5000);
	    SyncEDAResponseEntity responseEntity = null;
	    try {
	    	StringRequestEntity requestEntity = new StringRequestEntity(jsonString, "application/json", "UTF-8");
	    	//设置字符集编码
	    	method.getParams().setContentCharset("UTF-8");
	    	method.getParams().setHttpElementCharset("UTF-8");
	    	method.setRequestEntity(requestEntity);
			int statuCode = client.executeMethod(method);
			//返回状态如果是200为正常
			if(statuCode==HttpStatus.SC_OK){
				String responseString = method.getResponseBodyAsString();
				JSONObject obj = JSONObject.fromObject(responseString).getJSONObject("addrExprMappingResponse");
				responseEntity = (SyncEDAResponseEntity)JSONObject.toBean(obj, SyncEDAResponseEntity.class);
				logger.info("同步快递派送地址库基础资料至GIS返回JSON:"+responseString);
			}else{//如果返回状态码不是200，那么默认为异常
				if(responseEntity==null){
		    		responseEntity = new SyncEDAResponseEntity();
		    	}
				responseEntity.setIs_success(MessageConstants.FAILED);
				responseEntity.setExptionMSG("Server Error");
				logger.info("同步快递派送地址库基础资料至GIS服务端异常"+statuCode);
			}
			logger.info("同步快递派送地址库基础资料至GIS结束:"+statuCode);
	    } catch (Exception e) {
	    	if(responseEntity==null){
	    		responseEntity = new SyncEDAResponseEntity();
	    	}
			responseEntity.setIs_success(MessageConstants.FAILED);
			responseEntity.setExptionMSG(e.getClass().getSimpleName());
			//如果是服务端GIS异常，那么ESB会返回相应的错误信息，这会导致上面JSONObject.toBean转换异常抛出JSONException
			if(e instanceof JSONException){
				responseEntity.setExptionMSG("Server Error");
			}
			logger.info("同步快递派送地址库基础资料至GIS异常:"+e.getMessage());
		}
	    return responseEntity;
	}
	
	/**
	 * 判断时间是否交叉
	 * @param
	 * 	entity  新增修改数据
	 *  oriEntity 原始数据
	 * @return
	 */ 
	private boolean checkTime(ExpressDeliveryAddressEntity entity,ExpressDeliveryAddressEntity oriEntity){
		//<1>如果新增修改的数据的生效时间在已存在数据的生效时间和失效时间之间就交叉了
		boolean timeFlag1 = entity.getStartTime().getTime()>=oriEntity.getStartTime().getTime()&&
				entity.getStartTime().getTime()<=oriEntity.getEndTime().getTime();
		//<2>如果新增修改的数据的失效时间在已存在数据的生效时间和失效时间之间就交叉了
		boolean timeFlag2 = entity.getEndTime().getTime()>=oriEntity.getStartTime().getTime()&&
				entity.getEndTime().getTime()<=oriEntity.getEndTime().getTime();
		if(timeFlag1||timeFlag2){
			return true;
		}
		//<3>如果新增修改的数据的生效时间小于已存在数据的生效时间，失效时间大于已存在数据失效时间就交叉了
		timeFlag1 = entity.getStartTime().getTime()<=oriEntity.getStartTime().getTime()&&
				entity.getEndTime().getTime()>=oriEntity.getEndTime().getTime();
		//本来应该有第<4>种情况:如果新增修改的数据的生效时间大于已存在数据的生效时间，失效时间小于已存在数据的失效时间就交叉了，这种情况<1>,<2>都包含
		return timeFlag1;
	}
	
	/**
	 * 比较门牌号
	 * @param minNumber
	 * @param maxNumber
	 * @param oriType
	 * @param type
	 */
	private void checkNumber(int minNumber,int maxNumber,String oriType,String type){
		List<Integer> oriNumbers = this.getNumbers(minNumber, maxNumber, oriType);
		List<Integer> numbers = this.getNumbers(minNumber, maxNumber, type);
		Set<Integer> unionNumbers = new HashSet<Integer>();
		unionNumbers.addAll(oriNumbers);
		unionNumbers.addAll(numbers);
		//如果Set集合长度不等于初始集合长度+新增修改集合长度，说明门牌号交叉了
		if(unionNumbers.size()!=oriNumbers.size()+numbers.size()){
 			throw new BusinessException("同一时刻门牌号有交叉，操作失败");
 		}
	}
	
	//根据起始门牌号和终止门牌号，以及类型生成比较的门牌号，比较连续的三个数字就可以
	private List<Integer> getNumbers(int minNumber,int maxNumber,String type){
		List<Integer> numbers = new ArrayList<Integer>();
		if(MessageConstants.NUMBER_ODD.equals(type)){//单号
			for(int i=minNumber/2*2+1;i<=maxNumber;i=i+2){
				if(numbers.size()>=NumberConstants.NUMBER_3){
					break;
				}
				numbers.add(i);
			}
		}else if(MessageConstants.NUMBER_EVEN.equals(type)){//双号
			for(int i=(minNumber+1)/2*2;i<=maxNumber;i=i+2){
				if(numbers.size()>=NumberConstants.NUMBER_3){
					break;
				}
				numbers.add(i);
			}
		}else{//路全部，号全部
			for(int i=minNumber;i<=maxNumber;i++){
				if(numbers.size()>=NumberConstants.NUMBER_3){
					break;
				}
				numbers.add(i);
			}
		}
		return numbers;
	} 
	
	/**
	 * 
	 * @param entity
	 * @param ids
	 */
	private void check(ExpressDeliveryAddressEntity entity,List<String> ids){
		ExpressDeliveryAddressEntity condition = new ExpressDeliveryAddressEntity();
		//根据省市区地址查询
		condition.setPccAddress(entity.getPccAddress());
		//路街和乡镇二选一
		//先按照路街查询
		//313353 sonar
		List<ExpressDeliveryAddressEntity> expressDeliveryAddressEntityRoads = this.sonarSplitOne(entity, condition);
		//如果为空，则说明没有交叉的可能，则校验通过
		if(expressDeliveryAddressEntityRoads.size()==0){
			return;
		}
		
		//313353 sonar
		List<Integer> resultList = this.sonarSplitTwo(entity);
		int startHouseNumber = resultList.get(0);
		int endHouseNumber = resultList.get(1);
		
		outer:for(ExpressDeliveryAddressEntity oriEntity:expressDeliveryAddressEntityRoads){
			//判断是不是修改，批量修改原始数据，如果是直接跳过
			if(ids!=null){
				for(String id:ids){
					if(id.equals(oriEntity.getId())){//修改的时候如果修改数据的ID跟查询出来的ID一样，那么就继续外层循环
						continue outer;
					}
				}
			}
			//时间有交叉
			if(checkTime(entity, oriEntity)){
				if(MessageConstants.TOWN_ALL.equals(entity.getType())
						||MessageConstants.TOWN_ALL.equals(oriEntity.getType())){
					//313353 sonar
					this.sonarSplitCheck(entity, oriEntity);
				}else{
					//313353 sonar
					int oriStartHouseNumber = sonarSplitCalculateOne(entity, oriEntity);
					int oriEndHouseNumber = sonarSplitCalculateTwo(entity, oriEntity);
					//<1>如果新增修改的数据的起始门牌号在已存在数据的起始门牌号和终止门牌号之间,并且终止门牌号大于已存在数据终止门牌号就可能重复
					//313353 sonar
					boolean timeFlag = this.sonarSplitCalculateTimeFlag(startHouseNumber, oriStartHouseNumber, oriEndHouseNumber, endHouseNumber);
					if(timeFlag){
						checkNumber(startHouseNumber, oriEndHouseNumber, oriEntity.getType(), entity.getType());
						continue;
					}
					//<2>如果新增修改的数据的终止门牌号在已存在数据的起始门牌号和终止门牌号之间,并且起始门牌号小于已存在数据起始门牌号就可能重复
					timeFlag = endHouseNumber>=oriStartHouseNumber&&endHouseNumber<=oriEndHouseNumber
							&&startHouseNumber<=oriStartHouseNumber;
					if(timeFlag){
						checkNumber(oriStartHouseNumber, endHouseNumber, oriEntity.getType(), entity.getType());
						continue;
					}
					//<3>如果新增修改的数据的终止门牌号大于已存在数据的终止门牌号,并且起始门牌号小于已存在数据起始门牌号就可能重复
					timeFlag = startHouseNumber<=oriStartHouseNumber&&endHouseNumber>=oriEndHouseNumber;
					if(timeFlag){
						checkNumber(oriStartHouseNumber, oriEndHouseNumber, oriEntity.getType(), entity.getType());
						continue;
					}
					//<4>如果新增修改的数据的终止门牌号小于已存在数据的终止门牌号,并且起始门牌号大于已存在数据起始门牌号就可能重复
					timeFlag = startHouseNumber>=oriStartHouseNumber&&endHouseNumber<=oriEndHouseNumber;
					if(timeFlag){
						checkNumber(startHouseNumber, endHouseNumber, oriEntity.getType(), entity.getType());
						continue;
					}
				}
			}
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitCheck(ExpressDeliveryAddressEntity entity,ExpressDeliveryAddressEntity oriEntity) {
		if(!entity.getDepartmentCode().equals(oriEntity.getDepartmentCode())){
			throw new BusinessException("同一时间下同一位置不能属于两个营业部");
		}
		//此时判断新增数据和原数据都为镇全境
		if(entity.getType().equals(oriEntity.getType())){
			if(entity.getPccAddress().equals(oriEntity.getPccAddress())&&
					entity.getTownCode().equals(oriEntity.getTownCode())){
				throw new BusinessException("该条数据已存在，操作失败");
			}
		}
	}
	
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private int sonarSplitCalculateOne(ExpressDeliveryAddressEntity entity,ExpressDeliveryAddressEntity oriEntity) {
		return null==oriEntity.getStartHouseNumber()
				?0:Integer.parseInt("".equals(oriEntity.getStartHouseNumber().replaceFirst("[^1-9]*", ""))
						?"0":oriEntity.getStartHouseNumber().replaceFirst("[^1-9]*", ""));
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private int sonarSplitCalculateTwo(ExpressDeliveryAddressEntity entity,ExpressDeliveryAddressEntity oriEntity) {
		return null==oriEntity.getEndHouseNumber()
				?NumberConstants.NUMBER_99999999:Integer.parseInt("".equals(oriEntity.getEndHouseNumber().replaceFirst("[^1-9]*", ""))
						?"99999999":oriEntity.getEndHouseNumber().replaceFirst("[^1-9]*", ""));
	}
	
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private boolean sonarSplitCalculateTimeFlag(int startHouseNumber, int oriStartHouseNumber, int oriEndHouseNumber
			, int endHouseNumber) {
		return startHouseNumber>=oriStartHouseNumber&&startHouseNumber<=oriEndHouseNumber
				&&endHouseNumber>=oriEndHouseNumber;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private List<ExpressDeliveryAddressEntity> sonarSplitOne(ExpressDeliveryAddressEntity entity,
			ExpressDeliveryAddressEntity condition) {
		List<ExpressDeliveryAddressEntity> expressDeliveryAddressEntityRoads = new ArrayList<ExpressDeliveryAddressEntity>();
		if(entity.getStreet()!=null&&!"".equals(entity.getStreet())){
			condition.setStreet(entity.getStreet());
			if(entity.getTownCode()!=null&&!"".equals(entity.getTownCode())){
				condition.setTownCode(entity.getTownCode());
			}
			expressDeliveryAddressEntityRoads = expressDeliveryAddressDao.queryAlreadyExpressDeliveryAddressEntitysByCondition(condition);
		}
		List<ExpressDeliveryAddressEntity> expressDeliveryAddressEntityStreets = new ArrayList<ExpressDeliveryAddressEntity>();
		if(entity.getTownCode()!=null&&!"".equals(entity.getTownCode())){
			condition.setStreet(null);
			condition.setTownCode(entity.getTownCode());
			expressDeliveryAddressEntityStreets = expressDeliveryAddressDao.queryAlreadyExpressDeliveryAddressEntitysByCondition(condition);
		}
		if(MessageConstants.TOWN_ALL.equals(entity.getType())){
			expressDeliveryAddressEntityRoads.addAll(expressDeliveryAddressEntityStreets);
		}else{
			//如果添加的不是镇全境，只需要跟原有镇全境的数据做下校验
			for(ExpressDeliveryAddressEntity expressDeliveryAddressEntityStreet
					:expressDeliveryAddressEntityStreets){
				if(MessageConstants.TOWN_ALL.equals(expressDeliveryAddressEntityStreet.getType())){
					expressDeliveryAddressEntityRoads.add(expressDeliveryAddressEntityStreet);
				}
			}
		}
		return expressDeliveryAddressEntityRoads;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private List<Integer> sonarSplitTwo(ExpressDeliveryAddressEntity entity){
		List<Integer> resultList = new ArrayList<Integer>();
		resultList.set(0, -1);
		resultList.set(1, -1);
		if(!MessageConstants.TOWN_ALL.equals(entity.getType())){//如果不是镇全部就要校验门牌号
			//前台如果没有填门牌号起和门牌号止(前端已经做了判断门牌号起跟门牌号止必须成对出现,而且最多只能输入8位数字)
			if("".equals(entity.getStartHouseNumber())||entity.getStartHouseNumber()==null){
				resultList.set(0, 0);
				resultList.set(1, NumberConstants.NUMBER_99999999);
			}else{
				//截取门牌号起后面的数字，如00020替换为20
				String startHouseNumberStr = entity.getStartHouseNumber().replaceFirst("[^1-9]*", "");
				//截取门牌号止后面的数字，如00120替换为120
				String endHouseNumberStr = entity.getEndHouseNumber().replaceFirst("[^1-9]*", "");
				try{
				    //如果此时转换异常，说明传过来的起止门牌号为0号(或000号等)，因为前端控制终止门牌号必须大于起止门牌号，故终止门牌号可不做相应的处理
					resultList.set(0, Integer.parseInt(startHouseNumberStr));
				}catch(Exception e){
					resultList.set(0, 0);
				}
				resultList.set(1, Integer.parseInt(endHouseNumberStr));
			}
		}
		return resultList;
	}
	
	//实体转换
	private List<SyncEDARequestEntity> convertor(List<ExpressDeliveryAddressEntity> entitys){
		List<SyncEDARequestEntity> requestEntitys = new ArrayList<SyncEDARequestEntity>();
		SyncEDARequestEntity requestEntity = null;
		for(ExpressDeliveryAddressEntity entity:entitys){
			requestEntity = new SyncEDARequestEntity();
			//唯一标识
			requestEntity.setFossid(entity.getId());
			//营业部编码
			requestEntity.setDept_no(entity.getDepartmentCode());
			//营业部名称
			requestEntity.setDeptName(entity.getDepartmentName());
			//省编码
			requestEntity.setProvince_code(entity.getProvinceCode());
			//省
			requestEntity.setProvince(entity.getProvinceName());
			//市区编码
			requestEntity.setCity_code(entity.getCityCode());
			//市
			requestEntity.setCity(entity.getCityName());
			//区县编码
			requestEntity.setCounty_code(entity.getCountyCode());
			//区县
			requestEntity.setCounty(entity.getCountyName());
			//乡镇/街道编码
			requestEntity.setTown_code(entity.getTownCode());
			//乡镇/街道
			requestEntity.setTown(entity.getTownName());
			//路/街
			requestEntity.setStreet(entity.getStreet());
			//省市区requestEntity.setAddress(entity.getPccAddress());  GIS无此字段
			//门牌号起
			requestEntity.setStart_house_no(entity.getStartHouseNumber());
			//门牌号止
			requestEntity.setEnd_house_no(entity.getEndHouseNumber());
			//生效时间
			requestEntity.setStart_time(entity.getStartTime());
			//失效时间
			requestEntity.setEnd_time(entity.getEndTime());
			//类型
			requestEntity.setType(entity.getType());
			//地标
			requestEntity.setSign(entity.getSign());
			//是否启用
			requestEntity.setActive(entity.getActive());
			//创建时间
			requestEntity.setCreateDate(entity.getCreateDate());
			//创建人
			requestEntity.setCreateUser(entity.getCreateUser());
			//修改时间
			requestEntity.setModifyDate(entity.getModifyDate());
			//修改人
			requestEntity.setModifyUser(entity.getModifyUser());
			requestEntitys.add(requestEntity);
		}
		return requestEntitys;
	}
}
