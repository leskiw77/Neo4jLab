package pl.agh.edu;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends GraphRepository<Subject> {
    @Query("match (s:Subject{name:{name}})<-[:TERMS]-(t:Term)-[:ATTEND]->(p:Person) return p")
    List<Person> findCourseParticipants (@Param("name") String name);
}
