/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segurosbolivar.finita.aplicacion.entity.rol;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.segurosbolivar.finita.aplicacion.entity.Persistente;

/**
 *
 * @author calegria
 */
@Entity
@Table(name = "FINSGUSUARIOS_ROLES")
public class FinsgusuariosRoles extends Persistente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FinsgusuariosRolesPK finsgusuariosRolesPK;
    
    @Column(name = "UXR_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uxrFecha;
    
    @Size(max = 8)
    @Column(name = "UXR_USUARIO")
    private String uxrUsuario;
    
    @JoinColumn(name = "UXR_ROL_CODIGO", referencedColumnName = "ROL_CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @NotFound(action = NotFoundAction.IGNORE) 
    private Finsgroles finsgroles;

    public FinsgusuariosRoles() {
    }

    public FinsgusuariosRoles(FinsgusuariosRolesPK finsgusuariosRolesPK) {
        this.finsgusuariosRolesPK = finsgusuariosRolesPK;
    }

    public FinsgusuariosRoles(String uxrUsuCodigo, String uxrRolCodigo) {
        this.finsgusuariosRolesPK = new FinsgusuariosRolesPK(uxrUsuCodigo, uxrRolCodigo);
    }

    public FinsgusuariosRolesPK getFinsgusuariosRolesPK() {
        return finsgusuariosRolesPK;
    }

    public void setFinsgusuariosRolesPK(FinsgusuariosRolesPK finsgusuariosRolesPK) {
        this.finsgusuariosRolesPK = finsgusuariosRolesPK;
    }

    public Date getUxrFecha() {
        return uxrFecha;
    }

    public void setUxrFecha(Date uxrFecha) {
        this.uxrFecha = uxrFecha;
    }

    public String getUxrUsuario() {
        return uxrUsuario;
    }

    public void setUxrUsuario(String uxrUsuario) {
        this.uxrUsuario = uxrUsuario;
    }

    public Finsgroles getFinsgroles() {
        return finsgroles;
    }

    public void setFinsgroles(Finsgroles finsgroles) {
        this.finsgroles = finsgroles;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (finsgusuariosRolesPK != null ? finsgusuariosRolesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinsgusuariosRoles)) {
            return false;
        }
        FinsgusuariosRoles other = (FinsgusuariosRoles) object;
        if ((this.finsgusuariosRolesPK == null && other.finsgusuariosRolesPK != null) || (this.finsgusuariosRolesPK != null && !this.finsgusuariosRolesPK.equals(other.finsgusuariosRolesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FinsgusuariosRoles[ finsgusuariosRolesPK=" + finsgusuariosRolesPK + " ]";
    }
    
}
