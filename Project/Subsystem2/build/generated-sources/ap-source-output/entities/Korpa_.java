package entities;

import entities.Korisnik;
import entities.Stavka;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-26T00:38:23")
@StaticMetamodel(Korpa.class)
public class Korpa_ { 

    public static volatile SingularAttribute<Korpa, Integer> ukupnaCena;
    public static volatile ListAttribute<Korpa, Stavka> stavkaList;
    public static volatile SingularAttribute<Korpa, Integer> idKorpe;
    public static volatile SingularAttribute<Korpa, Korisnik> idKorisnik;

}