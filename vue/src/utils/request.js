import axios from 'axios'
import router from "@/router";

const request = axios.create({
    baseURL: "/api",
    timeout: 5000
})
//设置白名单，如果请求在白名单里面，将不会拦截校验权限
const whiteUrls = ["/admin/login", "/admin/register", "/files/**"];
// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(
    config => {
        config.headers['Content-Type'] = 'application/json;charset=utf-8';
        //取出session Storage里面缓存的用户信息
        let adminJson = sessionStorage.getItem("admin");
        // console.log(adminJson);
        if (!whiteUrls.includes(config.url)) {      //校验请求白名单
            if (!adminJson) {     //如果数据池为空
                router.push("/login");
            } else {
                let admin = JSON.parse(adminJson);
                // console.log(admin);
                config.headers['token'] = admin.token;
            }
        }
        return config
    },
    error => {
        return Promise.reject(error)
    });

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        //验证token
        if(res.code==='401') {
            console.log("token过期，重新登陆");
            router.push("/login");
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request

