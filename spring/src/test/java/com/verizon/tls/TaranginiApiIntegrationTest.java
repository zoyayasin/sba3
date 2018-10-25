package com.verizon.tls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.verizon.tls.Model.Customer;
import com.verizon.tls.Service.CustomerService;
import com.verizon.tls.Service.PlansService;
import com.verizon.tls.testutil.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaranginiApiIntegrationTest {
	
		
			private MockMvc mockMvc;

			@Autowired
			private WebApplicationContext webApplicationContext;

			@Autowired
			private  CustomerService cusServMock;
			
			@Autowired
			private  PlansService plansServMock;

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
				assertThat(this.plansServMock).isNotNull();

				mockMvc.perform(get("/taranginiLtd")).andExpect(status().isOk()).andDo(print());

			}

			@Test
			public void testGetCustomerById() throws Exception {
				assertThat(this.cusServMock).isNotNull();
				
				mockMvc.perform(get("/taranginiLtd/1")).andExpect(status().isOk()).andDo(print());

			}

			@Test
			public void testGetAllPlansByType() throws Exception {
				assertThat(this.plansServMock).isNotNull();

				mockMvc.perform(get("/taranginiLtd/maxSpeed/120")).andExpect(status().isOk()).andDo(print());

			}

			@Test
			public void testAddCustomer() throws Exception {
				assertThat(this.cusServMock).isNotNull();

				Customer cust=new Customer();
				
				cust.setMobile(878787872);
				cust.setAddress("");
				cust.setName("pakode");
				cust.setPkgId("pk5678");
				cust.setTime("14:40");
				cust.setDateOfRequest(null);
			
				

				mockMvc.perform(post("/taranginiLtd")
						.contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(cust))).andDo(print()).andExpect(status().isOk())
						.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

			}

}