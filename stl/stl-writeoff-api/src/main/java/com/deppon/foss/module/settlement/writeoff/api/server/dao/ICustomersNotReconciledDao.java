package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.CustomersNotReconciledDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomersNotReconciledEntity;

public interface ICustomersNotReconciledDao {
	public List<CustomersNotReconciledEntity> queryCustomersNotReconciled(CustomersNotReconciledDto dto);
}