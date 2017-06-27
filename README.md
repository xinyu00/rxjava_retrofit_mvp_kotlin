# MVP

#### 这是一个基于kotlin语言的android mvp快速开发框架
##### 框架使用技术: rxjava、retrofit、dagger2、greendao、fresco、Material Design风格、6.0权限
##### author: tianchen
##### email: 963181974@qq.com


kotlin移植方式
1. 在项目的build.gradle中加入 
```
buildscript {
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:${KOTLIN_VERSION}"
    }
}
```
classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:${KOTLIN_VERSION}"
2. 在app的build.gradle中加入
```
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-kapt'
apply plugin: 'kotlin-android-extensions'
```
3. 在app的build.gradle的dependencies中加入
```
compile "org.jetbrains.kotlin:kotlin-stdlib:${KOTLIN_VERSION}"
```
