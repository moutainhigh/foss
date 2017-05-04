/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.server.dao
 * FILE    NAME: IBatchSaveProcessDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto;

/**
 * 控制批量新增、修改条数
 * @author dp-duyi
 * @date 2013-3-25 上午9:36:18
 */
public interface IBatchSaveProcessDao {
 public int batchSaveProcess(String saveType,String saveId,List<?> data);
 /**328768*/
 public int batchUpdateProcess(String batchUpdateType, String updateId, List<AssignUnloadTaskDto> tasks);
}
