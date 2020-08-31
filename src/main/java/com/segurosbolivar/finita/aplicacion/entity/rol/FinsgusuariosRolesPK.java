/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segurosbolivar.finita.aplicacion.entity.rol;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author calegria
 */
@Embeddable
public class FinsgusuariosRolesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "UXR_USU_CODIGO")
    private String uxrUsuCodigo;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "UXR_ROL_CODIGO")
    private String uxrRolCodigo;

    public FinsgusuariosRolesPK() {
    }

    public FinsgusuariosRolesPK(String uxrUsuCodigo, String uxrRolCodigo) {
        this.uxrUsuCodigo = uxrUsuCodigo;
        this.uxrRolCodigo = uxrRolCodigo;
    }

    public String getUxrUsuCodigo() {
        return uxrUsuCodigo;
    }

    public void setUxrUsuCodigo(String uxrUsuCodigo) {
        this.uxrUsuCodigo = uxrUsuCodigo;
    }

    public String getUxrRolCodigo() {
        return uxrRolCodigo;
    }

    public void setUxrRolCodigo(String uxrRolCodigo) {
        this.uxrRolCodigo = uxrRolCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uxrUsuCodigo != null ? uxrUsuCodigo.hashCode() : 0);
        hash += (uxrRolCodigo != null ? uxrRolCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinsgusuariosRolesPK)) {
            return false;
        }
        FinsgusuariosRolesPK other = (FinsgusuariosRolesPK) object;
        if ((this.uxrUsuCodigo == null && other.uxrUsuCodigo != null) || (this.uxrUsuCodigo != null && !this.uxrUsuCodigo.equals(other.uxrUsuCodigo))) {
            return false;
        }
        if ((this.uxrRolCodigo == null && other.uxrRolCodigo != null) || (this.uxrRolCodigo != null && !this.uxrRolCodigo.equals(other.uxrRolCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FinsgusuariosRolesPK[ uxrUsuCodigo=" + uxrUsuCodigo + ", uxrRolCodigo=" + uxrRolCodigo + " ]";
    }
    
}
