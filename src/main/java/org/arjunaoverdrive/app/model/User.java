package org.arjunaoverdrive.app.model;

import org.arjunaoverdrive.app.security.ApplicationUserRole;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private ApplicationUserRole role;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "createdBy")
    private Set<WordSet> userSets;

    @ManyToMany(mappedBy = "practicedBy")
    private Set<WordSet> practicedSets;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, ApplicationUserRole role, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ApplicationUserRole getRole() {
        return role;
    }

    public void setRole(ApplicationUserRole role) {
        this.role = role;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public Set<WordSet> getUserSets() {
        return userSets;
    }

    public void setUserSets(Set<WordSet> userSets) {
        this.userSets = userSets;
    }

    public Collection<WordSet> getPracticedSets() {
        return practicedSets;
    }

    public void setPracticedSets(Set<WordSet> practicedSets) {
        this.practicedSets = practicedSets;
    }
}