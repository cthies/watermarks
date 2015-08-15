/**
 * Copyright (C) 2012-2015 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;



import static org.junit.Assert.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import etc.WatermarkMaker;
import ninja.NinjaTest;


/*
 * test for {WatermarkController}
 */

public class WatermarkControllerTest extends NinjaTest {

    public static final String TEST_TITLE = "test title";
    public static final String TEST_AUTHOR = "Test author";
    public static final String TEST_TOPIC = "Test topic";

    public static class DocumentPayload {
        public String title;
        public String author;
        public String topic;
    }

    @Test
    public void testPostJournal() {
        String result = ninjaTestBrowser.postJson(
                getServerAddress() + "/watermark/journal",
                getDocumentPayload(TEST_TITLE, TEST_AUTHOR));


        assertTrue(result.contains("\"id\":1"));
        assertTrue(result.contains("title"));
        assertTrue(result.contains(TEST_TITLE));
        assertTrue(result.contains("author"));
        assertTrue(result.contains(TEST_AUTHOR));
    }

    @Test
    public void testPostBook() {
        String result = ninjaTestBrowser.postJson(
                getServerAddress() + "/watermark/book",
                getBookPayload(TEST_TITLE, TEST_AUTHOR, "science"));

        assertTrue(result.contains("\"id\":1"));
        assertTrue(result.contains("title"));
        assertTrue(result.contains(TEST_TITLE));
        assertTrue(result.contains("author"));
        assertTrue(result.contains(TEST_AUTHOR));
        assertTrue(result.contains("topic"));
        assertTrue(result.contains("science"));
    }
    
    @Test
    public void testGetWatermark() {
        String result = ninjaTestBrowser.postJson(
                getServerAddress() + "/watermark/journal",
                getDocumentPayload(TEST_TITLE, TEST_AUTHOR));

        JSONObject document;
        Integer id = 0;
        try {
            document = new JSONObject(result);
            id = (Integer) document.get("id");

        } catch (JSONException e) {
            assertTrue("No JSON String", false);
        }

        String url = getServerAddress() + "/watermark/" + id.toString();
        result = ninjaTestBrowser.makeJsonRequest(url);
        assertTrue(result.contains("Not Found"));
        try {
            Thread.sleep(WatermarkMaker.WATERMARKING + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = ninjaTestBrowser.makeJsonRequest(url);
        assertTrue(result.contains("title"));
        assertTrue(result.contains(TEST_TITLE));
        assertTrue(result.contains("author"));
        assertTrue(result.contains(TEST_AUTHOR));
        assertTrue(result.contains("content"));
        assertTrue(result.contains("journal"));
    }

    private DocumentPayload getDocumentPayload(String title, String author) {
        DocumentPayload documentPayload = new DocumentPayload();
        documentPayload.title = title;
        documentPayload.author = author;
        return documentPayload;
    }
    private DocumentPayload getBookPayload(String title, String author, String topic) {
      DocumentPayload documentPayload = new DocumentPayload();
      documentPayload.title = title;
      documentPayload.author = author;
      documentPayload.topic = topic;
      return documentPayload;
    }
}
