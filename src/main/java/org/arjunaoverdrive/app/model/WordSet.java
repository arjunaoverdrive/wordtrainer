package org.arjunaoverdrive.app.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class WordSet implements Comparable<WordSet>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;

    @Column(name = "created_on", nullable = false, columnDefinition = "DATETIME")
    private Timestamp createdOn;

    @OneToMany(mappedBy = "wordSet", cascade = CascadeType.ALL)
    private List<Word>wordList;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    @Override
    public int compareTo(WordSet ws) {
        return ws.getCreatedOn().compareTo(createdOn);
    }
}
