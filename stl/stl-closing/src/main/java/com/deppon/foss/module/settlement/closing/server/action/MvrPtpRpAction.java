/**
 * 
 */
package com.deppon.foss.module.settlement.closing.server.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPtpRpService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpRpEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpRpDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrPtpRpVo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * @author 231438
 * 合伙人奖罚月报表
 */
public class MvrPtpRpAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/** 日志 LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(MvrRfdEntityAction.class);
	/** 
	 * 合伙人奖罚月报表服务
	 * */
	private IMvrPtpRpService mvrPtpRpService;
	
	private MvrPtpRpVo mvrPtpRpVo;

	/**
	 * 查询合伙人奖罚月报表
	 * @return
	 */
	@JSON
	public String queryPtpRpByConditions(){
		try {
			//封装Vo中的查询参数到Dto中
			MvrPtpRpDto dto = checkAndPackageVoToDto(mvrPtpRpVo); 
			//查询总数
			MvrPtpRpDto totalDto = mvrPtpRpService.queryTotalByConditions(dto);
			List<MvrPtpRpEntity> mvrPtpRpList = null;
			if(null != totalDto && totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0){
				//查询合伙人奖罚月报表记录
				mvrPtpRpList = mvrPtpRpService.queryByConditions(dto,start, limit);
				totalDto.setPeriod("汇总");
				totalDto.setProductCode("总条数:"+totalDto.getCount());
				mvrPtpRpList.add(totalDto);
			}
			// 设置Vo的统计Dto
			mvrPtpRpVo.setMvrPtpRpDto(totalDto);
			// 设置Vo的报表集合
			mvrPtpRpVo.setMvrPtpRpList(mvrPtpRpList);
		} catch(SettlementException e){
			LOGGER.error("查询合伙人奖罚月报表异常 "+e.getErrorCode());
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 检查并封装查询条件.
	 * 
	 */
	private MvrPtpRpDto checkAndPackageVoToDto(MvrPtpRpVo vo) {

		// 专线到达VO非空判断
		if (null == vo) {
			// 内部错误，专线到达VO参数为空！
			throw new SettlementException("内部错误，VO参数为空！");
		}

		// 统计期间非空判断
		if (StringUtil.isBlank(vo.getPeriod())) {
			// 统计期间不能为空！
			throw new SettlementException("统计期间不能为空！");
		}

		MvrPtpRpDto dto = new MvrPtpRpDto();
		try {
			// 把vo上属性拷贝到dto上
			BeanUtils.copyProperties(dto, vo);

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

		} catch (IllegalAccessException e) {
			// BeanUtils.copyProperties异常
			LOGGER.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			// BeanUtils.copyProperties异常
			LOGGER.error(e.getMessage(), e);
		}
		return dto;
	}
	
	public void setMvrPtpRpService(IMvrPtpRpService mvrPtpRpService) {
		this.mvrPtpRpService = mvrPtpRpService;
	}
	public MvrPtpRpVo getMvrPtpRpVo() {
		return mvrPtpRpVo;
	}
	public void setMvrPtpRpVo(MvrPtpRpVo mvrPtpRpVo) {
		this.mvrPtpRpVo = mvrPtpRpVo;
	}
}
