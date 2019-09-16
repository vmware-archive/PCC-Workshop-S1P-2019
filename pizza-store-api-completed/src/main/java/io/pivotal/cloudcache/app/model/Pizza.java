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

package io.pivotal.cloudcache.app.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Region("Pizza")
@RequiredArgsConstructor(staticName = "named")
@EqualsAndHashCode(of = { "name", "sauce", "toppings"})
public class Pizza {

    private static final Sauce DEFAULT_SAUCE = Sauce.TOMATO;

//    public static final Set<String> VEGETABLE_TOPPINGS = new HashSet<>(Arrays.asList(SET_VALUES));
    public static final String[] VEGETABLE_TOPPINGS = new String[] { Topping.ARUGULA.name(),
            Topping.BANANA_PEPPERS.name(),
            Topping.BLACK_OLIVES.name(),
            Topping.CHERRY_TOMATOES.name(),
            Topping.GREEN_OLIVES.name(),
            Topping.GREEN_PEPPERS.name(),
            Topping.JALAPENO.name(),
            Topping.MUSHROOM.name(),
            Topping.ONIONS.name() };
/*    
    private static final Set<String> vegetableToppings = new HashSet<>(Arrays.asList<String>(
    		VEGETABLE_TOPPINGS));
*/    
    @Getter
    private Set<Topping> toppings = new HashSet<>();

    @Getter @Id @NonNull
    private final String name;

    @Getter
    private Sauce sauce = DEFAULT_SAUCE;

    public boolean has(Topping topping) {
        return this.toppings.contains(topping);
    }

    public boolean uses(Sauce sauce) {
        return this.sauce.equals(sauce);
    }

    public Pizza having(Sauce sauce) {
        this.sauce = Optional.ofNullable(sauce).orElse(DEFAULT_SAUCE);
        return this;
    }

    public Pizza with(Topping topping) {

        Optional.ofNullable(topping)
            .ifPresent(this.toppings::add);

        return this;
    }

    @Override
    public String toString() {

        return String.format("%1$s Pizza having %2$s Sauce with Toppings %3$s",
            getName(), getSauce(), Arrays.toString(getToppings().toArray()));
    }
/*
    public Boolean isVeggie() {
        
    	Iterator toppingsIterator = toppings.iterator();
    	toppingsIterator.forEachRemaining(topping -> {
    		VEGETABLE_TOPPINGS.contains("ARGULA");
    	};
    }
*/
    public enum Sauce {

        ALFREDO,
        BARBECUE,
        HUMMUS,
        MARINARA,
        PESTO,
        TAPENADE,
        TOMATO,

    }

    public enum Topping {

        ARUGULA,
        BACON,
        BANANA_PEPPERS,
        BLACK_OLIVES,
        CHERRY_TOMATOES,
        CHICKEN,
        GREEN_OLIVES,
        GREEN_PEPPERS,
        JALAPENO,
        MUSHROOM,
        ONIONS,
        PARMESAN,
        PEPPERONI,
        SAUSAGE,
        CHEESE,

    }
}
