package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.web.dto.ResultDto;

public interface SetResultService {
    void save(ResultDto result, User userFromSecurityContext);


    WordSetStats saveResults(User user, ResultDto result);
}
