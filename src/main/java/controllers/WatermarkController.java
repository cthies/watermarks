/**
 * Copyright (C) 2013 the original author or authors.
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

import com.google.inject.Inject;
import com.google.inject.Singleton;

import controllers.ApplicationController.Error;
import dao.DocumentDao;
import etc.WatermarkMaker;
import models.dtos.BookWatermarkDto;
import models.dtos.WatermarkDto;
import models.dtos.JournalWatermarkDto;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;


@Singleton
public class WatermarkController {

    @Inject
    DocumentDao documentDao;

    /**
     *
     * @param documentDto
     * @return response object
     */
    public Result bookWatermark(
            WatermarkDto documentDto,
            @PathParam("topic") String topic
    ) {
        documentDto.watermark = new BookWatermarkDto(
                documentDto.title, documentDto.author,  documentDto.topic);
        return handleDocument(documentDto);
    }

    /**
     *
     * @param documentDto the document values
     * @return response object
     */
    public Result journalWatermark(WatermarkDto documentDto) {
        documentDto.watermark = new JournalWatermarkDto(documentDto.title, documentDto.author);
        return handleDocument(documentDto);
    }


    /**
     *
     * @param id ticket of watermarked object
     * @return response object
     */
    public Result watermark(@PathParam("id") Long id) {
        WatermarkDto documentDto = documentDao.getDocument(id);
        if (null == documentDto || null == documentDto.watermark) {
            return notFound();
        }

        return Results.json().status(200).render(documentDto.watermark);
    }

    private Result handleDocument(WatermarkDto documentDto) {
        boolean created = documentDao.saveDocument(documentDto);
        documentDto = documentDao.getDocument(documentDto);
        if (created) {
            //simulate watermark making
            WatermarkMaker watermarkMaker = new WatermarkMaker(documentDao, documentDto.id);
            (new Thread(watermarkMaker)).start();
        }

        return Results.json().status(201).render(documentDto);
    }

    private Result notFound() {
        Error error = new Error();
        error.error = "Not Found";
        return Results.json().status(404).render(error);
    }

}
