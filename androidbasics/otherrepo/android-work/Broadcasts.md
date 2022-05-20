
### INtent Filters:

When the system receives an implicit intent to start an activity, it searches for the best activity for the intent by comparing it to intent filters based on three aspects:

1. Action.
2. Data (both URI and data type).
3. Category.

#### Action test

To pass this filter, the action specified in the Intent must match one of the actions listed in the filter.

If the filter does not list any actions, there is nothing for an intent to match, so all intents fail the test. 

However, if an Intent does not specify an action, it passes the test as long as the filter contains at least one action.

#### Category test

For an intent to pass the category test, every category in the Intent must match a category in the filter. 
The reverse is not necessary—the intent filter may declare more categories than are specified in the Intent and the Intent still passes. 
Therefore, an intent with no categories always passes this test, regardless of what categories are declared in the filter.

### INtent matching

Intents are matched against intent filters not only to discover a target component to activate, but also to discover something about the set of components on the device. For example, the Home app populates the app launcher by finding all the activities with intent filters that specify the ACTION_MAIN action and CATEGORY_LAUNCHER category. A match is only successful if the actions and categories in the Intent match against the filter, as described in the documentation for the IntentFilter class.

Your application can use intent matching in a manner similar to what the Home app does. The PackageManager has a set of query...() methods that return all components that can accept a particular intent and a similar series of resolve...() methods that determine the best component to respond to an intent. For example, queryIntentActivities() returns a list of all activities that can perform the intent passed as an argument, and queryIntentServices() returns a similar list of services. Neither method activates the components; they just list the ones that can respond. There's a similar method, queryBroadcastReceivers(), for broadcast receivers.

