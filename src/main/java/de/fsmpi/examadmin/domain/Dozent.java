package de.fsmpi.examadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Dozent.
 */
@Entity
@Table(name = "dozent")
public class Dozent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "vorname", nullable = false)
    private String vorname;

    @NotNull
    @Column(name = "nachname", nullable = false)
    private String nachname;

    @NotNull
    @Column(name = "d_id", nullable = false)
    private Integer dID;

    @OneToMany(mappedBy = "dozent")
    @JsonIgnore
    private Set<Klausur> klausurs = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "dozent_fach",
               joinColumns = @JoinColumn(name="dozents_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="faches_id", referencedColumnName="ID"))
    private Set<Fach> faches = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Integer getdID() {
        return dID;
    }

    public void setdID(Integer dID) {
        this.dID = dID;
    }

    public Set<Klausur> getKlausurs() {
        return klausurs;
    }

    public void setKlausurs(Set<Klausur> klausurs) {
        this.klausurs = klausurs;
    }

    public Set<Fach> getFaches() {
        return faches;
    }

    public void setFaches(Set<Fach> faches) {
        this.faches = faches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dozent dozent = (Dozent) o;
        if(dozent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dozent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Dozent{" +
            "id=" + id +
            ", vorname='" + vorname + "'" +
            ", nachname='" + nachname + "'" +
            ", dID='" + dID + "'" +
            '}';
    }
}
