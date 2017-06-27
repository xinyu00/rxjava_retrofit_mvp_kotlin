# MVP

#### 这是一个基于kotlin语言的android mvp快速开发框架
##### 框架使用技术: rxjava、retrofit、dagger2、greendao、fresco、Material Design风格、6.0权限
##### author: tianchen
##### email: 963181974@qq.com


#### 一、kotlin移植方式
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

#### 二、JNI(cmake)的加入
1.创建cpp文件目录，用于编写cpp文件

2.创建CamkeLists.txt文件
```
# cmake最低版本号
cmake_minimum_required(VERSION 3.4.1)
# 生成库文件
add_library( # library名
             native-lib
             # SHARED表示为动态链接库
             SHARED
             # 设置编译目录
             src/main/cpp/native-lib.cpp )
# 添加NDK API
find_library( # 给NDK库定义引用名称
              log-lib
              # 指定NDK库的名称
              log )
# 将library相互关联
target_link_libraries( # 指定目标库
                       native-lib
                       # 关联库
                       ${log-lib} )
```
3.在app的build.gradle文件中配置cmake和so库加载方式
```
android {
  ...
  defaultConfig {
    ...
    //指定可选配置
    externalNativeBuild {
      cmake {
         abiFilters 'armeabi'   // 只编译armeabi的so库
        cppFlags "-std=c++11 -frtti -fexceptions"   //c++11标准
        
      }
    }
    ndk{
        abiFilters 'armeabi'    // 只加载armeabi的so库
    }
  }
  //加载CMakeLists.txt文件
  externalNativeBuild {
        cmake {
            path "CMakeLists.txt"   //cmake相对路径
        }
  }
   sourceSets {
          main {
              jniLibs.srcDirs = ['libs']    //配置加载jni路径为libs
          }
      }
}
```
4.java中加载so库
```
   System.loadLibrary("native-lib");
```