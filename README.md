# dresscode 👔 
[![](https://jitpack.io/v/Daio-io/dresscode.svg)](https://jitpack.io/#Daio-io/dresscode)

Tiny lightweight Kotlin Android library to change theme at runtime. 

- Very simple to use. 
- DressCode uses only extension functions to enable runtime theme changes avoiding the need to create some kind of `BaseThemeActivity` class.
- Declare your Themes as normal in `styles.xml`
- Automatically updates activity themes.
- Remembers the current theme between app launches.

## Add DressCode

```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

`implementation 'com.github.Daio-io:dresscode:{latest-release}'`

## Setting your App DressCode

In your Android Application class `onCreate` call the library function to set your dress codes 

```kotlin
declareDressCode(this, DressCode("themeone", R.style.ThemeOne),
                DressCode("themetwo", R.style.ThemeTwo),
                DressCode("themethree", R.style.ThemeThree))
```

Then from your Activities simply call `matchDressCode` before `setContentView`.

```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        matchDressCode() // Call me first otherwise who knows that 
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
```
DressCode will automatically manage applying the new theme when it has been changed. So a simple call from within an Activity to:

`dressCodeName = "themetwo"`

Will just apply the new theme to all Activities that call `matchDressCode()`.

Check out the sample app in the project for a simple implementation.

## Contributing Info 
Coming soon


