package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.DTO.ResultDto;

public interface SetResultService {
    void save(Integer id, ResultDto result);
}
