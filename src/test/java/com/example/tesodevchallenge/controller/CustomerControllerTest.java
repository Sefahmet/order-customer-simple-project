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
class CustomerControllerTest {

    private MockMvc mvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    CustomerControllerTest() throws Exception {
    }

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }



    @Test
    void getCustomer_Success() throws Exception {
        Date date = new Date();
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Customer expectedCustomer = new Customer(uuid,
                                        "Sefa","Altundal","sefaaltundal@gmail.com",
                                         "5314992211",date,date,null,null);
        when(customerService.getCustomer(uuid)).thenReturn(expectedCustomer);
        MockHttpServletResponse response = mvc.perform(
                get("/api/customer/id/?id=b03ac8f7-c531-4a35-b4e6-59dd822d8882")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();
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
        MockHttpServletResponse response = mvc.perform(
                        get("/api/customer/id/?id=b03ac8f7-c531-4a35-b4e6-59dd822d8882")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    @Test
    void getCustomer_InvalidRequest() throws  Exception{
        // init
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");

        // when
        when(customerService.getCustomer(uuid)).thenThrow(new InvalidRequestException("Invalid ID"));
        MockHttpServletResponse response = mvc.perform(
                        get("/api/customer/id/?id=b03ac8f7-c531-4a35-b4e6-59dd822d8882")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        // then
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

        //wehen
        when(customerService.getAllCustomer()).thenReturn(expectedCustomers);
        MockHttpServletResponse response = mvc.perform(
                        get("/api/customer/all")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Customer >actualCustomers = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Customer>>(){});
        assertEquals(expectedCustomers.size(),actualCustomers.size());
        assertEquals(expectedCustomers.get(0).toString(),actualCustomers.get(0).toString());
    }

    @Test
    void createCustomer() throws Exception {
        // init
        Date date = new Date();
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Customer expectedCustomer = new Customer(uuid,
                "Sefa","Altundal","sefaaltundal@gmail.com",
                "5314992211",date,date,null,null);

        //when
        when(customerService.createCustomer(expectedCustomer)).thenReturn(uuid);

        // Json type
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        /*String jsonCredit = ow.writeValueAsString(credit);*/
        String jsonCredit = "{\"firstName\" : \"Sefa\",\"lastName\" : \"Altundal\",\"email\" : \"sefaaltundal1@gmail.com\",\"phoneNumber\" : \"5314992211\"}";

        MockHttpServletResponse response = mvc.perform(post("/api/customer/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCredit)).andDo(print()).andReturn().getResponse();

        System.out.println(response.getContentAsString());
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertEquals(response.getContentAsString(),uuid.toString());
    }

}