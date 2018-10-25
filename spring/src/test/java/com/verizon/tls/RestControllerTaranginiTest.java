package com.verizon.tls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.verizon.tls.Model.Customer;
import com.verizon.tls.Model.Plans;
import com.verizon.tls.Service.CustomerService;
import com.verizon.tls.Service.PlansService;
import com.verizon.tls.restApi.RestControllerTarangini;
import com.verizon.tls.testutil.TestUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers=RestControllerTarangini.class)
public class RestControllerTaranginiTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private CustomerService custServiceMock;
	
	@MockBean
	private PlansService planServiceMock;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void tearDown() {
		mockMvc = null;
	}
	
	@Test
	public void testGetAllPlans() throws Exception {
		assertThat(this.planServiceMock).isNotNull();
		List<Plans> planList=new ArrayList<>();
		planList.add(new Plans());
		
		when(planServiceMock.getAllPlans()).thenReturn(planList);
		
		mockMvc.perform(get("/taranginiLtd")).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void testGetAllPlansByType() throws Exception{
		assertThat(this.planServiceMock).isNotNull();
		
		String type="maxSpeed";
		int searchValue=100;
		
		List<Plans> planList=new ArrayList<>();
		planList.add(new Plans());
		when(planServiceMock.findAllByMaxSpeed(searchValue)).thenReturn(planList);
		
		mockMvc.perform(get("/taranginiLtd/maxSpeed/100")).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void testAddCustomer() throws Exception{
		assertThat(this.custServiceMock).isNotNull();
		Customer cust=new Customer();
		cust.setOrderNumber(2);
		cust.setName("raj");
		cust.setPkgId("pk45567");
		cust.setMobile(9988);
		cust.setTime("14:14");
		cust.setAddress("nowhere");
		cust.setDateOfRequest(null);
		
		Customer custAdded=new Customer();
		custAdded.setOrderNumber(2);
		custAdded.setName("raj");
		custAdded.setPkgId("pk45567");
		custAdded.setMobile(9988);
		custAdded.setTime("14:14");
		custAdded.setAddress("nowhere");
		custAdded.setDateOfRequest(null);
		
		when(custServiceMock.addCustomer(Mockito.any(Customer.class))).thenReturn(custAdded);

		mockMvc.perform(post("/taranginiLtd").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(cust))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
	}
	
	@Test
	public void testGetCustomerById() throws Exception {
		assertThat(this.custServiceMock).isNotNull();
		
		int orderNum=5;

		Customer cust=new Customer();
		
		cust.setOrderNumber(5);
		cust.setMobile(87878787);
		cust.setAddress("Jhumritilaya");
		cust.setName("aaloo");
		cust.setPkgId("pk3455");
		cust.setTime("15:30");
		cust.setDateOfRequest(null);
		
		when(custServiceMock.getCustomerByOrderNumber(orderNum)).thenReturn(cust);

		mockMvc.perform(get("/taranginiLtd/5")).andExpect(status().isOk()).andDo(print());

	}
}
