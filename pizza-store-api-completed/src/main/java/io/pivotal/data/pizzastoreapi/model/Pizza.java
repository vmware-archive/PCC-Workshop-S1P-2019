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

package io.pivotal.data.pizzastoreapi.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.lang.NonNull;

@Region("Pizza")
public class Pizza {

	private static final Sauce DEFAULT_SAUCE = Sauce.TOMATO;

	public static final List<String> VEGETABLE_TOPPINGS = Arrays.asList(Topping.ARUGULA.name(),
			Topping.BANANA_PEPPERS.name(), Topping.BLACK_OLIVES.name(), Topping.CHERRY_TOMATOES.name(),
			Topping.GREEN_OLIVES.name(), Topping.GREEN_PEPPERS.name(), Topping.JALAPENO.name(), Topping.MUSHROOM.name(),
			Topping.ONIONS.name());

	private static final Set<String> vegetableToppings = new HashSet<>(VEGETABLE_TOPPINGS);

	@Id
	@NonNull
	private String name = "";
	private Sauce sauce = DEFAULT_SAUCE;
	private Set<Topping> toppings = new HashSet<>();
	
	private Pizza(String name) {
		if (name == null)
			throw new NullPointerException("name cannot be null");
		this.name = name;
	}

	public static Pizza named(String name) {
		return new Pizza(name);
	}

	public Set<Topping> getToppings() {
		return toppings;
	}

	public void setToppings(Set<Topping> toppings) {
		this.toppings = toppings;
	}

	public Sauce getSauce() {
		return sauce;
	}

	public void setSauce(Sauce sauce) {
		this.sauce = sauce;
	}

	public static Sauce getDefaultSauce() {
		return DEFAULT_SAUCE;
	}

	public static List<String> getVegetableToppings() {
		return VEGETABLE_TOPPINGS;
	}

	public static Set<String> getVegetabletoppings() {
		return vegetableToppings;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return name;
	}

	public void setId(String id) {
		this.name = id;
	}

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

		Optional.ofNullable(topping).ifPresent(this.toppings::add);

		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {

		return String.format("%1$s Pizza having %2$s Sauce with Toppings %3$s", getName(), getSauce(),
				Arrays.toString(getToppings().toArray()));
	}

	public Boolean isVeggie(Topping topping) {
		return vegetableToppings.contains(topping.name());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sauce == null) ? 0 : sauce.hashCode());
		result = prime * result + ((toppings == null) ? 0 : toppings.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pizza other = (Pizza) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sauce != other.sauce)
			return false;
		if (toppings == null) {
			if (other.toppings != null)
				return false;
		} else if (!toppings.equals(other.toppings))
			return false;
		return true;
	}

	public enum Sauce {

		ALFREDO, BARBECUE, HUMMUS, MARINARA, PESTO, TAPENADE, TOMATO,

	}

	public enum Topping {

		ARUGULA, BACON, BANANA_PEPPERS, BLACK_OLIVES, CHERRY_TOMATOES, CHICKEN, GREEN_OLIVES, GREEN_PEPPERS, JALAPENO,
		MUSHROOM, ONIONS, PARMESAN, PEPPERONI, SAUSAGE, CHEESE,

	}
}
