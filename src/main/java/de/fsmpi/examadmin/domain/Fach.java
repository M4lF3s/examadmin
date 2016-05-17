package de.fsmpi.examadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fach.
 */
@Entity
@Table(name = "fach")
public class Fach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "f_id", nullable = false)
    private Integer fID;

    @OneToMany(mappedBy = "fach")
    @JsonIgnore
    private Set<Klausur> klausurs = new HashSet<>();

    @ManyToMany(mappedBy = "faches")
    @JsonIgnore
    private Set<Dozent> dozents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getfID() {
        return fID;
    }

    public void setfID(Integer fID) {
        this.fID = fID;
    }

    public Set<Klausur> getKlausurs() {
        return klausurs;
    }

    public void setKlausurs(Set<Klausur> klausurs) {
        this.klausurs = klausurs;
    }

    public Set<Dozent> getDozents() {
        return dozents;
    }

    public void setDozents(Set<Dozent> dozents) {
        this.dozents = dozents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fach fach = (Fach) o;
        if(fach.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fach.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fach{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", fID='" + fID + "'" +
            '}';
    }
}
