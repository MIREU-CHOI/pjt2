// import React from "react";
// import { useState, useDispatch } from "react";

// function RegisterPage(props) {

//     const dispatch = useDispatch();

//     // props , state -> 이 안에서는 state 를 변화시켜서 이 안의 데이터를 변화시킬 수 있음
//     // 입력한 이메일과 비밀번호를 서버에 넘길 수 있도록 state 에서 받고 있음
//     const [Email, setEmail] = useState("")
//     const [Password, setPassword] = useState("")
//     const [Name, setName] = useState("")
//     const [confirmPassword, setconfirmPassword] = useState("")

//     const onEmailHandler = (event) => {
//         setEmail(event.currentTarget.value)
//     }

//     const onPasswordHandler = (event) => {
//         setPassword(event.currentTarget.value)
//     }

//     const onNameHandler = (event) => {
//         setName(event.currentTarget.value)
//     }

//     const onconfirmPasswordHandler = (event) => {
//         setconfirmPassword(event.currentTarget.value)
//     }

//     const hasError = passwordEntered =>
//         Password.length < 5 ? true : false;
    
//     const hasNotSameError = passwordEntered =>
//         Password != confirmPassword ? true : false;    

//     const onSubmitHandler = (event) => {
//         event.preventDefault(); // 아무 동작 안하고 버튼만 눌러도 리프레쉬 되는 것을 막는다

//         if(Password !== confirmPassword){
//             return alert('비밀번호와 비밀번호 확인은 같아야 합니다.')
//         }

//         let body = {
//             email: Email,
//             password: Password,
//             name: Name,
//         }
//         dispatch(registerUser(body))
//             .then(response =>{
//                 if(response.payload.success){
//                     alert('회원가입이 완료되었습니다!');
//                     props.history.push('/login') // react 에서의 페이지 이동 코드
//                 } else{
//                     alert('Error!!');
//                 }
//             })
//         // 완료가 잘 되었을 경우 이동
//     }

//     const classes = useStyles();

//     return (
//             <Container component="main" maxWidth="xs">
//               <CssBaseline />
//               <div className={classes.paper}>
//                 <Avatar className={classes.avatar}>
//                   <LockOutlinedIcon />
//                 </Avatar>
//                 <Typography component="h1" variant="h5">
//                   Messenger Sign Up
//                 </Typography>
//                 <form className={classes.form} noValidate onSubmit={onSubmitHandler}>
//                     <Grid item xs={12}>
//                       <TextField
//                         autoComplete="fname"
//                         name="firstName"
//                         variant="filled"
//                         value={Name}
//                         onChange={onNameHandler}
//                         required
//                         fullWidth
//                         id="firstName"
//                         label="이름"
//                         autoFocus
//                       />
//                     </Grid>
//                     <Grid item xs={12}>
//                       <TextField
//                          variant="filled"
//                         required
//                         fullWidth
//                         value={Email}
//                         onChange={onEmailHandler}
//                         id="email"
//                         label="Email Address"
//                         name="email"
//                         autoComplete="email"
//                       />
//                     </Grid>
//                     <Grid item xs={12}>
//                       <TextField
//                         variant="filled"
//                         required
//                         fullWidth
//                         value={Password}
//                         onChange={onPasswordHandler}
//                         name="password"
//                         error={hasError('password')} // 해당 텍스트필드에 error 핸들러 추가
//                         label="Password(5글자 이상 필수)"
//                         type="password"
//                         id="password"
//                         autoComplete="current-password"
//                       />
//                     </Grid>
//                     <Grid item xs={12}>
//                       <TextField
//                         variant="filled"
//                         required
//                         fullWidth
//                         value={confirmPassword}
//                         onChange={onconfirmPasswordHandler}
//                         name="confirmPassword"
//                         error={hasNotSameError('confirmPassword')} // 해당 텍스트필드에 error 핸들러 추가
//                         helperText={
//                             hasNotSameError('confirmPassword') ? "입력한 비밀번호와 일치하지 않습니다." : null
//                         } // 에러일 경우에만 안내 문구 표시
//                         label="Confirm Password"
//                         type="password"
//                         id="confirmPassword"
//                         autoComplete="current-password"
//                       />
//                     </Grid>
//                   <Button
//                     type="submit"
//                     fullWidth
//                     variant="contained"
//                     onSubmit={onSubmitHandler}
//                     color="primary"
//                     className={classes.submit}
//                   >
//                     회원가입
//                   </Button>
//                   <Grid container justify="flex-end">
//                     <Grid item>
//                       <Link href="/login" variant="body2">
//                         이미 가입하셨다면, 로그인해 주세요!
//                       </Link>
//                     </Grid>
//                   </Grid>
//                 </form>
//               </div>
//               <Box mt={5}>
                
//               </Box>
//             </Container>
//     )
// }

// export default Join3;