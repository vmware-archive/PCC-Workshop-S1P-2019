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

package io.pivotal.data.pizzastoreapi.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.geode.config.annotation.UseMemberName;

import io.pivotal.data.pizzastoreapi.model.Pizza;
import io.pivotal.data.pizzastoreapi.repo.NameRepository;

/**
 * This configuration is used when you start this app with !tls spring profile.
 *
 */
@Configuration
@EnableClusterConfiguration(useHttp = true, requireHttps = false)
@EnableEntityDefinedRegions(basePackageClasses = Pizza.class)
@EnableGemfireRepositories(basePackageClasses = NameRepository.class)
@UseMemberName("PizzaStore-API")
public class CloudCacheClientConfig {


}
