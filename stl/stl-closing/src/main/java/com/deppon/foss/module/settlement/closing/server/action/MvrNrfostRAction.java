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
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfostRService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfostREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostRDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrNrfostRVo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 02特殊业务重分类报表Action
 * @author 340778 foss
 * @createTime 2016年8月17日 下午12:26:07
 */
public class MvrNrfostRAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/** 日志 LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(MvrNrfostRAction.class);
	/** 
	 * 02特殊业务重分类报表服务
	 * */
	private IMvrNrfostRService MvrNrfostRService;
	
	private MvrNrfostRVo MvrNrfostRVo;

	/**
	 * 查询02特殊业务重分类报表
	 * @return
	 */
	@JSON
	public String queryNrfostRByConditions(){
		try {
			//封装Vo中的查询参数到Dto中
			MvrNrfostRDto dto = checkAndPackageVoToDto(MvrNrfostRVo); 
			//查询总数
			MvrNrfostRDto totalDto = MvrNrfostRService.queryTotalByConditions(dto);
			List<MvrNrfostREntity> mvrNrfostRList = null;
			if(null != totalDto && totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0){
				//查询02特殊业务重分类报表记录
				mvrNrfostRList = MvrNrfostRService.queryByConditions(dto,start, limit);
				totalDto.setPeriod("汇总");
				totalDto.setProductCode("总条数:"+totalDto.getCount());
				mvrNrfostRList.add(totalDto);
			}
			// 设置Vo的统计Dto
			//MvrNrfostRVo.setMvrNrfostRDto(totalDto);
			// 设置Vo的报表集合
			MvrNrfostRVo.setMvrNrfostRList(mvrNrfostRList);
		} catch(SettlementException e){
			LOGGER.error("查询02特殊业务重分类报表异常 "+e.getErrorCode());
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 检查并封装查询条件.
	 * 
	 */
	private MvrNrfostRDto checkAndPackageVoToDto(MvrNrfostRVo vo) {

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
		
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 设置用户数据查询权限
		vo.setUserCode(currentInfo.getEmpCode());
		
		MvrNrfostRDto dto=new MvrNrfostRDto();
		try {
			BeanUtils.copyProperties(dto,vo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public void setMvrNrfostRService(IMvrNrfostRService MvrNrfostRService) {
		this.MvrNrfostRService = MvrNrfostRService;
	}
	public MvrNrfostRVo getMvrNrfostRVo() {
		return MvrNrfostRVo;
	}
	public void setMvrNrfostRVo(MvrNrfostRVo MvrNrfostRVo) {
		this.MvrNrfostRVo = MvrNrfostRVo;
	}
}
