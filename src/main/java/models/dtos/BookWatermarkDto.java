package models.dtos;

import models.Document;
import models.interfaces.WatermarkDtoInterface;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static models.interfaces.ContentDtoInterface.ContenType.book;

public class BookWatermarkDto implements WatermarkDtoInterface {

    public ContenType content = book;

    @Size(min = 5)
    public String title;

    @Size(min = 5)
    public String author;
    
    @NotNull
    public String topic;

    public void BookDto(String title, String author, String topic) {
      this.title = title;
      this.author = author;
      this.topic = topic;
    }

    public BookWatermarkDto() {}

    public BookWatermarkDto(String title, String author, String topic) {
        this.title = title;
        this.author = author;
        this.topic = topic;
    }

    @Override
    public BookWatermarkDto loadFromPersistentObject(Document document) {
        title = document.title;
        author = document.author;
        topic = document.topic;

        return this;
    }

    @Override
    public Document buildPersistentObject() {
        Document document = new Document(title, author, topic, book.name());
        return document;
    }

}
