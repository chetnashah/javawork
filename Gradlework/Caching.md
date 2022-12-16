

## Build cache

for actions/tasks


## Configuration cache

for build config

## 

## Incremental build

https://blog.gradle.org/introducing-incremental-build-support

Text net to tasks like `UPTODATE` is called outcome label

if no outcome label, then task ran.

`NO-SOURCE` means no input was found

Input can be file (code to compile) as well as configuration options (e.g. compile flags).


Incremental builds are not supported in **branch switching**


You can see all kinds of gradle caches at `~/.gradle/caches`

Incremental build uses `build/` folder caches.

First lookup happens in `build cache`. Then `local cache` is looked up, then `remote cache`.

## How to inspect cache key that is created?

`-Dorg.c


## How to create `local` build-cache?

```groovy
buildCache {
    local {
        directory = File(rootDir,"my-build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}
```

