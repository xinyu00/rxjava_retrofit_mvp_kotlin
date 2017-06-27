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