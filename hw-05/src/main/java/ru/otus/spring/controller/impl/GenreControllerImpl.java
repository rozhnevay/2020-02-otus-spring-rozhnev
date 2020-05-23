package ru.otus.spring.controller.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.GenreController;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreControllerImpl implements GenreController {

    private final GenreService genreService;

    private final ModelMapper mapper;

    @Override
    public List<GenreDto> list() {
        return genreService.list().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public void create(GenreDto genreDto) {
        genreService.save(convertDtoToEntity(genreDto));
    }

    @Override
    public void update(long id, GenreDto genreDto) {
        Genre genre = convertDtoToEntity(genreDto);
        genre.setId(id);
        genreService.save(genre);

    }

    private Genre convertDtoToEntity(GenreDto genreDto) {
        return mapper.map(genreDto, Genre.class);
    }

    private GenreDto convertEntityToDto(Genre genre) {
        return mapper.map(genre, GenreDto.class);
    }
}
