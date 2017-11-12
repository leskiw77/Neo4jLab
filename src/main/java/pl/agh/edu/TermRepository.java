package pl.agh.edu;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermRepository extends GraphRepository<Term> {

}
