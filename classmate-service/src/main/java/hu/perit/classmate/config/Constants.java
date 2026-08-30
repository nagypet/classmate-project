/*
 * Copyright 2020-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.perit.classmate.config;

import lombok.AccessLevel;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Peter Nagy
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Generated // To disable counting in unit test coverage
public class Constants
{
    public static final String SUBSYSTEM_NAME = "classmate-service";
    public static final String SCHEMA = "cmsa";
    public static final List<Integer> PAGE_SIZE_OPTIONS = List.of(25, 50, 100);
    public static final int DEFAULT_PAGESIZE = 25;

    public static final int MAX_SELECTION_SIZE = 5000;

    public static final String TRACE_ID = "traceId";
    public static final String BATCH_ID = "batchId";


    public static final int USER_ACCOUNT_CONTROLLER_BASE = 1000;
    public static final int USER_ACCOUNT_CONTROLLER_GET_MY_PROFILE = USER_ACCOUNT_CONTROLLER_BASE + 1;
    public static final int USER_ACCOUNT_CONTROLLER_REGISTER_USER = USER_ACCOUNT_CONTROLLER_BASE + 2;
}
