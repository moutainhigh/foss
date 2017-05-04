package com.deppon.foss.module.transfer.packaging.server.service.impl;

import java.util.ArrayList;
import java.util.List;








import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPackCommonDao;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackCommonService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryWaybillPackEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.ValidatePackedDto;
import com.deppon.foss.util.CollectionUtils;

/**
 * @author foss-105795-wqh
 * @desc 存放PDA端与PC端的公共方法
 * @date 2014-04-10
 * */
public class PackCommonService implements IPackCommonService {

    private static final Logger LOGGER=LoggerFactory.getLogger(PackageMainPriceService.class);

	//PC与PDA共用接口
	private IPackCommonDao packCommonDao;
	
	/**
	 * 获取指定部门接口
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * @desc 根据运单号，查询带打包件数
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	public int queryUnPackQtyByWaybillNo(String waybillNo,String packageOrgCode){
		validateParm(waybillNo,packageOrgCode);
		return packCommonDao.queryUnPackQtyByWaybillNo(waybillNo,packageOrgCode);
	}
	
	/**
	 * @desc 根据运单号，查询包装后货物总件数
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	public int queryPackedQtyByWaybillNo(String waybillNo,String orgCode){
		
		validateParm(waybillNo,orgCode);

		return packCommonDao.queryPackedQtyByWaybillNo(waybillNo,orgCode);
	}
	
	
	/**
	 * @desc 根据运单号，查询打包装需求中还未打包的所有流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	public List<String> queryUnPackSeriaByWaybillNo(String waybillNo,String packageOrgCode){
		
		validateParm(waybillNo,packageOrgCode);

		return packCommonDao.queryUnPackSeriaByWaybillNo(waybillNo,packageOrgCode);
		
	}

	/**
	 * @desc 根据运单号，查询包装需求中已经打包装的流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-12
	 * @param waybillNo
	 * 
	 * */
	public List<String> queryRePackedSeriaListByWaybillNo(String waybillNo,String packageOrgCode){
		
		validateParm(waybillNo,packageOrgCode);

		return packCommonDao.queryRePackedSeriaListByWaybillNo(waybillNo,packageOrgCode);

	}
	
	
	/**
	 * @desc 根据运单号，查询实际包装中所有的新流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-12
	 * @param waybillNo
	 * 
	 * */
	public List<String> queryPackedNewSeriaListByWaybillNo(String waybillNo,String orgCode){
		
		validateParm(waybillNo,orgCode);

		return packCommonDao.queryRePackedSeriaListByWaybillNo(waybillNo,orgCode);

	}
	/**
	 * @desc 根据运单号，查询打包装需求中获剩下未打包的流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	public List<String> queryUnPackSeriaAfterByWaybillNo(String waybillNo,String packageOrgCode,String unPackType){
		
		validateParm(waybillNo,packageOrgCode);
        if(StringUtil.isBlank(unPackType))
        {
        	throw new TfrBusinessException("包装类型不能为空");
        }
		
		return packCommonDao.queryUnPackSeriaAfterByWaybillNo(waybillNo,packageOrgCode,unPackType);

		
	}
	
	/**
	 * @desc 作废包装需求中的流水号 
	 * @author foss-105795-wqh
	 * @date 2014-04-14
	 * @param waybillNo
	 * 
	 * */
	public void cancleUnPackReSerias(String waybillNo,String packageOrgCode,List<String> seriaNoList){
		
		validateParm(waybillNo,packageOrgCode);

		 if(CollectionUtils.isEmpty(seriaNoList)&&seriaNoList.size()<0)
		 {
			 throw new TfrBusinessException("作废流水号不存在");
		 }
		 packCommonDao.cancleUnPackReSerias(waybillNo,packageOrgCode,seriaNoList);

	}
	
	/**
	 * @desc 根据运单号，查询需求信息
	 * @author foss-105795-wqh
	 * @date 2014-04-15
	 * @param waybillNo
	 * 
	 * */
	public QueryWaybillPackEntity queryRePackByWaybillNo(String waybillNo,String orgCode){
		
		validateParm(waybillNo,orgCode);

		return packCommonDao.queryRePackByWaybillNo(waybillNo,orgCode);

	}
	
	//参数检验
	private void validateParm(String waybillNo,String orgCode)
	{
		
		if(StringUtil.isBlank(waybillNo))
		{
			throw new TfrBusinessException("运单号不能为空！");
		}
		if(StringUtil.isBlank(orgCode))
		{
			throw new TfrBusinessException("部门编码不能为空！");

		}
	}
	
	/*
	 * @desc 获取当前外场部门的code
	 * @author foss-105795-wqh
	 * @param 
	 * @ date 2014-04-11
	 * 
	 * */
    @SuppressWarnings("unused")
	private OrgAdministrativeInfoEntity getOutFieldCode(){
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity activeDeptInfo = FossUserContext
				.getCurrentDept();
		//组织对象
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoByCode(activeDeptInfo.getCode(),
							bizTypes);
	   if(org==null)
	   {
		   throw new TfrBusinessException("当前部门不存在");
		   
	   }
	   return org;
     }
	
    /**
	 * @desc 查询出所有合打的新流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-15
	 * @param waybillNo
	 * 
	 * */
	public List<String> queryPackedJoinSeriasByWaybillNo(String waybillNo,String orgCode){
		validateParm(waybillNo,orgCode);
		return packCommonDao.queryPackedJoinSeriasByWaybillNo(waybillNo,orgCode);

	}
	/**
	 * @desc 查询需要生成代办的流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-15
	 * @param waybillNo
	 * 
	 * */
	public List<String> queryToDoSeriasByWaybillNo(String waybillNo,String orgCode){
		validateParm(waybillNo,orgCode);
			return packCommonDao.queryToDoSeriasByWaybillNo(waybillNo,orgCode);
	}
	//set function

	public void setPackCommonDao(IPackCommonDao packCommonDao) {
		this.packCommonDao = packCommonDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	/**
	 * @desc 限制加托个数计算方法
	 * @author foss-105795-wqh
	 * @date 2014-08-19
	 * @param waybillNo,beforePalletQty
	 * 
	 * 1当一票货物开单件数等于1件时，限制编辑加托的个数小于等于2且为正整数；
	 * 2 当一票货物的开单件数大于1件时，限制编辑加托个数小于等于货物的开单件数，且为正整数；
	 * 3 当不满足1、2则提示“提示，加托个数错误，重新核实录入”；
	 * 
	 * */
	public void checkWoodMask(WaybillEntity waybillEntity,ValidatePackedDto validatePackeddto){
		//开单件数限制条件
		int goodsQtyLimit=1;
	
		//参数为空
		if(validatePackeddto==null)
		{
			LOGGER.error("校验参数为空");
			throw new TfrBusinessException("校验参数为空");
		}
		/**
		 * 这里先处理加托个数限制
		 * */
		
		if(waybillEntity==null)
		{
			LOGGER.error("运单信息不存在");
			throw new TfrBusinessException("运单信息不存在");
		}
		//获取运单开单件数
		int goodsQty=waybillEntity.getGoodsQtyTotal();
		if(goodsQty<goodsQtyLimit)
		{
			LOGGER.error("运单开单件数小于1件");
			throw new TfrBusinessException("运单开单件数小于1件");
		}
//		//1当一票货物开单件数等于1件时，限制编辑加托的个数小于等于2且为正整数；
//		if(goodsQty==GOODSQTYLIMIT&&validatePackeddto.getMaskQty()>2)
//		{
//			LOGGER.error("运单:{"+waybillEntity.getWaybillNo()+"},开单件数为1,加托个数不能大于2个");
//			throw new TfrBusinessException("运单:{"+waybillEntity.getWaybillNo()+"},开单件数为1,加托个数不能大于2个");
//		//2 当一票货物的开单件数大于1件时，限制编辑加托个数小于等于货物的开单件数，且为正整数；
//		}else 
			
		if(validatePackeddto.getMaskQty()>2*goodsQty){
			LOGGER.error("运单:{"+waybillEntity.getWaybillNo()+"},加托个数不能大于开单件数的2倍");
			throw new TfrBusinessException("运单:{"+waybillEntity.getWaybillNo()+"},加托个数不能大于开单件数的2倍");
		}
		
		
		
	}
	
}
