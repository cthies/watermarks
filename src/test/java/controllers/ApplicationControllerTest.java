/**
 * Copyright (C) 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import static org.junit.Assert.assertTrue;
import ninja.NinjaTest;

import org.junit.Test;

/*
 * test for {ApplicationController}
 */

public class ApplicationControllerTest extends NinjaTest {

    @Test
    public void testUndefinedRoute() {
        // test wrong route
        String result = ninjaTestBrowser.makeJsonRequest(getServerAddress() + "/whatever");
        assertTrue(result.contains("Bad Request"));
    }

}
