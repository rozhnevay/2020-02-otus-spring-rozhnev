package ru.otus.spring.controller.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.AuthorController;
import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorControllerImpl implements AuthorController {

    private final AuthorService authorService;

    private final ModelMapper mapper;

    @Override
    public List<AuthorDto> list() {
        return authorService.list().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public void create(AuthorDto authorDto) {
        authorService.save(convertDtoToEntity(authorDto));
    }

    @Override
    public void update(long id, AuthorDto authorDto) throws AuthorNotFoundException {
        authorDto.setId(id);
        authorService.save(convertDtoToEntity(authorDto));
    }

    private Author convertDtoToEntity(AuthorDto authorDto) {
        return mapper.map(authorDto, Author.class);
    }

    private AuthorDto convertEntityToDto(Author author) {
        return mapper.map(author, AuthorDto.class);
    }
}
