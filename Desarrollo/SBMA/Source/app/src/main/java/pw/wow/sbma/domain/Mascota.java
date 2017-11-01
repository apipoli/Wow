package pw.wow.sbma.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import pw.wow.sbma.domain.enumeration.Tamano;

/**
 * A Mascota.
 */
@Entity
@Table(name = "mascota")
public class Mascota implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "meses", nullable = false)
    private String meses;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tamano", nullable = false)
    private Tamano tamano;

    @ManyToOne(optional = false)
    @NotNull
    private Raza raza;

    @ManyToOne(optional = false)
    @NotNull
    private User dueno;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Mascota nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMeses() {
        return meses;
    }

    public Mascota meses(String meses) {
        this.meses = meses;
        return this;
    }

    public void setMeses(String meses) {
        this.meses = meses;
    }

    public Tamano getTamano() {
        return tamano;
    }

    public Mascota tamano(Tamano tamano) {
        this.tamano = tamano;
        return this;
    }

    public void setTamano(Tamano tamano) {
        this.tamano = tamano;
    }

    public Raza getRaza() {
        return raza;
    }

    public Mascota raza(Raza raza) {
        this.raza = raza;
        return this;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    public User getDueno() {
        return dueno;
    }

    public Mascota dueno(User user) {
        this.dueno = user;
        return this;
    }

    public void setDueno(User user) {
        this.dueno = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mascota mascota = (Mascota) o;
        if (mascota.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mascota.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mascota{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", meses='" + getMeses() + "'" +
            ", tamano='" + getTamano() + "'" +
            "}";
    }
}
