/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

document.addEventListener("DOMContentLoaded", function (event) {
    var quill = new Quill('#editor', {
        theme: 'snow'
    });
    
    const titleInput = $("#titleInput");
    const contentInput = $("#text-area");
    const saveBtn = $("#saveBtn");
    const updateBtn = $("#updateBtn");
    const deleteBtn = $("#deleteBtn");

    const curHref = window.location.href;
    const url = window.location.origin + '/auth' + window.location.pathname;

    const savedTab = $('#tab01'),
          searchTab = $('#tab02'),
          $tabButtonItem = $('#tab-button li'),
          $tabSelect = $('#tab-select'),
          $tabContents = $('.tab-contents'),
          activeClass = 'is-active',
          articleDiv = $('.article'),
          articleBtn = $('.article button'),
          addBtn = $('.addBtn'),
          removeBtn = $('.removeBtn'),
          searchBtn = $('.searchBtn'),
          savedArticlesContainer = $('#savedArticles'),
          searchArticlesContainer = $('#searchArticles'),
          keywordsContainer = $('#keywordSelect');
          
    let note = JSON.parse(localStorage.getItem('note'));
    console.log('note: ');
    console.log(note);
    
    setup();

    let savedArticles = JSON.parse(localStorage.getItem('savedArticles')) === null ? [] : JSON.parse(localStorage.getItem('savedArticles'));
    console.log('articles: ');
    console.log(savedArticles);
    
    let articlesAll = [];

    let toBeAddedArticles = [];
    
    let toBeRemovedArticles = [];
    
//    let searchArticles = [
//        {
//            word : "Apple",
//            articles: [
//                {
//                    author : "Nick Douglas",
//                    title : "Put Tape on Your Apple TV Remote",
//                    description : "Apple, the good design company, bundles its Apple TV with a pitch-black and horizontally symmetrical remote. This means that if you can find the remote in the dark, you’re likely to grab it by the wrong end, grasping the touchpad and clicking the wrong button…",
//                    url : "https://lifehacker.com/put-tape-on-your-apple-tv-remote-1825319793",
//                    urlToImage : "https://i.kinja-img.com/gawker-media/image/upload/s--8mhfTeH6--/c_fill,fl_progressive,g_center,h_900,q_80,w_1600/ci2n5xlowlplsjmeg9v0.jpg",
//                    publishedAt : "2018-04-19T19:00:00Z"
//                },
//                {
//                    author : "Jacob Kleinman",
//                    title : "Here's What Apple Knows About You (Spoiler: Not Much)",
//                    description : "Apple likes to say that it cares more about your privacy than other tech giants like Facebook, Google, and Amazon, but does that claim actually hold up? It turns out the answer is a pretty resounding yes. Read more...",
//                    url : "https://lifehacker.com/heres-what-apple-knows-about-you-spoiler-not-much-1825779350",
//                    urlToImage : "https://i.kinja-img.com/gawker-media/image/upload/s--5gWPbN-1--/c_fill,fl_progressive,g_center,h_900,q_80,w_1600/kgukj6yykudzspxsy71e.jpg",
//                    publishedAt : "2018-05-04T19:45:00Z"
//                },
//                {
//                    author : "David Murphy",
//                    title : "If You're an iPhone-loving Windows User, You'll Want iTunes From the Windows Store",
//                    description : "Before we begin, Windows users, let’s start the affirmation: We use Windows. We all agree that iTunes has a terrible design. We will throw a party if Apple ever modernizes its app, but we hate that iTunes is the best possible option for synchronizing content …",
//                    url : "https://lifehacker.com/if-youre-an-iphone-loving-windows-user-youll-want-itun-1825606649",
//                    urlToImage : "https://i.kinja-img.com/gawker-media/image/upload/s--yVuMwpgK--/c_fill,fl_progressive,g_center,h_900,q_80,w_1600/am5cj6t7hc7zt2pwhlk4.jpg",
//                    publishedAt : "2018-04-27T19:15:00Z"
//                },
//                {
//                    author : "David Murphy",
//                    title : "How to Charge Your iPhone Faster",
//                    description : "Today’s “I’ll believe it when I see it” iPhone rumor-mongering concerns not the iPhone itself, but its charger—the most important accessory you’ll find in its meticulously crafted packaging. As the speculation goes, Apple is allegedly considering bumping up t…",
//                    url : "https://lifehacker.com/how-to-charge-your-iphone-faster-1825757543",
//                    urlToImage : "https://i.kinja-img.com/gawker-media/image/upload/s--QZDtozA1--/c_fill,fl_progressive,g_center,h_900,q_80,w_1600/lnexskynqjwsah3pw65x.jpg",
//                    publishedAt : "2018-05-04T13:45:00Z"
//                }
//            ]
//        },
//        {
//            word : "Samsung",
//            articles : [
//                {
//                    author : "Jacob Kleinman",
//                    title : "Don't Ditch Your Galaxy S7, Give It an (Android 8.0) Oreo",
//                    description : "Android: It’s been a little over two years since the launch of Samsung’s Galaxy S7 and Galaxy S7 Edge. If you’re still holding on to yours, you’re probably itching to upgrade to a new device—especially since Samsung still hasn’t updated either S7 smartphone t…",
//                    url : "https://lifehacker.com/dont-ditch-your-galaxy-s7-give-it-an-android-8-0-ore-1825656496",
//                    urlToImage : "https://i.kinja-img.com/gawker-media/image/upload/s--WoNWaeeZ--/c_fill,fl_progressive,g_center,h_900,q_80,w_1600/tr645nut5ledrmjhwh0c.jpg",
//                    publishedAt : "2018-04-30T19:15:00Z"
//                },
//                {
//                    author : "David Murphy",
//                    title : "What to Do if You Accidentally Delete Photos From Your Phone",
//                    description : "It happens. You’re absentmindedly browsing through your photos while enjoying a lovely view or a summery drink. You tap around—and then realize you’ve accidentally deleted a ton of photos on your device. You panic. Have you just lost your precious memories fo…",
//                    url : "https://lifehacker.com/what-to-do-if-you-accidentally-delete-photos-from-your-1825571018",
//                    urlToImage : "https://i.kinja-img.com/gawker-media/image/upload/s--CxNn7AQB--/c_fill,fl_progressive,g_center,h_900,q_80,w_1600/rsv8rxxqu16tpvov6ken.jpg",
//                    publishedAt : "2018-04-26T18:00:00Z"
//                }
//            ]
//        }
//    ];
    
    //set formated text to editor
    function setup(){
        if(note.content !== ""){
            quill.setContents(JSON.parse(note.content));
        }
    }

    $tabButtonItem.first().addClass(activeClass);
    $tabContents.not(':first').hide();

    $tabButtonItem.find('a').on('click', function(e) {
        var target = $(this).attr('href');

        $tabButtonItem.removeClass(activeClass);
        $(this).parent().addClass(activeClass);
        $tabSelect.val(target);
        $tabContents.hide();
        $(target).show();
        e.preventDefault();
    });

    $tabSelect.on('change', function() {
        var target = $(this).val(),
            targetSelectNum = $(this).prop('selectedIndex');

        $tabButtonItem.removeClass(activeClass);
        $tabButtonItem.eq(targetSelectNum).addClass(activeClass);
        $tabContents.hide();
        $(target).show();
    });

    searchTab.on('click', '.aBtn', function(e){
        let articleUrl = $(this).parent().find('a').attr('href');
        let article = findArticleFromAll(articleUrl) === null ? findArticleFromSaved(articleUrl) : findArticleFromAll(articleUrl);
        if(containsArticle(article, toBeRemovedArticles)){
            //remove article from toBeRemovedArticles array
            let index = toBeRemovedArticles.indexOf(article);
            toBeRemovedArticles.splice(index, 1);
            $(this).parent().remove();
            addArticle(article, savedArticlesContainer, "-");
        }else if (!containsArticle(article, savedArticles)) {
            //adds article to toBeAddedArticles array
            toBeAddedArticles.push(article);
            console.log("toBeAddedArticles");
            console.log(toBeAddedArticles);
            $(this).parent().remove();
            addArticle(article, savedArticlesContainer, "-");
        }

    });

    savedTab.on('click', '.aBtn', function(){
        let articleUrl = $(this).parent().find('a').attr('href');
        let article = findArticleFromAll(articleUrl) === null ? findArticleFromSaved(articleUrl) : findArticleFromAll(articleUrl);
        if(containsArticle(article, savedArticles)) {
            //adds article to toBeAddedArticles array
            toBeRemovedArticles.push(article);
            console.log("toBeRemovedArticles");
            console.log(toBeRemovedArticles);
        }else{
            //remove article from toBeAddedArticles array
            let index = toBeAddedArticles.indexOf(article);
            toBeAddedArticles.splice(index, 1);
            console.log("toBeAddedArticles");
            console.log(toBeAddedArticles);
        }
        $(this).parent().remove();
        addArticle(article, searchArticlesContainer, "+");
    });

    updateBtn.on("click", function(){
        updateNoteInfo();
        let init = (m, toBeSentObj) => {
            let obj = {
                method: m,
                body: JSON.stringify(toBeSentObj),
                headers: {
                    "Content-type": "application/json; charset=UTF-8"
                }
            };
            return obj;
        };
        fetch(url, init("PUT", note))
            .then(response => {
                if(toBeAddedArticles.length !== 0){
                    fetch(url + '/articles', init("POST", toBeAddedArticles));
                }
            })
            .then(response => {
                if(toBeRemovedArticles.length !== 0){
                    fetch(url + '/articles/remove', init("POST", toBeRemovedArticles))
                        .then(response => console.log(response));
                }
            })   
            .then(response => showNotification());
    });

    saveBtn.on("click", function(){
        updateNoteInfo();
        if(note.name === "") note.name = "Untitled document";
        
        let init = (m, toBeSentObj) => {
            let obj = {
                method: m,
                body: JSON.stringify(toBeSentObj),
                headers: {
                    "Content-type": "application/json; charset=UTF-8"
                }
            };
            return obj;
        };
        let aUrl = url.substring(0, url.length-8);
        fetch(url, init("POST", note))
            .then(response => response.text())
            .then(str => {
                let redirectUrl = curHref.substring(0, curHref.length-8);
                if(toBeAddedArticles.length !== 0){
                    fetch(aUrl+"/"+str+"/articles", init("POST", toBeAddedArticles))
                        .then(response => {
                            window.location.replace(redirectUrl);
                        });
                }
                window.location.replace(redirectUrl);
            });
    });

    deleteBtn.on("click", function(){
        fetch(url, {
            method : "DELETE"
        })
            .then(response => {
                let redirectUrl = curHref.substring(0, curHref.lastIndexOf('/'));
                window.location.replace(redirectUrl);
            });
    });

    searchBtn.on('click', function(){
        resetSearchArticles();
        $('#loader').css('display', 'block');
        setTimeout(function(){
            loadArticles(quill.getText());
        }, 1000);
    });
    
    keywordsContainer.on('change', function(){
        let keyword = $(this).val(); 
        if(keyword === 'all'){
           resetSearchArticles();
           setAllArticles();
        }else{
            searchArticlesContainer[0].innerHTML = "";
            showArticlesOfKeyword(findArticlesFromKeyword(keyword));
        }
    });

    function showNotification(){
        $("#noti").css('visibility', 'visible');
        $("#noti").fadeOut(4000, function(){
            $(this).css('display', 'block');
            $(this).css('visibility', 'hidden');
        });
    }
    
    //reset articles div and select input
    function resetSearchArticles(){
        searchArticlesContainer[0].innerHTML = "";
        keywordsContainer[0].innerHTML = "<option value='all'>ALL</option>";
    }
    
    function addArticle(article, div, sign){
        let a = `
            <div class="article">
                <p><b><a href="${article.url}" target="_blank">${article.title}</a></b></p>
                <p>${article.description}</p>
                <button class="aBtn">${sign}</button>
            </div>
        `;
        div[0].innerHTML+=a;
    }
    
    function addKeyword(word){
        let opt = `<option value="${word}">${word.toUpperCase()}</option>`;
        keywordsContainer[0].innerHTML+=opt;
    }
    
    function updateNoteInfo(){
        note.name = titleInput.val();
        note.content = JSON.stringify(quill.getContents());
    }

//ARTICLES!!!

    function loadArticles(text) {
        fetch(window.location.origin + '/news', {
          'method': 'POST',
          'body': text,
          'headers': new Headers({'Content-Type': 'text/plain'})
        })
        .then(response => response.json())
        .then(function(articleJson) {
            $('#loader').css('display', 'none');
            articlesAll = articleJson;
            setAllArticles();
        });
//            articlesAll = searchArticles;
//            setAllArticles();
      }

    //take all articles from array to display
    function setAllArticles() {
        console.log("articlesAll: ");
        console.log(articlesAll);

        for (let keyword of articlesAll) {
            addKeyword(keyword.word);
            for (let article of keyword.articles) {
                if(!containsArticle(article, savedArticles) || containsArticle(article, toBeRemovedArticles)){
                    addArticle(article ,searchArticlesContainer, "+");
                }
            }
        }
    }
    
    //find article from articlesAll array
    function findArticleFromAll(articleUrl) {
        for (let keyword of articlesAll) {
            for (let article of keyword.articles) {
                if (article.url === articleUrl) {
                  return article;
                }
            }
        }
        return null;
    }
    
    //find article from savedArticles array
    function findArticleFromSaved(articleUrl){
        for(let article of savedArticles){
            if(article.url === articleUrl){
                return article;
            }
        }
        return null;
    }

    //checks form savedArticles array if it contains article already
    function containsArticle(article, articlesArr) {
        for (let a of articlesArr) {
            if (a.url === article.url) {
                console.log(a);
                return true;
            }
        }
        return false;
    }
    
    function findArticlesFromKeyword(word){
        for(let keyword of articlesAll){
            if(keyword.word === word){
                return keyword.articles;
            }
        }
        return [];
    }
    
    function showArticlesOfKeyword(articlesArr){
        for(let article of articlesArr){
            if(!containsArticle(article, savedArticles) || containsArticle(article, toBeRemovedArticles)){
                addArticle(article ,searchArticlesContainer, "+");
            }
        }
    }
    
});
