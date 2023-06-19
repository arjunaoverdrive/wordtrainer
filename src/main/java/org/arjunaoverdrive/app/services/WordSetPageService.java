package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.web.dto.WordSetResponseDto;

import java.util.List;

public interface WordSetPageService {
    List<WordSetResponseDto> findWordSets();
}
