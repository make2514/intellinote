let articlesAll;
let articlesSaved;

function loadArticles(text) {
  return fetch('http://localhost:8080/news', {
    'method': 'POST',
    'body': text,
    'headers': new Headers({'Content-Type': 'text/plain'})
  })
  .then(response => response.json())
  .then(function(articleJson) {
    articlesAll = articleJson;
    setArticles();
  });
}

function setArticles() {
  console.log(articlesAll);
  let articleContainer = document.querySelector('.article-container-all');
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
        <button>+</button>
      </li>`;
    }
    articleContainer.appendChild(listElement);
  }
}
