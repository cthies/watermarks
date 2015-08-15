package models.dtos;

import models.Document;
import models.interfaces.ContentDtoInterface;
import models.interfaces.WatermarkDtoInterface;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class WatermarkDto implements ContentDtoInterface{

    public Long id;

    @NotNull
    @Size(max = 500)
    public String title;

    @NotNull
    @Size(max = 300)
    public String author;
    
    public String topic;

    public WatermarkDtoInterface watermark;

    public String content;

    @Override
    public WatermarkDto loadFromPersistentObject(Document document) {
            id =  document.id;
            title =  document.title;
            author =  document.author;
            content =  document.content;
            topic =  document.topic;
            boolean isWatermarked =  document.isWatermarked;

            if (isWatermarked) {
                if (content == ContenType.book.name()) {
                    this.watermark = new BookWatermarkDto();
                } else {
                    this.watermark = new JournalWatermarkDto();
                }

                this.watermark.loadFromPersistentObject(document);
            }


        return this;
    }

    @Override
    public Document buildPersistentObject() {
        if (null == watermark) {
            return null;
        }

        Document document =  watermark.buildPersistentObject();
        if (null != id) {
            document.id = id;
        }

        return document;
    }
}
