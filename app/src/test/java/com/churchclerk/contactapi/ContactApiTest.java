/*
 */
package com.churchclerk.contactapi;


import com.churchclerk.baseapi.model.ApiCaller;
import com.churchclerk.contactapi.entity.ContactEntity;
import com.churchclerk.contactapi.model.Contact;
import com.churchclerk.contactapi.service.ContactService;
import com.churchclerk.securityapi.SecurityApi;
import com.churchclerk.securityapi.SecurityToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.Date;
import java.util.UUID;

/**
 *
 */
@SpringBootTest
@TestPropertySource(locations="classpath:application-mock.properties")
public class ContactApiTest {

	private static final String HEADER_AUTHORIZATION	= "Authorization";
	private static final String TOKEN_PREFIX 			= "Bearer ";
	private static final String LOCAL_ADDRESS			= "127.0.0.1";

	@InjectMocks
	private ContactApi 				testObject;

	@Mock
	protected HttpServletRequest	testHttpRequest;

	@Mock
	private ContactService 			testService;

	@Value("${jwt.secret}")
	private String					testSecret;


	private SecurityToken	testToken;
	private Principal 		testPrincipal;
	private Date			testDate;
	private UUID			testId;
	private Contact			testResource;
	private ContactEntity	testEntity;

	@BeforeEach
	public void setupMock() {

		Mockito.clearInvocations(testHttpRequest);
		Mockito.clearInvocations(testService);

		testDate		= new Date();
		testId			= UUID.randomUUID();
		testResource	= createResource(testId.toString());
		testEntity		= new ContactEntity();

		if (createToken(testId.toString(), LOCAL_ADDRESS) == false) {
			throw new RuntimeException("Error creating security token");
		};

		testPrincipal   = new UsernamePasswordAuthenticationToken(null, testToken, null);

		Mockito.when(testHttpRequest.getHeader(HEADER_AUTHORIZATION)).thenReturn(TOKEN_PREFIX+testToken.getJwt());
		Mockito.when(testHttpRequest.getRemoteAddr()).thenReturn(LOCAL_ADDRESS);
		Mockito.when(testHttpRequest.getUserPrincipal()).thenReturn(testPrincipal);

		ReflectionTestUtils.setField(testObject, "secret", testSecret);
	}

	private Contact createResource(String name) {
		Contact resource = new Contact();

		resource.setId(testId);
		resource.setActive(true);

		resource.setPhone("1234567890");
		resource.setEmail("test@test.com");
		resource.setWeb(null);
		resource.setStreet("1111 Test Street");
		resource.setCity("Test City");
		resource.setState("Test State");
		resource.setZip("99999");
		resource.setCountry("US");

		return resource;
	}

	private boolean createToken(String id, String location) {
		testToken = new SecurityToken();

		testToken.setId(id + "|");
		testToken.setRoles(ApiCaller.Role.SUPER.name());
		testToken.setLocation(location);
		testToken.setSecret(testSecret);

		return SecurityApi.process(testToken);
	}

	@Test
	@Order(0)
	public void contexLoads() throws Exception {
		Assertions.assertThat(testObject).isNotNull();
	}

	@Test
	public void testGetResources() throws Exception {
		ReflectionTestUtils.setField(testObject, "sortBy", "street");

		Mockito.when(testService.getResources(null, null)).thenReturn(null);

		Response response = testObject.getResources();

		Assertions.assertThat(response.getEntity()).isNull();
	}


	@Test
	public void testGetResource() throws Exception {

		ReflectionTestUtils.setField(testObject, "id", testId.toString());

		Mockito.when(testService.getResource(testId.toString())).thenReturn(null);

		Response response = testObject.getResource();

		Assertions.assertThat(response.getEntity()).isNull();
	}

	@Test
	public void testCreateResource() throws Exception {

		Mockito.when(testService.createResource(testResource)).thenReturn(testResource);

		testResource.setId(null);
		Response response = testObject.createResource(testResource);

		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(response.getStatus()).isBetween(200, 299);
		Assertions.assertThat(response.getEntity()).isNotNull();
		Assertions.assertThat(response.getEntity()).isInstanceOf(Contact.class);

		Contact actual = (Contact) response.getEntity();

		Assertions.assertThat(actual.isActive()).isEqualTo(true);
	}

	@Test
	public void testUpdateResource() throws Exception {

		ReflectionTestUtils.setField(testObject, "id", testId.toString());

		Mockito.when(testService.getResource(testId.toString())).thenReturn(testResource);
		Mockito.when(testService.updateResource(testResource)).thenReturn(testResource);

		testResource.setActive(false);
		Response response = testObject.updateResource(testResource);

		Assertions.assertThat(response.getEntity()).isNotNull();
		Assertions.assertThat(response.getEntity()).isInstanceOf(Contact.class);

		Contact actual = (Contact) response.getEntity();

		Assertions.assertThat(actual.isActive()).isEqualTo(false);
		Assertions.assertThat(actual.getUpdatedBy()).isEqualTo(testId.toString());
		Assertions.assertThat(actual.getUpdatedDate()).isAfterOrEqualTo(testDate);
	}

	@Test
	public void testUpdateResourceNotExist() throws Exception {
		ReflectionTestUtils.setField(testObject, "id", testId.toString());

		Mockito.when(testService.updateResource(testResource)).thenReturn(null);

		Response response = testObject.updateResource(testResource);

		Assertions.assertThat(response.getEntity()).isNull();
	}

	@Test
	public void testDeleteResource() throws Exception {
		ReflectionTestUtils.setField(testObject, "id", testId.toString());

		Mockito.when(testService.deleteResource(testId.toString())).thenReturn(testResource);

		Response response = testObject.deleteResource();

		Assertions.assertThat(response.getEntity()).isNotNull();
		Assertions.assertThat(response.getEntity()).isEqualTo(testResource);
	}
}
