package com.deppon.foss.module.base.baseinfo.server.service;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.pojo.domain.ecs2foss.ExpressPartSalesDeptEcsEntity;
import com.deppon.esb.pojo.domain.ecs2foss.ResponseResult;
import com.deppon.esb.pojo.domain.ecs2foss.SyncExpressPartSalesRequest;
import com.deppon.esb.pojo.domain.ecs2foss.SyncExpressPartSalesResponse;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity;
import com.deppon.foss.util.define.FossConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class ExpressPartSalesDeptListener implements IProcess{
	//日志记录
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressPartSalesDeptListener.class);

	
	private IExpressPartSalesDeptService expressPartSalesDeptService;

	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}
	
	@Override
	public Object process(Object req) {
		LOGGER.info("------------接收快递映射点部信息开始--------------");
		
		SyncExpressPartSalesResponse response = new SyncExpressPartSalesResponse();
		SyncExpressPartSalesRequest request = (SyncExpressPartSalesRequest) req;
		List<ResponseResult> resultList = new ArrayList<ResponseResult>();
		
		for(ExpressPartSalesDeptEcsEntity dto :  request.getExpressPartSalesDeptList()){
			{
				//创建对象
				try{
					LOGGER.info("请求参数:"+ com.alibaba.fastjson.JSONObject.toJSONString(dto));
					//设置转换类
					if(StringUtils.isBlank(dto.getId())){
						throw new ESBBusinessException("id is null");
					}
					
					ExpressPartSalesDeptEntity entity = new ExpressPartSalesDeptEntity();
					entity.setId(dto.getId());
					entity.setPartCode(dto.getPartCode());
					entity.setSalesDeptCode(dto.getSalesDeptCode());
					entity.setCreateTime(dto.getCreateTime());
					entity.setCreateUserCode(dto.getCreateUserCode());
					entity.setModifyUserCode(dto.getModifyUserCode());
					entity.setModifyTime(dto.getModifyTime());
					entity.setVersionNo(dto.getVersionNo());
					entity.setBeginTime(dto.getBeginTime());
					entity.setEndTime(dto.getEndTime());
					entity.setActive(dto.getActive());
					if(NumberConstants.NUMBER_1==dto.getChangeType()){
						expressPartSalesDeptService.sysAddExpressPartSalesDept(entity);
					}
					if(NumberConstants.NUMBER_3==dto.getChangeType()){
						expressPartSalesDeptService.sysDeleteExpressPartSalesDept(entity);
					}
					if(NumberConstants.NUMBER_2==dto.getChangeType()){
						expressPartSalesDeptService.sysUpdateExpressPartSalesDept(entity);
					}
					ResponseResult result=new ResponseResult();
					result.setId(dto.getId());
					result.setCode(dto.getSalesDeptCode());
					result.setInfo("success");
					result.setActive(FossConstants.ACTIVE);
					result.setExecuteTime(new Date().getTime());
					resultList.add(result);
				}catch(Exception e) {
					LOGGER.error(e.getMessage(),e);
					ResponseResult failInfo = new ResponseResult();
					failInfo.setId(dto.getId());
					failInfo.setCode(dto.getSalesDeptCode());
					failInfo.setInfo(e.getMessage());
					failInfo.setActive(FossConstants.INACTIVE);
					failInfo.setExecuteTime(new Date().getTime());
					resultList.add(failInfo);
				}	
			}	
		}
		LOGGER.info("------------接收快递映射点部信息结束--------------");
		response.setResponseResultList(resultList);
		return null;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		return null;
	}


}
