/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver
 * PACKAGE NAME: com.deppon.foss.module.pickup.predeliver.server.dao.impl
 * FILE    NAME: ExceptionProcessDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto;
import com.deppon.foss.util.UUIDUtils;


/**
 * 处理异常主数据Dao层
 * @author 043258-
 *     foss-zhaobin
 * @date 2012-10-31 
 *     上午10:54:04
 * @since
 * @version
 */

public class ExceptionProcessDao extends iBatis3DaoImpl implements IExceptionProcessDao {
  /**
   * 处理异常主数据Name space
   */
  private static final String NAMESPACE_CONTRACT = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity.";
  /**
   * 处理异常返回详情
   */
  private static final String QUERY_EXCEPTIONPROCESSINFO = "queryExceptionProcessInfo"; 
  /**
   * 查询异常返回详情王飞用
   */
  private static final String QUERY_EXCEPTIONPROCESSINFOTONOTICE = "queryExceptionProcessInfoToNotice"; 
  /**
   * 查询异常主表信息
   */
  private static final String QUERY_EXCEPTIONPROCESSINFOBYWAYBILLNO = "queryExceptionProcessInfoByWaybillNo"; 
  /**处理异常返回总条数
   * 
   */
  private static final String QUERY_EXCEPTIONPROCESSINFOCOUNT = "queryExceptionProcessInfoCount"; 
  
  private static final String QUERY_EXCEPTIONPROCESSINFOCOUNT_ = "queryExceptionProcessInfoCount_";
  /**
   * 处理异常返回详情
   */
  private static final String QUERY_EXCEPTIONPROCESSDETAILINFO = "queryExceptionProcessDetailInfo"; 
  
  
  /**
   * 处理异常返回预计送货日期
   */
  private static final String QUERY_ACTUALFREIGHTINFO = "queryActualFreightInfo"; 
  /**
   * 处理异常更新预计送货日期
   */
  private static final String UPDATE_ACTUALFREIGHTINFO = "updateActualFreightInfo"; 
  
  /**
   * 新增异常信息
   */
  private static final String ADD_EXCEPTIONPROCESSINFO = "addExceptionProcessInfo"; 
  /**
   * 更新异常信息
   */
  private static final String UPDATE_EXCEPTIONPROCESSINFO = "updateExceptionProcessInfo"; 
  /**
   * 新增异常处理记录
   */
  private static final String ADD_EXCEPTIONPROCESSDETAIL= "addExceptionProcessDetailInfo"; 
  /**
   * 更新异常信息
   */
  private static final String UPDATE_EXCEPTIONGOODSQTY = "updateExceptionGoodsQty"; 
  /**删除异常主表信息
   * 
   */
  private static final String DELETE_EXCEPTIONPROCESSINFO = "deleteExceptionProcessInfo"; 
  /**
   * 更新异常信息
   */
  private static final String DELETE_EXCEPTIONPROCESSDETAIL = "deleteExceptionProcessDetailInfo"; 
  /**
   * 更新异常信息
   */
  private static final String QUERY_EXCEPTIONISOPERATE = "queryExceptionIsOperate"; 
  /**
   * 查询处理中异常条数
   */
  private static final String QUERY_HANDLINGINFO = "queryhandlinginfo";
  
  /**
   * 查询异常处理信息
   */
  private static final String QUERYEXCEPTIONPROCESSINFOBYPARAMS = "queryExceptionProcessInfoByParams";
  
  /** 
   * 按照查询条件查询异常信息
   * @author 043258-
   *     foss-zhaobin
   * @date 2012-10-31
   *      上午10:54:04
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#queryExceptionProcessInfo
   * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto, int, int)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<ExceptionProcessDto> queryExceptionProcessInfo(ExceptionProcessConditionDto exceptionProcessConditionDto, int start, int limit)
  {
    //分页参数绑定
    RowBounds rowBounds = new RowBounds(start, limit);
    return this.getSqlSession().selectList(
        NAMESPACE_CONTRACT + QUERY_EXCEPTIONPROCESSINFO, exceptionProcessConditionDto,
        rowBounds);  
  }

  /** 
   * 获得总条数
   * @author 043258-
   *     foss-zhaobin
   * @date 2012-10-31 
   *     上午10:54:04
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#queryExceptionProcessInfoCount
   * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto)
   */
  @Override
  public Long queryExceptionProcessInfoCount(ExceptionProcessConditionDto exceptionProcessConditionDto) 
  {
    return (Long)this.getSqlSession().selectOne(NAMESPACE_CONTRACT + QUERY_EXCEPTIONPROCESSINFOCOUNT,exceptionProcessConditionDto);
  }

  /** 
   * 新增异常信息
   * @author 043258-
   *     foss-zhaobin
   * @date 2012-10-31 
   *     上午10:54:04
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#addExceptionProcessInfo
   * (com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity)
   */
  @Override
  public void addExceptionProcessInfo(ExceptionEntity exceptionEntity)
  {
    //获得UUID
    exceptionEntity.setId(UUIDUtils.getUUID()) ;
    //设定异常时间
    exceptionEntity.setExceptionTime(new Date());
    //修改时间
    exceptionEntity.setModifyDate(exceptionEntity.getExceptionTime());
    getSqlSession().insert(NAMESPACE_CONTRACT + ADD_EXCEPTIONPROCESSINFO, exceptionEntity);
  }
  
  /** 
   * 根据异常ID查看异常详情
   * @author 043258-
   *     foss-zhaobin
   * @date 2012-11-1
   *      上午11:17:21
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#queryExceptionProcessDetailInfo
   * (java.lang.String)
   */
  @Override
  public ExceptionProcessDetailDto queryExceptionProcessDetailDto(ExceptionProcessConditionDto exceptionProcessConditionDto) 
  {
    return (ExceptionProcessDetailDto) this.getSqlSession().selectOne(
        NAMESPACE_CONTRACT + QUERY_EXCEPTIONPROCESSDETAILINFO, exceptionProcessConditionDto);  
  }
  
  /**
   * 处理异常返回预计送货日期
   */
  @Override
  public Date queryActualFreightInfo(String waybillNo) {
    return (Date)this.getSqlSession().selectOne(NAMESPACE_CONTRACT + QUERY_ACTUALFREIGHTINFO,waybillNo);
  }

  /** 
   * 更新异常信息状态
   * @author 043258-
   *     foss-zhaobin
   * @date 2012-11-1
   *      下午6:31:27
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#updateExceptionProcessInfo
   * (java.lang.String, java.lang.String)
   */
  @Override
  public boolean updateExceptionProcessInfo(ExceptionEntity exceptionEntity) 
  {
    //修改时间
    exceptionEntity.setModifyDate(new Date());
    return this.getSqlSession().update(
        NAMESPACE_CONTRACT + UPDATE_EXCEPTIONPROCESSINFO, exceptionEntity) > 0 ? true : false;
  }
  
  
  /** 
   * 更新预计送货日期
   * @author 
   * @date 
   * @see 
   */
  @Override
  public boolean updateActualFreightInfo(String waybillNo ,Date deliverDate) 
  {
	  ExceptionProcessConditionDto exceptionProcessCondition =new ExceptionProcessConditionDto();
	  exceptionProcessCondition.setWaybillNo(waybillNo);
	  exceptionProcessCondition.setDeliverDate(deliverDate);
    return this.getSqlSession().update(
        NAMESPACE_CONTRACT + UPDATE_ACTUALFREIGHTINFO, exceptionProcessCondition) > 0 ? true : false;
  }
  

  /** 
   * 新增异常处理
   * @author 043258-
   *     foss-zhaobin
   * @date 2012-11-2
   *      下午2:46:22
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#addExceptionProcessDetail
   * (com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity)
   */
  @Override
  public void addExceptionProcessDetail(ExceptionProcessDetailEntity exceptionProcessDetailEntity) 
  {  
    exceptionProcessDetailEntity.setId(UUIDUtils.getUUID()) ;
    getSqlSession().insert(NAMESPACE_CONTRACT + ADD_EXCEPTIONPROCESSDETAIL, exceptionProcessDetailEntity);
  }
  
  
  
  /**
   * 
   * 查询异常信息（非分页）
   * @author 043258-
   *     foss-zhaobin
   * @date 2012-11-6
   *      下午4:26:19
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#queryExceptionProcessInfo
   * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<ExceptionProcessDto> queryExceptionProcess(ExceptionProcessConditionDto exceptionProcessConditionDto) 
  {
    return this.getSqlSession().selectList(
        NAMESPACE_CONTRACT + QUERY_EXCEPTIONPROCESSINFOTONOTICE, exceptionProcessConditionDto);  
  }

  /**
   * 
   * 更新异常件数
   * @author 043258-
   *     foss-zhaobin
   * @date 2012-12-7
   *      上午9:24:17
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#updateExceptionGoodsQty
   * (java.lang.String)
   */
  @Override
  public boolean updateExceptionGoodsQty(String waybillNo) {
    return this.getSqlSession().update(
        NAMESPACE_CONTRACT + UPDATE_EXCEPTIONGOODSQTY, waybillNo) > 0 ? true : false;
  }

  /**
   * 
   * 查询异常主表信息
   * @author 043258-
   *     foss-zhaobin
   * @date 2012-12-7 
   *     上午9:24:31
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#queryExceptionProcessInfo
   * (com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<ExceptionEntity> queryExceptionProcessInfo(ExceptionEntity exceptionEntity)
  {
    return this.getSqlSession().selectList(
        NAMESPACE_CONTRACT + QUERY_EXCEPTIONPROCESSINFOBYWAYBILLNO, exceptionEntity);
  }

  /**
   * 
   * 删除异常主表信息
   * @author 043258-
   *     foss-zhaobin
   * @date 2012-12-7
   *      上午9:24:51
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#deleteExceptionProcessInfo
   * (com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity)
   */
  @Override
  public void deleteExceptionProcessInfo(String exceptionId)
  {
    this.getSqlSession().delete(NAMESPACE_CONTRACT + DELETE_EXCEPTIONPROCESSINFO, exceptionId);
  }

  /**
   * 
   * 删除异常分录信息
   * @author 043258-
   *     foss-zhaobin
   * @date 2012-12-7 
   *     上午9:25:07
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#deleteExceptionProcessDetail
   * (java.lang.String)
   */
  @Override
  public void deleteExceptionProcessDetail(String tSrvExceptionId) 
  {
    this.getSqlSession().delete(NAMESPACE_CONTRACT + DELETE_EXCEPTIONPROCESSDETAIL, tSrvExceptionId);
  }

  /**
   * 
   * 是否已完结
   * @author 043258-
   *     foss-zhaobin
   * @date 2013-1-14 
   *     下午2:36:05
   */
  @Override
  public boolean isOperate(String tSrvExceptionId)
  {
    ExceptionEntity exceptionEntity = new ExceptionEntity();
    exceptionEntity.setId(tSrvExceptionId);
    exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
    return this.getSqlSession().selectOne(
        NAMESPACE_CONTRACT + QUERY_EXCEPTIONISOPERATE, exceptionEntity) == null ? false : true;
  }

  @Override
  public int queryHandlingInfo(String waybillNo, String serialNo) {
    Map<String, String> params = new HashMap<String, String>();
    params.put("waybillNo", waybillNo);
    params.put("serialNo", serialNo);
    params.put("status", ExceptionProcessConstants.HANDLING);
    return (Integer)this.getSqlSession().selectOne(NAMESPACE_CONTRACT + QUERY_HANDLINGINFO,params);
  }


  /** 
   * 获得总条数
   * @author 045925-foss-yangbin
   * @date 2012-10-31 
   *     上午10:54:04
   * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#queryExceptionProcessInfoCountLast
   * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto)
   */
  @Override
  public Long queryExceptionProcessInfoCountLast(
      ExceptionProcessConditionDto exceptionProcessConditionDto) {
    return (Long)this.getSqlSession().selectOne(NAMESPACE_CONTRACT + QUERY_EXCEPTIONPROCESSINFOCOUNT_,exceptionProcessConditionDto);
  }

  	/**
  	 * 根据运单号号和状态查询异常处理记录
  	 * 
  	 * @author gpz-foss
  	 * @date 2015-11-9 下午4:32:10
  	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao#queryExceptionProcessInfoByParams(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity)
  	 */
	@Override
	public List<ExceptionEntity> queryExceptionProcessInfoByParams(
			ExceptionEntity exceptionCondition) {
		return this.getSqlSession().selectList(NAMESPACE_CONTRACT + QUERYEXCEPTIONPROCESSINFOBYPARAMS, exceptionCondition);
	}

	@Override
	public List<ExceptionProcessDetailEntity> selectExceptionProcessDetailList(
			String exceptionProcessId) {
		// NAMESPACE_CONTRACT
		return super.getSqlSession().selectList(NAMESPACE_CONTRACT + "selectExceptionProcessDetailList", exceptionProcessId);
	}

	@Override
	public ExceptionProcessDetailEntity selectSingleExceptionProcessDetailOfEnd(
			String exceptionProcessId) {
		// TODO Auto-generated method stub
		return (ExceptionProcessDetailEntity) super.getSqlSession().selectOne(NAMESPACE_CONTRACT + "selectExceptionProcessDetailOfEnd", exceptionProcessId);
	}
}