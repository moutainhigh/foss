package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHeavyBubbleRatioService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HeavyBubbleRatioEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.HeavyBubbleRatioException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.HeavyBubbleRatioVo;

/**
 * @author 218392 张永雪
 *
 * 2014-11-19下午4:58:27
 */
public class HeavyBubbleRatioAction extends AbstractAction{
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HeavyBubbleRatioAction.class);
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 重泡比Service接口
	 */
	private IHeavyBubbleRatioService heavyBubbleRatioService;

	/**
	 * 重泡比信息VO
	 */
	private HeavyBubbleRatioVo heavyBubbleRatioVo = new HeavyBubbleRatioVo();
	
	/**
	 * 增加重泡比信息
	 * @author 218392 
	 */
	@JSON
	public String addHeavyBubbleRatio(){
		//判断重泡比信息的实体是否为null
		try {
			if(null == heavyBubbleRatioVo.getEntity()){
				LOGGER.info("增加重泡比传入的参数不能为空");
				return null;
			}
			HeavyBubbleRatioEntity entity = heavyBubbleRatioVo.getEntity();
			if(StringUtils.isEmpty(entity.getOutfield()) || StringUtils.isEmpty(entity.getHeavyBubbleParam())){
				LOGGER.info("外场或重泡比参数为空!");
				return null;
			}
			//判断外场是否存在
			
			if(heavyBubbleRatioService.queryOutfieldNameByHeavyBubbleRatio(entity.getOutfield())){
				LOGGER.info("外场已经存在，一个外场只能对应一个重泡比参数");
				throw new HeavyBubbleRatioException("外场已存在,请重新选择！");
			}
			//保存成功 ,返回一个对象“保存成功”
			heavyBubbleRatioService.addHeavyBubbleRatio(entity);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 作废重泡比信息
	 * @author 218392 
	 */
	@JSON
	public String deleteHeavyBubbleRatio(){
		try {
			if(null == heavyBubbleRatioVo.getIdList()){
				LOGGER.info("删除重泡比信息传入的参数不能为空!");
				return null;
			}
			//删除成功之后返回一个对象
			heavyBubbleRatioService.deleteHeavyBubbleRatio(heavyBubbleRatioVo.getIdList());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 修改重泡比信息
	 * @author 218392
	 */
	@JSON
	public String updateHeavyBubbleRatio(){
		try {
			if(null == heavyBubbleRatioVo.getEntity()){
				LOGGER.info("修改重泡比信息传入的参数不能为空");
				return null;
			}
			//修改成功之后返回一个对象
			heavyBubbleRatioService.updateHeavyBubbleRatio(heavyBubbleRatioVo.getEntity());
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	
	/**
	 * 分页查询重泡比信息
	 * @author 218392
	 */
	@JSON
	public String queryAllHeavyBubbleRatio(){
		try {
			List<HeavyBubbleRatioEntity> entityList = 
					heavyBubbleRatioService.queryAllHeavyBubbleRatio(heavyBubbleRatioVo.getEntity(), limit, start);
			heavyBubbleRatioVo.setEntityList(entityList);
			//查询总记录数
			Long totalCount = heavyBubbleRatioService.queryRecordCount(heavyBubbleRatioVo.getEntity());
			//设置totalCount
			this.setTotalCount(totalCount);
			
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	
	
	/**
	 * 获取重泡比信息Vo
	 * @return  the heavyBubbleRatioVo
	 */
	public HeavyBubbleRatioVo getHeavyBubbleRatioVo() {
		return heavyBubbleRatioVo;
	}

	/**
	 * 设置重泡比信息Vo
	 * @param heavyBubbleRatioVo
	 */
	public void setHeavyBubbleRatioVo(HeavyBubbleRatioVo heavyBubbleRatioVo) {
		this.heavyBubbleRatioVo = heavyBubbleRatioVo;
	}


	/**
	 * 设置重泡比Service
	 * @param heavyBubbleRatioService
	 */
	public void setHeavyBubbleRatioService(
			IHeavyBubbleRatioService heavyBubbleRatioService) {
		this.heavyBubbleRatioService = heavyBubbleRatioService;
	}
}
