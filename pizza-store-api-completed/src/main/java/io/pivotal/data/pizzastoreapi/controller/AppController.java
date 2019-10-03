/*
 * Copyright (C) 2018-Present Pivotal Software, Inc. All rights reserved.
 * This program and the accompanying materials are made available under
 * the terms of the under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.data.pizzastoreapi.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.data.pizzastoreapi.model.Pizza;
import io.pivotal.data.pizzastoreapi.repo.PizzaRepository;

/**
 * Implementation of all the REST APIs exposed by pizza store app
 */
@RestController
public class AppController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppController.class);
	 

    private final PizzaRepository pizzaRepository;

    public AppController(PizzaRepository pizzaRepository) {

        this.pizzaRepository = pizzaRepository;
    }

    /**
     * Health check
     */
    @GetMapping("/ping")
    public String ping() {
        return "<h1>PONG!</h1>";
    }

    /**
     * Creates a new pizza with the given name, toppings and sauce.
     * @param name
     * @param pizzaSauce
     * @param toppings
     */
    @GetMapping("/pizzas/order/{name}")
    public String order(@PathVariable("name") String name,
            @RequestParam(name = "sauce", defaultValue = "TOMATO") Pizza.Sauce pizzaSauce,
            @RequestParam(name = "toppings", defaultValue = "CHEESE") Pizza.Topping[] toppings) {

        Pizza namedPizza = Pizza.named(name).having(pizzaSauce);

        Arrays.stream(toppings).forEach(namedPizza::with);

        this.pizzaRepository.save(namedPizza);

        return String.format("<h1>Pizza [%s] Ordered</h1>", namedPizza);
    }
    
    /**
     * Returns details of a given pizza.
     * @param pizzaName
     */
    @GetMapping("/pizzas/{name}")
    public Object getNamedPizza(@PathVariable("name") String pizzaName) {

        Pizza namedPizza = this.pizzaRepository.findById(pizzaName).orElse(null);

        return namedPizza != null ? namedPizza : String.format("<h1>Pizza [%s] Not Found</h1>", pizzaName);
    }
    
    /**
     * Returns all pizzas from Pizza region.
     */
    @GetMapping("/pizzas")
    public Object getPizzas() {

        Iterable<Pizza> pizzas = this.pizzaRepository.findAll();

        return nullSafeIterable(pizzas).iterator().hasNext() ? pizzas : "<h1>No Pizzas Found</h1>";
    }
    
    /**
     * Creates some predefined pizzas.
     *
     */
    @RequestMapping("/preheatOven")
    public ResponseEntity<Object> preheatOven() {       

        Pizza plainPizza = makePlainPizza();
        Pizza fancyPizza = makeFancyPizza();
        Pizza superFancyPizza = makeSuperFancyPizza("test");

        this.pizzaRepository.save(plainPizza);
        this.pizzaRepository.save(fancyPizza);
        this.pizzaRepository.save(superFancyPizza);

        log.info("Finished baking pizzas");

        Optional<Pizza> pizza = this.pizzaRepository.findById("plain");

        if (!pizza.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!pizza.filter(it -> it.uses(Pizza.Sauce.TOMATO)).isPresent()) {

            log.info(String.format("I ordered tomato sauce; Pizza was [%s]",
                pizza.map(Pizza::toString).orElse(null)));

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!pizza.filter(it -> it.has(Pizza.Topping.CHEESE)).isPresent()) {

            log.info(String.format("Where's my cheese? Pizza was [%s]",
                pizza.map(Pizza::toString).orElse(null)));

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("<h1>OVEN HEATED!</h1>", HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/orders")
    @ResponseBody
    public String getPizzasBySauceType(@RequestParam(value = "sauce", required = true) String sauce) {

    	List<Pizza> pizzaObjects = pizzaRepository.findPizzasBySauceType(sauce);
    	
    	StringBuilder result = new StringBuilder();
        if (pizzaObjects != null && pizzaObjects.size() > 0) {

        	pizzaObjects.forEach(item -> result.append(item + "</br>"));

        	return String.format("Result [<b>%1$s</b>] <br/>", result.toString());
        }
    	return "No Results Found.";
    }

    private Pizza makeFancyPizza() {

        return Pizza.named("fancy")
            .having(Pizza.Sauce.ALFREDO)
            .with(Pizza.Topping.ARUGULA)
            .with(Pizza.Topping.CHICKEN);
    }

    private Pizza makePlainPizza() {
        return Pizza.named("plain").with(Pizza.Topping.CHEESE);
    }

    private Pizza makeSuperFancyPizza(String name) {

        return Pizza.named(name)
            .having(Pizza.Sauce.PESTO)
            .with(Pizza.Topping.CHICKEN)
            .with(Pizza.Topping.PARMESAN)
            .with(Pizza.Topping.CHERRY_TOMATOES);
    }

    private <T> Iterable<T> nullSafeIterable(Iterable<T> iterable) {
        return iterable != null ? iterable : Collections::emptyIterator;
    }
}
