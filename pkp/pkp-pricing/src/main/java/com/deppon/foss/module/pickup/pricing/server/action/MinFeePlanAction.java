package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.MinFeePlanVo;

public class MinFeePlanAction extends AbstractAction {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(MinFeePlanAction.class);

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5340005527084814061L;

	/**
	 * 最低一票页面交互对象
	 */
	private MinFeePlanVo minFeePlanVo = new MinFeePlanVo();

	/**
	 * 最低一票业务对象
	 */
	private IMinFeePlanService minFeePlanService;

	@JSON
	public String addMinFeePlan() {
		try {
			minFeePlanService.addMinFeePlan(minFeePlanVo.getMinFeePlanEntity());
			return returnSuccess(MessageType.SAVE_MINFEEPLAN_SUCCESS);
		} catch (BusinessException e) {
			log.error("新增最低一票方案异常", e);
			return returnError(e.getMessage());
		}
	}

	/**
	 * 
	 * 分页查询最低一票方案
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 下午2:08:44
	 */
	@JSON
	public String selectMinFeePlanList() {
		try {
			MinFeePlanEntity minFeePlanEntity = new MinFeePlanEntity();
			minFeePlanEntity = minFeePlanVo.getMinFeePlanEntity();
			List<MinFeePlanEntity> minFeePlanEntityList = minFeePlanService
					.getMinFeePlanEntityByCondition(minFeePlanEntity,
							getStart(), getLimit());
			minFeePlanVo.setMinFeePlanEntityList(minFeePlanEntityList);
			this.setTotalCount(minFeePlanService
					.countMinFeePlanByCondition(minFeePlanEntity));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}

	/**
	 * 
	 * 删除方案
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-14 下午7:12:51
	 */
	@JSON
	public String deleteMinFeePlan() {
		try {
			minFeePlanService.deleteMinFeePlan(minFeePlanVo.getMinFeePlanIds());
			return returnSuccess(MessageType.DELETE_MINFEEPLAN_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}

	/**
	 * 
	 * 根据PrimaryKey查询记录
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 上午10:40:57
	 */
	@JSON
	public String selectMinFeePlanByPrimaryKey() {
		try {
			String id = minFeePlanVo.getMinFeePlanEntity().getId();
			MinFeePlanEntity minFeePlanEntity = minFeePlanService
					.getMinFeePlanEntityById(id);
			minFeePlanVo.setMinFeePlanEntity(minFeePlanEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			log.error("根据id查询最低一票方案异常", e);
			return returnError(e.getMessage());
		}
	}

	/**
	 * 
	 * 更新最低一票记录
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 上午10:42:29
	 */
	@JSON
	public String updateMinFeePlan() {
		try {
			minFeePlanService.updateMinFeePlanEntity(minFeePlanVo
					.getMinFeePlanEntity());
			return returnSuccess(MessageType.SAVE_MINFEEPLAN_SUCCESS);
		} catch (BusinessException e) {
			log.error("更新最低一票记录异常", e);
			return returnError(e.getMessage());
		}
	}

	/**
	 * 立即激活最低一票方案
	 * 
	 * @return
	 */
	@JSON
	public String activateImmediatelyMinFeePlan() {
		try {
			String id = minFeePlanVo.getMinFeePlanEntity().getId();
			Date newBeginTime = minFeePlanVo.getMinFeePlanEntity()
					.getBeginTime();
			minFeePlanService.activateImmediatelyMinFeePlan(id, newBeginTime);
			return returnSuccess("激活成功！");
		} catch (BusinessException e) {
			log.error("激活最低一票记录异常", e);
			return returnError(e.getMessage());
		}
	}

	/**
	 * 立即中止最低一票方案
	 * 
	 * @return
	 */
	@JSON
	public String stopImmediatelyMinFeePlan() {
		try {
			String id = minFeePlanVo.getMinFeePlanEntity().getId();
			Date newEndTime = minFeePlanVo.getMinFeePlanEntity().getEndTime();
			minFeePlanService.stopImmediatellyMinFeePlan(id, newEndTime);
			return returnSuccess("中止成功！");
		} catch (BusinessException e) {
			log.error("中止最低一票记录异常", e);
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 读取3级产品信息
	 * @return
	 */
	@JSON
	public String getLevel3Product(){
		try{
			minFeePlanVo.setProductEntityList(minFeePlanService.findProductByCondition());
			return returnSuccess();
		} catch (BusinessException e) {
			log.error("读取3级产品异常", e);
			return returnError(e.getMessage());
		}
	}

	public MinFeePlanVo getMinFeePlanVo() {
		return minFeePlanVo;
	}

	public void setMinFeePlanVo(MinFeePlanVo minFeePlanVo) {
		this.minFeePlanVo = minFeePlanVo;
	}

	public void setMinFeePlanService(IMinFeePlanService minFeePlanService) {
		this.minFeePlanService = minFeePlanService;
	}

}
