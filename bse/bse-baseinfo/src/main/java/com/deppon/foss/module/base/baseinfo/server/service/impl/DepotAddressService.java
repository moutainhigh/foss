package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDepotAddressDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepotAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepotAddressEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;

/**
 * 进仓地址Service
 * 
 * @ClassName: DepotAddressService
 * @Description: TODO(进仓地址Service)
 * @author 310854-liuzhenhua
 * @date 2016-9-1 下午3:53:48
 * 
 */
public class DepotAddressService implements IDepotAddressService {

	private Logger LOGGER = LoggerFactory.getLogger(DepotAddressService.class);

	private IDepotAddressDao depotAddressDao;

	public IDepotAddressDao getDepotAddressDao() {
		return depotAddressDao;
	}

	public void setDepotAddressDao(IDepotAddressDao depotAddressDao) {
		this.depotAddressDao = depotAddressDao;
	}

	@Override
	public List<DepotAddressEntity> queryDepotAddress(
			DepotAddressEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		List<DepotAddressEntity> list = new ArrayList<DepotAddressEntity>();

		try {
			RowBounds rowBound = new RowBounds(start, limit);
			list = depotAddressDao.queryDepotAddress(entity, rowBound);
		} catch (BusinessException e) {
			LOGGER.error("查询进仓地址出错：" + e.getMessage());
			throw new BusinessException("查询进仓地址出错：" + e.getMessage());
		}
		return list;
	}

	@Override
	public long getCountByCondition(DepotAddressEntity entity) {
		// TODO Auto-generated method stub
		long count = 0;
		try {
			count = depotAddressDao.getCountByCondition(entity);
		} catch (BusinessException e) {
			LOGGER.error("查询进仓地址总数出错：" + e.getMessage());
			throw new BusinessException("查询进仓地址总数出错：" + e.getMessage());
		}
		return count;
	}

	@Override
	public void addDepotAddress(DepotAddressEntity entity) {
		// TODO Auto-generated method stub
		try {
			entity.setId(UUIDUtils.getUUID());
			entity.setCreateDate(new Date());
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
			entity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
			
			depotAddressDao.addDepotAddress(entity);
		} catch (BusinessException e) {
			LOGGER.error("新增进仓地址出错：" + e.getMessage());
			throw new BusinessException("新增进仓地址出错：" + e.getMessage());
		}
	}

	@Override
	public void updateDepotAddress(DepotAddressEntity entity) {
		// TODO Auto-generated method stub
		try {
			if(null == entity || StringUtil.isEmpty(entity.getId())){
				LOGGER.error("数据为空，修改失败");
				throw new BusinessException("数据为空，修改失败");
			}
			if(entity.getDepotState().equals("2")){
				entity.setConfirmDateTime(new Date());
			}
			depotAddressDao.updateDepotAddress(entity);
		} catch (BusinessException e) {
			LOGGER.error("修改进仓地址出错：" + e.getMessage());
			throw new BusinessException("修改进仓地址出错：" + e.getMessage());
		}
	}

	@Override
	public void deleteDepotAddress(DepotAddressEntity entity) {
		// TODO Auto-generated method stub
		try {
		
			entity.setModifyDate(new Date());
			entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
			depotAddressDao.deleteDepotAddress(entity);
		} catch (BusinessException e) {
			LOGGER.error("作废进仓地址出错：" + e.getMessage());
			throw new BusinessException("作废进仓地址出错：" + e.getMessage());
		}
	}
	

	@Override
	public List<DepotAddressEntity> queryDepotAddressByDepCode(String depCode) {
		// TODO Auto-generated method stub
		try{
			if(StringUtil.isEmpty(depCode)){
				LOGGER.error("营业部CODE为空");
				throw new BusinessException("营业部CODE为空");
			}
			return depotAddressDao.queryDepotAddressByDepCode(depCode);
		}catch (BusinessException e) {
			LOGGER.error("通过营业部code获取进仓地址信息出错：" + e.getMessage());
			throw new BusinessException("通过营业部code获取进仓地址信息出错：" + e.getMessage());
		}
	}

	@Override
	public ExportResource exportDepotAddressList() {
		// TODO Auto-generated method stub
		
		
		RowBounds rowBound = new RowBounds(0, Integer.MAX_VALUE);
		List<DepotAddressEntity> list = depotAddressDao.queryDepotAddress(null, rowBound);
		// 返回的结果集
		List<List<String>> resultList = new ArrayList<List<String>>();
		for(DepotAddressEntity depotAddressEntity : list){
			resultList.add(exportTransferDepotAddress(depotAddressEntity));
		}
		ExportResource sheet = new ExportResource();
		sheet.setHeads(ComnConst.DEPOT_ADDRESS_TITLE);
		sheet.setRowList(resultList);
		return sheet;
	}

	/**
	 * 把进仓信息转成可导出信息
	* @Title: exportTransferDepotAddress 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param entity
	* @param @return    设定文件 
	* @return List<String>    返回类型 
	* @throws 
	* @user 310854-liuzhenhua
	 */
	private List<String> exportTransferDepotAddress(DepotAddressEntity entity){
		if(null == entity){
			return null;
		}
		List<String> result = new ArrayList<String>();
		result.add(entity.getDepotName());
		result.add(entity.getDepotStateStr());
		result.add(entity.getDepotTypeStr());
		result.add(entity.getProvCode());
		result.add(entity.getProvCodeStr());
		result.add(entity.getCityCode());
		result.add(entity.getCityCodeStr());
		result.add(entity.getCountyCode());
		result.add(entity.getCountyCodeStr());
		result.add(entity.getAddress());
		result.add(entity.getLongitude());
		result.add(entity.getLatitude());
		result.add(entity.getDepotRemark());
		result.add(entity.getOrgCode());
		result.add(entity.getOrgName());
		result.add(entity.getRepulseReason());
		if(null != entity.getConfirmDateTime()){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			
			result.add(sdf.format(entity.getConfirmDateTime()));
		} else {
			result.add("");
		}
		return result;
	}
}
