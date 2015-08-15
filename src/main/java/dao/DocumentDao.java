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

package dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import models.dtos.WatermarkDto;
import models.Document;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.google.inject.Provider;


public class DocumentDao {
   
    @Inject
    Provider<EntityManager> entitiyManagerProvider;
    


    @UnitOfWork
    public WatermarkDto getDocument(Long id) {

        Document document = fetchDocument(id);
        return buildDto(document);


    }

    @UnitOfWork
    public WatermarkDto getDocument(WatermarkDto documentDto) {
        Document document = fetchDocument(documentDto.buildPersistentObject());
        return buildDto(document);
    }



    /**
     * Returns false if document already exists.
     */
    @Transactional
    public boolean saveDocument(WatermarkDto documentDto) {

        Document document = documentDto.buildPersistentObject();

        if (fetchDocument(document) != null) {
            return false;
        }

        EntityManager entityManager = entitiyManagerProvider.get();
        entityManager.persist(document);
        entityManager.flush();
        return true;
        
    }


    /**
     *
     * @param documentId
     */
    @Transactional
    public boolean saveWatermark(Long documentId) {
        Document document = fetchDocument(documentId);
        if (null == document) {
            return false;
        }

        EntityManager entityManager = entitiyManagerProvider.get();
        document.isWatermarked = true;
        entityManager.persist(document);
        entityManager.flush();
        return true;
    }





    private Document fetchDocument(Document document) {

        EntityManager entityManager = entitiyManagerProvider.get();
        Query query = entityManager.createQuery(
                "SELECT x FROM Document x WHERE x.title = :title AND x.author = :author AND x.content = :content AND " +
                        (null == document.topic? "x.topic is null" : "x.topic = :topic")
        );
        query.setParameter("title", document.title)
                .setParameter("author", document.author)
                .setParameter("content", document.content);

        if (null != document.topic) {
            query.setParameter("topic", document.topic);
        }

        return executeQuery(query);
    }


    private Document fetchDocument(Long id) {
        EntityManager entityManager = entitiyManagerProvider.get();
        Query query = entityManager.createQuery("SELECT x FROM Document x WHERE x.id = :idParam");
        query.setParameter("idParam", id);

        return executeQuery(query);
    }

    private WatermarkDto buildDto(Document document) {
        if (null == document) {
            return null;
        }

        WatermarkDto result =  new WatermarkDto();

        return result.loadFromPersistentObject(document);
    }

    private Document executeQuery(Query query) {
        Document document;
        try {
            document = (Document) query.getSingleResult();
        } catch (NoResultException ex) {
            document = null;
        }
        return document;
    }


}
