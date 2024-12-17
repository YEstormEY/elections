## Bewijs mediumstake

### 9.2:
Design:
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-s-se-gd/semester-1/cooguukaacoo13/-/issues/40
Design voor de articlePage
```css
<style scoped>
.background {
  background-color: #24786E99;
  width: 100%;
  padding: 20px;
  min-height: 60vh;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
}

.container {
  max-width: 900px;
}

.card-img-top {
  height: 300px;
  object-fit: cover;
}

.card-body {
  padding: 30px;
}

.card-title {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 20px;
}

.card-text {
  font-size: 1.2rem;
  margin-bottom: 20px;
}

.article-content {
  font-size: 1rem;
  line-height: 1.6;
  color: #333;
}

.likes-section {
  display: flex;
  align-items: center;
  font-size: 1.1rem;
  color: #555;
}

.btn-like {
  padding: 8px 16px;
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  margin-right: 10px;
  display: flex;
  align-items: center;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-liked {
  background-color: #007bff;
  color: white;
}

.btn-unliked {
  background-color: #6c757d;
  color: white;
}

.btn-like i {
  margin-right: 5px;
}

.navigation-buttons {
  display: flex;
  justify-content: space-between;
}

@media (max-width: 768px) {
  .card-title {
    font-size: 1.5rem;
  }

  .card-text {
    font-size: 1rem;
  }

  .navigation-buttons {
    flex-direction: column;
    align-items: center;
  }
}

</style>
```

Frontend en backend:
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-s-se-gd/semester-1/cooguukaacoo13/-/issues/43
(is EPIC zie child issues)

Frontend code voor de articlePage
```html
<template>
  <link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
  />
  <div class="background">
    <div class="container mt-5">
      <!-- Article card -->
      <router-link to="/articlepage">
        <button class="btn btn-secondary btn-block mt-3">Back</button>
      </router-link>

      <div class="card shadow-lg">
        <!-- Image Section -->
        <img :src="article.imagePath" class="card-img-top" alt="Article Image">

        <!-- Content Section -->
        <div class="card-body">
          <h5 class="card-title">{{ article.title }}</h5>
          <p class="text-muted">{{ date }}</p>
          <p class="card-text">{{ article.summary }}</p>
          <div class="article-content" v-html="article.content"></div>

          <!-- Likes Section -->
          <div class="likes-section mt-3">
            <button
              v-if="authState"
              @click="likeArticle"
              :class="{'btn-liked': isLiked, 'btn-unliked': !isLiked}"
              class="btn btn-like"
            >
              <i class="fas fa-thumbs-up"></i>
            </button>
            <span class="likes-count">Likes: {{ likes }}</span>
          </div>
          <p class="likes-count" v-if="!authState">login to like an article.</p>

          <!-- Navigation Buttons -->
          <div class="navigation-buttons mt-4">
            <button @click="previousArticle" class="btn btn-outline-primary">Previous Article</button>
            <button @click="nextArticle" class="btn btn-outline-primary">Next Article</button>
          </div>
        </div>
      </div>
    </div>

    <div class="container mt-5">
      <Comments :articleId="articleId" />
    </div>
  </div>
  <div class="container-fluid mt-5">
    <h2 class="text-center">You may also like:</h2>
    <random-articles :articleId="articleId" />
  </div>
</template>
```

```javascript
async getArticle() {
  try {
    const article = await fetchArticleById(this.articleId); // Fetch article from service
    this.article = article;
    this.newArticleId = article.articleId; // Update navigation article ID
    this.date = new Date(article.created_at).toDateString(); // Format and store the date
  } catch (error) {
    console.log(error.message);
  }
}

// article service
/**
 * Function used to fetch an article by its id
 * @param articleId - id connected to a specific article
 */
export async function fetchArticleById(articleId) {
  try {
    const response = await fetch(`http://localhost:8080/article/get/${articleId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      console.error('Failed to fetch article');
      alert('Article not found');
      router.push('/articlepage');

      return;
    }

    return await response.json();
  } catch (error) {
    throw new Error(error.message);
  }
}
```

Dit is de backendCode die het correcte article returnt:
```java
    @GetMapping("/get/{articleId}")
public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long articleId) {
    Article article = articleService.getArticleById(articleId);

    // If the article doesn't exist, return a 400 Bad Request response
    if (article == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // Convert the article to an ArticleDTO
    ArticleDTO articleDTO = convertToDTO(article);
    return ResponseEntity.ok(articleDTO);
}

// Vervolgens word de getArticleById methode aangeroepen in de service met articleId als parameter

public Article getArticleById(Long articleId) {
    return articleRepository.findByarticleId(articleId);
}

// De repository zoekt naar het article met het juiste articleId

// Convert the article to an ArticleDTO

ArticleDTO articleDTO = convertToDTO(article);
    return ResponseEntity.ok(articleDTO);
//Dit article wordt gereturned naar de frontend als ArticleDTO(datatransferObject) en gedisplayed
```


### 9.3:
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-s-se-gd/semester-1/cooguukaacoo13/-/issues/56

Als bewijs de processVotes method die de juiste informatie uit de XML haalt. In dit geval moet hij het totaal aantal votes per partij uit de XML halen.
```java
    private void processVotes(XMLParser parser) throws XMLStreamException {
        while (parser.findBeginTag("Selection")) {
            String partyName = null;
            Integer votes = null; // Use Integer to check if votes were already processed

            // Find the party name in <RegisteredName> within <AffiliationIdentifier>
            if (parser.findBeginTag(AFFILIATION_IDENTIFIER)) {
                if (parser.findBeginTag(REGISTERED_NAME)) {
                    partyName = parser.getElementText();
                    parser.findAndAcceptEndTag(REGISTERED_NAME);
                }
                parser.findAndAcceptEndTag(AFFILIATION_IDENTIFIER);
            }

            // If party name is found, fetch the first <ValidVotes> immediately following
            if (partyName != null && !totalVotesMap.containsKey(partyName)) {
                if (parser.findBeginTag(VALID_VOTES)) {
                    try {
                        votes = Integer.parseInt(parser.getElementText());
                        LOG.info("ValidVotes for " + partyName + ": " + votes);
                    } catch (NumberFormatException e) {
                        LOG.warning("Invalid vote count for party: " + partyName);
                    }
                    parser.findAndAcceptEndTag(VALID_VOTES);
                }

                // Add the first <ValidVotes> for the party to the map
                if (votes != null) {
                    totalVotesMap.put(partyName, votes);
                    LOG.info("Votes aggregated for " + partyName + ": " + votes);
                }
            }

            // Skip remaining selections for this <AffiliationIdentifier>
            parser.findAndAcceptEndTag("Selection");
        }
    }
```

### 9.4:
gedetailleerde beschrijving van errors tijdens het inloggen voor de user en een password strength checker die zichtbaar is voor de user.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-s-se-gd/semester-1/cooguukaacoo13/-/issues/39

Ook hebben we een responsive design gemaakt voor de website. Bewijs:

```css
@media (max-width: 768px) {
.card-title {
font-size: 1.5rem;
}

.card-text {
font-size: 1rem;
}

.navigation-buttons {
flex-direction: column;
align-items: center;
}
```

### 9.5:
Wij hebben onze backlog opgedeeld in sprints. Deze hebben we vervolgens uitgewerkt met behulp van de issue board.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-s-se-gd/semester-1/cooguukaacoo13/-/boards/29239?milestone_title=Sprint%204


### 9.7:
Ik heb feedback die ik kreeg tijdens de product review verwerkt en een issue aangemaakt.
de feedback:
Het design gebruiken op alle pagina's zodat het een geheel wordt.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-s-se-gd/semester-1/cooguukaacoo13/-/issues/40


## verwerkte feedback bewijzen:
ontvangen feedback:
Het design gebruiken op alle pagina's zodat het een geheel wordt.

Deze feedback wordt met open armen ontvangen, ik heb de article page / admin page al overeenkomend gemaakt volgens het algemene design.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-s-se-gd/semester-1/cooguukaacoo13/-/issues/40
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-s-se-gd/semester-1/cooguukaacoo13/-/issues/43 (is EPIC zie child issues)

ontvangen feedback:
In scrum always try to have a working version every sprint.

Wij hebben deze keer vroeg voor de product review gemergd, waardoor er niet zomaar nog errors konden ontstaan in de werkende versie.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-s-se-gd/semester-1/cooguukaacoo13/-/merge_requests/18 
dit is de laatste merge request voor de product review. Hierna heb ik het veilig in main gezet zodat wij een werkende versie hadden voor de product review.