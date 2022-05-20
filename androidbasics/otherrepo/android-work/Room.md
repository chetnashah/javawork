preferred ORM for android.

**Note** - Even though most object-relational mapping libraries allow entity objects to reference each other, Room explicitly forbids this.

### Database

The class that's annotated with `@Database` should satisfy the following conditions:

1. Be an `abstract class` that `extends RoomDatabase`.
2. Include the list of entities associated with the database within the annotation.
3. Contain an `abstract method` that has `0 arguments` and `returns the class that is annotated with @Dao`.

Example Database:
```java
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    // usually databse class with have this getDao method
    public abstract UserDao userDao();
}

AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        AppDatabase.class, "database-name").build();
```


### Entity

Represents a `Table` within the database.

The class name that was annotated with `@Entity` is automatically
picked up as table name in database. If you want something else,
annotate it like following: `@Entity(tableName = "users")`.

If you want to give column name other than field name use 
`@ColumnInfo(name="first_name")`
Use `@Ignore` on fields you want to ignore.

Example Entity:
```java
@Entity
public class User {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;
}
```

Nested objects using `@Embedded`:
```java
public class Address {
    public String street;
    public String state;
    public String city;

    @ColumnInfo(name = "post_code") public int postCode;
}

@Entity
public class User {
    @PrimaryKey public int id;

    public String firstName;

    @Embedded public Address address;
}
```

### DAO

Contains Methods for accessing the databse.
`@Dao` annotated class must be an `Interface` mostly, or an `abstract class`.

Example DAO

The methods can be named anything you like, only annotations
are looked at.

1. `@Innsert`: needs a void return type.
2. `@Query`: Give a SQL query as an argument to the `@Query` annotation.
Return type is what you would need in your UI etc.


```java
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
           "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
```

### Acquiring the database

At runtime, you can acquire an instance of Database by calling `Room.databaseBuilder()` or `Room.inMemoryDatabaseBuilder()`.


### Relationships and foreign keys

#### Many to Many relationships

```java

@Entity
public class Playlist {
    @PrimaryKey public int id;

    public String name;
    public String description;
}

@Entity
public class Song {
    @PrimaryKey public int id;

    public String songName;
    public String artistName;
}

@Entity(tableName = "playlist_song_join",
        primaryKeys = { "playlistId", "songId" },
        foreignKeys = {
                @ForeignKey(entity = Playlist.class,
                            parentColumns = "id",
                            childColumns = "playlistId"),
                @ForeignKey(entity = Song.class,
                            parentColumns = "id",
                            childColumns = "songId")
                })
public class PlaylistSongJoin {
    public int playlistId;
    public int songId;
}
```

The corresponding DAO for many to many relationship as follows:
```java
@Dao
public interface PlaylistSongJoinDao {
    @Insert
    void insert(PlaylistSongJoin playlistSongJoin);

    @Query("SELECT * FROM playlist " +
           "INNER JOIN playlist_song_join " +
           "ON playlist.id=playlist_song_join.playlistId " +
           "WHERE playlist_song_join.songId=:songId")
    List<Playlist> getPlaylistsForSong(final int songId);

    @Query("SELECT * FROM song " +
           "INNER JOIN playlist_song_join " +
           "ON song.id=playlist_song_join.songId " +
           "WHERE playlist_song_join.playlistId=:playlistId")
    List<Song> getSongsForPlaylist(final int playlistId);
}
```

### Converters for custom types

TypeConverters are supposed to be annotated along with Database declaration
```java
@Database(entities = {ProductEntity.class, ProductFtsEntity.class, CommentEntity.class}, version = 2)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
```

Each converter method should receive 1 parameter and have non-void return type.

```java
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
```