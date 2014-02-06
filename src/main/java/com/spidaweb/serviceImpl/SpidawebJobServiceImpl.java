/**
 * 
 */
package com.spidaweb.serviceImpl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import com.spidaweb.exception.SpidawebServiceException;
import com.spidaweb.job.Application;
import com.spidaweb.job.ApplicationResponse;
import com.spidaweb.job.Job;
import com.spidaweb.service.SpidawebJobService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author Suman
 * 
 */
@Component
public class SpidawebJobServiceImpl implements SpidawebJobService {

	private static final Logger log = Logger
			.getLogger(SpidawebJobServiceImpl.class);

	static final String CONTENT_TYPE = "application/json";
	static final String JOB_URL = "http://www.spidasoftware.com/apply/jobs";
	static final String APPLICATION_URL = "http://www.spidasoftware.com/apply/applications";
	public static final String ERROR_MESSAGE = "Service Returned Invalid Response. Please try again later.";

	Client client;

	WebResource jobResource;
	WebResource applicationResource;
	WebResource applicationStatusResource;

	@PostConstruct
	public void init() {
		client = new Client();
		jobResource = client.resource(JOB_URL);
		applicationResource = client.resource(APPLICATION_URL);
	}

	public List<Job> getJobs() throws SpidawebServiceException {
		ClientResponse response = null;
		List<Job> jobs = null;

		try {
			response = jobResource.accept(CONTENT_TYPE).get(
					ClientResponse.class);
		} catch (Exception e) {
			throw new SpidawebServiceException(ERROR_MESSAGE);
		}

		response.bufferEntity();
		String res = response.getEntity(String.class);

		log.info("Response from Service: " + res);

		ObjectMapper mapper = new ObjectMapper();

		try {
			jobs = mapper.readValue(res, new TypeReference<List<Job>>() {
			});
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return jobs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.spidaweb.service.SpidawebJobService#applyJob(com.spidaweb.job.Application
	 * )
	 */
	public String applyJob(Application application)
			throws SpidawebServiceException {
		ClientResponse response = null;
		String applicationId = null;
		String request = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			request = mapper.writeValueAsString(application);
			log.info(request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		try {
			response = applicationResource.type(CONTENT_TYPE).post(
					ClientResponse.class, request);
		} catch (Exception e) {
			throw new SpidawebServiceException(ERROR_MESSAGE);
		}
		ApplicationResponse applicationResponse = getApplicationResponse(
				response, mapper);
		if (null != applicationResponse) {
			applicationId = applicationResponse.get_id();
		}

		return applicationId;
	}

	public ApplicationResponse getApplicationDetail(String applicationId)
			throws SpidawebServiceException {
		ClientResponse response = null;
		applicationStatusResource = client.resource(APPLICATION_URL + "/"
				+ applicationId);
		try {
			response = applicationStatusResource.accept(CONTENT_TYPE).get(
					ClientResponse.class);
		} catch (Exception e) {
			throw new SpidawebServiceException(ERROR_MESSAGE);
		}

		return getApplicationResponse(response, new ObjectMapper());
	}

	private ApplicationResponse getApplicationResponse(ClientResponse response,
			ObjectMapper mapper) {
		response.bufferEntity();
		ApplicationResponse applicationResponse = null;
		String responseString = response.getEntity(String.class);
		log.info("Response from Service: " + responseString);
		try {
			applicationResponse = mapper.readValue(responseString,
					new TypeReference<ApplicationResponse>() {
					});

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}
