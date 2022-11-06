let page = document.title;

switch(page){
    case 'Practice':
    case 'Mistaken Words':
        processPracticePage();
        break;

    case 'Import':
        processImportPage();
        break;

    case 'Home Page':
    case 'Choose Set':
        processHomePage();
        break;

    case 'Statistics':
        processStatisticsPage();
        break;

    case 'Correct Mistakes':
        processCorrectMistakesPage();
        break;

    default:
        processAddSetAndSetPage();
}

function processAddSetAndSetPage(){
    const more = document.getElementById("add-more");
    const setid = document.getElementById("set-id").value;
    const itemsBlock = document.getElementById("word-items-body");
    const saveSet = document.getElementById("save-changes");
    const deleteBtns = document.getElementsByClassName("delete-item");

    more.addEventListener("click", addOneItemMore);

    for (let delBtn of deleteBtns) {
        delBtn.addEventListener("click", handleDeleteClick);
    }

    function handleDeleteClick(event) {
        let btn = event.target;
        let wordItem = btn.parentElement.parentElement;

        wordItem.parentElement.removeChild(wordItem);

        if (setid != -1) {
            let id = btn.attributes.id.value;
            doAjaxCall(id, setid);
        }
    }

    function addOneItemMore() {
        let i = itemsBlock.childElementCount;
        let oneMoreItemMarkup = generateOneMoreItemMarkup(i);
        let oneMore = itemsBlock.appendChild(document.createElement("tr"));

        oneMore.classList.add("word-item");
        oneMore.innerHTML += oneMoreItemMarkup;

        let newItemDeleteBtn = oneMore.lastChild.children.item(0);
        console.log("added new item");
        newItemDeleteBtn.addEventListener("click", handleDeleteClick);
    }

    function generateOneMoreItemMarkup(i) {
        const setidInputField = setid == -1 ? '' : '<input type="hidden" name="wordList[' + i + '].wordSet" value="' + setid + '"></input>';
        const newItemMarkup = '<td><input type="text" class="input word" name="wordList[' + i + '].word" required value></td>' +
            '<td><input type="text" class="input translation" name="wordList[' + i + '].translation" required value"></td>' +
            '<td><button class="delete-item" type="button">Delete</button></td>';
        return setidInputField + newItemMarkup;
    }


    if (document.title === "Set Page") {

        const editSet = document.getElementById("edit-set-btn");
        const inputs = document.getElementsByClassName("input");

        const visibleBtns = document.getElementsByClassName("edit-practice-return-btns");

        editSet.addEventListener("click", toggleSaveAndEdit);

        function toggleSaveAndEdit() {
            for (btn of visibleBtns) {
                btn.classList.toggle("hidden");
            }

            saveSet.classList.toggle("hidden");
            more.classList.toggle("hidden");
            enableInputs();
            toggleElems(deleteBtns);
        }

        function doAjaxCall(id, setid) {
            let deleteCall = new XMLHttpRequest();
            let url = "/sets/" + setid + "/words/delete/" + id;

            deleteCall.open("post", url);
            deleteCall.setRequestHeader("Content-type", "application/json");
            deleteCall.send();
        }

        function handleEditClick(event) {
            let target = event.target;

            toggleElement(target);
            enableInputs(target);
        }

        function toggleElement(elem) {
            elem.classList.toggle("hidden");

            let siblingSave = elem.nextElementSibling;
            siblingSave.classList.toggle("hidden");
        }

        function enableInputs() {
            for (let item of inputs) {
                item.attributes.removeNamedItem("disabled");
            }
            autofocusFirstWord();
        }

        function toggleElems(elems) {
            for (let elem of elems) {
                elem.classList.toggle("hidden");
            }
        }

        function autofocusFirstWord() {
            let firstWord = document.querySelector("#word-items-body > tr:nth-child(1) > td:nth-child(3) > input");
            let text = firstWord.value;
            firstWord.focus();
            firstWord.setSelectionRange(0, text.length);
        }
    }
}

function processCorrectMistakesPage(){
    const addL2Words = document.getElementById("add-l2-words");
    addL2Words.addEventListener("click", addProblematicWordsForLang2);

    const moreWords = document.getElementById("add-more-words");

    let setId = getSetId();
    let l2 = getOtherLang();

    if(setId === '0'){
        moreWords.classList.add("hidden");
    }

    function addProblematicWordsForLang2(){
        return  addProblematicWordsForL2() 
    }

    function getSetId(){
        return document.getElementById("set-id").value;
    }

    function addProblematicWordsForL2() {
        let otherLangWords = doAjaxToGetProblematicWordsL2();
        generateMarkupForL2Words(otherLangWords);
        addL2Words.classList.toggle("hidden");
    }


    function getOtherLang() {
        return getCurrentLang() === '0' ? 1 : 0;
    }

    function getCurrentLang() {
        let lang = document.getElementById("lang").value;
        return lang;
    }

    function doAjaxToGetProblematicWordsL2() {
        let ajaxCall = new XMLHttpRequest();

        let url = "/problematicWords/l2?lang=" + l2;
        url += setId === '0' ? "" : "&setId=" + setId;

        ajaxCall.open("GET", url, false);
        ajaxCall.setRequestHeader("Content-type", "application/json");

        ajaxCall.send();
        let response = ajaxCall.responseText;
        let json = JSON.parse(response);
        return json;
    }

    function generateMarkupForL2Words(response) {
        let itemsBlock = document.getElementById("word-items-body");
        let words = response.words;
        for (let word of words) {
            let currentItemsCount = itemsBlock.childElementCount;
            let oneMoreItemMarkup = generateMarkupForOneL2Word(currentItemsCount, word);
            let oneMore = itemsBlock.appendChild(document.createElement("tr"));
            oneMore.classList.add("word-item");
            oneMore.innerHTML += oneMoreItemMarkup;
            let newItemDeleteBtn = oneMore.lastChild.children.item(0);
            newItemDeleteBtn.addEventListener("click", handleDeleteClick);
        }
    }

    function generateMarkupForOneL2Word(currentItemsCount, word) {
        let wordValue = word.word;
        let translationValue = word.translation;
        const newItemMarkup = '<td><input type="text" class="input word" name="words[' + currentItemsCount + '].word" required value="' + wordValue + '"></td>' +
            '<td><input type="text" class="input translation" name="words[' + currentItemsCount + '].translation" required value="' + translationValue + '"></td>' +
            '<td><button class="delete-item" type="button">Delete</button></td>';
        return newItemMarkup;
    }

    function handleDeleteClick(event) {
        let btn = event.target;
        let wordItem = btn.parentElement.parentElement;
        wordItem.parentElement.removeChild(wordItem);
    }
}

function processStatisticsPage(){
    const errorZoneBnts = document.getElementsByClassName("error-zone");

    for (btn of errorZoneBnts) {
        btn.addEventListener("click", openErrorsPage);
    }

    function openErrorsPage(event) {
        let setId = event.target.attributes.id.value;
        let lang = event.target.attributes.lang.value;
        window.location.replace("/problematicWords?setId=" + setId + "&lang=" + lang);
    }
}

function processHomePage(){
    let setBtns = document.getElementsByClassName("set-page");
    let practiceBtns = document.getElementsByClassName("practice-set-btn");

    for (btn of setBtns) {
        btn.addEventListener("click", handleSetClick);
    }


    for (btn of practiceBtns) {
        btn.addEventListener("click", handlePracticeClick);
    }

    function handleSetClick(event) {
        let setid = event.target.attributes.id.value;
        window.location.replace("/sets/" + setid);
    }

    function handlePracticeClick(event) {
        let setid = event.target.attributes.id.value;
        window.location.replace("/practice/" + setid);
    }
}

function processImportPage(){
    markDefaultRadioBtnSelected();

    function markDefaultRadioBtnSelected() {
        const defaultDelimiter = document.getElementById("-");
        defaultDelimiter.checked = "checked";
    }
}

function processPracticePage(){
    const translation = document.getElementById("translation");
    const answerInput = document.getElementById("answer");

    let translationBtn = document.getElementById("showTranslation");
    let originalLanguage = getOriginalLanguage();

    const startOverBtn = document.getElementById("start-over");
    const typo = document.getElementById("typo");
    const hiddenListContent = document.getElementById("hiddenList").textContent;
    const changeLang = document.getElementById("change-lang-btn");

    changeLang.addEventListener("click", swapLanguages);

    startOverBtn.addEventListener("click", (event) => {
        window.location.reload(true);
    });

    let isShowTranslation = false;

    typo.addEventListener("click", handleTypo);


    //get language to practice from session storage

    function getOriginalLanguage() {
        let originalLang = sessionStorage.getItem("originalLang");
        let lang;
        if (originalLang == null) {
            lang = true;
        } else {
            lang = originalLang == "true";
        }
        return lang;
    }

    //read words to an array of words
    const wordObjectsArr = cacheWordObjects();
    const resultObject = createResultObject();

    function cacheWordObjects() {
        let words = hiddenListContent.substring(1, hiddenListContent.length - 1);
        const wordsArr = words.split(", ");
        const wordObjects = new Array();

        for (w in wordsArr) {
            let word = JSON.parse(wordsArr[w]);
            if (originalLanguage == true) {
                wordObjects.push(word);
            } else {
                let inverted = {};
                inverted.word = word.translation;
                inverted.translation = word.word;
                wordObjects.push(inverted);
            }
        }
        return wordObjects;
    }

    //create object to store results
    function createResultObject() {
        let res = {};
        for (w in wordObjectsArr) {
            let item = wordObjectsArr[w].word;
            res[item] = 0;
        }
        return res;
    }

    //swap languages

    function swapLanguages() {
        sessionStorage.setItem("originalLang", !originalLanguage);
        window.location.reload(true);
    }

    //displaying first element
    let currentIndex = 0;
    let wordToDisplay = wordObjectsArr[currentIndex].word;

    displayWord(wordToDisplay);

    function displayWord(word) {
        const wordItemContainer = document.getElementById("word-item");
        wordItemContainer.innerHTML = word;
    }

    //answer input and answer button handler
    answerInput.addEventListener("change", handleAnswer, false);
    translationBtn.addEventListener("click", handleEmptyAnswer, false);

    function handleAnswer() {
        verifyAnswer(wordToDisplay, answerInput.value.trim());
    }

    function verifyAnswer(currentWord, answer) {
        let correctAnswer = wordObjectsArr[currentIndex].translation;
        if (correctAnswer === answer) {
            if (isShowTranslation == false) {
                handleCorrectAnswer(currentWord);
            } else {
                wordObjectsArr.push(wordObjectsArr[currentIndex]);
                wordObjectsArr.splice(0, 1);
                resultObject[currentWord]++;
                wordToDisplay = wordObjectsArr[currentIndex].word;
                displayWord(wordToDisplay);
                answerInput.value = "";
                hideTranslation();
                isShowTranslation = false;
            }
        } else if (answer != correctAnswer || answer.length == 0) {
            handleIncorrectAnswer(currentWord, answer);
        }
    }

    function handleCorrectAnswer(currentWord) {
        answerInput.setAttribute("style", "background-color:#7bfd7c5e");
        wordObjectsArr.splice(0, 1);
        resultObject[currentWord]++;
        answerInput.value = "";
        
        setTimeout(() => {
            answerInput.removeAttribute("style");
        }, 150)

        if (wordObjectsArr.length > 0) {
            wordToDisplay = wordObjectsArr[currentIndex].word;
            displayWord(wordToDisplay);
        }
        else {
            showResults();
            showNavigationLinks();
        }
    }

    function handleEmptyAnswer() {
        handleIncorrectAnswer(wordToDisplay, "");
        typo.classList.add("hidden");
    }

    function handleIncorrectAnswer(currentWord, answer) {
        if (answer.length != 0) {
            typo.classList.remove("hidden");
        }
        isShowTranslation = true;
        showTranslation(currentWord);
    }

    function showTranslation() {
        translation.classList.remove("hidden");
        translation.children[0].innerHTML = wordObjectsArr[currentIndex].translation;
    }

    function handleTypo() {
        typo.classList.add("hidden");
        isShowTranslation = false;
        handleCorrectAnswer(wordToDisplay);
        hideTranslation();
    }

    function hideTranslation() {
        translation.classList.add("hidden");
    }

    //display results

    function showResults() {
        const finishBlock = document.getElementById("finish-block");

        appendResultsHeader(finishBlock);
        appendAccuracy(finishBlock);
        appendResults(finishBlock);

        const main = document.getElementById("main");
        finishBlock.classList.remove("hidden");
        main.classList.add("hidden");
    }


    function appendResultsHeader(finishBlock) {
        const finishHeading = finishBlock.appendChild(document.createElement("h3"));
        finishHeading.textContent = "Results";
    }

    function appendResults(finishBlock) {
        const resultsContainer = finishBlock.appendChild(document.createElement("div"));
        resultsContainer.id = "results-container";
        let markup = getMarkupForSortedResults();
        resultsContainer.innerHTML = markup;
    }

    function appendAccuracy(finishBlock) {
        const accuracy = finishBlock.appendChild(document.createElement("div"));
        accuracy.textContent = "Accuracy: " + calculateCorrectness() + "%";
    }

    function getSetId() {
        return document.getElementById("hiddenList").attributes.setid.value;
    }

    function getMarkupForSortedResults() {
        let sortedResultsObject = getSortedReultsArray();
        if (document.title === "Practice") {
            doAjaxCallToPersistResults(sortedResultsObject);
        } else {
            doAjaxCallToUpdateWordsRates(sortedResultsObject);
        }

        let attemptsMarkup = document.createElement("div");
        for (item of sortedResultsObject) {
            for (key in item) {
                attemptsMarkup.innerHTML += generateMarkupForWordResArray(key, item[key]);
            }
        }
        return attemptsMarkup.innerHTML;
    }

    function doAjaxCallToPersistResults(result) {
        let ajaxCall = new XMLHttpRequest();
        let setId = getSetId();
        let url = "/results/save/" + setId;

        let dto = {};
        dto.lang = originalLanguage;
        dto.result = result;

        let json = JSON.stringify(dto);
        console.log(json);

        ajaxCall.open("post", url);
        ajaxCall.setRequestHeader("Content-type", "application/json");
        ajaxCall.send(json);
    }

    function doAjaxCallToUpdateWordsRates(result){
        let ajaxCall = new XMLHttpRequest();
        let url = "/results/mistaken";

        let json = JSON.stringify(result);
        console.log(json);

        ajaxCall.open("post", url);
        ajaxCall.setRequestHeader("Content-type", "application/json");
        ajaxCall.send(json);
    }


    function getSortedReultsArray() {
        let sortedArray = new Array();
        let attemptsArr = getAttemptsArray();
        for (i of attemptsArr) {
            let wordResArr = populateWordResArray(i);
            let resItem = {};
            resItem[i] = wordResArr;
            sortedArray.push(resItem);
        }
        return sortedArray;
    }

    function getAttemptsArray() {
        let attemptsArr = [];
        for (word in resultObject) {
            let attempts = resultObject[word];
            attemptsArr[attempts] = attempts;
        }
        attemptsArr = removeEmptySlots(attemptsArr);
        attemptsArr.sort();
        attemptsArr.reverse();

        return attemptsArr;
    }

    function removeEmptySlots(arr) {
        let res = [];
        for (i of arr) {
            if (i == undefined) {
                continue;
            } res.push(i);
        }
        return res;
    }

    function populateWordResArray(tries) {
        let resultWordsArr = [];
        for (res in resultObject) {
            if (resultObject[res] == tries) {
                resultWordsArr.push(res);
                delete resultObject[res];
            }
        }
        return resultWordsArr;
    }

    function generateMarkupForWordResArray(tries, wordsArr) {
        const triesSection = document.createElement("div");
        triesSection.classList.add("tries-section")
        let triesItem = triesSection.appendChild(document.createElement("div"));
        let triesHeading = triesItem.appendChild(document.createElement("h4"));
        if (tries == 1) {
            triesHeading.textContent = "Great result: ";
            triesItem.id = "success";
        } else {
            triesHeading.textContent = tries + " attempts. Repeat these words:";
            triesItem.classList.add("attempts");
        }
        for (let i = 0; i < wordsArr.length; i++) {
            let resItem = triesItem.appendChild(document.createElement("div"));
            resItem.classList.add("res-item");
            resItem.textContent = wordsArr[i];
        }
        return triesSection.innerHTML;
    }


    function showNavigationLinks() {
        const navLinks = document.getElementById("navigation-links");
        navLinks.classList.remove("hidden");
    }

    function calculateCorrectness() {
        let keyCount = 0;
        let sum = 0;
        for (key in resultObject) {
            sum += resultObject[key];
            keyCount++;
        }
        return (Math.round(keyCount / sum * 100 - 1) % 100) + 1;
    }
}
// if (document.title === "Practice" || document.title === "Mistaken Words") {

    

// }

// else if (document.title === "Import") {
   
// }

// else 
// if (document.title === "Home Page" || document.title === "Choose Set") {

    
// }

// else if (document.title == "Statistics") {
   
// }

// else if (document.title == "Correct Mistakes") {
    
// }

// else {


// }

