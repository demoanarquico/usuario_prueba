package cl.bci.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import cl.bci.user.controller.UserController;
import cl.bci.user.dto.Phone;
import cl.bci.user.dto.ReqUser;
import cl.bci.user.dto.RespInfoUser;
import cl.bci.user.dto.RespUser;
import cl.bci.user.service.ApiService;

public class UserControllerTest {
	
	private MockMvc mockMvc;

	@InjectMocks
	private UserController userController;
	
	@Mock
	private ApiService service;
	
	private WebApplicationContext context;
	
	/**
	   * @throws java.lang.Exception
	*/
	@BeforeEach
	public void setUp() throws Exception {
	   MockitoAnnotations.openMocks(this);
	   this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	  
	}
	
//	@Test
//	void whenSubmitUser_thenUserIsGenerated() throws Exception {
//		
//		ReqUser user = new ReqUser();
//		user.setName( "Camilo Rojas" );
//		user.setEmail( "crojas@prueba.cl" );
//		user.setPassword( "zY97@564" );
//		
//		Phone phone = new Phone();
//		phone.setNumber( "7654321" );
//		phone.setCitycode( "1" );
//		phone.setContrycode( "57" );
//		
//		List<Phone> phones = new ArrayList<Phone>();
//		phones.add( phone );
//		
//		user.setPhones( phones );
//		
//		RespUser result = new RespUser();
//		result.setId(1);
//		
//		Mockito.when(service.createUser(Mockito.any(ReqUser.class))).thenReturn(result);
//		
//		this.mockMvc
//        .perform(put("/api/user/create")
//        		.contentType(MediaType.APPLICATION_JSON)
//        		.content(DTOtoJson(user)))
//        		.andExpect(status().isOk())
//        		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            //.accept(MediaType.APPLICATION_JSON))
//       // .andExpect(status().isOk())
////        .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(notNullValue())))
////        .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(equalTo(id.toString()))))
//        .andReturn();
//		
//	}
	
	@Test
	void getUser_whenUserNoExist() throws Exception {
		
		RespInfoUser result = new RespInfoUser();
		
		Mockito.when( service.getUser( Mockito.anyInt() ) ).thenReturn( result );
		
		this.mockMvc
        .perform((RequestBuilder) ((ResultActions) get("/api/user/get/100"))
        		.andExpect(status().isNotFound())
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON)));
            //.accept(MediaType.APPLICATION_JSON))
       // .andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(notNullValue())))
//        .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(equalTo(id.toString()))))

		
	}
	
	private static String DTOtoJson ( ReqUser user ) {
		Gson gson = new Gson();
		String retorno = gson.toJson( user );
		return retorno;
	}
	
	@Configuration
	public class RequestLoggingFilterConfig {

	    @Bean
	    public CommonsRequestLoggingFilter logFilter() {
	        CommonsRequestLoggingFilter filter
	          = new CommonsRequestLoggingFilter();
	        filter.setIncludeQueryString(true);
	        filter.setIncludePayload(true);
	        filter.setMaxPayloadLength(10000);
	        filter.setIncludeHeaders(false);
	        filter.setAfterMessagePrefix("REQUEST DATA: ");
	        return filter;
	    }
	}

}
