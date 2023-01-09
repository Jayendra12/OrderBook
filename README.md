# OrderBook

This application allows user to store,cancel and fetch orders from orderbook

Endpoints:
Example
Get:http://localhost:8081/market/orderBook
Post:http://localhost:8081/market/placeOrder
payload:
       {
            "price":"220",
            "volume":"20",
            "side":"BUY",
            "clientOrderId":"9391"
        }
Delete:http://localhost:8081/market/cancelOrder/9398

Also  the orders stored are processed in background for concurrent  buy and sell operations using a commandLineListener
