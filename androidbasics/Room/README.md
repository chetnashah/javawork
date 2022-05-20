

## Tables

Tables are specified via `@Entity` annotation, along with a `@PrimaryKey`

```kt
@Entity(tableName = "articles")
data class Article(
    @PrimaryKey
    var id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)
```

## Specifying Database as an abstract class

```kotlin
@Database(entities = [Article::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    // usually database wiill have this getXYZDao()
    abstract fun getArticleDao(): ArticleDao
}
```