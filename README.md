# Watermark service

A global publishing company that publishes books and journals wants to develop a service to watermark their documents. Book publications include topics in business, science and media. Journals don’t include any specific topics. A document (books, journals) has a title, author and a watermark property. An empty watermark property indicates that the document has not been watermarked yet.

The watermark service has to be asynchronous. For a given content document the service should return a ticket, which can be used to poll the status of processing. If the watermarking is finished the document can be retrieved with the ticket. The watermark of a book or a journal is identified by setting the watermark property of the object. For a book the watermark includes the properties content, title, author and topic. The journal watermark includes the content, title and author. 

Examples for watermarks:
{content:”book”, title:”The Dark Code”, author:”Bruce Wayne”, topic:”Science”}
{content:”book”, title:”How to make money”, author:”Dr. Evil”, topic:”Business”}
{content:”journal”, title:”Journal of human flight routes”, author:”Clark Kent”}


## Technology

REST service based on [ninja framework](http://www.ninjaframework.org/). 
ninja framework requires java and maven, for more information please have a look at the 
[ninja documentation](http://www.ninjaframework.org/documentation/getting_started/installing_ninja.html) 


## Get started

Clone this project and go to your watermark-service directory.

Compile the application:

    mvn compile

Start ninja:

    mvn ninja:run

The application is available at <code>http://localhost:8080</code>

## Post a journal:

    curl -H "Content-Type: application/json" -X POST -d '{"title":"yourTitle","author":"authorsName"}' http://localhost:8080/watermark/journal

This returns the json with id and an empty watermark:

```
#!json
{"id":1,"title":"yourTitle","author":"authorsName","topic":null,"watermark":null,"content":"journal"}C
```

If this document is already watermarked, the post returns json with watermark:
```
#!json
{"id":1,"title":"yourTitle","author":"authorsName","topic":null,"watermark":{"content":"journal","title":"yourTitle","author":"authorsName"},"content":"journal"}
```

## Post a book

    curl -H "Content-Type: application/json" -X POST -d '{"title":"title","author":"authorName", "topic":"topic"}' http://localhost:8080/watermark/book
    
This returns the json with id and an empty watermark:

```
#!json
{"id":2,"title":"title","author":"authorName","topic":"topic","watermark":null,"content":"book"}
```

If this document is already watermarked, the post returns json with watermark:

```
#!json
{"id":2,"title":"title","author":"authorName","topic":"topic","watermark":{"content":"book","title":"title","author":"authorName","topic":"topic"},"content":"book"}
```

## Get watermark

    curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/watermark/{document_id}

If the watermark does not exists it will returns an error:

```
#!json
{"error":"Not Found"}
```

otherwise it will returns the watermark json:

```
#!json
{"content":"book","title":"title","author":"authorName","topic":"topic"}
```

## Test

Run tests with:

    mvn test

