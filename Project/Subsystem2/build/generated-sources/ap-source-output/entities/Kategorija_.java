package entities;

import entities.Artikal;
import entities.Kategorija;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-26T00:38:23")
@StaticMetamodel(Kategorija.class)
public class Kategorija_ { 

    public static volatile ListAttribute<Kategorija, Kategorija> kategorijaList;
    public static volatile SingularAttribute<Kategorija, Integer> idK;
    public static volatile SingularAttribute<Kategorija, Kategorija> idNatK;
    public static volatile SingularAttribute<Kategorija, String> naziv;
    public static volatile ListAttribute<Kategorija, Artikal> artikalList;

}