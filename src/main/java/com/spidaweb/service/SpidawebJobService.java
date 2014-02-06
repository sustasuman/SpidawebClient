/**
 * 
 */
package com.spidaweb.service;

import java.util.List;

import com.spidaweb.exception.SpidawebServiceException;
import com.spidaweb.job.Application;
import com.spidaweb.job.ApplicationResponse;
import com.spidaweb.job.Job;

/**
 * @author Suman
 * 
 */
public interface SpidawebJobService {
	public List<Job> getJobs() throws SpidawebServiceException;

	public String applyJob(Application application)
			throws SpidawebServiceException;

	public ApplicationResponse getApplicationDetail(String applicationId)
			throws SpidawebServiceException;
}
