package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.web.DTO.ResultDto;
import org.arjunaoverdrive.app.web.DTO.WordRes;

import java.util.List;

public interface SetResultService {
    void save(ResultDto result);

    void updateWordsRates(List<WordRes> wordResultsList);
}
