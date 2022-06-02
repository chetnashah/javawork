
## Test android fragments in isolation

## Using `FragmentScenario`

Instrumentation test: Creates empty hosting activity and launches fragment within it.
```kt
@Test
fun testProfileFragment() {
    val userId = "test"
    val directions = MainFragmentDirections.showProfile(userId)

    val scenario: FragmentScenario<ProfileFragment> = launchFragmentInContainer(direction.arguments)

    scenario.onFragment {
        fragment -> 
        val args = ProfileFragmentArgs.fromBundle(fragment.arguments)
        // testing args
        assertThat(args.userId).isEqualTo(userId)
    }
}
```

## Testing fragments with navigation

https://youtu.be/2k8x8V77CrU?t=1100

