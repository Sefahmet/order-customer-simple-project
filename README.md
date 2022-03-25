# order-customer-simple-project
My second Spring Boot project and I will work on it.
This project will be a project where I can monitor my progress.

This project include customer-order services which are related with products and addresses. 


# Rest API

<li> Customer
    <ul>
      <li>Create(Customer)  -> POST   : /api/customer/create</li>
      <li>Update(Customer)  -> PUT    : /api/customer/update</li>
      <li>Delete(UUID)      -> DELETE : /api/customer/delete</li>
      <li>GetCustomer(UUID) -> GET : /api/customer/id/?id="customer_id"</li>
      <li>GetCustomers()    -> GET : /api/customer/all</li>
    </ul>
  </li>
<li>Order
    <ul>
      <li>Create(Order)  -> POST   : /api/order/create</li>
      <li>Update(Customer)  -> PUT    : /api/order/update</li>
      <li>Delete(UUID)      -> DELETE : /api/order/delete</li>
      <li>GetOrder(UUID) -> GET : /api/order/id/?id="order_id"</li>
      <li>Customer's_Orders(UUID)    -> GET : /api/order/cid/?cid="customer_id"</li>
      <li>GetOrders()    -> GET : /api/order/all</li>
      <li>ChangeStatus(UUID,String)    -> PUT : /api/order/change-status?id="uuid"&status="status"</li>
    </ul>
  </li>

# RDMS
  <img width="412" alt="Ekran Resmi 2022-03-26 01 28 49" src="https://user-images.githubusercontent.com/58665552/160210316-66aae2f4-5a72-4ae3-a645-46c061b215b7.png">
