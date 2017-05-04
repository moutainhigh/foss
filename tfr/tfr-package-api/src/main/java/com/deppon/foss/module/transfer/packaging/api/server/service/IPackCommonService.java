package com.deppon.foss.module.transfer.packaging.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryWaybillPackEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.ValidatePackedDto;
/**
 * @author foss-105795-wqh
 * @desc 存放PDA端与PC端的公共方法
 * @date 2014-04-10
 * */
public interface IPackCommonService extends IService{
	
	/**
	 * @desc 根据运单号，查询带打包件数
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	int queryUnPackQtyByWaybillNo(String waybillNo,String packageOrgCode);
	
	/**
	 * @desc 根据运单号，查询包装后货物总件数
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	int queryPackedQtyByWaybillNo(String waybillNo,String orgCode);
	
	
	/**
	 * @desc 根据运单号，查询打包装需求中还未打包的所有流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	List<String> queryUnPackSeriaByWaybillNo(String waybillNo,String packageOrgCode);
	
	/**
	 * @desc 根据运单号，查询包装需求中已经打包装的流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-12
	 * @param waybillNo
	 * 
	 * */
	List<String> queryRePackedSeriaListByWaybillNo(String waybillNo,String packageOrgCode);
	
	
	/**
	 * @desc 根据运单号，查询实际包装中所有的新流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-12
	 * @param waybillNo
	 * 
	 * */
	List<String> queryPackedNewSeriaListByWaybillNo(String waybillNo,String orgCode);
	
	/**
	 * @desc 根据运单号，查询打包装需求中获剩下未打包的流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	List<String> queryUnPackSeriaAfterByWaybillNo(String waybillNo,String packageOrgCode,String unPackType);
	
	/**
	 * @desc 作废包装需求中的流水号 
	 * @author foss-105795-wqh
	 * @date 2014-04-14
	 * @param waybillNo
	 * 
	 * */
	void cancleUnPackReSerias(String waybillNo,String packageOrgCode,List<String> seriaNoList);
	
	/**
	 * @desc 根据运单号，查询需求信息
	 * @author foss-105795-wqh
	 * @date 2014-04-15
	 * @param waybillNo
	 * 
	 * */
	QueryWaybillPackEntity queryRePackByWaybillNo(String waybillNo,String orgCode);
	
	/**
	 * @desc 查询出所有合打的新流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-15
	 * @param waybillNo
	 * 
	 * */
	List<String> queryPackedJoinSeriasByWaybillNo(String waybillNo,String orgCode);
	
	/**
	 * @desc 查询需要生成代办的流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-15
	 * @param waybillNo
	 * 
	 * */
	List<String> queryToDoSeriasByWaybillNo(String waybillNo,String orgCode);
	
	/**
	 * @desc 限制加托个数计算方法
	 * @author foss-105795-wqh
	 * @date 2014-08-19
	 * @param waybillNo,int beforePalletQty
	 * 
	 * */
	void checkWoodMask(WaybillEntity waybillEntity,ValidatePackedDto validatePackeddto);
	
	
}
