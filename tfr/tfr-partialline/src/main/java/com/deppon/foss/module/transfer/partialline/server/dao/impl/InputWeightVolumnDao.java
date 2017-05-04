/**
 * @author foss 257200
 * 2015-9-1
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IInputWeightVolumnDao;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InputWeightVolumnEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnDto;

/**
 * @author 257220
 *
 */
public class InputWeightVolumnDao extends iBatis3DaoImpl implements IInputWeightVolumnDao  {


	/**
	 * 前缀
	 */
	private static String prefix = "foss.partialline.inputWeightVolumnMapper.";

	/**
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @author 257220
	 * @date 2015-9-1上午10:01:13
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IInputWeightVolumnDao#queryInputWeightVolumnBaseInfo(com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InputWeightVolumnDto> queryInputWeightVolumnBaseInfo(
			InputWeightVolumnDto dto, int start, int limit) {
		// 分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(prefix + "queryInputWeightVolumnBaseInfo", dto, rowBounds);
	}
	
	/**
	 *查询总数 
	 *@param dto
	 *@return
	 *@date  2015-11-14下午4:26:22
	 *@author 257220
	 */
	public Long queryInputWeightVolumnBaseInfoCount(InputWeightVolumnDto dto){
		return (Long)this.getSqlSession().selectOne(prefix + "queryInputWeightVolumnBaseInfoCount",dto);
	}
	
	/**
	 * @param parentWaybillNo
	 * @return
	 * @author 257220
	 * @date 2015-9-7下午12:26:37
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InputWeightVolumnDto> queryInputWeightVolumnInfo(String parentWaybillNo){
		return this.getSqlSession().selectList(prefix + "queryInputWeightVolumnInfo",parentWaybillNo);
	}

	/**
	 * @param waybillNo
	 * @return
	 * @author 257220
	 * @date 2015-9-8下午6:00:37
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IInputWeightVolumnDao#queryInputWeightVolumnCount(java.lang.String)
	 */
	@Override
	public int queryInputWeightVolumnCount(String waybillNo) {
		return  (Integer)this.getSqlSession().selectOne(prefix + "queryInputWeightVolumnCount",waybillNo);
	}

	/**
	 * @param inputWeightVolumnDto
	 * @author 257220
	 * @date 2015-9-8下午6:57:21
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IInputWeightVolumnDao#addWeightVolumnInfo(com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnDto)
	 */
	@Override
	public void addWeightVolumnInfo(InputWeightVolumnDto inputWeightVolumnDto) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(prefix + "addWeightVolumnInfo",inputWeightVolumnDto);
	}

	/**
	 * @param inputWeightVolumnDto
	 * @author 257220
	 * @date 2015-9-8下午6:57:21
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IInputWeightVolumnDao#updateWeightVolumnInfo(com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnDto)
	 */
	@Override
	public void updateWeightVolumnInfo(InputWeightVolumnDto inputWeightVolumnDto) {
		this.getSqlSession().update(prefix + "updateWeightVolumnInfo",inputWeightVolumnDto);
	}

	@Override
	@SuppressWarnings("unchecked")
	public InputWeightVolumnEntity queryInputWeightVolumnByWaybillNo(
			String waybillNo,String serialNo) {
		 Map<String,String> parmMap = new HashMap<String,String>();
		 parmMap.put("waybillNo", waybillNo);
		 parmMap.put("serialNo", serialNo);
		 List<InputWeightVolumnEntity> list = this.getSqlSession().selectList(prefix + "queryInputWeightVolumnByWaybillNo",parmMap);
		 if(list != null && list.size() > 0){
			 return list.get(0);
		 }
		 return null;
	}
}
