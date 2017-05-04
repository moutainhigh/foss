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
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrSummaryRService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrSummaryREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrSummaryRDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrSummaryRVo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 02业务重分类汇总报表Action
 * @author 340778 foss
 * @createTime 2016年8月18日 下午4:49:21
 */
public class MvrSummaryRAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/** 日志 LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(MvrRfdEntityAction.class);
	/** 
	 * 02业务重分类汇总报表服务
	 * */
	private IMvrSummaryRService MvrSummaryRService;
	
	private MvrSummaryRVo MvrSummaryRVo;

	/**
	 * 查询02业务重分类汇总报表
	 * @return
	 */
	@JSON
	public String querySummaryRByConditions(){
		try {
			//封装Vo中的查询参数到Dto中
			MvrSummaryRDto dto = checkAndPackageVoToDto(MvrSummaryRVo); 
			//查询总数
			MvrSummaryRDto totalDto = MvrSummaryRService.queryTotalByConditions(dto);
			List<MvrSummaryREntity> mvrSummaryRList = null;
			if(null != totalDto && totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0){
				//查询02业务重分类汇总报表记录
				mvrSummaryRList = MvrSummaryRService.queryByConditions(dto,start, limit);
				totalDto.setPeriod("汇总");
				totalDto.setProductCode("总条数:"+totalDto.getCount());
				mvrSummaryRList.add(totalDto);
			}
			// 设置Vo的统计Dto
			MvrSummaryRVo.setMvrSummaryRDto(totalDto);
			// 设置Vo的报表集合
			MvrSummaryRVo.setMvrSummaryRList(mvrSummaryRList);
		} catch(SettlementException e){
			LOGGER.error("查询02业务重分类汇总报表异常 "+e.getErrorCode());
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 检查并封装查询条件.
	 * 
	 */
	private MvrSummaryRDto checkAndPackageVoToDto(MvrSummaryRVo vo) {

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
		MvrSummaryRDto dto=new MvrSummaryRDto();
		try {
			BeanUtils.copyProperties(dto, vo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public void setMvrSummaryRService(IMvrSummaryRService MvrSummaryRService) {
		this.MvrSummaryRService = MvrSummaryRService;
	}
	public MvrSummaryRVo getMvrSummaryRVo() {
		return MvrSummaryRVo;
	}
	public void setMvrSummaryRVo(MvrSummaryRVo MvrSummaryRVo) {
		this.MvrSummaryRVo = MvrSummaryRVo;
	}
}
