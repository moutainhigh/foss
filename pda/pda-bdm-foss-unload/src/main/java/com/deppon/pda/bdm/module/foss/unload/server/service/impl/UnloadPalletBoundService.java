package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.BindingScanEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.PalletBindingEntity;

/**
 * 卸车托盘绑定服务类
 * 
 * @author wenwuneng
 * @date 2013-08-09
 * @version 1.0
 * @since
 */
public class UnloadPalletBoundService implements
		IBusinessService<Void, PalletBindingEntity> {
	//private static final Logger logger = Logger.getLogger(UnloadPalletBoundService.class);
	private IPDATrayScanService pdaTrayScanService;
	private IUnloadDao unloadDao;
	@Override
	public PalletBindingEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		PalletBindingEntity entity = new PalletBindingEntity();
		entity = JsonUtil.parseJsonToObject(PalletBindingEntity.class,
				asyncMsg.getContent());
		//部门编号
		entity.setDeptCode(asyncMsg.getDeptCode());
		//pda编号
		entity.setPdaCode(asyncMsg.getPdaCode());
		//用户编号
		entity.setScanUser(asyncMsg.getUserCode());
		//扫描类型
		entity.setScanType(asyncMsg.getOperType());
		//上传时间
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, PalletBindingEntity param)
			throws PdaBusiException {
		this.validate(asyncMsg, param);
		//封装扫描信息进入本地数据库
	  try{
		List<BindingScanEntity> bindingScanInfoList= param.getBindingScanInfoList();
		
		PalletBindingEntity entity=param;
		unloadDao.saveUnldSindingScan(entity);
		PDATrayScanTaskEntity fossEntity=new PDATrayScanTaskEntity();
		List<PDATrayScanDetailEntity> fossDetailList=new ArrayList<PDATrayScanDetailEntity>();
		for(BindingScanEntity scanEntity:bindingScanInfoList){
			//保存绑定扫描明细（包含唯一绑定唯一编码，和绑定时间）
			scanEntity.setId(UUIDUtils.getUUID());
			//绑定唯一编号
			scanEntity.setBindingNo(entity.getBindingNo());
			//绑定时间
			scanEntity.setBindingDate(entity.getBindingDate());
			//保存  
			scanEntity.setSerialNo(scanEntity.getSn().toString());
			unloadDao.saveUnldSindingScanDetail(scanEntity);
			
			for(String serialNo:scanEntity.getSn()){
				PDATrayScanDetailEntity fossDetailEntity=new PDATrayScanDetailEntity();
				fossDetailEntity.setWaybillNo(scanEntity.getWl());//运单号
				fossDetailEntity.setSerialNo(serialNo);//流水号
				fossDetailList.add(fossDetailEntity);
			}
			//封装绑定任务明细List集合给foss
//			fossDetailEntity.setId(scanEntity.getId());//id
//			fossDetailEntity.setDestDept(scanEntity.getDeptDestCode());//目的站编码
//			if(scanEntity.getDeptDestName()==null || scanEntity.getDeptDestName().equals("")){
//				logger.info("-----------托盘扫描上传运单任务明细：获取运目的站名称为空---------------");
//			}
			
//			fossDetailEntity.setDestDeptName(scanEntity.getDeptDestName());//目的站名称
			
		}
		//封装绑定扫描实体
		fossEntity.setBindingCode(entity.getScanUser());//设置绑定人工号
		fossEntity.setTaskNo(entity.getBindingNo());//绑定唯一编号
		fossEntity.setBindingDept(entity.getDeptCode());//绑定部门编号
		fossEntity.setTraytaskCreatTime(entity.getBindingDate());//绑定任务时间
		fossEntity.setTrayScanDetails(fossDetailList);//绑定任务明细
		fossEntity.setUnloadTaskNo(param.getUnloadTaskCode());
		
		if(param.getOperationType()==null || param.getOperationType().equals("")){
			fossEntity.setOperationType("UNSCAN");  //兼容老版本,旧版本改字段为空,默认叉车扫描
		}else{
			fossEntity.setOperationType(param.getOperationType());
		}
		
		
		//调用FOSS接口
		pdaTrayScanService.createTrayScanTask(fossEntity);
		return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author wenwuneng
	 * @date 2013-8-9 上午9:54
	 * @return
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#validate()
	 */
	private void validate(AsyncMsg asyncMsg, PalletBindingEntity entity) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "PalletSindingEntity");
		//托盘绑定时间
		Argument.notNull(entity.getBindingDate(), "PalletSindingEntity.bindingDate");
		//托盘绑定唯一编码
		Argument.hasText(entity.getBindingNo(), "PalletSindingEntity.bindingNo");
//		//运单号
//		Argument.hasText(entity.getWblCode(), "PalletSindingEntity.wblCode");
		//扫描列表
		Argument.notEmpty(entity.getBindingScanInfoList(),"PalletSindingEntity.bindingScanInfoList");
	}


	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author wenwuneng
	 * @date 2013-8-9 上午9:54
	 * @return
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#getOperType()
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_PALLET_BOUND_SCAN.VERSION;
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author wenwuneng
	 * @date 2013-8-9 上午9:54
	 * @return
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#isAsync()
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}

	public void setPdaTrayScanService(IPDATrayScanService pdaTrayScanService) {
		this.pdaTrayScanService = pdaTrayScanService;
	}
	

	

}
