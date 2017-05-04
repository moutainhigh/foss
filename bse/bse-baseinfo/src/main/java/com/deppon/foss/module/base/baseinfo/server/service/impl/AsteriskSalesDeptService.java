package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAsteriskSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAsteriskSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AsteriskSalesDeptException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 加星标营业部Dao接口实现
 * 
 * @author 132599-foss-shenweihua
 * @date 2013-5-4 上午11:58:12
 * @since
 * @version
 */
public class AsteriskSalesDeptService implements IAsteriskSalesDeptService{
	
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AsteriskSalesDeptService.class);
    
    /**
     * 加星标营业部信息维护Dao接口
     */
	private IAsteriskSalesDeptDao asteriskSalesDeptDao;
    
	/**
     * <p>新增加星标营业部信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 上午11:18:12
     * @param entity
     * @return
     * @see
     */
	@Override
	public int addAsteriskSalesDept(AsteriskSalesDeptEntity entity) {
		// 创建日期
		Date date = new Date();
		if(null == entity){
		    throw new AsteriskSalesDeptException("传入的参数不允许为空！");
		}
		if(queryAsteriskDeptByCode(entity.getSalesDeptCode())){  //return 不为空
			throw new AsteriskSalesDeptException("此营业部已是星标营业部！");
		}
		// 第一次记录新增时，虚拟编码为记录的id
		entity.setId(UUIDUtils.getUUID());
		entity.setVirtualCode(entity.getId());
		entity.setCreateDate(date);
		// 设置版本号
		entity.setVersionNo(date.getTime());
		//设置状态为有效
		entity.setActive(FossConstants.ACTIVE);
		//设置营业部为星标营业部1号线
		entity.setAsteriskCode(DictionaryValueConstants.ASTERISK_TYPE_LINE1);
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		LOGGER.debug("virtualCode: " + entity.getVirtualCode());

		return asteriskSalesDeptDao.addAsteriskSalesDept(entity);
	}
	
	/**
     * <p>修改加星标营业部信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 上午11:18:12
     * @param entity
     * @return
     * @see
     */
	@Override
	public int updateAsteriskSalesDept(AsteriskSalesDeptEntity entity) {
		if(null == entity){
		    throw new AsteriskSalesDeptException("传入的参数不允许为空！");
		}else if(StringUtils.isBlank(entity.getVirtualCode())){
		    throw new AsteriskSalesDeptException("虚拟编码不允许为空！");
		}
		List<String> list = new ArrayList<String>();
		list.add(entity.getVirtualCode());
		//作废星标营业部信息
		int result = asteriskSalesDeptDao.deleteAsteriskSalesDeptByVirtualCode(list, entity.getModifyUser());
		if(result > 0){
		    //作废成功
		    entity.setActive(FossConstants.ACTIVE);
		    entity.setId(UUIDUtils.getUUID());
		    entity.setCreateDate(new Date());
		    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		    entity.setAsteriskCode(DictionaryValueConstants.ASTERISK_TYPE_LINE1);
		    return asteriskSalesDeptDao.addAsteriskSalesDept(entity);
		}else {
		    return FossConstants.FAILURE;
		}
	}
	
	/**
     * <p>作废加星标营业部信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 上午11:18:12
     * @param asteriskDeptVirtualCodes 虚拟编码集合
     * @param modifyUser 修改人编码
     * @return
     * @see
     */
	@Override
	public int deleteAsteriskSalesDeptByVirtualCode(
			List<String> asteriskDeptVirtualCodes, String modifyUser) {
		
		if(CollectionUtils.isEmpty(asteriskDeptVirtualCodes)){
		    throw new AsteriskSalesDeptException("虚拟编码不允许为空！");
		}else{
		    return asteriskSalesDeptDao.deleteAsteriskSalesDeptByVirtualCode(asteriskDeptVirtualCodes, modifyUser);
		}
	}
	
	/**
     * 根据传入对象查询符合条件所有加星标营业部信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 上午11:18:12
     * @param entity
     *            加星标营业部信息实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
	@Override
	public List<AsteriskSalesDeptEntity> queryAllAsteriskSalesDept(
			AsteriskSalesDeptEntity entity, int limit, int start) {
		if(null == entity){
		    throw new AsteriskSalesDeptException("传入的参数不允许为空！");
		}else {
		    entity.setActive(FossConstants.ACTIVE);
		    return asteriskSalesDeptDao.queryAllAsteriskSalesDept(entity, limit, start);
		}
	}
	
	/**
     * 统计总记录数
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 上午11:18:12
     * @param entity
     *            加星标营业部信息实体
     * @return
     * @see
     */
	@Override
	public Long queryRecordCount(AsteriskSalesDeptEntity entity) {
		if(null == entity){
		    throw new AsteriskSalesDeptException("传入的参数不允许为空！");
		}else {
		    entity.setActive(FossConstants.ACTIVE);
		    return asteriskSalesDeptDao.queryRecordCount(entity);
		}
	}
	
	/**
	 * 设置加星标营业部信息维护Dao接口
	 * @param asteriskSalesDeptDao
	 */
	public void setAsteriskSalesDeptDao(IAsteriskSalesDeptDao asteriskSalesDeptDao) {
		this.asteriskSalesDeptDao = asteriskSalesDeptDao;
	}
	
	/**
     * 验证此营业部是否已是加星营业部
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 下午5:58:12
     * @param salesDeptCode
     *            加星标营业部编码
     * @return
     * @see
     */
	@Override
	public boolean queryAsteriskDeptByCode(String salesDeptCode) {
		if(StringUtil.isBlank(salesDeptCode)){
		    return false;
		}else {
		    return asteriskSalesDeptDao.queryAsteriskDeptByCode(salesDeptCode);
		}
	}

}
