/**
 * 
 */
package com.deppon.foss.module.boot.client.autorun;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.client.commons.task.CancelledException;
import com.deppon.foss.framework.client.commons.task.ITask;
import com.deppon.foss.framework.client.commons.task.ITaskContext;
import com.deppon.foss.framework.client.commons.task.ITaskService;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class BeforeLoginTaskContext implements ITaskContext {

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#getValue()
	 */
	@Override
	public <T> T getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#addChunk(java.lang.Object)
	 */
	@Override
	public void addChunk(Object chunk) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#getChunks()
	 */
	@Override
	public <T> List<T> getChunks() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#setMessage(java.lang.String)
	 */
	@Override
	public void setMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#getMessage()
	 */
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#setProgress(int, int)
	 */
	@Override
	public void setProgress(int progress, int total) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#setProgress(int)
	 */
	@Override
	public void setProgress(int progress) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#getProgress()
	 */
	@Override
	public int getProgress() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#getDuration()
	 */
	@Override
	public long getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#getStart()
	 */
	@Override
	public Date getStart() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#isExecuting()
	 */
	@Override
	public boolean isExecuting() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#isExecuted()
	 */
	@Override
	public boolean isExecuted() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#isSucceeded()
	 */
	@Override
	public boolean isSucceeded() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#isFailed()
	 */
	@Override
	public boolean isFailed() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#isCancelled()
	 */
	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#isCancelling()
	 */
	@Override
	public boolean isCancelling() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#checkCancel()
	 */
	@Override
	public void checkCancel() throws CancelledException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#isCancelable()
	 */
	@Override
	public boolean isCancelable() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#setCancelable(boolean)
	 */
	@Override
	public void setCancelable(boolean cancelable) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#cancel()
	 */
	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#isInBackground()
	 */
	@Override
	public boolean isInBackground() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#isInForeground()
	 */
	@Override
	public boolean isInForeground() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#isBackgroundable()
	 */
	@Override
	public boolean isBackgroundable() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#toBackground()
	 */
	@Override
	public void toBackground() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#getTaskService()
	 */
	@Override
	public ITaskService getTaskService() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.client.commons.task.ITaskContext#getTask()
	 */
	@Override
	public ITask getTask() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isForceCancel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setForceCancel(boolean isForceCancel) {
		// TODO Auto-generated method stub
		
	}

}
