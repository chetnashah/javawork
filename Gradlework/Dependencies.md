

## Where are they present locally?


`~/.gradle/caches/modules-2/...`

In the output of:
`./gradlew :projName:dependencies`

`n` - `n` stands for not resolved - bucket configurations, not a resolved configuration.

no `(n)` = resolved configuration.

bucket configurations - usually defined by project developers. they can have ranges

resolved configurations - used by system runtime/compile/classpath. these will have final selected deps/jars/files.