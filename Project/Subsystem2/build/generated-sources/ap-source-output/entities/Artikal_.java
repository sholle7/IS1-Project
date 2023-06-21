package entities;

import entities.Kategorija;
import entities.Korisnik;
import entities.Stavka;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-26T00:38:23")
@StaticMetamodel(Artikal.class)
public class Artikal_ { 

    public static volatile SingularAttribute<Artikal, Kategorija> idKat;
    public static volatile SingularAttribute<Artikal, Korisnik> idKor;
    public static volatile SingularAttribute<Artikal, Integer> idA;
    public static volatile SingularAttribute<Artikal, String> naziv;
    public static volatile ListAttribute<Artikal, Stavka> stavkaList;
    public static volatile SingularAttribute<Artikal, Integer> popust;
    public static volatile SingularAttribute<Artikal, Integer> cena;
    public static volatile SingularAttribute<Artikal, String> opis;

}