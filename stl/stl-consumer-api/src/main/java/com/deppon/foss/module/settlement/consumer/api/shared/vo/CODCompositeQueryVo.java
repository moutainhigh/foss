package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeGridDto;

/**
 * 代收货款综合查询VO
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-30 下午2:58:08
 */
public class CODCompositeQueryVo implements Serializable {

	private static final long serialVersionUID = 217647512998963456L;

	/**
	 * 查询Vo
	 */
	private CODCompositeConditionVo queryVo;

	/**
	 * 表格集合
	 */
	private List<CODCompositeGridDto> gridVoSet;

	/**
	 * 代收货款日志
	 */
	private List<CODLogEntity> logVoSet;

	/**
	 * @return queryVo
	 */
	public CODCompositeConditionVo getQueryVo() {
		return queryVo;
	}

	/**
	 * @param queryVo
	 */
	public void setQueryVo(CODCompositeConditionVo queryVo) {
		this.queryVo = queryVo;
	}

	/**
	 * @return gridVoSet
	 */
	public List<CODCompositeGridDto> getGridVoSet() {
		return gridVoSet;
	}

	/**
	 * @param gridVoSet
	 */
	public void setGridVoSet(List<CODCompositeGridDto> gridVoSet) {
		this.gridVoSet = gridVoSet;
	}

	/**
	 * @return logVoSet
	 */
	public List<CODLogEntity> getLogVoSet() {
		return logVoSet;
	}

	/**
	 * @param logVoSet
	 */
	public void setLogVoSet(List<CODLogEntity> logVoSet) {
		this.logVoSet = logVoSet;
	}

}
