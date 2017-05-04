package com.deppon.foss.module.transfer.packaging.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPackCommonDao;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryWaybillPackEntity;

public class PackCommonDao extends iBatis3DaoImpl implements IPackCommonDao {

	/**
	 * 定义ibatis的命名空间
	 */
	public final static String PACKCOMMON_IBATIS_NAMESAPCE = "foss.package.packCommon.";
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @desc 根据运单号，查询代打包件数
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	public int queryUnPackQtyByWaybillNo(String waybillNo,String packageOrgCode) {
		// TODO Auto-generated method stub
		Map dataMap=new HashMap<String, String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("packageOrgCode", packageOrgCode);
		int result=Integer.parseInt(getSqlSession().selectOne(PACKCOMMON_IBATIS_NAMESAPCE+
				"queryUnPackQtyByWaybillNo", dataMap).toString());
		return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @desc 根据运单号，查询包装后货物总件数
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	public int queryPackedQtyByWaybillNo(String waybillNo,String orgCode) {
		// TODO Auto-generated method stub
		Map dataMap=new HashMap<String, String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("orgCode", orgCode);
		int result=Integer.parseInt(getSqlSession().selectOne(PACKCOMMON_IBATIS_NAMESAPCE+
				"queryPackedQtyByWaybillNo", dataMap).toString());
		return  result;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @desc 根据运单号，查询打包装需求中还未打包的所有流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	public List<String> queryUnPackSeriaByWaybillNo(String waybillNo,String packageOrgCode) {
		// TODO Auto-generated method stub
		Map<String,String> dataMap=new HashMap<String, String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("packageOrgCode", packageOrgCode);
		return getSqlSession().selectList(PACKCOMMON_IBATIS_NAMESAPCE+"queryUnPackSeriaByWaybillNo", dataMap);
	}

	
	/**
	 * @desc 根据运单号，查询包装需求中已经打包装的流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-12
	 * @param waybillNo
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<String> queryRePackedSeriaListByWaybillNo(String waybillNo,String packageOrgCode){
		Map<String,String> dataMap=new HashMap<String, String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("packageOrgCode", packageOrgCode);
		return getSqlSession().selectList(PACKCOMMON_IBATIS_NAMESAPCE+"queryRePackedSeriaListByWaybillNo", dataMap);
	}
	
	
	/**
	 * @desc 根据运单号，查询实际包装中所有的新流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-12
	 * @param waybillNo
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<String> queryPackedNewSeriaListByWaybillNo(String waybillNo,String orgCode){
		Map<String,String> dataMap=new HashMap<String, String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("packageOrgCode", orgCode);
		return getSqlSession().selectList(PACKCOMMON_IBATIS_NAMESAPCE+"queryUnPackSeriaByWaybillNo", dataMap);
	}
	/**
	 * @desc 根据运单号，查询打包装需求中获剩下未打包的流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-11
	 * @param waybillNo
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<String> queryUnPackSeriaAfterByWaybillNo(String waybillNo,String packageOrgCode,String unPackType){
		Map dataMap=new HashMap();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("packageOrgCode", packageOrgCode);
		dataMap.put("unPackType", unPackType);
		return getSqlSession().selectList(PACKCOMMON_IBATIS_NAMESAPCE+"queryUnPackSeriaAfterByWaybillNo", dataMap);
	}
	
	/**
	 * @desc 作废包装需求中的流水号 
	 * @author foss-105795-wqh
	 * @date 2014-04-14
	 * @param waybillNo
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public void cancleUnPackReSerias(String waybillNo,String packageOrgCode,List<String> seriaNoList){
		Map dataMap=new HashMap();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("packageOrgCode", packageOrgCode);
		dataMap.put("seriaNoList", seriaNoList);
		getSqlSession().selectList(PACKCOMMON_IBATIS_NAMESAPCE+"cancleUnPackReSerias", dataMap);
	}
	
	/**
	 * @desc 根据运单号，查询需求信息
	 * @author foss-105795-wqh
	 * @date 2014-04-15
	 * @param waybillNo
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public QueryWaybillPackEntity queryRePackByWaybillNo(String waybillNo,String orgCode){
		Map<String,String> dataMap=new HashMap<String, String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("packageOrgCode", orgCode);
		List<QueryWaybillPackEntity> result=
				getSqlSession().selectList(PACKCOMMON_IBATIS_NAMESAPCE+"queryRePackByWaybillNo", dataMap);
	    if(result!=null&&result.size()>0)
	    {
	    	return result.get(0);
	    }else{
	    	
	    	return null;
	    	
	    }
		
	}
	/**
	 * @desc 查询出所有合打的新流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-15
	 * @param waybillNo
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<String> queryPackedJoinSeriasByWaybillNo(String waybillNo,String orgCode){
		Map<String,String> dataMap=new HashMap<String, String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("orgCode", orgCode);
		return getSqlSession().selectList(PACKCOMMON_IBATIS_NAMESAPCE+"queryPackedJoinSeriasByWaybillNo", dataMap);
	}
	
	/**
	 * @desc 查询需要生成代办的流水号
	 * @author foss-105795-wqh
	 * @date 2014-04-15
	 * @param waybillNo
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<String> queryToDoSeriasByWaybillNo(String waybillNo,String orgCode){
		Map<String,String> dataMap=new HashMap<String, String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("orgCode", orgCode);
		return getSqlSession().selectList(PACKCOMMON_IBATIS_NAMESAPCE+"queryToDoSeriasByWaybillNo", dataMap);
	}
	
}
