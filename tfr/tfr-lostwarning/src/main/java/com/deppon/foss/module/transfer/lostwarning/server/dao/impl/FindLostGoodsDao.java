package com.deppon.foss.module.transfer.lostwarning.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.lostwarning.api.server.dao.IFindLostGoodsDao;
import com.deppon.foss.module.transfer.lostwarning.api.server.define.LostWarningConstant;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostGoodsFindOrgDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningLogDto;

/**
 * 丢货找到处理实现类
 * 
 * 项目名称：tfr-lostwarning
 * 
 * 类名称：FindLostGoodsDao
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-8-7 下午4:22:49
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class FindLostGoodsDao extends iBatis3DaoImpl implements
		IFindLostGoodsDao {
	
	private static final String NAMESPACE = "foss.lostwarning.findLostGoods.";

	/**
	 * @Description: 查询有效的运单信息数
	 * @date 2015-8-7 下午4:54:55   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public long queryWayBillCount(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryWayBillCountByBillNo",dataMap);
	}
	
	/**
	 * @Description: 查询运单是否签收
	 * @date 2015-11-24 下午4:22:04   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public long queryWayBillIsSign(String wayBillNo){
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryWayBillISSign",dataMap);
	}

	
	/**
	 * @Description: 查询未找到的丢货数据
	 * @date 2015-8-8 上午9:11:05   
	 * @author 263072 
	 * @return
	 */
	@Override
	public List<LostWarningLogDto> queryLostGoodsNotFound(String wayBillNo,String uploadTime) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		dataMap.put("uploadTime", uploadTime);
		return this.getSqlSession().selectList(NAMESPACE + "queryLostGoodsNotFindByBillNo",dataMap);
	}

	/**
	 * @Description: 更改丢货数据的找到状态
	 * @date 2015-8-8 上午9:23:56   
	 * @author 263072 
	 * @param wayBillNo 运单号
	 * @param isFind 是否找到 1找到  0未找到
	 * @param repMsg 备注信息 
	 * @param findMsg 找到上报信息
	 * @param idList 需要更新数据的ID列表（可为NULL）
	 */
	@Override
	public void updateLostGoodsStatus(String wayBillNo,String isFind,String repMsg,
			String findMsg,List<String> idList) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		dataMap.put("isFind", isFind);
		dataMap.put("repMsg", repMsg);
		dataMap.put("findMsg", findMsg);
		dataMap.put("idList", idList);
		this.getSqlSession().update(NAMESPACE+"updateLostWarningLog",dataMap);
	}


	/**
	 * @Description:查询丢货未找到运单号信息
	 * @date 2015-8-8 下午4:26:32   
	 * @author 263072 
	 * @param uploadTime
	 * @return
	 */
	@Override
	public List<LostWarningLogDto> queryLostGoodsWayBill(String uploadTime) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("uploadTime", uploadTime);
		return this.getSqlSession().selectList(NAMESPACE + "queryLostGoodsNotFind",dataMap);
	}
	
	

	/**
	 * @Description: 查询丢货入库记录
	 * @date 2015-8-10 上午10:34:47   
	 * @author 263072 
	 * @param bean
	 * @return
	 */
	@Override
	public List<LostGoodsFindOrgDto> queryInStockGoodsInfo(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		dataMap.put("invalidOrg", LostWarningConstant.INVALID_ORG_CODE);
		return this.getSqlSession().selectList(NAMESPACE+"queryInStockInfo",dataMap);
	}

	/**
	 * @Description: 查询丢货的装车记录信息
	 * @date 2015-8-10 下午3:05:09   
	 * @author 263072 
	 * @return
	 */
	@Override
	public List<LostGoodsFindOrgDto> queryLoadGoodsInfo(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryLoadGoodsInfo",dataMap);
	}

	/**
	 * @Description: 查询丢货的清仓记录信息
	 * @date 2015-8-10 下午3:39:35   
	 * @author 263072 
	 * @param bean
	 * @return
	 */
	@Override
	public List<LostGoodsFindOrgDto> queryCheckStockInfo(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryCheckStockGoodsInfo",dataMap);
	}

	/**
	 * @Description: 查询无效的运单信息
	 * @date 2015-8-11 上午9:52:54   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public long queryInvalidWayBill(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryInvaildWayBill",dataMap);
	}

	/**
	 * @Description: 查询丢货运单的叉车扫描信息
	 * @date 2015-8-10 下午5:49:30   
	 * @author 263072 
	 * @param bean
	 * @return
	 */
	@Override
	public List<LostGoodsFindOrgDto> queryTrayScanGoodsInfo(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return this.getSqlSession().selectList(NAMESPACE + "queryTrayScanGoodsInfo",dataMap);
	}


	/**
	 * @Description: 查询丢货运单的分拣扫描记录 
	 * @date 2015-8-11 上午10:05:10   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostGoodsFindOrgDto> querySortingScanGoodsInfo(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return this.getSqlSession().selectList(NAMESPACE + "querySortingScanGoodsInfo",dataMap);
	}

	/**
	 * @Description: 查询丢货运单的签收记录 
	 * @date 2015-8-11 上午10:05:55   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostGoodsFindOrgDto> querySignScanGoodsInfo(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return this.getSqlSession().selectList(NAMESPACE + "querySignScanGoodsInfo",dataMap);
	}
	
	
	public long getSeqNextVal() {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getNextSequenceValue");
	}
//----------------------------------------------------------------代码添加区begin-------------------------------------
	/**
	 * 收集丢货上报未找到且运单失效的运单号
	 */
	
	@Override
	public void insertBillAndEfficacy(String uploadTime) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("uploadTime", uploadTime);
		this.getSqlSession().insert(NAMESPACE+"insertBillAndEfficacy",dataMap);
		
	}
	/**
	 * 收集丢货上报未找到且运单作废的运单号
	 */

	@Override
	public void insertBillAndVoided(String uploadTime) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("uploadTime", uploadTime);
		this.getSqlSession().insert(NAMESPACE+"insertBillAndVoided",dataMap);
		
	}
	/**
	 * 收集上报未找到而且已经签收的运单号
	 */

	@Override
	public void insertBillAndSigned(String uploadTime) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("uploadTime", uploadTime);
		this.getSqlSession().insert(NAMESPACE+"insertBillAndSigned",dataMap);
		
	}
	
	
	
	/**
	 * 获取上述失效的运单号
	 */

	@Override
	public List<LostWarningLogDto> queryLostGoodsWayBillAndEfficacy(String findType) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("findType", findType);
		return this.getSqlSession().selectList(NAMESPACE + "queryLostGoodsWayBillAndEfficacy", paramMap);
	}
	
	
	/**
	 * 获取上述作废的运单号
	 */

	@Override
	public List<LostWarningLogDto> queryLostGoodsWayBillAndVoided() {
	return this.getSqlSession().selectList(NAMESPACE + "queryLostGoodsWayBillAndVoided");
	
	}
	/**
	 * 获取上述签收的运单号
	 */

	@Override
	public List<LostWarningLogDto> queryLostGoodsWayBillAndSigned() {
		return this.getSqlSession().selectList(NAMESPACE + "queryLostGoodsWayBillAndSigned");
	}

	
	
	
	
	/**
	 * 删除暂时存储失效数据表数据
	 */

	@Override
	public void deleteEfficacyData(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		this.getSqlSession().update(NAMESPACE+"deleteEfficacyData",dataMap);
		
	}
	/**
	 * 删除暂时存储作废数据表数据
	 */
	@Override
	public void deleteVoidedData(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		this.getSqlSession().update(NAMESPACE+"deleteVoidedData",dataMap);
		
	}
	/**
	 * 删除暂时存储签收数据表数据
	 */
	@Override
	public void deleteSignedData(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		this.getSqlSession().update(NAMESPACE+"deleteSignedData",dataMap);
	}

	
	
	//----------------------------------------------------------------代码添加区end-------------------------------------
	
	//---------------------------------------------------------------找到处理由线程变为按模块处理----------------------------------------
	
	@Override
	public void insertLoadData(String uploadTime) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("uploadTime", uploadTime);
		this.getSqlSession().insert(NAMESPACE+"insertLoadData",dataMap);
		
	}

	@Override
	public void insertInventoryData(String uploadTime) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("uploadTime", uploadTime);
		this.getSqlSession().insert(NAMESPACE+"insertInventoryData",dataMap);
		
	}

	@Override
	public void insertDriverData(String uploadTime) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("uploadTime", uploadTime);
		this.getSqlSession().insert(NAMESPACE+"insertDriverData",dataMap);
		
	}

	@Override  
	public void insertSortingData(String uploadTime) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("uploadTime", uploadTime);
		this.getSqlSession().insert(NAMESPACE+"insertSortingData",dataMap);
		
	}

	@Override
	public void insertSSignedData(String uploadTime,int day) {     
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("uploadTime", uploadTime);
		dataMap.put("days",day);
		this.getSqlSession().insert(NAMESPACE+"insertSSignedData",dataMap);
		
	}

	@Override
	public void insertUnloadData(String uploadTime) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("uploadTime", uploadTime);
		this.getSqlSession().insert(NAMESPACE+"insertUnloadData",dataMap);
		
	}
	@Override
	public void inserNewInstockData(int days) {
		this.getSqlSession().insert(NAMESPACE+"inserNewInstockData",days);
		
	}
	/**
	 * 删除新建入库表中超过十五天的记录
	 */
	@Override
	public void deleteInstockNewInVaildData() {
		
		this.getSqlSession().delete(NAMESPACE+"deleteInstockNewInVaildData");
		
	}
	
	
	
	
	
	
	//---------------------------------------------------------------找到处理由线程变为按模块处理----------------------------------------

}
