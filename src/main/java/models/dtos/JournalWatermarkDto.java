package models.dtos;


import models.Document;
import models.interfaces.WatermarkDtoInterface;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static models.interfaces.ContentDtoInterface.ContenType.journal;

public class JournalWatermarkDto implements WatermarkDtoInterface {

    public ContenType content = journal;

    @NotNull
    @Size(max = 500)
    public String title;

    @NotNull
    @Size(max = 500)
    public String author;


    public JournalWatermarkDto(){}

    public JournalWatermarkDto(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public JournalWatermarkDto loadFromPersistentObject(Document persistentObject) {
        title =  persistentObject.title;
        author = persistentObject.author;
        return this;
    }

    @Override
    public Document buildPersistentObject() {
        return new Document(title, author, null, journal.name());
    }

}
