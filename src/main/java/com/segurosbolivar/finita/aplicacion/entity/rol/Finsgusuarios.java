/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segurosbolivar.finita.aplicacion.entity.rol;


import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.segurosbolivar.finita.aplicacion.entity.Persistente;
import com.segurosbolivar.finita.aplicacion.entity.usuairo.FinsgempUsuarios;


/**
 *
 * @author calegria
 */
@Entity
public class Finsgusuarios extends Persistente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "USU_CODIGO", nullable = false, length = 8)
    private String usuCodigo;
    @Size(max = 30)
    @Column(name = "USU_NOMBRE", length = 30)
    private String usuNombre;
    @Size(max = 1000)
    @Column(name = "USU_PASSWD", length = 1000)
    private String usuPasswd;
    
    @Transient
   	private String confirmPassword;
    
    @Size(max = 6)
    @Column(name = "USU_USU_CODIGO", length = 6)
    private String usuUsuCodigo;
    @Size(max = 1)
    @Column(name = "USU_NEGOCIA", length = 1)
    private String usuNegocia;
    @Column(name = "USU_ULTIMO_LOGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuUltimoLogin;
    @Column(name = "USU_ULTIMO_BYE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuUltimoBye;
    @Size(max = 1)
    @Column(name = "USU_ESTADO", length = 1)
    private String usuEstado;
    @Size(max = 1)
    @Column(name = "USU_EST_ACT", length = 1)
    private String usuEstAct;
    @Column(name = "USU_F_ULT_CAMBIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFUltCambio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "finsgusuarios")
    private Collection<FinsgempUsuarios> finsgempUsuariosCollection;
    

    public Finsgusuarios() {
    }
    

    public Finsgusuarios(String usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public String getUsuCodigo() {
        return usuCodigo;
    }

    public void setUsuCodigo(String usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuPasswd() {
        return usuPasswd;
    }

    public void setUsuPasswd(String usuPasswd) {
        this.usuPasswd = usuPasswd;
    }

    public String getUsuUsuCodigo() {
        return usuUsuCodigo;
    }

    public void setUsuUsuCodigo(String usuUsuCodigo) {
        this.usuUsuCodigo = usuUsuCodigo;
    }

    public String getUsuNegocia() {
        return usuNegocia;
    }

    public void setUsuNegocia(String usuNegocia) {
        this.usuNegocia = usuNegocia;
    }

    public Date getUsuUltimoLogin() {
        return usuUltimoLogin;
    }

    public void setUsuUltimoLogin(Date usuUltimoLogin) {
        this.usuUltimoLogin = usuUltimoLogin;
    }

    public Date getUsuUltimoBye() {
        return usuUltimoBye;
    }

    public void setUsuUltimoBye(Date usuUltimoBye) {
        this.usuUltimoBye = usuUltimoBye;
    }

    public String getUsuEstado() {
        return usuEstado;
    }

    public void setUsuEstado(String usuEstado) {
        this.usuEstado = usuEstado;
    }

    public String getUsuEstAct() {
        return usuEstAct;
    }

    public void setUsuEstAct(String usuEstAct) {
        this.usuEstAct = usuEstAct;
    }

    public Date getUsuFUltCambio() {
        return usuFUltCambio;
    }

    public void setUsuFUltCambio(Date usuFUltCambio) {
        this.usuFUltCambio = usuFUltCambio;
    }

    @XmlTransient
    public Collection<FinsgempUsuarios> getFinsgempUsuariosCollection() {
        return finsgempUsuariosCollection;
    }

    public void setFinsgempUsuariosCollection(Collection<FinsgempUsuarios> finsgempUsuariosCollection) {
        this.finsgempUsuariosCollection = finsgempUsuariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuCodigo != null ? usuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Finsgusuarios)) {
            return false;
        }
        Finsgusuarios other = (Finsgusuarios) object;
        if ((this.usuCodigo == null && other.usuCodigo != null) || (this.usuCodigo != null && !this.usuCodigo.equals(other.usuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.segurosbolivar.modelo.entidades.Finsgusuarios[ usuCodigo=" + usuCodigo + " ]";
    }
    
    public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
    
}
