package com.deppon.foss.module.transfer.common.api.server.dao;

import java.util.Date;

public interface IFirstTransferCenterHandoverTimeDao {
	Date getFirstTransferCenterHandoverTime(String wayBillNo);
}
