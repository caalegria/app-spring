/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segurosbolivar.finita.aplicacion.entity.usuairo;

import java.io.Serializable;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.segurosbolivar.finita.aplicacion.entity.rol.Finsgusuarios;


/**
 *
 * @author calegria
 */
@Entity
@Table(name = "FINSGEMP_USUARIOS")
public class FinsgempUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FinsgempUsuariosPK finsgempUsuariosPK;
    
    @JoinColumn(name = "EXU_USU_CODIGO", referencedColumnName = "USU_CODIGO", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Finsgusuarios finsgusuarios;

    public FinsgempUsuarios() {
    }

    public FinsgempUsuarios(FinsgempUsuariosPK finsgempUsuariosPK) {
        this.finsgempUsuariosPK = finsgempUsuariosPK;
    }

    public FinsgempUsuarios(String exuEmpCodigo, String exuUsuCodigo) {
        this.finsgempUsuariosPK = new FinsgempUsuariosPK(exuEmpCodigo, exuUsuCodigo);
    }

    public FinsgempUsuariosPK getFinsgempUsuariosPK() {
        return finsgempUsuariosPK;
    }

    public void setFinsgempUsuariosPK(FinsgempUsuariosPK finsgempUsuariosPK) {
        this.finsgempUsuariosPK = finsgempUsuariosPK;
    }

    public Finsgusuarios getFinsgusuarios() {
        return finsgusuarios;
    }

    public void setFinsgusuarios(Finsgusuarios finsgusuarios) {
        this.finsgusuarios = finsgusuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (finsgempUsuariosPK != null ? finsgempUsuariosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinsgempUsuarios)) {
            return false;
        }
        FinsgempUsuarios other = (FinsgempUsuarios) object;
        if ((this.finsgempUsuariosPK == null && other.finsgempUsuariosPK != null) || (this.finsgempUsuariosPK != null && !this.finsgempUsuariosPK.equals(other.finsgempUsuariosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.segurosbolivar.modelo.entidades.FinsgempUsuarios[ finsgempUsuariosPK=" + finsgempUsuariosPK + " ]";
    }
    
}
