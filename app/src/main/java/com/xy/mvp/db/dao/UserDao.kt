package com.xy.mvp.db.dao


import com.xy.mvp.entity.UserEntity

interface UserDao {

    fun addUser(user: UserEntity)        // 添加User

    fun getUser(userId: Int): UserEntity?        // 根据userId查询User

    fun update(user: UserEntity)            // 更新User

    fun delete(user: UserEntity)            // 删除User

    fun findUser(userName: String): UserEntity?    // 根据用户名查询User

}
