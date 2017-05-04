package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICallCenterWaybillInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICallCenterWaybillInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncBackCallCenterInfoToCCService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CallCenterWaybillInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CallCenterWaybillInfoException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * cc催运单信息Service接口实现
 * @author 132599-foss-shenweihua
 * @date 2014-07-18 下午 4:17:54
 * @since
 * @version
 */
public class CallCenterWaybillInfoService implements ICallCenterWaybillInfoService{
	
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(CallCenterWaybillInfoService.class);
    
    /**
     * 催运单信息dao
     */
    private ICallCenterWaybillInfoDao callCenterWaybillInfoDao;
    
    /**
     * 催运单反馈信息service
     */
    private ISyncBackCallCenterInfoToCCService syncBackCallCenterInfoToCCService;
	
    /**
	 * 新增CC催运单信息
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-18 下午 4:17:54
	 * @param entity
	 * @return
	 */
    @Transactional
    @Override
	public int addCallCenterWaybillInfo(CallCenterWaybillInfoEntity entity) throws CallCenterWaybillInfoException{
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		if(null == entity.getPressWaybillNo()){
		    throw new CallCenterWaybillInfoException("催运单凭证号不允许为空！");
		}
		//验证客户信息是否存在
		boolean isFlag = callCenterWaybillInfoDao.queryCallInfoByCallCenterWaybillNo(entity.getPressWaybillNo());
		if(isFlag){
			callCenterWaybillInfoDao.updateCallCenterWaybillInfo(entity);
		}else{
			// 第一次记录新增时，虚拟编码为记录的id
			entity.setId(UUIDUtils.getUUID());
			entity.setHasDone(FossConstants.INACTIVE);
			callCenterWaybillInfoDao.addCallCenterWaybillInfo(entity);
		}
		
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 修改CC催运单信息
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-18 下午 4:17:54
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public int updateCallCenterWaybillInfo(CallCenterWaybillInfoEntity entity) throws CallCenterWaybillInfoException{
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		if(null == entity.getPressWaybillNo()){
		    throw new CallCenterWaybillInfoException("催运单凭证号不允许为空！");
		}
		entity.setDealTime(new Date());
		entity.setModifyDate(new Date());
		entity.setModifyUser(entity.getDealUser());
		int hasDone = callCenterWaybillInfoDao.updateCallCenterWaybillInfo(entity);
		if(hasDone==-1){//说明修改失败
			LOGGER.error("处理催运单信息失败");
		}
		else{
			boolean flag =syncBackCallCenterInfoToCCService.syncBackCallCenterInfoToCC(entity);
			if(!flag){
				throw new CallCenterWaybillInfoException("同步反馈信息失败！");
			}
		}
		return hasDone;
		
	}
	
	/**
	 * 根据运单凭证号查询催运单信息
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-18 下午 4:17:54
	 * @param callCenterWaybillNo
	 * @return
	 */
	@Override
	public CallCenterWaybillInfoEntity queryCcInfoByCallCenterWaybillNo(String callCenterWaybillNo) {
		return null;
	}
	
	/**
	 * 根据运单凭证号判断催运单信息是否已存在
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-18 下午 4:17:54
	 * @param callCenterWaybillNo
	 * @return
	 */
	@Override
	public boolean queryCallInfoByCallCenterWaybillNo(String callCenterWaybillNo) {
		return callCenterWaybillInfoDao.queryCallInfoByCallCenterWaybillNo(callCenterWaybillNo);
	}
	
	/**
     * 根据传入对象查询符合条件催运单信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2014-07-21 下午10:06:59
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
	@Override
	public List<CallCenterWaybillInfoEntity> queryCallCenterInfos(
			CallCenterWaybillInfoEntity entity, int limit, int start) {
		
		return callCenterWaybillInfoDao.queryCallCenterInfos(entity, limit, start);
	}
	
	/**
     * 统计总记录数
     * 
     * @author 132599-foss-shenweihua
     * @date 2014-07-21 下午10:06:59
     * @param entity
     * @return
     * @see
     */
	@Override
	public Long queryRecordCount(CallCenterWaybillInfoEntity entity) {
		return callCenterWaybillInfoDao.queryRecordCount(entity);
	}
	
	
	/**
	 * 设置催运单信息dao
	 * @param callCenterWaybillInfoDao
	 */
	public void setCallCenterWaybillInfoDao(
			ICallCenterWaybillInfoDao callCenterWaybillInfoDao) {
		this.callCenterWaybillInfoDao = callCenterWaybillInfoDao;
	}
	
	/**
	 * 设置催运单反馈信息service
	 * @param syncBackCallCenterInfoToCCService
	 */
	public void setSyncBackCallCenterInfoToCCService(
			ISyncBackCallCenterInfoToCCService syncBackCallCenterInfoToCCService) {
		this.syncBackCallCenterInfoToCCService = syncBackCallCenterInfoToCCService;
	}
	
}
