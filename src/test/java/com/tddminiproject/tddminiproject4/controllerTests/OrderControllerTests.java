package com.tddminiproject.tddminiproject4.controllerTests;
import com.tddminiproject.tddminiproject4.models.Order;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.tddminiproject.tddminiproject4.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;



import java.time.LocalDate;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void createOrder_Successful() throws Exception {
        //Returns a 201 status which is success and body has the info
        String request = "{\"customerName\": \"John Doe\", \"orderDate\": \"2023-07-06\", \"shippingAddress\": \"123 Main St\", \"total\": 100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate").value("2023-07-06"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shippingAddress").value("123 Main St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(100.0))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getOrder_ExistingOrder_Successful() throws Exception {
        // Mock the behavior of the order repository
        Order order = new Order("John Doe", LocalDate.now(), "123 Main St", 100.0);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        mockMvc.perform(MockMvcRequestBuilders.get("/order/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate").value(LocalDate.now().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shippingAddress").value("123 Main St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(100.0))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getOrder_NonExistingOrder_ReturnsNotFound() throws Exception {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/order/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void createOrder_Failure() throws Exception {
        // Status = 400 which is a failure, the body states must not be empty, so it is a pass.
        String request = "{\"customerName\": \"\", \"orderDate\": null, \"shippingAddress\": \"\", \"total\": -10.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void createOrder_ValidationErrors_ShouldReturnBadRequest() throws Exception {
        String request = "{ \"customerName\": \"\", \"orderDate\": null, \"shippingAddress\": \"\", \"total\": -10.0 }";

        mockMvc.perform(MockMvcRequestBuilders.post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void updateOrder_OrderNotFound() throws Exception {
        String orderId = "1000"; // Assuming order with ID 1000 does not exist
        String request = "{\"customerName\": \"John Doe\", \"orderDate\": \"2023-07-06\", \"shippingAddress\": \"123 Main St\", \"total\": 100.0}";

        mockMvc.perform(MockMvcRequestBuilders.put("/order/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

}
