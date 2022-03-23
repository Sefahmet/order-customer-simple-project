package com.example.tesodevchallenge.controller;

import com.example.tesodevchallenge.exception.InvalidRequestException;
import com.example.tesodevchallenge.exception.NotFoundException;
import com.example.tesodevchallenge.exception.handler.GenericExceptionHandler;
import com.example.tesodevchallenge.model.entity.Address;
import com.example.tesodevchallenge.model.entity.Customer;
import com.example.tesodevchallenge.model.entity.Order;
import com.example.tesodevchallenge.model.entity.Product;
import com.example.tesodevchallenge.service.CustomerService;
import com.example.tesodevchallenge.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderControllerTest {
    private MockMvc mvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void getOrder_Success() throws Exception{
        //init
        Date date = new Date();
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Customer customer = new Customer(uuid,
                "Sefa","Altundal","sefaaltundal@gmail.com",
                "5314992211",date,date,null,null);
        Address address = new Address("addres_line", "city","country",34000);

        Order order = new Order(uuid,15,1000,"delivered",date,date,customer,null,address);

        //when
        when(orderService.getOrder(uuid)).thenReturn(order);

        //then
        MockHttpServletResponse response = mvc.perform(
                        get("/api/order/id/?id=b03ac8f7-c531-4a35-b4e6-59dd822d8882")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    @Test
    void getOrder_NotFound() throws Exception{
        //init
        Date date = new Date();
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");

        //when
        when(orderService.getOrder(uuid)).thenThrow(new NotFoundException("Order"));

        //then
        MockHttpServletResponse response = mvc.perform(
                        get("/api/order/id/?id=b03ac8f7-c531-4a35-b4e6-59dd822d8882")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


    @Test
    void allOrder() throws Exception {
        //init
        Date date = new Date();
        UUID uuid1 = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        UUID uuid2 = UUID.fromString("9afae41c-0d67-4549-9cb3-d485858e37b0");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Customer customer = new Customer(uuid1,
                "Sefa","Altundal","sefaaltundal@gmail.com",
                "5314992211",date,date,null,null);
        Address address = new Address("addres_line", "city","country",34000);

        Order order1 = new Order(uuid1,15,1000,"delivered",date,date,customer,null,address);
        Order order2 = new Order(uuid2,15,1000,"delivered",date,date,customer,null,address);
        List<Order> orders  = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        // when
        when(orderService.getAllOrder()).thenReturn(orders);

        //then
        MockHttpServletResponse response = mvc.perform(
                        get("/api/order/all")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        // assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void getOrdersByCustomer() throws Exception{
        //init
        Date date = new Date();
        UUID uuid1 = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        UUID uuid2 = UUID.fromString("9afae41c-0d67-4549-9cb3-d485858e37b0");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Customer customer = new Customer(uuid1,
                "Sefa","Altundal","sefaaltundal@gmail.com",
                "5314992211",date,date,null,null);
        Address address = new Address("addres_line", "city","country",34000);

        Order order1 = new Order(uuid1,15,1000,"delivered",date,date,customer,null,address);
        Order order2 = new Order(uuid2,15,1000,"delivered",date,date,customer,null,address);
        List<Order> orders  = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        //when
        when(orderService.getOrdersByCustomerId(uuid1)).thenReturn(orders);

        //then
        MockHttpServletResponse response = mvc.perform(
                        get("/api/order/cid/?cid=b03ac8f7-c531-4a35-b4e6-59dd822d8882")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();
        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void deleteOrder() throws Exception {
        //init
        UUID uuid = UUID.fromString("95c394c0-aee3-4014-9501-d2df0bf4fbf3");

        //when
        when(orderService.deleteOrder(uuid)).thenReturn(true);

        //then
        MockHttpServletResponse response = mvc.perform(
                delete("/api/order/delete/?uuid=95c394c0-aee3-4014-9501-d2df0bf4fbf3")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertEquals(response.getContentAsString(),"true");

    }




    @Test
    void createOrder() throws Exception {
        // init
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        Order order = getSampleOrders().get(0);
        Address address = getSampleAddress();
        Customer customer  =getSampleCustomers().get(0);
        System.out.println(address.getId());
        System.out.println(customer.getId());
        //when
        when(orderService.createOrder(order,address.getId(),customer.getId())).thenReturn(order.getId());

        // Json type
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonCustomer = ow.writeValueAsString(order);

        //then
        MockHttpServletResponse response = mvc.perform(post("/api/order/create?address_id=b03ac8f7-c531-4a35-b4e6-59dd822d8882&customer_id=b03ac8f7-c531-4a35-b4e6-59dd822d8882")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCustomer)).andDo(print()).andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void changeStatus() {
    }

    public List<Customer>  getSampleCustomers() {
        Date date = new Date();
        UUID uuid1 = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        UUID uuid2 = UUID.fromString("1d4d5c03-f343-43fc-ba0f-a2a206dd7edf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        List<Address> addresses = new ArrayList<>();
        addresses.add(getSampleAddress());

        Customer expectedCustomer1 = new Customer(uuid1,
                "Sefa","Altundal","sefaaltundal@gmail.com",
                "5314992211",date,date,null,addresses);
        Customer expectedCustomer2 = new Customer(uuid2,
                "Ahmet Sefa","Altundal","sfltndl@gmail.com",
                "5314992212",date,date,null,addresses);


        List<Customer> expectedCustomers= new ArrayList<>();
        expectedCustomers.add(expectedCustomer1);
        expectedCustomers.add(expectedCustomer2);
        return expectedCustomers;
    }

    public List<Product> getSampleProducts(){
        Date date = new Date();
        UUID uuid = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        UUID uuid1 = UUID.fromString("9afae41c-0d67-4549-9cb3-d485858e37b0");

        Product product1 = new Product(uuid,"www.image-url.com","I phone",1000,null);
        Product product2 = new Product(uuid1,"www.image-url.com","I pad",1000,null);

        List<Product> products  =new ArrayList<>();
        products.add(product1);
        products.add(product2);

        return products;
    }
    public List<Order> getSampleOrders(){
        //init
        Date date = new Date();
        UUID uuid1 = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        UUID uuid2 = UUID.fromString("9afae41c-0d67-4549-9cb3-d485858e37b0");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        Order order1 = new Order(uuid1,15,1000,"delivered",date,date,null,null,null);
        Order order2 = new Order(uuid2,15,1000,"delivered",date,date,null,null,null);

        List<Order> orders  = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        return orders;
    }

    public Address getSampleAddress(){
        UUID uuid1 = UUID.fromString("b03ac8f7-c531-4a35-b4e6-59dd822d8882");
        Address address = new Address("addres_line", "city","country",34000);
        address.setId(uuid1);
        return address;
    }
}