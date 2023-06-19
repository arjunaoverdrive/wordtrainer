package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.services.wordset.WordSetService;
import org.arjunaoverdrive.app.web.dto.WordSetResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordSetPageServiceImpl implements WordSetPageService{

    private final WordSetService wordSetService;

    @Autowired
    public WordSetPageServiceImpl(WordSetService wordSetService) {
        this.wordSetService = wordSetService;
    }

    @Override
    public List<WordSetResponseDto> findWordSets() {
        return wordSetService.findAll()
                .stream()
                .map(ws -> new WordSetResponseDto(ws.getId(),
                        ws.getName(),
                        ws.getWordList().size(),
                        ws.getCreatedBy()))
                .collect(Collectors.toList());
    }
}
