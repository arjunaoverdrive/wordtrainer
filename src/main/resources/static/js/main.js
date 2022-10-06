if (window.location.href.includes('/practice')) {
    const translation = document.getElementById("translation");

    const answerInput = document.getElementById("answer");
    const answerBtn = document.getElementById("verify");
    let translationBtn = document.getElementById("showTranslation");
    const startOverBtn = document.getElementById("start-over");

    startOverBtn.addEventListener("click", (event) => {
        window.location.reload(true);
    })

    let isShowTranslation = false;

    //read words to an array of words
    const wordObjectsArr = cacheWordObjects();
    const resultObject = createResultObject();

    function cacheWordObjects() {
        const hiddenListContent = document.getElementById("hiddenList").textContent;
        let words = hiddenListContent.substring(1, hiddenListContent.length - 1);
        const wordsArr = words.split(", ");
        const wordObjects = new Array();
        for (w in wordsArr) {
            let word = JSON.parse(wordsArr[w]);
            wordObjects.push(word);
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
    answerBtn.addEventListener("click", handleAnswer, false);
    translationBtn.addEventListener("click", handleIncorrectAnswer, false);

    function handleAnswer() {
        verifyAnswer(wordToDisplay, answerInput.value);
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
            handleIncorrectAnswer(currentWord);
        }
    }

    function handleCorrectAnswer(currentWord) {
        wordObjectsArr.splice(0, 1);
        resultObject[currentWord]++;
        answerInput.value = "";
        if (wordObjectsArr.length > 0) {
            wordToDisplay = wordObjectsArr[currentIndex].word;
            displayWord(wordToDisplay);
        }
        else {
            showResults();
            showNavigationLinks();
        }
    }

    function handleIncorrectAnswer(currentWord) {
        isShowTranslation = true;
        showTranslation(currentWord);
    }

    function showTranslation() {
        translation.classList.remove("hidden");
        translation.children[0].innerHTML = wordObjectsArr[currentIndex].translation;
    }

    function hideTranslation() {
        translation.classList.add("hidden");
    }

    function showResults() {
        const finishBlock = document.getElementById("finish-block");
        const finishHeading = finishBlock.appendChild(document.createElement("h3"));
        finishHeading.textContent = "Results";
        const resultsContainer = finishBlock.appendChild(document.createElement("div"));
        resultsContainer.classList.add("results-container");

        for (let res in resultObject) {
            let finishItem = resultsContainer.appendChild(document.createElement("div"));
            finishItem.classList.add("res-item");
            finishItem.textContent = res + " => " + resultObject[res] + " tries"
        }

        const accuracy = resultsContainer.appendChild(document.createElement("div"));
        accuracy.textContent = "Accuracy: " + calculateCorrectness() + "%";
        finishBlock.classList.remove("hidden");
        const main = document.getElementById("main");
        main.classList.add("hidden");
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

else if (window.location.href.endsWith("/import")) {
    console.log(true);
    markDefaultRadioBtnSelected();

    function markDefaultRadioBtnSelected() {
        const defaultDelimiter = document.getElementById("-");
        console.log("in function")
        defaultDelimiter.checked = "checked";
    }
}

else {

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
    }

    function generateOneMoreItemMarkup(i) {
        const setidInputField = setid == -1 ? '' : '<input type="hidden" name="wordList[' + i + '].wordSet" value=' + setid + '></input>';
        const newItemMarkup = '<td><input type="text" class="input word" name="wordList[' + i + '].word" required value></td>' +
            '<td><input type="text" class="input translation" name="wordList[' + i + '].translation" required value"></td>' +
            '<td><button class="delete-item" type="button">Delete</button></td>';
        return setidInputField + newItemMarkup;
    }


    if (window.location.href.includes("/sets/")) {


        const editSet = document.getElementById("edit-set-btn");
        const inputs = document.getElementsByClassName("input");

        editSet.addEventListener("click", toggleSaveAndEdit);

        function toggleSaveAndEdit() {
            editSet.classList.toggle("hidden");
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
        }

        function toggleElems(elems) {
            for (let elem of elems) {
                elem.classList.toggle("hidden");
            }
        }
    }
}
