export const loginRules = {
  username: [
    {
      required: true,
      message: "用户名为必填项！",
      trigger: "blur",
    },
  ],
  password: [
    {
      required: true,
      message: "密码为必填项！",
      trigger: "blur",
    },
  ]
};
