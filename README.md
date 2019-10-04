# Pivotal Cloud Cache Developer Workshop

This workshop will provide developers with getting started guide for enabling Spring Boot applications with Pivotal Cloud Cache for data caching. Purpose of the workshop is to walk-through enterprise features of Cloud Cache and susbsequtly enabling these features in a Spring boot app. 

This workshop will provide a step by step guide for implementing set of APIs backed by cloud cache that simulate operations of a Pizza Store. Session includes presentations, demos and hands-on lab sessions.

### Pizza Store App supports following endpoints:

- `GET /preheatOven`

    Loads the `Pizza` region with three pre-defined pizzas.
    This REST API endpoint calls `Repository.save()` for each pizza
    and verifies the pizzas with the `Repository.findById(..)` on the
    `Pizza` region to verify that everything was setup properly.
    It creates these pizzas:

    1. tomato sauce and a cheese topping
    2. Alfredo sauce, and chicken and arugula toppings
    3. pesto sauce, and chicken, cherry tomatoes and parmesan cheese toppings
 
    Responds with an HTTP message body of "OVEN HEATED!".

    ```
    $ curl -k https://APP-URL/preheatOven
    ```

- `GET /pizzas`

    Lists the current contents of the `Pizza` region, formatted as
    a JSON array containing `Pizza` objects.
    Returns "No Pizzas Found" if the region is empty.

    ```
    $ curl -k https://APP-URL/pizzas
    ```

- `GET /pizzas/{name}`
     
    Returns the pizza with the given name in JSON form.
    Returns "Pizza \[name\] Not Found"
    if no pizza with the given name exists.

    ```
    curl -k https://APP-URL/pizzas/plain
    ```

- `GET /pizzas/order/{name}\[?sauce=<sauce>\[&toppings=\<topping-1>,\<topping-2>,...,\<topping-N>]]`

    Bakes a pizza of the user's specification,
    with an optional `sauce` (defaults to `TOMATO`)
    and optional `toppings` (defaults to `CHEESE`):

    ```
    curl -k https://APP-URL/pizzas/order/myCustomPizza?sauce=MARINARA&toppings=CHEESE,PEPPERONI,MUSHROOM
    ```

### Pizza sauces are one of:

- ALFREDO
- BARBECUE
- HUMMUS
- MARINARA
- PESTO
- TAPENADE
- TOMATO

### Pizza toppings are any of:

- ARUGULA
- BACON
- BANANA_PEPPERS
- BLACK_OLIVES
- CHEESE
- CHERRY_TOMATOES
- CHICKEN
- GREEN_OLIVES
- GREEN_PEPPERS
- JALAPENO
- MUSHROOM
- ONIONS
- PARMESAN
- PEPPERONI
- SAUSAGE

