package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.ICarloadPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICarloadPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceOrgEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricePlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.define.WaybillConstants;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

public class CarloadPriceService implements ICarloadPriceService  {
	
	private static final int FOUR = 4;
	
	/**
     * 整车价格参数波动方案主信息 
     */
    @Inject
    ICarloadPricePlanDao carloadPricePlanDao;
    
	public ICarloadPricePlanDao getCarloadPricePlanDao() {
		return carloadPricePlanDao;
	}


	public void setCarloadPricePlanDao(ICarloadPricePlanDao carloadPricePlanDao) {
		this.carloadPricePlanDao = carloadPricePlanDao;
	}


	/**
     * 
     * queryCarloadPricePlanBatchInfo(分页查询整车价格参数价格方案)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * 
     * @param start
     * 
     * @param limit
     * 
     * @return 
     * 
     * List<PricePlanEntity>
     * 
     * @exception 
     * 
     * @since  1.0.0
     */
	@Override
	public List<CarloadPricePlanDto> queryCarloadPricePlanBatchInfo(
			CarloadPricePlanDto carloadPricePlanDto, int start, int limit) {
		//设置条件
	    if (PricingConstants.ALL.equals(carloadPricePlanDto.getActive())) {
	    	carloadPricePlanDto.setActive(null);
	    }
	    //设置条件
	    if (PricingConstants.CENTRALIZE_PICKUP_YES.equals(carloadPricePlanDto.getCurrentUsedVersion())) {
	    	carloadPricePlanDto.setCurrentUsedVersion("yes");
	    }else if(PricingConstants.CENTRALIZE_PICKUP_NO.equals(carloadPricePlanDto.getCurrentUsedVersion())){
	    	carloadPricePlanDto.setCurrentUsedVersion("no");
	    }else{
	    	carloadPricePlanDto.setCurrentUsedVersion(null);
	    }
	    List<CarloadPriceEntity> plans=null;
	    List<CarloadPriceOrgEntity>  planOrgs=new ArrayList<CarloadPriceOrgEntity>();
	    //组织部门不为空
	    if(StringUtil.isEmpty(carloadPricePlanDto.getOrganizationCode())){
	    	//分页查询整车价格参数方案
	    	plans=	carloadPricePlanDao.queryCarloadPricePlanList(carloadPricePlanDto, start, limit);
	    	Set<String> seto =new HashSet<String>();
		    for(CarloadPriceEntity plan:plans){
		    	if(!seto.contains(plan.getId()) ){
		    		//查询方案适合的部门
		    		List<CarloadPriceOrgEntity>	oanys =carloadPricePlanDao.searchCarloadPriceOrglist(plan.getId());
		    		//方案适合的所有部门
		    		planOrgs.addAll(oanys);
	    		}
	    		seto.add(plan.getId());
		    }
	    }
	    //方案及适合部门的集合
	    List<CarloadPricePlanDto> dtoList =new ArrayList<CarloadPricePlanDto>();
	    for(CarloadPriceOrgEntity planOrg:planOrgs){
	    	CarloadPricePlanDto dto =new CarloadPricePlanDto();
	    	String planId=planOrg.getCarloadPriceId();
	    	dto.setId(planId);
	    	dto.setOrganizationCode(planOrg.getOrganizationCode());
	    	dto.setOrganizationID(planOrg.getOrganizationID());
	    	dto.setOrganizationName(planOrg.getOrganizationName());
	    	for(CarloadPriceEntity ect:plans){
	    		if(planId.equals(ect.getId())){
	    			dto.setActive(ect.getActive());
	    			dto.setBeginTime(ect.getBeginTime());
	    			dto.setCreateTime(ect.getCreateTime());
	    			dto.setCreateUser(ect.getCreateUser());
	    			dto.setCurrentUsedVersion(ect.getCurrentUsedVersion());
	    			dto.setEndTime(ect.getEndTime());
	    			dto.setModifyTime(ect.getModifyTime());
	    			dto.setModifyUser(ect.getModifyUser());
	    			dto.setRemark(ect.getRemark());
	    			dto.setScenarioName(ect.getScenarioName());
	    			dto.setVersion(ect.getVersion());
	    		}
	    	}
	    	dtoList.add(dto);
	    }
	    List<CarloadPricePlanDto> list=null;
	    //组织部门是否为空
	    if(StringUtil.isEmpty(carloadPricePlanDto.getOrganizationCode())){
	    	list=dtoList;
	    }else{
	    	//方案及适合部门的集合
	    	list =  carloadPricePlanDao.queryCarloadPricePlanBatchInfo(carloadPricePlanDto, start, limit);
	    }
	    //返回的组织集合
	    List<CarloadPricePlanDto> planDtos = new ArrayList<CarloadPricePlanDto>();
	    if(CollectionUtils.isNotEmpty(list)){
	    	Set<String> set =new HashSet<String>();
	    	for(CarloadPricePlanDto dto:list){
	    		String carPriceID=dto.getId();
	    		//过滤掉重复方案
	    		if(!set.contains(carPriceID) ){
	    			planDtos.add(dto);
	    		}
	    		set.add(carPriceID);
	    	}
	   
	    	Map<String, String> codeMap= new HashMap<String, String>();
	    	Map<String, String> nameMap= new HashMap<String, String>();
	    	//拼接方案适合部门和适合部门编码
	    	for(CarloadPricePlanDto dto:list){
	    		String carpID =dto.getId();
	    		String code =dto.getOrganizationCode();
	    		String name =dto.getOrganizationName();
	    		for(CarloadPricePlanDto dtp:planDtos){
	    			String pid = dtp.getId();
                    if(carpID.equals(pid)){
                    	codeMap.put(pid, codeMap.get(pid)+code+",");
    	    			nameMap.put(pid, nameMap.get(pid)+name+"," );
                    }
	    		}
	    		 
		    			
		    		 
		    	 
	    	}
	    
	      for(CarloadPricePlanDto dto:planDtos){
	    	  String id = dto.getId();
	    	  Date beginTime = dto.getBeginTime();
	    	  Date endTime = dto.getEndTime();
	    	  if(beginTime.before(new Date()) 
	    			  &&
	    			new Date().before(endTime) 
	    			  && 
	    			  PricingConstants.CENTRALIZE_PICKUP_YES.equals(dto.getActive())){
	    		  dto.setCurrentUsedVersion(FossConstants.YES);
	    	  }else{
	    		  dto.setCurrentUsedVersion(FossConstants.NO);
	    	  }
	    	  dto.setOrganizationCode(codeMap.get(id).substring(FOUR));
	    	  dto.setOrganizationName(nameMap.get(id).substring(FOUR));
	      }
	    	
	    }
	    return planDtos;
	}


	/**
     * 
     * queryCarloadPricePlanBatchInfoCount(查询整车价格参数方案总记录数)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * @return Long
     */
	@Override
	public Long queryCarloadPricePlanBatchInfoCount(
			CarloadPricePlanDto carloadPricePlanDto) {
		 //设置条件
	    if (PricingConstants.ALL.equals(carloadPricePlanDto.getActive())) {
	    	carloadPricePlanDto.setActive(null);
	    }
	    //设置条件
	    if (PricingConstants.CENTRALIZE_PICKUP_YES.equals(carloadPricePlanDto.getCurrentUsedVersion())) {
	    	carloadPricePlanDto.setCurrentUsedVersion("yes");
	    }else if(PricingConstants.CENTRALIZE_PICKUP_NO.equals(carloadPricePlanDto.getCurrentUsedVersion())){
	    	carloadPricePlanDto.setCurrentUsedVersion("no");
	    }else{
	    	carloadPricePlanDto.setCurrentUsedVersion(null);
	    }
		return carloadPricePlanDao.queryCarloadPricePlanBatchInfoCount(carloadPricePlanDto);
	}
	//f分页
	@Override
	public Long queryCarloadPricePlanCount(
			CarloadPricePlanDto carloadPricePlanDto) {
		 //设置条件
	    if (PricingConstants.ALL.equals(carloadPricePlanDto.getActive())) {
	    	carloadPricePlanDto.setActive(null);
	    }
	    //设置条件
	    if (PricingConstants.CENTRALIZE_PICKUP_YES.equals(carloadPricePlanDto.getCurrentUsedVersion())) {
	    	carloadPricePlanDto.setCurrentUsedVersion("yes");
	    }else if(PricingConstants.CENTRALIZE_PICKUP_NO.equals(carloadPricePlanDto.getCurrentUsedVersion())){
	    	carloadPricePlanDto.setCurrentUsedVersion("no");
	    }else{
	    	carloadPricePlanDto.setCurrentUsedVersion(null);
	    }
		return carloadPricePlanDao.queryCarloadPricePlanCount(carloadPricePlanDto);
	}
	


	/**
     * 
     * <p>(新增整车价格参数方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-9 上午10:04:06
     * 
     * @return
     * 
     * @see
     */
	@Override
	public CarloadPriceEntity addCarloadPricePlan(CarloadPriceEntity carloadPriceEntity,
			List<CarloadPriceOrgEntity> carloadPriceOrgEntityList) {
		if(CollectionUtils.isEmpty(carloadPriceOrgEntityList)){
			throw new PricePlanException("组织部门不能为空,请重新选择",null);
		}
		if(null==carloadPriceEntity){
			throw new PricePlanException("方案信息不能为空,请重新选择",null);
		}
		if(carloadPriceEntity.getBeginTime().before(new Date())){
		    throw new PricePlanException("方案生效日期:"+DateUtils.convert(carloadPriceEntity.getBeginTime())+"必须大于当前营业日期",null);
		}
		carloadPriceEntity = getCarloadPricePlanValue(carloadPriceEntity);
		String scenarioName = carloadPriceEntity.getScenarioName();
		CarloadPriceEntity queryBean = new CarloadPriceEntity();
		queryBean.setScenarioName(scenarioName);
		List<CarloadPriceEntity> carloadPriceEntitys =carloadPricePlanDao.queryCarloadPricePlanBatchInfo(queryBean);
		if(CollectionUtils.isNotEmpty(carloadPriceEntitys)){
		    throw new PricePlanException("方案名称为:"+scenarioName+"已经存在,不可重复添加",null);
		}
		carloadPricePlanDao.insertCarlaodPrice(carloadPriceEntity);
		for(CarloadPriceOrgEntity carloadPriceOrgEntity:carloadPriceOrgEntityList){
			//整车价格参数方案id
			carloadPriceOrgEntity.setCarloadPriceId(carloadPriceEntity.getId());
			carloadPriceOrgEntity.setId(UUIDUtils.getUUID());
			carloadPricePlanDao.insertCarlaodPriceOrg(carloadPriceOrgEntity);
		}
		return carloadPricePlanDao.searchCarloadByPrimaryKey(carloadPriceEntity.getId());
		

	}
	
	/**
	 * 价格方案批次新增信息
	 *
	 * @param pricePlanEntity 
	 * @return 
	 */
	private CarloadPriceEntity getCarloadPricePlanValue(CarloadPriceEntity carloadPriceEntity){
	    Date currentTime = new Date();
	    carloadPriceEntity.setId(UUIDUtils.getUUID());
	    //是否激活
	    carloadPriceEntity.setActive(FossConstants.INACTIVE);
	    //生效日期
	    carloadPriceEntity.setBeginTime(carloadPriceEntity.getBeginTime());
	    //中止时间
	    carloadPriceEntity.setEndTime(new Date(PricingConstants.ENDTIME));
	    //修改时间
	    carloadPriceEntity.setModifyTime(currentTime);
	    //创建时间
	    carloadPriceEntity.setCreateTime(currentTime);
	    //修改人
	    carloadPriceEntity.setModifyUser(carloadPriceEntity.getModifyUser());
	    //备注
	    carloadPriceEntity.setRemark(null);
	    //方案名称
	    carloadPriceEntity.setScenarioName(carloadPriceEntity.getScenarioName());
	    //版本号
	    carloadPriceEntity.setVersion(currentTime.getTime());
	    return carloadPriceEntity;
	}

	/**
     * 
     * <p>(新增整车价格参数方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-9 上午10:04:06
     * 
     * @return
     * 
     * @see
     */
	@Override
	public List<CarloadPriceDetailEntity> addCarloadPriceDetail(
			CarloadPriceDetailEntity carloadPriceDetailEntity) {
		Date currentTime = new Date();
		//查看同一发票标记类型的方案明细
		CarloadPriceDetailEntity detail =carloadPricePlanDao.searchCarloadPriceDetail(carloadPriceDetailEntity);
		if(null!=detail){
			carloadPriceDetailEntity.setId(detail.getId());
			carloadPricePlanDao.updateCarloadPriceDetailPlan(carloadPriceDetailEntity);
		}else{
			carloadPriceDetailEntity.setActive(FossConstants.ACTIVE);
			carloadPriceDetailEntity.setSerialNo(currentTime.getTime());
			carloadPriceDetailEntity.setId(UUIDUtils.getUUID());
			carloadPricePlanDao.insertCarloadPriceDetail(carloadPriceDetailEntity);
		}
		
		return carloadPricePlanDao.searchCarloadPriceDetailByKey(carloadPriceDetailEntity.getCarloadPriceId());
	}



	/**
     * 
     * <p>(修改整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@Override
	public List<CarloadPriceDetailEntity> updateCarloadPriceDetailPlan(
			CarloadPriceDetailEntity carloadPriceDetailEntity) {
		    String detailID= carloadPriceDetailEntity.getId();
			//查看同一发票标记类型的方案明细  同一个方案发票标记唯一
			CarloadPriceDetailEntity detail =carloadPricePlanDao.searchCarloadPriceDetail(carloadPriceDetailEntity);
			if(null!=detail && !detailID.equals(detail.getId())){
				 throw new PricePlanException("同类发票标记已经存在,不可重复添加",null);
			}
			//修改整车价格参数方案
		    CarloadPriceEntity actionPlan =new CarloadPriceEntity();
		    UserEntity currentUser = FossUserContext.getCurrentUser();
		    actionPlan.setId(carloadPriceDetailEntity.getCarloadPriceId());
		    actionPlan.setModifyTime(new Date());
		    actionPlan.setModifyUser(currentUser.getEmpName());
		    carloadPricePlanDao.updatecarloadPlan(actionPlan);
		    //修改整车价格参数方案明细
			carloadPricePlanDao.updateCarloadPriceDetailPlan(carloadPriceDetailEntity);
		return carloadPricePlanDao.searchCarloadPriceDetailByKey(carloadPriceDetailEntity.getCarloadPriceId());
		  

		 
	}


	/**
     * <p>(删除所选择整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@Override
	public List<CarloadPriceDetailEntity> deleteCarloadPricePlanDetail(
			List<String> carPlanDetailIds) {
		CarloadPriceDetailEntity carloadPriceDetailEntity=carloadPricePlanDao.selectCarloadPricePlanDetailByplanID(carPlanDetailIds.get(0));
		for(String id:carPlanDetailIds){
			carloadPricePlanDao.deleteCarloadPricePlanDetail(id);
		}
		return carloadPricePlanDao.searchCarloadPriceDetailByKey(carloadPriceDetailEntity.getCarloadPriceId());
	
		
	}


	/**
     * <p>(激活整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@Override
	public void activeCarloadPricePlan(List<String> carPlanIds) {
		for(String id :carPlanIds){
			CarloadPriceEntity carloadPriceEntity=carloadPricePlanDao.searchCarloadByPrimaryKey(id);
			if(null==carloadPriceEntity ){
			    throw new PricePlanException("所选方案为空! 请检查",null);  
		    }
		    //激活日期需要大于当前营业日期
		    if(carloadPriceEntity.getBeginTime().before(new Date(System.currentTimeMillis()))){
			    throw new PricePlanException("当前营业日期必须小于生效日期,才能被激活,请重新修改草稿！",null);
			}
		    
		    // 查询适合整车参数方案的部门
		    List<CarloadPriceOrgEntity> carloadPriceOrgs = carloadPricePlanDao.searchCarloadPriceOrglist(id);
		    for(CarloadPriceOrgEntity org : carloadPriceOrgs){
		    	 CarloadPricePlanDto dto = new CarloadPricePlanDto();
		    	 	dto.setBeginTime(carloadPriceEntity.getBeginTime());
		    	 	dto.setEndTime(carloadPriceEntity.getEndTime());
				    dto.setActive(FossConstants.ACTIVE);
				    dto.setOrganizationCode(org.getOrganizationCode());
			 //查询部门是否存在激活的整车参数方案
				    CarloadPricePlanDto  rCarloadPlan = carloadPricePlanDao.searchCarloadPricePlanDto(dto);
				    if(rCarloadPlan!=null){
				    	throw new PricePlanException(org.getOrganizationName()+"已经存在激活状态的"+"【"+rCarloadPlan.getScenarioName()+"】"+"的方案"+",请重新修改草稿",null);
				    }
		    }
		    
		   //验证成功后激活整车价格参数方案
		    CarloadPriceEntity actionPlan =new CarloadPriceEntity();
		    UserEntity currentUser = FossUserContext.getCurrentUser();
		    actionPlan.setId(id);
		    actionPlan.setModifyTime(new Date());
		    actionPlan.setModifyUser(currentUser.getEmpName());
		    actionPlan.setActive(FossConstants.ACTIVE);
		    carloadPricePlanDao.updatecarloadPlan(actionPlan);
		}
		
		
	}


	/**
     * <p>(删除整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@Override
	public void deleteCarloadPricePlan(List<String> carPlanIds) {
		for(String id:carPlanIds){
			
			//删除整车价格参数方案明细
			carloadPricePlanDao.deleteCarloadPricePlanDetailByPlanId(id);
			
			//删除整车价格参数方案所适应的部门
			carloadPricePlanDao.deleteCarloadPricePlanOrgByPlanId(id);
			
			//删除整车价格参数方案
			carloadPricePlanDao.deleteCarloadPricePlanByPlanId(id);
		}
	}


	/**
     * <p>(立即激活整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@Override
	public void immediatelyActiveCarloadPricePlan(
			CarloadPriceEntity carloadPriceEntity) {
		//查询整车价格参数方案
		CarloadPriceEntity carPlan = carloadPricePlanDao.searchCarloadByPrimaryKey(carloadPriceEntity.getId());
		if(null == carPlan){
		    throw new PricePlanException("所选整车价格参数方案为空! 请检查",null);  
	    }
		// 查询适合整车参数方案的部门
	    List<CarloadPriceOrgEntity> carloadPriceOrgs = carloadPricePlanDao.searchCarloadPriceOrglist(carloadPriceEntity.getId());
	    //查询同一部门是否存在相同激活方案
	    for(CarloadPriceOrgEntity org : carloadPriceOrgs){
	    	 CarloadPricePlanDto dto = new CarloadPricePlanDto();
	    	 	dto.setBeginTime(carloadPriceEntity.getBeginTime());
	    	 	dto.setEndTime(carPlan.getEndTime());
			    dto.setActive(FossConstants.ACTIVE);
			    dto.setOrganizationCode(org.getOrganizationCode());
			    //查询部门是否存在激活的整车参数方案
			    CarloadPricePlanDto  rCarloadPlan = carloadPricePlanDao.searchCarloadPricePlanDto(dto);
			    if(rCarloadPlan!=null){
			    	throw new PricePlanException(org.getOrganizationName()+"已经存在激活状态的"+"【"+rCarloadPlan.getScenarioName()+"】"+"的方案"+",请重新修改草稿",null);
			    }
	    }
	    
	  //验证成功后激活整车价格参数方案
	  CarloadPriceEntity actionPlian = new CarloadPriceEntity();
	  UserEntity currentUser = FossUserContext.getCurrentUser();
	  actionPlian.setActive(FossConstants.ACTIVE);
	  actionPlian.setBeginTime(carloadPriceEntity.getBeginTime());
	  actionPlian.setId(carloadPriceEntity.getId());
	  actionPlian.setModifyTime(new Date());
	  actionPlian.setModifyUser(currentUser.getEmpName());
	  carloadPricePlanDao.updatecarloadPlan(actionPlian);
		
		
	}


	/**
     * <p>(立即中止整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@Override
	public void immediatelystopCarloadPricePlan(CarloadPriceEntity carloadPriceEntity) {
		  String pricePlanId = carloadPriceEntity.getId();
		    Date stopTime = carloadPriceEntity.getEndTime();
		    if(StringUtil.isBlank(pricePlanId)){
			throw new PricePlanException("所选方案ID为空!",null);
		    }
		    if(stopTime == null){
			throw new PricePlanException("截至日期不能为空!",null);
		    }
			if(stopTime.before(new Date())){
				throw new PricePlanException("中止日期必须大于当前营业日期!",null);
			}

			CarloadPriceEntity carPlan = carloadPricePlanDao.searchCarloadByPrimaryKey(pricePlanId);
		    if(null==carPlan)
		    {
			throw new PricePlanException("根据前台参数实体ID,没有找到对应的实体!",null);
		    }
		    if(stopTime.after(carPlan.getEndTime()))
		    {
			throw new PricePlanException("中止日期不能延长原方案所制定的活动结束日期!",null);
		    }
		    
	  //验证完后中止整车价格方案
	    CarloadPriceEntity actionPlian = new CarloadPriceEntity();
	    UserEntity currentUser = FossUserContext.getCurrentUser();
		actionPlian.setEndTime(stopTime);
		actionPlian.setId(pricePlanId);
		actionPlian.setModifyTime(new Date());
		actionPlian.setModifyUser(currentUser.getEmpName());
		carloadPricePlanDao.updatecarloadPlan(actionPlian);
	}


	/**
     * <p>(导出整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@Override
	public ExportResource exportCarloadPricePlan(
			CarloadPricePlanDto carloadPricePlanDto) {
		//设置条件
	    if (PricingConstants.ALL.equals(carloadPricePlanDto.getActive())) {
	    	carloadPricePlanDto.setActive(null);
	    }
	    //设置条件
	    if (PricingConstants.CENTRALIZE_PICKUP_YES.equals(carloadPricePlanDto.getCurrentUsedVersion())) {
	    	carloadPricePlanDto.setCurrentUsedVersion("yes");
	    }else if(PricingConstants.CENTRALIZE_PICKUP_NO.equals(carloadPricePlanDto.getCurrentUsedVersion())){
	    	carloadPricePlanDto.setCurrentUsedVersion("no");
	    }else{
	    	carloadPricePlanDto.setCurrentUsedVersion(null);
	    }
	    //解决乱码问题
	    String name = carloadPricePlanDto.getScenarioName();
	    if(StringUtil.isNotEmpty(name)){
	    	try {
				carloadPricePlanDto.setScenarioName( URLDecoder.decode(name, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	    }
		ExportResource exportResource = new ExportResource();
		List<CarloadPricePlanDto> carloadPricePlanDtos  = carloadPricePlanDao.queryCarloadPricePlanBatchInfo(carloadPricePlanDto, PricingConstants.ZERO, Integer.MAX_VALUE);
		if(CollectionUtils.isEmpty(carloadPricePlanDtos)){
		    return null;
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (CarloadPricePlanDto cDto : carloadPricePlanDtos) {
		     List<String> row = exportPlatform(cDto);
		     rowList.add(row);
		}
		exportResource.setHeads(PricingColumnConstants.CARLOAD_PRICE_PLAN_TITLE);
		exportResource.setRowList(rowList);
		return exportResource;
	}
	
	 /**
     * 
     * <p>填充方案 sheet row </p> 
     * 
     * @author DP-Foss-PanGuoYang
     * 
     * @date 2014-05-02 上午9:59:41
     * 
     * @param CarloadPricePlanDto
     * 
     * @return
     * 
     * @see
     */
    private List<String> exportPlatform(CarloadPricePlanDto carloadPricePlanDto){
		List<String> result = new ArrayList<String>();
		//方案名称
		result.add(carloadPricePlanDto.getScenarioName());
		//组织
		result.add(carloadPricePlanDto.getOrganizationName());
		//组织编码
		result.add(carloadPricePlanDto.getOrganizationCode());
		Date beginTime = carloadPricePlanDto.getBeginTime();
  	    Date endTime = carloadPricePlanDto.getEndTime();
		String modifyDate = DateUtils.convert(carloadPricePlanDto.getModifyTime(), DateUtils.DATE_FORMAT);
		String beginDate = DateUtils.convert(beginTime, DateUtils.DATE_FORMAT);
		String endDate = DateUtils.convert(endTime, DateUtils.DATE_FORMAT);
		result.add(modifyDate);
		result.add(carloadPricePlanDto.getModifyUser());
		result.add(beginDate);
		result.add(endDate);
		if(StringUtil.equalsIgnoreCase(FossConstants.ACTIVE, carloadPricePlanDto.getActive())){
		    result.add("激活");
		}else{
		    result.add("未激活");
		}
		if(beginTime.before(new Date()) 
	  			  &&
	  			new Date().before(endTime) 
	  			  && 
	  			  PricingConstants.CENTRALIZE_PICKUP_YES.equals(carloadPricePlanDto.getActive())){
	  	    	result.add("是");
	  	    }else{
	  	    	result.add("否");
	  	    }
		return result;
    }



    /**
     * <p>(查询整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@Override
	public List<CarloadPriceDetailEntity> queryCarloadPricePlanDetailInfo(
			CarloadPricePlanDto carloadPricePlanDto,
			CarloadPriceDetailEntity carloadPriceDetailEntity) {
		if(null==carloadPricePlanDto || null==carloadPriceDetailEntity){
			return null;
		}
		List<CarloadPricePlanDto> dtos= carloadPricePlanDao.queryCarloadPricePlanBatchInfo(carloadPricePlanDto,PricingConstants.ZERO, Integer.MAX_VALUE);
		if(CollectionUtils.isEmpty(dtos)){
			return null;
		}
		List<CarloadPriceDetailEntity> list = carloadPricePlanDao.searchCarloadPriceDetailByKey(carloadPricePlanDto.getId());
		if (PricingConstants.ALL.equals(carloadPriceDetailEntity.getInvoiceType())) {
	    	return list;
		}
		List<CarloadPriceDetailEntity> entys = new ArrayList<CarloadPriceDetailEntity>();
		for(CarloadPriceDetailEntity enty:list){
			 if (carloadPriceDetailEntity.getInvoiceType().equals(enty.getInvoiceType())) {
			       entys.add(enty);
			 } 
		}
		return entys;
	}



	@Override
	public CarloadPriceEntity queryCarloadPricePlanAndOrgInfo(
			CarloadPricePlanDto carloadPricePlanDto) {
		return carloadPricePlanDao.searchCarloadByPrimaryKey(carloadPricePlanDto.getId());
	}



	@Override
	public List<CarloadPricePlanDto> queryCarloadPricePlanDtos(
			CarloadPricePlanDto carloadPricePlanDto) {
		carloadPricePlanDto.setOrganizationCode(null);
		return carloadPricePlanDao.queryCarloadPricePlanBatchInfo(carloadPricePlanDto,PricingConstants.ZERO, Integer.MAX_VALUE);
	}


	/**
     * <p>(修改整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@Override
	public void updateCarloadPricePlan(
			CarloadPriceEntity carloadPriceEntity ,
			List<CarloadPriceOrgEntity> carloadPriceOrgEntityList) {
		if(null == carloadPriceOrgEntityList){
		    throw new PricePlanException(PricePlanException.PRICE_PLAN_ADD_PLANISNULL_ERROR_CODE,null);
		}
		if(null == carloadPriceEntity){
		    throw new PricePlanException(PricePlanException.PRICE_PLAN_ADD_PLANISNULL_ERROR_CODE,null);
		}
		if(carloadPriceEntity.getBeginTime().before(new Date())){
		    throw new PricePlanException("方案生效日期:"+DateUtils.convert(carloadPriceEntity.getBeginTime())+"必须大于当前营业日期",null);
		}
		UserEntity currentUser = FossUserContext.getCurrentUser();
		String id =carloadPriceEntity.getId();
		carloadPriceEntity = getCarloadPricePlanValue(carloadPriceEntity);
		carloadPriceEntity.setId(id);
		carloadPriceEntity.setModifyTime(new Date());
		carloadPriceEntity.setModifyUser(currentUser.getEmpName());
		carloadPricePlanDao.updatecarloadPlan(carloadPriceEntity);
		//删除原方案适合的部门
		carloadPricePlanDao.deleteCarloadPricePlanOrgByPlanId(id);
		for(CarloadPriceOrgEntity carloadPriceOrgEntity:carloadPriceOrgEntityList){
			//整车价格参数方案id
			carloadPriceOrgEntity.setCarloadPriceId(id);
			carloadPriceOrgEntity.setId(UUIDUtils.getUUID());
			carloadPricePlanDao.insertCarlaodPriceOrg(carloadPriceOrgEntity);
		}
		
	}



	@Override
	public List<CarloadPriceDetailEntity> carloadPriceDetailList(
			CarloadPricePlanDto carloadPricePlanDto) {
		return carloadPricePlanDao.searchCarloadPriceDetailByKey(carloadPricePlanDto.getId());
	}



	/**
     * <p>(复制整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@Override
	public String copyCarloadPricePlan(CarloadPricePlanDto carloadPricePlanDto) {
		//原来方案id
		String yid =carloadPricePlanDto.getId();
		//原来方案
		CarloadPriceEntity   carloadPriceEntity =carloadPricePlanDao.searchCarloadByPrimaryKey(yid);
		//复制方案
		carloadPriceEntity.setActive(FossConstants.INACTIVE);
		carloadPriceEntity.setId(UUIDUtils.getUUID());
		carloadPriceEntity.setCreateTime(new Date());
		carloadPricePlanDao.insertCarlaodPrice(carloadPriceEntity);
		
		//复制方案对应部门
		List<CarloadPriceOrgEntity>  olist =carloadPricePlanDao.searchCarloadPriceOrglist(yid);
		for(CarloadPriceOrgEntity carg:olist){
			carg.setCarloadPriceId(carloadPriceEntity.getId());
			carg.setId(UUIDUtils.getUUID());
			carloadPricePlanDao.insertCarlaodPriceOrg(carg);
		}
	   
		//复制方案明细
		List<CarloadPriceDetailEntity> dlist =  carloadPricePlanDao.searchCarloadPriceDetailByKey(yid);
		for(CarloadPriceDetailEntity cDetail:dlist){
			cDetail.setCarloadPriceId(carloadPriceEntity.getId());
			cDetail.setId(UUIDUtils.getUUID());
			carloadPricePlanDao.insertCarloadPriceDetail(cDetail);
		}
		return carloadPriceEntity.getId();
	}


	/**
     * <p>(中止整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * @see
     */
	@Override
	public void stopCarloadPricePlan(CarloadPriceEntity carloadPriceEntity) {
		String planId=carloadPriceEntity.getId();
		if(StringUtil.isBlank(planId)){
			throw new PricePlanException("所选价格方案ID为空!",null);
		 }
		 Date stopTime = carloadPriceEntity.getEndTime();
	    if(stopTime == null){
		throw new PricePlanException("截至日期不能为空!",null);
	    }

		if(stopTime.before(new Date())){
			throw new PricePlanException("中止日期必须大于当前营业日期!",null);
		}
		//查询整车价格参数方案
		CarloadPriceEntity carPlan = carloadPricePlanDao.searchCarloadByPrimaryKey(planId);
		if(null == carPlan){
		    throw new PricePlanException("所选整车价格参数方案为空! 请检查",null);  
	    }
	    if(stopTime.after(carPlan.getEndTime()))
	    {
		throw new PricePlanException("中止日期不能延长原方案所制定的活动结束日期!",null);
	    }
		
	  //验证完后中止整车价格方案
	    CarloadPriceEntity actionPlian = new CarloadPriceEntity();
	    UserEntity currentUser = FossUserContext.getCurrentUser();
		actionPlian.setEndTime(stopTime);
		actionPlian.setId(planId);
		actionPlian.setModifyTime(new Date());
		actionPlian.setModifyUser(currentUser.getEmpName());
		carloadPricePlanDao.updatecarloadPlan(actionPlian);
		
	}


	/**
     * <p>(导出整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return ExportResource
     * 
     * @see
     */
	@Override
	public ExportResource exportCarloadPricePlanDetail(
			CarloadPricePlanDto carloadPricePlanDto) {
	    //设置条件
	    if (PricingConstants.ALL.equals(carloadPricePlanDto.getInvoiceType())) {
	    	carloadPricePlanDto.setInvoiceType(null);
	    }
		ExportResource exportResource = new ExportResource();
		List<CarloadPricePlanDto> carloadPricePlanDtos  = carloadPricePlanDao.exportCarloadPricePlanDetail(carloadPricePlanDto);
		if(CollectionUtils.isEmpty(carloadPricePlanDtos)){
		    return null;
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (CarloadPricePlanDto cDto : carloadPricePlanDtos) {
		     List<String> row = exportPlatformDetail(cDto);
		     rowList.add(row);
		}
		exportResource.setHeads(PricingColumnConstants.CARLOAD_PRICE_PLAN_DETAIL_TITLE);
		exportResource.setRowList(rowList);
		return exportResource;
	}


	 /**
     * 
     * <p>填充方案 sheet row </p> 
     * 
     * @author DP-Foss-PanGuoYang
     * 
     * @date 2014-05-02 上午9:59:41
     * 
     * @param CarloadPricePlanDto
     * 
     * @return
     * 
     * @see
     */
    private List<String> exportPlatformDetail(CarloadPricePlanDto carloadPricePlanDto){
		List<String> result = new ArrayList<String>();
		//方案名称
		result.add(carloadPricePlanDto.getScenarioName());
		//组织
		result.add(carloadPricePlanDto.getOrganizationName());
		String invoiceType =carloadPricePlanDto.getInvoiceType();
		if(WaybillConstants.INVOICE_01.equals(invoiceType)){
			//发票标记
			result.add(WaybillConstants.INVOICE_TYPE_01);
		}else{
			//发票标记
			result.add(WaybillConstants.INVOICE_TYPE_02);
		}
		
		//整车价格波动向上浮动值
		result.add(carloadPricePlanDto.getFloatUp()+"");
		//整车价格波动向下浮动值
		result.add(carloadPricePlanDto.getFloatDown()+"");
		//其他成本参数
		result.add(carloadPricePlanDto.getOtherCostParameter()+"");
		//包装费参数
		result.add(carloadPricePlanDto.getPackageFeeParameter()+"");
		//重量参数
		result.add(carloadPricePlanDto.getWeightParameter()+"");
		//车价参数
		result.add(carloadPricePlanDto.getCarCostParameter()+"");
		//人力成本参数
		result.add(carloadPricePlanDto.getHumanCostParameter()+"");
		//备注
		result.add(carloadPricePlanDto.getRemark());
		return result;
    }

    /**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.CarloadPricePlanDao.selectCarloadPricePlanDetailByOrganizationCode
	 * @Description:通过组织编码查询当前版本的整车价格波动参数方案
	 *
	 * @param carloadPricePlanDto
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-15 下午3:29:29
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-11-15    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public List<CarloadPricePlanDto> selectCarloadPricePlanDetailByOrganizationCode(
			CarloadPricePlanDto carloadPricePlanDto) {
			   //默认只查询当前版本的方案
			   carloadPricePlanDto.setCurrentUsedVersion(FossConstants.YES);
			   carloadPricePlanDto.setActive(FossConstants.YES);
		return carloadPricePlanDao.selectCarloadPricePlanDetailByOrganizationCode(carloadPricePlanDto);
	}
	
	
	
	
	
	
    
	
	

}
