package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.web.dto.ImportDto;

public interface ImportService {

    void importSet(ImportDto importDto, User user);
}
