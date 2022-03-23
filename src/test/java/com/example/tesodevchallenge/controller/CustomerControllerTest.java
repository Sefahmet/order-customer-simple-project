package com.example.tesodevchallenge.controller;

import com.example.tesodevchallenge.exception.InvalidRequestException;
import com.example.tesodevchallenge.exception.NotFoundException;
import com.example.tesodevchallenge.exception.handler.GenericExceptionHandler;
import com.example.tesodevchallenge.model.entity.Customer;
import com.example.tesodevchallenge.service.CustomerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.InstanceOfAssertFactories.completableFuture;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerControllerTest {

    private MockMvc mvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;



    @BeforeEach
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }



    @Test
    void getCustomer_Success() throws Exception {
        //init
        Date date = new Date();
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Customer expectedCustomer = new Customer(uuid,
                                        "Sefa","Altundal","sefaaltundal@gmail.com",
                                         "5314992211",date,date,null,null);

        //when
        when(customerService.getCustomer(uuid)).thenReturn(expectedCustomer);

        //then
        MockHttpServletResponse response = mvc.perform(
                get("/api/customer/id/?id=b03ac8f7-c531-4a35-b4e6-59dd822d8882")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Customer actualCustomer = new ObjectMapper().readValue(response.getContentAsString(),Customer.class);
        assertEquals(expectedCustomer.toString(),actualCustomer.toString());
    }

    @Test
    void getCustomer_NotFound() throws Exception {
        //init
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");

        //when
        when(customerService.getCustomer(uuid)).thenThrow(new NotFoundException("Customer"));

        //then
        MockHttpServletResponse response = mvc.perform(
                        get("/api/customer/id/?id=b03ac8f7-c531-4a35-b4e6-59dd822d8882")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    @Test
    void getCustomer_InvalidRequest() throws  Exception{
        // init
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");

        // when
        when(customerService.getCustomer(uuid)).thenThrow(new InvalidRequestException("Invalid ID"));

        //then
        MockHttpServletResponse response = mvc.perform(
                        get("/api/customer/id/?id=b03ac8f7-c531-4a35-b4e6-59dd822d8882")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        // assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    void getAllCustomers_Success() throws Exception{
        //init
        Date date = new Date();
        UUID uuid1 = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Customer expectedCustomer1 = new Customer(uuid1,
                "Sefa","Altundal","sefaaltundal@gmail.com",
                "5314992211",date,date,null,null);
        UUID uuid2 = UUID.fromString("1d4d5c03-f343-43fc-ba0f-a2a206dd7edf");
        Customer expectedCustomer2 = new Customer(uuid2,
                "Ahmet Sefa","Altundal","sfltndl@gmail.com",
                "5314992212",date,date,null,null);
        List<Customer> expectedCustomers= new ArrayList<>();
        expectedCustomers.add(expectedCustomer1);
        expectedCustomers.add(expectedCustomer2);

        //when
        when(customerService.getAllCustomer()).thenReturn(expectedCustomers);

        //then
        MockHttpServletResponse response = mvc.perform(
                        get("/api/customer/all")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        //assert
        List<Customer >actualCustomers = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Customer>>(){});
        assertEquals(expectedCustomers.size(),actualCustomers.size());
        assertEquals(expectedCustomers.get(0).toString(),actualCustomers.get(0).toString());
    }

    @Test
    void createCustomer_Success() throws Exception {

        // init
        Date date = new Date();
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Customer expectedCustomer = new Customer();
        expectedCustomer.setFirstName("Sefa");
        expectedCustomer.setLastName("Altundal");
        expectedCustomer.setEmail("sefaaltundal@gmail.com");
        expectedCustomer.setPhoneNumber("5314992211");

        //when
        when(customerService.createCustomer(expectedCustomer)).thenReturn(uuid);

        // Json type
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonCustomer = ow.writeValueAsString(expectedCustomer);

        //then
        MockHttpServletResponse response = mvc.perform(post("/api/customer/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCustomer)).andDo(print()).andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    @Test
    void createCustomer_FirstNameIsNull() throws Exception {

        // init
        Date date = new Date();
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Customer expectedCustomer = new Customer();
        expectedCustomer.setFirstName("Sefa");
        expectedCustomer.setEmail("sefaaltundal@gmail.com");
        expectedCustomer.setPhoneNumber("5314992211");

        //when
        when(customerService.createCustomer(expectedCustomer)).thenThrow(new InvalidRequestException("Customer"));

        // Json type
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonCustomer = ow.writeValueAsString(expectedCustomer);

        //then
        MockHttpServletResponse response = mvc.perform(post("/api/customer/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCustomer)).andDo(print()).andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE.value());
    }
    @Test
    void createCustomer_LastFirstNameIsNull() throws Exception {

        // init
        Date date = new Date();
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Customer expectedCustomer = new Customer();
        expectedCustomer.setLastName("Altundal");
        expectedCustomer.setEmail("sefaaltundal@gmail.com");
        expectedCustomer.setPhoneNumber("5314992211");

        //when
        when(customerService.createCustomer(expectedCustomer)).thenThrow(new InvalidRequestException("Customer"));

        // Json type
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonCustomer = ow.writeValueAsString(expectedCustomer);

        //then
        MockHttpServletResponse response = mvc.perform(post("/api/customer/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCustomer)).andDo(print()).andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE.value());
    }
    @Test
    void createCustomer_EmailIsNull() throws Exception {

        // init
        Date date = new Date();
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Customer expectedCustomer = new Customer();
        expectedCustomer.setFirstName("Sefa");
        expectedCustomer.setLastName("Altundal");
        expectedCustomer.setPhoneNumber("5314992211");

        //when
        when(customerService.createCustomer(expectedCustomer)).thenReturn(uuid);

        // Json type
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonCustomer = ow.writeValueAsString(expectedCustomer);

        //then
        MockHttpServletResponse response = mvc.perform(post("/api/customer/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCustomer)).andDo(print()).andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE.value());
    }


}