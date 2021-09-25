package melo.rodrigues.guilherme.multitenantapp.repositories;

import melo.rodrigues.guilherme.multitenantapp.entities.Note;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface NoteRepository extends Repository<Note, Long> {

    Note save(Note note);

    Optional<Note> findById(Long id);

    List<Note> findAll();
}
