/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author student
 */
@Entity
@Table(name = "korpa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korpa.findAll", query = "SELECT k FROM Korpa k"),
    @NamedQuery(name = "Korpa.findByIdKorpe", query = "SELECT k FROM Korpa k WHERE k.idKorpe = :idKorpe"),
    @NamedQuery(name = "Korpa.findByUkupnaCena", query = "SELECT k FROM Korpa k WHERE k.ukupnaCena = :ukupnaCena")})
public class Korpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKorpe")
    private Integer idKorpe;
    @Column(name = "ukupnaCena")
    private Integer ukupnaCena;
    @OneToMany(mappedBy = "idKorpe")
    private List<Stavka> stavkaList;
    @JoinColumn(name = "idKorisnik", referencedColumnName = "idK")
    @ManyToOne
    private Korisnik idKorisnik;

    public Korpa() {
    }

    public Korpa(Integer idKorpe) {
        this.idKorpe = idKorpe;
    }

    public Integer getIdKorpe() {
        return idKorpe;
    }

    public void setIdKorpe(Integer idKorpe) {
        this.idKorpe = idKorpe;
    }

    public Integer getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(Integer ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    @XmlTransient
    public List<Stavka> getStavkaList() {
        return stavkaList;
    }

    public void setStavkaList(List<Stavka> stavkaList) {
        this.stavkaList = stavkaList;
    }

    public Korisnik getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Korisnik idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKorpe != null ? idKorpe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korpa)) {
            return false;
        }
        Korpa other = (Korpa) object;
        if ((this.idKorpe == null && other.idKorpe != null) || (this.idKorpe != null && !this.idKorpe.equals(other.idKorpe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Korpa[ idKorpe=" + idKorpe + " ]";
    }
    
}
