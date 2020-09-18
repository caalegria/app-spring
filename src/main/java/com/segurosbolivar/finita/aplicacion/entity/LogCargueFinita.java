package com.segurosbolivar.finita.aplicacion.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author calegria
 */
@Entity
@Table(name = "LOG_CARGUE_FINITA")
public class LogCargueFinita implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 4000)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
    @Size(max = 60)
    @Column(name = "PROCESO_CREACION")
    private String procesoCreacion;
    @Size(max = 10)
    @Column(name = "TIPO_ERROR")
    private String tipoError;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIAL")
    private BigDecimal secuencial;

    public LogCargueFinita() {
    }

    public LogCargueFinita(BigDecimal secuencial) {
        this.secuencial = secuencial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getProcesoCreacion() {
        return procesoCreacion;
    }

    public void setProcesoCreacion(String procesoCreacion) {
        this.procesoCreacion = procesoCreacion;
    }

    public String getTipoError() {
        return tipoError;
    }

    public void setTipoError(String tipoError) {
        this.tipoError = tipoError;
    }

    public BigDecimal getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(BigDecimal secuencial) {
        this.secuencial = secuencial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencial != null ? secuencial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogCargueFinita)) {
            return false;
        }
        LogCargueFinita other = (LogCargueFinita) object;
        if ((this.secuencial == null && other.secuencial != null) || (this.secuencial != null && !this.secuencial.equals(other.secuencial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.LogCargueFinita[ secuencial=" + secuencial + " ]";
    }
    
}
