package models.interfaces;


import models.Document;

public interface ContentDtoInterface {

    /**
     * type, book or journal
     */
    enum ContenType {book,journal}

    /**
     * loads a DTO from persistence object
     *
     * @param document
     */
    ContentDtoInterface loadFromPersistentObject(Document document);

    /**
     * creates a Document object from DTO properties
     * @return the entity representation of current DTO
     */
    Document buildPersistentObject();

}
