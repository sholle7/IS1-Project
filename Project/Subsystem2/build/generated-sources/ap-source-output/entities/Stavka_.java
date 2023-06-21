package entities;

import entities.Artikal;
import entities.Korpa;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-26T00:38:23")
@StaticMetamodel(Stavka.class)
public class Stavka_ { 

    public static volatile SingularAttribute<Stavka, Integer> idN;
    public static volatile SingularAttribute<Stavka, Integer> jedinicnaCena;
    public static volatile SingularAttribute<Stavka, Artikal> idA;
    public static volatile SingularAttribute<Stavka, Integer> idS;
    public static volatile SingularAttribute<Stavka, Integer> kolicina;
    public static volatile SingularAttribute<Stavka, Korpa> idKorpe;

}