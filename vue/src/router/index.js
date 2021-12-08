import {createRouter, createWebHistory} from 'vue-router'
import Layout from "@/layout/Layout";
import request from "@/utils/request";

const routes = [
    {
        path: '/',
        name: 'Layout',
        component: Layout,
        redirect: "/index",
        children: [
            {
                path: 'index',
                name: 'Index',
                component: () => import("@/views/Index"),
                meta: {
                    title: '主页',
                    keepAlive: true, // 需要被缓存
                }
            },
            {
                path: 'book',
                name: 'Book',
                component: () => import("@/views/Book"),
                meta: {
                    title: '书籍管理',
                    keepAlive: true, // 需要被缓存
                }
            },
            {
                path: 'category',
                name: 'Category',
                component: () => import("@/views/Category"),
                meta: {
                    title: '书籍分类',
                    keepAlive: true, // 需要被缓存
                }
            },
            {
                path: 'order',
                name: 'Order',
                component: () => import("@/views/Order"),
                meta: {
                    title: '订单管理',
                    keepAlive: true, // 需要被缓存
                }
            },
            {
                path: 'user',
                name: 'User',
                component: () => import("@/views/User"),
                meta: {
                    title: '用户管理',
                    keepAlive: true, // 需要被缓存
                }
            },
            {
                path: 'message',
                name: 'Message',
                component: () => import("@/views/Message"),
                meta: {
                    title: '用户留言',
                    keepAlive: true, // 需要被缓存
                }
            },
            {
                path: 'admin',
                name: 'Admin',
                component: () => import("@/views/Admin"),
                meta: {
                    role: 1,
                    title: '管理员管理',
                    keepAlive: true, // 需要被缓存
                }
            },
            {
                path: 'person',
                name: 'Person',
                component: () => import("@/views/Person"),
                meta: {
                    title: '个人信息',
                    keepAlive: true, // 需要被缓存
                }
            },
            {
                path: 'about',
                name: 'About',
                component: () => import("@/views/About"),
                meta: {
                    title: '关于',
                    keepAlive: true, // 需要被缓存
                }
            },
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import("@/views/Login"),
        meta: {
            title: '登录',
            keepAlive: true, // 需要被缓存
        }
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import("@/views/Register"),
        meta: {
            title: '注册',
            keepAlive: true, // 需要被缓存
        }
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

// 限制某些页面禁止未登录访问
//不在白名单里面的路径页面将会校验token
const whitePath = ['/login', '/register'];

router.beforeEach((to, from, next) => {
    if (!whitePath.includes(to.path)) {
        // 判断sessionStorage是否保存了用户信息
        let adminStr = sessionStorage.getItem("admin");
        let admin = JSON.parse(adminStr);
        //验证admin中的数据
        if (admin == null || !admin.token) {
            // 跳转到登录页面
            next({path: "/login"});
        } else {
            // console.log(admin.token);
            //通过每个请求所附带的token来进行admin的信息的获取
            request.get("/admin/getAdmin").then(res => {
                if (res.data == null) {
                    next({path: "/login"});
                    console.log('11111');
                } else {
                    sessionStorage.setItem("admin", JSON.stringify(res.data));
                    if(to.path=="/admin" && (res.data.role!=to.meta.role)) {
                        next({path: "/index"});
                    }
                    else next();
                }
            })
        }
    } else {
        next();
    }
})

export default router
