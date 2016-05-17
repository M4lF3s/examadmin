package de.fsmpi.examadmin.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import de.fsmpi.examadmin.domain.enumeration.Art;

/**
 * A Klausur.
 */
@Entity
@Table(name = "klausur")
public class Klausur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "pfad", nullable = false)
    private String pfad;

    @NotNull
    @Column(name = "pruefungsform", nullable = false)
    private String pruefungsform;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "art", nullable = false)
    private Art art;

    @NotNull
    @Column(name = "k_id", nullable = false)
    private Integer kID;

    @NotNull
    @Column(name = "semester", nullable = false)
    private String semester;

    @ManyToOne
    private Dozent dozent;

    @ManyToOne
    private Fach fach;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPfad() {
        return pfad;
    }

    public void setPfad(String pfad) {
        this.pfad = pfad;
    }

    public String getPruefungsform() {
        return pruefungsform;
    }

    public void setPruefungsform(String pruefungsform) {
        this.pruefungsform = pruefungsform;
    }

    public Art getArt() {
        return art;
    }

    public void setArt(Art art) {
        this.art = art;
    }

    public Integer getkID() {
        return kID;
    }

    public void setkID(Integer kID) {
        this.kID = kID;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Dozent getDozent() {
        return dozent;
    }

    public void setDozent(Dozent dozent) {
        this.dozent = dozent;
    }

    public Fach getFach() {
        return fach;
    }

    public void setFach(Fach fach) {
        this.fach = fach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Klausur klausur = (Klausur) o;
        if(klausur.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, klausur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Klausur{" +
            "id=" + id +
            ", pfad='" + pfad + "'" +
            ", art='" + art + "'" +
            ", pruefungsform='" + pruefungsform + "'" +
            ", art='" + art + "'" +
            ", kID='" + kID + "'" +
            ", semester='" + semester + "'" +
            '}';
    }
}
