package entities;

import entities.Korisnik;
import entities.Stavka;
import entities.Transakcija;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-26T00:38:23")
@StaticMetamodel(Narudzbina.class)
public class Narudzbina_ { 

    public static volatile SingularAttribute<Narudzbina, Korisnik> idK;
    public static volatile SingularAttribute<Narudzbina, Integer> idN;
    public static volatile SingularAttribute<Narudzbina, Integer> ukupnaCena;
    public static volatile SingularAttribute<Narudzbina, Date> vremeKreiranja;
    public static volatile SingularAttribute<Narudzbina, String> adresa;
    public static volatile ListAttribute<Narudzbina, Stavka> stavkaList;
    public static volatile ListAttribute<Narudzbina, Transakcija> transakcijaList;
    public static volatile SingularAttribute<Narudzbina, Integer> idG;

}