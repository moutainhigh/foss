package com.deppon.pda.bdm.module.foss.crgreg.server.service.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.CrgregConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.crgreg.server.dao.ICrgRegDAO;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.InOutStockPostionScanEntity;

/**
 * 
 * TODO(修改库位)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-12-1 下午2:43:52,content:TODO
 * </p>
 * 
 * @author Administrator
 * @date 2013-07-15 上午10:56:52
 * @since
 * @version
 */
public class UpdateStockPositionService implements IBusinessService<Void,InOutStockPostionScanEntity> {
	private static final Log LOG = LogFactory.getLog(UpdateStockPositionService.class);
	public final static int NUM = 4;
	private IPDAStockService ipdaStockService;

	private UserCache userCache;
	
	private DeptCache deptCache;
	
	private ICrgRegDAO crgRegDAO;
	
	@Override
	public InOutStockPostionScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		InOutStockPostionScanEntity entity = 
				JsonUtil.parseJsonToObject(InOutStockPostionScanEntity.class, asyncMsg.getContent());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, InOutStockPostionScanEntity param)
			throws PdaBusiException {
		//参数校验
		validate(param,asyncMsg);
		crgRegDAO.saveUpdateStockPostionScan(param);
		LOG.info("----------上传入库位信息-------------");
		//构造FOSS接口调用参数
		InOutStockEntity inOutStockEntity = createFossArgs(asyncMsg, param);
		
		try {
			ipdaStockService.updateStockStockPosition(inOutStockEntity);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		
		return null;
	}

	private InOutStockEntity createFossArgs(AsyncMsg asyncMsg,
			InOutStockPostionScanEntity param) {
		// 获取流水号
		String serialNo = param.getLabelCode().substring(param.getLabelCode().length()-NUM);
		UserEntity userEntity = userCache.get(asyncMsg.getUserCode());
		// 部门编码
		String orgCode = deptCache.get(userEntity.getDeptId()).getDeptCode();
		// 用户名称
		String userName = userEntity.getUserName();
		LOG.info("serialNo:"+serialNo+" position:"+param.getPosition()+
				" orgCode:"+orgCode+" userCode"+asyncMsg.getUserCode() +
				" userName"+userName+" AreaCode:"+param.getAreaCode());
		//-------
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO(param.getWblCode());  //运单号
		inOutStockEntity.setSerialNO(serialNo); //流水号 
		inOutStockEntity.setGoodsAreaCode(param.getAreaCode());//货区编号
		inOutStockEntity.setOrgCode(orgCode);//部门编号
		inOutStockEntity.setPosition(param.getPosition()); //库位
		inOutStockEntity.setOperatorCode(asyncMsg.getUserCode());//用户编码
		inOutStockEntity.setOperatorName(userEntity.getUserName());//用户名称
		inOutStockEntity.setScanTime(param.getScanTime());// 扫描时间
		inOutStockEntity.setPdaDeviceNO(asyncMsg.getPdaCode());//PDA设备号
		return inOutStockEntity;
	}

	private void validate(InOutStockPostionScanEntity param,AsyncMsg asyncMsg) {
		Argument.notNull(param, "InOutStockPostionScanEntity");
		//运单号不能为空
		Argument.hasText(param.getWblCode(), "WblCode");
		//标签号不能为空
		Argument.hasText(param.getLabelCode(), "LabelCode");
		//库位号不能为空
		Argument.hasText(param.getPosition(), "Position");
		//货区号不能为空
		Argument.hasText(param.getAreaCode(), "AreaCode");
		//扫时间不为空
		Argument.notNull(param.getScanTime(), "Scantime");
		//用户编码不为空
		Argument.hasText(asyncMsg.getUserCode(), "Usercode");
	}

	@Override
	public String getOperType() {
		return CrgregConstant.OPER_TYPE_REG_IN_STOCK_POSTION.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public void setIpdaStockService(IPDAStockService ipdaStockService) {
		this.ipdaStockService = ipdaStockService;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public void setDeptCache(DeptCache deptCache) {
		this.deptCache = deptCache;
	}

	public void setCrgRegDAO(ICrgRegDAO crgRegDAO) {
		this.crgRegDAO = crgRegDAO;
	}
	
}
