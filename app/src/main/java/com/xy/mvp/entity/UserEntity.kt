package com.xy.mvp.entity

class UserEntity {
    var userId: Int = 0     // 用户id
    var userName: String? = null // 用户名
    var userPwd: String? = null // 用户密码
    var userRoleId: Int = 0 // 用户角色	// 0 手机号 1 qq 2 微信 3 微博
    var userState: Int = 0  // 用户状态	0 已登录 1 未登录 2 已注册 3 未注册
    var regEmail: String? = null // 注册邮箱
    var regPhone: String? = null // 注册手机
    var regDate: String? = null // 注册时间
    var last_login_time: String? = null // 最后登录时间
    var last_login_ip: String? = null    // 最后登录IP 192.168.1.1
    var update_time: String? = null    // 更新时间 2017-01-01 01:01:01
}
