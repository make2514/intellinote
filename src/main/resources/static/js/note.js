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
    
    function updateNoteInfo(){
        note.name = titleInput.val();
        note.path = contentInput.val();
    }

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
        fetch(url, init("POST", note))
            .then(response => response.text())
            .then(str => {
                if(toBeAddedArticles.length !== 0){
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

