let page = document.title;

function showActive() {


}

switch (page) {
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

    case 'My Statistics':
    case 'Statistics':
        processStatisticsPage();
        break;

    case 'Account settings':
        processAccountSettingsPage();
        break;
    //todo not default
    default:
        processAddSetAndSetPage();
}

function processAddSetAndSetPage() {
    const more = document.getElementById("add-more");
    const setid = document.getElementById("set-id").value;
    const itemsBlock = document.getElementById("word-items-body");
    const saveSet = document.getElementById("save-changes");
    const deleteBtns = document.getElementsByClassName("delete-item");

    let itemsCount = getItemsCount();

    more.addEventListener("click", addOneItemMore);

    itemsBlock.onclick = function (e) {
        if (!e.target.classList.contains("delete-item")) return;
        handleDeleteClick(e);
    }

    function handleDeleteClick(event) {
        let tr = event.target.closest('tr');
        if (!tr) return;
        tr.parentElement.removeChild(tr);

        if (setid != -1) {
            let id = event.target.id;
            doAjaxCall(id, setid);
        }
        itemsCount--;
    }


    function getItemsCount() {
        let i = itemsBlock.childElementCount; 
        return i;
    }

    function addOneItemMore() {
        let oneMoreItemMarkup = generateOneMoreItemMarkup(itemsCount);
        itemsCount++;

        let oneMore = itemsBlock.appendChild(document.createElement("tr"));

        oneMore.classList.add("word-item");
        oneMore.innerHTML += oneMoreItemMarkup;
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
            for (let btn of visibleBtns) {
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

        function enableInputs() {
            for (let item of inputs) {
                item.disabled = false;
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


function processStatisticsPage() {
    // const errorZoneBnts = document.getElementsByClassName("error-zone");

    // for (btn of errorZoneBnts) {
    //     btn.addEventListener("click", openErrorsPage);
    // }

    // function openErrorsPage(event) {
    //     let setId = event.target.attributes.id.value;
    //     let lang = event.target.attributes.lang.value;
    //     window.location.replace("/problematicWords?setId=" + setId + "&lang=" + lang);
    // }
}

function processHomePage() {

    const container = document.getElementById("sets-container");

    container.onclick = function(e){
        if (e.target.classList.contains("set-page")){
            handleSetClick(e);
        } 
         else if(e.target.classList.contains("practice-set-btn")){
            handlePracticeClick(e);
        } 
        else return;
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

//import page ================================================================================

function processImportPage() {

    const customDelimiter = document.getElementById('custom');
    const predefinedDelimiters = document.getElementsByClassName('standard-delimiter');
    setform = document.getElementById('form');

    customDelimiter.addEventListener('click', disableStandardInput);

    for (el of predefinedDelimiters) {
        el.addEventListener('click', disableCustomInput);
    }

    function disableCustomInput() {
        customDelimiter.value = "";
    }

    function disableStandardInput() {
        for (el of predefinedDelimiters) {
            el.checked = false;
        }
    }


    markDefaultRadioBtnSelected();

    function markDefaultRadioBtnSelected() {
        const defaultDelimiter = document.getElementById("-");
        defaultDelimiter.checked = "checked";
    }
}


//practice page =======================================================

function processPracticePage() {

    const sourceLang = document.getElementById("source").attributes.getNamedItem("source").value;
    const targetLang = document.getElementById("target").attributes.getNamedItem("target").value;

    const translation = document.getElementById("translation");
    const answerInput = document.getElementById("answer");

    let translationBtn = document.getElementById("showTranslation");
    let practiceLanguage = getPracticeLanguage();

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

    function getPracticeLanguage() {
        let practiceLanguage = sessionStorage.getItem("practiceLanguage");

        if (practiceLanguage == null) {
            return sourceLang;
        }
        return sessionStorage.getItem("practiceLanguage");
    }

    function getAudio(text) {
        let speech = new SpeechSynthesisUtterance();
        speech.lang = practiceLanguage;
        speech.text = text;
        window.speechSynthesis.speak(speech);
    }


    //read words to an array of words
    const wordObjectsArr = cacheWordObjects();
    const resultObject = createResultObject();

    function cacheWordObjects() {

        const wordsArr = JSON.parse(hiddenListContent);
        const wordObjects = new Array();

        for (w in wordsArr) {
            let word = wordsArr[w];
            if (practiceLanguage === sourceLang) {
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

        if (practiceLanguage === sourceLang) {
            sessionStorage.setItem("practiceLanguage", targetLang);
        } else {
            sessionStorage.setItem("practiceLanguage", targetLang);
        }

        window.location.reload(true);
    }

    //displaying first element
    let currentIndex = 0;
    let wordToDisplay = wordObjectsArr[currentIndex].word;

    displayWord(wordToDisplay);

    function displayWord(word) {
        const wordItemContainer = document.getElementById("word-item");
        wordItemContainer.innerHTML = word;
        getAudio(word);
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
        let sortedResultsObject = getSortedResultsObject();
        doAjaxCallToPersistResults(sortedResultsObject);

        let attemptsMarkup = document.createElement("div");

        keys = Object.keys(sortedResultsObject);
        for (key in sortedResultsObject) {
            attemptsMarkup.innerHTML += generateMarkupForWordResArray(key, sortedResultsObject[key]);
        }
        return attemptsMarkup.innerHTML;
    }

    function doAjaxCallToPersistResults(result) {
        let ajaxCall = new XMLHttpRequest();
        let setId = getSetId();
        let url = "/results/save/";

        let dto = {};
        dto.lang = practiceLanguage;
        dto.result = result;
        dto.setId = setId;

        let json = JSON.stringify(dto);

        ajaxCall.open("post", url);
        ajaxCall.setRequestHeader("Content-type", "application/json");
        ajaxCall.send(json);
    }


    function getSortedResultsObject() {
        let sortedObject = {};
        let attemptsArr = getAttemptsArray();
        for (i of attemptsArr) {
            sortedObject[i] = populateWordResArray(i);
        }
        return sortedObject;
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
            if (i) res.push(i);
        }
        return res;
    }

    function populateWordResArray(tries) {
        let resultWordsArr = [];
        for (word in resultObject) {
            if (resultObject[word] == tries) {
                resultWordsArr.push(word);
                delete resultObject[word];
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
// account settings page =========================================================================    
function processAccountSettingsPage() {
    // const form = document.getElementsByClassName('user-settings')[0];
    const changePswdBtn = document.getElementsByClassName('change-password-btn')[0];
    const changePswdBlock = document.getElementsByClassName('change-password-block')[0];

    const pswdCheck = document.getElementsByClassName('password-check')[0];
    const pswd = document.getElementsByClassName('password')[0];
    const message = document.getElementsByClassName('message')[0];
    const userid = document.getElementById("id").value;

    const saveChanges = document.getElementsByClassName('save-changes-btn')[0];

    changePswdBtn.addEventListener('click', toggleChangePasswordBlock);
    saveChanges.addEventListener('click', validatePassword);
    pswd.addEventListener('change', enableSubmitField);


    function toggleChangePasswordBlock() {
        changePswdBlock.classList.toggle('hidden');
        toggleChangePswdBtn();
    }

    function toggleChangePswdBtn() {
        changePswdBtn.classList.toggle('hidden');
    }

    function validatePassword() {
        if (pswd.value === pswdCheck.value && changePasswordBlockExpanded()) {
            message.textContent = "Password has been updated!";
            toggleChangePasswordBlock();
            doAjaxToUpdateUser();
        }

        else if ((pswd.value !== pswdCheck.value && passwordCheckNotActive()) || !changePasswordBlockExpanded()) {
            message.textContent = "User has been updated!"
            doAjaxToUpdateUser();
        }
        else {
            message.textContent = "Passwords don't match!";
        }

        setTimeout(() => {
            message.textContent = "";
        }, 1500)
    }

    function changePasswordBlockExpanded() {
        return changePswdBtn.classList.contains('hidden');
    }

    function passwordCheckNotActive() {
        return pswdCheck.hasAttribute('disabled');
    }

    function enableSubmitField() {
        pswdCheck.removeAttribute('disabled');
        pswdCheck.setAttribute('required', 'required');
    }

    function doAjaxToUpdateUser() {
        let ajaxCall = new XMLHttpRequest();
        let url = "/user/" + userid + "/save-changes";

        let data = {};
        data.firstName = document.getElementsByClassName('first-name')[0].value;
        data.lastName = document.getElementsByClassName('last-name')[0].value;
        data.email = document.getElementsByClassName('email')[0].value;
        data.password = document.getElementsByClassName('password')[0].value;

        ajaxCall.open("post", url);
        ajaxCall.setRequestHeader("Content-type", "application/json");
        ajaxCall.send(JSON.stringify(data));
    }

}
