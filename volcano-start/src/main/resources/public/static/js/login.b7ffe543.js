(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["login"],{5326:function(e,t,r){"use strict";r.r(t);var o=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("a-row",{staticStyle:{"min-height":"100vh"},attrs:{type:"flex",justify:"center",align:"middle"}},[r("a-col",{attrs:{span:"8"}},[e.loginError?r("div",[r("a-alert",{staticStyle:{width:"100%"},attrs:{message:"登录失败",description:e.loginError,type:"error"}})],1):e._e(),r("a-form-model",e._b({ref:"form",attrs:{model:e.model,rules:e.rules}},"a-form-model",e.layout,!1),[r("a-form-model-item",{attrs:{"has-feedback":"",prop:"username"}},[r("a-input",{attrs:{placeholder:"用户名"},model:{value:e.model.username,callback:function(t){e.$set(e.model,"username",t)},expression:"model.username"}},[r("a-icon",{staticStyle:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"user"},slot:"prefix"})],1)],1),r("a-form-model-item",{attrs:{"has-feedback":"",prop:"password"}},[r("a-input",{attrs:{type:"password",placeholder:"密码"},model:{value:e.model.password,callback:function(t){e.$set(e.model,"password",t)},expression:"model.password"}},[r("a-icon",{staticStyle:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"lock"},slot:"prefix"})],1)],1),r("a-form-model-item",e._b({},"a-form-model-item",e.tailFormItemLayout,!1),[r("a-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitForm("form")}}},[e._v("登录")]),r("a-button",{attrs:{type:"link",htmlType:"button"},on:{click:function(t){return e.handleNavRegister()}}},[e._v("还没有注册？")])],1)],1)],1)],1)},a=[],s={username:[{required:!0,message:"用户名为必填项！",trigger:"blur"}],password:[{required:!0,message:"密码为必填项！",trigger:"blur"}]},l={data:function(){return{layout:{labelCol:{span:0},wrapperCol:{span:24}},tailFormItemLayout:{wrapperCol:{xs:{span:24,offset:0},sm:{span:24,offset:8}}},model:{username:"",password:""},rules:s}},methods:{submitForm:function(e){var t=this;this.$refs[e].validate((function(e){e&&t.$store.dispatch("login",{username:t.model.username,password:t.model.password})}))},handleNavRegister:function(){this.$router.push("/register")}},computed:{loginError:function(){return this.$store.state.loginErrMsg}}},n=l,i=r("2877"),m=Object(i["a"])(n,o,a,!1,null,null,null);t["default"]=m.exports}}]);
//# sourceMappingURL=login.b7ffe543.js.map