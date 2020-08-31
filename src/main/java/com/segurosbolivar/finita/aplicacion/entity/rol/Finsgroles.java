/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segurosbolivar.finita.aplicacion.entity.rol;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author calegria
 */
@Entity
@Table(name = "FINSGROLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Finsgroles.findAll", query = "SELECT f FROM Finsgroles f"),
    @NamedQuery(name = "Finsgroles.findByRolCodigo", query = "SELECT f FROM Finsgroles f WHERE f.rolCodigo = :rolCodigo"),
    @NamedQuery(name = "Finsgroles.findByRolDescripcion", query = "SELECT f FROM Finsgroles f WHERE f.rolDescripcion = :rolDescripcion"),
    @NamedQuery(name = "Finsgroles.findByRolRolCodigo", query = "SELECT f FROM Finsgroles f WHERE f.rolRolCodigo = :rolRolCodigo")})
public class Finsgroles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ROL_CODIGO")
    private String rolCodigo;
    @Size(max = 60)
    @Column(name = "ROL_DESCRIPCION")
    private String rolDescripcion;
    @Size(max = 30)
    @Column(name = "ROL_ROL_CODIGO")
    private String rolRolCodigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "finsgroles")
    private Collection<FinsgusuariosRoles> finsgusuariosRolesCollection;

    public Finsgroles() {
    }

    public Finsgroles(String rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    public String getRolCodigo() {
        return rolCodigo;
    }

    public void setRolCodigo(String rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    public String getRolDescripcion() {
        return rolDescripcion;
    }

    public void setRolDescripcion(String rolDescripcion) {
        this.rolDescripcion = rolDescripcion;
    }

    public String getRolRolCodigo() {
        return rolRolCodigo;
    }

    public void setRolRolCodigo(String rolRolCodigo) {
        this.rolRolCodigo = rolRolCodigo;
    }

    @XmlTransient
    public Collection<FinsgusuariosRoles> getFinsgusuariosRolesCollection() {
        return finsgusuariosRolesCollection;
    }

    public void setFinsgusuariosRolesCollection(Collection<FinsgusuariosRoles> finsgusuariosRolesCollection) {
        this.finsgusuariosRolesCollection = finsgusuariosRolesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolCodigo != null ? rolCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Finsgroles)) {
            return false;
        }
        Finsgroles other = (Finsgroles) object;
        if ((this.rolCodigo == null && other.rolCodigo != null) || (this.rolCodigo != null && !this.rolCodigo.equals(other.rolCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Finsgroles[ rolCodigo=" + rolCodigo + " ]";
    }
    
}
