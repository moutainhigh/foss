package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncSalesDescExpandRequestTrans;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncSalesDescExpandService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.oms.inteface.domain.SalesDescExpandInfo;
import com.deppon.oms.inteface.domain.SyncSalesDescExpandRequest;

/**
 * 同步营业部自提派送扩展资料给周边系统，订单，快递
 * 
 * @author 273311
 * 
 */
public class SyncSalesDescExpandService implements ISyncSalesDescExpandService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncSalesDescExpandService.class);
	/**
	 * esb服务编码
	 */
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_EXPANSION_TABLE";
	 private IEsbErrorLoggingDao esbErrorLoggingDao;
		public void setEsbErrorLoggingDao(IEsbErrorLoggingDao esbErrorLoggingDao) {
			this.esbErrorLoggingDao = esbErrorLoggingDao;
		}
	/**
	 * <p>
	 * 同步营业部自提派送扩展资料给周边系统，订单，快递
	 * </p> 
	 * 
	 * @date 2016-3-23 下午3:48:24
	 * @author 273311
	 */
	@Override
	public void syncSalesDescExpand(List<SalesDescExpandEntity> salesDescExpandLis, String operateType) {
		if (CollectionUtils.isNotEmpty(salesDescExpandLis)) {
			SyncSalesDescExpandRequest request = new SyncSalesDescExpandRequest();
			List<SalesDescExpandInfo> salesDescExpandLst = new ArrayList<SalesDescExpandInfo>();
			for (SalesDescExpandEntity entity : salesDescExpandLis) {
				// 转换成esb对象
				SalesDescExpandInfo salesDescExpandInfo = this.transFossToEsb(
						entity, operateType);
				salesDescExpandLst.add(salesDescExpandInfo);
			}
			request.getSalesDescExpandInfo().addAll(salesDescExpandLst);
			// 服务编码
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(UUID.randomUUID().toString());
			accessHeader.setBusinessDesc1("同步营业部自提派送扩展资料给周边系统");
			accessHeader.setVersion("0.1");
			long startTime = System.currentTimeMillis();
			EsbErrorLogging erlog = new EsbErrorLogging();
			try {
				erlog.setParamenter(new SyncSalesDescExpandRequestTrans().fromMessage(request));
				erlog.setRequestSystem("ESB");
				erlog.setRequestTime(new Date());
				erlog.setCreateTime(new Date());
				erlog.setMethodDesc("同步营业部自提派送扩展资料给周边系统");
				erlog.setMethodName(this.getClass().getName()+".syncSalesDescExpand");
 				LOGGER.info("开始同步营业部自提派送扩展资料：\n"
						+ new SyncSalesDescExpandRequestTrans()
								.fromMessage(request));
				ESBJMSAccessor.asynReqeust(accessHeader, request);
				long responseTime = System.currentTimeMillis()-startTime; 
				if(responseTime>FossConstants.MAX_RESPONSE_TIME){
					erlog.setErrorMessage("响应超时");
					erlog.setResponseTime(responseTime);
					esbErrorLoggingDao.addErrorMessage(erlog);
				}
				LOGGER.info("结束同步营业部自提派送扩展资料：\n"
						+ new SyncSalesDescExpandRequestTrans()
								.fromMessage(request));
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				erlog.setResponseTime(System.currentTimeMillis()-startTime);
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				erlog.setErrorMessage(sw.toString());
				esbErrorLoggingDao.addErrorMessage(erlog);
				throw new SynchronousExternalSystemException(" 同步营业部自提派送信息",
						" 同步营业部信息 发送数据到ESB错误");
			}

		}
	}

	/**
	 * <p>
	 * 将foss对象封装成esb 对象
	 * </p>
	 * 
	 * @date 2016-3-23 下午4:05:27
	 * @author 273311
	 */
	private SalesDescExpandInfo transFossToEsb(
			SalesDescExpandEntity fossEntity, String operateType) {
		if (null == fossEntity) {
			return null;
		}
		SalesDescExpandInfo info = new SalesDescExpandInfo();
		// id
		info.setId(fossEntity.getId());
		// 部门编码
		info.setCode(fossEntity.getCode());
		// 扩展类型
		info.setDescType(fossEntity.getDescType());
		// 内容
		info.setDescContent(fossEntity.getDescContent());
		// 序列
		info.setDescOrder(fossEntity.getDescOrder());
		// 内容
		info.setActive(fossEntity.getActive());
		// 创建时间
		info.setCreatTime(fossEntity.getCreateDate());
		// 更新时间
		info.setModifyTime(fossEntity.getModifyDate());
		// 创建人
		info.setCreateUserCode(fossEntity.getCreateUser());
		// 更新人
		info.setModifyUserCode(fossEntity.getModifyUser());
		// 版本号
		info.setVersionNo((double) fossEntity.getVersionNo());
		// 操作类别：(1、新增；2、删除)
		 if(FossConstants.INACTIVE.equals(fossEntity.getActive())){
				//删除
			 info.setOperateType("2");
			}else{
				//新增
				info.setOperateType("1");
			}
		return info;

	}
}
