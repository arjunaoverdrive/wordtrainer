package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.dao.WordSetStatsRepository;
import org.arjunaoverdrive.app.model.*;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.services.user.UserService;
import org.arjunaoverdrive.app.web.dto.ResultDto;
import org.arjunaoverdrive.app.web.dto.statistics.WordSetDetailedStatsDto;
import org.arjunaoverdrive.app.web.dto.statistics.WordStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordSetStatsServiceImpl implements WordSetStatsService {

    private final WordSetStatsRepository wordSetStatsRepository;
    private final WordSetService wordSetService;
    private final UserService userService;


    @Autowired
    public WordSetStatsServiceImpl(WordSetStatsRepository wordSetStatsRepository, WordSetService wordSetService, UserService userService) {
        this.wordSetStatsRepository = wordSetStatsRepository;
        this.wordSetService = wordSetService;
        this.userService = userService;
    }


    @Override
    public WordSetStats saveResults(User user, ResultDto result) {
        WordSetStats setStats = new WordSetStats();
        setStats.setWordSet(wordSetService.findById(result.getSetId()));
        setStats.setPracticedBy(user);
        setStats.setPracticedAt(new Timestamp(System.currentTimeMillis()));
        setStats.setLang(result.isLang() ? 1 : 0);
        setStats.setAccuracy(calculateAccuracy(result));
        WordSetStats saved = wordSetStatsRepository.save(setStats);
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


    @Override
    public Set<WordSetStats> getWordSetStatsForUser(User user) {
        return wordSetStatsRepository.findAllByPracticedBy(user);
    }

    @Override
    public WordSetDetailedStatsDto getWordSetDetailedStats(Integer setId, User user) {
        WordSetDetailedStatsDto wordSetDetailedStatsDto = new WordSetDetailedStatsDto();
        WordSet ws = wordSetService.findById(setId);
        wordSetDetailedStatsDto.setSetId(setId);
        wordSetDetailedStatsDto.setName(ws.getName());

        List<WordStatsDto> wordStatsDtos = createWordStatsDtos(ws);
        wordSetDetailedStatsDto.setWordStatsDtos(wordStatsDtos);

        return wordSetDetailedStatsDto;
    }

    private List<WordStatsDto> createWordStatsDtos( WordSet ws) {
        List<WordSetStats> wordSetStats = wordSetStatsRepository.findByWordSet(ws);

        Map<Long, Float> wordIdsToRateSource = getWordIdsToRate(wordSetStats, 1);
        Map<Long, Float> wordIdsToRateTarget = getWordIdsToRate(wordSetStats, 0);

        List<Word> words = getSortedWords(ws);

        List<WordStatsDto> wordStatsDtos = new ArrayList<>();
        for(Word w : words){
            WordStatsDto wsd = new WordStatsDto();
            wsd.setWord(w.getWord());
            wsd.setTranslation(w.getTranslation());
            wsd.setRateSource(wordIdsToRateSource.computeIfAbsent( w.getId(), k -> 0f));
            wsd.setRateTarget(wordIdsToRateTarget.computeIfAbsent(w.getId(), k -> 0f));
            wordStatsDtos.add(wsd);
        }

        return wordStatsDtos;
    }

    private List<Word> getSortedWords(WordSet ws) {
        List<Word> words = ws.getWordList().stream()
                .sorted(Comparator.comparing(Word::getWord))
                .collect(Collectors.toList());
        return words;
    }

    private Map<Long, Float> getWordIdsToRate(List<WordSetStats> wordSetStats, int lang) {
        List<WordSetStats> wss = getWordSetStatsByLang(wordSetStats, lang);
        List<Set<WordStat>> wordStats = getWordStatSetsList(wss);
        Map<Long, Float> wordIdsToRate = getWordIdsToRate(wordStats);
        return wordIdsToRate;
    }

    private List<Set<WordStat>> getWordStatSetsList(List<WordSetStats> wss) {
        List<Set<WordStat>> wordStatsSetsList = wss.stream()
                .map(WordSetStats::getWordStats)
                .collect(Collectors.toList());
        return wordStatsSetsList;
    }

    private Map<Long, Float> getWordIdsToRate(List<Set<WordStat>> wordStatsSetsList) {
        Map<Long, Float> idToRate = new HashMap<>();

        for(Set<WordStat> wordStatSet : wordStatsSetsList){
            for(WordStat wordStat : wordStatSet){
                idToRate.compute(wordStat.getWordId(),
                        (k, v) -> v == null ? 1f / wordStat.getRate() : (v += (1f / wordStat.getRate())));
            }
        }
        int count = wordStatsSetsList.size();

        idToRate.entrySet().forEach(e -> e.setValue((e.getValue() / count)));

        return idToRate;
    }

    private List<WordSetStats> getWordSetStatsByLang(List<WordSetStats> wordSetStats, int lang) {
        List<WordSetStats> filtered = wordSetStats.stream()
                .filter(wss -> wss.getLang() == lang)
                .collect(Collectors.toList());
        return filtered;
    }

}
