package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.web.dto.user.UserStatsDto;

public interface UserStatisticsService {
    UserStatsDto getStatistics(User user);
}
