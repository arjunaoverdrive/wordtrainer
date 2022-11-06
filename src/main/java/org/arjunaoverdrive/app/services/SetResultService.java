package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.DTO.ResultDto;
import org.arjunaoverdrive.app.DTO.WordRes;

import java.util.List;

public interface SetResultService {
    void save(Integer id, ResultDto result);

    void updateWordsRates(List<WordRes> wordResultsList);
}
