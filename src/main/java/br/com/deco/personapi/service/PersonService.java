package br.com.deco.personapi.service;

import br.com.deco.personapi.dto.response.MessageResponseDTO;
import br.com.deco.personapi.dto.request.PersonDTO;
import br.com.deco.personapi.entity.Person;
import br.com.deco.personapi.exception.PersonNotFoundException;
import br.com.deco.personapi.mapper.PersonMapper;
import br.com.deco.personapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {
    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @PostMapping
    public MessageResponseDTO createPerson(@Valid PersonDTO personDTO){
        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created! Id: ");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO getById(Long id) throws PersonNotFoundException {
        Person person = exists(id);
        return personMapper.toDTO(person);
    }

    public void del(Long id) throws PersonNotFoundException {
        exists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO puById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        exists(id);

        Person personToUpdate= personMapper.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated! Id: ");
    }

    private Person exists(Long id) throws PersonNotFoundException{
        return personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String s) {
        return MessageResponseDTO
                .builder()
                .message(s + id)
                .build();
    }
}
