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

package conf;

import controllers.WatermarkController;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;

import controllers.ApplicationController;


public class Routes implements ApplicationRoutes {
    
    @Inject
    NinjaProperties ninjaProperties;

    /**
     * @param router
     * The default router of this application
     */
    @Override
    public void init(Router router) {

        router.GET().route("/watermark/{id}").with(WatermarkController.class, "watermark");

        router.POST().route("/watermark/book").with(WatermarkController.class, "bookWatermark");
        router.POST().route("/watermark/journal").with(WatermarkController.class, "journalWatermark");

        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
        router.GET().route("/.*").with(ApplicationController.class, "index");
    }

}
