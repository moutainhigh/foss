package com.deppon.foss.module.transfer.lostwarning.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostGoodsFindOrgDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningLogDto;

/**
 * 丢货找到处理接口
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：IFindLostGoodsDao
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-8-7 下午4:21:38
 * 
 * 版权所有：上海德邦物流有限公司
 */
public interface IFindLostGoodsDao {
	
	/**
	 * @Description: 查询有效的运单信息数
	 * @date 2015-8-7 下午4:54:55   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public long queryWayBillCount(String wayBillNo);
	
	/**
	 * @Description: 查询运单是否签收
	 * @date 2015-11-24 下午4:22:04   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public long queryWayBillIsSign(String wayBillNo);
	
	/**
	 * @Description: 根据运单号查询未找到的丢货数据
	 * @date 2015-8-8 上午9:11:05   
	 * @author 263072 
	 * @return
	 */
	public List<LostWarningLogDto> queryLostGoodsNotFound(String wayBillNo,String uploadTime);
	
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
	public void updateLostGoodsStatus(String wayBillNo,String isFind,String repMsg,
			String findMsg,List<String> idList);
	
	/**
	 * @Description:查询丢货未找到运单号信息
	 * @date 2015-8-8 下午4:26:32   
	 * @author 263072 
	 * @param uploadTime
	 * @return
	 */
	
	public List<LostWarningLogDto> queryLostGoodsWayBill(String uploadTime);
	//---------------------------------------------------代码添加区 begin---------------------------------------------
	/**
	 * 收集丢货上报未找到且运单失效的运单号
	 */
	public void insertBillAndEfficacy(String uploadTime);
	
	/**
	 * 收集丢货上报未找到且运单作废的运单号
	 */
	
	public void insertBillAndVoided(String uploadTime);
	
	/**
	 * 收集上报未找到而且已签收的运单号
	 */
	public void insertBillAndSigned(String uploadTime);
	
	/**
	 * 获取上述失效的运单号
	 */
	
	public List<LostWarningLogDto> queryLostGoodsWayBillAndEfficacy(String findType);
	/**
	 * 获取上述作废的运单号
	 */
	
	public List<LostWarningLogDto> queryLostGoodsWayBillAndVoided();
	/**
	 * 获取上述签收的运单号
	 */
	
	public List<LostWarningLogDto> queryLostGoodsWayBillAndSigned();
	
	
	
	
	/**
	 * 删除存储失效运单数据表数据
	 */
	public void deleteEfficacyData(String wayBillNo);
	/**
	 * 删除存储作废运单数据表数据
	 */
	public void deleteVoidedData(String wayBillNo);
	/**
	 * 删除存储签收 运单数据表数据
	 */
	public void deleteSignedData(String wayBillNo);
	
	//---------------------------------------------------代码添加区end---------------------------------------------
	
	//-------------------------------------------------找到上报线程处理转换为模块处理------------------------------
	
	/**
	 * 收集丢货上报装车扫描找到数据
	 */
	public void insertLoadData(String uploadTime);
	
	/**
	 * 收集清仓上报丢货找到数据
	 * */
	 public void insertInventoryData(String uploadTime); 
	 
	 /**
		 * 收集叉车司机上报上报丢货找到数据
		 * */
	 public void insertDriverData(String uploadTime); 
		 /**
			 * 收集分鎌上报丢货找到数据
			 * */
	 public void insertSortingData(String uploadTime); 
			
	 /**
		 * 收集签收上报丢货找到数据
		 * */
	 public void insertSSignedData(String uploadTime,int day);   
		
	
	 /**
		 * 收集卸车单票上报丢货找到数据
		 * */
	 public void insertUnloadData(String uploadTime);
	
	
	
	
	//-------------------------------------------------找到上报线程处理转换为模块处理------------------------------
	
	/**
	 * @Description: 查询丢货入库记录
	 * @date 2015-8-10 上午10:34:47   
	 * @author 263072 
	 * @return
	 */
	public List<LostGoodsFindOrgDto> queryInStockGoodsInfo(String wayBillNo);
	
	
	/**
	 * @Description: 查询丢货的装车记录信息
	 * @date 2015-8-10 下午3:05:09   
	 * @author 263072 
	 * @return
	 */
	public List<LostGoodsFindOrgDto> queryLoadGoodsInfo(String wayBillNo);
	
	/**
	 * @Description: 查询丢货的清仓记录信息
	 * @date 2015-8-10 下午3:39:35   
	 * @author 263072 
	 * @return
	 */
	public List<LostGoodsFindOrgDto> queryCheckStockInfo(String wayBillNo);
	
	/**
	 * @Description: 查询无效的运单信息
	 * @date 2015-8-11 上午9:52:54   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public long queryInvalidWayBill(String wayBillNo);
	
	
	/**
	 * @Description: 查询丢货运单的叉车扫描信息
	 * @date 2015-8-10 下午5:49:30   
	 * @author 263072 
	 * @param bean
	 * @return
	 */
	public List<LostGoodsFindOrgDto> queryTrayScanGoodsInfo(String wayBillNo);
	
	/**
	 * @Description: 查询丢货运单的分拣扫描记录 
	 * @date 2015-8-11 上午10:05:10   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostGoodsFindOrgDto> querySortingScanGoodsInfo(String wayBillNo);
	
	/**
	 * @Description: 查询丢货运单的签收记录 
	 * @date 2015-8-11 上午10:05:55   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostGoodsFindOrgDto> querySignScanGoodsInfo(String wayBillNo);
	
	public long getSeqNextVal();
	/**
	 * 收集入库表中的数据同步到新建的入库信息表中
	 */
	public void inserNewInstockData(int days);
	/**
	 * 删除新建的入库记录表中超过十五天的数据
	 */
	public void deleteInstockNewInVaildData();
	
}
