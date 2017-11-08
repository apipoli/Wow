package pe.wow.sbma.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import pe.wow.sbma.domain.enumeration.EstadoMascotaPerdida;

/**
 * A PublicacionMascotaPerdida.
 */
@Entity
@Table(name = "publicacion_mascota_perdida")
public class PublicacionMascotaPerdida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private ZonedDateTime fecha;

    @NotNull
    @Column(name = "lugar", nullable = false)
    private String lugar;

    @Column(name = "fecha_encuentro")
    private ZonedDateTime fechaEncuentro;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMascotaPerdida estado;

    @ManyToOne(optional = false)
    @NotNull
    private User dueno;

    @ManyToOne(optional = false)
    @NotNull
    private Distrito distrito;

    @ManyToOne(optional = false)
    @NotNull
    private Mascota mascota;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public PublicacionMascotaPerdida fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public PublicacionMascotaPerdida lugar(String lugar) {
        this.lugar = lugar;
        return this;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public ZonedDateTime getFechaEncuentro() {
        return fechaEncuentro;
    }

    public PublicacionMascotaPerdida fechaEncuentro(ZonedDateTime fechaEncuentro) {
        this.fechaEncuentro = fechaEncuentro;
        return this;
    }

    public void setFechaEncuentro(ZonedDateTime fechaEncuentro) {
        this.fechaEncuentro = fechaEncuentro;
    }

    public EstadoMascotaPerdida getEstado() {
        return estado;
    }

    public PublicacionMascotaPerdida estado(EstadoMascotaPerdida estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoMascotaPerdida estado) {
        this.estado = estado;
    }

    public User getDueno() {
        return dueno;
    }

    public PublicacionMascotaPerdida dueno(User user) {
        this.dueno = user;
        return this;
    }

    public void setDueno(User user) {
        this.dueno = user;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public PublicacionMascotaPerdida distrito(Distrito distrito) {
        this.distrito = distrito;
        return this;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public PublicacionMascotaPerdida mascota(Mascota mascota) {
        this.mascota = mascota;
        return this;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
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
        PublicacionMascotaPerdida publicacionMascotaPerdida = (PublicacionMascotaPerdida) o;
        if (publicacionMascotaPerdida.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publicacionMascotaPerdida.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PublicacionMascotaPerdida{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", lugar='" + getLugar() + "'" +
            ", fechaEncuentro='" + getFechaEncuentro() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
