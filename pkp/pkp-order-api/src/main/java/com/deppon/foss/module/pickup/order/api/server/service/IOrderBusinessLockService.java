package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.dto.OrderMutexElement;


/**
 * 业务互斥锁服务
 */
public interface IOrderBusinessLockService {

	/**
	 * 锁定某个业务对象
	 * timeout = 0 时，非阻塞调用，如果对象已锁定立刻返回失败
	 * timeout > 0 时，阻塞调用，如果对象已锁定，会等待直到1）获取锁并返回成功 2）超时并返回失败
	 * @param mutex 互斥对象
	 * @param timeout 超时时间，单位：秒
	 * @return true：锁定成功，false：锁定失败
	 */
	boolean lock(OrderMutexElement mutex, int timeout);

	/**
	 * 解除某个业务对象锁定
	 * @param mutex 互斥对象
	 */
	void unlock(OrderMutexElement mutex);
	
	/**
	 * 批量锁定多个业务对象
	 * timeout = 0 时，非阻塞调用，如果任意对象已锁定立刻返回失败
	 * timeout > 0 时，阻塞调用，如果任意对象已锁定，会等待直到1）获取锁并返回成功 2）超时并返回失败
	 * @param mutexes 多个互斥对象
	 * @param timeout 超时时间，单位：秒
	 * @return true：锁定成功，false：锁定失败
	 */
	boolean lock(List<OrderMutexElement> mutexes, int timeout);
	
	/**
	 * 批量解除多个业务对象锁定
	 */
	void unlock(List<OrderMutexElement> mutexes);
}
