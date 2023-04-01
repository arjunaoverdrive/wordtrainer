package org.arjunaoverdrive.app.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "sets_2_users",
    joinColumns = @JoinColumn(
            name = "set_id", referencedColumnName = "id"
    ),
    inverseJoinColumns = @JoinColumn(
            name = "user_id", referencedColumnName = "id"
    ))
    private Set<User> practicedBy;

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }


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

    public Set<User> getPracticedBy() {
        return practicedBy;
    }

    public void setPracticedBy(Set<User> practicedBy) {
        this.practicedBy = practicedBy;
    }

    @Override
    public int compareTo(WordSet ws) {
        return ws.getCreatedOn().compareTo(createdOn);
    }
}
