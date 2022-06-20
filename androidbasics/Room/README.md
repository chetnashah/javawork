

## Dependencies

1. room runtime - runtime stuff for room
2. room compiler - annotations and other compile time checking stuff.

## Gradle plugin needed

`apply kotlin-kapt`

## Coroutine support for room


## Common steps

1. Define Database (abstract class extending RoomDatabase, exposing abstract `getXYZDao()` methods).
2. Define Entity/Table (data class (POJO) with annotations) - note it can be treated as domain models or domain model class can be separate
3. Define Dao (Interface with suspend functions to read/write data i.e CRUD like methods) - can refer models in return types of query and in argument type for inserts. - methods are annotated with @INSERT/@UPDATE/@DELETE/@QUERY
4. Define repository (public interface to manipulation collections) 
5. Define viewModel (state/data holder for UI)

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

### Another sample Database class implementation

```kt
abstract class AppDatabase: RoomDatabase() {

    abstract fun scheduleDao(): ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .createFromAsset("database/bus_schedule.db")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
```