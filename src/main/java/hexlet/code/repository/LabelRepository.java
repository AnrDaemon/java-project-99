package hexlet.code.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hexlet.code.model.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    Optional<Label> findByName(String name);

    Set<Label> findByIdIn(Set<Long> labelIds);
}
