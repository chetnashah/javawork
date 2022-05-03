

## Add dependency

```groovy
dependencies{
    testImplementation('junit:junit:4.13.2')
    testImplementation 'org.jetbrains.kotlin:kotlin-test'
}
```

## Add test task to `build.gradle`

```groovy
test {
    useJUnitPlatform()
}
```

## Add a sample test in `src/test/kotlin/SampleTest.kt`

```kt
import kotlin.test.Test
import kotlin.test.assertEquals

class SampleTest {
    @Test
    fun testSum(){
        val expct = 34
        assertEquals(expct, 34)
        
    }
}
```