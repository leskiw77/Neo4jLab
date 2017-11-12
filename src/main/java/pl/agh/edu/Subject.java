package pl.agh.edu;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Subject {
    @GraphId
    public Long id;

    public String name;

    @Relationship(type = "TERMS", direction = Relationship.INCOMING)
    public Set<Term> terms;

    @Relationship(type = "TEACHER", direction = Relationship.OUTGOING)
    public Person preceptor;

    public Subject(String name, Person preceptor) {
        this.name = name;
        this.preceptor = preceptor;
    }
    public Subject() {
    }

    public void addTerm(Term term){
        if (terms == null) {
            terms = new HashSet<>();
        }
        terms.add(term);
    }

    @Override
    public String toString(){
        return String.format("%s - %s",this.name,this.preceptor.getName());
    }
}
