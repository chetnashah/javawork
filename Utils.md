

## Locale

A `Locale` object represents a specific geographical, political, or cultural region. It represents a language/country/variant combination. Locales are used to alter the presentation of information such as numbers or dates to suit the conventions in the region. Date and time are locale-sensitive, because they are written differently in different parts of the world. You will use the method `Locale.getDefault()` to retrieve the locale information set on the user's device and pass it into the SimpleDateFormat constructor.


## SimpleDateFormat

`SimpleDateFormat` is a concrete class for formatting and parsing dates in a locale-sensitive manner. 

It allows for formatting (date -> text), parsing (text -> date), and normalization.


```kt
val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
```


## Calendar

Use `Calendar` only as a calculator which, when given `Date` and `TimeZone` objects, will do calculations for you. Avoid its use for property typing in an application.

## Date

`Date` is best for storing a date object. It is the persisted one, the Serialized one.
`Calendar` is best for manipulating Dates.
`Date`s should be used as immutable points in time; `Calendar`s are mutable, and can be passed around and modified
`Date` does not have a notion of timezone.

## Date vs Calendar

`Date` and `Calendar` are really the same fundamental concept (both represent an instant in time and are wrappers around an underlying long value - time since jan 1 1970).
`Date`s should be used as immutable points in time; `Calendar`s are mutable, and can be passed around and modified

## Java Time API (jdk 8 onwards)

### java.time.LocalDate

`java.time.LocalDate` : represents a year-month-day in the ISO calendar and is useful for representing a date without a time. It can be used to represent a date only information such as a birth date or wedding date.

E.g.
```java
System.out.println(LocalDate.now());// 2022-07-05
```

### java.time.LocalTime

`java.time.LocalTime` : deals in time only. It is useful for representing human-based time of day, such as movie times, or the opening and closing times of the local library.

### java.time.LocalDateTime

`java.time.LocalDateTime` : handles both date and time, without a time zone. It is a combination of LocalDate with LocalTime.

### java.time.ZonedDateTime

`java.time.ZonedDateTime` : combines the LocalDateTime class with the zone information given in ZoneId class. It represent a complete date time stamp along with timezone information.

### java.time.OffsetTime

`java.time.OffsetTime` : handles time with a corresponding time zone offset from Greenwich/UTC, without a time zone ID.

### java.time.Clock

java.time.Clock : provides access to the current instant, date and time in any given time-zone. Although the use of the Clock class is optional, this feature allows us to test your code for other time zones, or by using a fixed clock, where time does not change.

### java.time.Instant

`java.time.Instant` : represents the start of a nanosecond on the timeline (since EPOCH) and useful for generating a timestamp to represent machine time. An instant that occurs before the epoch has a negative value, and an instant that occurs after the epoch has a positive value.

### java.time.Duration

`java.time.Duration` : Differnce between two instants and measured in seconds or nanoseconds and does not use date-based 
constructs such as years, months, and days, though the class provides methods that convert to days, hours, and minutes.

### java.time.Period

`java.time.Period` : To define the difference between dates in date-based values (years, months, days).
