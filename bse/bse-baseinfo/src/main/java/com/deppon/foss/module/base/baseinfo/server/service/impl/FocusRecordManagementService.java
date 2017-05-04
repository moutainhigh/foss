package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFocusRecordManagementDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillingGroupTransFerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFocusRecordManagementService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesBillingGroupService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FocusRecordManagementEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FocusRecordManagementException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;

public class FocusRecordManagementService implements IFocusRecordManagementService{

	
	/**
     * 日志类
     */
    private static final Logger log = Logger.getLogger(FocusRecordManagementService.class);
    //清除缓存的常量
    public static final String KEY="key";
    /**
	 * <p>集中开单的dao层</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	private  IFocusRecordManagementDao focusRecordManagementDao;
	/**
	 * <p> 车队 Service实现</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	private IMotorcadeService motorcadeService;
	/**
	 * <p> 营业部和集中开单组关系Service类</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	private ISalesBillingGroupService salesBillingGroupService;
	/**
	 * <p>营业部和集中开单组关系Service类</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	private IBillingGroupTransFerService billingGroupTransFerService;
	/**
	 * <p>组织的service</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	//private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * <p>get set 方法</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	/*public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}*/

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	public void setBillingGroupTransFerService(
			IBillingGroupTransFerService billingGroupTransFerService) {
		this.billingGroupTransFerService = billingGroupTransFerService;
	}

	public void setFocusRecordManagementDao(
			IFocusRecordManagementDao focusRecordManagementDao) {
		this.focusRecordManagementDao = focusRecordManagementDao;
	}

	

	public void setSalesBillingGroupService(
			ISalesBillingGroupService salesBillingGroupService) {
		this.salesBillingGroupService = salesBillingGroupService;
	}
	
	/**
	 * 将日期全部转成1970年的
	 * @param date
	 * @return
	 */
	private Date convert(Date date){
		SimpleDateFormat s=new SimpleDateFormat("HH:mm");
		String dateString =s.format(date);
		try {
			Date d=s.parse(dateString);
			return d;
		} catch (ParseException e) {
			log.debug(e.getMessage());
			throw new FocusRecordManagementException("日期转换错误");
		}
	}
	/**
	 * <p>新增方法</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public void addFocusRecordManagement(FocusRecordManagementEntity entity) {
		// TODO Auto-generated method stub
		List<FocusRecordManagementEntity> entities=focusRecordManagementDao.queryIfExist(entity);
		if(entities.size()>0){
			throw new FocusRecordManagementException("开单组信息已经存在请勿再添加！");
		}
		entity.setStartDate(convert(entity.getStartDate()));
		entity.setEndDate(convert(entity.getEndDate()));
		//先查询出开单组对应的外场
		MapDto transferCenter=billingGroupTransFerService.queryTransferCenterByBillingGroupCode(entity.getBillingGroupCode());
			if(transferCenter==null){
				throw new FocusRecordManagementException("开单组对应的外场为空");
			}
		//外场查询对应的顶级车队
			MapDto transDepartment=motorcadeService.queryTopFleetByTransferCenterCode(transferCenter.getCode());
		if(transDepartment==null){
			throw new FocusRecordManagementException("对应的顶级车队为空");
		}
		//entity.setTransDepartmentName(orgAdministrativeInfoEntity.getName());
		entity.setTransDepartmentCode(transDepartment.getCode());
		List<MapDto> list=salesBillingGroupService.querySalesListByBillingGroupCode(entity.getBillingGroupCode());
		if(CollectionUtils.isEmpty(list)){
			throw new FocusRecordManagementException("服务营业部为空");
		}
		for(MapDto map: list){
			Date date=new Date();
			entity.setId(UUIDUtils.getUUID());
			entity.setCreateDate(date);
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
			entity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
			entity.setActive(FossConstants.ACTIVE);
			//entity.setSalesDepartmentName(map.getName());
			entity.setSalesDepartmentCode(map.getCode());
			focusRecordManagementDao.addFocusRecordManagement(entity);
		}
		invalidList(KEY);
	}
	/**
	 * <p>分页查询</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public List<FocusRecordManagementEntity> queryFocusRecordManagementList(
			FocusRecordManagementEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		//条件的非空判断，如果是空的，那么就新建一个实体
				if (entity == null) {
					entity=new FocusRecordManagementEntity();
				}
				//调用Dao层的方法，通过所有条件进行分页查询
				return focusRecordManagementDao.queryFocusRecordManagementList(entity, start, limit);
	}
	/**
	 * <p>根据code查询</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public List<FocusRecordManagementEntity> queryFocusRecordManagementByCode(
			String billingGroupCode) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(billingGroupCode)){
			return null;
		}
		return focusRecordManagementDao.queryFocusRecordManagementByCode(billingGroupCode);
	}
	/**
	 * <p>分页查询count</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public long queryCount(FocusRecordManagementEntity entity) {
		// TODO Auto-generated method stub
		return focusRecordManagementDao.queryCount(entity);
	}

	/**
	 * <p>删除 后台是将active状态置为N</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public long deleteFocusRecordManagement(String ids) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(ids)){
			throw new FocusRecordManagementException("参数有误");
		}
		String[] id=ids.split(",");
		List<String> idStr=Arrays.asList(id);
		invalidList(KEY);
		return focusRecordManagementDao.deleteFocusRecordManagement(idStr);
	}
	/**
	 * <p>修改操作</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public void updateFocusRecordManagement(FocusRecordManagementEntity entity) {
		// TODO Auto-generated method stub
		if(entity==null){
			throw new FocusRecordManagementException("参数有误");
		}
		this.deleteFocusRecordManagement(entity.getId());
		this.addFocusRecordManagement(entity);
		invalidList(KEY);
	}

	  /**
     * 
     * <p>取缓存中的数据</p> 
     * @author 232608-wusuhua
     * @date 2015-6-24
     * @param key
     * @return
     * @see
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<FocusRecordManagementEntity> queryListCache(String key) {
	List<FocusRecordManagementEntity> resultList = new ArrayList<FocusRecordManagementEntity>();
	try {
	    CacheManager cacheManager = CacheManager.getInstance();
	    if (cacheManager == null) {
		return resultList;
	    }
	    ICache cache = cacheManager.getCache(FossTTLCache.FOCUS_RECORD_LIST_UUID);
	    if (cache == null) {
		return resultList;
	    }
	    resultList = (List<FocusRecordManagementEntity>) cache.get(key);
	} catch (Exception t) {
	    log.error("cache找不到", t);
	}
	return resultList;
    }
	/**
	 * 清缓存
	 */
    @SuppressWarnings("unchecked")
    private void invalidList(String key) {
	((ICache<String, List<FocusRecordManagementEntity>>)CacheManager.getInstance().getCache(FossTTLCache.FOCUS_RECORD_LIST_UUID)).invalid(key);
    }
    /**
	 * <p>查询所有集中开单组信息 提供给接送货</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	public List<FocusRecordManagementEntity> queryAllBillingGroup(){
		List<FocusRecordManagementEntity> list=queryListCache(KEY);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list;
	}

}
