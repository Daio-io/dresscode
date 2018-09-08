# dresscode 👔 
[![](https://jitpack.io/v/Daio-io/dresscode.svg)](https://jitpack.io/#Daio-io/dresscode) [![codebeat badge](https://codebeat.co/badges/c4f32440-60e4-4878-820b-b258c1cb8fc8)](https://codebeat.co/projects/github-com-daio-io-dresscode-master)

Tiny lightweight Kotlin Android library to change theme at runtime. 

- Tiny 🔍
- Very simple to use. 
- DressCode uses only extension functions to enable runtime theme changes avoiding the need to create some kind of `BaseThemeActivity` class.
- Declare your Themes as normal in `styles.xml`
- Automatically updates activity themes.
- Remembers the current theme between app launches.

#### Used By:
<a href ="https://play.google.com/store/apps/details?id=studio.goodegg.capsule">
  <img width="70" src="https://lh3.googleusercontent.com/YjY4BpK9K9M_GzdJE-16MftRKZMbp51yHTSXVjMJ1pT2tqlnNE6ype2yAG7VRrOFgnE=s360">
</a>

Make a pull request if you want your app in this section

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
declareDressCode(DressCode("themeone", R.style.ThemeOne),
                DressCode("themetwo", R.style.ThemeTwo),
                DressCode("themethree", R.style.ThemeThree))
```

Then from your Activities simply call `matchDressCode` before `setContentView`.

```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        matchDressCode() // Call me first or someone may come dressed as a 🤡 
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
```
DressCode will automatically manage applying the new theme when it has been changed. So a simple call from within an Activity to:

`dressCodeStyleId = R.style.ThemeTwo`

Will just apply the new theme to all Activities that call `matchDressCode()`.

Check out the sample app in the project for a simple implementation.

## Contributing Info 
Coming soon


