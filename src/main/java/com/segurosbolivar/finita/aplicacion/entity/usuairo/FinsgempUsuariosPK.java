/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segurosbolivar.finita.aplicacion.entity.usuairo;

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
public class FinsgempUsuariosPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "EXU_EMP_CODIGO", nullable = false, length = 2)
    private String exuEmpCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "EXU_USU_CODIGO", nullable = false, length = 8)
    private String exuUsuCodigo;

    public FinsgempUsuariosPK() {
    }

    public FinsgempUsuariosPK(String exuEmpCodigo, String exuUsuCodigo) {
        this.exuEmpCodigo = exuEmpCodigo;
        this.exuUsuCodigo = exuUsuCodigo;
    }

    public String getExuEmpCodigo() {
        return exuEmpCodigo;
    }

    public void setExuEmpCodigo(String exuEmpCodigo) {
        this.exuEmpCodigo = exuEmpCodigo;
    }

    public String getExuUsuCodigo() {
        return exuUsuCodigo;
    }

    public void setExuUsuCodigo(String exuUsuCodigo) {
        this.exuUsuCodigo = exuUsuCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exuEmpCodigo != null ? exuEmpCodigo.hashCode() : 0);
        hash += (exuUsuCodigo != null ? exuUsuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinsgempUsuariosPK)) {
            return false;
        }
        FinsgempUsuariosPK other = (FinsgempUsuariosPK) object;
        if ((this.exuEmpCodigo == null && other.exuEmpCodigo != null) || (this.exuEmpCodigo != null && !this.exuEmpCodigo.equals(other.exuEmpCodigo))) {
            return false;
        }
        if ((this.exuUsuCodigo == null && other.exuUsuCodigo != null) || (this.exuUsuCodigo != null && !this.exuUsuCodigo.equals(other.exuUsuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.segurosbolivar.modelo.entidades.FinsgempUsuariosPK[ exuEmpCodigo=" + exuEmpCodigo + ", exuUsuCodigo=" + exuUsuCodigo + " ]";
    }
    
}
