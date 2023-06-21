/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author student
 */
@Entity
@Table(name = "stavka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavka.findAll", query = "SELECT s FROM Stavka s"),
    @NamedQuery(name = "Stavka.findByIdS", query = "SELECT s FROM Stavka s WHERE s.idS = :idS"),
    @NamedQuery(name = "Stavka.findByKolicina", query = "SELECT s FROM Stavka s WHERE s.kolicina = :kolicina"),
    @NamedQuery(name = "Stavka.findByJedinicnaCena", query = "SELECT s FROM Stavka s WHERE s.jedinicnaCena = :jedinicnaCena"),
    @NamedQuery(name = "Stavka.findByIdN", query = "SELECT s FROM Stavka s WHERE s.idN = :idN")})
public class Stavka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idS")
    private Integer idS;
    @Column(name = "kolicina")
    private Integer kolicina;
    @Column(name = "jedinicnaCena")
    private Integer jedinicnaCena;
    @Column(name = "idN")
    private Integer idN;
    @JoinColumn(name = "idA", referencedColumnName = "idA")
    @ManyToOne
    private Artikal idA;
    @JoinColumn(name = "idKorpe", referencedColumnName = "idKorpe")
    @ManyToOne
    private Korpa idKorpe;

    public Stavka() {
    }

    public Stavka(Integer idS) {
        this.idS = idS;
    }

    public Integer getIdS() {
        return idS;
    }

    public void setIdS(Integer idS) {
        this.idS = idS;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Integer getJedinicnaCena() {
        return jedinicnaCena;
    }

    public void setJedinicnaCena(Integer jedinicnaCena) {
        this.jedinicnaCena = jedinicnaCena;
    }

    public Integer getIdN() {
        return idN;
    }

    public void setIdN(Integer idN) {
        this.idN = idN;
    }

    public Artikal getIdA() {
        return idA;
    }

    public void setIdA(Artikal idA) {
        this.idA = idA;
    }

    public Korpa getIdKorpe() {
        return idKorpe;
    }

    public void setIdKorpe(Korpa idKorpe) {
        this.idKorpe = idKorpe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idS != null ? idS.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavka)) {
            return false;
        }
        Stavka other = (Stavka) object;
        if ((this.idS == null && other.idS != null) || (this.idS != null && !this.idS.equals(other.idS))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Stavka[ idS=" + idS + " ]";
    }
    
}
