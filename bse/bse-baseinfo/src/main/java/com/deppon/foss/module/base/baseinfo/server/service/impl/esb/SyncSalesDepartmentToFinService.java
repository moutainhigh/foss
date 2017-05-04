package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.inteface.domain.foss.BusinessDeptreq;
import com.deppon.esb.pojo.transformer.jaxb.BusinessDeptreqTrans;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncSalesDepartmentToFinService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SyncSalesInfoToFinSelfDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
/**
 * foss 同步营业部部分信息给发票项目JMS接口客户端
 * @author 130566
 *
 */
public class SyncSalesDepartmentToFinService implements
		ISyncSalesDepartmentToFinService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncSalesDepartmentToFinService.class);
	
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYNC_DEPT_ADDRESS";
	/**
	 * 
	 *<p>t同步信息</p>	
	 * @date 2014-6-18 下午6:56:03
	 * @author 130566-ZengJunfan
	 * @param dto
	 */
	@Override
	public void syncSalesInfoToFinSelf(SyncSalesInfoToFinSelfDto dto) {
		//非空校验
		if(null !=dto){
			BusinessDeptreq request =new BusinessDeptreq();
			//转换成esb 对象
			request =transFossToEsb(request,dto);
			//服务编码
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(UUID.randomUUID().toString());
			accessHeader.setBusinessDesc1("同步 营业部信息");
			accessHeader.setVersion("0.1");
			try {
				LOGGER.info("开始调用 同步营业部：\n"+new BusinessDeptreqTrans().fromMessage(request));
				
				ESBJMSAccessor.asynReqeust(accessHeader, request);
				LOGGER.info("结束调用 同步营业部：\n"+new BusinessDeptreqTrans().fromMessage(request));
			} catch (ESBException e) {
				LOGGER.error(e.getMessage(), e);
				throw new SynchronousExternalSystemException(" 同步营业部信息"," 同步营业部信息 发送数据到ESB错误");
			}
		}

	}
	/**
	 * 
	 *<p>将foss对象封装成esb 对象</p>	
	 * @date 2014-6-19 下午6:05:27
	 * @author 130566-ZengJunfan
	 * @param request
	 * @param dto
	 * @return
	 */
	private BusinessDeptreq transFossToEsb(BusinessDeptreq request,
			SyncSalesInfoToFinSelfDto dto) {
		if(null !=dto){
			//营业部编码
			request.setBusinessDept(dto.getOrgCode());
			//营业部名称
			request.setBusinessName(dto.getOrgName());
			//营业部地址
			request.setBusinessAddr(dto.getOrgAddress());
			//营业部状态
			request.setStatus(dto.getActive());
		}
		return request;
	}

}
