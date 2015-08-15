package models;


import javax.persistence.*;


@Entity
@Table(name = "WatermarkedDocument",uniqueConstraints =
@UniqueConstraint(name = "content_identifier", columnNames = {"content", "title", "author", "topic"}))
public class Document {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    @Column(nullable = false)
    public String content;

    @Column(nullable = true)
    public String topic;

    @Column(nullable = false)
    public String author;

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public boolean isWatermarked = false;

    public Document(String title, String author, String topic, String content) {
        this.author = author;
        this.title = title;
        this.topic = topic;
        this.content = content;
    }

    public Document() {
    }
}
