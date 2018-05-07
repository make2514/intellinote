/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

document.addEventListener("DOMContentLoaded", function (event) {
    const titleInput = $("#titleInput");
    const contentInput = $("#contentInput");
    const saveBtn = $("#saveBtn");
    const updateBtn = $("#updateBtn");
    const deleteBtn = $("#deleteBtn");
    
    const curHref = window.location.href;
    const url = window.location.origin + '/auth' + window.location.pathname;
    
//    console.log(curHref.substring(0, curHref.length-8));
//    console.log(titleInput);
//    console.log(contentInput);

    const savedTab = $('#tab01'),
          searchTab = $('#tab02'),
          $tabButtonItem = $('#tab-button li'),
          $tabSelect = $('#tab-select'),
          $tabContents = $('.tab-contents'),
          activeClass = 'is-active',
          articleDiv = $('.article'),
          articleBtn = $('.article button'),
          addBtn = $('.addBtn'),
          removeBtn = $('.removeBtn');
          
    let note = JSON.parse(localStorage.getItem('note'));
    console.log('note: ');
    console.log(note);
    
    let savedArticles = JSON.parse(localStorage.getItem('savedArticles'));
    console.log('articles: ');
    console.log(savedArticles);

    let toBeAddedArticles = [
        {
            name : "article1",
            link : "article1.com"
        }
//        {
//            name : "funny article 2",
//            link : "hihihi"
//        }
    ];
    
    let toBeRemovedArticles = [
//        {
//            name : "funny article 2",
//            link : "hihihi"
//        }
    ];

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
      $(this).parent().remove();
      addArticle(savedTab, "-");
    });

    savedTab.on('click', '.aBtn', function(){
      $(this).parent().remove();
      addArticle(searchTab, "+");
    });

    function addArticle(article, div, sign){
      let a = `
        <div class="article">
          <p><b><a href="${article.url}">${article.title}</a></b></p>
          <p>${article.description}</p>
          <button class="aBtn">${sign}</button>
        </div>
      `;
      div[0].innerHTML+=a;
    }
    
    function updateNoteInfo(){
        note.name = titleInput.val();
        note.path = contentInput.val();
    }

    updateBtn.on("click", function(){
        updateNoteInfo();
//        console.log(note);
//        console.log(url);
//        console.log(toBeAddedArticles);
//        console.log(JSON.stringify(toBeAddedArticles));
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
                    console.log(url + '/articles');
                    fetch(url + '/articles', init("POST", toBeAddedArticles));
                }
            })
            .then(response => {
                if(toBeRemovedArticles.length !== 0){
                    console.log(url + '/articles/remove');
                    fetch(url + '/articles/remove', init("POST", toBeRemovedArticles))
                        .then(response => console.log(response));
                }
            });
    });
    
    saveBtn.on("click", function(){
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
        let aUrl = url.substring(0, url.length-8);
        console.log(aUrl);
        fetch(url, init("POST", note))
            .then(response => response.text())
            .then(str => {
                if(toBeAddedArticles.length !== 0){
                    console.log(aUrl+"/"+str+"/articles");
                    fetch(aUrl+"/"+str+"/articles", init("POST", toBeAddedArticles))
                        .then(response => {
                            let redirectUrl = curHref.substring(0, curHref.length-8);
                            window.location.replace(redirectUrl);
                        });
                }
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
});

