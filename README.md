### AgileToast


A simple and more powerful Toast. [![](https://jitpack.io/v/Doublemine/AgileToast.svg)](https://jitpack.io/#Doublemine/AgileToast)


![](art/normal.gif)
![](art/fill.gif)
![](art/corner.gif)
![](art/info.gif)
![](art/success.gif)
![](art/error.gif)



### import

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
dependencies {
	        compile 'com.github.Doublemine:AgileToast:1.0.2'
	}
```



### Usage


The base usage:

```java
   AgileToast.Companion.build(MainActivity.this)
            .text("Some message.")
            .show();
```

Advance usage:

```java
AgileToast.Companion.build(MainActivity.this)
            .type(ToastType.NORMAL)
            .text("AgileToast send msg...")
            .animation(AnimationType.DRAWER_BOTTOM)
            .duration(Duration.CUSTOM, 1000L)
            .style(ToastStyle.FILL)
            .gravity(Gravity.CENTER | Gravity.BOTTOM)
            .offsetY(200)
            .dismissCallback(MainActivity.this)
            .bgColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null))
            .show();
```

see the source code of demo to learn more advance usage.
