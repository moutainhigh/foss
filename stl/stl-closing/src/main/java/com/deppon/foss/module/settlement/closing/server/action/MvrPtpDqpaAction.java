package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPtpDqpaService;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrPtpDqpaVo;

/**
 * 合伙人德启代付月报表action
 * 
 * @author gpz
 * @date 2016年3月19日
 */
public class MvrPtpDqpaAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6922373603988416600L;

	/**
	 * Logger
	 */
	private static Logger logger = LogManager.getLogger(MvrPtpDqpaAction.class);

	
	/**
	 * 合伙人德启代付月报表service
	 */
	@Autowired
	private IMvrPtpDqpaService mvrPtpDqpaService;
	
	/**
	 * 合伙人德启代付月报表Vo
	 */
	private MvrPtpDqpaVo vo = new MvrPtpDqpaVo();
	
	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;

	/**
	 * 根据产品CODE找对应的名称
	 */
	@Autowired
	private IProductService productService;

	
	/**
	 * 分页查询合伙人德启代付月报表数据
	 * 
	 * @author gpz
	 * @date 2016年3月21日
	 */
	@JSON
	public String queryMvrPtpDqpa() {
		try {
			//获取当前用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			vo.getQueryDto().setUserCode(currentInfo.getEmpCode());
			
			//总记录数默认为0
			Long totalCount = 0L;
			//根据条件查询合伙人德启代付月报表数据总数
			totalCount = mvrPtpDqpaService.queryMvrPtpDqpaEntityTotalCount(vo.getQueryDto());
			if(totalCount != null && totalCount.intValue() > 0){
				vo.setMvrPtpDqpaEntityList(mvrPtpDqpaService.queryMvrPtpDqpaByParams(vo.getQueryDto(),this.getStart(),this.getLimit()));
			}else{
				vo.setMvrPtpDqpaEntityList(null);
			}
			this.setTotalCount(totalCount);
			
			// 正常返回
			return returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			logger.error(e.getMessage(), e);
			// 异常返回
			return returnError(e);
		}
	}

	
	
	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the execlName
	 */
	public String getExeclName() {
		return execlName;
	}

	/**
	 * @param execlName the execlName to set
	 */
	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}

	/**
	 * @return the vo
	 */
	public MvrPtpDqpaVo getVo() {
		return vo;
	}

	/**
	 * @param vo the vo to set
	 */
	public void setVo(MvrPtpDqpaVo vo) {
		this.vo = vo;
	}
	
}
