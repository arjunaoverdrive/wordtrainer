package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.web.dto.PracticeSetDto;

public interface PracticeService {
    PracticeSetDto getPracticeSetDto(Integer setId);
}
