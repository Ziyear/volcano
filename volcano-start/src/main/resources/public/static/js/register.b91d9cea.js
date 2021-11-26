(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["register"],{"935e":function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("a-row",{staticStyle:{"min-height":"100vh"},attrs:{type:"flex",justify:"center",align:"middle"}},[r("a-col",{attrs:{span:"16"}},[e.registerError?r("div",[r("a-alert",{attrs:{message:"注册失败",description:e.registerError,type:"error"}})],1):e._e(),r("a-form-model",e._b({ref:"regForm",attrs:{model:e.model,rules:e.rules}},"a-form-model",e.layout,!1),[r("a-form-model-item",{attrs:{"has-feedback":"",prop:"username"}},[r("span",{attrs:{slot:"label"},slot:"label"},[e._v(" 用户名 "),r("a-tooltip",{attrs:{title:"用于登录，只允许英文字母和数字组合"}},[r("a-icon",{attrs:{type:"question-circle-o"}})],1)],1),r("a-input",{attrs:{type:"text",autocomplete:"off"},model:{value:e.model.username,callback:function(t){e.$set(e.model,"username",t)},expression:"model.username"}})],1),r("a-form-model-item",{attrs:{prop:"password",label:"密码"}},[r("a-input",{attrs:{type:e.passwordVisible?"text":"password",autocomplete:"off"},model:{value:e.model.password,callback:function(t){e.$set(e.model,"password",t)},expression:"model.password"}},[r("a-icon",{attrs:{slot:"addonAfter",type:e.passwordVisible?"eye-invisible":"eye"},on:{click:function(t){e.passwordVisible=!e.passwordVisible}},slot:"addonAfter"})],1)],1),r("a-form-model-item",{attrs:{prop:"matchingPassword",label:"确认密码"}},[r("a-input",{attrs:{type:e.matchingPasswordVisible?"text":"password",autocomplete:"off"},model:{value:e.model.matchingPassword,callback:function(t){e.$set(e.model,"matchingPassword",t)},expression:"model.matchingPassword"}},[r("a-icon",{attrs:{slot:"addonAfter",type:e.matchingPasswordVisible?"eye-invisible":"eye"},on:{click:function(t){e.matchingPasswordVisible=!e.matchingPasswordVisible}},slot:"addonAfter"})],1)],1),r("a-form-model-item",{attrs:{"has-feedback":"",prop:"name",label:"姓名"}},[r("a-input",{attrs:{type:"text",autocomplete:"off"},model:{value:e.model.name,callback:function(t){e.$set(e.model,"name",t)},expression:"model.name"}})],1),r("a-form-model-item",{attrs:{"has-feedback":"",prop:"email",label:"电子邮件"}},[r("a-input",{attrs:{type:"text",autocomplete:"off"},model:{value:e.model.email,callback:function(t){e.$set(e.model,"email",t)},expression:"model.email"}})],1),r("a-form-model-item",{attrs:{"has-feedback":"",prop:"mobile",label:"手机"}},[r("a-input",{attrs:{type:"text",autocomplete:"off"},model:{value:e.model.mobile,callback:function(t){e.$set(e.model,"mobile",t)},expression:"model.mobile"}},[r("a-select",{staticStyle:{width:"70px"},attrs:{slot:"addonBefore",defaultValue:"86"},slot:"addonBefore"},[r("a-select-option",{attrs:{value:"86"}},[e._v("+86")]),r("a-select-option",{attrs:{value:"1"}},[e._v("+1")])],1)],1)],1),r("a-form-model-item",e._b({attrs:{prop:"agreement"}},"a-form-model-item",e.tailFormItemLayout,!1),[r("a-checkbox",{attrs:{checked:e.model.agreement},model:{value:e.model.agreement,callback:function(t){e.$set(e.model,"agreement",t)},expression:"model.agreement"}},[e._v(" 我已经阅读了 "),r("a",{attrs:{href:""}},[e._v("用户注册协议")])])],1),r("a-form-model-item",e._b({},"a-form-model-item",e.tailFormItemLayout,!1),[r("a-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitForm("regForm")}}},[e._v("注册")]),r("a-button",{attrs:{type:"link"},on:{click:e.handleNavLogin}},[e._v("已经有账户，去登录")])],1)],1)],1)],1)},o=[],s=r("9962"),l=function(e,t,r){""===t&&r(),s["a"].checkUsername(t).then((function(e){e.data&&r(new Error("用户名已存在！")),r()})).catch((function(){r(new Error("服务器异常"))}))},i=function(e,t,r){""===t&&r(),s["a"].checkEmail(t).then((function(e){e.data&&r(new Error("电子邮件已存在！")),r()})).catch((function(){r(new Error("服务器异常"))}))},n=function(e,t,r){""===t&&r(),s["a"].checkMobile(t).then((function(e){e.data&&r(new Error("手机号码已存在！")),r()})).catch((function(){r(new Error("服务器异常"))}))},m=function(e,t){return function(r,a,o){""!==e.matchingPassword&&t.validateField("matchingPassword"),o()}},d=function(e){return function(t,r,a){""!==r&&r!==e.password?a(new Error("密码和确认密码不一致！")):a()}},u=function(e,t){return{username:[{required:!0,message:"用户名为必填项！",trigger:"blur"},{pattern:/^[a-zA-Z0-9_]{3,50}$/,message:"用户名仅能使用英文字母和数字以及下划线，长度在3-50个字符",trigger:"blur"},{asyncValidator:l,trigger:"blur"}],password:[{required:!0,message:"密码为必填项！",trigger:"blur"},{pattern:/^.*(?=.{8,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$/,message:"密码最少8位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符！",trigger:"blur"},{validator:m(e,t),trigger:"blur"}],matchingPassword:[{required:!0,message:"确认密码为必填项！",trigger:"blur"},{validator:d(e),trigger:"blur"}],name:[{required:!0,message:"姓名为必填项",trigger:"blur"},{pattern:/^([\u4e00-\u9fa5]{2,20}|[a-zA-Z.\s]{2,50})$/,message:"中文姓名长度为2-20个字符，不能有空格。英文姓名在2-50个字符，可以有空格和小数点",trigger:"blur"}],mobile:[{required:!0,message:"手机号为必填项！",trigger:"blur"},{pattern:/^1[3-9]\d{9}$/,message:"手机号不合法",trigger:"blur"},{asyncValidator:n,trigger:"blur"}],email:[{required:!0,message:"电子邮件为必填项！",trigger:"blur"},{pattern:/^\S+@\S+\.\S+$/,message:"电子邮件地址不合法！",trigger:"blur"},{asyncValidator:i,trigger:"blur"}],agreement:[{type:"enum",enum:[!0],message:"注册前请阅读用户注册协议，并勾选同意",trigger:"change"}]}},c={data:function(){return{passwordVisible:!1,matchingPasswordVisible:!1,layout:{labelCol:{span:4},wrapperCol:{span:12}},tailFormItemLayout:{wrapperCol:{xs:{span:24,offset:0},sm:{span:16,offset:8}}},model:{username:"",password:"",matchingPassword:"",name:"",mobile:"",email:"",agreement:!1},rules:{}}},methods:{submitForm:function(e){var t=this;this.$refs[e].validate((function(e){return!!e&&(t.$store.dispatch("registerModule/register",t.model),!0)}))},handleNavLogin:function(){this.$router.push("/login")}},computed:{registerError:function(){var e;return null===(e=this.$store.state.register)||void 0===e?void 0:e.error}},mounted:function(){this.rules=u(this.model,this.$refs.regForm)}},p=c,g=r("2877"),f=Object(g["a"])(p,a,o,!1,null,null,null);t["default"]=f.exports}}]);
//# sourceMappingURL=register.b91d9cea.js.map