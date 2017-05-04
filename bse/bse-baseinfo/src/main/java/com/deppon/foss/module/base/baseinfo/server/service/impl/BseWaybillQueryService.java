 
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ITrackRecordDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDefinedQueryDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IWaybillMarkDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBseWaybillQueryService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.QueryingConstant;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDefinedQueryConditionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaybillMarkEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserDefinedQueryDto;
import com.deppon.foss.util.CollectionUtils;

/**
 * 综合查询标记和跟踪记录Service
 * @author 101911-foss-zhouChunlai
 * @date 2013-6-1 下午5:20:38
 */
public class BseWaybillQueryService implements IBseWaybillQueryService {

	/**
	 * 日志打印对象声明
	 */
	//private static final Logger log = Logger.getLogger(BseWaybillQueryService.class);
	/**
	 * 自定义查询 DAO
	 */
	private IUserDefinedQueryDao userDefinedQueryDao;
	/**
	 * 跟踪记录查询 DAO
	 */
	private ITrackRecordDao trackRecordDao;
	/**
	 * 紧急标记 DAO
	 */
	private IWaybillMarkDao waybillMarkDao;
	@Override
	public int deleteUserDefinedQuerySchemeByCode(String[] codes,
			String modifyUser) {
		int schemev = userDefinedQueryDao.deleteUserDefinedQuerySchemeByCode(
				codes, modifyUser);
		// 同时 作废 查询条件
		userDefinedQueryDao.deleteUserDefinedQueryConditionBySchemeCode(codes,
				modifyUser);
		return schemev;
	}

	@Override
	public int addUserDefinedQueryDto(UserDefinedQueryDto dto, String createUser) {
		int queryCon = 0;
		// 新增方案
		dto.setCreateUser(createUser);
		dto.setCreateDate(new Date());
		queryCon += userDefinedQueryDao.addUserDefinedQueryScheme(dto);
		// 新增条件
		for (UserDefinedQueryConditionEntity con : dto.getUserQueryConditions()) {
			con.setCreateUser(createUser);
			con.setCreateDate(new Date());
			con.setSchemeCode(dto.getCode());
			queryCon += userDefinedQueryDao.addUserDefinedQueryCondition(con);
		}
		return queryCon == (dto.getUserQueryConditions().size() + 1) ? 1 : -1;
	}

	@Override
	public int updateUserDefinedQueryDto(UserDefinedQueryDto dto,
			String modifyUser) {
		List<UserDefinedQueryDto> dtoList = queryQueryDtos(dto);
		UserDefinedQueryDto sourceDto = CollectionUtils.isNotEmpty(dtoList) ? dtoList
				.get(0) : new UserDefinedQueryDto();
		Map<String, List<UserDefinedQueryConditionEntity>> optMap = convertOperatorQueryDto(
				sourceDto, dto, modifyUser);
		List<UserDefinedQueryConditionEntity> addCon = optMap
				.get(QueryingConstant.ADD_CON);
		List<UserDefinedQueryConditionEntity> delCon = optMap
				.get(QueryingConstant.DEL_CON);
		List<UserDefinedQueryConditionEntity> updateCon = optMap
				.get(QueryingConstant.UPDATE_CON);
		int queryCon = 0;
		// 修改方案
		if (!StringUtil.equals(sourceDto.getName(), dto.getName())) {
			queryCon += userDefinedQueryDao.updateUserDefinedQueryScheme(dto);
		}
		// 新增条件
		for (UserDefinedQueryConditionEntity con : addCon) {
			queryCon += userDefinedQueryDao.addUserDefinedQueryCondition(con);
		}
		// 修改条件
		for (UserDefinedQueryConditionEntity con : updateCon) {
			queryCon += userDefinedQueryDao
					.updateUserDefinedQueryCondition(con);
		}
		// 删除条件
		if (CollectionUtils.isNotEmpty(delCon)) {
			String[] codes = new String[delCon.size()];
			int i = 0;
			for (UserDefinedQueryConditionEntity con : delCon) {
				codes[i] = con.getId();
				i++;
			}
			queryCon += userDefinedQueryDao
					.deleteUserDefinedQueryConditionByCode(codes, modifyUser);
		}
		return queryCon == (addCon.size() + updateCon.size() + 1 + 1) ? 1 : -1;
	}
	private List<UserDefinedQueryDto> queryQueryDtos(UserDefinedQueryDto entity) {
		if (entity != null) {
			return userDefinedQueryDao.queryUserDefinedQueryDtos(entity);
		}
		return null;
	}
	/**
	 * 
	 * <p>
	 * 返回操作的 查询条件DTO，包括新增，修改，删除
	 * </p>
	 * 
	 * @author FOSS-073586-LIXUEXING
	 * @date 2013-1-25 下午3:59:17
	 * @param map
	 * @return
	 * @see
	 */
	private Map<String, List<UserDefinedQueryConditionEntity>> convertOperatorQueryDto(
			UserDefinedQueryDto sourceDto, UserDefinedQueryDto targetDto,
			String modifyUser) {
		Map<String, List<UserDefinedQueryConditionEntity>> operatorMap = new HashMap<String, List<UserDefinedQueryConditionEntity>>();
		List<UserDefinedQueryConditionEntity> addCon = new ArrayList<UserDefinedQueryConditionEntity>();
		List<UserDefinedQueryConditionEntity> delCon = new ArrayList<UserDefinedQueryConditionEntity>();
		List<UserDefinedQueryConditionEntity> updateCon = new ArrayList<UserDefinedQueryConditionEntity>();
		List<UserDefinedQueryConditionEntity> sourceCon = sourceDto
				.getUserQueryConditions();
		List<UserDefinedQueryConditionEntity> targetCon = targetDto
				.getUserQueryConditions();
		for (UserDefinedQueryConditionEntity con : targetCon) {
			// id为空则为新增
			if (StringUtil.isEmpty(con.getId())) {
				con.setCreateUser(modifyUser);
				con.setCreateDate(new Date());
				con.setSchemeCode(targetDto.getCode());
				addCon.add(con);
			} else {
				// 源list中实体存在 id相同但是 具体 条件不同 则为 修改
				for (UserDefinedQueryConditionEntity souce_con : sourceCon) {
					if (StringUtil.equals(souce_con.getId(), con.getId())
							&& !souce_con.equals(con)) {
						con.setModifyUser(modifyUser);
						con.setModifyDate(new Date());
						updateCon.add(con);
					}
				}
			}
		}
		for (UserDefinedQueryConditionEntity souce_con : sourceCon) {
			// 如果源list中实体 不存在于 目标实体 则为删除
			if (!conditionListHasCon(souce_con, targetCon)) {
				souce_con.setModifyUser(modifyUser);
				souce_con.setModifyDate(new Date());
				delCon.add(souce_con);
			}
		}
		operatorMap.put(QueryingConstant.ADD_CON, addCon);
		operatorMap.put(QueryingConstant.UPDATE_CON, updateCon);
		operatorMap.put(QueryingConstant.DEL_CON, delCon);
		return operatorMap;
	}
	@Override
	public int addWaybillMark(WaybillMarkEntity entity) {
		return this.waybillMarkDao.addWaybillMark(entity);
	}
	/**
	 * 
	 * <p>
	 * 判断集合中是否有 指定的 查询条件bean类 判断依据是 如果 id 条件 比较符 比较值 逻辑符 相同则为相同
	 * </p>
	 * 
	 * @author FOSS-073586-LIXUEXING
	 * @date 2013-1-25 下午3:59:17
	 * @param map
	 * @return boolean 相同则返回true，不同则返回false
	 * @see
	 */
	private boolean conditionListHasCon(
			UserDefinedQueryConditionEntity condition,
			List<UserDefinedQueryConditionEntity> list) {
		for (UserDefinedQueryConditionEntity con : list) {
			if (StringUtil.equals(condition.getId(), con.getId())) {
				return true;
			}
		}
		return false;
	}
	@Override
	public int updateWaybillMark(WaybillMarkEntity entity) {
		return this.waybillMarkDao.updateWaybillMark(entity);
	}
	@Override
	public int addWaybillMarkList(String[] codeStr, String returnInt) {
		return this.waybillMarkDao.addWaybillMarkList(codeStr, returnInt);
	}

	@Override
	public int addTrackRecord(TrackRecordEntity entity) {
		return this.trackRecordDao.addTrackRecord(entity);
	}

	
	public IUserDefinedQueryDao getUserDefinedQueryDao() {
		return userDefinedQueryDao;
	}

	
	public void setUserDefinedQueryDao(IUserDefinedQueryDao userDefinedQueryDao) {
		this.userDefinedQueryDao = userDefinedQueryDao;
	}

	
	public ITrackRecordDao getTrackRecordDao() {
		return trackRecordDao;
	}

	
	public void setTrackRecordDao(ITrackRecordDao trackRecordDao) {
		this.trackRecordDao = trackRecordDao;
	}

	
	public IWaybillMarkDao getWaybillMarkDao() {
		return waybillMarkDao;
	}

	
	public void setWaybillMarkDao(IWaybillMarkDao waybillMarkDao) {
		this.waybillMarkDao = waybillMarkDao;
	}
 

} 