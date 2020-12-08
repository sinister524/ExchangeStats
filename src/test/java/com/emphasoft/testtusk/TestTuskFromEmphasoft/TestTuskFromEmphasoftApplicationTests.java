package com.emphasoft.testtusk.TestTuskFromEmphasoft;

import com.emphasoft.testtusk.TestTuskFromEmphasoft.controller.ExchangeController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestTuskFromEmphasoftApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ExchangeController exchangeController;

	@Test
	void testExchange() throws Exception {
		this.mockMvc.perform(post("/exchange")
					.param("from", "USD")
					.param("to", "RUB")
					.param("value", "579.23"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("sum"));
	}

}
