let articlesAll = [];
let articlesSaved = [];

function loadArticles(text) {
  fetch(window.location.origin + '/news', {
    'method': 'POST',
    'body': text,
    'headers': new Headers({'Content-Type': 'text/plain'})
  })
  .then(response => response.json())
  .then(function(articleJson) {
    articlesAll = articleJson;
    setAllArticles();
  });
}

//sets Atricles to unordered list into element with class js-article-container-all
function setAllArticles() {
  console.log(articlesAll);
  let articleContainer = document.querySelector('.js-article-container-all');
  articleContainer.innerHTML = '';

  for (let keyword of articlesAll) {
    let listElement = document.createElement('li');
    let list = document.createElement('ul');
    listElement.innerHTML = `<p>${keyword.word}</p>`;
    listElement.appendChild(list);

    for (let article of keyword.articles) {
      list.innerHTML +=
      `<li class="article">
        <a href="${article.url}">
          <p class="article-header">${article.title}</p>
        </a>
        <button class="js-add-article">+</button>
      </li>`;
    }
    articleContainer.appendChild(listElement);
  }

  $('.js-add-article').click(function() {
    let articleUrl = $(this).parent().find('a').attr('href');
    let article = findArticleFromAll(articleUrl);
    if (!savedContainsAtricle(article)) {
      //adds article to note.js toBeAddedArticles array
      toBeAddedArticles.push(article);
    }
    console.log(articlesSaved);
  });
}

function seticles() {
  let savedContainer = document.querySelector('.js-article-container-saved');
  savedContainer.innerHTML = '';

  for (let a of articlesSaved) {
    savedContainer.innerHTML +=
    `<li class="article">
      <a href="${a.url}">
        <p class="article-header">${a.title}</p>
      </a>
      <button class="js-add-article">+</button>
    </li>`;
  }
}

function findArticleFromAll(articleUrl) {
  for (let keyword of articlesAll) {
    for (let article of keyword.articles) {
      if (article.url === articleUrl) {
        return article;
      }
    }
  }
}



//checks form note.js savedArticles array if it contains article already
function savedContainsAtricle(article) {
  for (let a of savedArticles) {
    if (a === article) {
      return true;
    }
  }
  return false;
}
