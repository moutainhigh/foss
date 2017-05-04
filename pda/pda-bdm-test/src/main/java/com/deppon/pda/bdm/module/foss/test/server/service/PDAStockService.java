package com.deppon.pda.bdm.module.foss.test.server.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockPositionNumberEntity;

public class PDAStockService implements IPDAStockService{
	private Logger log = Logger.getLogger(getClass());
	@Override
	public int inStockPackageAreaPDA(InOutStockEntity inOutStockEntity) {
		log.debug("包装入库成功");
		return 1;
	}

	@Override
	public int outStockPackageAreaPDA(InOutStockEntity inOutStockEntity) {
		log.debug("包装出库成功");
		return 1;
	}

	@Override
	public int inStockValuableAreaPDA(InOutStockEntity inOutStockEntity) {
		log.debug("贵重入库成功");
		return 1;
	}

	@Override
	public int outStockValuableAreaPDA(InOutStockEntity inOutStockEntity) {
		log.debug("贵重出库成功");
		return 1;
	}

	@Override
	public int inStockExceptionAreaPDA(InOutStockEntity inOutStockEntity) {
		log.debug("异常入库成功");
		return 1;
	}

	@Override
	public int outStockExceptionAreaPDA(InOutStockEntity inOutStockEntity) {
		log.debug("异常出库成功");
		return 1;
	}

	@Override
	public int singleInStockPDA(InOutStockEntity inOutStockEntity) {
		log.debug("单件入库成功");
		return 1;
	}

	@Override
	public List<BaseDataDictDto> areaByOrgcodeList(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BaseDataDictDto> queryPositionList(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateStockStockPosition(InOutStockEntity arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<StockPositionNumberEntity> queryStockPositionNumber(
			String waybillNo, String orgCode) {
		List<StockPositionNumberEntity> list = new ArrayList<StockPositionNumberEntity>();
		
		StockPositionNumberEntity st = new StockPositionNumberEntity();
		st.setWaybillNO("1234567890");
		st.setStockPositionNumber("0001");
		st.setSerialNO("0001");
		st.setOrgCode("W398981234");		
		list.add(st);
		
		StockPositionNumberEntity  st1 = new StockPositionNumberEntity();
		st1.setWaybillNO("2345678901");
		st1.setStockPositionNumber("0002");
		st1.setSerialNO("0001");
		st1.setOrgCode("W398981234");
		list.add(st1);
		
		
		
		return list;
	}

	@Override
	public void saveStockPositionNumber(
			List<StockPositionNumberEntity> stockPositionNumberEntityList) {
		// TODO Auto-generated method stub
		
	}

}
