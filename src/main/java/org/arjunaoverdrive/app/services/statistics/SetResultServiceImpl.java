package org.arjunaoverdrive.app.services.statistics;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.model.Language;
import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.services.user.UserService;
import org.arjunaoverdrive.app.services.wordset.WordSetService;
import org.arjunaoverdrive.app.web.dto.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class SetResultServiceImpl implements SetResultService {

    private final WordSetStatsService wordSetStatsService;
    private final WordStatService wordStatService;
    private final WordSetService wordSetService;
    private final UserService userService;


    @Autowired
    public SetResultServiceImpl(WordSetStatsService wordSetStatsService, WordStatService wordStatService, WordSetService wordSetService, UserService userService) {
        this.wordSetStatsService = wordSetStatsService;
        this.wordStatService = wordStatService;
        this.wordSetService = wordSetService;
        this.userService = userService;
    }

    @Override
    public void save(ResultDto result, User user) {
        WordSetStats wordSetStats = saveResults(user, result);
        wordStatService.save(result, wordSetStats);
    }

    @Override
    public WordSetStats saveResults(User user, ResultDto result) {
        WordSetStats setStats = new WordSetStats();
        setStats.setWordSet(wordSetService.findById(result.getSetId()));
        setStats.setPracticedBy(user);
        setStats.setPracticedAt(new Timestamp(System.currentTimeMillis()));
        String language = Arrays.stream(Language.values())
                .filter(l -> l.getLocale().equals(result.getLanguage()))
                .map(Language::getLanguage).
                findFirst().orElseThrow(() -> new RuntimeException("Language not found " + result.getLanguage()));
        setStats.setLanguage(language);
        setStats.setAccuracy(calculateAccuracy(result));
        WordSetStats saved = wordSetStatsService.save(setStats);
        user.addPracticedSet(saved);
        userService.save(user);
        return saved;
    }

    private float calculateAccuracy(ResultDto result) {
        int attemptsCount = result.getResult().entrySet()
                .stream()
                .map(e -> e.getValue().size() * Integer.parseInt(e.getKey()))
                .reduce(Integer::sum)
                .get();

        int wordsCount = result.getResult()
                .values().stream()
                .map(List::size)
                .reduce(Integer::sum)
                .get();

        return (float) wordsCount / attemptsCount;
    }

}
